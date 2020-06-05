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

import org.asmeta.assertion_catalog.InvariantGraphicsInterface;
import org.asmeta.runtime_commander.Commander;
import org.asmeta.runtime_commander.CommanderException;
import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_commander.CommanderStatus;
import org.asmeta.runtime_container.ContainerRT;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.InvariantData;
import org.asmeta.runtime_container.RunOutput;
import org.asmeta.runtime_simulator.AsmetaSservice;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class Test1.
 */
public class TestRuntimeModelContainer {
	
	
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
		ContainerRT i = ContainerRT.getInstance();
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
		ContainerRT i = new ContainerRT();
		i.init(3);
		assertTrue(i.startExecution(model) == 1);
	}

	@Test // model not found exception
	public void StartExec2() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START2 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/Lavatri.asm";
		ContainerRT i = new ContainerRT();
		i.init(3);
		assertTrue(i.startExecution(model) == -3);
	}
	
	@Test // parseException
	public void StartExec3() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START3 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/LavatriCe.asm";
		ContainerRT i = new ContainerRT();
		i.init(3);
		assertTrue(i.startExecution(model) == -5);
	}

	@Test // General Exception
	public void StartExec4() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START4 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = null;
		ContainerRT i = new ContainerRT();
		i.init(3);
		assertTrue(i.startExecution(model) == -6);
	}

	@Test // noMainRule
	public void StartExec5() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START5 |||||||||||||||||||||||||||||||||||||||||||||");
		String model = "examples/noMainRule.asm";
		ContainerRT i = new ContainerRT();
		i.init(3);
		assertTrue(i.startExecution(model) == -2);
	}
	
	@Test // FullMap
	public void StartExec6() throws Exception {
		System.out.println(" |||||||||||||||||||||  TEST START6 |||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("CHECKID for fullmap");
		String model = "examples/Lavatrice.asm";	
		ContainerRT i = ContainerRT.getInstance();
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
			ContainerRT imp = new ContainerRT();
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
			ContainerRT imp = new ContainerRT();
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
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runStep(id, monitored, model)));
		}
		@Test //names mismatch 
		public void run2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "ALLUMER");
			//imp.runStep(id, monitored, model);
			assertTrue(r1.equals(imp.runStep(id, monitored, model)));
		}
		
		@Test //id not found
		public void run3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "ALLUMER").equals(imp.runStep(-1, monitored, model)));
		}
		
		@Test //input mismatch ====> TOF FIX
		public void run4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
			
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Input Mismatchs");
			assertTrue(r1.equals(imp.runStep(id, monitored, model)));
		}
		
		@Test //invalid  invariant
		public void run5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/InvariantsMon.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("monA", "false");
			monitored.put("monB", "true");
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equals(imp.runStep(id, monitored, model2)));;
			
		}
		
		@Test //inconsistent  update
		public void run6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST 6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			ContainerRT imp = ContainerRT.getInstance();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored, model2);
			imp.runStep(id, monitored, model2);
			imp.runStep(id, monitored, model2);
			
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equals(imp.runStep(id, monitored, model2)));;
		}
//===================================END RUN WITH MONITORING=============================================
		
		
		
//===================================START RUN UNTIL EMPTY TIMEOUT WITH MONITORING=============================================		
		
				/**
				 * RUN UNTILEMPTY TEST
				 */
				
				@Test //everything goes well
				public void runE1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					ContainerRT imp = ContainerRT.getInstance();
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					int id = imp.startExecution(model);
					monitored.put("operation", "ALLUMER"); 
					assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runUntilEmptyTimeout(id, monitored, model, max,1000)));
				}	
				
				@Test //names mismatch 
				public void runE2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E2 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					//da chiedere: questa entra in errore perchè dentro mytimertask.run abbiamo supposto che rununtilempty ritornasse sempre
					//un oggetto RunOutput, invece in questa prova va in errore e stampa ROUT all'interno di checksafety senza però passare
					//ROUT in rununtilempty che, lasciando ROUT con valore null, non restituisce niente.
					//possibile fix1: cambiare la tipologia di return di checksafety (che tanto non viene nemmeno usata) in ROUT così da poter
					//poter passare ROUT giusto e bloccare subito l'esecuzione.
					//possibile fix2: prevedere la catch di nullpointerexception all'interno di rununtilempty (lanciata da AsmS) e cambiare
					//la tipologia dell'errore
				
					
					ContainerRT imp = new ContainerRT();
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operaion", "ALLUMER"); 
					int id = imp.startExecution(model);
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "ALLUMER");
					//imp.runUntilEmpty(id, monitored, model, max);
					assertTrue(r1.equals(imp.runUntilEmptyTimeout(id, monitored, model, max, 1000)));
				}
				
				@Test //id not found
				public void runE3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					ContainerRT imp = new ContainerRT();
					
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER"); 
					assertTrue(new RunOutput(Esit.UNSAFE, "ALLUMER").equals(imp.runUntilEmptyTimeout(-1, monitored, model, max,1000)));
				}
				
				@Test //input mismatch 
				public void runE4() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E4 |||||||||||||||||||||||||||||||||||||||||||||");
					String model = "examples/Lavatrice.asm";
					ContainerRT imp = new ContainerRT();
					
					imp.init(3);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUmER"); 
					int id = imp.startExecution(model);
					
				    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Input Mismatchs");
				    imp.runStep(id,monitored, model);
					assertTrue(r1.equals(imp.runUntilEmptyTimeout(id, monitored, model, max,1000)));
				}
				
				@Test //invalid  invariant
				public void runE5() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E5 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/Invariants.asm";
					ContainerRT imp = ContainerRT.getInstance();
					imp.init(3);
					int id = imp.startExecution(model2);
					System.out.println(id);
					Map<String, String> monitored = new HashMap<String, String>();
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
					assertTrue(r1.equals(imp.runUntilEmptyTimeout(id, monitored, model2, max,1000)));;
					
				}
				
				@Test //inconsistent  update
				public void runE6() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST E6 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					ContainerRT imp = ContainerRT.getInstance();
					Map<String, String> monitored = new HashMap<String, String>();
					
					imp.init(1);
					int id = imp.startExecution(model2);
					imp.runStep(id, monitored, model2);
					
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equals(imp.runUntilEmptyTimeout(id, monitored, model2, max,1000)));;
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
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "ALLUMER"); 
			assertTrue( new RunOutput(Esit.SAFE, "ALLUMER").equals(imp.runUntilEmpty(id, monitored, model, max)));
		}	
		
		@Test //names mismatch 
		public void runU2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operaion", "ALLUMER"); 
			int id = imp.startExecution(model);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "ALLUMER");
			imp.runUntilEmpty(id, monitored, model, max);
			assertTrue(r1.equals(imp.runUntilEmpty(id, monitored, model, max)));
		}
		
		@Test //id not found
		public void runU3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUMER"); 
			assertTrue(new RunOutput(Esit.UNSAFE, "ALLUMER").equals(imp.runUntilEmpty(-1, monitored, model, max)));
		}
		
		@Test //input mismatch 
		public void runU4() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U4 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			monitored.put("operation", "ALLUmER"); 
			int id = imp.startExecution(model);
			
		    RunOutput r1 = new RunOutput(Esit.UNSAFE, "Input Mismatchs");
		    imp.runStep(id,monitored, model);
			assertTrue(r1.equals(imp.runUntilEmpty(id, monitored, model, max)));
		}
		
		@Test //invalid  invariant
		public void runU5() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U5 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/Invariants.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			int id = imp.startExecution(model2);
			System.out.println(id);
			Map<String, String> monitored = new HashMap<String, String>();
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
			assertTrue(r1.equals(imp.runUntilEmpty(id, monitored, model2, max)));;
			
		}
		
		@Test //inconsistent  update
		public void runU6() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST U6 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			ContainerRT imp = ContainerRT.getInstance();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored, model2);
			
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			assertTrue(r1.equals(imp.runUntilEmpty(id, monitored, model2, max)));;
		}
		
//===================================END RUN UNTIL EMPTY WITH MONITORING=============================================
		
		
		
		
		
		
		
		
//===========================================RUNNING WITHOUT MONITORING==============================================*/
		@Test //evrything goes well
		public void runW1() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W1 |||||||||||||||||||||||||||||||||||||||||||||");
			String model  = "examples/test_insertAt_Sequence.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.SAFE, "everything okays").equals(imp.runStep(id)));
		}
		@Test //id not found
		public void runW2() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W2 |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = new ContainerRT();
			
			imp.init(3);
			Map<String, String> monitored = new HashMap<String, String>();
			assertTrue(new RunOutput(Esit.UNSAFE, "id not found").equals(imp.runStep(-1)));
		}
		
		
		@Test //inconsistent  update
		public void runW3() throws Exception {
			System.out.println(" |||||||||||||||||||||  TEST W3 |||||||||||||||||||||||||||||||||||||||||||||");
			String model2 =  "examples/updateClash.asm";
			ContainerRT imp = ContainerRT.getInstance();
			Map<String, String> monitored = new HashMap<String, String>();
			
			imp.init(1);
			int id = imp.startExecution(model2);
			imp.runStep(id, monitored, model2);
			imp.runStep(id, monitored, model2);
			imp.runStep(id, monitored, model2);
			RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
			
			assertTrue(r1.equals(imp.runStep(id)));
		}
			
		
//===========================================END RUNNING WITHOUT MONITORING=============================================*/
	
	
		
		
//=============================== START RUN UNTIL EMPTY WITHOUT MONITORING==========================================================
				@Test //evrything goes well
				public void runUW1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model  ="examples/test_insertAt_Sequence.asm";
					ContainerRT imp = ContainerRT.getInstance();
					imp.init(3);
					int id = imp.startExecution(model);
					assertTrue( new RunOutput(Esit.SAFE, "everything okays").equals(imp.runUntilEmpty(id, max)));
				}
				@Test //id not found
				public void runUW2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW2 |||||||||||||||||||||||||||||||||||||||||||||");
					ContainerRT imp = new ContainerRT();	
					imp.init(3);
					assertTrue(new RunOutput(Esit.UNSAFE, "id not found").equals(imp.runUntilEmpty(-1, max)));
				}
				
				
				@Test //inconsistent  update
				public void runUW3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UW3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					ContainerRT imp = ContainerRT.getInstance();
				//	Map<String, String> monitored = new HashMap<String, String>();
					
					imp.init(1);
					int id = imp.startExecution(model2);
					imp.runUntilEmpty(id, model2);
					
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equals(imp.runUntilEmpty(id, max)));;
				}
					
//===================================END RUN UNTIL EMPTY WITHOUT MONITORING=============================================
		
				
//===================================START RUN UNTIL EMPTY TIMEOUT WITHOUT MONITORING===================================
				
				@Test //everything should go well but it times out
				public void runUE1() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UE1 |||||||||||||||||||||||||||||||||||||||||||||");
					String model  ="examples/test_insertAt_Sequence.asm";
					ContainerRT imp = ContainerRT.getInstance();
					imp.init(3);
					int id = imp.startExecution(model);
					assertTrue( new RunOutput(Esit.UNSAFE, "Run timed out").equals(imp.runUntilEmptyTimeout(id, max,1)));
					
				}
				@Test //id not found timeout 1 second
				public void runUE2() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UE2 |||||||||||||||||||||||||||||||||||||||||||||");
					ContainerRT imp = new ContainerRT();	
					imp.init(3);
					//Da problemi di nullpointerexception se fatto partire in blocco con tutti i test ma non da problemi da solo
					assertTrue(new RunOutput(Esit.UNSAFE, "id not found").equals(imp.runUntilEmptyTimeout(-1, max,1000)));
				}
				@Test //inconsistent  update
				public void runUE3() throws Exception {
					System.out.println(" |||||||||||||||||||||  TEST UE3 |||||||||||||||||||||||||||||||||||||||||||||");
					String model2 =  "examples/updateClash.asm";
					ContainerRT imp = ContainerRT.getInstance();
				//	Map<String, String> monitored = new HashMap<String, String>();
					
					imp.init(1);
					int id = imp.startExecution(model2);
					imp.runUntilEmpty(id, model2);
					
					RunOutput r1 = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
					assertTrue(r1.equals(imp.runUntilEmptyTimeout(id, max,1000)));;
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
					ContainerRT imp = new ContainerRT();
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
				public void stopExe() {
					System.out.println(" |||||||||||||||||||||  TEST STOP1 |||||||||||||||||||||||||||||||||||||||||||||");
					ContainerRT imp = new ContainerRT();
					String model = "examples/Lavatrice.asm";
					 imp.init(1);
					int id = imp.startExecution(model);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER");
					imp.runStep(id, monitored, model);
					int stop = imp.stopExecution(id);
					assertTrue(stop == 1);
				}
				
				@Test
				public void stopExe1() {
					System.out.println(" |||||||||||||||||||||  TEST STOP2 |||||||||||||||||||||||||||||||||||||||||||||");
					ContainerRT imp = new ContainerRT();
					String model = "examples/Lavatrice.asm";
				    imp.init(1);
					Map<String, String> monitored = new HashMap<String, String>();
					monitored.put("operation", "ALLUMER");
					imp.runStep(-1, monitored, model);
					int stop = imp.stopExecution(-1);
					assertTrue(stop == -1);
				}
	
//=========================Fine Execution Test===============================================================		
		
//=========================Inizio Parsing Test=============================================================
			
			//everything goes well
			@Test
			public void parse1() throws CommanderException {
				System.out.println(" |||||||||||||||||||||  TEST PARSE1 |||||||||||||||||||||||||||||||||||||||||||||");
				String input="init -n 3";
				CommanderOutput res;
				ContainerRT imp = ContainerRT.getInstance();
				res=Commander.parseInput(imp, input, true);
				input="startexecution -modelpath \"examples/Lavatrice.asm\"";
				res=Commander.parseInput(imp, input, true);
				int id=-11;
				try {
					id = res.getID();
				} catch (CommanderException e) {
					e.printStackTrace();
				}
				input="runstep -id "+id+" -locationvalue {operation=ALLUMER} -modelpath \"examples/Lavatrice.asm\"";
				res=Commander.parseInput(imp, input, true);
				RunOutput ro=null;
				ro=res.getRunOutput();
				assertTrue(new RunOutput(Esit.SAFE,"ALLUMER").equals(ro));
			}	
			
			@Test
			public void parse2() {
				System.out.println(" |||||||||||||||||||||  TEST PARSE2 |||||||||||||||||||||||||||||||||||||||||||||");
				String input="rununttilempty -id 1 -max 5 -modelpath \"ciao.asm\" -locationvalue {operaion  = ALLUMER ,, ALLUMEER=ALLUMER, ALLUMER=ALLUMER}";
				ContainerRT imp = new ContainerRT();
				//assertFalse(ContainerParser.parseInput(imp, input, true));
			}	
//=========================Fine Parsing Test===============================================================		

			
			
//=========================Inizio Transaction Test=============================================================
		@Test
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
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.UNSAFE, "everything okays").equals(imp.runStepTransaction(id,tail,model)));
		}
		//ferryman rununtilempty
		@Test
		public void Transaction2() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION2 MANUAL |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT");
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "FERRYMAN"); 
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "CABBAGE");
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "GOAT");
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "WOLF");
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "FERRYMAN");
			imp.runUntilEmpty(id, monitored, model);
			monitored.clear();monitored.put("carry", "GOAT");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runUntilEmpty(id, monitored, model)));
			
		}
		//ferryman ciclo funzionante
		@Test
		public void Transaction3() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION3 MANUAL |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/ferrymanSimulator_raff1.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("carry", "GOAT"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "CABBAGE");
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "GOAT");
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "WOLF");
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "FERRYMAN");
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("carry", "GOAT");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runStep(id, monitored, model)));
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
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.UNSAFE, "Invalid Invariant").equalsMessage(imp.runTransaction(id, tail, model)));
		}
		//ciclo lavatrice funzionante
		@Test
		public void Transaction5() {
			System.out.println(" |||||||||||||||||||||  TEST TRANSACTION5  |||||||||||||||||||||||||||||||||||||||||||||");
			String model = "examples/Lavatrice.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			Map<String, String> monitored = new HashMap<String, String>();
			int id = imp.startExecution(model);
			monitored.put("operation", "OUVRIR"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("operation", "FERMER"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("operation", "ALLUMER");
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("operation", "PLUS"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("operation", "LANCER"); 
			imp.runStep(id, monitored, model);
			monitored.clear();monitored.put("operation", "TERMINER");
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runStep(id, monitored, model)));
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
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			int id = imp.startExecution(model);
			assertTrue( new RunOutput(Esit.SAFE, "").equals(imp.runTransaction(id, tail, model)));
		}
		
//=========================Fine Transaction Test===============================================================	
		
//=========================Inizio Asm Test===============================================================
			
		@Test
		public void testPrintInvariant() throws Exception
		{
			String model = "examples/ferrymanSimulator_raff1.asm";
			InvariantData final_list = new InvariantData();
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			int id = imp.startExecution(model);
			final_list = imp.viewListInvariant(id);
			int i=0;
			System.out.println(final_list);
		}
		
		public void rigenera() throws Exception {
			String model = "examples/ferrymanSimulator_raff1.asm";
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
		}
		
		@Test
		public void testAddInvariant() throws Exception {
			int result=0;
			Map<String,String> m=new HashMap<String, String>();
			m.put("carry", "WOLF");
			String model = "examples/ferrymanSimulator_raff1.asm";
			Files.copy(Paths.get(model+".original"), Paths.get(model), StandardCopyOption.REPLACE_EXISTING);
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(2);
			int id = imp.startExecution(model);
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
			assertTrue(result>0);
		}
		@Test
		public void testUpdateInvariant() throws Exception {
			int result;
			rigenera();
			String model = "examples/ferrymanSimulator_raff1.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			int id = imp.startExecution(model);
			result = imp.updateInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(WOLF)=position(FERRYMAN)","invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			if(result == 0)
				System.out.println("Everything goes well");
			else if(result == 1)
				System.out.println("Variable already taken");
			else
				System.out.println("Failed to rename");
		}
		
		@Test
		public void testRemoveInvariant() throws Exception {
			rigenera();
			String model = "examples/ferrymanSimulator_raff1.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(1);
			int id = imp.startExecution(model);
			imp.removeInvariant(id,"invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			System.out.println(imp.viewListInvariant(id));
		}
		
		@Test
		public void testAddInvariantSimulation() throws Exception {
			boolean result;
			Map<String, String> monitored = new HashMap<String, String>();
			String model = "examples/ferrymanSimulator_raff1.asm";
			ContainerRT imp = ContainerRT.getInstance();
			imp.init(3);
			int id = imp.startExecution(model);
			imp.startExecution(model);
			imp.startExecution(model);
			//imp.stopExecution(2);
			monitored.put("carry", "GOAT"); 
			imp.runStep(id, monitored, model);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored, model);
			monitored.put("carry", "CABBAGE"); 
			imp.runStep(id, monitored, model);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored, model);
			imp.addInvariant(id, "invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)");
			System.out.println(imp.getLoadedIDs());
			/*monitored.put("carry", "WOLF"); 
			imp.runStep(id, monitored, model);*/
			/*monitored.put("carry", "CABBAGE"); 
			imp.runStep(id, monitored, model);
			monitored.put("carry", "FERRYMAN"); 
			imp.runStep(id, monitored, model);*/
		
		}
		//=========================Fine Asm Test===============================================================
		//=========================Inizio GUI Test===============================================================
	@Test
	public void testGUI() {
		InvariantGraphicsInterface.main(null);
	}

}
