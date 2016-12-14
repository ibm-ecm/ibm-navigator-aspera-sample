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
 * The action to list packages.
 */
class ListPackagesAction extends PackageAction {
	private static final String run = "run";

	ListPackagesAction(final FaspexClient client, final PackageActionType actionType, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		super(client, actionType, null, userPackageIds, adminUser, logger);
	}

	/**
	 * Returns true if the current user is an administrator or the action is returning the packages of the current user.
	 *
	 * @return true if valid
	 */
	protected boolean isValid() {
		return adminUser || actionType != PackageActionType.listAll && actionType != PackageActionType.listAllRecent;
	}

	/**
	 * Returns a list of packages in JSON format.
	 *
	 * @return A list of packages in JSON format
	 */
	protected JSONArtifact run() {
		logger.logEntry(this, run);
		JSONArray packages;
		switch (actionType) {
			case list:
				packages = transfers.runningTransfersToJson(userPackageIds);
				break;
			case listRecent:
				packages = transfers.archivedTransfersToJson(userPackageIds);
				break;
			case listAll:
				packages = transfers.runningTransfersToJson();
				break;
			case listAllRecent:
				packages = transfers.archivedTransfersToJson();
				break;
			default:
				packages = new JSONArray();
				break;
		}
		logger.logExit(this, run);

		return packages;
	}
}
