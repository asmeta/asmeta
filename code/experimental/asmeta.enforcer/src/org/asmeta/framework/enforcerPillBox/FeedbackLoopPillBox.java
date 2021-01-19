package org.asmeta.framework.enforcerPillBox;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework. airConditioner.AirConditioner;
import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.framework.pillBox.PillBox;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;

public class FeedbackLoopPillBox extends FeedbackLoop{
	KnowledgePB kPB; //casted version
	PillBox probePB, effectorPB;
		
	public FeedbackLoopPillBox(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kPB= (KnowledgePB) this.getKnowledge();
		probePB = (PillBox) this.getProbe();
		effectorPB = (PillBox) this.getEffector();
	}

	@Override
	public void monitor() {
		//Read and save the observed probe values from the PillBox into the knowledge
		//and check if system changed
		if (kPB.systemStateChanged(probePB.getOutput())) 
			//if the system produced a new output, perform analysis (output sanitisation by adaptation)
			analysis();
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
		 return true; //Can we invoke here only the ASM sanitizer for checking invariants only with the model rolling back in case of violation?
	}
	
	@Override
	public void planning() {
		//Output sanitisation made by the ASM runtime model: make an ASM evaluation step from the current system state and the new user input (as saved/sanitized into the knowledge) 
		System.out.println("Output sanitisation made by the ASM enforcement model...");
		RunOutput result = eval(kPB.getInput()); 	
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
        //result.getResult(); //Timeout expired or not
		//result.getControlledvalues(); //Output values from the ASM model	
		if (result.getEsit() == Esit.SAFE) {
			//store the adaptation plan as computed by the ASM runtime model into the knowledge and trigger execution
			//kPB.setOutputForPillbox(result.getControlledvalues()); TO DO
			execution();
		}
		else {
			System.out.println("Error: something got wrong with the output sanitisation made by the ASM runtime model. No enforcement applied.");
		}
	}
	
	
	
	@Override
	public void execution() {
		//Force the system as planned by actuating the effectors 
		//((AirConditioner)(this.getEffector())).setAirIntensity(kAC.airSpeed);
		//effectorPB.
		//TO DO
		
	
	}

	
	
}
