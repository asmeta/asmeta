package asmeta.asmeta_zeromq.digitalTwinExample;

import asmeta.asmeta_zeromq.zeroMQWA;

public class consumerASM {
    public static void main(String[] args) {
        zeroMQWA consumerASM = new zeroMQWA("/configs/digitalTwinExample/zmq_config_consumer.properties");
        consumerASM.run();
    }
}
