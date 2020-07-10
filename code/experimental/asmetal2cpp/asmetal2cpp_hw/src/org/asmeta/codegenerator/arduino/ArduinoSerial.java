package org.asmeta.codegenerator.arduino;

public class ArduinoSerial {
	String serialname;
	ArduinoPinID tx;
	ArduinoPinID rx;
	
	public ArduinoSerial(){
		this("TX0", ArduinoPinID.D0,ArduinoPinID.D1);
	}
	
	public ArduinoSerial(String name, ArduinoPinID rx, ArduinoPinID tx){
		this.serialname = name;
		this.tx=tx;
		this.rx=rx;
	}
}
