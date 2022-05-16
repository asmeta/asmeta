/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerPillBox1;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.RunOutput;	
public class EnforcerPillBox extends Enforcer{

	public EnforcerPillBox() {
		super();
	}
	
	public EnforcerPillBox(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
		
	}
	
	
	//First init step for the ASM SafePillbox used as enforcement model by the enforcer
	public Map<String, String> initStep (String input) {
		Map<String, String> initState = prepareInput(input);
		System.out.println("Model initialization with the actual pillbox content: "+ initState.toString());
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

	@Override
	public boolean sanitiseInput(String inputValues) {
		//Read and save user input for the PillBox into the knowledge
		KnowledgePB kPB = (KnowledgePB) this.loop.getKnowledge();
		kPB.setInput(prepareInput(inputValues));
		return false; //no input sanitisation applied, only input storing into the knowledge in a tabular form
	}
	
	public Map<String, String> getSanitisedInput(){
		return ((KnowledgePB) this.loop.getKnowledge()).getInput();
	}

	@Override
	public boolean sanitiseOutput(String outputValues) {
		// TODO Auto-generated method stub
		return false; //no output sanitisation applied by this method
	}

	//parse the input cmd and create an input object for the ASM runtime model 
	private Map<String, String> prepareInput(String cmd) {	
		Map<String, String> data = new HashMap<>();
		String[] input = cmd.split(" ");
		for(int i=0; i<input.length; i+=2)
		data.put(input[i],input[i+1]); //put monitored value (key,value)  
		return data;
		}

	
	//Output for effectors
	public Map<String, String>  getOutputForEffectors(){
				return ((KnowledgePB) this.loop.getKnowledge()).getEffectors();
				
			}

}
