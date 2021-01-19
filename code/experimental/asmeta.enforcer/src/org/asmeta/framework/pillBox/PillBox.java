package org.asmeta.framework.pillBox;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.asmeta.framework.auxiliary.Utility;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class PillBox extends ManagedSystem implements Probe, Effector{
	//Java wrapper of the simulated Pillbox system (an ASM model)
	
	 /** Runtime model simulator*/
    private SimulationContainer modelEngine; 
    private int id;
    private Map<String, String> currentState;
   
 
	public PillBox(String SYSTEM_MODEL_PATH) {
	
	    System.out.println("Trying to initialize a model engine for "+ SYSTEM_MODEL_PATH);	       
	    //Initialize an AsmetaS@run.time model engine instance for the runtime system model (the simulated managed system!) 
		modelEngine = SimulationContainer.getInstance();
		modelEngine.init(1);
		currentState = new HashMap<>();
		int result = modelEngine.startExecution(SYSTEM_MODEL_PATH);
		if (result < 0) 
			System.err.println("ERROR: Simulation engine not initialized for the model "+ SYSTEM_MODEL_PATH);
		else {
			id = result;
		    System.out.println("Model engine initialized for "+ SYSTEM_MODEL_PATH + " with simulation id " + id);
		}
	}

	//Make an ASM evaluation step from the monitored input and returns the output location values
	//Input command syntax example: 
    //openSwitch(compartment2) false openSwitch(compartment3) false set openSwitch(compartment4) true systemTime 414 
	public Map<String, String> run (String input) {
		Map<String, String> output = new HashMap<>();
		RunOutput result = modelEngine.runStep(1, prepareInput(input));
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
		//result.getResult(); //Timeout expired or not
		//System.out.println(result.getControlledvalues().get("airSpeed")); //Output values from the ASM model
		if (result.getEsit() == Esit.SAFE) {
		    //store the new output location value as computed by the ASM into the output map
			currentState.putAll(result.getControlledvalues());
			output = prepareOutput(currentState);
		}
		else 
			 System.out.println("Error: something got wrong with the outcome of the ASM-based simulated system.");
		return output;

	}
	
		
	private Map<String, String> prepareOutput(Map<String, String> locations) {
		//TO DO: Filter only the output locations; it should be done in a general manner in the ASM simulator@run.time
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
					System.out.println(input);
					for(int i=0; i<input.length; i+=2)
						data.put(input[i],input[i+1]); //put monitored value (key,value)  
					System.out.println(data);
					return data;
	}
	
	
	//OUT to patient
	//out outMess: Compartment -> String
	//out redLed: Compartment -> LedLights 
	//O={outMess,redLed}
	public Map<String, String>  getOutputToPatient() {
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those starting with "outMess" or "redLed"
		    for (String key : currentState.keySet()) {
		        if (key.startsWith("outMess") || key.startsWith("redLed")) 
		        	tmp.put(key,currentState.get(key));	
		    }
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
	

	public Map<String, String> getOutput() {
		return prepareOutput(currentState);
	}
	
	//To test the PillBox wrapper in a standlone manner
	//Example of input cmd: systemTime 412 openSwitch(compartment2) false openSwitch(compartment3) false openSwitch(compartment4) false 
		public static void main(String[] args) {	
	        PillBox p = new PillBox("examples/pillbox/pillbox.asm");	
	        
	        Scanner s = new Scanner(System.in); 
	        System.out.println("PillBox ON, enter monitored values> ");	
	        try {
	         String str = s.nextLine();
	         while (! str.equals("###")) { 	
					p.run(str);
					//System.out.println(p.getOutputToPatient());
					System.out.println("Enter monitored values> ");
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