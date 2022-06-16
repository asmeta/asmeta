/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.mvm;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.managedSystem.Effector;
import org.asmeta.framework.managedSystem.ManagedSystem;
import org.asmeta.framework.managedSystem.Probe;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;


//Component (handle) for the causal connection with the Arduino prototype of the MVM
public class MVM extends ManagedSystem implements Probe, Effector{

	 /** Runtime model simulator*/
    SimulationContainer modelEngine;
    private int id; //model simulation identifier
    private Map<String, String> currentState; //system current state (controlled, out and monitored locations)
    private Map<String, String> moncurrentState; //system monitored current state (only monitored locations)
    private String SYSTEM_MODEL_PATH;
	
    public MVM(String model_path) { 	
	    //System.out.println("Trying to initialize a simulation engine for "+ SYSTEM_MODEL_PATH);	       
	    //Initialize an AsmetaS@run.time model engine instance for the runtime system model (the simulated managed system!) 
		modelEngine = new SimulationContainer();
		modelEngine.init(1);
		currentState = new HashMap<>();
		moncurrentState = new HashMap<>();
		moncurrentState.putAll(this.prepareInput("adc_reply_m RESPONSE cmdExPause false cmdInPause false cmdRm false comp_lung 0.06 dropPAW_ITS_ASV false dropPAW_ITS false fan_working_m true flowDropPSV false flowDropASV false limPressure 45.0 mCurrTimeSecs 1 pawGTMaxPinsp false peep_in 5.0 pi_6_m 25 pi_6_reply_m RESPONSE rcexp_in 0.9 respirationMode PCV respirationMode_doc PCV startupEnded false selfTestPassed false startVentilation false stopRequested false temperature_m 15 vol_eff 0.9 volMinPerc 100.0 weight 60.0 al_bit NONE status_selftest NOTSTART watchdog_st INACTIVE"));
		SYSTEM_MODEL_PATH = model_path;
		int result = modelEngine.startExecution(SYSTEM_MODEL_PATH);
		if (result < 0) 
			System.err.println("ERROR: Simulation engine not initialized for <"+ SYSTEM_MODEL_PATH+">");
		else {
			id = result;
		    //System.out.println(modelEngine.toString()+"Simulation engine initialized for "+ SYSTEM_MODEL_PATH + " with simulation id " + id);
		}
	}
    
	public Map<String, String> run (String s) {
		Map<String, String> input = new HashMap<String, String>(moncurrentState); //To not loose unset location values OLD: prepareInput(s);
		input.putAll(prepareInput(s)); 
		//System.out.println (input.toString());
		RunOutput result = modelEngine.runStep(id, input);
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
		//result.getResult(); //Timeout expired or not
		if (result.getEsit() == Esit.SAFE) {
			System.out.println("Previous Controller monitored state: "+ moncurrentState.toString());
		    //store the new output location values as computed by the ASM into the output maps
			moncurrentState.putAll(prepareInput(s)); //Add monitored locations
			System.out.println("Controller monitored state: "+ moncurrentState.toString());
			currentState = input; //Add monitored locations
			currentState.putAll(result.getControlledvalues()); //Add output values from the ASM model
			System.out.println("User input: "+ s);
			System.out.println("Controller monitored state: "+ moncurrentState.toString());
			System.out.println("Controller state: "+ getOutput().toString());
      	    // Da rimuovere ??? //System.out.println("Output to patient: "+ getOutputToPatient().toString());
		}
		else 
			 System.out.println("Error: something got wrong with the outcome of the simulated system <"+ SYSTEM_MODEL_PATH+">");
		
		return currentState;

	}
	
	 //overloading
		public Map<String, String> run (Map<String, String> userinput) {
			Map<String, String> input = new HashMap<String, String>(moncurrentState); //OLD: userinput
			input.putAll(userinput); //To not loose unset location values OLD: prepareInput(s);
			RunOutput result = modelEngine.runStep(id, input);
			if (result.getEsit() == Esit.SAFE) {
			    //store the new output location values as computed by the ASM into the output maps
				currentState = input; //Add monitored locations
				//System.out.println("Previous PillBox monitored state: "+ moncurrentState.toString()); //for debugging
				//System.out.println("User input: "+ userinput.toString());
				moncurrentState.putAll(userinput); //Add monitored locations
				//System.out.println("PillBox monitored state: "+ moncurrentState.toString());  //for debugging
				currentState.putAll(result.getControlledvalues()); //Add output values from the ASM model
				//output = prepareOutput(currentState);
				//output = prepareOutput(result.getControlledvalues());
				//System.out.println("PillBox monitored state: "+ moncurrentState.toString());  //for debugging
				//System.out.println("PillBox state: "+ getOutput().toString());  //for debugging
	      	    System.out.println("Output to patient: "+ getOutputToPatient().toString());
				
			}
			else 
				 System.out.println("Error: something got wrong with the outcome of the ASM-based simulated system <"+ SYSTEM_MODEL_PATH +">\"");
			
			return currentState;

		}
   
		private Map<String, String> prepareOutput(Map<String, String> locations) {
			//TODO: Filter only the output locations; it should be done in a general manner in the ASM simulator@run.time
			//Currently, it returns everything
			//Map<String, String> output = new HashMap<>();
			//System.out.println("Pillbox Output: "+locations.toString());// for debugging purposes
			return locations;
			//return output; 
		}
		
	private Map<String, String> prepareInput(String cmd) {	
		Map<String, String> data = new HashMap<>();
		String[] input = cmd.split(" ");
		for(int i=0; i<input.length; i+=2)
		data.put(input[i],input[i+1]); //put monitored value (key,value)  
		return data;
    }

	//Overall current state of the Controller
	public Map<String, String> getOutput() {
		return prepareOutput(currentState);
	}
	
	public Map<String, String> getOutputForProbing() {
		Map<String, String> tmp = new HashMap<>();
		//iterating over keys only and selects those starting with ...
	    //out del controller e monitored del supervisor
		for (String key : currentState.keySet()) {
			if (key.startsWith("adc_reply")
	        	|| key.startsWith("all_cont")
	        	|| key.startsWith("breath_sync")
	        	|| key.startsWith("compliance")
	        	|| key.startsWith("current_volume")
	        	|| key.startsWith("dropPAW_ITS_sup")
	        	|| key.startsWith("enter_self")
	        	|| key.startsWith("exit_self")
	        	|| key.startsWith("fan_working")
	        	|| key.startsWith("iValve")
	        	|| key.startsWith("limit_pa")
	        	|| key.startsWith("mCurrSecs")
	        	|| key.startsWith("oValve")
	        	|| key.startsWith("peep")
	        	|| key.startsWith("pi_6")
	        	|| key.startsWith("pi_6_reply")
	        	|| key.startsWith("rcexp")
	        	|| key.startsWith("respirationMode_sup")
	        	|| key.startsWith("run_command")
	        	|| key.startsWith("snooze")
	        	|| key.startsWith("stop_command")	
	        	|| key.startsWith("temperature")
	        	|| key.startsWith("vol_min_perc")
	        	|| key.startsWith("watchdog")
	        	|| key.startsWith("weight_sup")	
	        	)
	        	tmp.put(key,currentState.get(key));
		}
		return tmp;
	}
	
	//Output to patient O={outMess,redLed}
	//out outMess: Compartment -> String
	//out redLed: Compartment -> LedLights 
	public Map<String, String>  getOutputToPatient() {
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those starting with "iValve" or "oValve" or "state"
		    for (String key : currentState.keySet()) {
		        if (key.startsWith("iValve") || key.startsWith("oValve") || key.startsWith("state")) //|| key.startsWith("logMess")) 
		        	tmp.put(key,currentState.get(key));	
		    }
			return tmp;		
		}
	
	@Override
	public Probe getProbe() { 
		return this; 
	}
	
	@Override
	public Effector getEffector() {
		return this;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		modelEngine.stopExecution(id);
		return false;
	}
	
}
