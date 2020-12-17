package org.asmeta.framework.enforcerAirConditioner;

import org.asmeta.framework.enforcer.Knowledge;



public class KnowledgeAirConditioner extends Knowledge {
	 

    /** Keeps the times when the MAPE loop starts */
    //public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the MAPE loop completes*/
    //public List<Double> endTimeList = new ArrayList<Double>();    
	
	
	int airSpeed;
	   

	public KnowledgeAirConditioner() {
		super();
	}
   
		
	
	 //Overloading (by the new parameter); checks and eventually store the new value if different
	public boolean systemStateChanged(int newAirSpeedVal){
			if	( this.airSpeed != newAirSpeedVal) {
				//store the new value into the knowledge
				airSpeed = newAirSpeedVal;
				return true;
			}
			return false;
		}

	
	
}
