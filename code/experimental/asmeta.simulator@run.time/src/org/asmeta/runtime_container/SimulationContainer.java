package org.asmeta.runtime_container;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

import org.asmeta.animator.MyState;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.runtime_simulator.AsmetaSservice;
import org.asmeta.runtime_simulator.IdNotFoundException;
import org.asmeta.runtime_simulator.InfoAsmetaService;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateClashException;

import org.asmeta.simulator.main.*;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.MonitoredFinder;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.ecore.xml.type.internal.RegEx;

import asmeta.AsmCollection;
import asmeta.definitions.Invariant;
import asmeta.definitions.Property;
import asmeta.definitions.impl.MonitoredFunctionImpl;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.terms.basicterms.Term;

/**
 * @author Federico Rebucini, Hernan Altamirano, Daniele Troiano
 * The Class  SimulationContainer. 
 * It is a container for managing the execution of an ASM model at runtime.
 * It provides methods for instantiating, starting, running and stopping a model execution
 */
public class SimulationContainer implements IModelExecution, IModelAdaptation {
    
	private int id; // returning the id of the simulator generated if everything goes well

	/** The ids. */
	private int ids; //the id for the method start to check if is full o not

	/** The asm S. */
	protected AsmetaSservice asmS;


	/** The ms. */
	MyState ms;
	
	/** The status of the simulator, used to check when to update the model. */
	SimStatus simulationRunning = SimStatus.READY;
	/** The status of timeout methods rollback, used to check if it has already been done or is needed. */
	//rollbackStatus rollbStatus = rollbackStatus.NOTROLLED;
	
	private static int stepRun = 0;

	private long startRun = 0L;
	private long endRun = 0L;
	private long duration = 0L;
	
	private List<String> invarNames;
	//private List<String> variables;
	
	private RunOutput routTO=null;	//support variable for the timeout methods


	
	public SimulationContainer() {
		asmS = new AsmetaSservice();
	}

	
	

	/**
	 * return the id of the simulator if the simulator is full return -1;.
	 *
	 * @param modelPath the model path
	 * @return the int
	 */
	public int startExecution(String modelPath) {
		StartOutput sout = null;
		try {
			id = asmS.start(modelPath);
			ids = checkStartId(id);
			

			sout = new StartOutput(ids, "The id " + ids + " is successfully created");
			//System.out.println(sout.toString());

		} catch (Exception e) {
			if (e instanceof MainRuleNotFoundException) {
				sout = new StartOutput(-2, "Main Rule Not Found");
				System.err.println(sout.toString());

			} else if (e instanceof AsmModelNotFoundException) {
				sout = new StartOutput(-3,
						"The Model " + modelPath.substring(modelPath.lastIndexOf("/") + 1) + " Doesn't esist");
				System.err.println(sout.toString());

			} else if (e instanceof FullMapException) {
				sout = new StartOutput(-4, "The simulator map is Full " + "can't add the model <"
						+ modelPath.substring(modelPath.lastIndexOf("/") + 1) + "> in the simulator map");
				System.err.println(sout.toString());

			} else if (e instanceof ParseException) {
				sout = new StartOutput(-5, "The model name " + modelPath.substring(modelPath.lastIndexOf("/") + 1)
						+ " Does not match:" + " Check for case sensitive in <<modelname.asm>>");
				System.err.println(sout.toString());
			}

			else {
				sout = new StartOutput(-6, "General Exception: Please report the problem");
				System.err.println(sout.toString());
			}

		}
		return sout.getId();
	}
	
	//tries to start a simulation that begins already on the given state, behaves same as startexecution.
	private int restartExecution(String modelPath,int oldId, State state) {
		StartOutput sout = null;
		try {
			id = asmS.restart(modelPath,oldId, state);
			ids = checkStartId(id);
			sout = new StartOutput(ids, "The id " + ids + " is successfully created");
			simulationRunning = SimStatus.READY;
			System.out.println(sout.toString());
		} catch (Exception e) {
			if (e instanceof MainRuleNotFoundException) {
				sout = new StartOutput(-2, "Main Rule Not Found");
				System.err.println(sout.toString());
			} 
			else if (e instanceof AsmModelNotFoundException) {
				sout = new StartOutput(-3,
						"The Model " + modelPath.substring(modelPath.lastIndexOf("/") + 1) + " Doesn't esist");
				System.err.println(sout.toString());
			} 
			else if (e instanceof FullMapException) {
				sout = new StartOutput(-4, "The simulator map is Full " + "can't add the model <"
						+ modelPath.substring(modelPath.lastIndexOf("/") + 1) + "> in the simulator map");
				System.err.println(sout.toString());
			} 
			else if (e instanceof ParseException) {
				sout = new StartOutput(-5, "The model name " + modelPath.substring(modelPath.lastIndexOf("/") + 1)
						+ " Does not match:" + " Check for case sensitive in <<modelname.asm>>");
				System.err.println(sout.toString());
			} 
			else if (e instanceof InvalidInvariantException) {
				sout = new StartOutput(-7, "Invalid invariant on initial state, please check the updated model again");
				System.err.println(sout.toString());
			}
			else {
				sout = new StartOutput(-6, "General Exception: Please report the problem");
				System.err.println(sout.toString());
			}
		}
		return sout.getId();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see asmeta.safetyawaremodeleexecute.client.IModelExecute#stopExecution(int)
	 * stop of giusso remove a simulator(model) by an id
	 */
	// @Override
	public int stopExecution(int id) {
		try {
			System.out.println("Model " + asmS.getModelName(id) + " successfully stopped");
			asmS.stop(id);
			id = 1;
		} catch (RuntimeException e) {
			if (e instanceof IdNotFoundException) {
				System.out.println("Id " + id + " not found");
				id = -1;
			}
		}
		return id;
	}
	
	@Override
	public int init(int maxSimInstance) {
		while (maxSimInstance <= 0) {
			System.out.print("Insert a positive number for the simulator table: ");
			Scanner scan = new Scanner(System.in);
			maxSimInstance = scan.nextInt();
			scan.close();
		}
		//System.out.printf("Max number of simulators instantiated: %d" + "\n", maxSimInstance);
		asmS.init(maxSimInstance);
		return maxSimInstance;

	}

	@Override
	public RunOutput runStep(int id, Map<String, String> locationValue) {
		simulationRunning=SimStatus.RUNNING;
		//rollbStatus = rollbackStatus.NOTROLLED;
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
		String modelPath=null;
		try {
			try {
				modelPath=asmS.getSimulatorTable().get(id).getModelPath();
			} catch (NullPointerException e) {
				throw new IdNotFoundException("Id not valid");
			}
			if (locationValue!=null) 
				rout = checkSafety(modelPath, locationValue);
			if (rout.equalsMessage(new RunOutput(Esit.UNSAFE, "rout not initialized"))) {
				startRun = System.nanoTime();
				ms = asmS.run(id, locationValue); //run ASM with id and monitored locations locationValue
				endRun = System.nanoTime();
				duration = (endRun - startRun);
				if (locationValue!=null) 
					rout = new RunOutput(Esit.SAFE, asmS.getCurrentState(id).getMonitoredValues(), ms);
				else
					rout = new RunOutput(Esit.SAFE, ms);
				printState(asmS.getSimulatorTable().get(id).getSim().getCurrentStep(), rout, duration, id);
			}
		} catch (Exception e) {
			if (e instanceof InvalidInvariantException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model "
						+ asmS.getSimulatorTable().get(id).getModelPath().substring(modelPath.lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}

			} else if (e instanceof IdNotFoundException) {
				rout = new RunOutput(Esit.UNSAFE, "the id is not found");
				System.err.println(rout.toString());

			} else if (e instanceof UpdateClashException) {
				if (modelPath != null)
					System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
							+ "  for model "
							+ asmS.getSimulatorTable().get(id).getModelPath().substring(modelPath.lastIndexOf("/") + 1));
				else
					System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
							+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
									.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}

			} else if (e.getCause() instanceof InputMismatchException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ " for model "
						+ asmS.getSimulatorTable().get(id).getModelPath().substring(modelPath.lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid input value");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}
			} else {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ " for model "
						+ asmS.getSimulatorTable().get(id).getModelPath().substring(modelPath.lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid input or domain value");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}
			}
		}
		simulationRunning = SimStatus.READY;
		return rout; 

	}
	
	@Override
	public RunOutput runStep(int id) {
		return runStep(id, null);
	}
	
	/*@Override
	public RunOutput runStep(int id) {
			
		RunOutput rout = null;
		ss=SimStatus.RUNNING;
		try {
			startRun = System.nanoTime();
			ms = asmS.run(id);
			endRun = System.nanoTime();
			duration = (endRun - startRun);
			rout = new RunOutput(Esit.SAFE, ms);
			printState(stepRun++, rout, duration, id);

		} catch (RuntimeException e) {
			if (e instanceof IdNotFoundException) {
				rout = new RunOutput(Esit.UNSAFE, "the id is not found");
				System.err.println(rout.toString());

			} else if (e instanceof UpdateClashException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
								.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
				System.err.println(rout.toString());
				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));

			}
		}
		ss=SimStatus.READY;
		return rout; // can be use for Json

	}*/
	
	public RunOutput runStepTimeout(int id,int timeout) {
		return runStepTimeout(id,null,timeout);
	}
	public RunOutput runStepTimeout(int id,Map<String, String> locationValue,int timeout) {	
		//Timer timer = new Timer(false);
		
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
	    routTO = rout;
	    PrintStream sysOut = System.out;
	    PrintStream sysErr = System.err;
		Thread t = new Thread() {
			public void run() {
				try {
					System.setOut(new PrintStream(new OutputStream() {
						public void write(int b) {}})); //temporarily blocks system.out prints to console to remove overwhelming messages
				    System.setErr(new PrintStream(new OutputStream() {
					  public void write(int b) {}})); //temporarily blocks system.err prints to console to remove overwhelming messages
					SimulationContainer clone = new SimulationContainer();	//instantiation of a cloned execution
					clone.init(1);
					String modelPath = asmS.getSimulatorTable().get(id).getModelPath();
					org.asmeta.simulator.State stateOrig = asmS.getSimulatorTable().get(id).getSim().getCurrentState();
					org.asmeta.simulator.State stateClon = new org.asmeta.simulator.State(stateOrig); //clone constructor
					if (stateClon.previousLocationValues==null)
						stateClon.previousLocationValues = new HashMap<Location, Value>();
					int id;
					if (stateClon.locationMap.isEmpty())
						id = clone.startExecution(modelPath);
					else
						id = clone.restartExecution(modelPath,1, stateClon);	//in order to be a clone, it needs to be started at the same state as the original
	           		routTO = clone.runStep(id, locationValue);	//the cloned simulation tries to do the operation
	           		routTO.setTimeoutFlag(true); //once finished, signal the original with the RunOutput object that simulation has stopped, probably deprecated
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			t.start();
			try {
				t.join(timeout);
				//Thread.sleep(50);
			} catch (InterruptedException e) {
				// DEBUG
				System.out.println("TIMEOUT ERROR");
				e.printStackTrace();
			}
			
			if (t.isAlive()) { //timeout triggered
				//t.stop();
				t.interrupt();
				rout = new RunOutput(Esit.UNSAFE, "Run timed out");
			}else {	//finished before the timeout
				System.setOut(sysOut);
				System.setErr(sysErr);
				rout=this.runStep(id,locationValue);
				rout.setTimeoutFlag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.setOut(sysOut); //must restore system.out functionality
			System.setErr(sysErr); // and system.err
		}
		return rout;
        /*TimerTask timeoutTask = new TimerTask() {
        	@Override  
            public void run() {  
            	 //System.out.println("Timer task started at:"+new Date());
        		try {
        			SimulationContainer mirror = new SimulationContainer();	//instantiation of a mirrored execution
        			mirror.init(1);
        			String modelPath = asmS.getSimulatorTable().get(id).getModelPath();
        			State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState();
        			if (state.previousLocationValues==null)
        				state.previousLocationValues = new HashMap<Location, Value>();
        			mirror.restartExecution(modelPath,1, state);	//after getting the model file path and current state, the mirrored simulation is started to be at the same state of the original
               		routTO = mirror.runStep(1, locationValue);	//the mirrored simulation tries to do the operation
        		}catch (Exception e) {}
        		finally {
               		routTO.setTimeoutFlag(true); //once finished, signal with the RunOutput object that simulation has stopped
        			timer.cancel(); //->"Note that calling this method from within the run method of a timer task that was invoked by this timer absolutely guarantees that the ongoing task execution is the last task execution that will ever be performed by this timer."
        		}

                 //System.out.println("Timer task finished at:"+new Date());
            }; 
        };  
	    timer.schedule(timeoutTask, 0);
	    //System.out.println("TimerTask started");
	    if (timeout<0)
	    	timeout=1;	//negative timeout values -> sets minimum timeout time (1ms as int)
	    try {
	    	int splits=10;	//10ms time splits waited before controlling if task is finished
	    	for (int i=0;splits*i<timeout && !routTO.getTimeoutFlag();i++)	//keep going until timeout exceeded or task (run) finished
	    		Thread.sleep(splits);
	    } catch (InterruptedException e) {
	    	//DEBUG
	        e.printStackTrace();
	    }
	    if (!routTO.getTimeoutFlag()) {
	    	timer.cancel();	//task didn't finish, i can cancel the run without repercussions as the simulation object inside is different and doesn't affect the original
	    	//in the original timeout method, cancelling the task meant that execution on the simulator was only temporarily paused, not halted nor reversed, so on the next execution it would continue to finish before processing the new order. Without the removal of singleton patter this was made possible 
	    	rout = new RunOutput(Esit.UNSAFE, "Run timed out");
	    }else {
	    	//if the mirrored run was good (inside timeout) i can push the real simulation to the correct state and return the RunOutput
	    	rout=runStep(id,locationValue);
	    }
	    
	    return rout;*/
	}
	
//	//Rimane comunque il problema che se allo scadere non ha finito il programma restituirà run timed out con rollback della sim
//	//ma la simulazione deve comunque finire altrimenti tutte le run successive non funzioneranno (forse mettendo il timeout direttamente
//	//nella parte del simulator invece del container in modo tale di almeno arginare il finish forzato su uno step)
//	public RunOutput runStepTimeout(int id,Map<String, String> locationValue,int timeout) {	
//		Timer timer = new Timer(false);
//		
//		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
//	    routTO = rout;
//	    
//	    MyState pre;
//	    
//		try {
//			pre = asmS.getCurrentState(id);
//			if (pre!=null)
//				pre.monitoredValues=null;
//		} catch (IdNotFoundException e) {
//			rout = new RunOutput(Esit.UNSAFE, "the id is not found");
//			System.err.println(rout.toString());
//			return rout;
//		}
//	    
//        TimerTask timeoutTask = new TimerTask() {
//        	@Override  
//            public void run() {  
//            	 //System.out.println("Timer task started at:"+new Date());
//        		try {
//               		routTO = runStep(id, locationValue);
//        		}catch (Exception e) {}
//        		finally {
//               		routTO.setTimeoutFlag(true);
//        			timer.cancel();
//        		}
//
//                 //System.out.println("Timer task finished at:"+new Date());
//            }; 
//        };  
//	    //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
//	    timer.schedule(timeoutTask, 0);
//	    //System.out.println("TimerTask started");
//	    //cancel after timeout if the task has not terminated
//	    if (timeout<0)
//	    	timeout=1;
//	    try {//soluzione brutta per far finire il timeout prima
//	    	int splits=10;
//	    	for (int i=0;splits*i<timeout && !routTO.getTimeoutFlag();i++)
//	    		Thread.sleep(splits);
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    }
//	    /*if (! taskTerminated.getResult()) { 
//	    	timer.cancel();
//	    	System.out.println("TimerTask cancelled"+" -- flag TaskTerminated: "+taskTerminated.getResult());
//	    }
//	    else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+taskTerminated.getResult());*/
//	    
//	    if (!routTO.getTimeoutFlag()) {	//if the thread is still going after time runs out  
//	    	boolean rolledbackQ=false;
//    		while (simulationRunning==SimStatus.ROLLINGBACK) {	//cannot stop the timertask while it's doing a rollback
//    			rolledbackQ=true;
//    			try {
//    				Thread.sleep(10);	//how often the program check for the rollback to finish before killing the thread
//        		} catch (InterruptedException e) {
//                    e.printStackTrace();}
//    		}
//    		while (!routTO.getTimeoutFlag()) {	//se non ho ancora rOut vuol dire che non ha ancora finito, devo aspettare altrimenti non funziona più nulla successivamente
//    			try {
//    				Thread.sleep(10);	
//        		} catch (InterruptedException e) {
//                    e.printStackTrace();}
//    		}
//    		timer.cancel();
//    		if (new RunOutput(Esit.UNSAFE, "").equals(routTO))
//    			rolledbackQ=true;
//	    	rout = new RunOutput(Esit.UNSAFE, "Run timed out");	//returns that the operation couldn't be finished in time
//	    	System.err.println(rout.toString());
//
//	    	MyState after = asmS.getCurrentState(id);
//	    	
//			if (/*rollbStatus!=rollbackStatus.ROLLOK &&*/ after!=null && !after.equals(pre))	//do a rollback if it has not already been done 
//			{	//OLDtodo se il rollback è già stato fatto lo fa di nuovo perchè l'equals di mystate non esiste (provato a sistemare con variabile rolledbackQ)
//				try {
//					if (!rolledbackQ)
//						printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
//				} catch (NullPointerException e1) {
//					System.out.println("No previous state!");
//				}/* catch (EmptyStackException e1) {
//					System.out.println("empty stack exception dal simulator");
//				}*/
//			}
//	    	
//	    	/*timer.cancel();
//	    	routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
//			System.err.println(routTO.toString());
//			/*try {
//				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
//			} catch (NullPointerException e1) {
//				System.out.println("No previous state!");
//			}*/
//	    }else
//	    	rout=routTO;
//	    //else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+routTO.getResult());	
//	        
//	 	return rout;
//	}
	


	
	@Override
	public RunOutput runUntilEmpty(int id, Map<String, String> locationValue, int max) {
		simulationRunning = SimStatus.RUNNING;
		//rollbStatus = rollbackStatus.NOTROLLED;
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");		
		String modelPath=null;
		try {
			try {
				modelPath=asmS.getSimulatorTable().get(id).getModelPath();
			} catch (NullPointerException e) {
				throw new IdNotFoundException("Id not valid");
			}
			if (locationValue!=null)
				rout = checkSafety(modelPath, locationValue);
			
			if (rout.equalsMessage(new RunOutput(Esit.UNSAFE, "rout not initialized"))) {
				startRun = System.nanoTime();
				ms = asmS.runUntilEmpty(id, locationValue, max);
				endRun = System.nanoTime();
				duration = (endRun - startRun);
				rout = new RunOutput(Esit.SAFE, asmS.getCurrentState(id).getMonitoredValues(), ms);
				printState(asmS.getSimulatorTable().get(id).getContSim(), rout, duration, id);
			}
		} catch (RuntimeException e) {
			if (e instanceof InvalidInvariantException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
								.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid Invariant");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));					
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}

			} else if (e instanceof IdNotFoundException) {
				//System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
					//	+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
							//	.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "the id is not found");
				System.err.println(rout.toString());

			} else if (e instanceof UpdateClashException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
								.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Inconsistent Update");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}
			} else if (e.getCause() instanceof InputMismatchException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
								.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid input value");
				System.err.println(rout.toString());
				try {
					simulationRunning=SimStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("No previous state!");
				}finally {
					simulationRunning=SimStatus.RUNNING;
				}

			}
		}
		simulationRunning=SimStatus.READY;
		return rout;

	}
	
	@Override
	public RunOutput runUntilEmpty(int id) {
		return runUntilEmpty(id, null, 0);
	}

	@Override
	public RunOutput runUntilEmpty(int id, Map<String, String> locationValue) {
		return runUntilEmpty(id, locationValue, 0);
	}

	@Override
	public RunOutput runUntilEmpty(int id, int max) {
		return runUntilEmpty(id, null, max);
	}
	
	public RunOutput runUntilEmptyTimeout(int id, int max, int timeout) {
	 	 
	 	return runUntilEmptyTimeout(id, null, max, timeout);
	}
	
	//same logic as the one with runstep but using runUntilEmpty
	public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, int max, int timeout) {
		//Timer timer = new Timer(false);
		
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
	    routTO = rout;
	    PrintStream sysOut = System.out;
	    PrintStream sysErr = System.err;
		Thread t = new Thread() {
			public void run() {
				try {
			    System.setOut(new PrintStream(new OutputStream() {
				  public void write(int b) {}})); //temporarily blocks system.out prints to console to remove overwhelming messages
			    System.setErr(new PrintStream(new OutputStream() {
				  public void write(int b) {}})); //temporarily blocks system.err prints to console to remove overwhelming messages
				SimulationContainer clone = new SimulationContainer();	//instantiation of a cloned execution
				clone.init(1);
				String modelPath = asmS.getSimulatorTable().get(id).getModelPath();
				org.asmeta.simulator.State stateOrig = asmS.getSimulatorTable().get(id).getSim().getCurrentState();
				org.asmeta.simulator.State stateClon = new org.asmeta.simulator.State(stateOrig); //clone constructor
				if (stateClon.previousLocationValues==null)
					stateClon.previousLocationValues = new HashMap<Location, Value>();
				int id;
				if (stateClon.locationMap.isEmpty())
					id = clone.startExecution(modelPath);
				else
					id = clone.restartExecution(modelPath,1, stateClon);	//in order to be a clone, it needs to be started at the same state as the original
           		routTO = clone.runUntilEmpty(id, locationValue, max);	//the cloned simulation tries to do the operation
           		routTO.setTimeoutFlag(true); //once finished, signal the original with the RunOutput object that simulation has stopped, probably deprecated
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			t.start();
			try {
				t.join(timeout);
				//Thread.sleep(50);
			} catch (InterruptedException e) {
				// DEBUG
				System.out.println("TIMEOUT ERROR");
				e.printStackTrace();
			}
			
			if (t.isAlive()) { //timeout triggered
				//t.stop();
				t.interrupt();
				rout = new RunOutput(Esit.UNSAFE, "Run timed out");
			}else {	//finished before the timeout
				System.setOut(sysOut);
				System.setErr(sysErr);
				rout=this.runUntilEmpty(id,locationValue,max);
				rout.setTimeoutFlag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.setOut(sysOut); //must restore system.out functionality
			System.setErr(sysErr); // and system.err
		}
		return rout;
	}
	
//	//same logic as the one with runstep but using runUntilEmpty and rollbacktostate instead
//	//OLD sistemare problema della sleep come in runstep
//	public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, int max, int timeout) {
//
//        Timer timer = new Timer(false);
//        RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
//        routTO = rout;
//		MyState pre;
//		try {
//			pre = asmS.getCurrentState(id);
//		} catch (IdNotFoundException e) {
//			rout = new RunOutput(Esit.UNSAFE, "the id is not found");
//			System.err.println(rout.toString());
//			return rout;
//		}
//		
//        TimerTask timeoutTask = new TimerTask() {
//        	@Override  
//            public void run() {  
//            	 //System.out.println("Timer task started at:"+new Date());
//        		try {
//        			routTO = runUntilEmpty(id, locationValue, max);
//        		}catch (Exception e) {}
//        		finally {
//               		routTO.setTimeoutFlag(true);
//        			timer.cancel();
//        		}
//                 //System.out.println("Timer task finished at:"+new Date());
//            }; 
//        };  
//
//        //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
//        timer.schedule(timeoutTask, 0);
//        //System.out.println("TimerTask started");
//        //cancel after timeout if the task has not terminated
//	    if (timeout<0)
//	    	timeout=1;
//        try {
//            Thread.sleep(timeout);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        /*if (! taskTerminated.getResult()) { 
//        	timer.cancel();
//        	System.out.println("TimerTask cancelled"+" -- flag TaskTerminated: "+taskTerminated.getResult());
//        }
//        else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+taskTerminated.getResult());*/
//        if (!routTO.getTimeoutFlag()) {	//if the thread is still going after time runs out  
//    		while (simulationRunning==SimStatus.ROLLINGBACK) {	//cannot stop the timertask while it's doing a rollback
//    			try {
//    				Thread.sleep(10);	//how often the program check for the rollback to finish before killing the thread
//        		} catch (InterruptedException e) {
//                    e.printStackTrace();}
//    		}
//    		while (!routTO.getTimeoutFlag()) {
//    			try {
//    				Thread.sleep(10);	
//        		} catch (InterruptedException e) {
//                    e.printStackTrace();}
//    		}
//    		timer.cancel();
//	    	rout = new RunOutput(Esit.UNSAFE, "Run timed out");	//returns that the operation couldn't be finished in time
//	    	System.err.println(rout.toString());
//
//	    	MyState after = asmS.getCurrentState(id);
//	    	
//			if (/*rollbStatus!=rollbackStatus.ROLLOK &&*/ after!=null && !after.equals(pre))	//do a rollback if it has not already been done 
//			{
//				try {
//					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollbackToState(id));
//				} catch (NullPointerException e1) {
//					System.out.println("No previous state!");
//				}/* catch (EmptyStackException e1) {
//					System.out.println("empty stack exception dal simulator");
//				}*/
//			}
//        	
//        	/*timer.cancel();
//        	routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
//			System.err.println(routTO.toString());
//			try {
//				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
//			} catch (NullPointerException e1) {
//				System.out.println("No previous state!");
//			}/* catch (EmptyStackException e1) {
//				System.out.println("empty stack exception dal simulator");
//			}*/
//        }else
//        	rout=routTO;
//        
//        //else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+routTO.getResult());	  
//	 	return rout;
//	}
	

	public RunOutput runUntilEmptyTimeout(int id, int timeout) {
		return runUntilEmptyTimeout(id, null, 0, timeout);
	}


	public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, int timeout) {
		return runUntilEmptyTimeout(id, locationValue, 0, timeout);
	}

	private  void printState(int step, RunOutput r1, long time, int id) {
		System.out.println(
				"[step:" + asmS.getSimulatorTable().get(id).getContSim() + " of " + asmS.getModelName(id) + "]");
		System.out.println(r1.toString());
		System.out.println("Execution time (in milliseconds): " + time/ 1000000 +" ms");
		//System.out.println(
		//		"Number of steps for the transition: " + asmS.getSimulatorTable().get(id).getSim().getCurrentStep());
		/*
		 * System.out.println("Maximum number of state before rollBackToState: " +
		 * asmS.getSimulatorTable().get(id).getSim().getMax() );
		 */

		System.out.println("                                 ");
		//System.out.println("=====================");
		
	}

	private  void printRollback(int step, MyState state) {

		System.out.println("Model rollback to previous step: " + step);

		if (state.controlledValues.size() != 0)
			System.out.println("< Controlled function >");

		for (Entry<Location, Value> entry : state.getControlledValues().entrySet()) {
			System.out.println("- " + entry.getKey().toString() + ": " + entry.getValue().toString());
		}
		System.out.println("=====================");

	}

	/**
	 * findAllMonitored: Search recursively for monitored function names in the model and all imported ones
	 *
	 * @param monNames: starting list (starts empty)
	 * @param modelPath: Path file root (starts at the same directory the main .asm is located) and goes deep as each module is opened
	 * @return list with all monitored function names in the ASM model
	 * @throws Exception from ASMParser
	 */
	private List<String> findAllMonitored(List<String> monNames, String modelPath) throws Exception{
		String root="";
		root=modelPath.substring(0,modelPath.lastIndexOf("/")+1);
		File asmFile = new File(modelPath);
		try {
			if (!asmFile.exists()) {
				throw new AsmModelNotFoundException(modelPath);
			}
		}catch (AsmModelNotFoundException ex){
			//DEBUG
			System.out.println("CheckSafety: "+ex.getMessage());
		}
		AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
		// cerco di prendere la classe delle monitorate  NON LEGGE LE MONITORATE NEI FILE DI IMPORT
		for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
			if (asm.getMain().getHeaderSection().getSignature().getFunction()
					.get(i) instanceof MonitoredFunctionImpl)
				monNames.add(asm.getMain().getHeaderSection().getSignature().getFunction().get(i).getName());
		}
		int c = asm.getMain().getHeaderSection().getImportClause().size();
		for (int i=0;i<c;i++) {
			String moduleName=asm.getMain().getHeaderSection().getImportClause().get(i).getModuleName();
			if (!moduleName.toLowerCase().endsWith("standardlibrary"))	//Skips the StandardLibrary.asm
				monNames=findAllMonitored(monNames, root+moduleName+".asm");
		}
		return monNames;
	}
	//Could be improved by adding a List<String> moduleNames to skip multiple imports of the same .asm (if it's possible to create import cycles)

	/**
	 * Check safety: Checks if given monitored function names are correct
	 *
	 * @param modelPath     the model path
	 * @param locationValue Change to CheckInputName Safety
	 * @return the array list
	 */
	/**
	 * Check safety: Checks if given monitored function names are correct
	 *
	 * @param modelPath     the model path
	 * @param locationValue Change to CheckInputName Safety
	 * @return the array list
	 */
	private RunOutput checkSafety(String modelPath, Map<String, String> locationValue) {
		List<String> nameL = new ArrayList<String>(); // monitored functions name list
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
		// AsmCollection asm = null;
		String name = "";
		try {
			nameL = findAllMonitored(nameL, modelPath);
			//System.out.println("Patrizia modelpath: "+ modelPath + " nanmeL: "+nameL.toString()+"\nlocations: "+locationValue.toString());
			
			/*
			File asmFile = new File(modelPath);
			if (!asmFile.exists()) {
				throw new AsmModelNotFoundException(modelPath);
			}

			AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
			// cerco di prendere la classe delle monitorate  NON LEGGE LE MONITORATE NEI FILE DI IMPORT
			for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
				if (asm.getMain().getHeaderSection().getSignature().getFunction()
						.get(i) instanceof MonitoredFunctionImpl)
					nomi.add(asm.getMain().getHeaderSection().getSignature().getFunction().get(i).getName());
			}*/

	
			boolean found = false;
			for (String s : locationValue.keySet()) {
				//This part lets multiple parameters monitored functions name pass if at least the name is correct
				String monName = "".concat(s);
				if (monName.contains("(") && monName.endsWith(")"))
					monName=monName.substring(0,monName.indexOf("("));
				
				for (int i = 0; i < nameL.size(); i++) {
					if (monName.equals(nameL.get(i))) {
						found = true;
					}
				}
				if (!found) {
					name = monName;
					throw new NameMistMatchException("Name <<" + s + ">> Not Found");
				} else
					found = true;

			}
		} catch (Exception e) {
			if (e instanceof NameMistMatchException) {
				//System.err.println("No transition to step " 
						//+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
							//	.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "monitored location name <<" + name + ">> not found");
				System.err.println(rout.toString());
			}

		}

		return rout;
	}
	
	/**
	 * Check start id.
	 *
	 * @return the int
	 * @throws FullMapException the full map exception
	 */
	private int checkStartId(int idC) throws FullMapException { 
		if (idC == -1) {
			idC = -5;
			throw new FullMapException("The map is full");
		}

		return idC;

	}
	
	//OLDTODO Federico Rebucini->da finalizzare? permette di eseguire n runstep con una lista di n input, se uno di questi fallisce viene rollbackato alla partenza
	/*public RunOutput runStepTransaction(int id,Queue<Map<String, String>> locationValue, String modelPath) {
		boolean unsafe = false;
		RunOutput routTR = new RunOutput(Esit.UNSAFE, "rout not initialized");
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
		Map<String,String> element = new HashMap<String, String>();
		int statecount=0;
		while (!locationValue.isEmpty() && unsafe == false) {
			element=locationValue.poll();
			rout = runStep(id,element);
			statecount++;
			if(routTR.equals(rout)) {
				unsafe = true;
			}
		}
		
		if(unsafe==true)
		{
			for(int i=1;i<statecount;i++)
				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
		}
		
		return rout;	
		
	}*/
	/*public RunOutput runTransaction(int id,Queue<Map<String, String>> locationValue) { //da finalizzare?
		boolean unsafe = false;
		RunOutput routTR = new RunOutput(Esit.UNSAFE, "rout not initialized");
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not initialized");
		Map<String,String> element = new HashMap<String, String>();
		int statecount=0;
		while (!locationValue.isEmpty() && unsafe == false) {
			element=locationValue.poll();
			rout = runUntilEmpty(id,element);
			statecount++;
			if(routTR.equals(rout)) {
				unsafe = true;
			}
		}
		
		if(unsafe==true)
		{
			for(int i=1;i<statecount;i++)
				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollbackToState(id));
		}
		
		return rout;	
		
	}*/
	
	@Override
	public int addInvariant(int id, String invariant_to_add) {
		if (!asmS.checkValidId(id))
			return -1;
		simulationRunning=SimStatus.ADAPTING;
		boolean success = true;
		int start_output = 0;     // if > 0 everything goes well   if<0 exception error
		String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
		State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
		String variable = "";
		String appoggio;
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(modelfile));
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"_to_overwrite"));
			String line = reader.readLine();
			
			Files.copy(Paths.get(modelfile), Paths.get(modelfile+"_old"), StandardCopyOption.REPLACE_EXISTING);
			if(invariant_to_add.contains("inv_"))
			{
				variable=invariant_to_add.substring(invariant_to_add.indexOf("inv_")+4,invariant_to_add.indexOf("over"));
			}
			
			while (line != null)
			{
				appoggio=line.trim();
				if(appoggio.contains("inv_"))
				{
					if(appoggio.substring(appoggio.indexOf("inv_")+4,appoggio.indexOf("over")).equals(variable))
					{
						System.out.println("UGUALI");
						success=false;
					}
				}
				if (success)
				{
					if (line.trim().startsWith("main rule")){
						writer.write("\t"+invariant_to_add+"\n");
					}
				}
				writer.write(line+"\n");
				line = reader.readLine();
			}
			reader.close();	
			writer.close();
			
			/*File file = new File(modelfile);
			file.delete();
			File file2 = new File(modelfile+"_to_overwrite");
			success = file2.renameTo(file);*/
			overwrite(modelfile,"_to_overwrite");
			
		    /*File f = new File(modelfile);		//CONTROLLO SE FILE APERTO
		    File sameFileName = new File(modelfile);
		    if(f.renameTo(sameFileName)){
		        System.out.println("file is closed");    
		    }else{
		        System.out.println("file is opened");
		    }*/
		    
			if (success)
				start_output=restartSim(id, state);
			else
				start_output=-8;
			if (start_output<0) {
				
			    /*f = new File(modelfile);		//CONTROLLO SE FILE APERTO
			    sameFileName = new File(modelfile);
			    if(f.renameTo(sameFileName)){
			        System.out.println("file is closed");    
			    }else{
			        System.out.println("file is opened");
			    }*/
			    
				overwrite(modelfile,"_old");
			    //Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
				if (success)
					restartExecution(modelfile,id, state); 
			}

		} catch (IOException e) {
			System.out.println("Couldn't open or write the model file");
			success=false;
		} finally {
			try {
				Files.deleteIfExists(Paths.get(modelfile+"_old"));
				Files.deleteIfExists(Paths.get(modelfile+"_to_overwrite"));
			} catch (IOException e) {}
		}
		simulationRunning=SimStatus.READY;
	    return start_output;
	}



	@Override
	public InvariantData viewListInvariant(int id) {
		String invar;
		//List<String> invarList = new ArrayList<String>();
		InvariantData inv_manager = new InvariantData();
		if (!asmS.checkValidId(id))
			return null;
		String values="";
		//variables = new ArrayList<String>();
		invarNames = new ArrayList<String>();
		boolean endinvariant = true;
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(asmS.getSimulatorTable().get(id).getModelPath()));
			String line = reader.readLine().trim();
			if (line==null)
				endinvariant=false;
			while (endinvariant) {
				if (line.contains("signature")){
					while(!line.contains("definitions"))
					{
						if(line.contains("controlled") || line.contains("monitored") || line.contains("derived"))
						{
							if(line.contains("controlled"))
							{
							   values=line.substring(line.indexOf("controlled")+11,line.indexOf(":")).trim();
							   inv_manager.variables.add(values);
							}
							else if(line.contains("monitored"))
							{
								values=line.substring(line.indexOf("monitored")+10,line.indexOf(":")).trim();
								inv_manager.variables.add(values);
							}else if(line.contains("derived"))
							{
								values=line.substring(line.indexOf("derived")+8,line.indexOf(":")).trim();
								inv_manager.variables.add(values);
							}
							
						}
						line = reader.readLine().trim();
					}
				}
				if (line.startsWith("invariant")){
					invar="";
					do {
						if (line.contains("//"))
							invar = invar+line.substring(0, line.indexOf("//"));
						else
							invar = invar+line;
						line=reader.readLine().trim();
						if (line==null || line.startsWith("main rule"))
							endinvariant=false;
					}while(endinvariant && !line.startsWith("invariant"));
					if(invar.contains("inv_"))
						invarNames.add(invar.substring(invar.indexOf("inv_")+4,invar.indexOf("over")-1));
					//System.out.println(invar);
					inv_manager.invarList.add(invar);
				}else {
					if(line.startsWith("main rule"))
						endinvariant=false;
					line = reader.readLine().trim();
				}
			}
			reader.close();	
		} catch (IOException e) {
			System.out.println("Couldn't open and read the given model");
		} 
		return inv_manager;
	}


	@Override
	public int updateInvariant(int id, String new_invariant, String old_invariant) {
		if (!asmS.checkValidId(id))
			return -1;
		simulationRunning=SimStatus.ADAPTING;
		old_invariant=old_invariant.substring(0, old_invariant.indexOf(':')+1)+old_invariant.substring(old_invariant.indexOf(':')+1).trim();
		boolean endinvariant=true;
		int start_output = 0;
		String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
		State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
		BufferedReader reader;
		BufferedReader reader2;
		boolean exit=true;
		try {
			reader2 = Files.newBufferedReader(Paths.get(modelfile));
			Files.copy(Paths.get(modelfile), Paths.get(modelfile+"_old"), StandardCopyOption.REPLACE_EXISTING);
			String line = reader2.readLine();
			String reading_invariant;
			String inv="";
			String new_invariant_name = "";
			String old_invariant_name = "";
			if(new_invariant.contains("inv_"))
			{							
				new_invariant_name = new_invariant.substring(new_invariant.indexOf("inv_")+4,new_invariant.indexOf("over")).trim();
				if(old_invariant.contains("inv_"))
					old_invariant_name = old_invariant.substring(old_invariant.indexOf("inv_")+4,old_invariant.indexOf("over")).trim();
				while(line!=null && exit)
				{
					if (line.trim().contains("inv_")){					
					   inv=line.substring(line.indexOf("inv_")+4,line.indexOf("over")).trim();
					   System.out.println("INV "+inv);
						if(new_invariant_name.equals(inv) && !inv.equals(old_invariant_name))
						{
							start_output=-8;
							exit=false;	
						}
					}
					 line=reader2.readLine();
				}
				reader2.close();
			}
			 
		    if(start_output!=-8)
			{
				reader = Files.newBufferedReader(Paths.get(modelfile));		
			    line = reader.readLine();
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"_to_overwrite"));
				Files.copy(Paths.get(modelfile), Paths.get(modelfile+"_old"), StandardCopyOption.REPLACE_EXISTING);
				while (line!=null)
				{
					if (line.trim().startsWith("invariant")){
						reading_invariant="";
						do {
							if (line.contains("//"))
								reading_invariant = reading_invariant+line.substring(0, line.indexOf("//"));
							else
								reading_invariant = reading_invariant+line;
							if(line.startsWith("//"))
								writer.write("\t"+line+"\n");
							line=reader.readLine().trim();
							if (line==null || line.startsWith("main rule"))
								endinvariant=false;
						}while(endinvariant && !line.startsWith("invariant"));
						if(reading_invariant.contains(": "))
							reading_invariant = reading_invariant.replace(": ", ":");
						if(reading_invariant.trim().equals(old_invariant))
							writer.write("\t"+new_invariant+"\n");
						else
							writer.write("\t"+reading_invariant.trim()+"\n");
					}
					else {
						if(line.startsWith("main rule"))
							writer.write("\n\t"+line+"\n");
						else
							writer.write(line+"\n");
						line = reader.readLine();
					}
				}
			reader.close();
			writer.close();
		
			/*File file = new File(modelfile);
			file.delete();
			File file2 = new File(modelfile+"_to_overwrite");
			success = file2.renameTo(file);	*/
			overwrite(modelfile,"_to_overwrite");
			start_output=restartSim(id, state);
			}
		    if (start_output<0) {
				overwrite(modelfile,"_old");
				//Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
				restartExecution(modelfile,id,state);
			}
		}
		catch (IOException e) {
			System.out.println("Couldn't open and read the given model");
		} finally {
			try {
				Files.deleteIfExists(Paths.get(modelfile+"_old"));
				Files.deleteIfExists(Paths.get(modelfile+"_to_overwrite"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	simulationRunning=SimStatus.READY;
	return start_output;
	}
	

	@Override
	public boolean removeInvariant(int id, String remove_invariant) {
			if (!asmS.checkValidId(id))
				return false;
			simulationRunning=SimStatus.ADAPTING;
			remove_invariant=remove_invariant.substring(0, remove_invariant.indexOf(':')+1)+remove_invariant.substring(remove_invariant.indexOf(':')+1).trim();
			boolean success = false;
			boolean endinvariant=true;
			String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
			State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
			BufferedReader reader;
			try {
				reader = Files.newBufferedReader(Paths.get(modelfile));
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"_to_overwrite"));
				
				Files.copy(Paths.get(modelfile), Paths.get(modelfile+"_old"), StandardCopyOption.REPLACE_EXISTING);
				
				String line = reader.readLine();
				String reading_invariant;
				while (line!=null)
				{
					if (line.trim().startsWith("invariant")){
						reading_invariant="";
						do {
							if (line.contains("//"))
								reading_invariant = reading_invariant+line.substring(0, line.indexOf("//"));
							else
								reading_invariant = reading_invariant+line;
							if(line.startsWith("//"))
								writer.write("\t"+line+"\n");
							line=reader.readLine().trim();
							if (line==null || line.startsWith("main rule"))
								endinvariant=false;
							if(reading_invariant.contains(": "))
								reading_invariant = reading_invariant.replace(": ", ":");
						}while(endinvariant && !line.startsWith("invariant"));
						if(!reading_invariant.trim().equals(remove_invariant))
							writer.write("\t"+reading_invariant.trim()+"\n");
					}
					else {
						if(line.startsWith("main rule"))
							writer.write("\n\t"+line+"\n");
						else
							writer.write(line+"\n");
						line = reader.readLine();
					}
				}
				reader.close();
				writer.close();
				
				/*File file = new File(modelfile);
				file.delete();
				File file2 = new File(modelfile+"_to_overwrite");
				success = file2.renameTo(file);*/
				overwrite(modelfile,"_to_overwrite");
				if (restartSim(id, state)<0) {
					//Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
					overwrite(modelfile,"_old");
					restartExecution(modelfile,id,state);
				}else {
					success=true;
				}
			}
			catch (IOException e) {
				System.out.println("Couldn't open, read or write the model file");
			} finally {
				try {
					Files.deleteIfExists(Paths.get(modelfile+"_old"));
					Files.deleteIfExists(Paths.get(modelfile+"_to_overwrite"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			simulationRunning=SimStatus.READY;
		return success;
	}
	//stops and starts again the simulation
	private int restartSim(int id, State state) {
		//boolean success = true;
		while (simulationRunning == SimStatus.RUNNING) { //cannot stop the simulation until it has finished the current operation
			try {
				Thread.sleep(100);	//how often the program check for the simulation to finish its execution
    		} catch (InterruptedException e) {
                e.printStackTrace();}
		}
		String modelPath = asmS.getSimulatorTable().get(id).getModelPath();
		if (state==null)
			state = asmS.getSimulatorTable().get(id).getSim().getCurrentState();
		if (state.previousLocationValues==null)
			state.previousLocationValues = new HashMap<Location, Value>();
		stopExecution(id);		
		int res = restartExecution(modelPath,id, state);
		/*// DEBUG CONOSCENZA DELLO STATO
		if (res>0) {
			//MyState control=asmS.getSimulatorTable().get(res).getState();
			//asmS.getSimulatorTable().get(res).getSim().getCurrentState().;
			MyState control = new MyState(asmS.getSimulatorTable().get(res).getSim().getCurrentState().getContrLocs(false), null);
			if (control!=null)
				System.out.println("::::CONTROL:::::The new state is: " + control.getControlledValues());
		}
		//end DEBUG*/
		return res;
	}
	
	//used to replace the currently loaded model file with another version regulated by the mod string
	private void overwrite(String model, String mod) {
		File fnew=new File(model);
		FileWriter f2;
		try {
		    f2 = new FileWriter(fnew,false);
		    f2.write(String.join("\n",Files.readAllLines(Paths.get(model+mod))));
		    f2.close();
		 } catch (IOException e) {
		       System.out.println("Couldn't overwrite the model file");
		 } 
	}
	
	/*public List<String> getVariables() {
	    return variables;
	}*/
	
	public Map<Integer, String> getLoadedIDs() {
		int max = asmS.getMaxInstances();
		Map<Integer, String> ids=new HashMap<Integer,String>();
		for (int i=1;i<=max;i++) 
			if (asmS.checkValidId(i))
				ids.put(i, asmS.getSimulatorTable().get(i).getModelPath());
		return ids;
	}
	
	/*public MyState getStatus(int id) {
		if (!asmS.checkValidId(id))
			return new MyState(null, null);
		State s=asmS.getSimulatorTable().get(id).getSim().getCurrentState();
		MyState state = new MyState(s.getContrLocs(false), null);
		return state;
		/*if (state.controlledValues.size() != 0)
			System.out.println("The<Controlled function>");

		for (Entry<Location, Value> entry : state.getControlledValues().entrySet()) {
			System.out.println("- " + entry.getKey().toString() + ": " + entry.getValue().toString());
		}
	}*/
	
	/*private void overwrite(String model) {
		File fnew=new File(model);
		String source = "";
		FileWriter f2;
		try {
			//Files.readAllLines(Paths.get(model+"_old"));
		    f2 = new FileWriter(fnew,false);
		    f2.write(String.join("\n",Files.readAllLines(Paths.get(model+"_old"))));
		    f2.close();
		 } catch (IOException e) {
		       e.printStackTrace();
		 } 
	}*/
	
	/*private void overwrite2(String model) {
		File fnew=new File(model);
		String source = "";
		FileWriter f2;
		try {
			//Files.readAllLines(Paths.get(model+"_to_overwrite"));
		    f2 = new FileWriter(fnew,false);
		    f2.write(String.join("\n",Files.readAllLines(Paths.get(model+"_to_overwrite"))));
		    f2.close();
		 } catch (IOException e) {
		       e.printStackTrace();
		 } finally {
			 try {
					Files.deleteIfExists(Paths.get(model+"_to_overwrite"));
				} catch (IOException e) {}
		 }
	}*/
	
	/*public List<String> getVariables() {
	    return variables;
	  }*/
	/*@Override	 //INVARIANTS CON ASMCOLLECTION DINAMICO
	public List<Invariant> viewListInvariant(int id) {
		
		//System.out.println(asmS.getSimulatorTable().get(id).getSim().);
		//System.out.println(asmS.getSimulatorTable().get(id).getSim().getAsmCollection().getMain().getBodySection().getInvariantConstraint());
		System.out.println( asmS.getSimulatorTable().get(id).getModelPath());
		for (Iterator<Asm> i = asmS.getSimulatorTable().get(id).getSim().getAsmCollection().iterator(); i.hasNext();) {
			Asm asm_i = i.next();
			Body b = asm_i.getBodySection();
			Collection<Property> propertiesList = b.getProperty();
			if (propertiesList != null) {
				for (Property property: propertiesList) {
					if(property instanceof Invariant) {
						//Term body = ((Invariant)property).getBody();
						System.out.println(((Invariant)property).toString());
					}
				}
			}
		}
		return null;
	}*/
	
}


