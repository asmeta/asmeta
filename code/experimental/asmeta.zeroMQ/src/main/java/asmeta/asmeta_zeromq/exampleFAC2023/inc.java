package asmeta.asmeta_zeromq.exampleFAC2023;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class inc {

    public static void main(String[] args) {
    	ZeroMQWA inc = new ZeroMQWA("/configs/exampleFAC2023-config/zmq_config_inc.properties");
        inc.run();
    }

}
