package org.asmeta.runtime_composer;

/**
 * @author Michele Zenoni
 */

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class AsmetaModel implements IAsmetaModel {
	private final int[] ID = new int[2]; // [containerInstanceID, modelID]
	private String modelName;
	private String modelPath;
	private SimulationContainer containerInstance;
	private long executionTime;
	RunOutput output;
	public ByteArrayOutputStream outputConsole;
	
	public AsmetaModel(int modelID, SimulationContainer contInstance) throws ModelCreationException {
		if(contInstance == null) {
			throw new ModelCreationException("The Simulation Container cannot be null!");
		}
		this.containerInstance = contInstance;
		this.ID[0] = containerInstance.getSimulatorId(); // Every instance of the Simulation Container must have an ID to allow the distributed composition of models
		this.ID[1] = modelID;
		if(containerInstance.getLoadedIDs().get(getModelId()) != null) {
			this.modelName = clearPath(containerInstance.getLoadedIDs().get(getModelId()));
		} else {
			throw new ModelCreationException("Invalid AsmetaModel ID!");
		}
		this.executionTime = 0L;
		this.output = null;
		this.outputConsole = new ByteArrayOutputStream();
	}
	
	public AsmetaModel(String modelName, SimulationContainer contInstance) throws ModelCreationException {
		if(contInstance == null) {
			throw new ModelCreationException("The Simulation Container cannot be null!");
		}
		this.containerInstance = contInstance;
		this.ID[0] = contInstance.getSimulatorId(); // Every instance of the Simulation Container must have an ID to allow the distributed composition of models
		this.modelName = modelName;
		if(containerInstance.getLoadedModels().get(getModelPath()) != null) {
			this.ID[1] = containerInstance.getLoadedModels().get(getModelPath());
		} else {
			throw new ModelCreationException("Invalid AsmetaModel name!");
		}
		this.executionTime = 0L;
		this.output = null;
		this.outputConsole = new ByteArrayOutputStream();
	}
	
	public RunOutput runStep(Map<String, String> input) { // input can be null
		output = containerInstance.runStep(getModelId(), input);
		return output;
	}
	
	public RunOutput runStepTimeout(Map<String, String> input, int timeout) { // input can be null
		if(timeout <= 0) {
			return null;
		}
		output = containerInstance.runStepTimeout(getModelId(), input, timeout);
		return output;
	}
	
	public RunOutput runUntilEmpty(Map<String, String> input, int max) { // input can be null
		if(max < 0) {
			return null;
		}
		output = containerInstance.runUntilEmpty(getModelId(), input, max);
		return output;
	}
	
	public RunOutput runUntilEmptyTimeout(Map<String, String> input, int max, int timeout) { // input can be null
		if(max < 0 || timeout <= 0) {
			return null;
		}
		output = containerInstance.runUntilEmptyTimeout(getModelId(), input, max, timeout);
		return output;
	}
	
	public RunOutput run(Map<String, String> input, int max, int timeout) { // input can be null
		if(max < 0) { 			// runStep(...) or runStepTimeout(...)
			if(timeout <= 0) { 	// runStep(...)
				output = this.runStep(input);
			} else { 			// runStepTimeout(...)
				output = this.runStepTimeout(input, timeout);
			}
		} else { 				// runUntilEmpty(...) or runUntilEmptyTimeout(...)
			if(timeout <= 0) {	// runUntilEmpty(...)
				output = this.runUntilEmpty(input, max);
			} else {			// runUntilEmptyTimeout(...)
				output = this.runUntilEmptyTimeout(input, max, timeout);
			}
		}
		return output;
	}
	
	public RunOutput getLastOutput() {
		return output;
	}
	
	public SimulationContainer getSimulationContainer() {
		return containerInstance;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public String getModelPath() {
		return Commander.getDefaultModelDir() + "/" + modelName;
	}
	
	public int getModelId() {
		return ID[1];
	}
	
	
	public int getSimulatorId() {
		return ID[0];
	}
	
	public void setExecutionTime(long duration) {
		if(duration >= 0) {
			this.executionTime = duration;
		}
	}
	
	public long getExecutionTime() {
		return this.executionTime;
	}
	
	/**
	 * Helper function to get the asm model filename from its absolute path.
	 * If the absolute path format is incorrect the function returns 
	 * the absolute path unchanged. 
	 * 
	 * @param path: the absolute path of the asm model
	 * @return: the filename of the asm model 
	 */
	public static String clearPath(String path) {
		if(!path.isEmpty() && path.indexOf(".asm") != -1 && path.indexOf("\\") >= 0) {
			return (path.substring(path.lastIndexOf("\\") + 1));
		 } else if(!path.isEmpty() && path.indexOf(".asm") != -1 && path.indexOf("/") >= 0) {
			 return (path.substring(path.lastIndexOf("/") + 1));
		 }
		return path;
	}
}
