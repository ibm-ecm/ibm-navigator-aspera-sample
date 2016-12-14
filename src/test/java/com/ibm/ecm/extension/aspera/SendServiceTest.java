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

import javax.crypto.BadPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SendServiceTest {
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpSession session;
	private SendService service;
	private String[] credentials;

	@Before
	public void setUp() throws Exception {
		when(request.getSession(false)).thenReturn(session);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return credentials;
			}
		}).when(session).getAttribute(eq(SendService.CREDENTIALS));
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
				Object[] args = invocationOnMock.getArguments();
				credentials = (String[]) args[1];
				return null;
			}
		}).when(session).setAttribute(eq(SendService.CREDENTIALS), anyObject());
		service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		Encryption.loadSecretKey();
	}

	@Test
	public void shouldValidatePostRequest() {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("");
		// when
		service.validateRequest(request);
	}

	@Test(expected = RuntimeException.class)
	public void shouldInvalidateGetRequest() {
		// given
		when(request.getMethod()).thenReturn("GET");
		when(request.getQueryString()).thenReturn("");
		// when
		service.validateRequest(request);
	}

	@Test(expected = RuntimeException.class)
	public void shouldInvalidateRequestWithSenderInQueryString() {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("sender=sender");
		// when
		service.validateRequest(request);
	}

	@Test(expected = RuntimeException.class)
	public void shouldInvalidateRequestWithPasswordInQueryString() {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("password=password");
		// when
		service.validateRequest(request);
	}

	@Test(expected = RuntimeException.class)
	public void shouldInvalidateRequestWithEarPasswordInQueryString() {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("earPassword=earPassword");
		// when
		service.validateRequest(request);
	}

	@Test
	public void shouldGetPackageDataFromAttribute() {
		// given
		when(request.getAttribute("json_post")).thenReturn(new JSONObject());
		// when
		Map<String, Object> data = service.getPackageData(request);
		// then
		assertEquals("{}", data.toString());
	}

	@Test
	public void shouldGetPackageDataFromParameter() {
		// given
		when(request.getParameter("json_post")).thenReturn("{}");
		// when
		Map<String, Object> data = service.getPackageData(request);
		// then
		assertEquals("{}", data.toString());
	}

	@Test(expected = RuntimeException.class)
	public void shouldGetExceptionWithInvalidPackageData() {
		// given
		when(request.getParameter("json_post")).thenReturn("[]");
		// when
		service.getPackageData(request);
	}

	@Test
	public void shouldGetDefaultMaxNumberOfItems() {
		// given
		SendService service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		// when
		assertEquals(SendService.DEFAULT_MAX_NUMBER_OF_ITEMS, service.getMaxNumberOfItems());
	}

	@Test
	public void shouldGetDefinedMaxNumberOfItems() {
		// given
		JSONObject pluginConfig = new JSONObject();
		SendService service = new SendService(pluginConfig, new PluginLogger("aspera"));
		// when
		pluginConfig.put("maxNumberOfItems", SendService.DEFAULT_MAX_NUMBER_OF_ITEMS + 1);
		// then
		assertEquals(SendService.DEFAULT_MAX_NUMBER_OF_ITEMS + 1, service.getMaxNumberOfItems());
	}

	@Test
	public void getPreparedItemsWithDefaultRepositoryId() {
		// given
		JSONArray requestedItems = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("itemId", "itemId");
		requestedItems.add(item);
		// when
		List<Map<String, String>> preparedItems = service.prepareItems(requestedItems, "defaultRepository", Arrays.asList("defaultRepository"));
		// then
		assertEquals("defaultRepository", ((JSONObject) preparedItems.get(0)).get("repositoryId"));
	}

	@Test
	public void shouldValidateSenderCredentials() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		JSONObject json = (JSONObject) JSON.parse(data);
		SendService service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		String nonce = service.validateSenderCredentials(request, json);
		// when
		when(request.getHeader(SendService.NONCE)).thenReturn(nonce);
		JSONObject handledJson = new JSONObject();
		String newNonce = service.validateSenderCredentials(request, handledJson);
		// then
		assertNotEquals(newNonce, nonce);
		assertEquals("sender", handledJson.get("sender"));
		assertEquals("password", handledJson.get("password"));
	}

	@Test(expected = BadPaddingException.class)
	public void shouldGetExceptionWhenNonceIsInvalid() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		JSONObject json = (JSONObject) JSON.parse(data);
		SendService service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		service.validateSenderCredentials(request, json);
		// when
		String invalidNonce = Util.base64Encode(Util.generateNonce());
		when(request.getHeader(SendService.NONCE)).thenReturn(invalidNonce);
		service.validateSenderCredentials(request, new JSONObject());
	}

	@Test(expected = BadPaddingException.class)
	public void shouldGetExceptionWhenNonceIsUsedAgain() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "}";
		JSONObject json = (JSONObject) JSON.parse(data);
		SendService service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		String nonce = service.validateSenderCredentials(request, json);
		// when
		when(request.getHeader(SendService.NONCE)).thenReturn(nonce);
		service.validateSenderCredentials(request, new JSONObject());
		service.validateSenderCredentials(request, new JSONObject());
	}

	@Test(expected = RuntimeException.class)
	public void shouldGetExceptionWhenSenderCredentialAreInsufficient() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender'"
				+ "}";
		JSONObject json = (JSONObject) JSON.parse(data);
		JSONObject pluginConfig = new JSONObject();
		SendService service = new SendService(pluginConfig, new PluginLogger("aspera"));
		// when
		service.validateSenderCredentials(request, json);
		// or given
		data = "{"
				+ "'password': 'password'"
				+ "}";
		json = (JSONObject) JSON.parse(data);
		service = new SendService(pluginConfig, new PluginLogger("aspera"));
		// when
		service.validateSenderCredentials(request, json);
	}

	@Test(expected = RuntimeException.class)
	public void shouldGetExceptionWhenSavedSenderCredentialsAreNotFound() throws Exception {
		// given
		SendService service = new SendService(new JSONObject(), new PluginLogger("aspera"));
		// when
		String invalidNonce = Util.base64Encode(Util.generateNonce());
		when(request.getHeader(SendService.NONCE)).thenReturn(invalidNonce);
		service.validateSenderCredentials(request, new JSONObject());
	}

	@Test
	public void getMaxPreparedItems() {
		// given
		JSONObject pluginConfig = new JSONObject();
		SendService service = new SendService(pluginConfig, new PluginLogger("aspera"));
		JSONArray requestedItems = new JSONArray();
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
		List<Map<String, String>> preparedItems = service.prepareItems(requestedItems, "defaultRepository", Arrays.asList("defaultRepository"));
		assertEquals(2, preparedItems.size());
	}

	@Test
	public void getPreparedPackageData() throws Exception {
		// given
		String data = "{"
				+ "'sender': 'sender',"
				+ "'password': 'password',"
				+ "'earPassword': 'earPassword',"
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
		Map<String, Object> preparedData = service.preparePackageData(request, Arrays.asList("defaultRepository", "repositoryId1"));
		// then
		assertEquals("sender", preparedData.get("sender"));
		assertEquals("password", preparedData.get("password"));
		assertEquals("earPassword", preparedData.get("earPassword"));
		assertEquals("internalUser, external@user.com", preparedData.get("recipients"));
		assertEquals("title", preparedData.get("title"));
		assertEquals("true", preparedData.get("notifyOnUpload"));
		assertEquals("false", preparedData.get("notifyOnDownload"));
		List<Map<String, String>> preparedItems = (List<Map<String, String>>) preparedData.get("items");
		assertEquals(2, preparedItems.size());
		assertEquals("itemId1", ((JSONObject) preparedItems.get(0)).get("itemId"));
		assertEquals("repositoryId1", ((JSONObject) preparedItems.get(0)).get("repositoryId"));
		assertEquals("itemId2", ((JSONObject) preparedItems.get(1)).get("itemId"));
		assertEquals("defaultRepository", ((JSONObject) preparedItems.get(1)).get("repositoryId"));
	}

	@Test(expected = RuntimeException.class)
	public void shouldGetExceptionWhenNothingPrepared() throws Exception {
		// given
		String data = "{"
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
		service.preparePackageData(request, Arrays.asList("defaultRepository"));
	}
}