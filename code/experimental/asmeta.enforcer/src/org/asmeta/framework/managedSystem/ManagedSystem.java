package org.asmeta.framework.managedSystem;

/*
 * Instrumentation to monitor and adapt a software system.
 */

public abstract class ManagedSystem {

	public abstract Probe getProbe();

	public abstract Effector getEffector(); 
	
	public abstract boolean shutDown();

}
