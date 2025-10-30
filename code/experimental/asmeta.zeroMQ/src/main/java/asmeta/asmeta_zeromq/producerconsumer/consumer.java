package asmeta.asmeta_zeromq.producerconsumer;

import asmeta.asmeta_zeromq.zeroMQWA;



public class consumer {
    public static void main(String[] args) {
        zeroMQWA consumer = new zeroMQWA("/configs/producerconsumer/zmq_config_consumer.properties");
        consumer.run();
    }
}
