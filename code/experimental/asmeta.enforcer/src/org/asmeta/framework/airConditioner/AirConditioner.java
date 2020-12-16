package org.asmeta.framework.airConditioner;

import org.asmeta.framework.managedSystem.*;

//import org.asmeta.monitoring.Asm;
//import org.asmeta.monitoring.FieldToFunction;
//import org.asmeta.monitoring.FieldToLocation;
//import org.asmeta.monitoring.MethodToFunction;
//import org.asmeta.monitoring.MethodToLocation;
//import org.asmeta.monitoring.Monitored;
//import org.asmeta.monitoring.RunStep;
//import org.asmeta.monitoring.StartMonitoring;

//@Asm(asmFile="models/airConditioner.asm")
public class AirConditioner extends ManagedSystem implements Probe, Effector{
	//@Monitored(func="temperature", args={})
	public int roomTemperature;
	//@FieldToLocation(func="airSpeed", args={})
	//@FieldToFunction(func="airSpeed")
	public int airIntensity;

	//@StartMonitoring
	public AirConditioner(int temperature) {
		airIntensity = 0;
		this.roomTemperature = temperature;
	}

	//@RunStep
	public void setAirIntensity() {
		if(roomTemperature < 20) {
			airIntensity = 0;
		}
		else if(roomTemperature < 25) {
			airIntensity = 1;
		}
		else {
			airIntensity = 2;
		}
	}

	public void setAirIntensity(int val) {
		airIntensity = val;
		
	}
	
	public void setRoomTemperature(int roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	//@MethodToLocation(func="airSpeed", args={})
	public int getAirIntensity() {
		return airIntensity;
	}

	
	public Probe getProbe() {
		return this;
	}

	public Effector getEffector() {
		return this;
	}

	@Override
	public boolean shutDown() {
		// TODO Auto-generated method stub
		return false;
	}
}