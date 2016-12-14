package com.ibm.ecm.extension.aspera;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendServiceTest {
	@Mock
	private HttpServletRequest request;
	@Mock
	private SendService service;

	@Before
	public void setUp() throws Exception {
		service = new SendService();
		Encryption.loadSecretKey();
	}

	@Test
	public void shouldValidatePostRequest() throws AsperaPluginException {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("");
		// when
		service.validateRequest(request);
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldInvalidateGetRequest() throws AsperaPluginException {
		// given
		when(request.getMethod()).thenReturn("GET");
		when(request.getQueryString()).thenReturn("");
		// when
		service.validateRequest(request);
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldInvalidateRequestWithSenderInQueryString() throws AsperaPluginException {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("sender=sender");
		// when
		service.validateRequest(request);
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldInvalidateRequestWithPasswordInQueryString() throws AsperaPluginException {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("password=password");
		// when
		service.validateRequest(request);
	}

	@Test(expected = AsperaPluginException.class)
	public void shouldInvalidateRequestWithEarPassphraseInQueryString() throws AsperaPluginException {
		// given
		when(request.getMethod()).thenReturn("POST");
		when(request.getQueryString()).thenReturn("earPassphrase=earPassphrase");
		// when
		service.validateRequest(request);
	}
}