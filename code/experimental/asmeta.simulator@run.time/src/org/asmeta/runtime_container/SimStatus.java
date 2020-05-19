package org.asmeta.runtime_container;

public enum SimStatus {
	EMPTY,		//Simulation not started
	PAUSE,		//Simulation started and waiting for input, can update model
	RUNNING;	//Running simulation, can't update model
	
}
