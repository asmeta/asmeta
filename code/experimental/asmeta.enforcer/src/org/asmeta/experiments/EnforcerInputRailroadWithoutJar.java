package org.asmeta.experiments;

import java.util.Map;
import java.util.HashMap;

import org.asmeta.java_models.Command;
import org.asmeta.java_models.RailroadGate_v2;
import org.asmeta.runtime_container.*;

public class EnforcerInputRailroadWithoutJar {
	
	private static String modelName=".\\ASMmodels\\railroadGateMonitored.asm";	
	//private static IModelExecution simu; //mi farebbe importare altre 2 eccezioni da asmeta.simulator che richiedono il linking quindi al progetto simulator
	private static SimulationContainer simu;
	
	public static int fakeBuilder() {
		simu = SimulationContainer.getInstance();
		int resu = simu.init(1);
		simu.startExecution(modelName);
		return resu;
	}
	
	public static boolean enforcingInput(Command inpu) {
		Map<String,String> imap = new HashMap<String,String>();
		switch (inpu) {
			case CLOSED:
				imap.put("lightMon", "OFF");
				imap.put("gateMon", "CLOSED");
				imap.put("event", "GATE");
				break;
			case CLOSING:
				imap.put("lightMon", "OFF");
				imap.put("gateMon", "CLOSING");
				imap.put("event", "GATE");
				break;
			case FLASH:
				imap.put("lightMon", "FLASHING");
				imap.put("gateMon", "CLOSED");
				imap.put("event", "LIGHT");
				break;
			case OFF:
				imap.put("lightMon", "OFF");
				imap.put("gateMon", "CLOSED");
				imap.put("event", "LIGHT");
				break;
			case OPENED:
				imap.put("lightMon", "OFF");
				imap.put("gateMon", "OPENED");
				imap.put("event", "GATE");
				break;
			case OPENING:
				imap.put("lightMon", "OFF");
				imap.put("gateMon", "OPENING");
				imap.put("event", "GATE");
				break;
		}
		RunOutput outp = simu.runStep(1, imap);
		if (outp.equals(new RunOutput(Esit.SAFE, "ok")))
			return true;
		else
			return false;
	}	
	
	/*public static Map<String,String> inputConverter(Command comm) {
		Map<String,String> conv = new HashMap<String,String>();
		switch (comm) {
		case CLOSED:
			break;
		case CLOSING:
			break;
		case FLASH:
			break;
		case OFF:
			break;
		case OPENED:
			break;
		case OPENING:
			break;
		}
		return conv;
	}*/
	private static void stamp(RailroadGate_v2 mod, int i) {
		System.out.println("Command "+i+": Light: "+mod.light+" Gate: "+mod.gate+"===============");
	}
	public static void main(String[] args) {
		Command input;
		fakeBuilder();
		RailroadGate_v2 RGOK = new RailroadGate_v2();
		RailroadGate_v2 RGNOTOK = new RailroadGate_v2();
		//run ok
		stamp(RGOK,0);
		input=Command.FLASH;
		if (enforcingInput(input))
			RGOK.exec(input);
		stamp(RGOK,1);
		input=Command.CLOSING;
		if (enforcingInput(input))
			RGOK.exec(input);
		stamp(RGOK,2);
		input=Command.CLOSED;
		if (enforcingInput(input))
			RGOK.exec(input);
		stamp(RGOK,3);
		//run non OK
		simu.stopExecution(1);
		simu.startExecution(modelName);
		stamp(RGNOTOK,0);
		input=Command.FLASH;
		if (enforcingInput(input))
			RGNOTOK.exec(input);
		stamp(RGNOTOK,1);
		input=Command.CLOSING;
		if (enforcingInput(input))
			RGNOTOK.exec(input);
		stamp(RGNOTOK,2);
		input=Command.OFF;
		if (enforcingInput(input))
			RGNOTOK.exec(input);
		stamp(RGNOTOK,3);
	}

}
