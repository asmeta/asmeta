package org.asmeta.runtime_simulator;


import java.util.Map;

import org.asmeta.animator.MyState;


/**
 * AsmetaSservice Interface. See implementation for comment
 * 
 * @author Simone Giusso
 * 
 */
public interface IAsmetaSservice {
	
	public int start(String modelName) throws Exception;
	
	public MyState run(int id, Map<String, String> locationValue);
	
	public MyState run(int id);
	
	public MyState runUntilEmpty(int id, Map<String, String> locationValue, int max); //aggiunto da frank
	
	public MyState runUntilEmpty(int id, int max); //aggiunto da frank
	
	public void stop(int id);
	
	public Map<Integer, InfoAsmetaService> getSimulatorTable();
	
	public MyState getCurrentState(int id);
	
	public String getModelName(int id);
	
	public MyState rollback(int id);
	
	public MyState rollbackToState(int id);
	
	public void reset(int id) throws Exception;
}
