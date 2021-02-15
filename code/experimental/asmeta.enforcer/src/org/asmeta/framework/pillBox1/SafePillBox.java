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


import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class SafePillBox {
	//Java wrapper of the simulated SafePillbox system (an ASM model)
	//For debugging purposes!
	
	 /** Runtime model simulator*/
    private SimulationContainer modelEngine; 
    private int id;
    private Map<String, String> currentStateChange;
   
 
	public SafePillBox(String SYSTEM_MODEL_PATH) {
	
	    System.out.println("Trying to initialize a model engine for "+ SYSTEM_MODEL_PATH);	       
	    //Initialize an AsmetaS@run.time model engine instance for the runtime system model (the simulated managed system!) 
		modelEngine = SimulationContainer.getInstance();
		modelEngine.init(1);
		currentStateChange = new HashMap<>();
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
		//Map<String, String> output = new HashMap<>();
		RunOutput result = modelEngine.runStep(1, prepareInput(input));
		//RunOutput result = modelEngine.runUntilEmpty(1, prepareInput(input));
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
		//result.getResult(); //Timeout expired or not
		if (result.getEsit() == Esit.SAFE) {
		    //store the new output location value as computed by the ASM into the output map
			//currentState.putAll(result.getControlledvalues());
			currentStateChange = result.getControlledvalues(); //Output values changed from the ASM model
			//output = prepareOutput(currentState);
			//output = prepareOutput(result.getControlledvalues());
		}
		else 
			 System.out.println("Error: something got wrong with the outcome of the ASM-based simulated system.");
		
		return currentStateChange;

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
		if (!cmd.isBlank()) {			
					String[] input = cmd.split(" ");
					for(int i=0; i<input.length; i+=2) {
					     data.put(input[i],input[i+1]); //put monitored value (key,value)  
					     System.out.println("("+input[i]+","+input[i+1]+")");
					}}
		return data;
	}
	
	
	//Output to Pillbox 
	public Map<String, String>  getOutputToPillBox() {
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those starting with "setNewTime", etc.
		    for (String key : currentStateChange.keySet()) {
		        if (key.startsWith("setNewTime") || key.startsWith("newTime") || key.startsWith("skipNextPill") || key.startsWith("setOriginalTime")) 
		        	tmp.put(key,currentStateChange.get(key));	
		    }
			return tmp;
			
		}
	

	public boolean shutDown() {
		modelEngine.stopExecution(id);
		return false;
	}
	

	public Map<String, String> getOutput() {
		return prepareOutput(currentStateChange);
	}
	
	//To test the PillBox wrapper in a standlone manner
	//Example of input cmd: 
	//redLed(compartment2) OFF redLed(compartment3) OFF redLed(compartment4) OFF name(compartment2) "aspirine" name(compartment3) "moment" name(compartment4) "fosamax" time_consumption(compartment2) [960] time_consumption(compartment3) [780,1140] time_consumption(compartment4) [410] drugIndex(compartment2) 0 drugIndex(compartment3) 0 drugIndex(compartment4) 0 nextDrugIndex(compartment2) 0 nextDrugIndex(compartment3) 1
		public static void main(String[] args) {	
	        SafePillBox p = new SafePillBox("examples/pillbox1/safePillbox.asm");	
	        
	        Scanner s = new Scanner(System.in); 
	        System.out.println("SafePillBox ON, enter input values> ");	
	        try {
	         String str = s.nextLine();
	         while (! str.equals("###") && ! str.isBlank()) { 	
					p.run(str);
					System.out.println("Output to pillbox: "+p.getOutputToPillBox().toString());
					System.out.println("Enter input value> ");
					str = s.nextLine();
				}
	         }
				catch(InputMismatchException ex) {
					System.err.println("Error, input illformed.");
		        } 
			finally {
			        s.close();
			        p.shutDown();
			        System.out.println("SafePillBox OFF");
		       }
	        
	   }
	
}