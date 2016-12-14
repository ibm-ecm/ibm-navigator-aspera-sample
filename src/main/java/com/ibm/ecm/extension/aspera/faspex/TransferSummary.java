/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSONObject;

import java.time.Instant;
import java.util.UUID;

/**
 * A transfer summary
 */
class TransferSummary {
	private final String id;
	private String packageId;
	private String title;
	private String sender;
	private Status status;
	private Instant updatedOn;
	private long size;
	private String lastEvent;

	/**
	 * Constructs a transfer summary for the specified package.
	 *
	 * @param pkg The package
	 */
	TransferSummary(final Package pkg) {
		if (pkg != null) {
			this.title = pkg.getTitle();
			this.sender = pkg.getSender();
		}
		this.id = UUID.randomUUID().toString();
		this.status = Status.PENDING;
		this.updatedOn = Instant.now();
	}

	/**
	 * Returns true if the transfer has not started.
	 *
	 * @return true if the transfer has not started
	 */
	boolean isPending() {
		return status == Status.PENDING;
	}

	/**
	 * Returns true if the transfer is started.
	 *
	 * @return true if the transfer is started
	 */
	boolean isStarted() {
		return status == Status.STARTED;
	}

	void setStarted() {
		updateStatus(Status.STARTED);
	}

	/**
	 * Returns true if the transfer is failed.
	 *
	 * @return true if the transfer is failed
	 */
	boolean isFailed() {
		return status == Status.FAILED;
	}

	void setFailed() {
		updateStatus(Status.FAILED);
	}

	/**
	 * Returns true if the transfer is stopped.
	 *
	 * @return true if the transfer is stopped
	 */
	boolean isStopped() {
		return status == Status.STOPPED;
	}

	void setStopped() {
		updateStatus(Status.STOPPED);
	}

	/**
	 * Returns true if the transfer is completed.
	 *
	 * @return true if the transfer is completed
	 */
	boolean isCompleted() {
		return status == Status.COMPLETED;
	}

	void setCompleted() {
		updateStatus(Status.COMPLETED);
	}

	private void updateStatus(final Status status) {
		this.status = status;
		setUpdatedOn(Instant.now());
	}

	/**
	 * Returns the Id of the package. The Id is set once the transfer is started.
	 *
	 * @return The Id of the package
	 */
	String getPackageId() {
		return packageId;
	}

	void setPackageId(final String packageId) {
		this.packageId = packageId;
	}

	/**
	 * Returns the Id of this transfer.
	 *
	 * @return The Id of this transfer
	 */
	String getId() {
		return id;
	}

	/**
	 * Returns the time when the status was last updated.
	 *
	 * @return The last updated time
	 */
	Instant getUpdatedOn() {
		return updatedOn;
	}

	void setUpdatedOn(final Instant updatedOn) {
		this.updatedOn = updatedOn;
	}

	void setSize(final long size) {
		this.size = size;
	}

	void setLastEvent(final String lastEvent) {
		this.lastEvent = lastEvent;
	}

	/**
	 * Returns the summary in JSON format.
	 *
	 * @return The summary in JSON format
	 */
	JSONObject toJson() {
		final JSONObject json = new JSONObject();
		json.put("_transferId", id);
		json.put("id", packageId);
		json.put("title", title);
		json.put("sender", sender);
		json.put("size", size);
		json.put("status", status.toString());
		json.put("updatedOn", updatedOn.toString());
		json.put("lastEvent", lastEvent);

		return json;
	}

	/**
	 * The package statuses.
	 */
	enum Status {
		PENDING,
		STARTED,
		STOPPED,
		FAILED,
		COMPLETED
	}
}
