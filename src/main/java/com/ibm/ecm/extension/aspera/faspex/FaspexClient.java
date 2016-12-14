/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Faspex;
import com.asperasoft.faspex.client.sdklite.data.UploadSettings;
import com.asperasoft.faspmanager.XferParams;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginServiceCallbacks;
import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A faspex client used to send packages to a fapex server.
 */
public class FaspexClient {
	private static final int DEFAULT_MAX_NUMBER_OF_REQUESTS = 5;
	private static final int DEFAULT_TARGET_TRANSFER_RATE_MBPS = 100;
	private static final int DEFAULT_ARCHIVE_DURATION_MINUTES = 60;
	private static final int DEFAULT_PROGRESS_TIMEOUT_SECONDS = 30;
	private static final int DEFAULT_START_TIMEOUT_SECONDS = 30;
	private static final String CERT_VALIDATION_OFF = "ICNAPCertValidationOff";
	private static final String DEBUG_MODE_ON = "ICNAPDebugModeOn";

	private static FaspexClient client;

	private PluginLogger logger;
	private String asperaServerUrl;
	private boolean certValidationOff;
	private boolean debugModeOn;
	private UploadSettings uploadSettings;
	private int maxNumberOfRequests;
	private Instant archiveDuration;
	private Instant progressTimeout;
	private int startTimeoutSeconds;
	private List<SendProcess> processes = new ArrayList<>();
	private List<SendProcess> recentArchives = new ArrayList<>();

	private FaspexClient(PluginServiceCallbacks pluginCallbacks) throws Exception {
		this((JSONObject) JSON.parse(pluginCallbacks.loadConfiguration()), pluginCallbacks.getLogger());
	}

	FaspexClient(JSONObject pluginConfig, PluginLogger logger) {
		this.logger = logger;
		asperaServerUrl = (String) pluginConfig.get("asperaServerUrl");
		certValidationOff = Boolean.parseBoolean(System.getenv(CERT_VALIDATION_OFF));
		debugModeOn = Boolean.parseBoolean(System.getenv(DEBUG_MODE_ON));
		maxNumberOfRequests = getIntegerConfigValue(pluginConfig, "maxNumberOfRequests", DEFAULT_MAX_NUMBER_OF_REQUESTS);

		String ascpPath = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources", "bin", "ascp4").toString();
		String privateKeyPath = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources", "asperaweb_id_dsa.openssh").toString();
		XferParams params = new XferParams();
		params.targetRateKbps = getIntegerConfigValue(pluginConfig, "targetTransferRateMbps", DEFAULT_TARGET_TRANSFER_RATE_MBPS) * 1000;
		params.localLogDir = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources").toString();
		uploadSettings = UploadSettings.forStreams(privateKeyPath, ascpPath, null, params);
		uploadSettings.setAutoShutdown(false);

		int duration = getIntegerConfigValue(pluginConfig, "archiveDurationMinutes", DEFAULT_ARCHIVE_DURATION_MINUTES);
		archiveDuration = Instant.now().minus(duration, ChronoUnit.MINUTES);
		int timeout = getIntegerConfigValue(pluginConfig, "progressTimeoutSeconds", DEFAULT_PROGRESS_TIMEOUT_SECONDS);
		progressTimeout = Instant.now().minus(timeout, ChronoUnit.SECONDS);
		startTimeoutSeconds = getIntegerConfigValue(pluginConfig, "startTimeoutSeconds", DEFAULT_START_TIMEOUT_SECONDS);
	}

	int getIntegerConfigValue(JSONObject pluginConfig, String option, int defaultValue) {
		int value = defaultValue;
		try {
			Object obj = pluginConfig.get(option);
			if (obj != null) {
				String str = obj.toString();
				logger.logDebug(this, "getIntegerConfigValue", "option: " + option + ", value: " + str);
				value = Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
			logger.logWarning(this, "getIntegerConfigValue", "Failed to get the integer value" + e);
		}

		return value;
	}

	/**
	 * Gets a singleton instance of this class.
	 *
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @return The singleton instance of this class
	 */
	public static synchronized FaspexClient getInstance(PluginServiceCallbacks callbacks) throws Exception {
		if (client != null)
			return client;

		client = new FaspexClient(callbacks);

		return client;
	}

	/**
	 * Stops all in-progress processes and terminates the instance.
	 */
	public static synchronized void terminate() {
		if (client == null)
			return;

		client.stopAllProcesses();
		Faspex.shutdown();
		client = null;
	}

	/**
	 * Sends a package asynchronously and returns the Id of the package once a send process is started.
	 *
	 * @param pkg The package to send
	 * @return The Id of the package being sent
	 * @throws Exception if an error occurs while starting a send process or a send process is not started within 30 seconds
	 */
	public String startProcess(Package pkg) throws Exception {
		logger.logEntry(this, "startProcess");
		if (isMaxNumberOfRequestsReached())
			throw new RuntimeException("The maximum number of requests reached.");

		Faspex faspex = new Faspex(asperaServerUrl, pkg.getSender(), pkg.getPassword(), certValidationOff, debugModeOn);
		SendProcess process = new SendProcess(faspex, uploadSettings, pkg, this::archiveProcess, startTimeoutSeconds, logger);
		addProcess(process);
		String packageId = process.start();
		logger.logExit(this, "startProcess");

		return packageId;
	}

	/**
	 * Gets the process data in JSON format.
	 *
	 * @param packageId The Id of the package
	 * @return JSONObject The process data in JSON format
	 */
	public synchronized JSONObject processToJson(String packageId) {
		SendProcess process = getProcess(packageId);
		if (process == null)
			process = getArchivedProcess(packageId);

		return process == null ? null : process.toJson();
	}

	SendProcess getProcess(String packageId) {
		if (packageId == null)
			return null;

		return processes.stream().filter(p -> packageId.equals(p.getPackageId())).findFirst().orElse(null);
	}

	SendProcess getArchivedProcess(String packageId) {
		if (packageId == null)
			return null;

		return recentArchives.stream().filter(p -> packageId.equals(p.getPackageId())).findFirst().orElse(null);
	}

	/**
	 * Stops an in-progress process.
	 *
	 * @param packageId The Id of the package
	 * @return true if the process was stopped; false if there isn't a process with the package Id
	 */
	public synchronized boolean stopProcess(String packageId) {
		boolean stopped = false;
		SendProcess process = getProcess(packageId);
		if (process != null) {
			try {
				logger.logDebug(this, "stopProcess", "stop package: " + packageId);
				stopped = process.stop();
			} catch (Exception e) {
				logger.logDebug(this, "stopProcess", "failed to stop package: " + packageId);
				logger.logError(this, "stopProcess", e);
			}
		}

		return stopped;
	}

	/**
	 * Stops the specified in-progress processes.
	 *
	 * @param packageIds The Ids of the packages to be stopped
	 * @return The stopped package Ids
	 */
	public synchronized String[] stopProcesses(String... packageIds) {
		return Arrays.stream(packageIds).filter(id -> {
			boolean stopped = false;
			SendProcess process = getProcess(id);
			if (process != null) {
				try {
					logger.logDebug(this, "stopProcesses", "stop package: " + id);
					stopped = process.stop();
				} catch (Exception e) {
					logger.logDebug(this, "stopProcesses", "failed to stop package: " + id);
					logger.logError(this, "stopProcesses", e);
				}
			}

			return stopped;
		}).toArray(String[]::new);
	}

	/**
	 * Stops all in-progress processes.
	 *
	 * @return The stopped package Ids
	 */
	public synchronized String[] stopAllProcesses() {
		String[] packageIds = processes.stream().map(SendProcess::getPackageId).toArray(String[]::new);

		return stopProcesses(packageIds);
	}

	/**
	 * Gets the summary of recently sent packages in JSON format.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The summary of packages in JSON format
	 */
	public JSONArray archivedProcessesToJson(String... packageIds) {
		if (packageIds == null)
			return new JSONArray();

		return recentArchives.stream().filter(p -> Arrays.stream(packageIds).anyMatch(id -> id.equals(p.getPackageId()))).map(SendProcess::summaryToJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
	}

	/**
	 * Gets the summary of all recently sent packages in JSON format.
	 *
	 * @return The summary of packages in JSON format
	 */
	public JSONArray archivedProcessesToJson() {
		return recentArchives.stream().map(SendProcess::summaryToJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
	}

	/**
	 * Gets the summary of in-progress processes in JSON format.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The summary of processes in JSON format
	 */
	public JSONArray runningProcessesToJson(String... packageIds) {
		if (packageIds == null)
			return new JSONArray();

		return processes.stream().filter(p -> Arrays.stream(packageIds).anyMatch(id -> id.equals(p.getPackageId()))).map(SendProcess::summaryToJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
	}

	/**
	 * Gets the summary of all in-progress processes in JSON format.
	 *
	 * @return The summary of processes in JSON format
	 */
	public JSONArray runningProcessesToJson() {
		return processes.stream().map(SendProcess::summaryToJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
	}

	/**
	 * Filters the package Ids to return ones that are being sent or recently sent.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The filtered package Ids
	 */
	public String[] getExistentPackageIds(String... packageIds) {
		if (packageIds == null)
			return new String[]{};

		return Arrays.stream(packageIds).filter(id -> getProcess(id) != null || getArchivedProcess(id) != null).toArray(String[]::new);
	}

	synchronized void addProcess(SendProcess process) {
		processes.add(process);
	}

	synchronized void archiveProcess(SendProcess process) {
		processes.remove(process);
		if (!recentArchives.contains(process))
			recentArchives.add(process);
	}

	/**
	 * Stops hanging processes and purses recently sent packages that are older than 30 minutes.
	 */
	public synchronized void cleanUpProcesses() {
		stopHangingProcesses();
		purgeExpiredArchives();
	}

	private synchronized void stopHangingProcesses() {
		List<SendProcess> hanging = getHangingProcesses();
		hanging.forEach(p -> {
			try {
				logger.logDebug(this, "stopHangingProcesses", "stop hanging process: " + p.getPackageId());
				p.stop();
			} catch (Exception e) {
				logger.logDebug(this, "stopHangingProcesses", "failed to stop hanging process: " + p.getPackageId());
				logger.logError(this, "stopHangingProcesses", e);
			}
		});
	}

	List<SendProcess> getHangingProcesses() {
		return processes.stream().filter(p -> p.isStarted() && p.getUpdatedOn().isBefore(progressTimeout)).collect(Collectors.toList());
	}

	synchronized void purgeExpiredArchives() {
		recentArchives = recentArchives.stream().filter(p -> p.getUpdatedOn().isAfter(archiveDuration)).collect(Collectors.toList());
	}

	private synchronized int numberOfRunningProcesses() {
		return processes.size();
	}

	/**
	 * Returns true if the maximum number of request has been reached.
	 *
	 * @return true if the maximum number of request has been reached
	 */
	public synchronized boolean isMaxNumberOfRequestsReached() {
		return numberOfRunningProcesses() >= maxNumberOfRequests;
	}
}
