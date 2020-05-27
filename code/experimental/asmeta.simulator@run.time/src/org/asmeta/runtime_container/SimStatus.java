package org.asmeta.runtime_container;

public enum SimStatus {
	PAUSE,		//Simulation waiting for input, can update model
	RUNNING;	//Running simulation, can't update model
	
}
