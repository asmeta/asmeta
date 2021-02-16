package org.asmeta.framework.enforcerAirConditioner;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.auxiliary.Utility;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;	
public class EnforcerAirConditioner_v2 extends Enforcer{

	private static long SIM_TIMEOUT;	//solamente perchè nella superclasse è privata ma mi serve ad ogni run.
	private int SIM_AIRSPEED=0;
	private String TEMPERATURE="0";
	public EnforcerAirConditioner_v2() {
		super();
		//Read the pathname of the runtime model and the simulation timeout value from the configuration file
	    String RUNTIME_MODEL_PATH = Utility.getProperty("RUNTIME_MODEL_PATH");
	    
	    SIM_TIMEOUT = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIMEOUT")));
	    //IL TIMEOUT NELLE PROPERTIES è IN SECONDI MA NELLA FUNZIONE è IN MS
	    SIM_TIMEOUT*=1000;
	    
	    //Initialize the AsmetaS@run.time model engine
		setModelEngine(new SimulationContainer());
		getModelEngine().init(1);
		int result = getModelEngine().startExecution(RUNTIME_MODEL_PATH);
		if (result < 0) 
			System.err.println("ERROR: Simulation engine not initialized for the model "+ RUNTIME_MODEL_PATH);
		else {
			System.out.println("Model engine initialized for "+ RUNTIME_MODEL_PATH + " with simulation step timeout " + SIM_TIMEOUT + " seconds");
		}
	}
	
	public EnforcerAirConditioner_v2(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
	}

	@Override
	public boolean sanitiseInput(String inputValues) {
		TEMPERATURE=inputValues.trim();
		RunOutput result = getModelEngine().runStepTimeout(1, prepareInput(), (int)SIM_TIMEOUT);
		return result.getEsit()==Esit.UNSAFE;
	}

	@Override
	public boolean sanitiseOutput(String outputValues) {
		//int OLD_AIRSPEED=SIM_AIRSPEED;
		SIM_AIRSPEED = Integer.parseInt(((outputValues.split(":"))[1]).trim());
		RunOutput result = getModelEngine().runStepTimeout(1, prepareInput(), (int)SIM_TIMEOUT);
		
		/*if (result.getEsit()==Esit.UNSAFE) {	//funziona quando inv_b non è commentato
			SIM_AIRSPEED=OLD_AIRSPEED;
			return true;
		}else
			return false;*/
		boolean check=true;
		int simAirSpeed=Integer.parseInt(result.getControlledvalues().get("airSpeed").trim());
		check = simAirSpeed != SIM_AIRSPEED; // perchè il modello utilizato ha l'invariante inv_b commentato in quanto è predisposto
		SIM_AIRSPEED = simAirSpeed;			 // per input adaptation: lo sfrutto con un confronto per capire se l'out è da filtrare o no.
		return check;
	}
	
	// create an output object from the knowledge which can be used as input object for the ASM runtime model 
	public Map<String, String> prepareInput() {	
				Map<String, String> data = new HashMap<>();
				data.put("temperature", TEMPERATURE);
				data.put("airIntensity", Integer.toString(SIM_AIRSPEED));	//avrei bisogno della knowledge
				return data;
		}

}
