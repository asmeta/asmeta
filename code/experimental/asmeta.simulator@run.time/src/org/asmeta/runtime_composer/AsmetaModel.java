package org.asmeta.runtime_composer;

/**
 * @author Michele Zenoni
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.impl.OutFunctionImpl;

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
	
	/**
	 * @deprecated use RunOutput getOutvalues() instead
	 */
	public Map<String, String> getOutValues(){
		Map<String, String> controlled = output.getControlledvalues();
		Map<String, String> out = new HashMap<>();
		List<String> names = new ArrayList<>();
		
		names = getAllOut(names, getModelPath());
		
		if(names != null) {
			for(String function: names) {
				if(controlled.containsKey(function)) {
					out.put(function, controlled.get(function));
				} else {
					for(String functionName: controlled.keySet()) {
						if(functionName.contains(function)) {
							out.put(functionName, controlled.get(functionName));
						}
					}
				}
			}
		}
		return out;
	}
	
	/**
	 * @deprecated use RunOutput getOutvalues() instead
	 */
	private List<String> getAllOut(List<String> outList, String modelPath){
		String root = modelPath.substring(0, modelPath.lastIndexOf("/") + 1);
		if(root.isEmpty()) {
			root = modelPath.substring(0, modelPath.lastIndexOf("\\") + 1);
		}
		if (!modelPath.equals("")) {
			File asmFile = new File(modelPath);
			if (asmFile.exists()) {
				AsmCollection asm;
				try {
					asm = ASMParser.setUpReadAsm(asmFile);
					for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
						Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
						if (f instanceof OutFunctionImpl) {
							outList.add(f.getName());
						}
					}
					
					int importSize = asm.getMain().getHeaderSection().getImportClause().size();
					for (int i = 0; i < importSize; i++) {
						String moduleName = asm.getMain().getHeaderSection().getImportClause().get(i).getModuleName();
						if(!moduleName.toLowerCase().endsWith("standardlibrary")) {	//Skips the StandardLibrary.asm
							outList = getAllOut(outList, root + moduleName + ASMParser.ASM_EXTENSION);
						}
					}
				} catch (Exception e) {
					outList = null;
					e.printStackTrace();
				}			
			}
		}
		return outList;
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
		if(!path.isEmpty() && path.indexOf(ASMParser.ASM_EXTENSION) != -1 && path.indexOf("\\") >= 0) {
			return (path.substring(path.lastIndexOf("\\") + 1));
		 } else if(!path.isEmpty() && path.indexOf(ASMParser.ASM_EXTENSION) != -1 && path.indexOf("/") >= 0) {
			 return (path.substring(path.lastIndexOf("/") + 1));
		 }
		return path;
	}
}
