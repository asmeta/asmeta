package org.asmeta.runtime_composer;

/**
 * @author Michele Zenoni
 */

import java.util.Map;

import org.asmeta.runtime_container.RunOutput;

public interface IModelComposition {
	/**
	 * Get the root node of the composition syntax tree,
	 * it could return null.
	 * @return the root node of the composition syntax tree.
	 */
	public CompositionTreeNode getCompositionTreeRoot();
	
	/**
	 * Get the last composition output.
	 * It can be null.
	 * @return the last composition output.
	 */
	public RunOutput getLastOutput();
	
	/**
	 * Simulation container's runStep(...) method on the composed simulation.
	 * @param id: the id of the first model of the composition.
	 * @param locationValue: the composition input.
	 * @throws CompositionException: when an error occurs while evaluating the composition tree.
	 */
	public void runStep(int id, Map<String, String> locationValue) throws CompositionException;
	
	/**
	 * Simulation container's runUntilEmpty(...) method on the composed simulation.
	 * @param id: the id of the first model of the composition.
	 * @param locationValue: the composition input.
	 * @param max: the maximum number of steps for each model to finisch its execution.
	 * @throws CompositionException: when an error occurs while evaluating the composition tree.
	 */
	public void runUntilEmpty(int id, Map<String, String> locationValue, int max) throws CompositionException;
	
	/**
	 * Simulation container's runStepTimeout(...) method on the composed simulation.
	 * @param id: the id of the first model of the composition.
	 * @param locationValue: the composition input.
	 * @param timeout: the maximum time for the whole composition to finish the execution.
	 * @throws CompositionException: when an error occurs while evaluating the composition tree.
	 */
	public void runStepTimeout(int id, Map<String, String> locationValue, int timeout) throws CompositionException;
	
	/**
	 * Simulation container's runUntilEmptyTimeout(...) method on the composed simulation.
	 * @param id: the id of the first model of the composition.
	 * @param locationValue: the composition input.
	 * @param max: the maximum number of steps for each model to finish its execution.
	 * @param timeout: the maximum time for the whole composition to finish the execution.
	 * @throws CompositionException: when an error occurs while evaluating the composition tree.
	 */
	public void runUntilEmptyTimeout(int id, Map<String, String> locationValue, int max, int timeout) throws CompositionException;
	
	/**
	 * Invoke the Simulation container's rollback method for each model
	 * in the composition model list.
	 * @throws CompositionRollbackException: if the composition model list is undefined or empty.
	 */
	public void compositionRollback() throws CompositionRollbackException;
	
	/**
	 * Invoke the Simulation container's rollbackToState method for each model
	 * in the composition model list.
	 * @throws CompositionRollbackException: if the composition model list is undefined or empty.
	 */
	public void compositionRollbackToState() throws CompositionRollbackException;
}
