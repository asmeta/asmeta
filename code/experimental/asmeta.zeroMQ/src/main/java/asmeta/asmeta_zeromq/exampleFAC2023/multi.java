package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.zeroMQWA;

public class multi {

    public static void main(String[] args) {
        zeroMQWA multi = new zeroMQWA("/configs/exampleFAC2023-config/zmq_config_multi.properties");
        multi.run();
    }

}
