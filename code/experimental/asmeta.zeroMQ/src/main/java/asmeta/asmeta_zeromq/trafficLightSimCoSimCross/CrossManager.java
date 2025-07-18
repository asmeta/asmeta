// Suggested path: code/experimental/asmeta-zeromq/src/main/java/asmeta/asmeta_zeromq/trafficLightSimCoSimCross/CrossManager.java
package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class CrossManager {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_crossManager.properties";
        System.out.println("Starting CrossManager ASM with config: " + configFile);
        zeroMQW crossManagerInstance = new zeroMQW(configFile);
        crossManagerInstance.setName("CrossManagerASM-Thread");
        crossManagerInstance.start();
    }
}