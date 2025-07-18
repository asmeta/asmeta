package asmeta.asmeta_zeromq.trafficLightSimCoSimCross;

import asmeta.asmeta_zeromq.zeroMQW;

public class Controller {
    public static void main(String[] args) {
        String configFile = "/trafficLightSimCoSimCross-config/zmq_config_controller.properties"; 
        System.out.println("Starting Controller ASM with config: " + configFile);
        zeroMQW controllerInstance = new zeroMQW(configFile);
        controllerInstance.setName("ControllerASM-Thread");
        controllerInstance.start();
    }
}