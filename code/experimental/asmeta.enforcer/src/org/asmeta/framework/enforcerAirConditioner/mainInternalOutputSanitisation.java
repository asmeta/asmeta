package org.asmeta.framework.enforcerAirConditioner;
//https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/
	
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.airConditioner.*;

//First test class: the managed system is internal to its enforcer
public class mainInternalOutputSanitisation {
   
	
	public static void main(String[] args) {
		
		/** Initialization*/
		//Create system handle, probe, and effector
		AirConditioner managedSystem =  new AirConditioner(0); 
		//Create system knowledge and loop
		//KnowledgeSingleton k = KnowledgeAirConditioner.getInstance();
		Knowledge k = new KnowledgeAirConditioner();
		FeedbackLoop loop = new FeedbackLoopAirConditioner(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//create a new specialized enforcer for the AirConditioner system
		Enforcer.setConfigFile("./resources/AirConditioner/config.properties");
		Enforcer e = new EnforcerAirConditioner(managedSystem,k,loop);
		
		 /** Running (one step) -- example of output sanitisation via MAPE-K*/
		System.out.println("Air speed set by the system: " + managedSystem.getAirIntensity());
		managedSystem.setRoomTemperature(40);
		managedSystem.setAirIntensity();
		System.out.println("Air speed set by the system: " + managedSystem.getAirIntensity());
		//Output sanitisation via a MAPE-K loop
		e.runLoop();
		System.out.println("Air speed forced to: " + ((KnowledgeAirConditioner) k).airSpeed);
		System.out.println("Air speed set by the system: " + managedSystem.getAirIntensity());
		System.out.print("Starting/ending loop time: " + loop.getStartTime() + "\t" + loop.getEndTime());
	}

}
