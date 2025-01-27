package org.asmeta.codegenerator;

import org.asmeta.codegenerator.arduino.ArduinoPinFeature;

public enum ConfigurationMode {
	DIGITAL, DIGITALINVERTED, ANALOGLINEARIN, ANALOGLINEAROUT, PWM, USERDEFINED, SWITCH;
	
	public static ConfigurationMode fromString(String configMode){
		for(ConfigurationMode cm: ConfigurationMode.values()){
			if(cm.name().equals(configMode))
				return cm;
		}
		throw new RuntimeException("Error:" + configMode + " unknown!");
	}
}
