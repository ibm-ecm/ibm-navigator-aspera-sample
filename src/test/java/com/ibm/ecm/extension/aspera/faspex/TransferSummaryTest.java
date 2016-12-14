package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferSummaryTest {
	private TransferSummary summary;

	@Before
	public void setUp() throws Exception {
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
		summary = new TransferSummary(new Package(data));
		summary.setPackageId("packageId");
	}

	@Test
	public void shouldBeConvertedToJson() {
		// when
		final JSONObject json = summary.toJson();
		// then
		final JSONObject expectedJson = new JSONObject();
		expectedJson.put("_transferId", summary.getId());
		expectedJson.put("id", "packageId");
		expectedJson.put("title", "title");
		expectedJson.put("sender", "sender");
		expectedJson.put("size", 0);
		expectedJson.put("status", "PENDING");
		expectedJson.put("updatedOn", summary.getUpdatedOn().toString());
		expectedJson.put("lastEvent", null);
		assertEquals(expectedJson.toString(), json.toString());
	}
}