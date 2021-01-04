package org.asmeta.framework.enforcerPillBox;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.pillBox.*;

//First test class: the managed system is internal to its enforcer
public class Main {
   
	
	public static void main(String[] args) {
		
		/** Initialization*/
		//Create system handle, probe, and effector
		PillBox managedSystem =  new PillBox(); 
		//Create system knowledge and loop
		Knowledge k = new KnowledgePB();
		FeedbackLoop loop = new FeedbackLoopPillBox(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//create a new specialized enforcer for the AirConditioner system
		Enforcer.setConfigFile("./resources/PillBox/config.properties");
		Enforcer e = new EnforcerPillBox(managedSystem,k,loop);
		
		 /** Running -- example of safety enforcement via MAPE-K*/
		 //Causality relation implementation between managed system and the runtime ASM model, loop execution, system execution
		 //Once an event triggers the MAPE loop, the loop executes safety checks and eventually adapts the model and the system 
		
	   /*	System.out.println("Initial air speed of the system: " + managedSystem.getAirIntensity());
		managedSystem.setRoomTemperature(40);
		managedSystem.setAirIntensity();
		System.out.println("Room temperature measured by the system: " + managedSystem.getRoomTemperature());
		System.out.println("Air speed set by the system: " + managedSystem.getAirIntensity());
		//Output sanitisation via a MAPE-K loop
		e.runLoop();
		System.out.println("Air speed forced to: " + ((KnowledgeAirConditioner) k).airSpeed);
		System.out.println("Air speed set by the system: " + managedSystem.getAirIntensity());
		System.out.println("Starting/ending loop time: " + loop.getStartTime() + "\t" + loop.getEndTime() + "\n\n");
		
		
		*/
	}

}
