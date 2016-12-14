package com.ibm.ecm.extension.aspera;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PackageSenderTest {
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private HttpSession session;
	private String[] credentials;
	private PackageSender worker;

	@Before
	public void setUp() throws Exception {
		when(request.getSession(false)).thenReturn(session);
		doAnswer((Answer<Object>) invocationOnMock -> credentials).when(session).getAttribute(eq(PackageSender.CREDENTIALS));
		doAnswer((Answer<Void>) invocationOnMock -> {
			final Object[] args = invocationOnMock.getArguments();
			credentials = (String[]) args[1];
			return null;
		}).when(session).setAttribute(eq(PackageSender.CREDENTIALS), anyObject());
		worker = new PackageSender(null, new JSONObject(), null, new PluginLogger("aspera"));
		Encryption.loadSecretKey();
	}

	@Test
	public void shouldGetPackageDataFromAttribute() throws AsperaPluginException {
		// given
		when(request.getAttribute("json_post")).thenReturn(new JSONObject());
		// when
		final Map<String, Object> data = worker.getPackageData(request);
		// then
		assertEquals("{}", data.toString());
	}

	@Test
	public void shouldGetPackageDataFromParameter() throws AsperaPluginException {
		// given
		when(request.getParameter("json_post")).thenReturn("{}");
		// when
		final Map<String, Object> data = worker.getPackageData(request);
		// then
		assertEquals("{}", data.toString());
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldGetExceptionWithInvalidPackageData() throws AsperaPluginException {
		// given
		when(request.getParameter("json_post")).thenReturn("[]");
		// when
		worker.getPackageData(request);
	}

	@Test
	public void shouldGetDefaultMaxNumberOfItems() {
		// when
		final int max = worker.getMaxNumberOfItems();
		// then
		assertEquals(PackageSender.DEFAULT_MAX_NUMBER_OF_ITEMS, max);
	}

	@Test
	public void shouldGetDefinedMaxNumberOfItems() throws Exception {
		// given
		final JSONObject pluginConfig = new JSONObject();
		final PackageSender worker = new PackageSender(null, pluginConfig, null, new PluginLogger("aspera"));
		// when
		pluginConfig.put("maxNumberOfItems", PackageSender.DEFAULT_MAX_NUMBER_OF_ITEMS + 1);
		// then
		assertEquals(PackageSender.DEFAULT_MAX_NUMBER_OF_ITEMS + 1, worker.getMaxNumberOfItems());
	}

	@Test
	public void getPreparedItemsWithDefaultRepositoryId() {
		// given
		final JSONArray requestedItems = new JSONArray();
		final JSONObject item = new JSONObject();
		item.put("itemId", "itemId");
		requestedItems.add(item);
		// when
		final List<Map<String, String>> preparedItems = worker.prepareItems(requestedItems, "defaultRepository", Collections.singletonList("defaultRepository"));
		// then
		assertEquals("defaultRepository", ((JSONObject) preparedItems.get(0)).get("repositoryId"));
	}

	@Test
	public void shouldValidateSenderCredentials() throws Exception {
		// given
		final String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		final JSONObject json = (JSONObject) JSON.parse(data);
		final String nonce = worker.refreshNonce(request, response, json);
		// when
		when(request.getHeader(PackageSender.NONCE)).thenReturn(nonce);
		final JSONObject handledJson = new JSONObject();
		worker.validateSenderCredentials(request, response, handledJson);
		final String newNonce = worker.refreshNonce(request, response, handledJson);
		// then
		assertNotEquals(newNonce, nonce);
		assertEquals("sender", handledJson.get("sender"));
		assertEquals("password", handledJson.get("password"));
	}

	@Test(expected = AsperaPluginRuntimeException.class)
	public void shouldGetExceptionWhenNonceIsInvalid() throws Exception {
		// given
		final String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		final JSONObject json = (JSONObject) JSON.parse(data);
		worker.refreshNonce(request, response, json);
		// when
		final String invalidNonce = Util.base64Encode(Util.generateNonce());
		when(request.getHeader(PackageSender.NONCE)).thenReturn(invalidNonce);
		worker.validateSenderCredentials(request, response, new JSONObject());
	}

	@Test(expected = AsperaPluginRuntimeException.class)
	public void shouldGetExceptionWhenNonceIsUsedAgain() throws Exception {
		// given
		final String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		final JSONObject json = (JSONObject) JSON.parse(data);
		final String nonce = worker.refreshNonce(request, response, json);
		// when
		when(request.getHeader(PackageSender.NONCE)).thenReturn(nonce);
		worker.validateSenderCredentials(request, response, new JSONObject());
		worker.refreshNonce(request, response, json);
		worker.validateSenderCredentials(request, response, new JSONObject());
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldGetExceptionWhenSenderCredentialAreInsufficient() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender'"
				+ "}";
		JSONObject json = (JSONObject) JSON.parse(data);
		// when
		worker.validateSenderCredentials(request, response, json);
		// or given
		data = "{"
				+ "'password': 'password'"
				+ "}";
		json = (JSONObject) JSON.parse(data);
		// when
		worker.validateSenderCredentials(request, response, json);
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldGetExceptionWhenSavedSenderCredentialsAreNotFound() throws Exception {
		// when
		final String invalidNonce = Util.base64Encode(Util.generateNonce());
		when(request.getHeader(PackageSender.NONCE)).thenReturn(invalidNonce);
		worker.validateSenderCredentials(request, response, new JSONObject());
	}

	@Test
	public void getMaxPreparedItems() throws Exception {
		// given
		final JSONObject pluginConfig = new JSONObject();
		final PackageSender worker = new PackageSender(null, pluginConfig, null, new PluginLogger("aspera"));
		final JSONArray requestedItems = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("itemId", "itemId1");
		requestedItems.add(item);
		item = new JSONObject();
		item.put("itemId", "itemId2");
		requestedItems.add(item);
		item = new JSONObject();
		item.put("itemId", "itemId3");
		requestedItems.add(item);
		// when
		pluginConfig.put("maxNumberOfItems", 2);
		// then
		final List<Map<String, String>> preparedItems = worker.prepareItems(requestedItems, "defaultRepository", Collections.singletonList("defaultRepository"));
		assertEquals(2, preparedItems.size());
	}

	@Test
	public void getPreparedPackageData() throws Exception {
		// given
		final String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "'earPassphrase': 'earPassphrase',"
				+ "'recipients': 'internalUser, external@user.com',"
				+ "'title': 'title',"
				+ "'note': 'note',"
				+ "'items': [{"
				+ "	'itemId': 'itemId1',"
				+ "	'repositoryId': 'repositoryId1'"
				+ "},{"
				+ "	'itemId': 'itemId2',"
				+ "}],"
				+ "'notifyOnUpload': 'true',"
				+ "'notifyOnDownload': 'false'"
				+ "}";
		when(request.getParameter("json_post")).thenReturn(data);
		when(request.getParameter("repositoryId")).thenReturn("defaultRepository");
		// when
		final Map<String, Object> preparedData = worker.preparePackageData(request, response, Arrays.asList("defaultRepository", "repositoryId1"));
		// then
		assertEquals("sender", preparedData.get("sender"));
		assertEquals("password", preparedData.get("password"));
		assertEquals("earPassphrase", preparedData.get("earPassphrase"));
		assertEquals("internalUser, external@user.com", preparedData.get("recipients"));
		assertEquals("title", preparedData.get("title"));
		assertEquals("true", preparedData.get("notifyOnUpload"));
		assertEquals("false", preparedData.get("notifyOnDownload"));
		final List<Map<String, String>> preparedItems = (List<Map<String, String>>) preparedData.get("items");
		assertEquals(2, preparedItems.size());
		assertEquals("itemId1", ((JSONObject) preparedItems.get(0)).get("itemId"));
		assertEquals("repositoryId1", ((JSONObject) preparedItems.get(0)).get("repositoryId"));
		assertEquals("itemId2", ((JSONObject) preparedItems.get(1)).get("itemId"));
		assertEquals("defaultRepository", ((JSONObject) preparedItems.get(1)).get("repositoryId"));
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldGetExceptionWhenNothingPrepared() throws AsperaPluginException {
		// given
		final String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "'recipients': 'internalUser, external@user.com',"
				+ "'title': 'title',"
				+ "'note': 'note',"
				+ "'items': [{"
				+ "	'repositoryId': 'repositoryId1'"
				+ "}],"
				+ "'notifyOnUpload': 'true',"
				+ "'notifyOnDownload': 'false'"
				+ "}";
		when(request.getParameter("json_post")).thenReturn(data);
		when(request.getParameter("repositoryId")).thenReturn("defaultRepository");
		// when
		worker.preparePackageData(request, response, Collections.singletonList("defaultRepository"));
	}
}
