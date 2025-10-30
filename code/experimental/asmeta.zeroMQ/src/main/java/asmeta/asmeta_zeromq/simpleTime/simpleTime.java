package asmeta.asmeta_zeromq.simpleTime;

import asmeta.asmeta_zeromq.zeroMQWA;

public class simpleTime {
    public static void main(String[] args) {
        zeroMQWA simpleTime = new zeroMQWA("/simpleTime/zmq_config_simpleTime.properties");
        simpleTime.run();
    }
}
