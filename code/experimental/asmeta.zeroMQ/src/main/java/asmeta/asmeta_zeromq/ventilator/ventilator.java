package asmeta.asmeta_zeromq.ventilator;

import asmeta.asmeta_zeromq.zeroMQWA;

public class ventilator {
    
    public static void main(String[] args) {
        zeroMQWA ventilator = new zeroMQWA("/configs/ventilator/zmq_config_ventilator.properties");
        ventilator.run();
    }
}
