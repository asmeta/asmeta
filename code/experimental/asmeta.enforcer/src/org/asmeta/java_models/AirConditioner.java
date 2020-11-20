package org.asmeta.java_models;

//import org.asmeta.monitoring.Asm;
//import org.asmeta.monitoring.FieldToFunction;
//import org.asmeta.monitoring.FieldToLocation;
//import org.asmeta.monitoring.MethodToFunction;
//import org.asmeta.monitoring.MethodToLocation;
//import org.asmeta.monitoring.Monitored;
//import org.asmeta.monitoring.RunStep;
//import org.asmeta.monitoring.StartMonitoring;

//@Asm(asmFile="models/airConditioner.asm")
public class AirConditioner {
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
			airIntensity = 5;
		}
	}

	

	public void setRoomTemperature(int roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	//@MethodToLocation(func="airSpeed", args={})
	public int getAirIntensity1() {
		return airIntensity;
	}

	//@MethodToFunction(func="airSpeed")
	public int getAirIntensity2() {
		return airIntensity;
	}
}