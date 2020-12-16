package org.asmeta.framework.enforcer;

import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;

import org.asmeta.framework.auxiliary.Utility;
import org.asmeta.framework.managedSystem.*;


//import org.asmeta.runtime_container;


public abstract class Enforcer {//extends TimerTask{
	
	private static String configFile;

	/** MAPE-K loop handle*/
	protected FeedbackLoop loop;
	
	/** Sensor handle*/
	private Probe probe;
	
	/** Effector handle*/
	private Effector effector;
	
	/** Knowledge handle*/
	private Knowledge knowledge;

    /** Simulation timeout*/
    private static long SIMULATION_TIMEOUT;
    
    /** Runtime model pathname*/
    private static String RUNTIME_MODEL_PATH;
    
    /** Runtime model handle*/
    //private SimulationContainer modelEngine; 
    
    /** ManagedSystem handle*/
    private ManagedSystem system; 
	
	/**
	 * Enforcer constructor
	 */
	public Enforcer(ManagedSystem s, Knowledge k, FeedbackLoop l) {		
	    try {
	    		    	
		    //connect system and feedback loop
	    	
	    	// get probe and effectors
		    probe = s.getProbe();
			effector = s.getEffector();
			knowledge  = k;
		    loop = l;
		    
     		//init simulation timeout for the runtime model
			SIMULATION_TIMEOUT = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIMEOUT")));
		    System.out.println(SIMULATION_TIMEOUT);
			//init AsmetaS@run.time model engine
			//modelEngine = SimulationContainer.getInstance();
			//modelEngine.startExecution(RUNTIME_MODEL_PATHNAME);
			
	    }
	    catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the config.file pathname
	 */
	public static void setConfigFile(String s) {
		configFile = s;
	}

	/**
	 * get the config.file pathname
	 */
	public static String getConfigFile() {
		return configFile;
	}
	
	/**
	 * Run input sanitisation; input is eventually filtered out
	 * 
	 */
	public boolean sanitiseInput(String inputValues) {
		//return eval(inputValues).equals(new RunOutput(Esit.SAFE,"safe"));;
		return true;
	}
	
	//private RunOutput eval(String input) {
		//Map<String, String> inputValues = convert(input)
		//return modelEngine.runUntilEmptyTimeout(1, locationValues, SIMULATION_TIMEOUT)
				
	//}
	
	/**
	 * Run output sanitisation; output is filtered out
	 * 
	 */
	public boolean sanitiseOutput(String outputValues) {
		//return eval(outputValues).equals(new RunOutput(Esit.SAFE,"safe"));;
		return true;
	}
	
	
	/**
	 * Run the enforcer's MAPE-K control loop. Final so subclasses can't override
	 */
	public final void runLoop(){		
		
		loop.run();
		
	}
	
	/**
	 * Shut down the managed system
	 */
	public  void shutDown() throws IOException{
		boolean closed = false;
		
		try {
			closed = system.shutDown();
		}
		finally{
			if (closed)
				System.out.println("Managed system terminated correctly!");
			else
				System.out.println("Something was wrong with terminating the system!");
		}
	}

	
}
