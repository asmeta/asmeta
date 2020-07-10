package org.asmeta.codegenerator.arduino;

public enum ArduinoVersion {
	DUEMILANOVE, DIECIMILA, UNO, LILYPAD, ZERO, MEGA, MEGA2560;
	
	public static ArduinoVersion fromString(String arduinoVersion){
		for(ArduinoVersion av: ArduinoVersion.values()){
			if(av.name().equals(arduinoVersion))
				return av;
		}
		return null;
	}
}
