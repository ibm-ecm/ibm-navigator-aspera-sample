/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.configuration.Config;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginResponseUtil;
import com.ibm.ecm.extension.PluginService;
import com.ibm.ecm.extension.PluginServiceCallbacks;
import com.ibm.ecm.extension.aspera.faspex.MaxRequestsReachedException;
import com.ibm.ecm.json.JSONMessage;
import com.ibm.ecm.json.JSONResponse;
import com.ibm.json.java.JSONObject;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * The service to send packages.
 */
public class SendService extends PluginService {
	private static final String ID = "send";
	private static final String POST = "POST";
	private static final String execute = "execute";

	/**
	 * Returns the Id of the service.
	 *
	 * @return The Id of the service
	 */
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
	 */
	@Override
	public void execute(final PluginServiceCallbacks callbacks, final HttpServletRequest request, final HttpServletResponse response) {
		final PluginLogger logger = callbacks.getLogger();
		logger.logEntry(this, execute);
		final JSONResponse responseJson = new JSONResponse();
		final MessageResources resources = callbacks.getPluginResources();
		final Locale locale = request.getLocale();

		try {
			validateRequest(request);
			final PackageSender sender = new PackageSender(Config.getDesktopConfig(Config.APPLICATION_NAME, request.getParameter("desktop")), callbacks);
			final JSONObject pkg = sender.sendPackage(request, response, callbacks);
			responseJson.put("pkg", pkg);
			responseJson.addInfoMessage(createMessage(resources, locale, "sendAction.started", pkg.get("title")));
		} catch (final MaxRequestsReachedException e) {
			logger.logDebug(this, execute, "max # of requests reached");
			responseJson.addErrorMessage(createMessage(resources, locale, "sendAction.maxError"));
		} catch (final Exception e) { // NOPMD - log and continue writing the JSON response
			logger.logError(this, execute, e);
			responseJson.addErrorMessage(createMessage(resources, locale, "sendAction.sendError"));
		}

		PluginResponseUtil.writeJSONResponse(request, response, responseJson, callbacks, ID);
		logger.logExit(this, execute);
	}

	/**
	 * Validate request parameters. The send service accepts POST requests only and requires sensitive data to be passed
	 * in as part of the request body.
	 *
	 * @param request The HttpServletRequest object provided by the ICN API
	 * @throws AsperaPluginException if the request is invalid
	 */
	void validateRequest(final HttpServletRequest request) throws AsperaPluginException {
		if (!POST.equalsIgnoreCase(request.getMethod())) {
			throw new AsperaPluginException("The HTTP request method must be POST.");
		}

		final String queryString = request.getQueryString();
		if (queryString != null && (queryString.contains("sender") || queryString.contains("password") || queryString.contains("earPassphrase"))) {
			throw new AsperaPluginException("User name and password cannot be in the query string.");
		}
	}

	private JSONMessage createMessage(final MessageResources resources, final Locale locale, final String prefix, final Object... args) {
		final String id = resources.getMessage(locale, prefix + ".id");
		final int messageId = id == null ? 0 : Integer.parseInt(id);
		final String message = resources.getMessage(locale, prefix, args);
		final String explanation = resources.getMessage(locale, prefix + ".explanation", args);
		final String userResponse = resources.getMessage(locale, prefix + ".userResponse", args);

		return new JSONMessage(messageId, message, explanation, userResponse, null, null);
	}
}
