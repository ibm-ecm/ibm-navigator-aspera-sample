package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginServiceCallbacks;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * General utility methods.
 */
final class Util {
	private static final String RECENT_PACKAGE_IDS = "recentPackageIds";

	private Util() {
	}

	static void addToUserRecentPackageIds(final PluginServiceCallbacks callbacks, final String packageId) throws AsperaPluginRuntimeException {
		try {
			final String existingIds = callbacks.loadUserConfiguration(RECENT_PACKAGE_IDS);
			final String newIds = existingIds == null || existingIds.isEmpty() ? packageId : existingIds + "," + packageId;
			callbacks.saveUserConfiguration(RECENT_PACKAGE_IDS, newIds);
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginRuntimeException("Failed to add a user recent package Id: " + packageId, e);
		}
	}

	static void saveUserRecentPackageIds(final PluginServiceCallbacks callbacks, final String... packageIds) throws AsperaPluginRuntimeException {
		try {
			final String ids = String.join(",", packageIds);
			callbacks.saveUserConfiguration(RECENT_PACKAGE_IDS, ids);
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginRuntimeException("Failed to save user recent package Ids: " + Arrays.toString(packageIds), e);
		}
	}

	static String[] getUserRecentPackageIds(final PluginServiceCallbacks callbacks) throws AsperaPluginRuntimeException {
		try {
			final String existingIds = callbacks.loadUserConfiguration(RECENT_PACKAGE_IDS);

			return existingIds.split(",");
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginRuntimeException("Failed to get user recent package Ids.", e);
		}
	}

	static byte[] generateNonce() {
		final SecureRandom random = new SecureRandom();
		final byte[] iv = new byte[16];
		random.nextBytes(iv);

		return iv;
	}

	static String base64Encode(final byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	static byte[] base64Decode(final String data) {
		return Base64.getDecoder().decode(data);
	}

	static String getString(final String key, final Locale locale) {
		return getResourceBundle(locale).getString(key);
	}

	private static ResourceBundle getResourceBundle(final Locale locale) {
		return ResourceBundle.getBundle("com.ibm.ecm.extension.aspera.nls.ServicesMessages", locale);
	}
}
