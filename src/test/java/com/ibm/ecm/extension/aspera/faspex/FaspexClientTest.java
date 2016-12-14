package com.ibm.ecm.extension.aspera.faspex;

import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.AsperaPluginException;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FaspexClientTest {
	private FaspexClient client;
	private JSONObject pluginConfig;

	@Before
	public void setUp() throws Exception {
		// given
		final PluginLogger logger = new PluginLogger("aspera");
		pluginConfig = new JSONObject();
		pluginConfig.put("maxNumberOfRequests", 2);
		client = new FaspexClient(pluginConfig, logger);
	}

	@Test
	public void shouldGetIntegerConfigValue() {
		// when
		final int max = client.getIntegerConfigValue(pluginConfig, "maxNumberOfRequests", 1);
		// then
		assertEquals(2, max);
		// or when
		final int undefined = client.getIntegerConfigValue(pluginConfig, "undefined", 1);
		// then
		assertEquals(1, undefined);
	}

	@Test
	public void shouldReachMaxNumberOfTransfers() throws AsperaPluginException {
		// when
		client.getTransfers().addTransfer(new Transfer(null, null, null, null, 0, null));
		// then
		assertFalse(client.isMaxNumberOfRequestsReached());
		// and when
		client.getTransfers().addTransfer(new Transfer(null, null, null, null, 0, null));
		// then
		assertTrue(client.isMaxNumberOfRequestsReached());
	}
}