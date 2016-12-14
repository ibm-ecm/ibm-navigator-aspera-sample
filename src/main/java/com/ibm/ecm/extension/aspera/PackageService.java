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
import com.ibm.ecm.json.JSONResponse;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * The service to manage packages.
 */
public class PackageService extends PluginService {
	private static final String ID = "package";

	PackageService() {
	}

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
	 * @throws Exception if an error occurs while executing the service
	 */
	@Override
	public void execute(PluginServiceCallbacks callbacks, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PluginLogger logger = callbacks.getLogger();
		String loggingMethod = "execute";
		logger.logEntry(this, loggingMethod);

		JSONResponse responseJson = new JSONResponse();
		String packageAction = request.getParameter("packageAction");
		logger.logDebug(this, loggingMethod, "package action: " + packageAction);
		String packageId = request.getParameter("packageId");
		logger.logDebug(this, loggingMethod, "package id: " + packageId);

		try {
			FaspexClient client = FaspexClient.getInstance(callbacks);
			client.cleanUpProcesses();
			String[] myPackageIds = Util.getUserRecentPackageIds(callbacks);
			myPackageIds = client.getExistentPackageIds(myPackageIds);
			Util.saveUserRecentPackageIds(callbacks, myPackageIds);
			boolean myPackage = Arrays.stream(myPackageIds).anyMatch(id -> id.equals(packageId));
			boolean adminUser = callbacks.isApplicationAdminUser(request, null, null);
			switch (packageAction) {
				case "list":
					JSONArray packages = client.runningProcessesToJson(myPackageIds);
					logger.logDebug(this, loggingMethod, "packages: " + packages);
					responseJson.put("packages", packages);
					break;
				case "listAll":
					packages = adminUser ? client.runningProcessesToJson() : client.runningProcessesToJson(myPackageIds);
					logger.logDebug(this, loggingMethod, "packages: " + packages);
					responseJson.put("packages", packages);
					break;
				case "listRecent":
					packages = client.archivedProcessesToJson(myPackageIds);
					logger.logDebug(this, loggingMethod, "packages: " + packages);
					responseJson.put("packages", packages);
					break;
				case "listAllRecent":
					packages = adminUser ? client.archivedProcessesToJson() : client.archivedProcessesToJson(myPackageIds);
					logger.logDebug(this, loggingMethod, "packages: " + packages);
					responseJson.put("packages", packages);
					break;
				case "get":
					JSONObject json = adminUser ? client.processToJson(packageId) : myPackage ? client.processToJson(packageId) : null;
					logger.logDebug(this, loggingMethod, "package: " + json);
					responseJson.put("pkg", json);
					break;
				case "stop":
					boolean stopped = adminUser ? client.stopProcess(packageId) : myPackage && client.stopProcess(packageId);
					logger.logDebug(this, loggingMethod, "stopped: " + stopped);
					responseJson.put("stopped", stopped);
					break;
				case "stopAll":
					String[] stoppedIds = client.stopProcesses(myPackageIds);
					packages = client.archivedProcessesToJson(stoppedIds);
					logger.logDebug(this, loggingMethod, "stopped packages: " + packages);
					responseJson.put("stoppedPackages", packages);
					break;
				case "stopAllFromAll":
					stoppedIds = adminUser ? client.stopAllProcesses() : client.stopProcesses(myPackageIds);
					packages = client.archivedProcessesToJson(stoppedIds);
					logger.logDebug(this, loggingMethod, "stopped packages: " + packages);
					responseJson.put("stoppedPackages", packages);
					break;
				default:
			}
		} catch (Exception e) {
			logger.logError(this, loggingMethod, e);
		}

		PluginResponseUtil.writeJSONResponse(request, response, responseJson, callbacks, ID);
		logger.logExit(this, loggingMethod);
	}
}
