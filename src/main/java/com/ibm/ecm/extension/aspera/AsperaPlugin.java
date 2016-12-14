/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.*;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import org.apache.commons.lang.SystemUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

/**
 * The main class for the Aspera faspex plug-in. Files required to send files to a faspex server are copied when
 * the plug-in starts and deleted when the plug-in terminates.
 */
public class AsperaPlugin extends Plugin {
	private static final String ID = "aspera";
	private static final String MESSAGE_PROPERTIES = "com.ibm.ecm.extension.aspera.nls.ServicesMessages";
	private static final String applicationInit = "applicationInit";
	private static final String applicationTerminate = "applicationTerminate";
	private static final String BIN = "bin";
	private static final String BIN_LINUX = "bin/linux-64";
	private static final String BIN_OSX = "bin/osx-64";
	private static final String BIN_WINDOWS = "bin/windows-64";
	private static final String ETC = "etc";

	private String resourcesRoot;
	private List<Path> resourcePaths = new ArrayList<>();
	private PluginLogger logger;

	/**
	 * Returns an array of actions provided by the plug-in.
	 *
	 * @return An array of actions
	 */
	@Override
	public PluginAction[] getActions() {
		return new PluginAction[]{new SendAction()};
	}

	/**
	 * Returns the Id of the plug-in.
	 *
	 * @return The Id
	 */
	@Override
	public String getId() {
		return ID;
	}

	/**
	 * Returns a localized name of the plug-in.
	 *
	 * @param locale The locale for which a resource bundle is desired
	 * @return The localized name
	 */
	@Override
	public String getName(final Locale locale) {
		return Util.getString("plugin.name", locale);
	}

	/**
	 * Returns the name of the compressed and gzipped JavaScript file for the plug-in.
	 *
	 * @return The name of the JavaScript file
	 */
	@Override
	public String getScript() {
		return "AsperaPlugin.js.jgz";
	}

	/**
	 * Returns the name of the main, uncompressed, JavaScript file of the plug-in used in debug mode (debug=true).
	 *
	 * @return The name of the JavaScript file
	 */
	@Override
	public String getDebugScript() {
		return "AsperaPlugin.js";
	}

	/**
	 * Returns an array of services provided by the plug-in.
	 *
	 * @return An array of services
	 */
	@Override
	public PluginService[] getServices() {
		return new PluginService[]{new SendService(), new PackageActionService()};
	}

	/**
	 * Returns the version number of the plug-in.
	 *
	 * @return The version number
	 */
	@Override
	public String getVersion() {
		return "3.0.2-sample-1.0";
	}

	/**
	 * Returns the name of the Dojo module that is contained in the resources for the plug-in.
	 *
	 * @return The name of the the Dojo module
	 */
	@Override
	public String getDojoModule() {
		return "aspera";
	}

	/**
	 * Returns the name of the compressed and gzipped CSS file for the plug-in.
	 *
	 * @return The name of the CSS file
	 */
	@Override
	public String getCSSFileName() {
		return "AsperaPlugin.css.jgz";
	}

	/**
	 * Returns the name of the uncompressed CSS file for the plug-in used in debug mode (debug=true).
	 *
	 * @return The name of the CSS file
	 */
	@Override
	public String getDebugCSSFileName() {
		return "AsperaPlugin.css";
	}

	/**
	 * Returns the name of the Dojo <code>dijit</code> class that provides a configuration interface widget for the plug-in.
	 *
	 * @return The name of the Dojo <code>dijit</code> class
	 */
	@Override
	public String getConfigurationDijitClass() {
		return "aspera.ConfigurationPane";
	}

	/**
	 * Returns the base name of the plug-in message resources file.
	 *
	 * @return The base name of the resources file
	 */
	@Override
	public String getPluginMessageResourcesName() {
		return MESSAGE_PROPERTIES;
	}

	/**
	 * Copies files required to send files to a temporary directory and loads a secret key.
	 *
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @throws AsperaPluginException if an error occurs while initializing the plug-in
	 */
	@Override
	public void applicationInit(final HttpServletRequest request, final PluginServiceCallbacks callbacks) throws AsperaPluginException {
		logger = callbacks.getLogger();
		logger.logEntry(this, applicationInit, request);
		try {
			super.applicationInit(request, callbacks);
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginException("An error occurred while initializing the plugin.", e);
		}

		createResourcesFolderStructure();
		logger.logDebug(this, applicationInit, request, "created folder structure for resources: " + resourcesRoot);
		copyResources();
		Encryption.loadSecretKey();
		logger.logExit(this, applicationInit, request);
	}

	private void createResourcesFolderStructure() throws AsperaPluginException {
		final File resourcesDir = new File(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources");
		resourcesRoot = createResourceFolder(resourcesDir);
		createResourceFolder(new File(resourcesRoot, BIN));
		createResourceFolder(new File(resourcesRoot, ETC));
	}

	private String createResourceFolder(final File resourceFolder) throws AsperaPluginException {
		if (!resourceFolder.exists() && !resourceFolder.mkdir()) {
			throw new AsperaPluginException("Failed to create a plugin resource folder: " + resourceFolder);
		}

		try {
			return resourceFolder.getCanonicalPath();
		} catch (final IOException e) {
			throw new AsperaPluginException("Failed to get the canonical path of the resource folder: " + resourceFolder, e);
		}
	}

	private void copyResources() throws AsperaPluginException {
		resourcePaths.add(copyResource("", "asperaweb_id_dsa.openssh", ""));
		resourcePaths.add(copyResource(ETC, "aspera-license", ETC));
		resourcePaths.add(copyResource(ETC, "aspera.conf", ETC));
		if (SystemUtils.IS_OS_WINDOWS) {
			copyWindowsResources();
		} else if (SystemUtils.IS_OS_MAC_OSX) {
			resourcePaths.add(makeFileExecutable(copyResource(BIN_OSX, "ascp4", BIN)));
		} else if (SystemUtils.IS_OS_LINUX) {
			resourcePaths.add(makeFileExecutable(copyResource(BIN_LINUX, "ascp4", BIN)));
		} else {
			throw new AsperaPluginException("The operating system is not supported.");
		}
	}

	private void copyWindowsResources() throws AsperaPluginException {
		resourcePaths.add(copyResource(BIN_WINDOWS, "ascp4.exe", BIN));
		resourcePaths.add(copyResource(BIN_WINDOWS, "fasp3.dll", BIN));
		resourcePaths.add(copyResource(BIN_WINDOWS, "libeay32.dll", BIN));
		resourcePaths.add(copyResource(BIN_WINDOWS, "msvcp100.dll", BIN));
		resourcePaths.add(copyResource(BIN_WINDOWS, "msvcr100.dll", BIN));
		resourcePaths.add(copyResource(BIN_WINDOWS, "ssleay32.dll", BIN));
	}

	private Path makeFileExecutable(final Path file) throws AsperaPluginException {
		final Set<PosixFilePermission> perms = new HashSet<>();
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_EXECUTE);
		try {
			Files.setPosixFilePermissions(file, perms);
		} catch (final IOException e) {
			throw new AsperaPluginException("Failed to make the file executable: " + file, e);
		}

		return file;
	}

	private Path copyResource(final String subFolder, final String resourceName, final String toSubFolder) throws AsperaPluginException {
		final Path path = toSubFolder.isEmpty() ? Paths.get(resourcesRoot, resourceName) : Paths.get(resourcesRoot, toSubFolder, resourceName);
		final InputStream resource = this.getClass().getResourceAsStream("/aspera/" + subFolder + (subFolder.isEmpty() ? "" : "/") + resourceName);
		try {
			Files.copy(resource, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (final IOException e) {
			throw new AsperaPluginException("Failed to copy the plugin resource file: " + path, e);
		}

		return path;
	}

	/**
	 * Stops all in-progress transfers and deletes files that were copied to a temporary directory.
	 *
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 * @throws AsperaPluginRuntimeException if an error occurs while terminating the plug-in
	 */
	@Override
	public void applicationTerminate(final HttpServletRequest request, final PluginServiceCallbacks callbacks) throws AsperaPluginException {
		logger = callbacks.getLogger();
		logger.logEntry(this, applicationTerminate, request);
		FaspexClient.terminate();
		logger.logDebug(this, applicationTerminate, request, "terminated faspex client");
		deleteResources();
		logger.logDebug(this, applicationTerminate, request, "deleted resources");

		try {
			Encryption.loadSecretKey(); // reset the secret key
			super.applicationTerminate(request, callbacks);
		} catch (final Exception e) { // NOPMD - thrown from the super method
			throw new AsperaPluginException("An error occurred while terminating the plugin.", e);
		}
		logger.logExit(this, applicationTerminate, request);
	}

	private void deleteResources() {
		if (resourcePaths == null) {
			return;
		}

		resourcePaths.parallelStream().filter(path -> path.toFile().exists()).map(Path::toFile).forEach(file -> {
			final boolean deleted = file.delete();
			if (!deleted) {
				file.deleteOnExit();
			}
		});
		resourcePaths = new ArrayList<>();
	}

	/**
	 * Provides the copyright license for the plug-in. The information is displayed in the About dialog Plugins tab.
	 *
	 * @return The copyright license
	 */
	@Override
	public String getCopyright() {
		return "Licensed Materials - Property of IBM<br>(C) Copyright IBM Corp. 2010, 2017<br>US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	}
}
