/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginResponseUtil;
import com.ibm.ecm.extension.PluginService;
import com.ibm.ecm.extension.PluginServiceCallbacks;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.ecm.extension.aspera.packageaction.PackageAction;
import com.ibm.ecm.json.JSONResponse;
import com.ibm.json.java.JSONArtifact;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The service to manage packages.
 */
public class PackageActionService extends PluginService {
	private static final String ID = "package";
	private static final String execute = "execute";

	PackageActionService() {
		super();
	}

	/**
	 * Returns the Id of the service.
	 *
	 * @return The Id
	 */
	@Override
	public String getId() {
		return ID;
	}

	/**
	 * Provides services to list and get details of packages, and stop packages being sent.
	 *
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param response  The HttpServletResponse object provided by the ICN API
	 */
	@Override
	public void execute(final PluginServiceCallbacks callbacks, final HttpServletRequest request, final HttpServletResponse response) {
		final PluginLogger logger = callbacks.getLogger();
		logger.logEntry(this, execute);

		final JSONResponse responseJson = new JSONResponse();
		final String actionType = request.getParameter("actionType");
		logger.logDebug(this, execute, "action type: " + actionType);
		final String packageId = request.getParameter("packageId");
		logger.logDebug(this, execute, "package id: " + packageId);

		try {
			final FaspexClient client = FaspexClient.getInstance(callbacks);
			final String[] myPackageIds = client.getTransfers().getExistentPackageIds(Util.getUserRecentPackageIds(callbacks));
			Util.saveUserRecentPackageIds(callbacks, myPackageIds);
			final boolean adminUser = callbacks.isApplicationAdminUser(request, null, null);
			final PackageAction action = PackageAction.getPackageAction(client, actionType, packageId, myPackageIds, adminUser, logger);
			final JSONArtifact json = action.handleAction();

			logger.logDebug(this, execute, "json: " + json);
			if (PackageAction.PackageActionType.isMultiple(actionType)) {
				responseJson.put("packages", json);
			} else {
				responseJson.put("pkg", json);
			}
			if (PackageAction.PackageActionType.stop.equals(actionType)) {
				responseJson.put("stopped", client.getTransfers().isTransferStopped(packageId));
			}
		} catch (final RuntimeException e) { // NOPMD - log and continue writing the JSON response
			logger.logError(this, execute, e);
		}

		PluginResponseUtil.writeJSONResponse(request, response, responseJson, callbacks, ID);
		logger.logExit(this, execute);
	}
}
