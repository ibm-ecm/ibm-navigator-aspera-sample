package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.json.java.JSONObject;

/**
 * Thrown when an error occurs while starting or stopping a transfer. The package summary, if provided, will be
 * appended to the message.
 */
public class FaspexRuntimeException extends RuntimeException {
	private JSONObject pkg;

	FaspexRuntimeException(final String message, final JSONObject pkg, final Throwable cause) {
		super(message, cause);
		this.pkg = pkg;
	}

	FaspexRuntimeException(final String message, final JSONObject pkg) {
		super(message);
		this.pkg = pkg;
	}

	FaspexRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	FaspexRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Appends the package info, if set, to the message before returning.
	 *
	 * @return The message of the exception including the package info
	 */
	@Override
	public String getMessage() {
		return appendPackageInfo(super.getMessage());
	}

	private String appendPackageInfo(final String text) {
		final String base = text == null ? "" : text + " ";

		return base + (pkg == null ? "" : "(Package: " + pkg.toString() + ")");
	}
}
