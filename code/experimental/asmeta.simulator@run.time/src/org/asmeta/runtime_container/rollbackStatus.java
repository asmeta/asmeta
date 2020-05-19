package org.asmeta.runtime_container;

public enum rollbackStatus {
	NOTROLLED,		//Simulation not started
	ROLLINGBACK,		//Simulation started and waiting for input, can update model
	ROLLOK;	//Running simulation, can't update model
}
