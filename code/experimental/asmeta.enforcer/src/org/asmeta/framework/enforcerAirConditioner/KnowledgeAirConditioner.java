package org.asmeta.framework.enforcerAirConditioner;

import org.asmeta.framework.enforcer.Knowledge;



public class KnowledgeAirConditioner extends Knowledge {
	 

    /** Keeps the times when the controller started executing the MAPE loop (i.e, after every "time_window" seconds)*/
    //public List<Double> initTimeList = new ArrayList<Double>();    

    /** Keeps the times when the controller completed the execution of its MAPE loop*/
    //public List<Double> endTimeList = new ArrayList<Double>();    
	
	
	int airSpeed;
	   

	public KnowledgeAirConditioner() {
		super();
	}
   
		
	
	   //Overloading
		public boolean systemStateChanged(int newVal){
			if	( this.airSpeed != newVal)
				return true;
			return false;
		}



		@Override
		public boolean systemStateChanged() {

			return true;
		}
	
	
}
