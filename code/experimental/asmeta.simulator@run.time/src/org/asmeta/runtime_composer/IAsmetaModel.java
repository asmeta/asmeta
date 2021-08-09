package org.asmeta.runtime_composer;

/**
 * @author Michele Zenoni
 */

import java.util.Map;

import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_container.SimulationContainer;

public interface IAsmetaModel {
	/**
	 * Execute the model's Simulation Container runStep(...) method.
	 * @param input: the input location-value map.
	 * @return the output of the execution as a RunOutput object.
	 */
	public RunOutput runStep(Map<String, String> input);

	/**
	 * Execute the model's Simulation Container runStepTimeout(...) method.
	 * @param input: the input location-value map.
	 * @param timeout: the maximum time for the model execution.
	 * @return the output of the execution as a RunOutput object.
	 */
	public RunOutput runStepTimeout(Map<String, String> input, int timeout);
	
	/**
	 * Execute the model's Simulation Container runUntilEmpty(...) method.
	 * @param input: the input location-value map.
	 * @param max: the maximum number of steps for the model execution.
	 * @return the output of the execution as a RunOutput object.
	 */
	public RunOutput runUntilEmpty(Map<String, String> input, int max);
	
	/**
	 * Execute the model's Simulation Container runUntilEmptyTimeout(...) method.
	 * @param input: the input location-value map.
	 * @param max: the maximum number of steps for the model execution.
	 * @param timeout: the maximum time for the model execution.
	 * @return the output of the execution as a RunOutput object.
	 */
	public RunOutput runUntilEmptyTimeout(Map<String, String> input, int max, int timeout);
	
	/**
	 * Execute the model's Simulation Container appropriate method according to
	 * the parameters value (max and timeout can be negative values).
	 * @param input: the input location-value map.
	 * @param max: the maximum number of steps for the model execution.
	 * @param timeout: the maximum time for the model execution.
	 * @return the output of the execution as a RunOutput object.
	 */
	public RunOutput run(Map<String, String> input, int max, int timeout);
	
	/**
	 * Getter method for the last output saved.
	 * @return the last output as a RunOutput object.
	 */
	public RunOutput getLastOutput();
	
	/**
	 * Getter method for the Simulation Container instance.
	 * @return the SimulationContainer instance.
	 */
	public SimulationContainer getSimulationContainer();
	
	/**
	 * Getter method for the model name (it should end with ".asm").
	 * @return the model name.
	 */
	public String getModelName();
	
	/**
	 * Getter method for the model absolute path.
	 * @return the model absolute path.
	 */
	public String getModelPath();
	
	/**
	 * Getter method for the model ID.
	 * @return the model ID in its SimulationContainer instance.
	 */
	public int getModelId();

	/**
	 * Getter method for the Simulation Container ID.
	 * @return the model's SimulationContainer instance ID.
	 */
	public int getSimulatorId();
}
