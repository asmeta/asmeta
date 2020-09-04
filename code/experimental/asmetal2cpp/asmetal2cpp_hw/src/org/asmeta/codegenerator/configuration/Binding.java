package org.asmeta.codegenerator.configuration;

import org.asmeta.codegenerator.ConfigurationMode;
import org.asmeta.codegenerator.arduino.ArduinoPinID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Binding {

	@SerializedName("mode")
	private String mode;
	@SerializedName("function")
	private String function;
	@SerializedName("pin")
	private String pin;
	@SerializedName("minval")
	private double minval;
	@SerializedName("maxval")
	private double maxval;
	@SerializedName("offset")
	private double offset;

	public boolean isDataComplete() {
		if (mode == null)
			return false;
		else {
			switch (ConfigurationMode.fromString(mode)) {
			case DIGITAL:
			case DIGITALINVERTED:
			case ANALOGLINEARIN:
			case ANALOGLINEAROUT:
			case PWM:
			case SWITCH:
				return (function != null && pin != null);
			case USERDEFINED:
				return (function != null);
			default:
				throw new RuntimeException("Error: unknown binding mode'" + mode + "' found!");
			}
		}
	}

	@Override
	public String toString() {
		return (mode + " {" + function + "->" + pin + "}")
				+ ((minval != maxval) ? ("[" + minval + ":" + maxval + "]") : (""));
	}

	public double getMinVal() {
		return minval;
	}

	public void setMinVal(double minVal) {
		this.minval = minVal;
	}

	public double getMaxVal() {
		return maxval;
	}

	public void setMaxVal(double maxVal) {
		this.maxval = maxVal;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setOffset(double offset) {
		this.offset = offset;
	}
	
	public double getOffset() {
		return this.offset;
	}

	public ConfigurationMode getConfigMode() {
		ConfigurationMode cm = ConfigurationMode.fromString(mode);
		if (cm != null)
			return cm;
		throw new RuntimeException("Error: configuration mode '" + mode + "' unknown");
	}

	public ArduinoPinID getArduinoPin() {
		ArduinoPinID id = ArduinoPinID.fromString(pin);
		if (id != null)
			return id;
		throw new RuntimeException("Error: pin ID '" + pin + "' unknown.");
	}

}
