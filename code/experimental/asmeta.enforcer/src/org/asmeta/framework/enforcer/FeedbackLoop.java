package org.asmeta.framework.enforcer;
import java.util.Map;

import org.asmeta.framework.managedSystem.Effector;
import org.asmeta.framework.managedSystem.Probe;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public abstract class FeedbackLoop {

	/** Sensor handle*/
	private Probe probe;
	
	/** Effector handle*/
	private Effector effector;
	
	/** Loop starting time*/
	private long startTime;	
	
	/** Loop end time*/
	private long endTime;	

	/** Knowledge handle*/
	private Knowledge k;

	/** Runtime model handle*/
	private SimulationContainer model;
	
	/**
	 * FeedbackLoop constructor
	 */
	public FeedbackLoop(Probe probe, Effector effector, Knowledge k) {
		//init MAPE
	    this.probe		= probe;
	    this.effector 	= effector;  
	    this.k = k;
   }
	
	//Hook get/set methods:
	public void setKnowledge(Knowledge k) {
		this.k = k;
	}

	public Knowledge getKnowledge() {
		return this.k;
	}
	
	public void setProbe(Probe probe) {
		this.probe = probe;
	}

	public void setEffector(Effector effector) {
		this.effector = effector;
	}
	
	public Probe getProbe() {
		return this.probe;
	}

	public Effector getEffector() {
		return this.effector;
	}

	public long getStartTime() {
		return startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	//Final so subclasses can't override
	public final void run() {
		startTime = System.currentTimeMillis();	
		monitor(); //waterfall invocation
		endTime = System.currentTimeMillis();
	}

	//hook method
	public void monitor() {
		analysis();
	}

	//hook method
	public void analysis() {
	    planning();
	}
	
	//hook method
	public void planning() {
		execution();
	}

	//abstract method
	public abstract void execution();

	protected void setModel(SimulationContainer modelHandle) {
		model = modelHandle;
	};

	//make an ASM evaluation step from the monitored input 
	public RunOutput eval(Map<String, String> inputValues) {
		//return modelEngine.runStepTimeout(1, inputValues, SIMULATION_TIMEOUT);
		return model.runStep(1, inputValues);
	}
	
	// create an output object from the knowledge which can be used as input object for the ASM runtime model 
	//public abstract Map<String, String> prepareInput();
	
}
