package asmeta.server.controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.runtime_container.RunOutput;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import asmeta.server.Application;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;
import models.StepInputModel;

@RestController
public class AsmetaController {
	
	static final String modelsFolderPath = "src/main/resources/models/";
	static final String librariesFolderPath = "src/main/resources/libraries/";
		
	
	@Operation(summary = "Models under executions", description = "Return the list of the actual running models")
	@GetMapping("/running-models") 
	public Map<String, Object> runningModels() {
		
		// Legge i modelli in esecuzione 
		Map<Integer, String> loadedModels = Application.sim.getLoadedIDs();
		
		// Converte il path da assoluto a relativo con solo il nome del modello
		Map<Integer, String> outputModels = new HashMap<>();
		for (Map.Entry<Integer, String> entry : loadedModels.entrySet()) {
	        Path p = Paths.get(entry.getValue());

	        outputModels.put(entry.getKey(), p.getFileName().toString());
	    }		
		
		Map<String, Object> returnVal = new HashMap<>(); 
		returnVal.put("models", outputModels);
		
		return returnVal;
	}
	
	@Operation(summary = "Model state", description = "Return the current state of a model")
	@GetMapping("/get-model-status")
	public Map<String,Object> getModelStatus(@RequestParam(value = "id", defaultValue = "0") int id) {
		RunOutput lastStepOutput = Application.sim.getCurrentState(id);
		
		Map<String, Object> returnVal = new HashMap<>(); 
		returnVal.put("id", id);
		returnVal.put("runOutput", lastStepOutput);
		String modelName = Application.sim.getAsmetaModel(id).getModelName();
		returnVal.put("modelName", modelName);
		returnVal.put("monitored", Application.sim.getMonitored(modelsFolderPath + modelName));
		
		return returnVal;
	}
	
	
	// EXECUTION API 
	
	@Operation(summary = "Start model", description = "Start the execution of a new model")
	@PostMapping("/start")
	public Map<String, Object> start(@RequestParam(value = "name", defaultValue = "railroadGate") String name) {
			String modelPath = modelsFolderPath + name; 
			File asmFile = new File(modelPath);
			int id = Application.sim.startExecution(modelPath);

			Map<String, Object> returnVal = new HashMap<>(); 
			returnVal.put("id", id);
			
			return returnVal;
					
	}
	
	@Operation(summary = "Make step", description = "Execute one step of a model with the user input")
	@PutMapping("/step")
	public Map<String, Object> step(@RequestBody StepInputModel body) {
			RunOutput stepOutput;
			if (body.monitoredVariables == null || body.monitoredVariables.keySet().size() == 0) {
				stepOutput = Application.sim.runStep(body.id);
			} else {
				stepOutput = Application.sim.runStep(body.id, body.monitoredVariables);
			}

			
			Map<String, Object> returnVal = new HashMap<>(); 
			returnVal.put("id", body.id);
			returnVal.put("runOutput", stepOutput);
			List<String> monitored = Application.sim.getMonitored(
						modelsFolderPath + 
						Application.sim.getAsmetaModel(body.id).getModelName()
					);
			
			returnVal.put("monitored", monitored);
			
			return returnVal;		
	}
	
	@Operation(summary = "Make step", description = "Execute one step of a model with the user input sended as one single string")
	@PutMapping("/step-single-input")
	public Map<String, Object> stepWithSingleString(@RequestBody StepInputModel body) {
			Map<String, String> monitoredInput = prepareInput(body.monitoredVariable);
			RunOutput stepOutput;
			if (monitoredInput.keySet().size() == 0) {
				stepOutput = Application.sim.runStep(body.id);
			} else {
				stepOutput = Application.sim.runStep(body.id, monitoredInput);
			}

			
			Map<String, Object> returnVal = new HashMap<>(); 
			returnVal.put("id", body.id);
			returnVal.put("runOutput", stepOutput);
			List<String> monitored = Application.sim.getMonitored(
						modelsFolderPath + 
						Application.sim.getAsmetaModel(body.id).getModelName()
					);
			
			returnVal.put("monitored", monitored);
			
			return returnVal;		
	}
	
	
	@Operation(summary = "Run until empty", description = "Run until empty")
	@PutMapping("/run-until-empty")
	public Map<String, Object> runUntilEmpty(@RequestBody StepInputModel body) {
			RunOutput stepOutput;
			if (body.monitoredVariables == null || body.monitoredVariables.keySet().size() == 0) {
				stepOutput = Application.sim.runUntilEmpty(body.id);
			} else {
				stepOutput = Application.sim.runUntilEmpty(body.id, body.monitoredVariables);
			}

			
			Map<String, Object> returnVal = new HashMap<>(); 
			returnVal.put("id", body.id);
			returnVal.put("runOutput", stepOutput);
			List<String> monitored = Application.sim.getMonitored(
						modelsFolderPath + 
						Application.sim.getAsmetaModel(body.id).getModelName()
					);
			
			returnVal.put("monitored", monitored);
			
			return returnVal;		
	}
	
	
	@Operation(summary = "Stop model", description = "Delete and stop a running model")
	@DeleteMapping("/stop-model")
	public Map<String, Object> stopRunningModel(@RequestParam(value = "id", defaultValue = "0") int id) {
			Map<String, Object> returnVal = new HashMap<>(); 
			
			int val = Application.sim.stopExecution(id);
			if (val > 0)
				returnVal.put("status", true);
			else 
				returnVal.put("status", false);
			
			return returnVal;		
	}	

	private Map<String, String> prepareInput(String cmd) {	
		Map<String, String> data = new HashMap<>();
		String[] input = cmd.split(" ");
		
		for(int i = 0; i < input.length; i += 2)
			data.put(input[i], input[i+1]);
		
		return data;
	}
}
