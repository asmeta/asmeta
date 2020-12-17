package org.asmeta.framework.enforcerAirConditioner;
import org.asmeta.framework. airConditioner.AirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;

public class FeedbackLoopAirConditioner extends FeedbackLoop{
	KnowledgeAirConditioner kAC; //casted version
		
	public FeedbackLoopAirConditioner(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kAC= (KnowledgeAirConditioner) this.getKnowledge();
		
	}

	//@Override
	public void monitor() {
		//Read and save the air speed value calculated by the air conditioner into the knowledge
		AirConditioner probeAC = (AirConditioner) this.getProbe();
		int newVal = probeAC.getAirIntensity();
		//check and store if system changed
		if (kAC.systemStateChanged(newVal)) {
			//if changed, perform analysis
			analysis();
		}
	}

	//@Override 
	public void analysis() {
		// analyze all knowledge settings
		boolean adaptationRequired = analyzeKnowledge();
		// if adaptation is required, invoke the planner
		if (adaptationRequired) {
			planning();
		}
	}
	
  
	private boolean analyzeKnowledge() {
		
	   if (kAC.airSpeed == 2)
				return true;
		
		return false;
	}
	
	//@Override
	public void planning() {
		//store the new value into the knowledge
		kAC.airSpeed = 1;
		execution();
	}
	
	//@Override
	public void execution() {
		//Force the system to the new value
		((AirConditioner)(this.getEffector())).setAirIntensity(kAC.airSpeed);
		
	
	}
}
