package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class TrafficLightA {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_trafficlightA.properties";
        System.out.println("Starting TrafficLightA ASM with config: " + configFile);
        zeroMQW trafficLightAInstance = new zeroMQW(configFile);
        trafficLightAInstance.setName("TrafficLightA-ASM-Thread");
        trafficLightAInstance.start();
    }
}