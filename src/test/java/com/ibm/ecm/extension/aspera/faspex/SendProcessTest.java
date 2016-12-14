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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SendProcessTest {
	@Mock
	Faspex faspex;

	private SendProcess process;
	private boolean callbackCalled;

	@Before
	public void setUp() throws Exception {
		// given
		Map<String, Object> data = new HashMap<>();
		data.put("sender", "sender");
		data.put("password", "password");
		data.put("recipients", "recipient1, recipient2");
		Map<String, String> item1 = new HashMap<>();
		item1.put("repositoryId", "repositoryId");
		item1.put("itemId", "itemId1");
		Map<String, String> item2 = new HashMap<>();
		item2.put("repositoryId", "repositoryId");
		item2.put("itemId", "itemId2");
		data.put("items", Arrays.asList(item1, item2));
		data.put("title", "title");
		process = new SendProcess(faspex, null, new Package(data), this::processCallback, 2, new PluginLogger("aspera"));
	}

	public void processCallback(SendProcess p) {
		callbackCalled = true;
	}

	@Test(expected = RuntimeException.class)
	public void shouldTimout() throws InterruptedException {
		// when
		process.waitForSignal();
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionWhenFailedToStart() throws InterruptedException {
		// when
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					process.onFailed();
				} catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		};
		t.start();
		process.waitForSignal();
	}

	@Test
	public void shouldNotThrowExceptionWhenStartedSuccessfully() throws InterruptedException {
		// when
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					process.onStarted();
				} catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		};
		t.start();
		process.waitForSignal();
	}

	@Test
	public void shouldUpdateStatus() throws InterruptedException {
		// given
		Instant before = Instant.now();
		Thread.sleep(1000);
		// when
		process.updateStatus(SendProcess.Status.STARTED);
		// then
		assertTrue(process.isStarted());
		assertTrue(before.isBefore(process.getUpdatedOn()));
	}

	@Test
	public void shouldGetPackageId() {
		// when
		process.onSendProcessSpecReceived("packageId");
		// then
		assertEquals("packageId", process.getPackageId());
	}

	@Test
	public void shouldBeStartedWhenSessionStarts() {
		// when
		process.onSendProcessEvent(TransferEvent.SESSION_START, new TransferSessionInfo(TransferType.STREAM, "title"));
		// then
		assertTrue(process.isStarted());
		assertFalse(callbackCalled);
	}

	@Test
	public void shouldFailWhenSessionErrorOccurs() {
		// when
		process.onSendProcessEvent(TransferEvent.SESSION_ERROR, new TransferSessionInfo(TransferType.STREAM, "title"));
		// then
		assertTrue(process.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldUpdateProgress() throws InterruptedException {
		// given
		process.updateStatus(SendProcess.Status.STARTED);
		Instant before = process.getUpdatedOn();
		// when
		Thread.sleep(1000);
		TransferSessionInfo info = new TransferSessionInfo(TransferType.STREAM, "title");
		info.bytesTransferred = Long.valueOf(1);
		process.onSendProcessEvent(TransferEvent.PROGRESS, info);
		// then
		assertTrue(before.isBefore(process.getUpdatedOn()));
	}

	@Test
	public void shouldNotUpdateProgress() throws InterruptedException {
		// given
		TransferSessionInfo info = new TransferSessionInfo(TransferType.STREAM, "title");
		info.bytesTransferred = Long.valueOf(1);
		process.onSendProcessEvent(TransferEvent.PROGRESS, info);
		Instant before = process.getUpdatedOn();
		// when
		Thread.sleep(1000);
		process.onSendProcessEvent(TransferEvent.PROGRESS, info);
		// then
		assertTrue(before.equals(process.getUpdatedOn()));
	}

	@Test
	public void shouldBeFailedOnError() {
		// when
		process.onSendProcessError(new ErrorInfo("error"));
		// then
		assertTrue(process.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldBeCompletedWhenDone() {
		// given
		process.updateStatus(SendProcess.Status.STARTED);
		// when
		process.onSendProcessDone();
		// then
		assertTrue(process.isCompleted());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldGetUniqueName() {
		// given
		// when
		String uniqueName = process.getUniqueName(Arrays.asList("filename", "filename1", "filename2"), "new-filename");
		// then
		assertEquals("new-filename", uniqueName);
		// or when
		uniqueName = process.getUniqueName(Arrays.asList("filename.ext", "filename1.ext", "filename2.ext"), "new-filename.ext");
		// then
		assertEquals("new-filename.ext", uniqueName);
		// or when
		uniqueName = process.getUniqueName(Arrays.asList("filename", "filename1", "filename2"), "filename");
		// then
		assertEquals("filename3", uniqueName);
		// or when
		uniqueName = process.getUniqueName(Arrays.asList("filename.ext", "filename1.ext", "filename2.ext"), "filename.ext");
		// then
		assertEquals("filename3.ext", uniqueName);
		// or when
		uniqueName = process.getUniqueName(Arrays.asList("file.name.ext", "file.name1.ext", "file.name2.ext"), "file.name.ext");
		// then
		assertEquals("file.name3.ext", uniqueName);
		// or when
		uniqueName = process.getUniqueName(Arrays.asList("filename.ext", "filename2.ext", "filename3.ext"), "filename.ext");
		// then
		assertEquals("filename1.ext", uniqueName);
	}

	@Test
	public void shouldBeStopped() throws Exception {
		// when
		process.stop();
		// then
		assertTrue(process.isStopped());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldNotBeStoppedIfCompleted() throws Exception {
		// given
		process.updateStatus(SendProcess.Status.STARTED);
		process.onSendProcessDone();
		// when
		process.stop();
		// then
		assertTrue(process.isCompleted());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldNotBeStoppedIfFailed() throws Exception {
		// given
		process.onSendProcessError(new ErrorInfo("error"));
		// when
		process.stop();
		// then
		assertTrue(process.isFailed());
		assertTrue(callbackCalled);
	}

	@Test
	public void shouldBeConvertedToJson() {
		// given
		process.onSendProcessSpecReceived("packageId");
		// when
		JSONObject json = process.toJson();
		// then
		JSONObject expectedJson = new JSONObject();
		expectedJson.put("id", "packageId");
		expectedJson.put("title", "title");
		expectedJson.put("sender", "sender");
		expectedJson.put("size", 0);
		expectedJson.put("status", "PENDING");
		expectedJson.put("updatedOn", process.getUpdatedOn().toString());
		expectedJson.put("lastEvent", null);
		expectedJson.put("recipients", "recipient1,recipient2");
		expectedJson.put("note", null);
		expectedJson.put("progress", new JSONObject());
		assertEquals(expectedJson.toString(), json.toString());
	}
}