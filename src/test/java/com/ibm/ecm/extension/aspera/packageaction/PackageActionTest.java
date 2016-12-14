package com.ibm.ecm.extension.aspera.packageaction;

import com.ibm.ecm.extension.aspera.faspex.FaspexClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PackageActionTest {
	@Mock
	private FaspexClient client;

	@Test
	public void shouldGetGetPackageAction() {
		// when
		PackageAction action = PackageAction.getPackageAction(client, "get", null, null, false, null);
		// then
		assertTrue(action instanceof GetPackageAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.get, null, null, false, null);
		// then
		assertTrue(action instanceof GetPackageAction);
	}

	@Test
	public void shouldGetStopPackageAction() {
		// when
		PackageAction action = PackageAction.getPackageAction(client, "stop", null, null, false, null);
		// then
		assertTrue(action instanceof StopPackageAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.stop, null, null, false, null);
		// then
		assertTrue(action instanceof StopPackageAction);
	}

	@Test
	public void shouldGetListPackagesAction() {
		// when
		PackageAction action = PackageAction.getPackageAction(client, "list", null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, "listAll", null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, "listRecent", null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, "listAllRecent", null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.list, null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.listAll, null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.listRecent, null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.listAllRecent, null, null, false, null);
		// then
		assertTrue(action instanceof ListPackagesAction);
	}

	@Test
	public void shouldGetStopPackagesAction() {
		// when
		PackageAction action = PackageAction.getPackageAction(client, "stopAll", null, null, false, null);
		// then
		assertTrue(action instanceof StopPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, "stopAllFromAll", null, null, false, null);
		// then
		assertTrue(action instanceof StopPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.stopAll, null, null, false, null);
		// then
		assertTrue(action instanceof StopPackagesAction);
		// and when
		action = PackageAction.getPackageAction(client, PackageAction.PackageActionType.stopAllFromAll, null, null, false, null);
		// then
		assertTrue(action instanceof StopPackagesAction);
	}

	@Test
	public void shouldBeMyPackage() {
		// given
		final PackageAction action = PackageAction.getPackageAction(client, "get", "myPackage", new String[]{"myPackage", "myAnotherPackage"}, false, null);
		// when/then
		assertTrue(action.isMyPackage());
	}

	@Test
	public void shouldNotBeMyPackage() {
		// given
		final PackageAction action = PackageAction.getPackageAction(client, "get", "yourPackage", new String[]{"myPackage", "myAnotherPackage"}, false, null);
		// when/then
		assertFalse(action.isMyPackage());
	}

	@Test
	public void shouldBeSingleActionType() {
		assertFalse(PackageAction.PackageActionType.isMultiple("get"));
		assertFalse(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.get));
		assertFalse(PackageAction.PackageActionType.isMultiple("stop"));
		assertFalse(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.stop));
	}

	@Test
	public void shouldBeMultipleActionType() {
		assertTrue(PackageAction.PackageActionType.isMultiple("list"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.list));
		assertTrue(PackageAction.PackageActionType.isMultiple("listAll"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.listAll));
		assertTrue(PackageAction.PackageActionType.isMultiple("listRecent"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.listRecent));
		assertTrue(PackageAction.PackageActionType.isMultiple("listAllRecent"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.listAllRecent));
		assertTrue(PackageAction.PackageActionType.isMultiple("stopAll"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.stopAll));
		assertTrue(PackageAction.PackageActionType.isMultiple("stopAllFromAll"));
		assertTrue(PackageAction.PackageActionType.isMultiple(PackageAction.PackageActionType.stopAllFromAll));
	}

	@Test
	public void shouldbeEqualActionType() {
		assertTrue(PackageAction.PackageActionType.get.equals("get"));
		assertTrue(PackageAction.PackageActionType.stop.equals("stop"));
		assertTrue(PackageAction.PackageActionType.list.equals("list"));
		assertTrue(PackageAction.PackageActionType.listAll.equals("listAll"));
		assertTrue(PackageAction.PackageActionType.listRecent.equals("listRecent"));
		assertTrue(PackageAction.PackageActionType.listAllRecent.equals("listAllRecent"));
		assertTrue(PackageAction.PackageActionType.stopAll.equals("stopAll"));
		assertTrue(PackageAction.PackageActionType.stopAllFromAll.equals("stopAllFromAll"));
	}
}
