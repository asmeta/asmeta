// Suggested path: code/experimental/asmeta-zeromq/src/main/java/asmeta/asmeta_zeromq/runners/PedestrianRunner.java
package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class Pedestrian {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_pedestrian.properties";
        System.out.println("Starting Pedestrian ASM with config: " + configFile);
        zeroMQW pedestrianInstance = new zeroMQW(configFile);
        pedestrianInstance.setName("PedestrianASM-Thread");
        pedestrianInstance.start();
    }
}