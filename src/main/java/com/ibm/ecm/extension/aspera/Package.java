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
	final private String sender;
	final private String password;
	final private List<String> recipients;
	final private String title;
	final private String note;
	final private String earPassphrase;
	final private boolean notifyOnUpload;
	final private boolean notifyOnDownload;
	final private Map<String, Set<String>> items;
	private PluginLogger logger;
	private PluginServiceCallbacks pluginCallbacks;
	private DesktopConfig desktopConfig;
	private List<Content> contents;

	/**
	 * Constructs a package with the specified data.
	 *
	 * @param packageData     The package data
	 * @param desktopConfig   The desktop configuration object
	 * @param pluginCallbacks The PluginServiceCallbacks object provided by the ICN API
	 */
	public Package(final Map<String, Object> packageData, final DesktopConfig desktopConfig, final PluginServiceCallbacks pluginCallbacks) throws AsperaPluginException {
		this(packageData);
		this.logger = pluginCallbacks.getLogger();
		this.pluginCallbacks = pluginCallbacks;
		this.desktopConfig = desktopConfig;
	}

	/**
	 * Constructs a package with the specified data.
	 *
	 * @param packageData The package data
	 */
	public Package(final Map<String, Object> packageData) throws AsperaPluginException {
		this.sender = (String) packageData.get("sender");
		this.password = (String) packageData.get("password");
		this.recipients = Stream.of(((String) packageData.get("recipients")).split(",")).map(String::trim).collect(Collectors.toList());
		this.title = (String) packageData.get("title");
		this.note = (String) packageData.get("note");
		this.earPassphrase = (String) packageData.get("earPassphrase");
		final List<Map<String, String>> itemsParam = (List<Map<String, String>>) packageData.get("items");
		this.items = itemsParam.stream().collect(Collectors.groupingBy(item -> item.get("repositoryId"), Collectors.mapping(item -> item.get("itemId"), Collectors.toSet())));
		final Object notifyOnUploadParam = packageData.get("notifyOnUpload");
		this.notifyOnUpload = notifyOnUploadParam == null || Boolean.parseBoolean(notifyOnUploadParam.toString());
		final Object notifyOnDownloadParam = packageData.get("notifyOnDownload");
		this.notifyOnDownload = notifyOnUploadParam == null || Boolean.parseBoolean(notifyOnDownloadParam.toString());

		if (sender == null || password == null) {
			throw new AsperaPluginException("The sender must be specified.");
		}
		if (recipients == null || recipients.isEmpty()) {
			throw new AsperaPluginException("At least one recipient must be specified.");
		}
		if (this.items == null || this.items.isEmpty()) {
			throw new AsperaPluginException("At least one item must be specified.");
		}
	}

	/**
	 * Retrieves contents per repository.
	 *
	 * @return The retrieved contents
	 * @throws AsperaPluginRuntimeException if an error occurs while retrieving contents
	 */
	public List<Content> retrieveContents() throws AsperaPluginRuntimeException {
		if (contents != null) {
			return contents;
		}

		contents = items.entrySet().stream().map(entry -> {
			final String repoId = entry.getKey();
			final String repoType = desktopConfig.getRepository(repoId).getType();
			final Set<String> itemIds = entry.getValue();

			return retrieveRepositoryContents(itemIds, repoId, repoType);
		}).collect(ArrayList::new, List::addAll, List::addAll);

		return contents;
	}

	private List<Content> retrieveRepositoryContents(final Set<String> itemIds, final String repoId, final String repoType) {
		if ("p8".equalsIgnoreCase(repoType)) {
			final Subject subject = pluginCallbacks.getP8Subject(repoId);
			UserContext.get().pushSubject(subject);
		}

		logger.logDebug(this, "retrieveRepositoryContents", "itemIds: " + itemIds);
		try {
			return itemIds.stream().map(itemId -> {
				try {
					return Content.createContent(pluginCallbacks.retrieveDocumentContent(repoId, repoType, itemId, 0, null, null, null, 1, false));
				} catch (Exception e) { // NOPMD - thrown from the super method
					throw new AsperaPluginRuntimeException("Failed to retrieve content: " + itemId, e);
				}
			}).collect(Collectors.toList());
		} finally {
			if ("p8".equalsIgnoreCase(repoType)) {
				UserContext.get().popSubject();
			}
		}
	}

	/**
	 * Closes the retrieved contents.
	 */
	public void closeContents() {
		if (contents == null) {
			return;
		}

		logger.logDebug(this, "closeContents", "close contents");
		contents.forEach(content -> {
			try {
				content.close();
			} catch (final IOException e) {
				logger.logError(this, "closeContents", "failed to close the input stream of " + content.getFileName(), e);
			}
		});
	}

	/**
	 * Returns the sender of the package.
	 *
	 * @return The sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Returns the password of the sender.
	 *
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the recipients of the package.
	 *
	 * @return The recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}

	/**
	 * Returns the title of the package.
	 *
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the note of the package.
	 *
	 * @return The note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Returns the passphrase used to encrypt files being sent. (Not supported yet)
	 *
	 * @return The passphrase
	 */
	public String getEarPassphrase() {
		return earPassphrase;
	}

	/**
	 * Returns true if the sender is to be notified when the files are uploaded to the Faspex server.
	 *
	 * @return true if the send is to be notified
	 */
	public boolean isNotifyOnUpload() {
		return notifyOnUpload;
	}

	/**
	 * Returns true if the sender is to be notified when the files are downloaded by the recipients.
	 *
	 * @return true if the send is to be notified
	 */
	public boolean isNotifyOnDownload() {
		return notifyOnDownload;
	}

	/**
	 * Returns a string representation of the package.
	 *
	 * @return A String representation of the package
	 */
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
	public static class Content {
		private final PluginDocumentContent content;
		private InputStream inputStream;

		private Content(final PluginDocumentContent content) {
			this.content = content;
		}

		private static Content createContent(final PluginDocumentContent content) {
			return new Content(content);
		}

		/**
		 * Returns the input stream of the content.
		 *
		 * @return The input stream
		 * @throws AsperaPluginRuntimeException if an error occurs while getting the input stream
		 */
		public InputStream getInputStream() throws AsperaPluginRuntimeException {
			if (inputStream != null) {
				return inputStream;
			}

			try {
				inputStream = content.getInputStream();
			} catch (final Exception e) { // NOPMD - thrown from the super method
				throw new AsperaPluginRuntimeException("Failed to get the input stream of the content", e);
			}

			return inputStream;
		}

		/**
		 * Returns the name of the content file.
		 *
		 * @return The name of the content file
		 */
		public String getFileName() {
			return content.getFileName();
		}

		/**
		 * Returns the size of the content.
		 *
		 * @return The size of the content
		 */
		public long getSize() {
			return content.getContentSize();
		}

		/**
		 * Closes the input stream of the content.
		 *
		 * @throws IOException if an error occurs while closing the input stream
		 */
		void close() throws IOException {
			if (inputStream == null) {
				return;
			}

			inputStream.close();
		}
	}
}
