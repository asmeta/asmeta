package org.asmeta.runtime_simulator;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.animator.MyState;



/**
 * 
 * @author Simone Giusso
 * Contains all object sharing between AsmetaSservice (main Thread) and AsmetaSserviceRun.
 */

public class InfoAsmetaService {
	
	private SimulatorRT sim;
	private Map<String, String> locationValue;
	private int contSim;	//Simulator step counter
	private MyState state;	//Contains the new state after a step of Simulator
	private MyState previousState;
	private String modelPath;

	public InfoAsmetaService(SimulatorRT s) {
		sim = s;
		locationValue = new HashMap<>();
		contSim = 0;
	}

	/**
	 * increase by one when simulator do one step
	 * @param contSim
	 */
	public void incContSim() {
		contSim++;
	}
	
	public SimulatorRT getSim() {
		return sim;
	}
	
	public Map<String, String> getLocationValue() {
		return locationValue;
	}

	public void setLocationValue(Map<String, String> locationValue) {
		this.locationValue = locationValue;
	}

	public int getContSim() {
		return contSim;
	}
	
	public void setState(MyState state) {
		this.state = state;
	}

	public MyState getState() {
		return state;
	}
	
	public MyState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(MyState previousState) {
		this.previousState = previousState;
	}
	
	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

}
