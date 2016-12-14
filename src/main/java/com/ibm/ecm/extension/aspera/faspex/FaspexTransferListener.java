/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Client;
import com.asperasoft.faspex.client.sdklite.PackageTransferListener;
import com.asperasoft.faspex.client.sdklite.data.ErrorInfo;
import com.asperasoft.faspex.client.sdklite.data.TransferSessionInfo;
import com.asperasoft.faspmanager.TransferEvent;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import java.io.IOException;

/**
 * A listener for the Faspex package transfer events.
 */
class FaspexTransferListener implements PackageTransferListener {
	private final Transfer transfer;
	private final PluginLogger logger;

	/**
	 * Constructs a package transfer listener.
	 *
	 * @param transfer The transfer
	 * @param logger  The plug-in logger
	 */
	FaspexTransferListener(final Transfer transfer, final PluginLogger logger) {
		this.transfer = transfer;
		this.logger = logger;
	}

	/**
	 * Handles transfer events.
	 *
	 * @param event The transfer event
	 * @param info  The session info
	 */
	public void onTransferEvent(final TransferEvent event, final TransferSessionInfo info) {
		final TransferSummary summary = transfer.getSummary();
		summary.setLastEvent(event.toString());
		logger.logDebug(this, "onTransferEvent", "packageId: " + summary.getPackageId() + ", event: " + event);
		if (logger.isDebugLogged()) {
			Client.consoleOut(event, info);
		}

		switch (event) {
			case SESSION_START:
				transfer.onStarted();
				break;
			case FILE_START:
				if (summary.isPending()) {
					logger.logWarning(this, "onTransferEvent", "starting without the session start event");
					transfer.onStarted();
				}
				break;
			case PROGRESS:
				onProgress(info);
				break;
			case SESSION_ERROR:
				transfer.onFailed();
				break;
			default:
				break;
		}
	}

	private void onProgress(final TransferSessionInfo info) {
		try {
			final String details = Client.getSessionProgressDetails(info, true);
			if (details != null && !details.isEmpty()) {
				transfer.onProgress((JSONObject) JSON.parse(details));
			}
		} catch (final IOException e) {
			logger.logWarning(this, "onProgress", "failed to parse progress details: " + e);
		}
	}

	/**
	 * Handles error events
	 *
	 * @param info The error info
	 */
	public void onError(final ErrorInfo info) {
		logger.logDebug(this, "onError", "package failed: " + transfer.getSummary().getPackageId() + " - " + info.toString());
		transfer.onFailed();
	}

	/**
	 * Handles the success event.
	 */
	public void onDone() {
		logger.logDebug(this, "onDone", "package sent: " + transfer.getSummary().getPackageId());
		transfer.onCompleted();
	}
}
