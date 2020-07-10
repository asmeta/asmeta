package org.asmeta.codegenerator.arduino;

public class ArduinoMode {
	private String name = "";
	private int minValue = 0;
	private int maxValue = 0;
	private ArduinoPinFeature pinFeature;
	
	public ArduinoMode(String name, ArduinoPinFeature pinFeature) {
		this.name = name;
		this.minValue = 0;
		this.maxValue = 0;
		this.pinFeature = pinFeature;
	}

	public ArduinoMode(String name, int minValue, int maxValue, ArduinoPinFeature pinFeature) {
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.pinFeature = pinFeature;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getMinValue() {
		return this.minValue;
	}
	
	public int getMaxValue() {
		return this.maxValue;
	}
	
	public ArduinoPinFeature getPinFeature() {
		return this.pinFeature;
	}
}
