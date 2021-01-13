package org.asmeta.framework.enforcerPillBox;

import org.asmeta.framework.enforcer.Knowledge;



public class KnowledgePB extends Knowledge {
	 

    /** Keeps the times when the MAPE loop starts */
    //public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the MAPE loop completes*/
    //public List<Double> endTimeList = new ArrayList<Double>();    
	
	/** Monitored values from the Air Conditioner*/ 
	int airSpeed; //input value for the Air Conditioner
	int temperature; //output value as computed by the Air Conditioner
	   

	public KnowledgePB() {
		super();
	}
   
		
	
	 //Overloading (by the new parameter); checks and eventually store the new system output value if different
	/*public boolean systemStateChanged(int newAirSpeedVal, int newTempValue){
			if	( this.airSpeed != newAirSpeedVal) {
				//store the new value into the knowledge
				airSpeed = newAirSpeedVal;
				temperature = newTempValue;
				return true;
			}
			return false;
		}
    */
	
	
}
