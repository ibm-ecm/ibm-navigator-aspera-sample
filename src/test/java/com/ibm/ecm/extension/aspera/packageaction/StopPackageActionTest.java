package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.ecm.extension.aspera.faspex.Transfers;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StopPackageActionTest {
	@Mock
	private FaspexClient client;
	@Mock
	private Transfers transfers;

	@Before
	public void setUp() throws Exception {
		when(client.getTransfers()).thenReturn(transfers);
	}

	@Test
	public void shouldStopPackage() {
		// given
		final StopPackageAction action = new StopPackageAction(client, "packageId", new String[]{"packageId"}, false, new PluginLogger("aspera"));
		// when
		when(client.stopTransfer("packageId")).thenReturn(true);
		final JSONObject json = new JSONObject();
		when(transfers.archivedTransferToJson("packageId")).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldNotStopPackage() {
		// given
		final StopPackageAction action = new StopPackageAction(client, "packageId", new String[]{"packageId"}, false, new PluginLogger("aspera"));
		// when
		when(client.stopTransfer("packageId")).thenReturn(false);
		final JSONObject json = new JSONObject();
		when(transfers.archivedTransferToJson("packageId")).thenReturn(json);
		// then
		assertFalse(json == action.run());
	}

	@Test
	public void shouldBeValid() {
		// given
		StopPackageAction action = new StopPackageAction(client, "myPackage", new String[]{"myPackage"}, false, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
		// and given
		action = new StopPackageAction(client, "yourPackage", new String[]{"myPackage"}, true, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
	}

	@Test
	public void shouldBeInvalid() {
		// given
		final StopPackageAction action = new StopPackageAction(client, "yourPackage", new String[]{"myPackage"}, false, new PluginLogger("aspera"));
		// when/then
		assertFalse(action.isValid());
	}
}
