package org.asmeta.codegenerator.arduino;

public enum ArduinoPinFeature {
	DIGITAL, ANALOGIN10, ANALOGIN12, PWM8, ANALOGOUT10, SERIAL;
	
	public static ArduinoPinFeature fromString(String arduinoPinFeature){
		for(ArduinoPinFeature apf: ArduinoPinFeature.values()){
			if(apf.name().equals(arduinoPinFeature))
				return apf;
		}
		return null;
	}
}
