package com.ibm.ecm.extension.aspera.packageaction;

/**
 * Thrown when a runtime exception occurs while running a package action.
 */
public class PackageActionRuntimeException extends RuntimeException {
	final private String actionType;

	PackageActionRuntimeException(final String message, final String actionType, final Throwable cause) {
		super(message, cause);
		this.actionType = actionType;
	}

	PackageActionRuntimeException(final String message, final String actionType) {
		super(message);
		this.actionType = actionType;
	}

	/**
	 * Appends the package action type, if set, to the message before returning.
	 *
	 * @return The message of the exception including the package action type
	 */
	@Override
	public String getMessage() {
		return appendPackageActionType(super.getMessage());
	}

	private String appendPackageActionType(final String text) {
		final String base = text == null ? "" : text + " ";

		return base + (actionType == null ? "" : "(Action: " + actionType + ")");
	}
}
