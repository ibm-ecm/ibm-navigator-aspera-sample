/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.configuration.DesktopConfig;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginServiceCallbacks;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The package sender used to prepare, validate, and send packages.
 */
class PackageSender {
	static final int DEFAULT_MAX_NUMBER_OF_ITEMS = 50;
	static final String CREDENTIALS = "ICN-AP-Credentials";
	static final String NONCE = "ICN-AP-Nonce";
	private static final String SENDER = "sender";
	private static final String PASSWORD = "password";
	private static final String sendPackage = "sendPackage";
	private static final String prepareItems = "prepareItems";
	private final DesktopConfig desktopConfig;
	private final JSONObject pluginConfig;
	private final FaspexClient client;
	private final PluginLogger logger;

	PackageSender(final DesktopConfig desktopConfig, final PluginServiceCallbacks callbacks) {
		try {
			pluginConfig = (JSONObject) JSON.parse(callbacks.loadConfiguration());
			client = FaspexClient.getInstance(callbacks);
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginRuntimeException("Failed to construct the package worker.", e);
		}
		logger = callbacks.getLogger();
		this.desktopConfig = desktopConfig;
	}

	PackageSender(final DesktopConfig desktopConfig, final JSONObject pluginConfig, final FaspexClient client, final PluginLogger logger) {
		this.desktopConfig = desktopConfig;
		this.pluginConfig = pluginConfig;
		this.client = client;
		this.logger = logger;
	}

	/**
	 * Validates and sends the package requested by the client. An error is returned if any of the parameters are invalid
	 * or the maximum number of concurrent requests configured in the plug-in configuration page has been reached.
	 *
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param response  The HttpServletResponse object provided by the ICN API
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @throws AsperaPluginException if an error occurs while preparing the package
	 */
	JSONObject sendPackage(final HttpServletRequest request, final HttpServletResponse response, final PluginServiceCallbacks callbacks) throws AsperaPluginException {
		logger.logEntry(this, sendPackage);
		final Map<String, Object> data = preparePackageData(request, response, Arrays.stream(desktopConfig.getRepositoriesId()).collect(Collectors.toList()));
		final Package pack = new Package(data, desktopConfig, callbacks);
		logger.logDebug(this, sendPackage, "package: " + pack);
		final String packageId = client.startTransfer(pack);
		logger.logDebug(this, sendPackage, "package id: " + packageId);
		JSONObject json = client.getTransfers().transferToJson(packageId);
		if (json == null) {
			logger.logDebug(this, sendPackage, "is the transfer completed already?");
			json = client.getTransfers().archivedTransferToJson(packageId);
		}
		if (json == null) {
			throw new AsperaPluginException("Failed to find the package: " + packageId);
		}
		refreshNonce(request, response, data);

		try {
			Util.addToUserRecentPackageIds(callbacks, (String) json.get("id"));
			logger.logDebug(this, sendPackage, "added the user recent package id");
		} catch (final AsperaPluginRuntimeException e) {
			logger.logError(this, sendPackage, "failed add the user recent package id", e);
		}
		logger.logExit(this, sendPackage);

		return json;
	}

	/**
	 * Prepares the package data.
	 *
	 * @param request        The HttpServletRequest object provided by the ICN API
	 * @param desktopRepoIds The Ids of repositories configured for the current desktop
	 * @return The prepared package data
	 * @throws AsperaPluginException if no items were prepared to send
	 */
	Map<String, Object> preparePackageData(final HttpServletRequest request, final HttpServletResponse response, final List<String> desktopRepoIds) throws AsperaPluginException {
		final Map<String, Object> data = getPackageData(request);
		validateSenderCredentials(request, response, data);

		final List<Map<String, String>> preparedItems = prepareItems((List<Map<String, String>>) data.get("items"), request.getParameter("repositoryId"), desktopRepoIds);
		if (preparedItems.isEmpty()) {
			throw new AsperaPluginException("No items were prepared to sendPackage.");
		}

		data.put("items", preparedItems);

		return data;
	}

	/**
	 * Returns the package data from the request.
	 *
	 * @param request The HttpServletRequest object provided by the ICN API
	 * @return The package data
	 * @throws AsperaPluginException if it fails to get the package data
	 */
	Map<String, Object> getPackageData(final HttpServletRequest request) throws AsperaPluginException {
		final JSONObject jsonPostAttr = (JSONObject) request.getAttribute("json_post");
		if (jsonPostAttr != null) {
			return new HashMap<>(jsonPostAttr);
		}

		final String jsonPostParam = request.getParameter("json_post");
		if (jsonPostParam == null) {
			throw new AsperaPluginException("Failed to get package data.");
		} else {
			try {
				return new HashMap<>((JSONObject) JSON.parse(jsonPostParam));
			} catch (final IOException | RuntimeException e) { // NOPMD - thrown from the super method
				throw new AsperaPluginException("Failed to parse package data.", e);
			}
		}
	}

	void validateSenderCredentials(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> packageData) throws AsperaPluginException {
		populateSenderCredentialsIfMissing(request, packageData);
		final String sender = (String) packageData.get(SENDER);
		final String password = (String) packageData.get(PASSWORD);
		if (sender == null || sender.isEmpty() || password == null || password.isEmpty()) {
			throw new AsperaPluginException("Sender or password is missing.");
		}
	}

	String refreshNonce(final HttpServletRequest request, final HttpServletResponse response, final Map<String, Object> packageData) {
		final byte[] nonce = Util.generateNonce();
		request.getSession(false).setAttribute(CREDENTIALS, new String[]{(String) packageData.get(SENDER), Encryption.encrypt((String) packageData.get(PASSWORD), nonce)});
		final String nonceString = Util.base64Encode(nonce);
		response.setHeader(NONCE, nonceString);

		return nonceString;
	}

	private void populateSenderCredentialsIfMissing(final HttpServletRequest request, final Map<String, Object> packageData) throws AsperaPluginException {
		final String sender = (String) packageData.get(SENDER);
		final String password = (String) packageData.get(PASSWORD);
		if (sender != null && !sender.isEmpty() || password != null && !password.isEmpty()) {
			return;
		}

		final HttpSession session = request.getSession(false);
		final String nonce = request.getHeader(NONCE);
		final String[] credentials = (String[]) session.getAttribute(CREDENTIALS);
		if (nonce == null || nonce.isEmpty() || credentials == null || credentials.length != 2) {
			throw new AsperaPluginException("Failed to validate sender credentials.");
		}
		packageData.put(SENDER, credentials[0]);
		packageData.put(PASSWORD, Encryption.decrypt(credentials[1], Util.base64Decode(nonce)));
	}

	/**
	 * Prepares items to send. Items from repositories that are not available for the current desktop are excluded.
	 * Up to the maximum number of items configured in the plug-in configuration page are sent.
	 *
	 * @param requestedItems The list of items to send
	 * @param repositoryId   The Id of the current repository
	 * @param desktopRepoIds The list of repository Ids available for the current desktop
	 * @return The prepared items
	 */
	List<Map<String, String>> prepareItems(final List<Map<String, String>> requestedItems, final String repositoryId, final List<String> desktopRepoIds) {
		if (requestedItems == null) {
			return new ArrayList<>();
		}

		return requestedItems.stream().filter(item -> {
			final String itemId = item.get("itemId");
			logger.logDebug(this, prepareItems, "itemId: " + itemId);

			return itemId != null && !itemId.isEmpty();
		}).filter(item -> {
			String repoId = item.get("repositoryId");
			repoId = repoId == null ? repositoryId : repoId;
			logger.logDebug(this, prepareItems, "repositoryId: " + repoId);
			if (!desktopRepoIds.contains(repoId)) {
				logger.logDebug(this, prepareItems, "skip items of a repository that doesn't belong to the desktop");

				return false;
			}

			item.put("repositoryId", repoId);

			return repoId != null && !repoId.isEmpty();
		}).limit(getMaxNumberOfItems()).collect(Collectors.toList());
	}

	/**
	 * Returns the maximum number of items that users can send at a time. The default maximum is 50.
	 *
	 * @return The maximum number of items to send
	 */
	int getMaxNumberOfItems() {
		final Object obj = pluginConfig.get("maxNumberOfItems");
		if (obj != null) {
			try {
				final int max = Integer.parseInt(obj.toString());
				logger.logDebug(this, "getMaxNumberOfItems", "max: " + max);

				return max;
			} catch (final NumberFormatException e) {
				logger.logWarning(this, "getMaxNumberOfItems", "Failed to get the max number of items: " + e);
			}
		}

		return DEFAULT_MAX_NUMBER_OF_ITEMS;
	}
}
