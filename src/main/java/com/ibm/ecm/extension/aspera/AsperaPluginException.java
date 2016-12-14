package com.ibm.ecm.extension.aspera;

/**
 * Thrown when a system error occurs or an invalid send request is received.
 */
public class AsperaPluginException extends Exception {
	AsperaPluginException(final String message, final Throwable cause) {
		super(message, cause);
	}

	AsperaPluginException(final String message) {
		super(message);
	}

	AsperaPluginException(final Throwable cause) {
		super(cause);
	}
}
