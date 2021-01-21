package org.asmeta.framework.enforcerPillBox;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.framework.enforcer.*;
import org.asmeta.framework.managedSystem.ManagedSystem;	
public class EnforcerPillBox extends Enforcer{

	public EnforcerPillBox() {
		super();
	}
	
	public EnforcerPillBox(ManagedSystem system, Knowledge k, FeedbackLoop l) {
		super(system, k, l);
		
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

}
