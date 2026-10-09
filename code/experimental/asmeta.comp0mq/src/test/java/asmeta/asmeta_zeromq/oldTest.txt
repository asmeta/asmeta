package asmeta.asmeta_zeromq;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

@SpringBootTest(classes=AsmetaZeromqApplication.class)
class AsmetaZeromqApplicationTests {

	private final Gson gson = new Gson();

	@Test
	void testRunningModelsAction() {
		try (ZContext context = new ZContext()) {
			// request in json format
			// String requestJson = gson.toJson(Map.of("action", "start", "name", "LIFT.asm"));
			// String requestJson = gson.toJson(Map.of("action", "start", "name", "railroadGate.asm"));
			// String requestJson = gson.toJson(Map.of("action", "start", "name", "producer.asm"));
			// String requestJson = gson.toJson(Map.of("action", "running-models"));
			ZMQ.Socket publisher = context.createSocket(ZMQ.PUB);
            publisher.connect("tcp://localhost:5555");

            // Create SUB socket for receiving responses
            ZMQ.Socket subscriber = context.createSocket(ZMQ.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe("".getBytes(ZMQ.CHARSET)); // Subscribe to all messages
            // Can subscribe to specific messages
            // subscriber.subscribe("model-list".getBytes(ZMQ.CHARSET));

            // Wait for the connection
            Thread.sleep(100);

            // Request in JSON format
            // String requestJson = gson.toJson(Map.of("action", "model-list"));
            
            // Map<String, String> monitoredVars = new HashMap<>();
            // monitoredVars.put("lightMon", "OFF");
            // monitoredVars.put("gateMon", "OPENED");
            // monitoredVars.put("event", "GATE");
            // String requestJson = gson.toJson(Map.of(
            //     "action", "step", 
            //     "id", 1, 
            //     "monitoredVariables", monitoredVars
            // ));
            
            // Trigger msg
            Map<String, String> monitored = new HashMap<>();
            monitored.put("trigger", "1");
            String jsonMessage = gson.toJson(monitored);

            publisher.send(jsonMessage);
            System.out.println("Sent: " + jsonMessage);
            
            // Send the request
            // publisher.send(requestJson);
            // System.out.println("Sent request: " + requestJson);

            // Wait for and receive response
            String respJson = subscriber.recvStr();
            System.out.println("Received response: " + respJson);
            Map<?, ?> response = gson.fromJson(respJson, Map.class);

            // Verify response
            // assertTrue(response.containsKey("models"));

			// assertTrue(response.containsKey("id"));
			// assertTrue(response.containsKey("models"));
			// assertTrue(response.containsKey("runOutput"));
			// assertTrue(response.containsKey("monitored"));
			assertTrue(response.containsKey("controlledValues"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
