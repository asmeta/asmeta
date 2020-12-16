package org.asmeta.framework.enforcer;
import org.asmeta.framework.managedSystem.Effector;
import org.asmeta.framework.managedSystem.Probe;

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

	
}
