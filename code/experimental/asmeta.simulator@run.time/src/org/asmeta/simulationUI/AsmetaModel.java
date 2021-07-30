package org.asmeta.simulationUI;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public class AsmetaModel {
	private final int[] ID = new int[2]; // [containerInstanceID, modelID]
	private String modelName;
	private String modelPath;
	private SimulationContainer containerInstance;
	RunOutput output;
	ByteArrayOutputStream outputConsole;
	
	public AsmetaModel(int modelID, SimulationContainer contInstance) throws ModelCreationException {
		if(contInstance == null) {
			throw new ModelCreationException("The Simulation Container cannot be null!");
		}
		this.containerInstance = contInstance;
		this.ID[0] = containerInstance.getSimulatorId(); // Every instance of the Simulation Container must have an ID to allow distributed composition of models
		this.ID[1] = modelID;
		if(containerInstance.getLoadedIDs().get(getModelId()) != null) {
			this.modelName = clearPath(containerInstance.getLoadedIDs().get(getModelId()));
		} else {
			throw new ModelCreationException("Invalid AsmetaModel ID!");
		}
		this.output = null;
		this.outputConsole = new ByteArrayOutputStream();
	}
	
	public AsmetaModel(String modelName, SimulationContainer contInstance) throws ModelCreationException {
		if(contInstance == null) {
			throw new ModelCreationException("The Simulation Container cannot be null!");
		}
		this.containerInstance = contInstance;
		this.ID[0] = contInstance.getSimulatorId(); // Every instance of the Simulation Container must have an ID to allow distributed composition of models
		this.modelName = modelName;
		if(containerInstance.getLoadedModels().get(getModelPath()) != null) {
			this.ID[1] = containerInstance.getLoadedModels().get(getModelPath());
		} else {
			throw new ModelCreationException("Invalid AsmetaModel name!");
		}
		this.output = null;
		this.outputConsole = new ByteArrayOutputStream();
	}
	
	public RunOutput runStep(Map<String, String> input) { // input can be null
		output = containerInstance.runStep(getModelId(), input);
		return output;
	}
	
	// TODO: aggiungere runStepTimeout(...), runUntilEmpty(...) e runUntilEmptyTimeout(...)
	
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
