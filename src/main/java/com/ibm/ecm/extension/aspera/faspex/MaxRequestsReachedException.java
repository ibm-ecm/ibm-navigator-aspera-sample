package com.ibm.ecm.extension.aspera.faspex;

/**
 * Thrown when the maximum number of concurrent requests have been reached.
 */
public class MaxRequestsReachedException extends RuntimeException {
	MaxRequestsReachedException(final String message) {
		super(message);
	}
}
