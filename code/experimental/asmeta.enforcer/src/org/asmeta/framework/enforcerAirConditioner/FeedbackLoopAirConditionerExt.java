package org.asmeta.framework.enforcerAirConditioner;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework. airConditioner.AirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;

/**
 * @author Federico Rebucini
 */
public class FeedbackLoopAirConditionerExt extends FeedbackLoop{
	KnowledgeAirConditioner kAC; //casted version
		
	public FeedbackLoopAirConditionerExt(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kAC= (KnowledgeAirConditioner) this.getKnowledge();
		
	}

	@Override
	public void monitor() {
		//Read and save the air speed value calculated by the air conditioner into the knowledge
		ExtManagedSystemAC probeAC = (ExtManagedSystemAC) this.getProbe();
		//check and store if system changed
		if (kAC.systemStateChanged(probeAC.getAirIntensity(),probeAC.getRoomTemperature())) {
			//if the system produced a new output, perform analysis (output sanitisation)
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
		//Output sanitisation made by the ASM runtime model: make an ASM evaluation step from the current state and the new monitored input 
		System.out.println("Output sanitisation made by the ASM runtime model...");
		RunOutput result = eval(prepareInput());
		
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
        //result.getResult(); //Timeout expired or not
		//System.out.println(result.getControlledvalues().get("airSpeed")); //Output values from the ASM model
		
		if (result.getEsit() == Esit.SAFE) {
			//store the new value as computed by the ASM runtime model into the knowledge and trigger execution
			kAC.airSpeed = Integer.valueOf(result.getControlledvalues().get("airSpeed"));
			execution();
		}
		else {
			// solitamente runoutput viene creato con flag a false,
			// ma se viene utilizzata la funzione con timeout, questa corrisponderà sempre al fatto se il timeout è stato superato o no
			if (result.getTimeoutFlag())
				System.out.println("Error: something got wrong with the output sanitisation made by the ASM runtime model. No enforcement applied.");
			else
				System.out.println("Error: The ASM runtime model reached timeout. No enforcement applied.");
		}
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
		((ExtManagedSystemAC)(this.getEffector())).setAirIntensity(kAC.airSpeed);
		
	
	}

	
	
}
