/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.configuration.Config;
import com.ibm.ecm.configuration.DesktopConfig;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginResponseUtil;
import com.ibm.ecm.extension.PluginService;
import com.ibm.ecm.extension.PluginServiceCallbacks;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.ecm.json.JSONMessage;
import com.ibm.ecm.json.JSONResponse;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The service to send packages.
 */
public class SendService extends PluginService {
	private static final String ID = "send";

	static final int DEFAULT_MAX_NUMBER_OF_ITEMS = 50;
	static final String CREDENTIALS = "ICN-AP-Credentials";
	static final String NONCE = "ICN-AP-Nonce";

	private PluginLogger logger;
	private JSONObject pluginConfig;
	private DesktopConfig desktopConfig;
	private FaspexClient client;

	SendService(JSONObject pluginConfig, PluginLogger logger) {
		this.pluginConfig = pluginConfig;
		this.logger = logger;
	}

	SendService() {
	}

	@Override
	public String getId() {
		return ID;
	}

	/**
	 * Validates and sends the package requested by the client. An error is returned if any of the parameters are invalid
	 * or the maximum number of concurrent requests configured in the plug-in configuration page has been reached.
	 *
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param response  The HttpServletResponse object provided by the ICN API
	 * @throws Exception if an error occurs preparing or sending the package
	 */
	@Override
	public void execute(PluginServiceCallbacks callbacks, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (logger == null)
			logger = callbacks.getLogger();
		String loggingMethod = "execute";
		logger.logEntry(this, loggingMethod);

		JSONResponse responseJson = new JSONResponse();
		MessageResources resources = callbacks.getPluginResources();
		Locale locale = request.getLocale();
		try {
			initialize(callbacks, request);
			if (client.isMaxNumberOfRequestsReached()) {
				logger.logDebug(this, loggingMethod, "max # of requests reached");
				responseJson.addErrorMessage(createMessage(resources, locale, "sendAction.maxError"));
			} else {
				validateRequest(request);
				Map<String, Object> data = preparePackageData(request, Arrays.stream(desktopConfig.getRepositoriesId()).collect(Collectors.toList()));
				String nonce = validateSenderCredentials(request, data);
				Package pack = new Package(data, desktopConfig, callbacks);
				logger.logDebug(this, loggingMethod, "package: " + pack);
				String pid = client.startProcess(pack);
				logger.logDebug(this, loggingMethod, "package id: " + pid);
				responseJson.put("pkg", client.processToJson(pid));
				response.setHeader(NONCE, nonce);
				String[] ids = Util.addToUserRecentPackageIds(callbacks, pid);
				Util.saveUserRecentPackageIds(callbacks, client.getExistentPackageIds(ids));
				responseJson.addInfoMessage(createMessage(resources, locale, "sendAction.started", pack.getTitle()));
			}
		} catch (Exception e) {
			responseJson.addErrorMessage(createMessage(resources, locale, "sendAction.sendError"));
			logger.logError(this, loggingMethod, e);
		}

		PluginResponseUtil.writeJSONResponse(request, response, responseJson, callbacks, ID);
		logger.logExit(this, loggingMethod);
	}

	private void initialize(PluginServiceCallbacks callbacks, HttpServletRequest request) throws Exception {
		if (pluginConfig == null)
			pluginConfig = (JSONObject) JSON.parse(callbacks.loadConfiguration());
		desktopConfig = Config.getDesktopConfig(Config.APPLICATION_NAME, request.getParameter("desktop"));
		client = FaspexClient.getInstance(callbacks);
		client.cleanUpProcesses();
	}

	/**
	 * Validate request parameters. The send service accepts POST requests only and requires sensitive data to be passed
	 * in as part of the request body.
	 *
	 * @param request The HttpServletRequest object provided by the ICN API
	 */
	void validateRequest(HttpServletRequest request) {
		if (!"POST".equalsIgnoreCase(request.getMethod()))
			throw new RuntimeException("The HTTP request method must be POST.");

		String queryString = request.getQueryString();
		if (queryString != null && (queryString.contains("sender") || queryString.contains("password") || queryString.contains("earPassword")))
			throw new RuntimeException("User name and password cannot be in the query string.");
	}

	/**
	 * Prepares the package data.
	 *
	 * @param request        The HttpServletRequest object provided by the ICN API
	 * @param desktopRepoIds The Ids of repositories configured for the current desktop
	 * @return The prepared package data
	 * @throws RuntimeException if no items were prepared to send
	 */
	Map<String, Object> preparePackageData(HttpServletRequest request, List<String> desktopRepoIds) throws Exception {
		Map<String, Object> data = getPackageData(request);
		List<Map<String, String>> preparedItems = prepareItems((List<Map<String, String>>) data.get("items"), request.getParameter("repositoryId"), desktopRepoIds);
		if (preparedItems.size() < 1)
			throw new RuntimeException("No items were prepared to send.");

		data.put("items", preparedItems);

		return data;
	}

	/**
	 * Gets the package data from the request.
	 *
	 * @param request The HttpServletRequest object provided by the ICN API
	 * @return The package data
	 */
	Map<String, Object> getPackageData(HttpServletRequest request) {
		JSONObject jsonPost = (JSONObject) request.getAttribute("json_post");
		if (jsonPost == null) {
			String json = request.getParameter("json_post");
			if (json != null) {
				try {
					jsonPost = (JSONObject) JSON.parse(json);
				} catch (Exception e) {
					throw new RuntimeException("Failed to parse package data.", e);
				}
			} else {
				throw new RuntimeException("Failed to get package data.");
			}
		}

		return new HashMap<>(jsonPost);
	}

	String validateSenderCredentials(HttpServletRequest request, Map<String, Object> packageData) throws Exception {
		HttpSession session = request.getSession(false);
		String sender = (String) packageData.get("sender");
		String password = (String) packageData.get("password");
		boolean senderSet = sender != null && !sender.isEmpty();
		boolean passwordSet = password != null && !password.isEmpty();
		if (!senderSet && !passwordSet) {
			String nonce = request.getHeader(NONCE);
			String[] credentials = (String[]) session.getAttribute(CREDENTIALS);
			if (nonce == null || nonce.isEmpty() || credentials == null || credentials.length != 2)
				throw new RuntimeException("Unable to validate sender credentials");
			else
				password = Encryption.decrypt(credentials[1], Util.base64Decode(nonce));
			packageData.put("password", password);
			sender = sender != null && !sender.isEmpty() ? sender : credentials[0];
			packageData.put("sender", sender);
		} else if (!senderSet || !passwordSet) {
			throw new RuntimeException("Sender credentials not set");
		}

		byte[] nonce = Util.generateNonce();
		session.setAttribute(CREDENTIALS, new String[] { sender, Encryption.encrypt(password, nonce)});

		return Util.base64Encode(nonce);
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
	List<Map<String, String>> prepareItems(List<Map<String, String>> requestedItems, String repositoryId, List<String> desktopRepoIds) {
		List<Map<String, String>> sendItems = new ArrayList<>();
		if (requestedItems == null)
			return sendItems;

		String loggingMethod = "prepareItems";
		sendItems = requestedItems.stream().filter(item -> {
			String itemId = item.get("itemId");
			logger.logDebug(this, loggingMethod, "itemId: " + itemId);

			return itemId != null && !itemId.isEmpty();
		}).filter(item -> {
			String repoId = item.get("repositoryId");
			repoId = repoId == null ? repositoryId : repoId;
			logger.logDebug(this, loggingMethod, "repositoryId: " + repoId);
			if (!desktopRepoIds.contains(repoId)) {
				logger.logDebug(this, loggingMethod, "skip items of a foreign repository");

				return false;
			}

			item.put("repositoryId", repoId);

			return repoId != null && !repoId.isEmpty();
		}).limit(getMaxNumberOfItems()).collect(Collectors.toList());

		return sendItems;
	}

	/**
	 * Gets the maximum number of items that users can send at a time. The default maximum is 50.
	 *
	 * @return The maximum number of items to send
	 */
	int getMaxNumberOfItems() {
		int max = DEFAULT_MAX_NUMBER_OF_ITEMS;
		try {
			Object obj = pluginConfig.get("maxNumberOfItems");
			if (obj != null) {
				max = Integer.parseInt(obj.toString());
				logger.logDebug(this, "getMaxNumberOfItems", "max: " + max);
			}
		} catch (Exception e) {
			logger.logWarning(this, "getMaxNumberOfItems", "Failed to get the max number of items: " + e);
		}

		return max;
	}

	private JSONMessage createMessage(MessageResources resources, Locale locale, String prefix, Object... args) {
		String id = resources.getMessage(locale, prefix + ".id");
		int messageId = id == null ? 0 : Integer.parseInt(id);
		String message = resources.getMessage(locale, prefix, args);
		String explanation = resources.getMessage(locale, prefix + ".explanation", args);
		String userResponse = resources.getMessage(locale, prefix + ".userResponse", args);

		return new JSONMessage(messageId, message, explanation, userResponse, null, null);
	}
}
