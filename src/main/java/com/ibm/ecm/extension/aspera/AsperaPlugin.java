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

	private String resourcesRoot;
	private List<Path> resourcePaths;

	@Override
	public PluginAction[] getActions() {
		return new PluginAction[]{new SendAction()};
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.ibm.ecm.extension.aspera.nls.ServicesMessages", locale, this.getClass().getClassLoader());

		return bundle.getString("plugin.name");
	}

	@Override
	public String getScript() {
		return "AsperaPlugin.js.jgz";
	}

	@Override
	public String getDebugScript() {
		return "AsperaPlugin.js";
	}

	@Override
	public PluginService[] getServices() {
		return new PluginService[]{new SendService(), new PackageService()};
	}

	@Override
	public String getVersion() {
		return "3.0.2-sample-1.0";
	}

	@Override
	public String getDojoModule() {
		return "aspera";
	}

	@Override
	public String getCSSFileName() {
		return "AsperaPlugin.css.jgz";
	}

	@Override
	public String getDebugCSSFileName() {
		return "AsperaPlugin.css";
	}

	@Override
	public String getConfigurationDijitClass() {
		return "aspera.ConfigurationPane";
	}

	@Override
	public String getPluginMessageResourcesName() {
		return MESSAGE_PROPERTIES;
	}

	/**
	 * Copies files required to send files to a temporary directory and loads a secret key.
	 *
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 */
	@Override
	public void applicationInit(HttpServletRequest request, PluginServiceCallbacks callbacks) throws Exception {
		PluginLogger logger = callbacks.getLogger();
		String loggingMethod = "applicationInit";
		logger.logEntry(this, loggingMethod);
		super.applicationInit(request, callbacks);

		createResourcesFolderStructure();
		logger.logDebug(this, loggingMethod, "created folder structure for resources: " + resourcesRoot);
		copyResources();
		logger.logDebug(this, loggingMethod, "copied resources");
		logger.logExit(this, loggingMethod);
		Encryption.loadSecretKey();
	}

	private void createResourcesFolderStructure() throws IOException {
		File resourcesDir = new File(System.getProperty("java.io.tmpdir"), "aspera-plugin-resources");
		resourcesRoot = createResourceFolder(resourcesDir);
		createResourceFolder(new File(resourcesRoot, "bin"));
		createResourceFolder(new File(resourcesRoot, "etc"));
	}

	private String createResourceFolder(File resourceFolder) throws IOException {
		if (!resourceFolder.exists() && !resourceFolder.mkdir())
			throw new RuntimeException("Couldn't create a temporary directory to store Aspera resources.");

		return resourceFolder.getCanonicalPath();
	}

	private void copyResources() throws IOException {
		if (resourcePaths == null)
			resourcePaths = new ArrayList<>();

		resourcePaths.add(copyResource("", "asperaweb_id_dsa.openssh", ""));
		resourcePaths.add(copyResource("etc", "aspera-license", "etc"));
		resourcePaths.add(copyResource("etc", "aspera.conf", "etc"));
		if (SystemUtils.IS_OS_WINDOWS) {
			copyWindowsResources();
		} else if (SystemUtils.IS_OS_MAC_OSX) {
			copyOsxResources();
		} else if (SystemUtils.IS_OS_LINUX) {
			copyLinuxResources();
		} else {
			throw new RuntimeException("The operating system is not supported.");
		}
	}

	private void copyOsxResources() throws IOException {
		resourcePaths.add(makeFileExecutable(copyResource("bin/osx-64", "ascp4", "bin")));
	}

	private void copyLinuxResources() throws IOException {
		resourcePaths.add(makeFileExecutable(copyResource("bin/linux-64", "ascp4", "bin")));
	}

	private void copyWindowsResources() throws IOException {
		resourcePaths.add(copyResource("bin/windows-64", "ascp4.exe", "bin"));
		resourcePaths.add(copyResource("bin/windows-64", "fasp3.dll", "bin"));
		resourcePaths.add(copyResource("bin/windows-64", "libeay32.dll", "bin"));
		resourcePaths.add(copyResource("bin/windows-64", "msvcp100.dll", "bin"));
		resourcePaths.add(copyResource("bin/windows-64", "msvcr100.dll", "bin"));
		resourcePaths.add(copyResource("bin/windows-64", "ssleay32.dll", "bin"));
	}

	private Path makeFileExecutable(Path file) throws IOException {
		Set<PosixFilePermission> perms = new HashSet<>();
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_EXECUTE);
		Files.setPosixFilePermissions(file, perms);

		return file;
	}

	private Path copyResource(String subFolder, String resourceName, String toSubFolder) throws IOException {
		Path path = toSubFolder.isEmpty() ? Paths.get(resourcesRoot, resourceName) : Paths.get(resourcesRoot, toSubFolder, resourceName);
		InputStream resource = this.getClass().getResourceAsStream("/aspera/" + subFolder + (subFolder.isEmpty() ? "" : "/") + resourceName);
		Files.copy(resource, path, StandardCopyOption.REPLACE_EXISTING);

		return path;
	}

	/**
	 * Stops all in-progress processes and deletes files that were copied to a temporary directory.
	 *
	 * @param request   The HttpServletRequest object provided by the ICN API
	 * @param callbacks The PluginServiceCallbacks object provided by the ICN API
	 */
	@Override
	public void applicationTerminate(HttpServletRequest request, PluginServiceCallbacks callbacks) throws Exception {
		PluginLogger logger = callbacks.getLogger();
		String loggingMethod = "applicationTerminate";
		logger.logEntry(this, loggingMethod);
		FaspexClient.terminate();
		logger.logDebug(this, loggingMethod, "faspex client destroyed");
		deleteResources();
		logger.logDebug(this, loggingMethod, "deleted resources");

		super.applicationTerminate(request, callbacks);
		logger.logExit(this, loggingMethod);
	}

	private void deleteResources() {
		if (resourcePaths == null)
			return;

		resourcePaths.parallelStream().filter(path -> path.toFile().exists()).forEach(path -> {
			File file = path.toFile();
			boolean deleted = false;
			try {
				deleted = file.delete();
			} finally {
				if (file.exists() && !deleted)
					file.deleteOnExit();
			}
		});
	}

	@Override
	public String getCopyright() {
		return "Licensed Materials - Property of IBM<br>(C) Copyright IBM Corp. 2010, 2017<br>US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.";
	}
}
