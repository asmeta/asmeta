package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.zeroMQWA;

public class inc {

    public static void main(String[] args) {
        zeroMQWA inc = new zeroMQWA("/configs/exampleFAC2023-config/zmq_config_inc.properties");
        inc.run();
    }

}
