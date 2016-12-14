package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginServiceCallbacks;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public final class Util {
	private static final String CONFIG_RECENT_PACKAGE_IDS = "recentPackageIds";

	static String[] addToUserRecentPackageIds(PluginServiceCallbacks callbacks, String packageId) {
		String[] newIds = {};
		try {
			String ids = callbacks.loadUserConfiguration(CONFIG_RECENT_PACKAGE_IDS);
			ids = ids == null || ids.isEmpty() ? packageId : ids + "," + packageId;
			callbacks.saveUserConfiguration(CONFIG_RECENT_PACKAGE_IDS, ids);
			newIds = ids.split(",");
			callbacks.getLogger().logDebug(Util.class, "addToUserRecentPackageIds", "saved recent package ids: " + ids);
		} catch (Exception e) {
			callbacks.getLogger().logDebug(Util.class, "addToUserRecentPackageIds", "failed to add the package id: " + packageId);
			callbacks.getLogger().logError(Util.class, "addToUserRecentPackageIds", e);
		}

		return newIds;
	}

	static void saveUserRecentPackageIds(PluginServiceCallbacks callbacks, String[] packageIds) {
		try {
			String ids = String.join(",", packageIds);
			callbacks.saveUserConfiguration(CONFIG_RECENT_PACKAGE_IDS, ids);
			callbacks.getLogger().logDebug(Util.class, "saveUserRecentPackageIds", "saved recent package ids: " + ids);
		} catch (Exception e) {
			callbacks.getLogger().logDebug(Util.class, "saveUserRecentPackageIds", "failed to save recent package ids: " + Arrays.toString(packageIds));
			callbacks.getLogger().logError(Util.class, "saveUserRecentPackageIds", e);
		}
	}

	static String[] getUserRecentPackageIds(PluginServiceCallbacks callbacks) {
		try {
			String ids = callbacks.loadUserConfiguration(CONFIG_RECENT_PACKAGE_IDS);
			callbacks.getLogger().logDebug(Util.class, "getRecentPackageIds", "recent package ids: " + ids);

			return ids.split(",");
		} catch (Exception e) {
			callbacks.getLogger().logError(Util.class, "getRecentPackageIds", e);
		}

		return new String[]{};
	}

	static byte[] generateNonce() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);

		return iv;
	}

	static String base64Encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	static byte[] base64Decode(String data) {
		return Base64.getDecoder().decode(data);
	}
}
