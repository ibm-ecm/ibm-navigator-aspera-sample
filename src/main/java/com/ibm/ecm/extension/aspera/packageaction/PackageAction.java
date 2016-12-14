/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.ecm.extension.aspera.faspex.Transfers;
import com.ibm.json.java.JSONArtifact;
import com.ibm.json.java.JSONObject;

import java.util.Arrays;

/**
 * The abstract package action.
 */
public abstract class PackageAction {
	private static final String handleAction = "handleAction";

	final FaspexClient client;
	final Transfers transfers;
	final PackageActionType actionType;
	final String packageId;
	final String[] userPackageIds;
	final boolean adminUser;
	final PluginLogger logger;

	/**
	 * Constructs the package action handler with the specified parameters.
	 *
	 * @param client         The Faspex client
	 * @param userPackageIds The Ids of the packages sent by the current user
	 * @param adminUser      true if the current user is an administrator
	 * @param logger         The plugin logger
	 */
	PackageAction(final FaspexClient client, final PackageActionType actionType, final String packageId, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		this.client = client;
		this.transfers = client.getTransfers();
		this.actionType = actionType;
		this.packageId = packageId;
		this.userPackageIds = userPackageIds;
		this.adminUser = adminUser;
		this.logger = logger;
	}

	/**
	 * Returns a package action based on the action type specified.
	 *
	 * @param client         The Faspex client
	 * @param actionType     The type of the package action
	 * @param packageId      The Id of the package
	 * @param userPackageIds The Ids of the packages of the current user
	 * @param adminUser      true if the current user is an administrator
	 * @param logger         The plugin logger
	 * @return A package action
	 */
	public static PackageAction getPackageAction(final FaspexClient client, final String actionType, final String packageId, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		return getPackageAction(client, PackageActionType.valueOf(actionType), packageId, userPackageIds, adminUser, logger);
	}

	/**
	 * Returns a package action based on the action type specified.
	 *
	 * @param client         The Faspex client
	 * @param actionType     The type of the package action
	 * @param packageId      The Id of the package
	 * @param userPackageIds The Ids of the packages of the current user
	 * @param adminUser      true if the current user is an administrator
	 * @param logger         The plugin logger
	 * @return A package action
	 */
	public static PackageAction getPackageAction(final FaspexClient client, final PackageActionType actionType, final String packageId, final String[] userPackageIds, final boolean adminUser, final PluginLogger logger) {
		PackageAction action;
		switch (actionType) {
			case get:
				action = new GetPackageAction(client, packageId, userPackageIds, adminUser, logger);
				break;
			case stop:
				action = new StopPackageAction(client, packageId, userPackageIds, adminUser, logger);
				break;
			case list:
			case listAll:
			case listRecent:
			case listAllRecent:
				action = new ListPackagesAction(client, actionType, userPackageIds, adminUser, logger);
				break;
			case stopAll:
			case stopAllFromAll:
				action = new StopPackagesAction(client, actionType, userPackageIds, adminUser, logger);
				break;
			default:
				throw new PackageActionRuntimeException("Unhandled action type.", actionType.toString());
		}

		return action;
	}

	/**
	 * Handles the package action.
	 *
	 * @return The transfer(s) in JSON format
	 */
	public JSONArtifact handleAction() throws PackageActionRuntimeException {
		logger.logEntry(this, handleAction);
		if (!isValid()) {
			return new JSONObject();
		}
		try {
			return run();
		} catch (final RuntimeException e) { // NOPMD - rethrow as PackageActionRuntimeException
			throw new PackageActionRuntimeException("An error occurred while running the package action.", actionType.toString(), e);
		} finally {
			logger.logExit(this, handleAction);
		}
	}

	boolean isMyPackage() {
		return packageId != null && !packageId.isEmpty() && Arrays.stream(userPackageIds).anyMatch(id -> id.equals(packageId));
	}

	/**
	 * Returns true if the action is valid.
	 *
	 * @return true if the action is valid
	 */
	protected abstract boolean isValid();

	/**
	 * Runs the action and returns the package(s).
	 *
	 * @return The package(s) in JSON format.
	 */
	protected abstract JSONArtifact run();

	/**
	 * The package action types
	 */
	public enum PackageActionType {
		get,
		stop,

		list,
		listAll,
		listRecent,
		listAllRecent,
		stopAll,
		stopAllFromAll;

		/**
		 * Returns true if the action type returns multiple packages.
		 *
		 * @param actionType The action type
		 * @return true if the action type returns multiple packages; false if it returns a single package
		 */
		public static boolean isMultiple(final String actionType) {
			try {
				final PackageActionType type = PackageActionType.valueOf(actionType);

				return Arrays.stream(new PackageActionType[]{list, listAll, listRecent, listAllRecent, stopAll, stopAllFromAll}).anyMatch(t -> t == type);
			} catch (final IllegalArgumentException e) {
				return false;
			}
		}

		/**
		 * Returns true if the action type returns multiple packages.
		 *
		 * @param actionType The action type
		 * @return true if the action type returns multiple packages; false if it returns a single package
		 */
		public static boolean isMultiple(final PackageActionType actionType) {
			return Arrays.stream(new PackageActionType[]{list, listAll, listRecent, listAllRecent, stopAll, stopAllFromAll}).anyMatch(t -> t == actionType);
		}

		/**
		 * Returns true if the action type exists.
		 *
		 * @param actionType The action type
		 * @return true if the action type exists
		 */
		public boolean equals(final String actionType) {
			try {
				return PackageActionType.valueOf(actionType) == this;
			} catch (final IllegalArgumentException e) {
				return false;
			}
		}
	}
}
