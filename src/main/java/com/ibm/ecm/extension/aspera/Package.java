/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.filenet.api.util.UserContext;
import com.ibm.ecm.configuration.DesktopConfig;
import com.ibm.ecm.extension.PluginDocumentContent;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.PluginServiceCallbacks;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Package data used to send files.
 */
public class Package {
	private PluginLogger logger;
	private PluginServiceCallbacks pluginCallbacks;
	private DesktopConfig desktopConfig;

	private String sender;
	private String password;
	private List<String> recipients;
	private String title;
	private String note;
	private String earPassword;
	private boolean notifyOnUpload;
	private boolean notifyOnDownload;
	private Map<String, Set<String>> items;
	private List<Content> contents;

	/**
	 * Constructs a package with the specified data.
	 *
	 * @param packageData     The package data
	 * @param desktopConfig   The desktop configuration object
	 * @param pluginCallbacks The PluginServiceCallbacks object provided by the ICN API
	 * @throws RuntimeException if an error occurs while preparing the package;
	 *                          IllegalArgumentException if the sender or the recipient is not specified, or there are no items to send
	 */
	public Package(Map<String, Object> packageData, DesktopConfig desktopConfig, PluginServiceCallbacks pluginCallbacks) {
		this(packageData);
		this.logger = pluginCallbacks.getLogger();
		this.pluginCallbacks = pluginCallbacks;
		this.desktopConfig = desktopConfig;
	}

	public Package(Map<String, Object> packageData) {
		try {
			this.sender = (String) packageData.get("sender");
			this.password = (String) packageData.get("password");
			this.recipients = Stream.of(((String) packageData.get("recipients")).split(",")).map(String::trim).collect(Collectors.toList());
			this.title = (String) packageData.get("title");
			this.note = (String) packageData.get("note");
			this.earPassword = (String) packageData.get("earPassword");
			List<Map<String, String>> itemsParam = (List<Map<String, String>>) packageData.get("items");
			this.items = itemsParam.stream().collect(Collectors.groupingBy(item -> item.get("repositoryId"), Collectors.mapping(item -> item.get("itemId"), Collectors.toSet())));
			Object notifyOnUploadParam = packageData.get("notifyOnUpload");
			this.notifyOnUpload = notifyOnUploadParam == null || Boolean.parseBoolean(notifyOnUploadParam.toString());
			Object notifyOnDownloadParam = packageData.get("notifyOnDownload");
			this.notifyOnDownload = notifyOnUploadParam == null || Boolean.parseBoolean(notifyOnDownloadParam.toString());
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve the package data.", e);
		}

		if (sender == null || password == null)
			throw new IllegalArgumentException("The sender must be specified.");
		if (recipients == null || recipients.size() < 1)
			throw new IllegalArgumentException("At least one recipient must be specified.");
		if (this.items == null || this.items.size() < 1)
			throw new IllegalArgumentException("At least one item must be specified.");
	}

	/**
	 * Retrieves contents per repository.
	 *
	 * @return The retrieved contents
	 */
	public List<Content> retrieveContents() {
		if (contents != null)
			return contents;

		contents = items.entrySet().stream().map(entry -> {
			String repoId = entry.getKey();
			String repoType = desktopConfig.getRepository(repoId).getType();
			Set<String> itemIds = entry.getValue();

			return retrieveRepositoryContents(itemIds, repoId, repoType);
		}).collect(ArrayList::new, List::addAll, List::addAll);

		return contents;
	}

	private List<Content> retrieveRepositoryContents(Set<String> itemIds, String repoId, String repoType) {
		if (repoType.equalsIgnoreCase("p8")) {
			Subject subject = pluginCallbacks.getP8Subject(repoId);
			UserContext.get().pushSubject(subject);
		}

		logger.logDebug(this, "retrieveRepositoryContents", "itemIds: " + itemIds);
		List<Content> contents;
		try {
			contents = itemIds.stream().map(itemId -> {
				try {
					return new Content(pluginCallbacks.retrieveDocumentContent(repoId, repoType, itemId, 0, null, null, null, 1, false));
				} catch (Exception e) {
					throw new RuntimeException("Failed to retrieve content: " + itemId, e);
				}
			}).collect(Collectors.toList());
		} finally {
			if (repoType.equalsIgnoreCase("p8"))
				UserContext.get().popSubject();
		}

		return contents;
	}

	/**
	 * Closes the retrieved contents.
	 */
	public void closeContents() {
		if (contents == null)
			return;

		logger.logDebug(this, "closeContents", "close contents");
		contents.forEach(content -> {
			try {
				content.close();
			} catch (IOException e) {
				logger.logDebug(this, "closeContents", "failed to close the input stream of " + content.getFileName());
				logger.logDebug(this, "closeContents", "error : " + e);
			}
		});
		contents = null;
	}

	public String getSender() {
		return sender;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public String getTitle() {
		return title;
	}

	public String getNote() {
		return note;
	}

	public String getEarPassword() {
		return earPassword;
	}

	public boolean isNotifyOnUpload() {
		return notifyOnUpload;
	}

	public boolean isNotifyOnDownload() {
		return notifyOnDownload;
	}

	public String toString() {
		return "Package(sender = [" + sender +
				"], recipients = [" + recipients +
				"], title = [" + title +
				"], note = [" + note +
				"], notifyOnUpload = [" + notifyOnUpload +
				"], notifyOnDownload = [" + notifyOnDownload +
				"], items = [" + items + "])";
	}

	/**
	 * A helper class that wraps the content and its input stream.
	 */
	public class Content {
		private PluginDocumentContent content;
		private InputStream inputStream;

		private Content(PluginDocumentContent content) {
			this.content = content;
		}

		/**
		 * Gets the input stream of the content.
		 *
		 * @return The input stream
		 * @throws Exception if an error occurs while getting the input stream
		 */
		public InputStream getInputStream() throws Exception {
			if (inputStream != null)
				return inputStream;

			inputStream = content.getInputStream();

			return inputStream;
		}

		public String getFileName() {
			return content.getFileName();
		}

		public long getSize() {
			return content.getContentSize();
		}

		/**
		 * Closes the input stream of the content.
		 *
		 * @throws IOException if an error occurs while closing the input stream
		 */
		private void close() throws IOException {
			if (inputStream == null)
				return;

			inputStream.close();
		}
	}
}
