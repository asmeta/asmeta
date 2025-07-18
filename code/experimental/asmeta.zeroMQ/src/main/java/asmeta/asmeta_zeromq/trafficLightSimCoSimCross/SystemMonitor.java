// Suggested path: code/experimental/asmeta-zeromq/src/main/java/asmeta/asmeta_zeromq/simulators/SystemMonitor.java
package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class SystemMonitor {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket subscriber = context.createSocket(ZMQ.SUB);

            // Connect to multiple publishers
            System.out.println("Connecting to ASM publishers...");
            subscriber.connect("tcp://localhost:5560"); // controller.asm
            subscriber.connect("tcp://localhost:5561"); // crossManager.asm
            subscriber.connect("tcp://localhost:5562"); // trafficlightA.asm
            subscriber.connect("tcp://localhost:5563"); // trafficlightB.asm
            subscriber.connect("tcp://localhost:5565"); // tram.asm
            subscriber.connect("tcp://localhost:5566"); // pedestrian.asm

            // Subscribe to all messages (empty subscription topic)
            subscriber.subscribe("".getBytes(ZMQ.CHARSET));
            System.out.println("System Monitor started. Listening for messages...");

            while (!Thread.currentThread().isInterrupted()) {
                String message = subscriber.recvStr(0).trim();
                System.out.println("Received: " + message);
            }
        }
    }
}