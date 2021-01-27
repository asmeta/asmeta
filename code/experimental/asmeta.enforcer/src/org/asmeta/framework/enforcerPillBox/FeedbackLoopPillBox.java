/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerPillBox;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.framework.pillBox.PillBoxNotSing;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;

public class FeedbackLoopPillBox extends FeedbackLoop{
	KnowledgePB kPB; //casted version
	PillBoxNotSing probePB, effectorPB;
		
	public FeedbackLoopPillBox(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kPB= (KnowledgePB) this.getKnowledge();
		probePB = (PillBoxNotSing) this.getProbe();
		effectorPB = (PillBoxNotSing) this.getEffector();
		
	}

	@Override
	public void monitor() {
		//Read and save the observed probe values from the PillBox into the knowledge
		//and check if system changed
		if (kPB.systemStateChanged(probePB.getOutputForProbing())) 
			//if system changed, perform analysis (enforcement by adaptation)
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
		 return true; //We could invoke here only the ASM pillbox_sanitiser for checking invariants (safety assertions) only with the model rolling back in case of violation
	}
	
	@Override
	public void planning() {
		//Output sanitisation made by the ASM runtime model: make an ASM evaluation step from the current system state and the new user input (as saved/sanitized into the knowledge) 
		System.out.println("Output sanitisation made by the ASM enforcement model...");
		RunOutput result = eval(kPB.getProbes()); 	
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
        //result.getResult(); //Timeout expired or not
		//result.getControlledvalues(); //Output values from the ASM model	
		if (result.getEsit() == Esit.SAFE) {
			//store the adaptation plan as computed by the ASM runtime model into the knowledge and trigger execution
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those location values for the effectors 
		    for (String key : result.getControlledvalues().keySet()) {
		        if ( key.startsWith("setNewTime") || key.startsWith("newTime") || key.startsWith("skipNextPill") || key.startsWith("setOriginalTime")) 
		        	tmp.put(key,result.getControlledvalues().get(key));	
		     }
		    System.out.println("Enforcer output for effectors:~$ "+ tmp.toString() + "ciao: " + kPB.getInput().toString());
		    if (! tmp.isEmpty()) { //to add the other monitored locations as required in input by the Pillbox
	    	//iterating over keys of the input stored into the knowledge and selects those starting with "systemTime" and "openSwitch"
		    for (String key : kPB.getInput().keySet()) {
		        if (key.startsWith("openSwitch") || key.startsWith("systemTime")) 
		        	tmp.put(key,kPB.getInput().get(key));	
		     }
	        }
		    	
			kPB.setEffectors(tmp);
			execution();
		}
		else {
			System.out.println("Error: something got wrong with the output sanitisation made by the ASM runtime model. No enforcement applied.");
		
		}
	}
	
	
	
	@Override
	public void execution() {
		//If enforcement is required, force the system as planned by actuating the Pillbox effectors 
  	    if (! kPB.getEffectors().isEmpty()) {
  	  	   System.out.println("The Pillbox returns into a safe state with the enforced input:~$ "+kPB.getEffectors().toString());
  	       effectorPB.run(kPB.getEffectors()); //the managed system runs again to return in a safe region
  	    }
 	
	}

	
	
}
