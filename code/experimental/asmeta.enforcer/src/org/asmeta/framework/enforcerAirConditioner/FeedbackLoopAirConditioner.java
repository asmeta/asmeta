package org.asmeta.framework.enforcerAirConditioner;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework. airConditioner.AirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.runtime_container.RunOutput;

public class FeedbackLoopAirConditioner extends FeedbackLoop{
	KnowledgeAirConditioner kAC; //casted version
		
	public FeedbackLoopAirConditioner(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kAC= (KnowledgeAirConditioner) this.getKnowledge();
		
	}

	@Override
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

	@Override 
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
	
	@Override
	public void planning() {
		//Output sanitisation made by the ASM runtime model: make an ASM evaluation step from the monitored input 
		RunOutput result = eval(prepareInput());
		//result.getEsit(); //SAFE or UNSAFE
        //result.getResult(); //Timeout expired or not
		result.getControlledvalues(); //Output values from the ASM model
		
		//store the new value as computed by the ASM runtime model into the knowledge
		kAC.airSpeed = 1;
		execution();
	}
	
	
	// create an output object from the knowledge which can be used as input object for the ASM runtime model 
	public Map<String, String> prepareInput() {	
				Map<String, String> data = new HashMap<>();
				data.put("temperature", Integer.toString(kAC.temperature));
				data.put("airIntensity", Integer.toString(kAC.airSpeed));
				return data;
		}
			
	@Override
	public void execution() {
		//Force the system to the new value
		((AirConditioner)(this.getEffector())).setAirIntensity(kAC.airSpeed);
		
	
	}

	
	
}
