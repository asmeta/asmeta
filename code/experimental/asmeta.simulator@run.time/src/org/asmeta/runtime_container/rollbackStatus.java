package org.asmeta.runtime_container;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 * Obsolete, used in the old runStep/runUntilEmpty timeout functions
 */
public enum rollbackStatus {
	NOTROLLED,		//Simulation not started
	ROLLINGBACK,		//Simulation started and waiting for input, can update model
	ROLLOK;	//Running simulation, can't update model
}
