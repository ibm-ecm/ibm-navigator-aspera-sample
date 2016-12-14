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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TransfersTest {
	@Mock
	private Faspex faspex;

	private Transfers transfers;
	private Transfer transfer1;
	private Transfer transfer2;
	private TransferSummary summary1;
	private TransferSummary summary2;

	@Before
	public void setUp() throws Exception {
		// given
		final PluginLogger logger = new PluginLogger("aspera");
		final JSONObject pluginConfig = new JSONObject();
		pluginConfig.put("archiveDurationMinutes", 1);
		pluginConfig.put("progressTimeoutSeconds", 1);
		final FaspexClient client = new FaspexClient(pluginConfig, logger);
		transfers = client.getTransfers();

		final Map<String, Object> data = new HashMap<>();
		data.put("sender", "sender");
		data.put("password", "password");
		data.put("recipients", "recipient1, recipient2");
		final Map<String, String> item1 = new HashMap<>();
		item1.put("repositoryId", "repositoryId");
		item1.put("itemId", "itemId1");
		final Map<String, String> item2 = new HashMap<>();
		item2.put("repositoryId", "repositoryId");
		item2.put("itemId", "itemId2");
		data.put("items", Arrays.asList(item1, item2));
		transfer1 = new Transfer(faspex, null, new Package(data), null, 30, logger);
		transfer2 = new Transfer(faspex, null, new Package(data), null, 30, logger);
		summary1 = transfer1.getSummary();
		summary2 = transfer2.getSummary();
	}

	@Test
	public void shouldGetTransfer() {
		// given
		transfers.addTransfer(transfer1);
		// when
		transfer1.onTransferSpecReceived("packageId");
		// then
		assertEquals(transfer1, transfers.getTransfer("packageId"));
	}

	@Test
	public void shouldGetArchivedTransfer() {
		// given
		transfers.addTransfer(transfer1);
		transfer1.onTransferSpecReceived("packageId");
		// when
		transfers.archiveTransfer(transfer1);
		// then
		assertNull(transfers.getTransfer("packageId"));
		assertEquals(summary1, transfers.getArchivedTransfer("packageId"));
	}

	@Test
	public void shouldGetTransferToJson() {
		// given
		transfers.addTransfer(transfer1);
		// when
		transfer1.onTransferSpecReceived("packageId");
		// then
		assertEquals(transfer1.toJson(), transfers.transferToJson("packageId"));
	}

	@Test
	public void shouldGetArchivedTransferToJson() {
		// given
		transfers.addTransfer(transfer1);
		transfer1.onTransferSpecReceived("packageId");
		// when
		transfers.archiveTransfer(transfer1);
		// then
		assertEquals(summary1.toJson(), transfers.archivedTransferToJson("packageId"));
	}

	@Test
	public void shouldGetRunningTransfersToJson() {
		// given
		transfers.addTransfer(transfer1);
		transfers.addTransfer(transfer2);
		// when
		transfer1.onTransferSpecReceived("packageId");
		transfer2.onTransferSpecReceived("packageId2");
		// then
		assertEquals(Stream.of(summary1.toJson(), summary2.toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), transfers.runningTransfersToJson());
		assertEquals(Stream.of(summary1.toJson(), summary2.toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), transfers.runningTransfersToJson("packageId", "packageId2"));
	}

	@Test
	public void shouldGetArchivedTransfersToJson() {
		// given
		transfers.addTransfer(transfer1);
		transfers.addTransfer(transfer2);
		transfer1.onTransferSpecReceived("packageId");
		transfer2.onTransferSpecReceived("packageId2");
		// when
		transfers.archiveTransfer(transfer1);
		transfers.archiveTransfer(transfer2);
		// then
		assertEquals(Stream.of(summary1.toJson(), summary2.toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), transfers.archivedTransfersToJson());
		assertEquals(Stream.of(summary1.toJson(), summary2.toJson()).collect(JSONArray::new, JSONArray::add, JSONArray::addAll), transfers.archivedTransfersToJson("packageId", "packageId2"));
	}

	@Test
	public void shouldGetExistentPackageIds() {
		// given
		transfers.addTransfer(transfer1);
		transfer1.onTransferSpecReceived("packageId");
		// when
		final String[] packageIds = transfers.getExistentPackageIds("packageId", "nonExistentPackageId");
		// then
		assertEquals(1, packageIds.length);
		assertEquals("packageId", packageIds[0]);
	}

	@Test
	public void shouldGetHangingTransfers() {
		// given
		transfers.addTransfer(transfer1);
		transfers.addTransfer(transfer2);
		// when
		summary1.setStarted();
		summary1.setUpdatedOn(Instant.now().minus(2, ChronoUnit.SECONDS));
		// then
		final List<Transfer> hangingTransfers = transfers.getHangingTransfers(Instant.now().minus(1, ChronoUnit.SECONDS));
		assertEquals(1, hangingTransfers.size());
		assertEquals(transfer1, hangingTransfers.get(0));
	}

	@Test
	public void shouldPurgeExpiredArchives() {
		// given
		transfers.addTransfer(transfer1);
		transfer1.onTransferSpecReceived("packageId");
		summary1.setUpdatedOn(Instant.now().minus(2, ChronoUnit.MINUTES));
		transfers.archiveTransfer(transfer1);
		// when
		transfers.purgeExpiredArchives(Instant.now().minus(1, ChronoUnit.MINUTES));
		// then
		assertNull(transfers.getArchivedTransfer("packageId"));
	}
}
