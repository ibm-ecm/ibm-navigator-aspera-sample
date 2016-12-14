/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.json.java.JSONArtifact;
import com.ibm.json.java.JSONObject;

/**
 * The action to get the package.
 */
class GetPackageAction extends PackageAction {
	private static final String run = "run";

	GetPackageAction(final FaspexClient client, final String packageId, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		super(client, PackageActionType.get, packageId, userPackageIds, adminUser, logger);
	}

	/**
	 * Returns true if the current user is an administrator or the package is from the current user.
	 *
	 * @return true if valid
	 */
	protected boolean isValid() {
		return adminUser || isMyPackage();
	}

	/**
	 * Returns the package in JSON format. An empty JSON is returned if the package couldn't be found.
	 *
	 * @return The package or an empty package in JSON format
	 */
	protected JSONArtifact run() {
		logger.logEntry(this, run);
		JSONArtifact json = transfers.transferToJson(packageId);
		if (json == null) {
			logger.logDebug(this, run, "check if the transfer is archived");
			json = transfers.archivedTransferToJson(packageId);
		}
		if (json == null) {
			json = new JSONObject();
		}
		logger.logExit(this, run);

		return json;
	}
}
