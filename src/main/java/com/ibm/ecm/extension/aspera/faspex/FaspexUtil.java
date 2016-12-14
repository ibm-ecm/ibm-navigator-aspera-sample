package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.data.FaspexPackage;
import com.asperasoft.faspex.client.sdklite.data.Source;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.Package;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Faspex utility methods.
 */
final class FaspexUtil {
	private FaspexUtil() {
	}

	static FaspexPackage createFaspexPackage(final Package pkg) {
		final FaspexPackage faspPkg = new FaspexPackage(pkg.getTitle(), pkg.getNote(), pkg.getRecipients());
		if (pkg.isNotifyOnUpload()) {
			faspPkg.setUploadNotifyRecipients(Collections.singletonList(pkg.getSender()));
		}
		if (pkg.isNotifyOnDownload()) {
			faspPkg.setDownloadNotifyRecipients(Collections.singletonList(pkg.getSender()));
		}

		return faspPkg;
	}

	static List<Source> createSourceInfo(final List<Package.Content> contents, final PluginLogger logger) {
		final List<Source> sourceInfo = new ArrayList<>();
		final List<String> filenames = new ArrayList<>(contents.size());
		contents.forEach(c -> {
			final String filename = getUniqueName(filenames, c.getFileName(), logger);
			filenames.add(filename);
			logger.logDebug(FaspexUtil.class, "createSourceInfo", "create source: " + filename);
			sourceInfo.add(Source.createStreamInputSource(c.getInputStream(), filename));
		});

		return sourceInfo;
	}

	static String getUniqueName(final List<String> existingNames, final String newName, final PluginLogger logger) {
		if (!existingNames.contains(newName)) {
			return newName;
		}

		logger.logDebug(FaspexUtil.class, "getUniqueName", "non-unique filename: " + newName);
		return IntStream.range(1, existingNames.size() + 1).mapToObj(i -> {
			String name = newName;
			String ext = "";
			final int dot = newName.lastIndexOf('.');
			if (dot > 0) {
				name = newName.substring(0, dot);
				ext = newName.substring(dot);
			}
			return name + i + ext;
		}).filter(n -> !existingNames.contains(n)).findFirst().orElse(newName);
	}
}
