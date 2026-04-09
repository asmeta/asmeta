/*package asmeta.asmeta_zeromq.digitalTwinExample;

import asmeta.asmeta_zeromq.ZeroMQWA;

public class producerASM {
    public static void main(String[] args) {
    	ZeroMQWA producerASM = new ZeroMQWA("/configs/digitalTwinExample/zmq_config_digitaltwin_unified.properties");
        producerASM.run();
    }
}
*/
package asmeta.asmeta_zeromq.digitalTwinExample;
import asmeta.asmeta_zeromq.ZeroMQWA;

public class producerASM {
    public static void main(String[] args) {
    	ZeroMQWA producerASM = new ZeroMQWA("/configs/digitalTwinExample/zmq_config_digitaltwin_unified.properties", "producer");
    	producerASM.run();

    }
}
