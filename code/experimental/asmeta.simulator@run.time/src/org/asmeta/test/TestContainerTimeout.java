/*
 * 
 */
package org.asmeta.test;


import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_commander.CommanderStatus;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.InvariantData;
import org.asmeta.runtime_container.RunOutput;
import org.junit.Test;

/**
 * @author Federico Rebucini
 * Class used for timeout methods testing
 */
public class TestContainerTimeout {
	

	
	
//===================================START RUNSTEP TIMEOUT=============================================


		@Test // Normal, good run
		public void runST1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT"); 
			//System.out.println(imp.runStepTimeout(id, monitored, 1000));
			RunOutput result = imp.runStepTimeout(id, monitored, 10000);
			System.out.println("The actual RunOutput of ST1 is: "+result);
			assertTrue( new RunOutput(Esit.SAFE, "").equals(result));
			Thread.sleep(50);
		}
		@Test // Normal, rollback inside timeout
		public void runST2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "WOLF"); 
			RunOutput result = imp.runStepTimeout(id, monitored, 10000);
			System.out.println("The actual RunOutput of ST2 is: "+result);
			assertTrue( new RunOutput(Esit.UNSAFE, "Invalid Invariant").equals(result));
			Thread.sleep(50);
		}
		@Test // Timeout with positive outcome
		public void runST3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT"); 
			RunOutput result = imp.runStepTimeout(id, monitored, 1);
			System.out.println("The actual RunOutput of ST3 is: "+result);
			assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equals(result));
			Thread.sleep(50);
		}
		@Test // Timeout with already rollback 
		public void runST4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT"); 
			imp.runStepTimeout(id, monitored, 1000);
			imp.runStepTimeout(id, monitored, 1000);
			imp.runStep(id, monitored);
			monitored.put("carry", "WOLFF"); 
			RunOutput result = imp.runStepTimeout(id, monitored, 5);
			System.out.println("The actual RunOutput of ST4 is: "+result);
			assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equals(result));
			Thread.sleep(50);
		}
		@Test // Operation sequence 
		public void runST5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "WOLF"); 
			imp.runStepTimeout(id, monitored, 1000000);
			monitored.put("carry", "GOAT"); 
			imp.runStepTimeout(id, monitored, 10000);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStepTimeout(id, monitored, 10000);
			monitored.put("carry", "WOLF"); 
			imp.runStepTimeout(id, monitored, 10000);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStepTimeout(id, monitored, 10000);
			
			/*monitored.put("carry", "WOLF"); 
			imp.runStep(id, monitored);
			monitored.put("carry", "GOAT"); 
			imp.runStep(id, monitored);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored);*/
			Thread.sleep(50);
		}
		@Test // timeout con partenza 
		public void runST6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNSTEPT6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GO"); 
			//imp.runStep(id, monitored);
			monitored.put("carry", "GOAT"); 
			imp.runStepTimeout(id, monitored,1000);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStepTimeout(id, monitored, 10000);
			monitored.put("carry", "WOLF"); 
			imp.runStepTimeout(id, monitored, 10000);
		}

//===================================END RUNSTEP TIMEOUT===============================================

//===================================START RUNUNTILEMPTY TIMEOUT=======================================
		//===========================MONITORED FUNCTIONS===========================
		
		@Test //everything goes well
		public void runUEM1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runUntilEmptyTimeout(id, monitored, 500)));
		}	
		
		@Test //names mismatch 
		public void runUEM2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "monitored location name <<operaion>> not found");
			assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 500)));
		}
		
		@Test //id not found
		public void runUEM3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmptyTimeout(-1, monitored, 500)));
		}
		
		@Test //input mismatch, run times out
		public void runUEM4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Run timed out");
			assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 1)));
			Thread.sleep(50);
		}
		
		@Test //invalid  invariant
		public void runUEM5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/Invariants.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 500)));;
			
		}
		
		@Test //inconsistent  update
		public void runUEM6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			imp.init(1);
			int id = imp.startExecution(model);
			imp.runStep(id, monitored);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 500)));;
		}
		
		@Test //run times out
		public void runUEM7() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYTMON7 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER");
			assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 1)));
			Thread.sleep(50);
		}
		//===========================NO MONITORED FUNCTIONS===========================
		@Test //evrything goes well
		public void runUE1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYT1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model  ="examples/test_insertAt_Sequence.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.SAFE, "everything okays").equals(imp.runUntilEmptyTimeout(id,500)));
		}
		@Test //id not found
		public void runUE2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYT2 |||||||||||||||||||||||||||||||||||||||||||||");
			SimulationContainer imp = new SimulationContainer();	
			imp.init(1);
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmptyTimeout(-1, 500)));
		}
		
		
		@Test //inconsistent  update
		public void runUE3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYT3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, 500)));;
		}
		
		@Test //run times out
		public void runUE4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST RUNUNTILEMPTYT4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model  ="examples/test_insertAt_Sequence.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equalsMessage(imp.runUntilEmptyTimeout(id,1)));
		}
//===================================END RUNUNTILEMPTY TIMEOUT=========================================

		@Test // util 
		public void runUtil() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST util |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			//monitored.put("carry", "GOAT"); 
			imp.runStep(id, monitored);
			monitored.put("carry", "GOAT"); 
			imp.runStepTimeout(id, monitored,1000);
		}

}
