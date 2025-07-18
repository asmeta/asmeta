package asmeta.asmeta_zeromq.ventilator;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class ventilator {
    
    public static void main(String[] args) {
        ZeroMQWA ventilator = new ZeroMQWA("/ventilator/zmq_config_ventilator.properties");
        ventilator.run();
    }
}
