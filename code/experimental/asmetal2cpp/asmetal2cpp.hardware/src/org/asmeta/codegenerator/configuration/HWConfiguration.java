package org.asmeta.codegenerator.configuration;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.codegenerator.arduino.ArduinoBoard;
import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.codegenerator.configuration.*;

import com.google.gson.annotations.SerializedName;

/**
 * @author Marco The Java Object that contains all the information about the
 *         JSON configuration file
 */
public class HWConfiguration {
	@SerializedName("arduinoVersion")
	private String arduinoVersion;
	@SerializedName("modelPath")
	private String modelPath;
	@SerializedName("arduinoCodePath")
	private String arduinoCodePath;

	@SerializedName("stepTime")
	private Integer stepTime;
	@SerializedName("bindings")
	private List<Binding> bindings = new ArrayList<Binding>();
	@SerializedName("lcd")
	private LCD lcd;

	@Override
	public String toString() {
		String bindingsStr = bindings.toString();

		if (bindings != null)
			return "[ v." + arduinoVersion + ", " + "steptime=" + stepTime + "]" + "#binding=" + bindingsStr;
		else
			return "[ v. " + arduinoVersion + ", " + "steptime=" + stepTime + "]" + "#binding= null";
	}

	public ArduinoVersion createArduinoVersion() {
		if (ArduinoVersion.fromString(arduinoVersion) != null)
			return ArduinoVersion.fromString(arduinoVersion);
		throw new RuntimeException("Error: ArduinoVersion'" + arduinoVersion + "' unknown");
	}

	public boolean isValid() {
		ArduinoBoard arduino = new ArduinoBoard(createArduinoVersion());

		for (Binding binding : bindings) {
			// Check whether every binding have all the parameters
			//System.out.println("bindings in HWConfiguration " + binding);
			if (!binding.isDataComplete()) {
				System.out.println("Data not complete.");
				return false;}
		}
		// TODO Check whether a pin has assigned a supported binding (example A0
		// with Analog is supported)

		// Check whether a pin is used only once
		if (bindings.size() > 1)
			for (int i = 0; i < bindings.size() - 1; i++)
				for (int j = i + 1; j < bindings.size(); j++)
					if (bindings.get(i).getPin().equals(bindings.get(j).getPin())) {
						System.out.println("Pin allocated twice " + bindings.get(i));
						return false;}
		return true;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getArduinoCodePath() {
		return arduinoCodePath;
	}

	public void setArduinoCodePath(String arduinoCodePath) {
		this.arduinoCodePath = arduinoCodePath;
	}

	public String getArduinoVersion() {
		return arduinoVersion;
	}

	public void setArduinoVersion(String arduinoVersionStr) {
		this.arduinoVersion = arduinoVersionStr;
	}

	public Integer getStepTime() {
		return stepTime;
	}

	public void setStepTime(Integer stepTime) {
		this.stepTime = stepTime;
	}

	public List<Binding> getBindings() {
		return bindings;
	}

	public void setBindings(List<Binding> bindings) {
		this.bindings = bindings;
	}

	public LCD getLcd() {
		return lcd;
	}

	public void setLcd(LCD lcd) {
		this.lcd = lcd;
	}
	
	public void clearBindings() {
		bindings.clear();
	}
}
