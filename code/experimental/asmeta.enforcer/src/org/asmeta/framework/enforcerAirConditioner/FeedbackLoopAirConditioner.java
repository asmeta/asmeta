package org.asmeta.framework.enforcerAirConditioner;
import org.asmeta.framework. airConditioner.AirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;

public class FeedbackLoopAirConditioner extends FeedbackLoop{
		
	public FeedbackLoopAirConditioner(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		
	}

	//@Override
	public void monitor() {
		//Read and save the air speed value calculated by the air conditioner into the knowledge
		AirConditioner probeAC = (AirConditioner) this.getProbe();
		int newVal = probeAC.getAirIntensity();
		
		if (((KnowledgeAirConditioner)this.getKnowledge()).systemStateChanged(newVal)) {
			//store the new value into the knowledge
			((KnowledgeAirConditioner) this.getKnowledge()).airSpeed = newVal;
			// perform analysis
			analysis();
		}
	}

	//@Override 
	public void analysis() {

		// analyze all knowledge settings
		boolean adaptationRequired = analyzeKnowledge();
		// if adaptation required invoke the planner
		if (adaptationRequired) {
			planning();
		}
	}
	
  
	private boolean analyzeKnowledge() {
		
		if (((KnowledgeAirConditioner) this.getKnowledge()).airSpeed == 2)
				return true;
		return false;
	}
	
	//@Override
	public void planning() {

		execution();
	}
	
	//@Override
	public void execution() {
		//Force the system to the new value
		((AirConditioner)(this.getEffector())).setAirIntensity(1);
		//store the new value into the knowledge
		((KnowledgeAirConditioner) this.getKnowledge()).airSpeed = 1;
	
	}
}
