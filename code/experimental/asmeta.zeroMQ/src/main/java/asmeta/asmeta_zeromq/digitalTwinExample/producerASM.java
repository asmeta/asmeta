package asmeta.asmeta_zeromq.digitalTwinExample;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class producerASM {
    public static void main(String[] args) {
        ZeroMQWA producerASM = new ZeroMQWA("/digitalTwinExample/zmq_config_producer.properties");
        producerASM.run();
    }
}