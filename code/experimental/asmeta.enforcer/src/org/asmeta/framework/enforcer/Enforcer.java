/**
* 
*
* @author Patrizia Scandurra
*/
package org.asmeta.framework.enforcer;

import java.io.File;
import java.io.IOException;

import org.asmeta.framework.enforcer.pillBoxApp.PillBoxNotSing;
import org.asmeta.framework.managedSystem.*;


import org.asmeta.runtime_container.*;


public abstract class Enforcer {
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
    protected static long SIMULATION_TIMEOUT;
    
    /** Runtime model pathname*/
    protected static String RUNTIME_MODEL_PATH;
    
    /** Runtime model handle*/
    private SimulationContainer modelEngine; 
    
    /** ManagedSystem handle*/
    private ManagedSystem system; 
	
	/**
	 * Enforcer constructors
	 * 
	 */
    public Enforcer() {}
    
	public Enforcer(ManagedSystem s, Knowledge k, FeedbackLoop l, boolean absolute) {		
	    try {
	    		    		    
	    	//connect managed system and feedback loop (get probe and effectors from the managed system)
		    probe = s.getProbe();
			effector = s.getEffector();
			knowledge  = k;
		    loop = l;
	    	//Read the pathname of the runtime model and the simulation timeout value from the configuration file
		    if (absolute==false) {RUNTIME_MODEL_PATH = Utility.getProperty("RUNTIME_MODEL_PATH");
		    SIMULATION_TIMEOUT = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIMEOUT")));}
		    else {		   
			    File currentDirectory = new File(new File(".").getAbsolutePath());
				String path=currentDirectory.getAbsolutePath();
				RUNTIME_MODEL_PATH = path.substring(0,path.length()-2)+"\\src\\org\\asmeta\\framework\\enforcer\\pillBoxApp\\specs\\"+Utility.getProperty("RUNTIME_MODEL_PATH");
			    SIMULATION_TIMEOUT = Math.round(Double.parseDouble(Utility.getProperty("SIMULATION_TIMEOUT")));
		    }
			
		    //Initialize the AsmetaS@run.time model engine
			//modelEngine = SimulationContainer.getInstance();
		    modelEngine = new SimulationContainer();
			modelEngine.init(1);
			int result = modelEngine.startExecution(RUNTIME_MODEL_PATH);
			if (result < 0) 
				System.err.println("ERROR: Simulation engine not initialized for the model "+ RUNTIME_MODEL_PATH);
			else {
			    //connect the ASM runtime model to the loop
				loop.setModel(modelEngine);
				System.out.println("Enforcer and ASM@run.time model engine initialized for <"+ RUNTIME_MODEL_PATH + "> with simulation timeout of " + SIMULATION_TIMEOUT + " seconds");
			}
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
	 * Run input sanitisation; returns true if the input is to filter out, false otherwise
	 * 
	 */
	public abstract boolean sanitiseInput(String inputValues); 
	
	
	
	
	/**
	 * Run output sanitisation; returns true if the output is to filter out, false otherwise
	 * 
	 */
	public abstract boolean sanitiseOutput(String outputValues);
	
	
	/**
	 * Run the enforcer's MAPE-K control loop. Final so subclasses can't override
	 * @return 
	 */
	public final long runLoop(){		
		
		return loop.run();
		
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

	
	/**
	 * Sets the simulation engine.
	 * Should only be used inside inherited default constructor
	 */
	protected void setModelEngine(SimulationContainer engine) {
		if (modelEngine == null)
			modelEngine = engine;
	}
	
	
	/**
	 * Returns the current enforcer simulation engine.
	 */
	protected SimulationContainer getModelEngine() {
		return modelEngine;
	}
}
