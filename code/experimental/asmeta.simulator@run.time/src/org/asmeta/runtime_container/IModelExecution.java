package org.asmeta.runtime_container;

import java.util.Map;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.MainRuleNotFoundException;
import org.asmeta.parser.ParseException;



/**
 * The Interface IModelExecution.
 * @author Massoda Tchoussi Frank, Patrizia Scandurra
 */
public interface IModelExecution {
	
	 /**
 	 * Start a model execution.
 	 *
 	 * @param modelPath the model path
 	 * @return the simulation identifier (int); -1 if the maximum number of simulations is exceeded 
 	 */
 	int startExecution(String modelPath) throws MainRuleNotFoundException, AsmModelNotFoundException, FullMapException, ParseException;
	 
	 /**
 	 * Run step.
 	 *
 	 * @param locationValue the location value
 	 * @param modelPath the model path
 	 * @return the run output
 	 */
 	RunOutput runStep(int ids,Map<String, String> locationValue, String modelPath);
 	RunOutput runStep(int id);
	 
	 
	 /**
 	 * Stop execution.
 	 *
 	 * @param id the id
 	 * @return the int
 	 */
 	int stopExecution(int id);
	 
	 //String getInfo();
	 
	 /**
 	 * Inits the.
 	 *
 	 * @param maxSimInstance the max sim instance
 	 * @return the int
 	 */
 	int init(int maxSimInstance);
	
	 /**
 	 * Run until empty.
 	 *
 	 * @param locationValue the location value
 	 * @param modelPath the model path
 	 * @return the run output
 	 */
 	RunOutput runStepTimeout(int ids,Map<String, String> locationValue, String modelPath,int timeout);
 	RunOutput runStepTimeout(int id, int timeout);
 	
 	RunOutput runUntilEmpty(int id, Map<String, String> locationValue, String modelPath, int max);
 	RunOutput runUntilEmpty(int id, int max);
 	RunOutput runUntilEmpty(int id, String modelPath);
 	RunOutput runUntilEmpty(int id,  Map<String, String> locationValue, String modelPath);
 	RunOutput runUntilEmptyTimeout(int id, int max, int timeout);
 	RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, String modelPath, int max, int timeout);
 	RunOutput runUntilEmptyTimeout(int id, String modelPath, int timeout);
 	RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, String modelPath, int timeout);
 	
	  
}
