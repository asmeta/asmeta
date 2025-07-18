package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class multi {

    public static void main(String[] args) {
        ZeroMQWA multi = new ZeroMQWA("/exampleFAC2023-config/zmq_config_multi.properties");
        multi.run();
    }

}
