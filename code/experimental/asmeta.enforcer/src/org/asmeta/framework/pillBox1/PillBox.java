/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.pillBox1;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


import org.asmeta.framework.managedSystem.*;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class PillBox extends ManagedSystem implements Probe, Effector{
	//Java wrapper of the simulated Pillbox system (an ASM model)
	
	 /** Runtime model simulator*/
    SimulationContainer modelEngine;
    private int id; //model simulation identifier
    private Map<String, String> currentState; //system current state (controlled, out and monitored locations)
    private Map<String, String> moncurrentState; //system monitored current state (only monitored locations)
    private String SYSTEM_MODEL_PATH;
 
	public PillBox(String model_path) {
	
	    //System.out.println("Trying to initialize a simulation engine for "+ SYSTEM_MODEL_PATH);	       
	    //Initialize an AsmetaS@run.time model engine instance for the runtime system model (the simulated managed system!) 
		modelEngine = new SimulationContainer();
		modelEngine.init(1);
		currentState = new HashMap<>();
		moncurrentState = new HashMap<>();
		moncurrentState.putAll(this.prepareInput("setNewTime(compartment1) false setNewTime(compartment2) false skipNextPill(compartment1) false skipNextPill(compartment2) false setOriginalTime(compartment1) false setOriginalTime(compartment2) false"));
		SYSTEM_MODEL_PATH = model_path;
		int result = modelEngine.startExecution(SYSTEM_MODEL_PATH);
		if (result < 0) 
			System.err.println("ERROR: Simulation engine not initialized for <"+ SYSTEM_MODEL_PATH+">");
		else {
			id = result;
		    //System.out.println(modelEngine.toString()+"Simulation engine initialized for "+ SYSTEM_MODEL_PATH + " with simulation id " + id);
		}
	}

	//Make an ASM evaluation step from the monitored input and returns the output location values
	//User input command syntax example: 
    //openSwitch(compartment1) false openSwitch(compartment2) false systemTime 414 
	public Map<String, String> run (String s) {
		Map<String, String> input = new HashMap<String, String>(moncurrentState); //To not loose unset location values OLD: prepareInput(s);
		input.putAll(prepareInput(s)); 
		//System.out.println (input.toString());
		RunOutput result = modelEngine.runStep(id, input);
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
		//result.getResult(); //Timeout expired or not
		if (result.getEsit() == Esit.SAFE) {
			System.out.println("Previous PillBox monitored state: "+ moncurrentState.toString());
		    //store the new output location values as computed by the ASM into the output maps
			moncurrentState.putAll(prepareInput(s)); //Add monitored locations
			System.out.println("PillBox monitored state: "+ moncurrentState.toString());
			currentState = input; //Add monitored locations
			currentState.putAll(result.getControlledvalues()); //Add output values from the ASM model
			System.out.println("User input: "+ s);
			System.out.println("PillBox monitored state: "+ moncurrentState.toString());
			System.out.println("PillBox state: "+ getOutput().toString());
      	    System.out.println("Output to patient: "+ getOutputToPatient().toString());
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
	
	/*
	monitored systemTime: Natural //Time in minutes since midnight
	monitored setNewTime: Compartment -> Boolean //when pill is missed if true set new time, otherwise reset original time
	monitored newTime: Compartment -> Natural //new time when pill is missed
	monitored skipNextPill: Prod(Compartment,Compartment) -> Boolean //pill taken with delay if next pill causes minToInterfer violation is true
	User input: 	dynamic monitored openSwitch: Compartment -> Boolean
	**/
	//Input command syntax example: 
	//openSwitch(compartment2) false openSwitch(compartment3) false set openSwitch(compartment4) true systemTime 414 
	// Split the input cmd by whitespace and create an input object to be used for the ASM system model; note that input
	//can be partial, missing monitored functions are 
	private Map<String, String> prepareInput(String cmd) {	
					Map<String, String> data = new HashMap<>();
					String[] input = cmd.split(" ");
					for(int i=0; i<input.length; i+=2)
					data.put(input[i],input[i+1]); //put monitored value (key,value)  
					return data;
	}
	
	
	//Overall current state of the PillBox
	public Map<String, String> getOutput() {
		return prepareOutput(currentState);
	}
	
	//Output to patient O={outMess,redLed}
	//out outMess: Compartment -> String
	//out redLed: Compartment -> LedLights 
	public Map<String, String>  getOutputToPatient() {
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those starting with "outMess" or "redLed"
		    for (String key : currentState.keySet()) {
		        if (key.startsWith("outMess") || key.startsWith("redLed")) //|| key.startsWith("logMess")) 
		        	tmp.put(key,currentState.get(key));	
		    }
			return tmp;
			
		}

	//Output for probing
	public Map<String, String>  getOutputForProbing() {
		Map<String, String> tmp = new HashMap<>();
		//iterating over keys only and selects those starting with ...
	    for (String key : currentState.keySet()) {
	        if (key.startsWith("isPillMissed") || key.startsWith("pillTakenWithDelay") || key.startsWith("actual_time_consumption") ||
	        	key.startsWith("drugIndex") || key.startsWith("name") || key.startsWith("time_consumption") ||
	        	key.startsWith("redLed") || key.startsWith("systemTime") || key.startsWith("day")) {
	        	/*if (key.startsWith("name") )
	        		tmp.put(key,"\""+currentState.get(key)+"\"");
	            else tmp.put(key,currentState.get(key));	*/
	        	tmp.put(key,currentState.get(key));
	        }
	    }
	    //Alcuni segnali nel currentState del modello del PillBox risultano obsoleti come systemTime e/o mancanti 
	    //Patrizia TODO: da sostituire con qualcosa di pulito per recuperare i segnali mancanti
	    //Additional values for MPSafePillbox2 if missing
        if (! tmp.containsKey("isPillMissed(compartment1)")) tmp.put("isPillMissed(compartment1)","false");
        if (! tmp.containsKey("isPillMissed(compartment2)")) tmp.put("isPillMissed(compartment2)","false");
        //if (! tmp.containsKey("isPillMissed(compartment4)")) {if (tmp.get("systemTime").equals("362")) tmp.put("isPillMissed(compartment4)","true"); else tmp.put("isPillMissed(compartment4)","false");}
        if (! tmp.containsKey("pillTakenWithDelay(compartment1)")) tmp.put("pillTakenWithDelay(compartment1)","false");
        if (! tmp.containsKey("pillTakenWithDelay(compartment2)")) tmp.put("pillTakenWithDelay(compartment2)","false");
        //if (! tmp.containsKey("name(compartment1)")) tmp.put("name(compartment1)","\"fosamax\"");
        //if (! tmp.containsKey("name(compartment2)")) tmp.put("name(compartment2)","\"moment\"");       
        tmp.put("name(compartment1)","\"fosamax\"");
        tmp.put("name(compartment2)","\"moment\"");
        //System.out.println("Patrizia: Output for probing: "+tmp.toString());
		return tmp;
		
	}
	
	public Probe getProbe() {
		
		return this;
	}

	public Effector getEffector() {
		return this;
	}

	@Override
	public boolean shutDown() {
		modelEngine.stopExecution(id);
		return false;
	}
	

	
	
	//To test the PillBox wrapper in a standlone manner
	//Example of input cmd: systemTime 412 openSwitch(compartment2) false openSwitch(compartment3) false openSwitch(compartment4) false 
		public static void main(String[] args) {	
	        PillBox p = new PillBox("examples/pillbox1/pillbox.asm");	
	        
	        Scanner s = new Scanner(System.in); 
	        System.out.println("Enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");	
	        try {
	         String str = s.nextLine();
	         while (! str.equals("###") && ! str.isEmpty()) { 	
					p.run(str);
					//System.out.println("Output to patient: "+p.getOutputToPatient().toString());
					System.out.println("Output for probing: "+p.getOutputForProbing().toString());
					System.out.println("Enter user input (command line syntax: systemTime T openSwitch(compartmentN) true|false):~$");
					str = s.nextLine();
				}
	         }
				catch(InputMismatchException ex) {
					System.err.println("Error, input illformed.");
		        } 
			finally {
			        s.close();
			        p.shutDown();
			        System.out.println("PillBox OFF");
		       }
	        
	   }
	
}