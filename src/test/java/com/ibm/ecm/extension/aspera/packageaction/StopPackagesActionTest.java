package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import com.ibm.ecm.extension.aspera.faspex.Transfers;
import com.ibm.json.java.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StopPackagesActionTest {
	@Mock
	private FaspexClient client;
	@Mock
	private Transfers transfers;

	@Before
	public void setUp() throws Exception {
		when(client.getTransfers()).thenReturn(transfers);
	}

	@Test
	public void shouldStopAllPackages() {
		// given
		final String[] myPackages = new String[]{"myPackage"};
		final StopPackagesAction action = new StopPackagesAction(client, PackageAction.PackageActionType.stopAll, myPackages, false, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(client.stopTransfers(myPackages)).thenReturn(myPackages);
		when(transfers.archivedTransfersToJson(myPackages)).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldStopAllPackagesFromAll() {
		// given
		final String[] myPackages = new String[]{"myPackage"};
		final StopPackagesAction action = new StopPackagesAction(client, PackageAction.PackageActionType.stopAllFromAll, myPackages, true, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(client.stopAllTransfers()).thenReturn(myPackages);
		when(transfers.archivedTransfersToJson(myPackages)).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldBeValid() {
		// given
		StopPackagesAction action = new StopPackagesAction(client, PackageAction.PackageActionType.stopAll, null, false, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
		// and given
		action = new StopPackagesAction(client, PackageAction.PackageActionType.stopAllFromAll, null, true, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
	}

	@Test
	public void shouldBeInvalid() {
		// given
		final StopPackagesAction action = new StopPackagesAction(client, PackageAction.PackageActionType.stopAllFromAll, null, false, new PluginLogger("aspera"));
		// when/then
		assertFalse(action.isValid());
	}
}
