package org.asmeta.framework.enforcerPillBox;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.enforcerAirConditioner.KnowledgeAirConditioner;
import org.asmeta.framework.pillBox.*;
import org.apache.log4j.Logger;

//First test class: the managed system is internal to its enforcer
public class Main {
   
	
	public static void main(String[] args) {
		
		System.out.println("#################################################\n"+
				   "##   Medicine Reminder and Monitoring System   ##\n"+
				   "#################################################\n"+
				   "Pillbox initialization (simulated version) ...");
		/** Initialization*/
		//Create system handle, probe, and effector
		PillBox managedSystem =  new PillBox("examples/pillbox/pillbox.asm"); 		
		//Create system knowledge and loop
		Knowledge k = new KnowledgePB();
		FeedbackLoop loop = new FeedbackLoopPillBox(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//create a new specialized enforcer for the Pillbox system
		Enforcer.setConfigFile("./resources/PillBox/config.properties");
		Enforcer e = new EnforcerPillBox(managedSystem,k,loop);
		
		 /** Running -- example of safety enforcement via MAPE-K*/
		 //Causality relation implementation between managed system and the ASM enforcement model: user input reading (by console), system/loop execution
		 //Once an event triggers the MAPE loop, the loop executes safety checks and eventually adapts the model and the system 
		
		Scanner s = new Scanner(System.in); 
		
        System.out.println("PillBox ON, enter user input> ");	
        try {
         String str = s.nextLine();
         while (! str.equals("###") && ! str.isBlank()) { 	
        	    e.sanitiseInput(str); //system input sanitisation 
          	    managedSystem.run(str);
          	    System.out.println("Output to patient: "+managedSystem.getOutputToPatient().toString());
          	    e.runLoop(); //system output sanitisation by adaptation
				System.out.println("(Enforced) Output to patient: "+managedSystem.getOutputToPatient().toString());
				System.out.println("Enter user input> ");
				str = s.nextLine();
			}
         }
		catch(InputMismatchException ex) {
				System.err.println("Error, input illformed.");
	        } 
		finally {
		        s.close();
		        managedSystem.shutDown();
		        System.out.println("PillBox OFF");
	       }
        
   }
		
		
	private Map<String, String> prepareInput(String cmd) {	
		Map<String, String> data = new HashMap<>();
		String[] input = cmd.split(" ");
		for(int i=0; i<input.length; i+=2)
		data.put(input[i],input[i+1]); //put monitored value (key,value)  
		return data;
}

}
