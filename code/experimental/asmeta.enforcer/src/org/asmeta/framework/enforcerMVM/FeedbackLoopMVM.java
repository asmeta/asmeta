/**
* 
*
* @author Patrizia Scandurra
*/

package org.asmeta.framework.enforcerMVM;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.*;
import org.asmeta.framework.mvm.MVM;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;

public class FeedbackLoopMVM extends FeedbackLoop{
	KnowledgeMVM kMVM; //casted version
	MVM probe, effector;
		
	public FeedbackLoopMVM(Probe probe, Effector effector, Knowledge k){
		super(probe, effector, k);
		kMVM= (KnowledgeMVM) this.getKnowledge();
		this.probe = (MVM) this.getProbe();  //TODO
		this.effector = (MVM) this.getEffector(); //TODO	
	}

	@Override
	public void monitor() {
		//Read and save the observed probe values from the PillBox into the knowledge
		//and check if system changed
		
		if (kMVM.systemStateChanged(probe.getOutputForProbing())) 
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
		//TODO for the MVM
		//Output sanitisation made by the ASM runtime model: make an ASM evaluation step from the current system state and the new user input (as saved/sanitized into the knowledge) 
		System.out.println("Output sanitisation made by the enforcement model...");
		RunOutput result = eval(kMVM.getProbes()); 	
		//Usage:
		//result.getEsit(); //SAFE or UNSAFE
        //result.getResult(); //Timeout expired or not
		//result.getControlledvalues(); //Output values from the ASM model	
		if (result.getEsit() == Esit.SAFE) {
			//store the adaptation plan as computed by the ASM runtime model into the knowledge and trigger execution
			Map<String, String> tmp = new HashMap<>();
			//iterating over keys only and selects those location values for the effectors 
		    for (String key : result.getControlledvalues().keySet()) {
		        if ( key.startsWith("watchdog_st") || key.startsWith("status_selftest") || key.startsWith("al_bit") || key.startsWith("respirationMode_out")|| key.startsWith("exp_valve")|| key.startsWith("insp_valve")) 
		        	tmp.put(key,result.getControlledvalues().get(key));	
		     }
		    
			kMVM.setEffectors(tmp);
			System.out.println("Enforcer output for effectors:~$ "+ tmp.toString());
			execution();
		}
		else {
			System.out.println("Error: something got wrong with the output sanitisation made by the ASM runtime model. No enforcement applied.");
		
		}
	}
		
	@Override
	public void execution() {
		//If enforcement is required, force the system as planned by actuating the MVM effectors 
  	    if (! kMVM.getEffectors().isEmpty()) {
  	  	   System.out.println("The MVM returns into a safe state with the enforced input:~$ "+kMVM.getEffectors().toString());
  	       effector.run(kMVM.getEffectors()); //the managed system runs again to return in a safe region
  	    }
 	
	}

	
	
}

	

