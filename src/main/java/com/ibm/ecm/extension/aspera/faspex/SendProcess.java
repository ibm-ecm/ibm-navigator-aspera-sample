/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Client;
import com.asperasoft.faspex.client.sdklite.Faspex;
import com.asperasoft.faspex.client.sdklite.PackageTransferListener;
import com.asperasoft.faspex.client.sdklite.data.*;
import com.asperasoft.faspmanager.TransferEvent;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * A send process used to start, stop, and get progress of packages being sent.
 */
class SendProcess {
	private Lock lock = new ReentrantLock();
	private Condition initialized = lock.newCondition();

	private Faspex faspex;
	private UploadSettings uploadSettings;
	private Consumer<SendProcess> callback;

	private PluginLogger logger;

	private String id;
	private String packageId;
	private Package pkg;
	private long size;
	private Status status;
	private Instant updatedOn;
	private String lastEvent;
	private JSONObject progress;
	private int startTimeoutSeconds;

	/**
	 * Constructs a send process with the specified parameters.
	 *
	 * @param faspex         The Faspex instance
	 * @param uploadSettings The Faspex upload settings
	 * @param pkg            The package to send
	 * @param callback       The function to be called when the process is completed
	 * @param logger         The plug-in logger
	 */
	SendProcess(Faspex faspex, UploadSettings uploadSettings, Package pkg, Consumer<SendProcess> callback, int startTimeoutSeconds, PluginLogger logger) {
		this.id = UUID.randomUUID().toString();
		this.faspex = faspex;
		this.uploadSettings = uploadSettings;
		this.pkg = pkg;
		this.callback = callback;
		this.logger = logger;
		updateStatus(Status.PENDING);
		this.progress = new JSONObject();
		this.startTimeoutSeconds = startTimeoutSeconds;
	}

	/**
	 * Starts sending a package to a faspex server. The Id of the package is returned once a process is started.
	 *
	 * @return The Id of the package being sent
	 * @throws Exception if an error occurs while starting a process or a process is not started within 30 seconds
	 */
	String start() throws Exception {
		String loggingMethod = "start";
		logger.logEntry(this, loggingMethod);
		if (!isPending())
			throw new RuntimeException("The package has started already.");

		FaspexPackage faspPkg = new FaspexPackage(pkg.getTitle(), pkg.getNote(), pkg.getRecipients());
		List<Package.Content> contents = pkg.retrieveContents();
		List<Source> sourceInfo = createSourceInfo(contents);
		size = contents.stream().mapToLong(Package.Content::getSize).sum();
		if (pkg.isNotifyOnUpload())
			faspPkg.setUploadNotifyRecipients(Collections.singletonList(pkg.getSender()));
		if (pkg.isNotifyOnDownload())
			faspPkg.setDownloadNotifyRecipients(Collections.singletonList(pkg.getSender()));

		logger.logDebug(this, loggingMethod, "start process: " + id);
		faspex.sendPackageAndGetSpec(faspPkg, sourceInfo, uploadSettings, new PackageTransferListener() {
			@Override
			public void onTransferEvent(TransferEvent event, TransferSessionInfo info) {
				onSendProcessEvent(event, info);
			}

			@Override
			public void onError(ErrorInfo info) {
				onSendProcessError(info);
			}

			@Override
			public void onDone() {
				onSendProcessDone();
			}
		}, (transferSpec, packageId) -> onSendProcessSpecReceived(packageId));

		waitForSignal();
		logger.logDebug(this, loggingMethod, "started process: " + id);
		logger.logExit(this, loggingMethod);

		return packageId;
	}

	void waitForSignal() throws InterruptedException {
		lock.lock();
		try {
			Instant await = Instant.now();
			while (isPending()) {
				initialized.await(startTimeoutSeconds, TimeUnit.SECONDS);
				Instant signalTime = Instant.now();
				if (isFailed() || Duration.between(await, signalTime).getSeconds() >= startTimeoutSeconds) {
					if (!isFailed()) {
						try {
							logger.logDebug(this, "waitForSignal", "time out process: " + id);
							stop();
						} catch (Exception e) {
							logger.logWarning(this, "waitForSignal", "failed to stop: " + e);
						}
					}
					throw new RuntimeException("Failed to start the process: " + id);
				}
			}
		} finally {
			if (lock != null)
				lock.unlock();
		}
	}

	void updateStatus(Status status) {
		this.status = status;
		setUpdatedOn(Instant.now());
	}

	void onSendProcessSpecReceived(String packageId) {
		this.packageId = packageId;
		logger.logDebug(this, "onSendProcessSpecReceived", "spec received for process: " + id + ", packageId: " + packageId);
	}

	void onSendProcessEvent(TransferEvent event, TransferSessionInfo info) {
		lastEvent = event.toString();
		logger.logDebug(this, "onSendProcessEvent", "packageId: " + packageId);
		logger.logDebug(this, "onSendProcessEvent", "event: " + event);
		if (logger.isDebugLogged())
			Client.consoleOut(event, info);

		switch (event) {
			case PROGRESS:
				try {
					String details = Client.getSessionProgressDetails(info, true);
					if (details != null && !details.isEmpty())
						updateProgress((JSONObject) JSON.parse(details));
				} catch (IOException e) {
					logger.logWarning(this, "onSendProcessEvent", "failed to parse progress details: " + e);
				}
				break;
			case SESSION_ERROR:
				onFailed();
				break;
			case SESSION_START:
				onStarted();
				break;
		}
	}

	private void updateProgress(JSONObject newProgress) {
		Object previous = progress.get("bytesTransferred");
		Object now = newProgress.get("bytesTransferred");
		if (now != null && !now.equals(previous))
			setUpdatedOn(Instant.now());

		progress = newProgress;
	}

	void sendSignal() {
		lock.lock();
		try {
			logger.logDebug(this, "sendSignal", "signal all");
			initialized.signalAll();
		} catch (Exception e) {
			logger.logWarning(this, "sendSignal", "failed to signal all: " + e);
		} finally {
			lock.unlock();
		}
	}

	void onSendProcessError(ErrorInfo info) {
		logger.logDebug(this, "onSendProcessError", "package failed: " + packageId);
		logger.logDebug(this, "onSendProcessError", info.toString());
		onFailed();
	}

	void onSendProcessDone() {
		logger.logDebug(this, "onSendProcessDone", "package sent: " + packageId);
		onCompleted();
	}

	private List<Source> createSourceInfo(List<Package.Content> contents) {
		List<Source> sourceInfo = new ArrayList<>();
		String loggingMethod = "createSourceInfo";
		List<String> filenames = new ArrayList<>(contents.size());
		logger.logDebug(this, loggingMethod, "create source files: " + id);
		contents.forEach(c -> {
			try {
				String filename = getUniqueName(filenames, c.getFileName());
				filenames.add(filename);
				logger.logDebug(this, loggingMethod, "create source: " + filename);
				sourceInfo.add(Source.createStreamInputSource(c.getInputStream(), filename));
			} catch (Exception e) {
				throw new RuntimeException("Failed to create source", e);
			}
		});

		return sourceInfo;
	}

	String getUniqueName(List<String> existingNames, String newName) {
		if (!existingNames.contains(newName))
			return newName;

		logger.logDebug(this, "getUniqueName", "non-unique filename: " + newName);

		return IntStream.range(1, existingNames.size() + 1).mapToObj(i -> {
			String name = newName;
			String ext = "";
			int dot = newName.lastIndexOf(".");
			if (dot > 0) {
				name = newName.substring(0, dot);
				ext = newName.substring(dot);
			}
			return name + i + ext;
		}).filter(n -> !existingNames.contains(n)).findFirst().orElse(newName);
	}

	void onStarted() {
		updateStatus(Status.STARTED);
		sendSignal();
	}

	void onFailed() {
		updateStatus(Status.FAILED);
		sendSignal();
		onCompleted();
	}

	private void onCompleted() {
		pkg.closeContents();
		if (isStarted())
			updateStatus(Status.COMPLETED);
		if (callback != null)
			callback.accept(this);

		lock = null;
		initialized = null;
		faspex = null;
		uploadSettings = null;
		callback = null;
	}

	/**
	 * Stops the process and updates the progress.
	 *
	 * @throws Exception if an error occurs while stopping the process
	 */
	boolean stop() throws Exception {
		boolean stopped = false;
		try {
			if (isPending() || isStarted()) {
				faspex.stop();
				updateStatus(Status.STOPPED);
				stopped = true;
				logger.logDebug(this, "stop", "stopped package: " + packageId);
			}
		} finally {
			onCompleted();
		}

		return stopped;
	}

	/**
	 * Returns true if the process has not started.
	 *
	 * @return true if the process has not started
	 */
	boolean isPending() {
		return status == Status.PENDING;
	}

	/**
	 * Returns true if the process is started.
	 *
	 * @return true if the process is started
	 */
	boolean isStarted() {
		return status == Status.STARTED;
	}

	/**
	 * Returns true if the process is failed.
	 *
	 * @return true if the process is failed
	 */
	boolean isFailed() {
		return status == Status.FAILED;
	}

	/**
	 * Returns true if the process is stopped.
	 *
	 * @return true if the process is stopped
	 */
	boolean isStopped() {
		return status == Status.STOPPED;
	}

	/**
	 * Returns true if the process is completed.
	 *
	 * @return true if the process is completed
	 */
	boolean isCompleted() {
		return status == Status.COMPLETED;
	}

	/**
	 * Gets the Id of the package. The Id is set once the process is started.
	 *
	 * @return The Id of the package
	 */
	String getPackageId() {
		return packageId;
	}

	JSONObject toJson() {
		JSONObject json = summaryToJson();
		json.put("recipients", String.join(",", pkg.getRecipients()));
		json.put("note", pkg.getNote());
		json.put("progress", progress);

		return json;
	}

	/**
	 * Gets the summary in JSON format.
	 *
	 * @return The summary in JSON format
	 */
	JSONObject summaryToJson() {
		JSONObject json = new JSONObject();
		json.put("id", packageId);
		json.put("title", pkg.getTitle());
		json.put("sender", pkg.getSender());
		json.put("size", size);
		json.put("status", status.toString());
		json.put("updatedOn", updatedOn.toString());
		json.put("lastEvent", lastEvent);

		return json;
	}

	/**
	 * Gets the time when the status was last updated.
	 *
	 * @return The last updated time
	 */
	Instant getUpdatedOn() {
		return updatedOn;
	}

	void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}

	enum Status {
		PENDING,
		STARTED,
		STOPPED,
		FAILED,
		COMPLETED
	}
}
