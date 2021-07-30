package org.asmeta.runtime_container;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 */
public enum SimStatus {
	READY,		//Simulation waiting for input, can update model
	RUNNING,	//Running simulation, can't update model
	ROLLINGBACK,//Rollback in progress
	ADAPTING;	//Model update in progress
	
}
