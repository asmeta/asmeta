package org.asmeta.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.animator.MyState;
import org.asmeta.runtime_simulator.AsmetaSservice;
import org.asmeta.runtime_simulator.IdNotFoundException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

public class TestRuntimeSimulator {

	
	/**
	 * Run one model without monitored function
	 * @throws Exception
	 */
	@Test
	public void AdvanceClockTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("MODEL WITHOUT MONITORED TEST");
		
		String modelName = "examples/AdvancedClock.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		
		for(int i = 0; i < 10; i++) {
			printState(i, asmS.run(id, null));
		}
		
		System.out.println();
		asmS.printSimulatorTable();
		
		assertEquals(10, asmS.getSimulatorTable().get(id).getContSim()); //Controlled number of runs
	}
	
	/**
	 * Run model with monitored function
	 * @throws Exception
	 */
	@Test
	public void AdvanceClock2Test() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("MODEL WITH MONITORED TEST");
		
		String modelName = "examples/AdvancedClock2.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		Map<String, String> locationValue = new HashMap<String, String>();
		int id = asmS.start(modelName);
		
		locationValue.put("signal", "true");
		printState(0, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(1, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(2, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(3, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(4, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(5, asmS.run(id, locationValue));
		
		assertEquals(6, asmS.getSimulatorTable().get(id).getContSim()); //Controlled number of runs
		
		System.out.println();
		asmS.printSimulatorTable();
	}
	
	/**
	 * Test UpdateClashException
	 * @throws Exception
	 */
	@Test
	public void IncosistendUpdateTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("UPDATECLASHEXCEPTION TEST");
		
		String modelName = "examples/updateClash.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);

		try{
			asmS.run(id);
			asmS.run(id);
			asmS.run(id);
		}catch(UpdateClashException e) {
			System.out.println("UpdateClashException launched by run method");
		}
	}
	
	/**
	 * Test InvariantException
	 * @throws Exception
	 */
	@Test
	public void InvariantTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("INVARIANTEXCEPTION TEST");
		
		
		String modelName = "examples/Invariants.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		
		for(int i = 0; i < 5; i++) {
			printState(i, asmS.run(id));
		}
		
		try{
			asmS.run(id);
		}catch(InvalidInvariantException e) {
			System.out.println("InvalidInvariantException launched by run method");
		}
			
	}
	
	/**
	 * Test inputMismatchException
	 * @throws Exception
	 */
	@Test
	public void InputMismatchTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("MODEL WITH MONITORED TEST");
		
		String modelName =  "examples/AdvancedClock2.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		Map<String, String> locationValue = new HashMap<String, String>();
		int id = asmS.start(modelName);
		
		locationValue.put("signal", "tru");
		try {
			printState(0, asmS.run(id, locationValue));
		}catch(RuntimeException e) {
			if(e.getCause() instanceof InputMismatchException) {
				System.out.println("InputMismatchException launched by run method");
			}
		}
		
		
	}
	
	/**
	 * Test a normal execution of runUntilEmpty
	 */
	@Test
	public void RunUntilEmptyTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("RUN UNTIL EMPTY TEST");
		
		String modelName = "examples/test_insertAt_Sequence.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		
		printState(0, asmS.runUntilEmpty(id));
	}
	
	/**
	 * Test a normal execution of runUntilEmpty
	 */
	@Test
	public void RunUntilEmptyTest2() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("RUN UNTIL EMPTY TEST");
		
		String modelName =   "examples/AdvancedClock2.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		Map<String, String> locationValue = new HashMap<String, String>();
		int id = asmS.start(modelName);
		
		locationValue.put("signal", "true");
		printState(0, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(1, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(2, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(3, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(4, asmS.runUntilEmpty(id, locationValue));
		locationValue.put("signal", "true");
		printState(5, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(6, asmS.run(id, locationValue));
	}
	
	/**
	 * Test rollback method
	 * @throws Exception 
	 */
	@Test
	public void RollbackTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("ROLLBACK TEST");
		
		String modelName =   "examples/AdvancedClock.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		MyState previousState = null;
		
		for(int i = 0; i < 5; i++) {
			printState(i, asmS.run(id));
			
			if(i == 4) {
				previousState = asmS.getSimulatorTable().get(id).getPreviousState();
			}
		}
		
		System.out.println("\nRollback!");
		
		printState(3, asmS.rollback(id));
		//After rollback previousState become currentState
		assertEquals(previousState.controlledValues.get("seconds"), asmS.getCurrentState(id).controlledValues.get("seconds"));
		printState(4, asmS.run(id));
		
		asmS.init(5);
		Map<String, String> locationValue = new HashMap<String, String>();
		id = asmS.start(  "examples/AdvancedClock2.asm");
		
		locationValue.put("signal", "true");
		printState(0, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(1, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(2, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(3, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(4, asmS.run(id, locationValue));
		previousState = asmS.getSimulatorTable().get(id).getPreviousState();
		locationValue.put("signal", "true");
		printState(5, asmS.run(id, locationValue));
		
		System.out.println("\nRollback!");
		
		printState(4, asmS.rollback(id));
		//After rollback previousState become currentState
		assertEquals(previousState.controlledValues.get("seconds"), asmS.getCurrentState(id).controlledValues.get("seconds"));
		assertEquals(previousState.controlledValues.get("signal"), asmS.getCurrentState(id).controlledValues.get("signal"));
		locationValue.put("signal", "false");
		printState(5, asmS.run(id, locationValue));
		printState(6, asmS.run(id, locationValue));
	}
	
	/**
	 * Test rollback method with inconsistentUpdate
	 * @throws Exception 
	 */
	@Test
	public void RollbackTest2() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("ROLLBACK TEST");
		
		String modelName =  "examples/updateClash.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);

		try{
			printState(1, asmS.run(id));
			printState(2, asmS.run(id));
			printState(3, asmS.run(id));
		}catch(UpdateClashException e) {
			System.out.println("UpdateClashException launched by run method");
			System.out.println("Double Rollback. Return to state 1.");
			asmS.rollback(id);
			asmS.rollback(id);
		}
		
		System.out.println("\nRun on step");
		printState(2, asmS.run(id));
	}

	/**
	 * Test RollBack to state with invariantException
	 * @throws Exception
	 */
	@Test
	public void RollbackToStateTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("ROLLBACK TO STATE TEST");
		
		
		String modelName =   "examples/Invariants.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		
		printState(1, asmS.run(id));
		printState(2, asmS.run(id));
		printState(3, asmS.run(id));

		
		System.out.println("Run until empty...");
		
		try{
			asmS.runUntilEmpty(id);
		}catch(InvalidInvariantException e) {
			System.out.println("InvalidInvariantException launched by run method. Return to state 3...");
			printState(3, asmS.rollbackToState(id));
		}
		
		printState(4, asmS.run(id));
		printState(5, asmS.run(id));
			
	}
	
	/**
	 * test the reset method
	 * @throws Exception 
	 */
	@Test
	public void resetSimulatorTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("RESET SIMULATOR TEST");
		
		String modelName =   "examples/AdvancedClock2.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		Map<String, String> locationValue = new HashMap<String, String>();
		int id = asmS.start(modelName);
		
		locationValue.put("signal", "true");
		printState(0, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(1, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(2, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(3, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(4, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(5, asmS.run(id, locationValue));
		
		assertEquals(6, asmS.getSimulatorTable().get(id).getContSim()); //Controlled number of runs
		
		System.out.println();
		asmS.printSimulatorTable();
		
		System.out.println("\nReset Simulator!");
		
		asmS.reset(id);
		locationValue.put("signal", "true");
		printState(0, asmS.run(id, locationValue));
		locationValue.put("signal", "false");
		printState(1, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(2, asmS.run(id, locationValue));
		locationValue.put("signal", "true");
		printState(3, asmS.run(id, locationValue));
		
		System.out.println();
		asmS.printSimulatorTable();
	}
	
	/**
	 * test exception for id
	 * @throws Exception 
	 */
	@Test
	public void idExceptionTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("ID EXCEPTION TEST");
		
		String modelName =   "examples/AdvancedClock.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		
		try {
			printState(1, asmS.run(0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			printState(1, asmS.run(10));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		asmS.start(modelName);
		int idRemove = asmS.start(modelName);
		asmS.start(modelName);
		asmS.start(modelName);
		
		asmS.stop(idRemove);
		
		try {
			printState(1, asmS.run(idRemove));
		} catch (IdNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Test various methods and other exception of less importance
	 */
	@Test
	public void MethodsTest() throws Exception {
		System.out.println("\n||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("VARIOUS METHODS TEST");
		
		String modelName =   "examples/AdvancedClock.asm";
		AsmetaSservice asmS = AsmetaSservice.getInstance();
		asmS.init(5);
		int id = asmS.start(modelName);
		assertEquals(1, id);
		
		for(int i = 0; i < 121; i++) {
			asmS.run(id);
		}
		
		assertEquals(121, asmS.getSimulatorTable().get(id).getContSim()); //Control numbers of runs
		assertEquals("AdvancedClock", asmS.getModelName(id));	//Control modelname
		
		//Test method stop with full table and controlled id value (test getFirstFreeId method)
		assertEquals(2, asmS.start(modelName));
		assertEquals(3, asmS.start(modelName));
		assertEquals(4, asmS.start(modelName));
		assertEquals(5, asmS.start(modelName));
		
		assertEquals(5, asmS.getSimulatorTable().size());	//Control size of map
		assertEquals(-1, asmS.start(modelName));
		
		//Remove the model with id == 3 the start must return id = 3
		asmS.stop(3);
		assertEquals(4, asmS.getSimulatorTable().size());
		assertEquals(3, asmS.start(modelName));
		
		try {
			asmS.start("NotValidName");
		}catch(Exception e) {
			if (e instanceof AsmModelNotFoundException) {
				System.out.println(e);
			}
		}
		
	}
	
	/**
	 * 
	 * @param step (the state number)
	 * @param state
	 * print monitored e controlled function of the state
	 */
	public static void printState(int step, MyState state) {
		System.out.println("\n< State " + step + " >");
		
		if(state.controlledValues.size() != 0)
			System.out.println("< Controlled function >");
		
		for (Entry<Location, Value> entry : state.getControlledValues().entrySet())
		{
			System.out.println("- " + entry.getKey().toString() + ": " + entry.getValue().toString());
		}
		
		if(state.monitoredValues != null && state.monitoredValues.size() != 0)
		{
			System.out.println("< Monitored function >");
			
			for (Entry<Location, Value> entry : state.getMonitoredValues().entrySet())
			{
				System.out.println("- " + entry.getKey().toString() + ": " + entry.getValue().toString());
			}
		}
			
		
		System.out.println("-----------------------------------------------");
	}
}
