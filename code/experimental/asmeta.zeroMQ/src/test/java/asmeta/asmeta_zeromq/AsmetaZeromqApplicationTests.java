package asmeta.asmeta_zeromq;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class AsmetaZeromqApplicationTests {

	private static final String TRIGGER_PUB_ADDRESS = "tcp://*:5561";
	private static final String RESPONSE_SUB_ADDRESS = "tcp://localhost:5556";

	private final Gson gson = new Gson();
	private final Type mapStringObjectype = new TypeToken<Map<String, Object>>(){}.getType();

	@Test
	@Timeout(10)
	void testProducerTriggerAction() {
		System.out.println("Starting testProducerTriggerAction...");
		try (ZContext context = new ZContext()) {
			Socket triggerPublisher = context.createSocket(SocketType.PUB);
			System.out.println("TEST: Attempting to bind trigger PUB socket to " + TRIGGER_PUB_ADDRESS);
			triggerPublisher.bind(TRIGGER_PUB_ADDRESS);
			System.out.println("TEST: Trigger PUB socket bound to " + TRIGGER_PUB_ADDRESS);

			Socket responseSubscriber = context.createSocket(SocketType.SUB);
			System.out.println("TEST: Attempting to connect response SUB socket to " + RESPONSE_SUB_ADDRESS);
			responseSubscriber.connect(RESPONSE_SUB_ADDRESS);
			responseSubscriber.subscribe("".getBytes(ZMQ.CHARSET));
			System.out.println("TEST: Response SUB socket connected to " + RESPONSE_SUB_ADDRESS);

			System.out.println("TEST: Waiting a moment for sockets to establish connections...");
			Thread.sleep(2000);

			Map<String, String> triggerMsg = new HashMap<>();
			triggerMsg.put("trigger", "1");
			String jsonMessage = gson.toJson(triggerMsg);

			System.out.println("TEST: Sending trigger message: " + jsonMessage + " to " + TRIGGER_PUB_ADDRESS);
			triggerPublisher.send(jsonMessage);

			System.out.println("TEST: Waiting for response from producer...");
			String respJson = responseSubscriber.recvStr(ZMQ.DONTWAIT);
			long startTime = System.currentTimeMillis();
			long maxWaitTimeMillis = 5000;

			while (respJson == null && (System.currentTimeMillis() - startTime < maxWaitTimeMillis)) {
				Thread.sleep(100);
				respJson = responseSubscriber.recvStr(ZMQ.DONTWAIT);
			}

			System.out.println("TEST: Finished waiting loop.");

			assertNotNull(respJson, "Did not receive a response from the producer within timeout.");
			System.out.println("TEST: Received response: " + respJson);

			Map<String, Object> responseMap = gson.fromJson(respJson, mapStringObjectype);
			assertNotNull(responseMap, "Response JSON could not be parsed into a Map.");

			assertTrue(responseMap.containsKey("outputValues") || responseMap.containsKey("asm_status"),
					   "Response map did not contain expected keys ('outputValues' or 'asm_status')");

			System.out.println("TEST: Assertions passed.");

		} catch (org.zeromq.ZMQException e) {
			 System.err.println("TEST FAILED: ZMQException during setup/run: " + e.getMessage());
			 e.printStackTrace();
			 if (e.getErrorCode() == ZMQ.Error.EADDRINUSE.getCode()) {
				 fail("Test failed due to ZMQException: Address already in use (Code: " + e.getErrorCode() + "). Ensure port " + TRIGGER_PUB_ADDRESS + " is free.");
			 } else {
				 fail("Test failed due to ZMQException: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
			 }
		} catch (Exception e) {
			System.err.println("TEST FAILED: Test failed with exception:");
			e.printStackTrace();
			fail("Test threw an exception: " + e.getMessage());
		} finally {
			 System.out.println("Finished testProducerTriggerAction.");
		}
	}

}