package org.asmeta.codegenerator.configuration;

import org.asmeta.codegenerator.arduino.ArduinoPin;
import org.asmeta.codegenerator.arduino.ArduinoPinID;

import com.google.gson.annotations.SerializedName;

public class LCD {

	@SerializedName("name")
	private String name;
	@SerializedName("function")
	private String function;
	@SerializedName("pin0")
	private String pin0;
	@SerializedName("pin1")
	private String pin1;
	@SerializedName("pin2")
	private String pin2;
	@SerializedName("pin3")
	private String pin3;
	@SerializedName("pin4")
	private String pin4;
	@SerializedName("pin5")
	private String pin5;
	@SerializedName("numberofrows")
	private int numberofrows;
	@SerializedName("numberofcolumns")
	private int numberofcolumns;
	@SerializedName("isi2c")
	private Boolean isi2c;
	@SerializedName("address")
	private String address;
	
	public ArduinoPinID getPin0() {
		return ArduinoPinID.fromString(pin0);
	}
	public void setPin0(String pin0) {
		this.pin0 = pin0;
	}
	public ArduinoPinID getPin1() {
		return ArduinoPinID.fromString(pin1);
	}
	public void setPin1(String pin1) {
		this.pin1 = pin1;
	}
	public ArduinoPinID getPin2() {
		return ArduinoPinID.fromString(pin2);	
	}
	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}
	public ArduinoPinID getPin3() {
		return ArduinoPinID.fromString(pin3);		
	}
	public void setPin3(String pin3) {
		this.pin3 = pin3;
	}
	public ArduinoPinID getPin4() {
		return ArduinoPinID.fromString(pin4);
	}
	public void setPin4(String pin4) {
		this.pin4 = pin4;
	}
	public ArduinoPinID getPin5() {
		return ArduinoPinID.fromString(pin5);
	}
	public void setPin5(String pin5) {
		this.pin5 = pin5;
	}
	public int getNumberofrows() {
		return numberofrows;
	}
	public void setNumberofrows(int numberofrows) {
		this.numberofrows = numberofrows;
	}
	public int getNumberofcolumns() {
		return numberofcolumns;
	}
	public void setNumberofcolumns(int numberofcolumns) {
		this.numberofcolumns = numberofcolumns;
	}
	public String getName() {
		if(name!=null && name.length()>0)
			return name;
		else
			return "lcd";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}	
	public Boolean getIsi2c() {
		return isi2c;
	}
	public void setIsi2c(Boolean isi2c) {
		this.isi2c = isi2c;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString(){
		return "lcd" + name +"("+pin0+","+pin1+","+pin2+","+pin3+","+pin4+","+pin5+") "+"["+numberofcolumns+"x"+numberofrows+"]";
	}
	
}
