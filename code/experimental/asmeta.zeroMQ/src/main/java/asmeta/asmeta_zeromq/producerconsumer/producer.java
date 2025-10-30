package asmeta.asmeta_zeromq.producerconsumer;

import asmeta.asmeta_zeromq.zeroMQWA;

public class producer {

    public static void main(String[] args) {
        zeroMQWA producer = new zeroMQWA("/configs/producerconsumer/zmq_config_producer.properties");
        producer.run();
    }

}
