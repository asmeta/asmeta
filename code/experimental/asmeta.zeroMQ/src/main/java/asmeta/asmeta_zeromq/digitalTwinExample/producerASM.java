package asmeta.asmeta_zeromq.digitalTwinExample;

import asmeta.asmeta_zeromq.zeroMQWA;

public class producerASM {
    public static void main(String[] args) {
        zeroMQWA producerASM = new zeroMQWA("/configs/digitalTwinExample/zmq_config_producer.properties");
        producerASM.run();
    }
}