/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A helper class to retrieve and manage send transfers.
 */
public class Transfers {
	private final Object lock = new Object();
	private final List<Transfer> transfers = new ArrayList<>();
	private final List<TransferSummary> recentArchives = new ArrayList<>();

	Transfers() {
		// This constructor is intentionally empty to prevent from being instantiated from outside of the package.
	}

	/**
	 * Returns the transfer data in JSON format.
	 *
	 * @param packageId The Id of the package
	 * @return JSONObject The transfer data in JSON format
	 */
	public JSONObject transferToJson(final String packageId) {
		final Transfer transfer = getTransfer(packageId);

		return transfer == null ? null : transfer.toJson();
	}

	Transfer getTransfer(final String packageId) {
		if (packageId == null) {
			return null;
		}

		synchronized (lock) {
			return transfers.stream().filter(p -> packageId.equals(p.getSummary().getPackageId())).findFirst().orElse(null);
		}
	}

	/**
	 * Returns the summary of the recently sent package in JSON format.
	 *
	 * @param packageId The Id of the package
	 * @return The transfer summary in JSON format
	 */
	public JSONObject archivedTransferToJson(final String packageId) {
		final TransferSummary transfer = getArchivedTransfer(packageId);

		return transfer == null ? null : transfer.toJson();
	}

	TransferSummary getArchivedTransfer(final String packageId) {
		if (packageId == null) {
			return null;
		}

		synchronized (lock) {
			return recentArchives.stream().filter(p -> packageId.equals(p.getPackageId())).findFirst().orElse(null);
		}
	}

	/**
	 * Returns the summary of recently sent packages in JSON format.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The summary of packages in JSON format
	 */
	public JSONArray archivedTransfersToJson(final String... packageIds) {
		if (packageIds == null) {
			return new JSONArray();
		}

		synchronized (lock) {
			return recentArchives.stream().filter(p -> Arrays.stream(packageIds).anyMatch(id -> id.equals(p.getPackageId()))).map(TransferSummary::toJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
		}
	}

	/**
	 * Returns the summary of all recently sent packages in JSON format.
	 *
	 * @return The summary of packages in JSON format
	 */
	public JSONArray archivedTransfersToJson() {
		synchronized (lock) {
			return recentArchives.stream().map(TransferSummary::toJson).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
		}
	}

	/**
	 * Returns the summary of in-progress transfers in JSON format.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The summary of transfers in JSON format
	 */
	public JSONArray runningTransfersToJson(final String... packageIds) {
		if (packageIds == null) {
			return new JSONArray();
		}

		synchronized (lock) {
			return transfers.stream().filter(p -> Arrays.stream(packageIds).anyMatch(id -> id.equals(p.getSummary().getPackageId()))).map(p -> p.getSummary().toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
		}
	}

	/**
	 * Returns the summary of all in-progress transfers in JSON format.
	 *
	 * @return The summary of transfers in JSON format
	 */
	public JSONArray runningTransfersToJson() {
		synchronized (lock) {
			return transfers.stream().map(p -> p.getSummary().toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll);
		}
	}

	String[] getPackageIds() {
		return transfers.stream().map(p -> p.getSummary().getPackageId()).toArray(String[]::new);
	}

	/**
	 * Returns true if the transfer for the package is stopped.
	 *
	 * @param packageId The Id of the package
	 * @return true if the transfer is stopped
	 */
	public boolean isTransferStopped(final String packageId) {
		final TransferSummary transfer = getArchivedTransfer(packageId);

		return transfer != null && transfer.isStopped();
	}

	/**
	 * Filters the package Ids to return ones that are being sent or recently sent.
	 *
	 * @param packageIds The Ids of the packages
	 * @return The filtered package Ids
	 */
	public String[] getExistentPackageIds(final String... packageIds) {
		if (packageIds == null) {
			return new String[]{};
		}

		return Arrays.stream(packageIds).filter(id -> getTransfer(id) != null || getArchivedTransfer(id) != null).toArray(String[]::new);
	}

	void addTransfer(final Transfer transfer) {
		synchronized (lock) {
			transfers.add(transfer);
		}
	}

	void archiveTransfer(final Transfer transfer) {
		synchronized (lock) {
			transfers.remove(transfer);
			final TransferSummary summary = transfer.getSummary();
			if (getArchivedTransfer(summary.getPackageId()) == null) {
				recentArchives.add(summary);
			}
		}
	}

	List<Transfer> getHangingTransfers(final Instant progressTimeout) {
		synchronized (lock) {
			return transfers.stream().filter(p -> p.getSummary().isStarted() && p.getSummary().getUpdatedOn().isBefore(progressTimeout)).collect(Collectors.toList());
		}
	}

	void purgeExpiredArchives(final Instant archiveDuration) {
		synchronized (lock) {
			recentArchives.removeAll(recentArchives.stream().filter(p -> p.getUpdatedOn().isBefore(archiveDuration)).collect(Collectors.toList()));
		}
	}

	int getNumberOfRunningTransfers() {
		return transfers.size();
	}
}
