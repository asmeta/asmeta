package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class dec {

    public static void main(String[] args) {
        ZeroMQWA dec = new ZeroMQWA("/exampleFAC2023-config/zmq_config_dec.properties");
        dec.run();
    }

}
