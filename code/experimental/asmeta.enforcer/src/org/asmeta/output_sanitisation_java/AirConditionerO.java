package org.asmeta.output_sanitisation_java;

//import org.asmeta.monitoring.Asm;
//import org.asmeta.monitoring.FieldToFunction;
//import org.asmeta.monitoring.FieldToLocation;
//import org.asmeta.monitoring.MethodToFunction;
//import org.asmeta.monitoring.MethodToLocation;
//import org.asmeta.monitoring.Monitored;
//import org.asmeta.monitoring.RunStep;
//import org.asmeta.monitoring.StartMonitoring;

//@Asm(asmFile="models/airConditioner.asm")
public class AirConditionerO {
	//@Monitored(func="temperature", args={})
	public int roomTemperature;
	//@FieldToLocation(func="airSpeed", args={})
	//@FieldToFunction(func="airSpeed")
	public int airIntensity;

	//@StartMonitoring
	public AirConditionerO(int temperature) {
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

	public void setAirIntensity(int value) {
		airIntensity = value;
	}

	public void setRoomTemperature(int roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	//@MethodToLocation(func="airSpeed", args={})
	public int getAirIntensity() {
		return airIntensity;
	}

	
}