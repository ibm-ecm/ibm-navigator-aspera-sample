package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Faspex;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FaspexClientTest {
	@Mock
	Faspex faspex;

	private FaspexClient client;
	private JSONObject pluginConfig;
	private PluginLogger logger;
	private SendProcess process1;
	private SendProcess process2;

	@Before
	public void setUp() throws Exception {
		// given
		logger = new PluginLogger("aspera");
		pluginConfig = new JSONObject();
		pluginConfig.put("maxNumberOfRequests", 2);
		pluginConfig.put("archiveDurationMinutes", 1);
		pluginConfig.put("progressTimeoutSeconds", 1);
		client = new FaspexClient(pluginConfig, logger);

		Map<String, Object> data = new HashMap<>();
		data.put("sender", "sender");
		data.put("password", "password");
		data.put("recipients", "recipient1, recipient2");
		Map<String, String> item1 = new HashMap<>();
		item1.put("repositoryId", "repositoryId");
		item1.put("itemId", "itemId1");
		Map<String, String> item2 = new HashMap<>();
		item2.put("repositoryId", "repositoryId");
		item2.put("itemId", "itemId2");
		data.put("items", Arrays.asList(item1, item2));
		process1 = new SendProcess(faspex, null, new Package(data), null, 30, logger);
		process2 = new SendProcess(faspex, null, new Package(data), null, 30, logger);
	}

	@Test
	public void shouldGetIntegerConfigValue() {
		// when
		int max = client.getIntegerConfigValue(pluginConfig, "maxNumberOfRequests", 1);
		// then
		assertEquals(2, max);
		// or when
		int undefined = client.getIntegerConfigValue(pluginConfig, "undefined", 1);
		// then
		assertEquals(1, undefined);
	}

	@Test
	public void shouldReachMaxNumberOfProcesses() {
		// when
		client.addProcess(process1);
		// then
		assertFalse(client.isMaxNumberOfRequestsReached());
		// and when
		client.addProcess(process2);
		// then
		assertTrue(client.isMaxNumberOfRequestsReached());
	}

	@Test
	public void shouldGetProcess() {
		// given
		client.addProcess(process1);
		// when
		process1.onSendProcessSpecReceived("packageId");
		// then
		assertEquals(process1, client.getProcess("packageId"));
	}

	@Test
	public void shouldGetArchivedProcess() {
		// given
		client.addProcess(process1);
		process1.onSendProcessSpecReceived("packageId");
		// when
		client.archiveProcess(process1);
		// then
		assertNull(client.getProcess("packageId"));
		assertEquals(process1, client.getArchivedProcess("packageId"));
	}

	@Test
	public void shouldGetProcessToJson() {
		// given
		client.addProcess(process1);
		// when
		process1.onSendProcessSpecReceived("packageId");
		// then
		assertEquals(process1.toJson(), client.processToJson("packageId"));
	}

	@Test
	public void shouldGetArchivedProcessToJson() {
		// given
		client.addProcess(process1);
		process1.onSendProcessSpecReceived("packageId");
		// when
		client.archiveProcess(process1);
		// then
		assertEquals(process1.toJson(), client.processToJson("packageId"));
	}

	@Test
	public void shouldGetRunningProcessesToJson() {
		// given
		client.addProcess(process1);
		client.addProcess(process2);
		// when
		process1.onSendProcessSpecReceived("packageId");
		process2.onSendProcessSpecReceived("packageId2");
		// then
		assertEquals(Stream.of(process1.summaryToJson(), process2.summaryToJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), client.runningProcessesToJson());
		assertEquals(Stream.of(process1.summaryToJson(), process2.summaryToJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), client.runningProcessesToJson("packageId", "packageId2"));
	}

	@Test
	public void shouldGetArchivedProcessesToJson() {
		// given
		client.addProcess(process1);
		client.addProcess(process2);
		process1.onSendProcessSpecReceived("packageId");
		process2.onSendProcessSpecReceived("packageId2");
		// when
		client.archiveProcess(process1);
		client.archiveProcess(process2);
		// then
		assertEquals(Stream.of(process1.summaryToJson(), process2.summaryToJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), client.archivedProcessesToJson());
		assertEquals(Stream.of(process1.summaryToJson(), process2.summaryToJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), client.archivedProcessesToJson("packageId", "packageId2"));
	}

	@Test
	public void shouldGetExistentPackageIds() {
		// given
		client.addProcess(process1);
		process1.onSendProcessSpecReceived("packageId");
		// when
		String[] packageIds = client.getExistentPackageIds(new String[]{"packageId", "nonExistentPackageId"});
		// then
		assertEquals(1, packageIds.length);
		assertEquals("packageId", packageIds[0]);
	}

	@Test
	public void shouldGetHangingProcesses() {
		// given
		client.addProcess(process1);
		client.addProcess(process2);
		// when
		process1.updateStatus(SendProcess.Status.STARTED);
		process1.setUpdatedOn(Instant.now().minus(2, ChronoUnit.SECONDS));
		// then
		List<SendProcess> processes = client.getHangingProcesses();
		assertEquals(1, processes.size());
		assertEquals(process1, processes.get(0));
	}

	@Test
	public void shouldPurgeExpiredArchives() {
		// given
		client.addProcess(process1);
		process1.onSendProcessSpecReceived("packageId");
		process1.setUpdatedOn(Instant.now().minus(2, ChronoUnit.MINUTES));
		client.archiveProcess(process1);
		// when
		client.purgeExpiredArchives();
		// then
		assertNull(client.getArchivedProcess("packageId"));
	}
}