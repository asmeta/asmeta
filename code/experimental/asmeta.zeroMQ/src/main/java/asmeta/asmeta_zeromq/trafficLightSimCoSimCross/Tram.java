package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class Tram {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_tram.properties"; 
        System.out.println("Starting Tram ASM with config: " + configFile);
        zeroMQW tramInstance = new zeroMQW(configFile);
        tramInstance.setName("TramASM-Thread");
        tramInstance.start();
    }
}