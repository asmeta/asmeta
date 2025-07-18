package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class TrafficLightB {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_trafficlightB.properties";
        System.out.println("Starting TrafficLightB ASM with config: " + configFile);
        zeroMQW trafficLightBInstance = new zeroMQW(configFile);
        trafficLightBInstance.setName("TrafficLightB-ASM-Thread");
        trafficLightBInstance.start();
    }
}