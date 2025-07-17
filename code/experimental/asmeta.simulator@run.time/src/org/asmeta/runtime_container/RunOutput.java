package org.asmeta.runtime_container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.animator.MyState;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.Value;

// TODO: Auto-generated Javadoc
/**
 * The Class RunOutput.
 */
public class RunOutput implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The model state. */
	private MyState ms = null; 
	
	/** The location value. */
	@SuppressWarnings("rawtypes")
	private Map<Location, Value> locationValue = null; //Monitored map
	
	/** The outcome of a simulation run/step: SAFE (default value) or UNSAFE. */
	private Esit esit = Esit.SAFE; 
	
	/** The test. */
	public String message = "";
	
	/** Flag to check if the run has finished */
	private boolean timeoutFlag = false;
	
	/**
	 * Instantiates a new run output.
	 *
	 * @param esit the esit
	 * @param locationValue the location value
	 * @param ms the model state
	 */
	public RunOutput(Esit esit, @SuppressWarnings("rawtypes") Map<Location, Value> locationValue, MyState ms) {
		this.locationValue = locationValue;
		this.esit = esit;
		this.ms = ms;
		
	}
	

	/**
	 * Instantiates a new run output.
	 *
	 * @param esit the esit
	 * @param test the test
	 */
	public RunOutput(Esit esit, String message) {
		this.esit = esit;
		this.message = message;
	
	}
	
	/**
	 * Instantiates a new run output.
	 *
	 * @param esit the esit
	 * @param ms the model state
	 */
	
	public RunOutput(Esit esit, MyState ms) {
		this.esit = esit;
		this.ms = ms;
	}

	/** Changes the timeout flag value */
	public void setTimeoutFlag(boolean result) {
		timeoutFlag = result;
	}

	/**
	 * Shows the timeout flag value, for the timeout methods
	 * @return true if run finished, false if it's still running
	 */
	public boolean getTimeoutFlag() {
		return timeoutFlag;
	}
	
	/** Shows the outcome SAFE or UNSAFE */
	public Esit getEsit() {
		return esit;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Model execution outcome: " + esit + "\n");
		if (!message.equals(""))
			sb.append("Reason: " + message + "\n");
		if (ms!=null) {
			sb.append("Updated locations: " + ms.getControlledValuesToString());
			sb.append("\nOut locations: " + ms.getOutValuesToString()); //2021_12_13 SILVIA: print out functions
		}	
		

		return sb.toString();
	}
	

	//JUST IN USE FOR TESTING
	public String MytoString() {
		StringBuilder sb = new StringBuilder();
		sb.append("The outcome of the running is: " + esit + "\n");
		if (!message.equals(""))
			sb.append("Reason: " + message + "\n");
		if (locationValue!=null)
			sb.append("The monitored locations are: " + locationValue + "\n");
		if (ms!=null)
			sb.append("The new state is: " + ms.getControlledValues());
		//sb.append("-----------------------------------\n");
		return sb.toString();
	}
	
	//NEEDED TO GET OUT FUNCTIONS VALUES
	//TODO Federico Rebucini->ho messo l'output in string per isolare location e value al simulator, non so se � meglio importarli nell'enforcer
	public Map<String, String> getControlledvalues(){
		if (ms!=null && ms.getControlledValues()!=null) {
			Map<Location, Value> set=ms.getControlledValues();
			HashMap<String, String> controlled = new HashMap<String, String>();
			for (Location key : set.keySet()) {
			    Value val = set.get(key);
			    
			    if(val instanceof org.asmeta.simulator.value.StringValue) {
			    	controlled.put(key.toString(), "\"" + val.toString() + "\"");
			    } else {
			    	controlled.put(key.toString(), val.toString());
			    }
			}
			//System.out.println(controlled.toString());
			return controlled;
		}
		return new HashMap<String,String>();
	}

	public Map<String, String> getOutvalues(){
		if (ms!=null && ms.getOutValues()!=null) {
			Map<Location, Value> set=ms.getOutValues();
			HashMap<String, String> out = new HashMap<String, String>();
			for (Location key : set.keySet()) {
			    Value val = set.get(key);

			    if(val instanceof org.asmeta.simulator.value.StringValue) {
			    	out.put(key.toString(), "\"" + val.toString() + "\"");
			    } else {
			    	out.put(key.toString(), val.toString());
			    }
			}
			return out;
		}
		return new HashMap<String,String>();
	}
	
	@Override
	public boolean equals(Object obj) {
		    
        if (obj == this) { 
            return true; 
        } 
  
      
        if (!(obj instanceof RunOutput)) { 
            return false; 
        } 
          
        
        RunOutput c = (RunOutput) obj; 
          
       
        return this.esit == c.esit;
	}
	
	public boolean equalsMessage(Object obj) {
        return equals(obj) && this.message.equals(((RunOutput)obj).message);
	}
	

}
