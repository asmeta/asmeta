/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerPillBox;
import java.util.Map;
import org.asmeta.framework.enforcer.Knowledge;

public class KnowledgePB extends Knowledge {
	 

    /** Keeps the times when the MAPE loop starts */
    //public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the MAPE loop completes*/
    //public List<Double> endTimeList = new ArrayList<Double>();    
	
	/** Monitored values from the PillBox*/ 
	Map<String, String> input; //user input for the PillBox
	Map<String, String> output; //output as computed by the PillBox, and eventually sanitised by the enforcer
	   

	public KnowledgePB() {
		super();
	}
   
		
	 //Overloading (by the new parameter); checks and eventually store the new system output value if different
	public boolean systemStateChanged(Map<String, String> newOutput){
		  if (output.equals(newOutput)) return false;
		  output = newOutput;
		  return true;
		}
	
	public void setInput(Map<String, String> newInput){
		input = newInput;
	}
	
	public Map<String, String> getInput(){
		return input;
	}
	
	
}
