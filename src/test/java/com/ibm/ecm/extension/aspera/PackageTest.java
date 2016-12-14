package com.ibm.ecm.extension.aspera;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackageTest {
	@Test
	public void shouldRetrievePackageData() throws Exception {
		// given
		final JSONObject data = (JSONObject) JSON.parse("{" //
				+ "'sender': 'sender'," //
				+ "'password': 'password'," //
				+ "'earPassphrase': 'earPassphrase'," //
				+ "'recipients': 'internalUser, external@user.com'," //
				+ "'title': 'title'," //
				+ "'note': 'note'," //
				+ "'items': [{" //
				+ "	'itemId': 'itemId1'," //
				+ "	'repositoryId': 'repositoryId1'" //
				+ "},{" //
				+ "	'itemId': 'itemId2'," //
				+ "	'repositoryId': 'repositoryId1'" //
				+ "},{" //
				+ "	'itemId': 'itemId1'," //
				+ "	'repositoryId': 'repositoryId2'" //
				+ "},{" //
				+ "	'itemId': 'itemId2'," //
				+ "	'repositoryId': 'repositoryId2'" //
				+ "}]," //
				+ "'notifyOnUpload': 'true'," //
				+ "'notifyOnDownload': 'false'" //
				+ "}");
		// when
		final Package pack = new Package(data);
		// then
		assertEquals("password", pack.getPassword());
		assertEquals("earPassphrase", pack.getEarPassphrase());
		assertEquals("Package(sender = [sender], recipients = [[internalUser, external@user.com]], title = [title], note = [note], notifyOnUpload = [true], notifyOnDownload = [false], items = [{repositoryId1=[itemId1, itemId2], repositoryId2=[itemId1, itemId2]}])", pack.toString());
	}
}