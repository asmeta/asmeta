package org.asmeta.framework.enforcer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.asmeta.framework.auxiliary.Utility;
import org.asmeta.framework.managedSystem.*;


import org.asmeta.runtime_container.*;


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
    private SimulationContainer modelEngine; 
    
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
		    
     		//Read the pathname of the runtime model and the simulation timeout value from the configuration file
		    RUNTIME_MODEL_PATH = Utility.getProperty("RUNTIME_MODEL_PATH");
		    SIMULATION_TIMEOUT = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIMEOUT")));
		       
		    //Initialize the AsmetaS@run.time model engine
			modelEngine = SimulationContainer.getInstance();
			modelEngine.init(1);
			int result = modelEngine.startExecution(RUNTIME_MODEL_PATH);
			if (result < 0) 
				System.err.println("ERROR: Simulation engine not initialized for the model "+ RUNTIME_MODEL_PATH);
			else 
				System.out.println("Model engine initialized for "+ RUNTIME_MODEL_PATH + " with simulation step timeout " + SIMULATION_TIMEOUT);
			
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
	public boolean sanitiseInput(Map<String, String> inputValues) {
		return eval(inputValues).equals(new RunOutput(Esit.SAFE,"safe"));
		
	}
	
	private RunOutput eval(Map<String, String> inputValues) {
		//Map<String, String> inputs = convert(inputValues);
		//return modelEngine.runStepTimeout(1, inputValues, SIMULATION_TIMEOUT);
		return modelEngine.runStep(1, inputValues);
	}
	
	/* private static <T> Map<String, String> convert(String input) {	//ho cercato di fare una conversione di input generica non so se è giusto
		Map<String,String> locValue=new HashMap<>();
		for(Map.Entry<String, T> entry : input.entrySet()) {
		    String key = entry.getKey();
		    T value = entry.getValue();
		    if (value instanceof String)	//effettivamente se è un enum di asm non va bene
		    	locValue.put(key, "\""+value.toString()+"\"");
		    else
		    	locValue.put(key, value.toString());
		}
		return locValue;
	}*/
	
	
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
