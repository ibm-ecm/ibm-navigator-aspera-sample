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
import com.ibm.json.java.JSONObject;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

/**
 * A Faspex client used to send packages to a Fapex server.
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
	private final Transfers transfers = new Transfers();
	private final PluginLogger logger;
	private final String asperaServerUrl;
	private final boolean certValidationOff;
	private final boolean debugModeOn;
	private final UploadSettings uploadSettings;
	private final int maxNumberOfRequests;
	private final Instant archiveDuration;
	private final Instant progressTimeout;
	private final int startTimeoutSeconds;

	FaspexClient(final JSONObject pluginConfig, final PluginLogger logger) {
		this.logger = logger;
		asperaServerUrl = (String) pluginConfig.get("asperaServerUrl");
		certValidationOff = Boolean.parseBoolean(System.getenv(CERT_VALIDATION_OFF));
		debugModeOn = Boolean.parseBoolean(System.getenv(DEBUG_MODE_ON));
		maxNumberOfRequests = getIntegerConfigValue(pluginConfig, "maxNumberOfRequests", DEFAULT_MAX_NUMBER_OF_REQUESTS);

		final String ascpPath = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources", "bin", "ascp4").toString();
		final String privateKeyPath = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources", "asperaweb_id_dsa.openssh").toString();
		final XferParams params = new XferParams();
		params.targetRateKbps = getIntegerConfigValue(pluginConfig, "targetTransferRateMbps", DEFAULT_TARGET_TRANSFER_RATE_MBPS) * 1000;
		params.localLogDir = Paths.get(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources").toString();
		uploadSettings = UploadSettings.forStreams(privateKeyPath, ascpPath, null, params);
		uploadSettings.setAutoShutdown(false);

		final int duration = getIntegerConfigValue(pluginConfig, "archiveDurationMinutes", DEFAULT_ARCHIVE_DURATION_MINUTES);
		archiveDuration = Instant.now().minus(duration, ChronoUnit.MINUTES);
		final int timeout = getIntegerConfigValue(pluginConfig, "progressTimeoutSeconds", DEFAULT_PROGRESS_TIMEOUT_SECONDS);
		progressTimeout = Instant.now().minus(timeout, ChronoUnit.SECONDS);
		startTimeoutSeconds = getIntegerConfigValue(pluginConfig, "startTimeoutSeconds", DEFAULT_START_TIMEOUT_SECONDS);
	}

	/**
	 * Returns the singleton instance of this class. A new instance will be created if the instance doesn't exist.
	 *
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @return The singleton instance of this class
	 */
	public static FaspexClient getInstance(final PluginServiceCallbacks callbacks) {
		synchronized (FaspexClient.class) {
			if (client == null) {
				client = getNewInstance(callbacks);
			}
			client.cleanUpTransfers();

			return client;
		}
	}

	/**
	 * Returns an instance of this class. The instance will be remembered and returned when the <code>FaspexClient.getInstance()</code>
	 * method without any parameter is called.
	 *
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @return An instance of this class
	 */
	public static FaspexClient getNewInstance(final PluginServiceCallbacks callbacks) {
		synchronized (FaspexClient.class) {
			try {
				client = new FaspexClient((JSONObject) JSON.parse(callbacks.loadConfiguration()), callbacks.getLogger());

				return client;
			} catch (final Exception e) { // NOPMD - thrown from the super method
				throw new FaspexRuntimeException("Failed to get an instance of Faspex client.", e);
			}
		}
	}

	/**
	 * Stops all in-progress transfers and terminates the instance.
	 */
	public static void terminate() {
		synchronized (FaspexClient.class) {
			if (client == null) {
				return;
			}

			client.stopAllTransfers();
			Faspex.shutdown();
			client = null; // NOPMD - set to null to create a new instance next time
		}
	}

	int getIntegerConfigValue(final JSONObject pluginConfig, final String option, final int defaultValue) {
		final Object obj = pluginConfig.get(option);
		if (obj != null) {
			final String str = obj.toString();
			logger.logDebug(this, "getIntegerConfigValue", "option: " + option + ", value: " + str);
			try {
				return Integer.parseInt(obj.toString());
			} catch (final NumberFormatException e) {
				logger.logWarning(this, "getIntegerConfigValue", "Failed to get the integer value" + e);
			}
		}

		return defaultValue;
	}

	/**
	 * Sends a package asynchronously and returns the Id of the package once a transfer is started.
	 *
	 * @param pkg The package to send
	 * @return The Id of the package being sent
	 * @throws FaspexRuntimeException if an error occurs while starting a transfer or a transfer is not started
	 *                                within 30 seconds
	 */
	public String startTransfer(final Package pkg) throws FaspexRuntimeException {
		logger.logEntry(this, "startTransfer");
		if (isMaxNumberOfRequestsReached()) {
			throw new MaxRequestsReachedException("The maximum number of requests reached: " + maxNumberOfRequests);
		}

		final Faspex faspex = new Faspex(asperaServerUrl, pkg.getSender(), pkg.getPassword(), certValidationOff, debugModeOn);
		final Transfer transfer = new Transfer(faspex, uploadSettings, pkg, this.transfers::archiveTransfer, startTimeoutSeconds, logger);
		transfers.addTransfer(transfer);
		final String packageId = transfer.start();
		logger.logExit(this, "startTransfer");

		return packageId;
	}

	/**
	 * Stops an in-progress transfer.
	 *
	 * @param packageId The Id of the package
	 * @return true if the transfer was stopped; false if there isn't a transfer with the package Id
	 */
	public boolean stopTransfer(final String packageId) {
		final Transfer transfer = transfers.getTransfer(packageId);
		if (transfer != null) {
			logger.logDebug(this, "stopTransfer", "stop package: " + packageId);

			return transfer.stop();
		}

		return false;
	}

	/**
	 * Stops the specified in-progress transfers.
	 *
	 * @param packageIds The Ids of the packages to be stopped
	 * @return The stopped package Ids
	 */
	public String[] stopTransfers(final String... packageIds) {
		return Arrays.stream(packageIds).filter(id -> {
			boolean stopped = false;
			final Transfer transfer = transfers.getTransfer(id);
			if (transfer != null) {
				try {
					logger.logDebug(this, "stopTransfers", "stop package: " + id);
					stopped = transfer.stop();
				} catch (final FaspexRuntimeException e) {
					logger.logDebug(this, "stopTransfers", "failed to stop package: " + id);
					logger.logError(this, "stopTransfers", e);
				}
			}

			return stopped;
		}).toArray(String[]::new);
	}

	/**
	 * Stops all in-progress transfers.
	 *
	 * @return The stopped package Ids
	 */
	public String[] stopAllTransfers() {
		return stopTransfers(transfers.getPackageIds());
	}

	/**
	 * Stops hanging transfers and purses recently sent packages that are older than 30 minutes.
	 */
	public void cleanUpTransfers() {
		stopHangingTransfers();
		transfers.purgeExpiredArchives(archiveDuration);
	}

	private void stopHangingTransfers() {
		final List<Transfer> hanging = transfers.getHangingTransfers(progressTimeout);
		hanging.forEach(p -> {
			final TransferSummary summary = p.getSummary();
			try {
				logger.logDebug(this, "stopHangingTransfers", "stop hanging transfer: " + summary.getPackageId());
				p.stop();
			} catch (final FaspexRuntimeException e) {
				logger.logDebug(this, "stopHangingTransfers", "failed to stop hanging transfer: " + summary.getPackageId());
				logger.logError(this, "stopHangingTransfers", e);
			}
		});
	}

	/**
	 * Returns true if the maximum number of request has been reached.
	 *
	 * @return true if the maximum number of request has been reached
	 */
	boolean isMaxNumberOfRequestsReached() {
		return transfers.getNumberOfRunningTransfers() >= maxNumberOfRequests;
	}

	/**
	 * Returns the helper object that can be used to retrieve transfer info.
	 *
	 * @return The helper object
	 */
	public Transfers getTransfers() {
		return transfers;
	}
}
