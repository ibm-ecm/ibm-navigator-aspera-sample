package com.ibm.ecm.extension.aspera;

/**
 * Thrown when a runtime exception occurs while transferring a package.
 */
public class AsperaPluginRuntimeException extends RuntimeException {
	AsperaPluginRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	AsperaPluginRuntimeException(final String message) {
		super(message);
	}

	AsperaPluginRuntimeException(final Throwable cause) {
		super(cause);
	}
}
