package org.asmeta.framework.enforcer.pillBoxApp;

import java.util.Map;

import org.asmeta.framework.enforcer.Enforcer;
import org.asmeta.framework.enforcer.FeedbackLoop;

public class EnforcerApp {
	
	private PillBoxNotSing managedSystem;
	private KnowledgePB k;
	private FeedbackLoop loop;
	private EnforcerPillBox e ;
	
	public EnforcerApp() {
		System.out.println("#################################################\n"+
				   "##   Medicine Reminder and Monitoring System   ##\n"+
				   "#################################################\n"+
				   "Pillbox initialization (simulated version) ...");
		/** Initialization*/
		//Create managed system handle, probe, and effector
		String path=new MyPath("pillbox.asm").getPath();
		managedSystem = new PillBoxNotSing(path.substring(0,path.length()));
		
		//managedSystem = new PillBoxNotSing(path.substring(0,path.length()-2)+"\\src\\org\\asmeta\\framework\\enforcer\\pillBoxApp\\specs\\pillbox.asm");
		//Create system knowledge and feedback loop
		k = new KnowledgePB();
		loop = new FeedbackLoopPillBox(managedSystem.getProbe(),managedSystem.getEffector(),k);
		//Create a new specialized enforcer for the Pillbox system
		path=new MyPath("config.properties").getPath();
		Enforcer.setConfigFile(path.substring(0,path.length()));
		e = new EnforcerPillBox(managedSystem,k,loop,true);
		
		//Init step for the enforcement model SafePillbox for the plug-in of the pill box, with consistency checks of the invariants
		String initial_input_trace =  "redLed(compartment1) OFF redLed(compartment2) OFF name(compartment1) \"fosamax\" name(compartment2) \"moment\" time_consumption(compartment1) [360] time_consumption(compartment2) [730,1140] drugIndex(compartment1) 0 drugIndex(compartment2) 0";
		Map<String, String> initState = e.initStep(initial_input_trace); //TODO Aggiungere gestione eccezione per invarianti, ecc..
	}
	
	public EnforcerPillBox getEnforcerPillBox() {
		return e;
	}
	
	public PillBoxNotSing getManagedSystem() {
		return managedSystem;
	}
	
	public KnowledgePB getKnowledgePB() {
		return k;
	}
	
	public FeedbackLoop getFeedbackLoop() {
		return loop;
	}
	
}
