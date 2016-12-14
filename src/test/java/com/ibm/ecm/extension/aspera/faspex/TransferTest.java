package com.ibm.ecm.extension.aspera.faspex;

import com.asperasoft.faspex.client.sdklite.Faspex;
import com.asperasoft.faspex.client.sdklite.data.ErrorInfo;
import com.asperasoft.faspex.client.sdklite.data.TransferSessionInfo;
import com.asperasoft.faspex.client.sdklite.data.TransferType;
import com.asperasoft.faspmanager.TransferEvent;
import com.ibm.ecm.extension.PluginLogger;
import com.ibm.ecm.extension.aspera.Package;
import com.ibm.json.java.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferTest {
	@Mock
	private Faspex faspex;

	private Transfer transfer;
	private FaspexTransferListener listener;
	private TransferSummary summary;
	private boolean callbackCalled;

	@Before
	public void setUp() throws Exception {
		// given
		final Map<String, Object> data = new HashMap<>();
		data.put("sender", "sender");
		data.put("password", "password");
		data.put("recipients", "recipient1, recipient2");
		final Map<String, String> item1 = new HashMap<>();
		item1.put("repositoryId", "repositoryId");
		item1.put("itemId", "itemId1");
		final Map<String, String> item2 = new HashMap<>();
		item2.put("repositoryId", "repositoryId");
		item2.put("itemId", "itemId2");
		data.put("items", Arrays.asList(item1, item2));
		data.put("title", "title");
		final PluginLogger logger = new PluginLogger("aspera");
		transfer = new Transfer(faspex, null, new Package(data), this::transferCallback, 2, logger);
		listener = transfer.getTransferListener();
		summary = transfer.getSummary();
	}

	public void transferCallback(final Transfer p) {
		callbackCalled = true;
	}

	@Test(expected = RuntimeException.class)
	public void shouldTimout() throws InterruptedException {
		// when
		transfer.waitForSignal();
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionWhenFailedToStart() throws InterruptedException {
		// when
		final Thread t = new Thread(() -> {
			try {
				Thread.sleep(1000);
				transfer.onFailed();
			} catch (final InterruptedException e) {
				System.out.println(e);
			}
		});
		t.start();
		transfer.waitForSignal();
	}

	@Test
	public void shouldNotThrowExceptionWhenStartedSuccessfully() throws InterruptedException {
		// when
		final Thread t = new Thread(() -> {
			try {
				Thread.sleep(1000);
				transfer.onStarted();
			} catch (final InterruptedException e) {
				System.out.println(e);
			}
		});
		t.start();
		transfer.waitForSignal();
	}

	@Test
	public void shouldUpdateStatus() throws InterruptedException {
		// given
		final Instant before = Instant.now();
		Thread.sleep(1000);
		// when
		summary.setStarted();
		// then
		assertTrue(summary.isStarted());
		assertTrue(before.isBefore(summary.getUpdatedOn()));
	}

	@Test
	public void shouldGetPackageId() {
		// when
		transfer.onTransferSpecReceived("packageId");
		// then
		assertEquals("packageId", transfer.getSummary().getPackageId());
	}

	@Test
	public void shouldBeStartedWhenSessionStarts() {
		// when
		listener.onTransferEvent(TransferEvent.SESSION_START, new TransferSessionInfo(TransferType.STREAM, "title"));
		// then
		assertTrue(summary.isStarted());
		assertFalse(callbackCalled);
	}

	@Test
	public void shouldFailWhenSessionErrorOccurs() {
		// when
		listener.onTransferEvent(TransferEvent.SESSION_ERROR, new TransferSessionInfo(TransferType.STREAM, "title"));
		// then
		assertTrue(summary.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldUpdateProgress() throws InterruptedException {
		// given
		summary.setStarted();
		final Instant before = summary.getUpdatedOn();
		// when
		Thread.sleep(1000);
		final TransferSessionInfo info = new TransferSessionInfo(TransferType.STREAM, "title");
		info.bytesTransferred = 1L;
		listener.onTransferEvent(TransferEvent.PROGRESS, info);
		// then
		assertTrue(before.isBefore(summary.getUpdatedOn()));
	}

	@Test
	public void shouldNotUpdateProgress() throws InterruptedException {
		// given
		final TransferSessionInfo info = new TransferSessionInfo(TransferType.STREAM, "title");
		info.bytesTransferred = 1L;
		listener.onTransferEvent(TransferEvent.PROGRESS, info);
		final Instant before = summary.getUpdatedOn();
		// when
		Thread.sleep(1000);
		listener.onTransferEvent(TransferEvent.PROGRESS, info);
		// then
		assertTrue(before.equals(summary.getUpdatedOn()));
	}

	@Test
	public void shouldBeFailedOnError() {
		// when
		listener.onError(new ErrorInfo("error"));
		// then
		assertTrue(summary.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldBeCompletedWhenDone() {
		// given
		summary.setStarted();
		// when
		listener.onDone();
		// then
		assertTrue(summary.isCompleted());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldBeStopped() throws Exception {
		// when
		transfer.stop();
		// then
		assertTrue(summary.isStopped());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldNotBeStoppedIfCompleted() throws Exception {
		// given
		summary.setStarted();
		listener.onDone();
		// when
		transfer.stop();
		// then
		assertTrue(summary.isCompleted());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldNotBeStoppedIfFailed() throws Exception {
		// given
		listener.onError(new ErrorInfo("error"));
		// when
		transfer.stop();
		// then
		assertTrue(summary.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldBeConvertedToJson() {
		// given
		transfer.onTransferSpecReceived("packageId");
		// when
		final JSONObject json = transfer.toJson();
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
		expectedJson.put("recipients", "recipient1,recipient2");
		expectedJson.put("note", null);
		expectedJson.put("progress", new JSONObject());
		assertEquals(expectedJson.toString(), json.toString());
	}
}