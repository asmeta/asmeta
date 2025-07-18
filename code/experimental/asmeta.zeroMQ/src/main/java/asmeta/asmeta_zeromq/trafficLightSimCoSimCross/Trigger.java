package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import java.util.HashMap;
import java.util.Map;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import com.google.gson.Gson;

public class Trigger {

    // Ports for external inputs
    private static final String CONTROLLER_PORT      = "5570"; // transitionC
    private static final String PEDESTRIAN_PORT      = "5540"; // newPedestrianComing
    private static final String TRAM_REQUEST_PORT    = "5541"; // newTramComing
    private static final String TRAM_SIGNAL_PORT     = "5571"; // controllerTramSignal

    public static void main(String[] args) throws InterruptedException {
        try (ZContext context = new ZContext()) {
            // Create PUB sockets
            ZMQ.Socket pubCtrl    = context.createSocket(SocketType.PUB);
            pubCtrl.bind("tcp://*:" + CONTROLLER_PORT);

            ZMQ.Socket pubPed     = context.createSocket(SocketType.PUB);
            pubPed.bind("tcp://*:" + PEDESTRIAN_PORT);

            ZMQ.Socket pubTramReq = context.createSocket(SocketType.PUB);
            pubTramReq.bind("tcp://*:" + TRAM_REQUEST_PORT);

            ZMQ.Socket pubTramSig = context.createSocket(SocketType.PUB);
            pubTramSig.bind("tcp://*:" + TRAM_SIGNAL_PORT);

            // Allow subscribers to connect
            Thread.sleep(1000);

            Gson gson = new Gson();
            Map<String, String> msg;
            String json;

            // 1) TURN_ON controller
            msg = new HashMap<>();
            msg.put("transitionC", "TURN_ON");
            msg.put("mCurrTimeSecs", "0");
            json = gson.toJson(msg);
            send(pubCtrl, json);
            Thread.sleep(1000);

            // 2) OPERATE_T controller
            msg = new HashMap<>();
            msg.put("transitionC", "OPERATE_T");
            json = gson.toJson(msg);
            send(pubCtrl, json);
            Thread.sleep(1000);

            // 3) Pedestrian request
            msg = new HashMap<>();
            msg.put("newPedestrianComing", "true");
            json = gson.toJson(msg);
            send(pubPed, json);
            Thread.sleep(5000);

            // 4) Tram request
            msg = new HashMap<>();
            msg.put("newTramComing", "true");
            json = gson.toJson(msg);
            send(pubTramReq, json);
            Thread.sleep(5000);

            // Keep sockets open for late subscribers
            Thread.sleep(2000);

            pubCtrl.close();
            pubPed.close();
            pubTramReq.close();
            pubTramSig.close();
        }
        System.out.println("Trigger sequence completed.");
    }

    private static void send(ZMQ.Socket socket, String message) {
        socket.send(message);
        System.out.println("[" + java.time.LocalTime.now() + "] Sent: " + message);
    }
}
