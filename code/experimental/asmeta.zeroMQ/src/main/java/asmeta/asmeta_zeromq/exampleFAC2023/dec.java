package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.zeroMQWA;

public class dec {

    public static void main(String[] args) {
        zeroMQWA dec = new zeroMQWA("/configs/exampleFAC2023-config/zmq_config_dec.properties");
        dec.run();
    }

}
