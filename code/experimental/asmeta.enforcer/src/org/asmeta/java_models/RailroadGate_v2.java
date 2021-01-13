package org.asmeta.java_models;

/*import org.asmeta.monitoring.Asm;
import org.asmeta.monitoring.FieldToFunction;
import org.asmeta.monitoring.FieldToLocation;
import org.asmeta.monitoring.RunStep;
import org.asmeta.monitoring.StartMonitoring;*/

/**
 * It implements a railroad gate (see "Run-Time Verification" S. Colin and L. Mariani, pag. 529).
 * "It is composed of a gate and a light. The light can flash or be off. The gate
 * can be closed, opened, closing or opening. First the gate is open. Before the
 * door closes, the light flashes to warn the motorists of the imminent closing
 * of the door. The light continues to flash while the door closes until the door
 * is again opened."
 * 
 *
 */
//@Asm(asmFile = "models/railroadGate.asm")
//@Asm(asmFile = "models/railroadGate_v2.asm")
public class RailroadGate_v2 {
	//@FieldToFunction(func = "light")
	//@FieldToLocation(func = "light")
	public LightState light;
	//@FieldToFunction(func = "gate")
	//@FieldToLocation(func = "gate")
	public GateState gate;

	//@StartMonitoring
	public RailroadGate_v2() {
		light = LightState.OFF;
		gate = GateState.OPENED;
	}

	//@RunStep
	public void exec(Command command) {
		switch(command) {
			case FLASH:
				light = LightState.FLASH;
				break;
			case OFF:
				light = LightState.OFF;	
				break;
			case CLOSED:
				gate = GateState.CLOSED;	
				break;
			case OPENED:
				gate = GateState.OPENED;	
				break;
			case CLOSING:
				gate = GateState.CLOSING;	
				break;
			case OPENING:
				gate = GateState.OPENING;	
				break;
		}
		//System.out.println("Ricevuto comando " + command + "\ngate = " + gate + "\nlight = " + light + "\n");
	}
}