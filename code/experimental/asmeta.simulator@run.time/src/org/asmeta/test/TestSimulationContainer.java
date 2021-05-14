/*
 * 
 */
package org.asmeta.test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.EventQueue;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.asmeta.assertion_catalog.InvariantGUI;
import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_commander.CommanderStatus;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.InvariantData;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_simulator.AsmetaSserviceSingleton;
import org.junit.Test;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 * The SimulationContainer test class
 */
public class TestSimulationContainer {
	
	
	final int max = 50;
	
	
	//CheckID
	
	/**
	 * Check ID 1.
	 *
	 * @throws Exception the exception
	 */
	/*@Test
	public void CheckID1() throws Exception {
		
		System.out.println("CHECKID for Nonfullmap");
		String model = "examples/Lavatrice.asm";
		SimulationContainer i = SimulationContainer.getInstance();
		i.init(3);
		int id = i.startExecution(model);
	    int idc = i.checkStartId(id);
		assertTrue(idc == id );
		System.out.println("===========================================");
	}*/
	
//===================================START OF STARTEXE=============================================
	
		/**
	 * Start exec 1.
	 *
	 * @throws Exception the exception
	 */
	@Test // everything goes well
	public void StartExec1() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START1 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/Lavatrice.asm";
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		assertTrue(i.startExecution(model) == 1);
	}

	@Test // model not found exception
	public void StartExec2() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START2 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/Lavatri.asm";
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		assertTrue(i.startExecution(model) == -3);
	}
	
	@Test // parseException
	public void StartExec3() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START3 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/LavatriCe.asm";
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		assertTrue(i.startExecution(model) == -5);
	}

	@Test // General Exception
	public void StartExec4() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START4 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = null;
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		assertTrue(i.startExecution(model) == -6);
	}

	@Test // noMainRule
	public void StartExec5() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START5 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/noMainRule.asm";
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		assertTrue(i.startExecution(model) == -2);
	}
	
	@Test // FullMap
	public void StartExec6() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START6 |||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("CHECKID for fullmap");
		String model = "examples/Lavatrice.asm";	
		SimulationContainer i = new SimulationContainer();
		i.init(3);
		int id1 = i.startExecution(model);
		int id2 = i.startExecution(model);
		int id3 = i.startExecution(model);
		int id4 = i.startExecution(model);
		//int idc = i.checkStartId(id4);
	    assertTrue(id4== -4);
	}
	  
		
		//Check Safety
		
		//ABBIAMO MODIFICATO IL TIPO DI RETURN DI CHECKSAFETY PER RISOLVERE UN PROBLEMA DI PERDITA DEL VALORE DI ROUT NEL CASO
		//LO STATO UNSAFE VENGA GIA' TROVATO ALL'INTERNO DI CHECKSAFETY, NON SAPPIAMO SE PERO' IL TIPO PRECEDENTE ERA UTILIZZATO
		//ANCHE FUORI DAI TEST
	
		/**
		 * Check safety 1.
		 *
		 * @throws Exception the exception
		 */
/*		@Test
		public void CheckSafety1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST SAFETY1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER");
			ArrayList<String> test = new ArrayList<String>();
			test.add("operation");
			assertTrue(imp.checkSafety( model, monitored).equals(test));
	}
		
		@Test
		public void CheckSafety2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST SAFETY2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			int id = imp.startExecution(model);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER");
			ArrayList<String> test = new ArrayList<String>();
			test.add("operation");
			assertTrue(imp.checkSafety( model, monitored).equals(test) );
	}*/
//===================================END START EXE  =============================================
		
		
		
		
		
		
		
		
		
		

//===================================START RUN WITH MONITORING=============================================

		/**
		 * Run 1.
		 *
		 * @throws Exception the exception
		 */
		//TEST DI RUN
		
		@Test //evrything goes well
		public void run1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			System.out.println(imp.runStep(id, monitored));
			assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runStep(id, monitored)));
		}
		@Test //names mismatch 
		public void run2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "monitored location name <<operaion>> not found");
			//imp.runStep(id, monitored, model);
			assertTrue(r1.equalsMessage(imp.runStep(id, monitored)));
		}
		
		@Test //id not found
		public void run3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runStep(-1, monitored)));
		}
		
		@Test //input mismatch
		public void run4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
			
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Input value");
			assertTrue(r1.equalsMessage(imp.runStep(id, monitored)));
		}
		
		@Test //invalid  invariant
		public void run5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/InvariantsMon.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("monA", "false");
			monitored.put("monB", "true");
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equalsMessage(imp.runStep(id, monitored)));;
			
		}
		
		@Test //inconsistent  update
		public void run6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equalsMessage(imp.runStep(id, monitored)));;
		}
//===================================END RUN WITH MONITORING=============================================

//===================================START RUN WITH MONITORING=============================================

		/**
		 * Run 1.
		 *
		 * @throws Exception the exception
		 */
		//TEST DI RUN
		
		@Test //times out
		public void runT1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equalsMessage(imp.runStepTimeout(id, monitored, 1)));
			Thread.sleep(50);
		}
		@Test //names mismatch 
		public void runT2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "monitored location name <<operaion>> not found");
			//imp.runStep(id, monitored, model);
			assertTrue(r1.equalsMessage(imp.runStepTimeout(id, monitored, 1000)));
		}
		
		@Test //id not found
		public void runT3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runStepTimeout(-1, monitored, 1000)));
		}
		
		@Test //input mismatch ====> TOF FIX
		public void runT4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
			
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Input value");
			assertTrue(r1.equalsMessage(imp.runStepTimeout(id, monitored, 1000)));
		}
		
		@Test //invalid  invariant
		public void runT5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/InvariantsMon.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("monA", "false");
			monitored.put("monB", "true");
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equalsMessage(imp.runStepTimeout(id, monitored, 1000)));;
			
		}
		
		@Test //inconsistent  update
		public void runT6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST T6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equalsMessage(imp.runStepTimeout(id, monitored, 1000)));;
		}
//===================================END RUN WITH MONITORING=============================================
		
		
//===================================START RUN UNTIL EMPTY TIMEOUT WITH MONITORING=============================================		
		
				/**
				 * RUN UNTILEMPTY TEST
				 */
				
				@Test //everything goes well
				public void runUT1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					SimulationContainer imp = new SimulationContainer();
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					int id = imp.startExecution(model);
					monitored.put("operation", "ALLUMER"); 
					assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runUntilEmptyTimeout(id, monitored,1000)));
				}	
				
				@Test //names mismatch 
				public void runUT2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT2 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					SimulationContainer imp = new SimulationContainer();
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operaion", "ALLUMER"); 
					int id = imp.startExecution(model);
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "monitored location name <<operaion>> not found");
					//imp.runUntilEmpty(id, monitored, model, max);
					assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 1000)));
				}
				
				@Test //id not found
				public void runUT3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					SimulationContainer imp = new SimulationContainer();
					
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER"); 
					assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmptyTimeout(-1, monitored, 1000)));
				}
				
				@Test //input mismatch 
				public void runUT4() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT4 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					SimulationContainer imp = new SimulationContainer();
					
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUmER"); 
					int id = imp.startExecution(model);
					
				    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid input value");
					assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 1000)));
				}
				
				@Test //invalid  invariant
				public void runUT5() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT5 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/Invariants.asm";
					SimulationContainer imp = new SimulationContainer();
					imp.init(3);
					int id = imp.startExecution(model2);
					System.out.println(id);
					Map<String, String> monitored = new HashMap<String, String>();
					RunOutput r1 = imp.runUntilEmptyTimeout(id, monitored, 1000);
					assertTrue(r1.equalsMessage(new RunOutput(Esit.UNSAFE, "Invalid Invariant")));
				}
				
				@Test //inconsistent  update
				public void runUT6() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UT6 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					SimulationContainer imp = new SimulationContainer();
					Map<String, String> monitored = new HashMap<String, String>();
					
					imp.init(1);
					int id = imp.startExecution(model2);
					
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, monitored, 1000)));;
				}
				
//===================================END RUN UNTIL EMPTY TIMEOUT WITH MONITORING=============================================
		
		
		
		
		
		
//===================================START RUN UNTIL EMPTY WITH MONITORING=============================================		
		
		/**
		 * RUN UNTILEMPTY TEST
		 */
		
		@Test //evrything goes well
		public void runU1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runUntilEmpty(id, monitored, max)));
		}	
		
		@Test //names mismatch 
		public void runU2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "monitored location name <<operaion>> not found");
			assertTrue(r1.equalsMessage(imp.runUntilEmpty(id, monitored, max)));
		}
		
		@Test //id not found
		public void runU3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmpty(-1, monitored, max)));
		}
		
		@Test //input mismatch 
		public void runU4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
			
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid input value");
			assertTrue(r1.equalsMessage(imp.runUntilEmpty(id, monitored, max)));
		}
		
		@Test //invalid  invariant
		public void runU5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/Invariants.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equalsMessage(imp.runUntilEmpty(id, monitored, max)));;
			
		}
		
		@Test //inconsistent  update
		public void runU6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored);
			
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equalsMessage(imp.runUntilEmpty(id, monitored, max)));;
		}
		
//===================================END RUN UNTIL EMPTY WITH MONITORING=============================================
		
		
		
		
		
		
		
		
//===========================================RUNNING WITHOUT MONITORING==============================================*/
		@Test //evrything goes well
		public void runW1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model  = "examples/test_insertAt_Sequence.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.SAFE, "everything okays").equals(imp.runStep(id)));
		}
		@Test //id not found
		public void runW2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = new SimulationContainer();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runStep(-1)));
		}
		
		
		@Test //inconsistent  update
		public void runW3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			SimulationContainer imp = new SimulationContainer();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			imp.runStep(id, monitored);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			
			assertTrue(r1.equalsMessage(imp.runStep(id)));
		}
			
		
//===========================================END RUNNING WITHOUT MONITORING=============================================*/
	
	
		
		
//=============================== START RUN UNTIL EMPTY WITHOUT MONITORING==========================================================
				@Test //evrything goes well
				public void runUW1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model  ="examples/test_insertAt_Sequence.asm";
					SimulationContainer imp = new SimulationContainer();
					imp.init(3);
					int id = imp.startExecution(model);
					assertTrue( new RunOutput(Esit.SAFE, "everything okays").equals(imp.runUntilEmpty(id, max)));
				}
				@Test //id not found
				public void runUW2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW2 |||||||||||||||||||||||||||||||||||||||||||||");
					SimulationContainer imp = new SimulationContainer();	
					imp.init(3);
					assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmpty(-1, max)));
				}
				
				
				@Test //inconsistent  update
				public void runUW3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					SimulationContainer imp = new SimulationContainer();
				//	Map<String, String> monitored = new HashMap<String, String>();
					
					imp.init(1);
					int id = imp.startExecution(model2);
					imp.runUntilEmpty(id);
					
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equalsMessage(imp.runUntilEmpty(id, max)));;
				}
					
//===================================END RUN UNTIL EMPTY WITHOUT MONITORING=============================================
		
				
//===================================START RUN UNTIL EMPTY TIMEOUT WITHOUT MONITORING===================================
				
				@Test //everything should go well but it times out, sometimes it runs too fast and it doesn't, running this on its own should give the proper result
				public void runUWT1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UWT1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model  ="examples/test_insertAt_Sequence.asm";
					SimulationContainer imp = new SimulationContainer();
					imp.init(3);
					int id = imp.startExecution(model);
					assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equalsMessage(imp.runUntilEmptyTimeout(id, max,1)));
				}
				@Test //id not found timeout 1 second
				public void runUWT2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UWT2 |||||||||||||||||||||||||||||||||||||||||||||");
					SimulationContainer imp = new SimulationContainer();	
					imp.init(3);
					assertTrue(new RunOutput(Esit.UNSAFE, "the id is not found").equalsMessage(imp.runUntilEmptyTimeout(-1, max,1000)));
				}
				@Test //inconsistent  update
				public void runUWT3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UWT3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					SimulationContainer imp = new SimulationContainer();
				//	Map<String, String> monitored = new HashMap<String, String>();
					imp.init(1);
					int id = imp.startExecution(model2);
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equalsMessage(imp.runUntilEmptyTimeout(id, max,1000)));;
				}
			
//===================================END RUN UNTIL EMPTY TIMEOUT WITHOUT MONITORING===================================
				
//=========================Start Init Test==============================================================
				/**
				 * Inits the test.
				 */
				//TEST DI INIT
	
				
				/**
				 * Inits the test 1.
				 */
				@Test
				public void initTest1() {
					System.out.println(" |||||||||||||||||||||  TEST INIT1 |||||||||||||||||||||||||||||||||||||||||||||");
					System.out.println("==================================");
					SimulationContainer imp = new SimulationContainer();
					assertTrue(imp.init(1) > 0);
					System.out.println("==================================");
				
				}
				
//========================END Init Test==================================================================


				
				
				
				
				
//=========================Inizio Execution Test===============================================================
				/**
				 * Stop exe.
				 */
				// TEST DI Stop ok
				@Test
				public void stopExe1() {
					System.out.println(" |||||||||||||||||||||  TEST STOP1 |||||||||||||||||||||||||||||||||||||||||||||");
					SimulationContainer imp = new SimulationContainer();
					String model = "examples/Lavatrice.asm";
					 imp.init(1);
					int id = imp.startExecution(model);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER");
					imp.runStep(id, monitored);
					int stop = imp.stopExecution(id);
					assertTrue(stop == 1);
				}
				
				@Test
				public void stopExe2() {
					System.out.println(" |||||||||||||||||||||  TEST STOP2 |||||||||||||||||||||||||||||||||||||||||||||");
					SimulationContainer imp = new SimulationContainer();
					String model = "examples/Lavatrice.asm";
				    imp.init(1);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER");
					imp.runStep(-1, monitored);
					int stop = imp.stopExecution(-1);
					assertTrue(stop == -1);
				}
	
//=========================Fine Execution Test===============================================================		
		
//=========================Inizio Parsing Commander Test=============================================================
			
			//everything goes well
			@Test
			public void parse1() throws CommanderException {
				System.out.println(" |||||||||||||||||||||  TEST PARSE1 |||||||||||||||||||||||||||||||||||||||||||||");
				String input="init -n 3";
				CommanderOutput res;
				SimulationContainer imp = new SimulationContainer();
				Commander.debugMode = true;
				res=Commander.parseInput(imp, input);
				input="startexecution -modelpath \"examples/Lavatrice.asm\"";
				res=Commander.parseInput(imp, input);
				int id=-11;
				try {
					id = res.getID();
				} catch (CommanderException e) {
					e.printStackTrace();
				}
				input="runstep -id "+id+" -locationvalue {operation=ALLUMER} -modelpath \"examples/Lavatrice.asm\"";
				res=Commander.parseInput(imp, input);
				RunOutput ro=null;
				ro=res.getRunOutput();
				assertTrue(new RunOutput(Esit.SAFE,"ALLUMER").equals(ro));
			}	
			
			@Test
			public void parse2() {
				System.out.println(" |||||||||||||||||||||  TEST PARSE2 |||||||||||||||||||||||||||||||||||||||||||||");
				String input="rununttilempty -id 1 -max 5 -modelpath \"ciao.asm\" -locationvalue {operaion  = ALLUMER ,, ALLUMEER=ALLUMER, ALLUMER=ALLUMER}";
				SimulationContainer imp = new SimulationContainer();
				Commander.debugMode = true;
				assertTrue(Commander.parseInput(imp, input).getStatus()==CommanderStatus.FAILURE);
			}	
//=========================Fine Parsing Test===============================================================		

			
			
//=========================Inizio Transaction Test=============================================================
		/*@Test	//FIX ALL ASSERT .EQUALS INTO EQUALSMESSAGE if used
		public void Transaction1() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION1 (RUNSTEP) |||||||||||||||||||||||||||||||||||||||||||||");
			Queue<Map<String, String>> tail= new LinkedList<>();
			Map<String, String> locationValue1 = new HashMap<String, String>();
			Map<String, String> locationValue2 = new HashMap<String, String>();
			Map<String, String> locationValue3 = new HashMap<String, String>();
			Map<String, String> locationValue4 = new HashMap<String, String>();
			Map<String, String> locationValue5 = new HashMap<String, String>();
			Map<String, String> locationValue6 = new HashMap<String, String>();
			Map<String, String> locationValue7 = new HashMap<String, String>();
			Map<String, String> locationValue8 = new HashMap<String, String>();
			Map<String, String> locationValue9 = new HashMap<String, String>();
			
			locationValue1.put("operation", "ALLUMER");
			tail.add(locationValue1);
			
			locationValue2.put("operation", "ETEINDRE");
			tail.add(locationValue2);
			
			locationValue3.put("operation", "LANCER");
			tail.add(locationValue3);
			
			locationValue4.put("operation", "PAUSE");
			tail.add(locationValue4);
			
			locationValue5.put("operation", "OUVRIR");
			tail.add(locationValue5);
			
			locationValue6.put("operation", "FERMER");
			tail.add(locationValue6);
			
			locationValue7.put("operaion", "MOINS");
			tail.add(locationValue7);
			
			locationValue8.put("operation", "PLUS");
			tail.add(locationValue8);
			
			locationValue9.put("operation", "TERMINER");
			tail.add(locationValue9);
			
			
			String model  ="examples/Lavatrice.asm";
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(3);
			int id = imp.startExecution(model);
			//assertTrue( new RunOutput(Esit.UNSAFE, "everything okays").equals(imp.runStepTransaction(id,tail,model)));
		}
		//ferryman rununtilempty
		@Test
		public void Transaction2() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION2 MANUAL |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT");
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "FERRYMAN"); 
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "CABBAGE");
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "GOAT");
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "WOLF");
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "FERRYMAN");
			imp.runUntilEmpty(id, monitored);
			monitored.clear();monitored.put("carry", "GOAT");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runUntilEmpty(id, monitored)));
			
		}
		//ferryman ciclo funzionante
		@Test
		public void Transaction3() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION3 MANUAL |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "CABBAGE");
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "GOAT");
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "WOLF");
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "FERRYMAN");
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("carry", "GOAT");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runStep(id, monitored)));
		}
		//ferryman transazione runstep
		@Test
		public void Transaction4() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION4 RUNSTEP |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			Map<String, String> lv1 = new HashMap<String, String>();
			Map<String, String> lv2 = new HashMap<String, String>();
			Map<String, String> lv3 = new HashMap<String, String>();
			Map<String, String> lv4 = new HashMap<String, String>();
			Map<String, String> lv5 = new HashMap<String, String>();
			Map<String, String> lv6 = new HashMap<String, String>();
			Map<String, String> lv7 = new HashMap<String, String>();
			lv1.put("carry", "GOAT");
			lv2.put("carry", "FERRYMAN");
			lv3.put("carry", "CABBAGE");
			lv4.put("carry", "FERRYMAN");
			lv5.put("carry", "WOLF");
			lv6.put("carry", "FERRYMAN");
			lv7.put("carry", "GOAT");
			Queue<Map<String, String>> tail= new LinkedList<>();
			tail.add(lv1);tail.add(lv2);tail.add(lv3);tail.add(lv4);tail.add(lv5);tail.add(lv6);tail.add(lv7);
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			//assertTrue( new RunOutput(Esit.UNSAFE, "Invalid Invariant").equalsMessage(imp.runTransaction(id, tail)));
		}
		//ciclo lavatrice funzionante
		@Test
		public void Transaction5() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION5  |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "OUVRIR"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("operation", "FERMER"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("operation", "ALLUMER");
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("operation", "PLUS"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("operation", "LANCER"); 
			imp.runStep(id, monitored);
			monitored.clear();monitored.put("operation", "TERMINER");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runStep(id, monitored)));
		}
		//lavatrice transazione rununtilempty
		@Test
		public void Transaction6() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION6  |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			Map<String, String> lv1 = new HashMap<String, String>();
			Map<String, String> lv2 = new HashMap<String, String>();
			Map<String, String> lv3 = new HashMap<String, String>();
			Map<String, String> lv4 = new HashMap<String, String>();
			Map<String, String> lv5 = new HashMap<String, String>();
			Map<String, String> lv6 = new HashMap<String, String>();
			lv1.put("operation", "OUVRIR");
			lv2.put("operation", "FERMER");
			lv3.put("operation", "ALLUMER");
			lv4.put("operation", "PLUS");
			lv5.put("operation", "LANCER");
			lv6.put("operation", "TERMINER");
			Queue<Map<String, String>> tail= new LinkedList<>();
			tail.add(lv1);tail.add(lv2);tail.add(lv3);tail.add(lv4);tail.add(lv5);tail.add(lv6);
			SimulationContainer imp = SimulationContainer.getInstance();
			imp.init(1);
			int id = imp.startExecution(model);
			//assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runTransaction(id, tail)));
		}
		*/
//=========================Fine Transaction Test===============================================================	
		
//=========================Inizio Asm Test===============================================================
			
		@Test
		public void testPrintInvariant() throws Exception
		{
			String model = "examples/ferrymanSimulator_raff1.asm";
			InvariantData final_list = new InvariantData();
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			final_list = imp.viewListInvariant(id);
			int i=0;
			System.out.println(final_list);
			assertTrue(final_list!=null);
		}
		
		public void rigenera() throws Exception {
			String model = "examples/ferrymanSimulator_raff1.asm";
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
		}
		
		@Test
		public void testAddInvariant() throws Exception {
			int result=0;
			Map<String,String> m=new HashMap<String, String>();
			m.put("carry", "GOAT");
			String model = "examples/ferrymanSimulator_raff1.asm";
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
			SimulationContainer imp = new SimulationContainer();
			imp.init(2);
			int id = imp.startExecution(model);
			imp.runStep(id,m);
			imp.removeInvariant(id,"invariant over position:position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			System.out.println(imp.viewListInvariant(id));
			//imp.runStep(id,m, model);
			result=imp.addInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			//result=imp.addInvariant(id,"invariant che da parser error");
			System.out.println(imp.viewListInvariant(id));
			//result = imp.addInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			//imp.removeInvariant(id,"invariant over position:position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			/*if(result > 0)
				System.out.println("Everything goes well");
			else 
				System.out.println("An error has occurred");*/
			rigenera();
			assertTrue(result>0);
		}
		@Test
		public void testUpdateInvariant() throws Exception {
			int result;
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			result = imp.updateInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(WOLF)=position(FERRYMAN)","invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			if(result == 1)
				System.out.println("Everything goes well");
			else if(result == -8)
				System.out.println("Variable already taken");
			else
				System.out.println("Failed to rename");
			rigenera();
			assertTrue(result>0);
		}
		
		@Test
		public void testRemoveInvariant() throws Exception {
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			boolean result = imp.removeInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			//System.out.println(imp.viewListInvariant(id));
			rigenera();
			assertTrue(result);
		}
		
		@Test
		public void testruntimeout() {
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("carry", "GOAT"); 
			int id = imp.startExecution(model);
			imp.runStepTimeout(id, monitored,1000);
			System.out.println(imp.runStepTimeout(id, monitored,10));
			RunOutput r1 = imp.runStepTimeout(id,monitored,1000);
			assertTrue(r1.equalsMessage(new RunOutput(Esit.SAFE, "")));
			//imp.runStepTimeout(id, monitored,1000);
		    //RunOutput r1 = new RunOutput(Esit.UNSAFE, "Run timed out");
			//assertFalse(r1.equals(imp.runStepTimeout(id, monitored,300)));
		}
		
		@Test
		public void testAddInvariantSimulation() throws Exception {
			int result=0;
			Map<String, String> monitored = new HashMap<String, String>();
			String model = "examples/ferrymanSimulator_raff1.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(3);
			int id = imp.startExecution(model);
			imp.startExecution(model);
			imp.startExecution(model);
			imp.stopExecution(2);
			imp.removeInvariant(3, "invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			monitored.put("carry", "GOAT"); 
			imp.runStep(3, monitored);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(3, monitored);
			monitored.put("carry", "CABBAGE"); 
			imp.runStep(3, monitored);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(3, monitored);
			
			result = imp.addInvariant(3, "invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			System.out.println(imp.getLoadedIDs());
			rigenera();
			assertTrue(result==-7);
			/*monitored.put("carry", "WOLF"); 
			imp.runStep(id, monitored, model);*/
			/*monitored.put("carry", "CABBAGE"); 
			imp.runStep(id, monitored, model);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored, model);*/
		
		}
		
		//Made to check if the simulation requires all monitored variable or can run without some in particular cases
		@Test
		public void missingMonitoredTest() {
			Map<String, String> monitored = new HashMap<String, String>();
			String model = "examples/railroadGate.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			monitored.put("lightMon", "FLASHING"); monitored.put("gateMon","OPENED");monitored.put("event","LIGHT");
			imp.runStep(id, monitored);		
			monitored.clear();
			monitored.put("gateMon","CLOSING");monitored.put("event","GATE");
			imp.runStep(id, monitored);		
			monitored.clear();
			monitored.put("gateMon","CLOSED");monitored.put("event","GATE");
			imp.runStep(id, monitored);		
			monitored.clear();
			monitored.put("gateMon","OPENING");monitored.put("event","GATE");
			imp.runStep(id, monitored);		
			monitored.clear();
			monitored.put("gateMon","OPENED");monitored.put("event","GATE");
			imp.runStep(id, monitored);		
			monitored.clear();
			monitored.put("lightMon","OFF");monitored.put("event","LIGHT");
			imp.runStep(id, monitored);		
			monitored.clear();
		}
		
		//=========================Multiple parameters monitored test===============================================================

		//Multiple parameters monitored function test
		@Test
		public void liftTest() {
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput out;
			String model = "examples/LIFT.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			monitored.put("insideCall(lift1,2)", "true");
			monitored.put("insideCall(lift1,1)", "false");
			monitored.put("insideCall(lift1,0)", "false");
			monitored.put("outsideCall(0,UP)", "false");
			monitored.put("outsideCall(0,DOWN)", "false");
			monitored.put("outsideCall(1,UP)", "false");
			monitored.put("outsideCall(1,DOWN)", "false");
			monitored.put("outsideCall(2,UP)", "false");
			monitored.put("outsideCall(2,DOWN)", "false");
			out = imp.runStep(id, monitored);		
			//out = imp.runStep(id);	
			System.out.println(out.getControlledvalues());
			monitored.clear();
		}
		//Single parameter monitored function test
		@Test
		public void invariants2Test() {
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput out;
			String model = "examples/Invariants2.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			monitored.put("quantity(p1)", "1000");
			monitored.put("quantity(p2)", "10000");
			monitored.put("quantity(p3)", "100000");
			//out = imp.runUntilEmpty(id, monitored,50000);
			out = imp.runStep(id, monitored);	
			monitored.clear();
			monitored.put("quantity(p2)", "10000");
			out = imp.runStep(id, monitored);		
			//out = imp.runStep(id);	
			System.out.println(out.getControlledvalues());
			monitored.clear();
		}
		@Test
		public void pillboxTest() {
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput out;
			String model = "examples/Pillbox/pillbox.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			monitored.put("systemTime", "1000");
			monitored.put("openSwitch(compartment2)", "false");
			monitored.put("openSwitch(compartment3)", "true");
			monitored.put("openSwitch(compartment4)", "false");
			out = imp.runStep(id, monitored);
			//out = imp.runStep(id);	
			System.out.println(out.getControlledvalues());
		}
		@Test
		public void safePillboxTest() {
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput out;
			String model = "examples/Pillbox2/safePillbox.asm";
			SimulationContainer imp = new SimulationContainer();
			imp.init(1);
			int id = imp.startExecution(model);
			monitored.put("redLed(compartment2)", "OFF");
			monitored.put("redLed(compartment3)", "OFF");
			monitored.put("redLed(compartment4)", "OFF");
			monitored.put("name(compartment2)", "\"aspirine\"");
			monitored.put("name(compartment3)", "\"moment\"");
			monitored.put("name(compartment4)", "\"fosamax\"");
			monitored.put("time_consumption(compartment2)", "[960]");
			monitored.put("time_consumption(compartment3)", "[780, 1140]");
			monitored.put("time_consumption(compartment4)", "[410]");
			monitored.put("drugIndex(compartment2)", "0");
			monitored.put("drugIndex(compartment3)", "0");
			monitored.put("drugIndex(compartment4)", "0");
			monitored.put("nextDrugIndex(compartment2)", "0");
			monitored.put("nextDrugIndex(compartment3)", "1");
			out = imp.runStep(id, monitored);
			//out = imp.runStep(id);	
			System.out.println(out.getControlledvalues());
			//monitored.clear();
			monitored.put("redLed(compartment2)", "BLINKING");
			monitored.put("redLed(compartment3)", "OFF");
			monitored.put("redLed(compartment4)", "OFF");
			monitored.put("nextDrugIndex(compartment4)","0");
			monitored.put("drugIndex(compartment3)","1");
			monitored.put("isPillMissed(compartment2)","true");
			monitored.put("isPillMissed(compartment3)","false");
			monitored.put("isPillMissed(compartment4)","false");
			monitored.put("pillTakenWithDelay(compartment3)","false");
			monitored.put("pillTakenWithDelay(compartment4)","false");
			monitored.put("systemTime","1010");
			monitored.put("pillTakenWithDelay(compartment2)","false");
			
			/*monitored.put("nextDrugIndex(compartment2)","0");
			monitored.put("nextDrugIndex(compartment3)","1");
			monitored.put("time_consumption(compartment2)", "[960]");
			monitored.put("time_consumption(compartment3)", "[780, 1140]");
			monitored.put("time_consumption(compartment4)", "[410]");
			monitored.put("drugIndex(compartment2)", "0");
			monitored.put("drugIndex(compartment3)", "0");
			monitored.put("drugIndex(compartment4)", "0");*/
			out = imp.runStep(id, monitored);
			//out = imp.runStep(id);	
			//System.out.println(out.getControlledvalues());
		}
		//=========================Fine Asm Test===============================================================
		//=========================Inizio GUI Test===============================================================
	/*@Test
	public void testGUI() {
		InvariantGUI.main(null);
	}*/

}
