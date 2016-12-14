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
public class ListPackagesActionTest {
	@Mock
	private FaspexClient client;
	@Mock
	private Transfers transfers;

	@Before
	public void setUp() throws Exception {
		when(client.getTransfers()).thenReturn(transfers);
	}

	@Test
	public void shouldListPackages() {
		// given
		final String[] myPackages = new String[]{"myPackage"};
		final ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.list, myPackages, false, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(transfers.runningTransfersToJson(myPackages)).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldListArchivedPackages() {
		// given
		final String[] myPackages = new String[]{"myPackage"};
		final ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.listRecent, myPackages, false, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(transfers.archivedTransfersToJson(myPackages)).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldListAllPackages() {
		// given
		final ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.listAll, null, true, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(transfers.runningTransfersToJson()).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldListAllArchivedPackages() {
		// given
		final ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.listAllRecent, null, true, new PluginLogger("aspera"));
		final JSONArray json = new JSONArray();
		// then
		assertFalse(json == action.run());
		// and when
		when(transfers.archivedTransfersToJson()).thenReturn(json);
		// then
		assertTrue(json == action.run());
	}

	@Test
	public void shouldBeValid() {
		// given
		ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.list, null, false, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
		// and given
		action = new ListPackagesAction(client, PackageAction.PackageActionType.listRecent, null, false, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
		// and given
		action = new ListPackagesAction(client, PackageAction.PackageActionType.listAll, null, true, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
		// and given
		action = new ListPackagesAction(client, PackageAction.PackageActionType.listAllRecent, null, true, new PluginLogger("aspera"));
		// when/then
		assertTrue(action.isValid());
	}

	@Test
	public void shouldBeInvalid() {
		// given
		ListPackagesAction action = new ListPackagesAction(client, PackageAction.PackageActionType.listAll, null, false, new PluginLogger("aspera"));
		// when/then
		assertFalse(action.isValid());
		// and given
		action = new ListPackagesAction(client, PackageAction.PackageActionType.listAllRecent, null, false, new PluginLogger("aspera"));
		// when/then
		assertFalse(action.isValid());
	}
}
