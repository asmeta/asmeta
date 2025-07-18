package asmeta.asmeta_zeromq;



public class Starter {
    
    public static void main(String[] args) {
        // zeroMQW producer = new zeroMQW("/zmq_config.properties");
        // zeroMQW consumer = new zeroMQW("/zmq_config_consumer.properties");
        

        // Execution example: myinput=1, funcDec=0 -> Expected output in the first cycle: funcMulti=2, funcInc=4, funcDec=5
        // Multi: myinput (1) * 2 = 2 ---- funcMulti=2
        // Inc: funcMulti (2) + funcDec (0) + 2 = 4 ---- funcInc=4
        // Dec: funcMulti (2) + funcInc (4) - 1 = 5 ---- funcDec=5
        // zeroMQW wrapperInc = new zeroMQW("/exampleFAC2023-config/zmq_config_inc.properties");
        // wrapperInc.runLoop();
        // zeroMQW wrapperDec = new zeroMQW("/exampleFAC2023-config/zmq_config_dec.properties");
        // wrapperDec.runLoop();
        // zeroMQW wrapperMulti = new zeroMQW("/exampleFAC2023-config/zmq_config_multi.properties");
        // wrapperMulti.runLoop();

        System.out.println("Wrappers started");
    }
}
