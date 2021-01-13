package org.asmeta.framework.pillBox;

import org.asmeta.framework.managedSystem.*;

public class PillBox extends ManagedSystem implements Probe, Effector{
	//Java wrapper of the simulated Pillbox system (an ASM model)
	
	//public int roomTemperature;
	//public int airIntensity;

	public PillBox() {
		//airIntensity = 0;
		//this.roomTemperature = temperature;
	}

/*	public void setAirIntensity() {
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

	public int getRoomTemperature() {
		return roomTemperature;
	}
	
	public int getAirIntensity() {
		return airIntensity;
	}
   */
	
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