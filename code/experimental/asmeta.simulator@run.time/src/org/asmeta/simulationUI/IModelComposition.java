package org.asmeta.simulationUI;

/**
 * @author Michele Zenoni
 */
import java.util.Map;

import org.asmeta.runtime_commander.CompositionTreeNode;
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
	 * Simulation container's Run Step function on the composed simulation.
	 * @param initialOutput: the output of the first model.
	 * @param multiConsole: support for multiple consoles execution.
	 * @throws CompositionException: when the bidirectional pipe is not called only on two models.
	 * @throws ModelCreationException 
	 */
	public void runStep(int id, Map<String, String> locationValue) throws CompositionException, ModelCreationException;
	
	/**
	 * Simulation container's Run Until Empty function on the composed simulation.
	 * @param initialOutput: the output of the first model.
	 * @param multiConsole: support for multiple consoles execution.
	 */
	public void runUntilEmpty(int id, Map<String, String> locationValue);
	
	/**
	 * Simulation container's Run Step with Timeout function on the composed simulation.
	 * @param initialOutput: the output of the first model.
	 * @param timeout: the positive integer timeout value.
	 * @param multiConsole: support for multiple consoles execution.
	 */
	public void runStepTimeout(int id, Map<String, String> locationValue, int timeout);
	
	/**
	 * Simulation container's Run Until Empty with Timeout function on the composed simulation.
	 * @param initialOutput: the output of the first model.
	 * @param timeout: the positive integer timeout value. 
	 * @param multiConsole: support for multiple consoles execution.
	 */
	public void runUntilEmptyTimeout(int id, Map<String, String> locationValue, int timeout);
}
