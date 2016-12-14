/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Faspex;
import com.asperasoft.faspex.client.sdklite.data.FaspexPackage;
import com.asperasoft.faspex.client.sdklite.data.Source;
import com.asperasoft.faspex.client.sdklite.data.UploadSettings;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSONObject;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * A transfer used to start, stop, and get progress of packages being sent.
 */
class Transfer {
	private static final String start = "start";

	private final Lock lock = new ReentrantLock();
	private final Condition initialized = lock.newCondition();

	private final Faspex faspex;
	private final UploadSettings uploadSettings;
	private final Package pkg;
	private final Consumer<Transfer> callback;
	private final int startTimeoutSeconds;
	private final PluginLogger logger;

	private final TransferSummary summary;
	private final FaspexTransferListener transferListener;
	private JSONObject progress;

	/**
	 * Constructs a transfer with the specified parameters.
	 *
	 * @param faspex              The Faspex instance
	 * @param uploadSettings      The Faspex upload settings
	 * @param pkg                 The package to send
	 * @param callback            The function to be called when the transfer is completed
	 * @param startTimeoutSeconds The start timeout value in seconds
	 * @param logger              The plug-in logger
	 */
	Transfer(final Faspex faspex, final UploadSettings uploadSettings, final Package pkg, final Consumer<Transfer> callback, final int startTimeoutSeconds, final PluginLogger logger) {
		this.faspex = faspex;
		this.uploadSettings = uploadSettings;
		this.pkg = pkg;
		this.callback = callback;
		this.startTimeoutSeconds = startTimeoutSeconds;
		this.logger = logger;
		this.summary = new TransferSummary(pkg);
		this.progress = new JSONObject();
		this.transferListener = new FaspexTransferListener(this, logger);
	}

	FaspexTransferListener getTransferListener() {
		return this.transferListener;
	}

	TransferSummary getSummary() {
		return summary;
	}

	/**
	 * Starts sending a package to a faspex server. The Id of the package is returned once a transfer is started.
	 *
	 * @return The Id of the package being sent
	 * @throws FaspexRuntimeException if an error occurs while starting a transfer or a transfer is not started within 30 seconds
	 */
	String start() throws FaspexRuntimeException {
		logger.logEntry(this, start);
		if (!summary.isPending()) {
			throw new FaspexRuntimeException("The transfer is not pending.", summary.toJson());
		}

		final FaspexPackage faspPkg = FaspexUtil.createFaspexPackage(pkg);
		final List<Package.Content> contents = pkg.retrieveContents();
		final List<Source> sourceInfo = FaspexUtil.createSourceInfo(contents, logger);
		summary.setSize(contents.stream().mapToLong(Package.Content::getSize).sum());

		logger.logDebug(this, start, "send package for the transfer: " + summary.toJson());
		try {
			faspex.sendPackageAndGetSpec(faspPkg, sourceInfo, uploadSettings, transferListener, (transferSpec, packageId) -> onTransferSpecReceived(packageId));
		} catch (final FaspexRuntimeException e) {
			pkg.closeContents();
			throw e;
		} catch (final RuntimeException e) { // NOPMD - rethrow as FaspexRuntimeException
			pkg.closeContents();
			throw new FaspexRuntimeException("Failed to send the package.", summary.toJson(), e);
		}
		waitForSignal();
		logger.logExit(this, start);

		return summary.getPackageId();
	}

	void waitForSignal() throws FaspexRuntimeException {
		lock.lock();
		try {
			while (summary.isPending()) {
				try {
					if (!initialized.await(startTimeoutSeconds, TimeUnit.SECONDS) || summary.isFailed()) {
						if (!summary.isFailed()) {
							logger.logDebug(this, "waitForSignal", "time out the transfer: " + summary.getId());
							stop();
						}
						throw new FaspexRuntimeException("Failed to start the transfer.", summary.toJson());
					}
				} catch (final InterruptedException e) {
					throw new FaspexRuntimeException("Failed to wait for a signal.", summary.toJson(), e);
				}
			}
		} finally {
			lock.unlock();
		}
	}

	void onTransferSpecReceived(final String packageId) {
		logger.logDebug(this, "onTransferSpecReceived", "spec received for the transfer: " + summary.getId() + ", packageId: " + packageId);
		summary.setPackageId(packageId);
	}

	private void sendSignal() {
		lock.lock();
		try {
			logger.logDebug(this, "sendSignal", "signal all");
			initialized.signalAll();
		} finally {
			lock.unlock();
		}
	}

	void onStarted() {
		summary.setStarted();
		sendSignal();
	}

	void onProgress(final JSONObject newProgress) {
		final Object previous = progress.get("bytesTransferred");
		final Object now = newProgress.get("bytesTransferred");
		if (now != null && !now.equals(previous)) {
			summary.setUpdatedOn(Instant.now());
		}

		progress = newProgress;
	}

	void onFailed() {
		summary.setFailed();
		sendSignal();
		onCompleted();
	}

	void onCompleted() {
		pkg.closeContents();
		if (summary.isStarted()) {
			summary.setCompleted();
		}
		if (callback != null) {
			callback.accept(this);
		}
	}

	/**
	 * Stops the transfer and updates the progress.
	 *
	 * @throws FaspexRuntimeException if an error occurs while stopping the package
	 */
	boolean stop() throws FaspexRuntimeException {
		try {
			if (summary.isPending() || summary.isStarted()) {
				faspex.stop();
				summary.setStopped();
				logger.logDebug(this, "stop", "stopped package: " + summary.getPackageId());

				return true;
			}
		} catch (final RuntimeException e) { // NOPMD - rethrow as FaspexRuntimeException
			throw new FaspexRuntimeException("Failed to stop the package.", summary.toJson(), e);
		} finally {
			onCompleted();
		}

		return false;
	}

	JSONObject toJson() {
		final JSONObject json = summary.toJson();
		json.put("recipients", String.join(",", pkg.getRecipients()));
		json.put("note", pkg.getNote());
		json.put("progress", progress);

		return json;
	}
}
