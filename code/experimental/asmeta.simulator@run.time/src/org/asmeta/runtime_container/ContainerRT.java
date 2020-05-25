package org.asmeta.runtime_container;

import org.asmeta.assertion_catalog.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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

import javax.swing.JLabel;
import javax.swing.JTextPane;

import org.asmeta.animator.MyState;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;
import org.asmeta.runtime_simulator.AsmetaSservice;
import org.asmeta.runtime_simulator.IdNotFoundException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateClashException;

import org.asmeta.simulator.main.*;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.value.Value;

import asmeta.AsmCollection;
import asmeta.definitions.Invariant;
import asmeta.definitions.impl.MonitoredFunctionImpl;


/**
 * The Class  ContainerRT. 
 * It is a container for managing the execution of an ASM model at runtime.
 * It provides methods for instantiating, starting, running and stopping a model execution
 */
public class ContainerRT implements IModelExecution, IModelAdaptation {
    
	private int id; // returning the id of the simulator generated if everything goes welcl

	/** The ids. */
	private int ids; //the id for the method start to check if is full o not

	/** The asm S. */
	static AsmetaSservice asmS = AsmetaSservice.getInstance();

	/** The instance. */
	private static ContainerRT instance = null;

	/** The ms. */
	MyState ms;
	
	SimStatus ss = SimStatus.EMPTY;
	rollbackStatus rollbStatus = rollbackStatus.NOTROLLED;
	
	private static int stepRun = 0;

	private long startRun = 0L;
	private long endRun = 0L;
	private long duration = 0L;
	
	private RunOutput routTO=null;	//support variable for the timeout methods
	//private RunOutput routTR=null;  //support variable for the transaction methods



	/**
	 * Gets the single instance of ImpModelExecute.
	 *
	 * @return single instance of ImpModelExecute
	 */
	public static ContainerRT getInstance() {
		if (instance == null)
			instance = new ContainerRT();
		return instance;
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
			ss = SimStatus.PAUSE;
			System.out.println(sout.toString());

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
	
	public int restartExecution(String modelPath, State s) {
		StartOutput sout = null;
		try {
			id = asmS.restart(modelPath, s);
			ids = checkStartId(id);
			sout = new StartOutput(ids, "The id " + ids + " is successfully created");
			ss = SimStatus.PAUSE;
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
	


	@Override
	public RunOutput runStep(int id, Map<String, String> locationValue, String modelPath) {
		ss=SimStatus.RUNNING;
		rollbStatus = rollbackStatus.NOTROLLED;
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not intialized");
		try {
			rout = checkSafety(modelPath, locationValue);
			if (rout.equalsMessage(new RunOutput(Esit.UNSAFE, "rout not intialized"))) {
				startRun = System.nanoTime();
				ms = asmS.run(id, locationValue);
				endRun = System.nanoTime();
				duration = (endRun - startRun);
				rout = new RunOutput(Esit.SAFE, asmS.getCurrentState(id).getMonitoredValues(), ms);
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
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
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
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
				}

			} else if (e.getCause() instanceof InputMismatchException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ " for model "
						+ asmS.getSimulatorTable().get(id).getModelPath().substring(modelPath.lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid Input value");
				System.err.println(rout.toString());
				try {
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
				}
			}
		}
		ss = SimStatus.PAUSE;
		return rout; // can be use for Json

	}
	
	@Override
	public RunOutput runStep(int id) {
		return runStep(id, null, null);
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
		ss=SimStatus.PAUSE;
		return rout; // can be use for Json

	}*/
	
	public RunOutput runStepTimeout(int id,int timeout) {
		return runStepTimeout(id,null,null,timeout);
	}
	
	public RunOutput runStepTimeout(int id,Map<String, String> locationValue, String modelPath,int timeout) {
		Timer timer = new Timer(true);
	    routTO = new RunOutput(Esit.UNSAFE, "rout not intialized");
        TimerTask timeoutTask = new TimerTask() {
        	@Override  
            public void run() {  
            	 System.out.println("Timer task started at:"+new Date());
            	 routTO = runStep(id, locationValue, modelPath);
            	 routTO.setResult(true);
                 System.out.println("Timer task finished at:"+new Date());
            }; 
        };  
	    //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
	    timer.schedule(timeoutTask, 1);
	    System.out.println("TimerTask started");
	    //cancel after timeout if the task has not terminated
	    try {
	        Thread.sleep(timeout);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    /*if (! taskTerminated.getResult()) { 
	    	timer.cancel();
	    	System.out.println("TimerTask cancelled"+" -- flag TaskTerminated: "+taskTerminated.getResult());
	    }
	    else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+taskTerminated.getResult());*/
	    
	    
	    if (!routTO.getResult()) { 
	    	
	    	{
        		while (rollbStatus==rollbackStatus.ROLLINGBACK) {
        			try {
        				Thread.sleep(10);	//how often the program check for the rollback to finish before killing the thread
	        		} catch (InterruptedException e) {
	                    e.printStackTrace();}
        		}
        		timer.cancel();
    	    	routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
    	    	System.err.println(routTO.toString());
    			if (rollbStatus!=rollbackStatus.ROLLOK)
    			{
    				try {
    					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
    				} catch (NullPointerException e1) {
    					System.out.println("no previous state");
    				}/* catch (EmptyStackException e1) {
    					System.out.println("empty stack exception dal simulator");
    				}*/
    			}
    				
        	}
	    	
	    	/*timer.cancel();
	    	routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
			System.err.println(routTO.toString());
			/*try {
				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
			} catch (NullPointerException e1) {
				System.out.println("no previous state");
			}*/
	    }
	    else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+routTO.getResult());	
	        
	 	return routTO;
	}
	
//	public RunOutput runStepTimeout(int id,Map<String, String> locationValue, String modelPath,int timeout) {
//		
//		MyTimerTask timerTask = new MyTimerTask(id,0,null,null,TypeMyTimerTask.RUNSTEP);	 	
//	    Timer timer = new Timer(true);
//	    //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
//	    timer.schedule(timerTask, 1);
//	    System.out.println("TimerTask started");
//	    //cancel after timeout if the task has not terminated
//	    try {
//	        Thread.sleep(timeout);
//	    } catch (InterruptedException e) {
//	        e.printStackTrace();
//	    }
//	    /*if (! taskTerminated.getResult()) { 
//	    	timer.cancel();
//	    	System.out.println("TimerTask cancelled"+" -- flag TaskTerminated: "+taskTerminated.getResult());
//	    }
//	    else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+taskTerminated.getResult());*/
//	    if (timerTask.rout==null || !timerTask.rout.getResult()) { 
//	    	timer.cancel();
//	    	timerTask.rout = new RunOutput(Esit.UNSAFE, "Run timed out");
//			System.err.println(timerTask.rout.toString());
//			/*try {
//				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
//			} catch (NullPointerException e1) {
//				System.out.println("no previous state");
//			}*/
//	    }
//	    else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+timerTask.rout.getResult());	
//	        
//	 	return timerTask.rout;
//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see asmeta.safetyawaremodeleexecute.client.IModelExecute#stopExecution(int)
	 * stop of giusso remove a simulator(model) by an id
	 */
	// @Override
	public int stopExecution(int id) {
		try {
			System.out.println("the model " + asmS.getModelName(id) + " is successfully stop");
			asmS.stop(id);
			id = 1;
			ss=SimStatus.EMPTY;
		} catch (RuntimeException e) {
			if (e instanceof IdNotFoundException) {
				System.out.println("the " + id + " is not found");
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
		}
		System.out.printf("max number of simulators %d" + " successfully instantiated:\n", maxSimInstance);
		asmS.init(maxSimInstance);
		return maxSimInstance;

	}

	
	@Override
	public RunOutput runUntilEmpty(int id, Map<String, String> locationValue, String modelPath, int max) {
		ss = SimStatus.RUNNING;
		rollbStatus = rollbackStatus.NOTROLLED;
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not intialized");		
		try {
			rout = checkSafety(modelPath, locationValue);
			if (rout.equalsMessage(new RunOutput(Esit.UNSAFE, "rout not intialized"))) {
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
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));					
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
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
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
				}
			} else if (e.getCause() instanceof InputMismatchException) {
				System.err.println("No transition to step " + (asmS.getSimulatorTable().get(id).getContSim() + 1)
						+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
								.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "Invalid input value");
				System.err.println(rout.toString());
				try {
					rollbStatus = rollbackStatus.ROLLINGBACK;
					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
				} catch (NullPointerException e1) {
					System.out.println("no previous state");
				}finally {
					rollbStatus = rollbackStatus.ROLLOK;
				}

			}
		}
		ss=SimStatus.PAUSE;
		return rout;

	}
	
	@Override
	public RunOutput runUntilEmpty(int id, String modelPath) {
		return runUntilEmpty(id, null, modelPath, 0);
	}

	@Override
	public RunOutput runUntilEmpty(int id, Map<String, String> locationValue, String modelPath) {
		return runUntilEmpty(id, locationValue, modelPath, 0);
	}

	@Override
	public RunOutput runUntilEmpty(int id, int max) {
		return runUntilEmpty(id, null, null, max);
	}

	private static void printState(int step, RunOutput r1, long time, int id) {
		System.out.println(
				"[step:" + asmS.getSimulatorTable().get(id).getContSim() + " of model " + asmS.getModelName(id) + "]");
		System.out.println(r1.MytoString());
		System.out.println("time execution :" + time + " nT");
		System.out.println(
				"number of steps for the transition: " + asmS.getSimulatorTable().get(id).getSim().getCurrentStep());
		/*
		 * System.out.println("Maximum number of state before rollBackToState: " +
		 * asmS.getSimulatorTable().get(id).getSim().getMax() );
		 */

		System.out.println("                                 ");

		System.out.println("=====================");
	}

	private static void printRollback(int step, MyState state) {

		System.out.println("<Exception make return at previous step " + step + ">");

		if (state.controlledValues.size() != 0)
			System.out.println("< Controlled function >");

		for (Entry<Location, Value> entry : state.getControlledValues().entrySet()) {
			System.out.println("- " + entry.getKey().toString() + ": " + entry.getValue().toString());
		}
		System.out.println("=====================");

	}


	/**
	 * Check safety: Verifica se il nome del input è corretto
	 *
	 * @param modelPath     the model path
	 * @param locationValue Change to CheckInputName Safety
	 * @return the array list
	 */
	public RunOutput checkSafety(String modelPath, Map<String, String> locationValue) {
		ArrayList<String> nomi = new ArrayList<String>(); // per salvare i nomi di quelli che sono monitorate
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not intialized");
		// AsmCollection asm = null;
		String name = "";
		try {
			File asmFile = new File(modelPath);
			if (!asmFile.exists()) {
				throw new AsmModelNotFoundException(modelPath);
			}

			AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
			// cerco di prendere la classe delle monitorate
			for (int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
				if (asm.getMain().getHeaderSection().getSignature().getFunction()
						.get(i) instanceof MonitoredFunctionImpl)
					nomi.add(asm.getMain().getHeaderSection().getSignature().getFunction().get(i).getName());

			}

	
			boolean found = false;
			for (String s : locationValue.keySet()) {
				for (int i = 0; i < nomi.size(); i++) {
					if (s.equals(nomi.get(i))) {
						found = true;
					}
				}
				if (!found) {
					name = s;
					throw new NameMistMatchException("Name <<" + s + ">> Not Found");
				} else
					found = true;

			}
		} catch (Exception e) {
			if (e instanceof NameMistMatchException) {
				//System.err.println("No transition to step " 
						//+ "  for model " + asmS.getSimulatorTable().get(id).getModelPath()
							//	.substring(asmS.getSimulatorTable().get(id).getModelPath().lastIndexOf("/") + 1));
				rout = new RunOutput(Esit.UNSAFE, "monitored name <<" + name + ">> not found");
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
	public int checkStartId(int idC) throws FullMapException {
		if (idC == -1) {
			idC = -5;
			throw new FullMapException("The map is full");
		}

		return idC;

	}
	
	
	public RunOutput runUntilEmptyTimeout(int id, int max, int timeout) {
	 	 
	 	return runUntilEmptyTimeout(id, null, null, max, timeout);
	}
	
//	public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, String modelPath, int max, int timeout) {
//		
//        MyTimerTask timerTask = new MyTimerTask(id, max, locationValue, modelPath, TypeMyTimerTask.RUNUNTILEMPTY);
//	 	
//        Timer timer = new Timer(true);
//        //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
//        timer.schedule(timerTask, 1);
//        System.out.println("TimerTask started");
//        //cancel after timeout if the task has not terminated
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
//        if (timerTask.rout==null || !timerTask.rout.getResult()) { 
//        	timer.cancel();
//        	timerTask.rout = new RunOutput(Esit.UNSAFE, "Run timed out");
//			System.err.println(timerTask.rout.toString());
//			/*try {
//				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
//			} catch (NullPointerException e1) {
//				System.out.println("no previous state");
//			}*/
//        }
//        else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+timerTask.rout.getResult());	
//	        
//	 	return timerTask.rout;
//	}
	
public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, String modelPath, int max, int timeout) {
		routTO = new RunOutput(Esit.UNSAFE, "rout not intialized");
        TimerTask timeoutTask = new TimerTask() {
        	@Override  
            public void run() {  
            	 System.out.println("Timer task started at:"+new Date());
            	 routTO = runUntilEmpty(id, locationValue, modelPath, max);
            	 routTO.setResult(true);
                 System.out.println("Timer task finished at:"+new Date());
            }; 
        };  
        
        Timer timer = new Timer(true);
        //running timer task as daemon thread (no start delay, no period) but with timeout (thanks to Thread.sleep(timeout))
        timer.schedule(timeoutTask, 1);
        System.out.println("TimerTask started");
        //cancel after timeout if the task has not terminated
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if (! taskTerminated.getResult()) { 
        	timer.cancel();
        	System.out.println("TimerTask cancelled"+" -- flag TaskTerminated: "+taskTerminated.getResult());
        }
        else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+taskTerminated.getResult());*/
        if (!routTO.getResult()) { 
        	
        	{
        		while (rollbStatus==rollbackStatus.ROLLINGBACK) {
        			try {
        				Thread.sleep(10);	//how often the program check for the rollback to finish before killing the thread
	        		} catch (InterruptedException e) {
	                    e.printStackTrace();}
        		}
        		timer.cancel();
        		routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
    			System.err.println(routTO.toString());
    			if (rollbStatus!=rollbackStatus.ROLLOK)
    			{
    				try {
    					printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollbackToState(id));
    				} catch (NullPointerException e1) {
    					System.out.println("no previous state");
    				}/* catch (EmptyStackException e1) {
    					System.out.println("empty stack exception dal simulator");
    				}*/
    			}
    				
        	}
        	
        	/*timer.cancel();
        	routTO = new RunOutput(Esit.UNSAFE, "Run timed out");
			System.err.println(routTO.toString());
			try {
				printRollback(asmS.getSimulatorTable().get(id).getContSim(), asmS.rollback(id));
			} catch (NullPointerException e1) {
				System.out.println("no previous state");
			}/* catch (EmptyStackException e1) {
				System.out.println("empty stack exception dal simulator");
			}*/
        }
        
        else System.out.println("TimerTask alive"+" -- flag TaskTerminated: "+routTO.getResult());	  
	 	return routTO;
	}
	

	public RunOutput runUntilEmptyTimeout(int id, String modelPath, int timeout) {
		return runUntilEmptyTimeout(id, null, modelPath, 0, timeout);
	}


	public RunOutput runUntilEmptyTimeout(int id, Map<String, String> locationValue, String modelPath, int timeout) {
		return runUntilEmptyTimeout(id, locationValue, modelPath, 0, timeout);
	}
	
	public RunOutput runStepTransaction(int id,Queue<Map<String, String>> locationValue, String modelPath) { 
		boolean unsafe = false;
		RunOutput routTR = new RunOutput(Esit.UNSAFE, "rout not intialized");
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not intialized");
		Map<String,String> element = new HashMap<String, String>();
		int statecount=0;
		while (!locationValue.isEmpty() && unsafe == false) {
			element=locationValue.poll();
			rout = runStep(id,element,modelPath);
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
		
	}
	public RunOutput runTransaction(int id,Queue<Map<String, String>> locationValue, String modelPath) { 
		boolean unsafe = false;
		RunOutput routTR = new RunOutput(Esit.UNSAFE, "rout not intialized");
		RunOutput rout = new RunOutput(Esit.UNSAFE, "rout not intialized");
		Map<String,String> element = new HashMap<String, String>();
		int statecount=0;
		while (!locationValue.isEmpty() && unsafe == false) {
			element=locationValue.poll();
			rout = runUntilEmpty(id,element,modelPath);
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
		
	}

	
	@Override
	public boolean addInvariant(int id, String invariant_to_add) {
		boolean success = false;
		String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
		State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
		BufferedReader reader;
        
		try {
			reader = Files.newBufferedReader(Paths.get(modelfile));
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"to_overwrite.asm"));
			String line = reader.readLine();
			
			Files.copy(Paths.get(modelfile), Paths.get(modelfile+"_old"), StandardCopyOption.REPLACE_EXISTING);

			while (line != null)
			{
				if (line.trim().startsWith("main rule")){
					writer.write("\t"+invariant_to_add+"\n");
				}
				writer.write(line+"\n");
				line = reader.readLine();
			}
			reader.close();	
			writer.close();
			
			File file = new File(modelfile);
			file.delete();
			File file2 = new File(modelfile+"to_overwrite.asm");
			success = file2.renameTo(file);
			
			if (!restartSim(id, state)) {
				Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
				restartExecution(modelfile, state);
			}
		} catch (IOException e) {
			System.out.println("Couldn't open or write the model file");
			success=false;
		}
		finally {
			try {
				Files.deleteIfExists(Paths.get(modelfile+"_old"));
			} catch (IOException e) {}
		}
			
	    return success;
	}



	@Override
	public List<String> viewListInvariant(int id) {
		String invar;
		List<String> invarList = new ArrayList<String>();
		boolean endinvariant = true;
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(asmS.getSimulatorTable().get(id).getModelPath()));
			String line = reader.readLine().trim();
			if (line==null)
				endinvariant=false;
			while (endinvariant) {
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
					invarList.add(invar);
					
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
		return invarList;
	}



	@Override
	public boolean updateInvariant(int id, String new_invariant, String old_invariant) {
		boolean success = false;
		boolean endinvariant=true;
		String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
		State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(Paths.get(modelfile));
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"to_overwrite.asm"));
			
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
					}while(endinvariant && !line.startsWith("invariant"));
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
			
			File file = new File(modelfile);
			file.delete();
			File file2 = new File(modelfile+"to_overwrite.asm");
			success = file2.renameTo(file);	
			if (!restartSim(id, state)) {
				Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
				restartExecution(modelfile,state);
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't open and read the given model");
		}
		
	return success;
	}
	

	@Override
	public boolean removeInvariant(int id, String remove_invariant) {
			boolean success = false;
			boolean endinvariant=true;
			String modelfile=asmS.getSimulatorTable().get(id).getModelPath();
			State state = asmS.getSimulatorTable().get(id).getSim().getCurrentState(); 
			BufferedReader reader;
			try {
				reader = Files.newBufferedReader(Paths.get(asmS.getSimulatorTable().get(id).getModelPath()));
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(modelfile+"to_overwrite.asm"));
				
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
				
				File file = new File(asmS.getSimulatorTable().get(id).getModelPath());
				file.delete();
				File file2 = new File(modelfile+"to_overwrite.asm");
				success = file2.renameTo(file);	
				if (!restartSim(id, state)) {
					Files.copy(Paths.get(modelfile+"_old"), Paths.get(modelfile), StandardCopyOption.REPLACE_EXISTING);
					restartExecution(modelfile,state);
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Couldn't open and read the given model");
			}
			
		return success;
	}
	
	private boolean restartSim(int id, State state) {
		boolean success = true;
		String modelPath = asmS.getSimulatorTable().get(id).getModelPath();
		if (state==null)
			state = asmS.getSimulatorTable().get(id).getSim().getCurrentState();
		stopExecution(id);
		int res = restartExecution(modelPath, state);
		if (res<0)
			success=false;
		return success;
	}
	
	
}


