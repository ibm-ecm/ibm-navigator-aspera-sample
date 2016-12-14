package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.ecm.extension.PluginLogger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class FaspexUtilTest {
	private PluginLogger logger;

	@Before
	public void setUp() throws Exception {
		logger = new PluginLogger("aspera");
	}

	@Test
	public void shouldGetUniqueName() {
		// given
		// when
		String uniqueName = FaspexUtil.getUniqueName(Arrays.asList("filename", "filename1", "filename2"), "new-filename", logger);
		// then
		assertEquals("new-filename", uniqueName);
		// or when
		uniqueName = FaspexUtil.getUniqueName(Arrays.asList("filename.ext", "filename1.ext", "filename2.ext"), "new-filename.ext", logger);
		// then
		assertEquals("new-filename.ext", uniqueName);
		// or when
		uniqueName = FaspexUtil.getUniqueName(Arrays.asList("filename", "filename1", "filename2"), "filename", logger);
		// then
		assertEquals("filename3", uniqueName);
		// or when
		uniqueName = FaspexUtil.getUniqueName(Arrays.asList("filename.ext", "filename1.ext", "filename2.ext"), "filename.ext", logger);
		// then
		assertEquals("filename3.ext", uniqueName);
		// or when
		uniqueName = FaspexUtil.getUniqueName(Arrays.asList("file.name.ext", "file.name1.ext", "file.name2.ext"), "file.name.ext", logger);
		// then
		assertEquals("file.name3.ext", uniqueName);
		// or when
		uniqueName = FaspexUtil.getUniqueName(Arrays.asList("filename.ext", "filename2.ext", "filename3.ext"), "filename.ext", logger);
		// then
		assertEquals("filename1.ext", uniqueName);
	}
}