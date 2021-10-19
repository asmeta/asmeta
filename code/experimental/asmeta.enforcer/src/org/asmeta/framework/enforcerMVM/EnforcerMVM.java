package org.asmeta.framework.enforcerMVM;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;	

public class EnforcerMVM extends Enforcer {

	public EnforcerMVM() {
		super();
	}
	
	public EnforcerMVM(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
		
	}

	
	//First init step for the ASM enforcement model with the actual knowledge of the MVM state 
		public Map<String, String> initStep (String input) {
			Map<String, String> initState = prepareInput(input);
			System.out.println("Runtime enforcement model initialization: "+ initState.toString());
			RunOutput result = this.getModelEngine().runStep(1, initState);
			if (result.getEsit() == Esit.SAFE) {
			    //store the new output location value as computed by the ASM into the output map
				//currentState.putAll(result.getControlledvalues());
				initState.putAll(result.getControlledvalues()); //Output values from the ASM model for the controlled/out locations
			}
			else 
				 System.out.println("Error: something got wrong with the initialization of the ASM <"+ Enforcer.RUNTIME_MODEL_PATH +">\"");
			return initState;
		}

		
	//parse the input cmd and create an input object for the ASM runtime model 
		private Map<String, String> prepareInput(String cmd) {	
			Map<String, String> data = new HashMap<>();
			String[] input = cmd.split(" ");
			for(int i=0; i<input.length; i+=2)
			data.put(input[i],input[i+1]); //put monitored value (key,value)  
			return data;
			}

		
		
	@Override
	public boolean sanitiseInput(String inputValues) {
		//Read and save user input for the MVM into the knowledge
				KnowledgeMVM kMVM = (KnowledgeMVM) this.loop.getKnowledge();
				kMVM.setInput(prepareInput(inputValues));
				return false; //no input sanitisation applied, only input storing into the knowledge in a tabular form
	}

	@Override
	public boolean sanitiseOutput(String outputValues) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Map<String, String> getSanitisedInput(){
		return ((KnowledgeMVM) this.loop.getKnowledge()).getInput();
	}

	//Output for effectors
		public Map<String, String>  getOutputForEffectors(){
					return ((KnowledgeMVM) this.loop.getKnowledge()).getEffectors();
					
				}

	
}
