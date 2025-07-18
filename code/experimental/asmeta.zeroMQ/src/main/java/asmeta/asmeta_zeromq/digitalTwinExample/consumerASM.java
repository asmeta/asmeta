package asmeta.asmeta_zeromq.digitalTwinExample;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class consumerASM {
    public static void main(String[] args) {
        ZeroMQWA consumerASM = new ZeroMQWA("/digitalTwinExample/zmq_config_consumer.properties");
        consumerASM.run();
    }
}
