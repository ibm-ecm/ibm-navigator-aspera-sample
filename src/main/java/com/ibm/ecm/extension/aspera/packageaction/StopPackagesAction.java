/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONArtifact;

/**
 * The action to stop packages.
 */
class StopPackagesAction extends PackageAction {
	private static final String run = "run";

	StopPackagesAction(final FaspexClient client, final PackageActionType actionType, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		super(client, actionType, null, userPackageIds, adminUser, logger);
	}

	/**
	 * Returns true if the current user is an administrator or the action is stopping the packages from the current user.
	 *
	 * @return true if valid
	 */
	protected boolean isValid() {
		return adminUser || actionType != PackageActionType.stopAllFromAll;
	}

	/**
	 * Stops packages and returns stopped packages in JSON format. An empty JSON array is returned if none stopped.
	 *
	 * @return Stopped packages in JSON format.
	 */
	protected JSONArtifact run() {
		logger.logEntry(this, run);
		JSONArray packages;
		switch (actionType) {
			case stopAll:
				packages = transfers.archivedTransfersToJson(client.stopTransfers(userPackageIds));
				break;
			case stopAllFromAll:
				packages = transfers.archivedTransfersToJson(client.stopAllTransfers());
				break;
			default:
				packages = new JSONArray();
				break;
		}
		logger.logExit(this, run);

		return packages;
	}
}
