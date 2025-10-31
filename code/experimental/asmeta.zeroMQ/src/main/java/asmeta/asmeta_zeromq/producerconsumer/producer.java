package asmeta.asmeta_zeromq.producerconsumer;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class producer {

    public static void main(String[] args) {
    	ZeroMQWA producer = new ZeroMQWA("/configs/producerconsumer/zmq_config_producer.properties");
        producer.run();
    }

}
