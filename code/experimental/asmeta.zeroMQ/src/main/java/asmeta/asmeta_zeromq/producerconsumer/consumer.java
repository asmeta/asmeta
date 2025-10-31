package asmeta.asmeta_zeromq.producerconsumer;

import asmeta.asmeta_zeromq.ZeroMQWA;



public class consumer {
    public static void main(String[] args) {
    	ZeroMQWA consumer = new ZeroMQWA("/configs/producerconsumer/zmq_config_consumer.properties");
        consumer.run();
    }
}
