package asmeta.asmeta_zeromq.simpleTime;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class simpleTime {
    public static void main(String[] args) {
        ZeroMQWA simpleTime = new ZeroMQWA("/simpleTime/zmq_config_simpleTime.properties");
        simpleTime.run();
    }
}
