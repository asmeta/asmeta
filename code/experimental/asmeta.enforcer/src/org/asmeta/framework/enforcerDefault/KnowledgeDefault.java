/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcerDefault;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.framework.enforcer.Knowledge;



public class KnowledgeDefault extends Knowledge {
 
    
    /** Keeps the times when the controller started executing the MAPE loop (i.e, after every "time_window" seconds)*/
    public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the controller completed the execution of its MAPE loop*/
    public List<Double> endTimeList = new ArrayList<Double>();    
	
 
    
	private KnowledgeDefault() {
		super();
	}
   
	//@Override 
	public boolean systemStateChanged(){
		 
			return true;
		}
	
	
}
