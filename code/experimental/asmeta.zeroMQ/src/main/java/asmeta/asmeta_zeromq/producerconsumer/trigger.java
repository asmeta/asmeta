package asmeta.asmeta_zeromq.producerconsumer;

import java.util.HashMap;
import java.util.Map;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

public class trigger {

    private final static  Gson gson = new Gson();
    // Topic for the environment-specific messages
    private static final String ENVIRONMENT_TOPIC = "sharedValues";

    public static void main(String[] args) {

        try (ZContext context = new ZContext()) {
            // Publisher 1: For the "trigger" message
            ZMQ.Socket triggerPublisher1 = context.createSocket(SocketType.PUB);
            String triggerPubAddress1 = "tcp://*:5562";
            triggerPublisher1.bind(triggerPubAddress1);
            System.out.println("Trigger PUB socket 1 bound to " + triggerPubAddress1);

            // Publisher 2: For the "sharedValue" message on a specific topic
            ZMQ.Socket sharedValuePublisher = context.createSocket(SocketType.PUB);
            String sharedValuePubAddress = "tcp://*:5563"; // New address for shared value
            sharedValuePublisher.bind(sharedValuePubAddress);
            System.out.println("SharedValue PUB socket 2 bound to " + sharedValuePubAddress);

            // Brief pause to allow subscribers to connect (optional, but can be helpful)
            try {
                Thread.sleep(1000); // Reduced sleep time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Sleep interrupted: " + e.getMessage());
            }

            // Prepare the message data (used for both messages in this example)
            Map<String, String> messageData = new HashMap<>();
			messageData.put("trigger", "1");
            String jsonMessage = gson.toJson(messageData);
            
            // Send "trigger" message via Publisher 1 (no topic)
            triggerPublisher1.send(jsonMessage);
            System.out.println("Sent 'trigger' message to " + triggerPubAddress1 + ": " + jsonMessage);
            

            Thread.sleep(5000);

            // Send "sharedValue" message via Publisher 2 (with shared topic)
            sharedValuePublisher.sendMore("sharedValues");
            messageData.clear();
            messageData.put("sharedValue", "1");
            jsonMessage = gson.toJson(messageData);
            sharedValuePublisher.send(jsonMessage);
            System.out.println("Sent 'sharedValue' message on topic '" + "sharedValues" + "' to " + sharedValuePubAddress + ": " + jsonMessage);
            
            Thread.sleep(5000);

            // Send "trigger" message via Publisher 2 (with trigger topic only for producer)
            sharedValuePublisher.sendMore("trigger");
            messageData.clear();
            messageData.put("trigger", "4");
            jsonMessage = gson.toJson(messageData);
            sharedValuePublisher.send(jsonMessage);
            System.out.println("Sent 'trigger' message on topic '" + "trigger" + "' to " + sharedValuePubAddress + ": " + jsonMessage);

        } catch (Exception e) {
            // Improved error reporting
            System.err.println("An error occurred in the trigger application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
