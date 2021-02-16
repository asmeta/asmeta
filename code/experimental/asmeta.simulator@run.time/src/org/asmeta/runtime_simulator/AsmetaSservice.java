package org.asmeta.runtime_simulator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.animator.MyState;
import org.asmeta.parser.ASMParser;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.Simulator;

import asmeta.AsmCollection;

/**
 * 
 * @author Simone Giusso, Patrizia Scandurra
 * Manage multiple instances of Simulator by providing an interface.
 * See documentation for more details.
 */

public class AsmetaSservice implements IAsmetaSservice{
	
	private int maxSimInstance;	//maximum number of simulators
	private boolean id[];	//id. If cell is free id(index of cell) is available
	//private static AsmetaSserviceNotSing service;	//For singleton
	private Map<Integer, InfoAsmetaService> simulatorMap;	//Map id -> created instance of the simulator (see InfoAsmetaService)
	
	public AsmetaSservice() {}
	
	/**
	 * SINGLETON
	 * @return AsmetaSservice instance
	 */
	/*public static AsmetaSserviceNotSing getInstance(){
		if( service == null ) {
			service = new AsmetaSserviceNotSing();
		}
		
		return service;
	}*/
	
	/**
	 * TODO USED IN SOME TESTS BEFORE REMOVING SINGLETON PATTERN
	 * @return AsmetaSservice instance
	 */
	/*public static void setInstance(AsmetaSserviceNotSing instance) {
		if (instance!=null)
		service=instance;
	}*/

	/**
	 * Set the initial data of this singleton. It must be call just once before use this class
	 */
	public void init(int maxSimInstance) {
		this.maxSimInstance = maxSimInstance;
		id = new boolean[maxSimInstance];
		simulatorMap = new HashMap<Integer, InfoAsmetaService>();
		for(int i = 0; i < maxSimInstance; i++) {	//Initialize all id available
			id[i] = true;
		}
	}
	
	/**
	 * 
	 * @param modelName
	 * @return the id of the simulator generate, -1 if simulator map is full
	 * @throws AsmModelNotFoundException, MainRuleNotFoundException
	 */
	public int start(String modelPath) throws Exception{
		File asmFile = new File(modelPath);
		
		if (!asmFile.exists()) {
			throw new AsmModelNotFoundException(modelPath);
		}
	
		Logger.getLogger("org.asmeta.parser").addAppender( new WriterAppender(new PatternLayout("%m%n"), System.out)); //Patrizia Jan 2021: to avoid log4J:WARN messages
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.simulator").addAppender( new WriterAppender(new PatternLayout("%m%n"), System.out));
		Logger.getLogger("org.asmeta.simulator").setLevel(Level.OFF);
		AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
	
		String modelName = asm.getMain().getName();
		Environment env = new Environment(new AsmetaSserviceRun(simulatorMap));
		SimulatorRT sim = new SimulatorRT(modelName, asm, env);
		
		

		
		int id = getFirstFreeId();
		
		if(id != -1) {
			simulatorMap.put(id, new InfoAsmetaService(sim));
			simulatorMap.get(id).setModelPath(modelPath);
		}
		
		
		return id;
	}
	/**
	 * tries to start a simulation that begins already on the given state, behaves the same as start.
	 *
	 * @param modelPath the model path
	 * @param state the state to load as initial one
	 * @return the id of the simulator generate, -1 if simulator map is full
	 * @throws AsmModelNotFoundException, MainRuleNotFoundException
	 */
	public int restart(String modelPath,int oldId, State s) throws Exception{
		
		File asmFile = new File(modelPath);
		
		if (!asmFile.exists()) {
			throw new AsmModelNotFoundException(modelPath);
		}
	
		AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
		String modelName = asm.getMain().getName();
		Environment env = new Environment(new AsmetaSserviceRun(simulatorMap));
		SimulatorRT sim = new SimulatorRT(modelName, asm, env, s);
		
		int id = oldId;;
		if (this.id[id-1]) 			//use again the same id if it's free 
			this.id[id-1]=false;	//lock it again 
		else 
			id=getFirstFreeId();
		
		if(id != -1) {
			simulatorMap.put(id, new InfoAsmetaService(sim));
			simulatorMap.get(id).setModelPath(modelPath);
		}
		
		
		return id;
	}
	
	/**
	 * Execute a simulator step.
	 * @param id
	 * @return MyState: the new state with monitored and controlled values.
	 * Can Throw UpdateClashException, InvalidInvariantException, IdNotFoundException or InputMismatchException
	 */
	public MyState run(int id) {
		return run(id, null);
	}
	
	/**
	 * Execute a simulator step.
	 * @param id of the simulator
	 * @param locationValue the monitored function.
	 * @return MyState: the new state with monitored and controlled values.
	 * Can Throw UpdateClashException, InvalidInvariantException, IdNotFoundException or InputMismatchException
	 */
	public MyState run(int id, Map<String, String> locationValue) {
		checkId(id);
		simulatorMap.get(id).setLocationValue(locationValue);	//locationValue is a sharing variable with runner
		AsmetaSserviceRun runner = new AsmetaSserviceRun(id,simulatorMap);
		runner.run(RunMode.RUN_ONE_STEP);
		simulatorMap.get(id).incContSim();
		
		return simulatorMap.get(id).getState();
	}
	
	/**
	 * Execute a simulator until main rule is empty.
	 * @param id of the simulator
	 * @param locationValue the monitored function.
	 * @return MyState: the new state with monitored and controlled values.
	 * Can Throw UpdateClashException, InvalidInvariantException, IdNotFoundException or InputMismatchException
	 */
	public MyState runUntilEmpty(int id, Map<String, String> locationValue, int max) {
		checkId(id);
		simulatorMap.get(id).setLocationValue(locationValue);	//locationValue is a sharing variable with runner
		
		if(max > 0) {
			simulatorMap.get(id).getSim().setMax(max);
		}
		
		AsmetaSserviceRun runner = new AsmetaSserviceRun(id,simulatorMap);
		runner.run(RunMode.RUN_UNTIL_EMPTY);
		simulatorMap.get(id).incContSim();
		
		return simulatorMap.get(id).getState();
	}
	
	public MyState runUntilEmpty(int id, int max) {
		return runUntilEmpty(id, null, max);
	}
	
	public MyState runUntilEmpty(int id) {
		return runUntilEmpty(id, null, 0);
	}
	
	public MyState runUntilEmpty(int id, Map<String, String> locationValue) {
		return runUntilEmpty(id, locationValue, 0);
	}
	
	/**
	 * remove a simulator object by id
	 * @param id
	 */
	public void stop(int id) {
		checkId(id);
		this.id[id-1] = true;
		simulatorMap.remove(id);
	}
	
	/**
	 * 
	 * @return the first id available (starting from 1 to maxSimInstance), -1 if the simulatorMap is full
	 */
	private int getFirstFreeId() {
		
		for(int i = 0; i < maxSimInstance; i++) {
			if(id[i]) {
				id[i] = false;
				return i+1;
			}
		}
		
		return -1;
	}
	
	public Map<Integer, InfoAsmetaService> getSimulatorTable() {
		return simulatorMap;
	}
	
	/**
	 * Print simulator Map
	 */
	public void printSimulatorTable() {
		System.out.println("SIMULATOR MAP");
		System.out.println("id\tmodelName\tstep number\tstate");
		
		for (Entry<Integer, InfoAsmetaService> entry : simulatorMap.entrySet())
		{
			System.out.println(entry.getKey().toString() + "\t" + entry.getValue().getSim().getAsmModel().getName() + "\t" + entry.getValue().getContSim()
					+ "\t\t" + "Controlled: " + entry.getValue().getState().getControlledValues() + " Monitored: " + entry.getValue().getState().getMonitoredValues());
		}
	}

	@Override
	public MyState getCurrentState(int id) {
		checkId(id);
		return simulatorMap.get(id).getState();
	}

	@Override
	public String getModelName(int id) {
		checkId(id);
		return simulatorMap.get(id).getSim().getAsmModel().getName();
	}

	/**
	 * Return to previous state
	 */
	@Override
	public MyState rollback(int id) {
		checkId(id);
		simulatorMap.get(id).getSim().rollBack();
		
		State state = simulatorMap.get(id).getSim().getCurrentState();
		State previousState = simulatorMap.get(id).getSim().previousState;
		
		this.getSimulatorTable().get(id).setState(new MyState(state.getContrLocs(false), null));
		this.getSimulatorTable().get(id).setPreviousState(new MyState(previousState.getContrLocs(false), null));
		
		return simulatorMap.get(id).getState();
	}
	
	/**
	 * Return to previous state before call runUntilEmpty. If before this call, runUntilEmpty not call, return current state.
	 */
	@Override
	public MyState rollbackToState(int id) {
		checkId(id);
		simulatorMap.get(id).getSim().rollBackToState();
		
		State state = simulatorMap.get(id).getSim().getCurrentState();
		State previousState = simulatorMap.get(id).getSim().previousState;
		
		this.getSimulatorTable().get(id).setState(new MyState(state.getContrLocs(false), null));
		this.getSimulatorTable().get(id).setPreviousState(new MyState(previousState.getContrLocs(false), null));
		
		return simulatorMap.get(id).getState();
	}

	/**
	 * reset a simulator to initial state
	 * @throws AsmModelNotFoundException, MainRuleNotFoundException, IdNotFoundException
	 */
	@Override
	public void reset(int id) throws Exception {
		checkId(id);
		SimulatorRT sim = new SimulatorRT(simulatorMap.get(id).getSim().getAsmModel().getName(), 
				simulatorMap.get(id).getSim().getAsmCollection(), new Environment(new AsmetaSserviceRun(simulatorMap)));
		
		simulatorMap.put(id, new InfoAsmetaService(sim));
	}
	
	/**
	 * 
	 * @param id
	 * return an exception if id is not available
	 */
	private void checkId(int id) {
		if(id < 1 || id > maxSimInstance)
			throw new IdNotFoundException("The id must be between 1 and " + maxSimInstance + ".");
		
		for(int i = 0; i < maxSimInstance; i++) {
			if(this.id[i] && id == i+1) {
				
				String validId = ""; //Search valid id
				for(int j = 0; j < maxSimInstance; j++) {
					if(!this.id[j]) {
						validId += j+1 + ", ";
					}
				}
				validId = validId.substring(0, validId.length() - 2); //remove last ", ".
				
				throw new IdNotFoundException("This id is not used. Valid id are: " + validId + ".");
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * returns true if given id is a correct simulation
	 */
	public boolean checkValidId(int id) {
		if(id < 1 || id > maxSimInstance)
			return false;
		return !this.id[id-1];
	}
	
	/**
	 *
	 * returns maxSimInstance parameter
	 */
	public int getMaxInstances() {
		return maxSimInstance;
	}
	

}
