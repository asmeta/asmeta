package org.asmeta.eclipse.simulator.jobs;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Appender;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.eclipse.AsmeeConsoleParticipant;
import org.asmeta.eclipse.editor.actions.ParseJob;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.LocationSet;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateClashException;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.AsmModelNotFoundException;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.readers.MonFuncReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.IOConsole;

public abstract class RunJob extends Job {
	protected IFile asmFile;
	protected boolean random;
	protected State previousState;//needed for run until trivial
	static Appender outputfromSim;
	public static boolean stopSimulationIfUpdateSetEmpty;
	public static boolean stopSimulationIfUpdateSetTrivial;

	static {
		Simulator.showUpdateSet = true;
	}

	public RunJob(IFile asmFile, boolean random) {
		super("run asm machine " + asmFile.getName());
		this.asmFile = asmFile;
		this.random = random;
	}

	@Override
	public final IStatus run(IProgressMonitor monitor) {
		// GET THE CONSOLE
		IOConsole mc = org.asmeta.eclipse.AsmetaUtility.findDefaultConsole();
		mc.activate();
		
		//PA: 2016/05/12
		if(AsmeeConsoleParticipant.runJob != null) {
			/*Thread thread = AsmeeConsoleParticipant.runJob.getThread();
			if(thread != null) {
				thread.stop();
			}*/
			AsmeeConsoleParticipant.runJob.cancel();
		}

		// set the progress runJob
		AsmeeConsoleParticipant.runJob = this;
		OutputStream out = mc.newOutputStream();
		PrintStream printOut = new PrintStream(out);

		// run the parse job
		ParseJob parseJob = new ParseJob(asmFile, mc);
		parseJob.schedule();
		// wait for parsing to finish
		try {
			parseJob.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		}
		if (!(parseJob.getResult().isOK()))
			return Status.CANCEL_STATUS;
	
		if (parseJob.getAsm() == null) return Status.CANCEL_STATUS;
		// parse OK
		printOut.println(getRunMessage() + " " + asmFile.getName());
		Simulator asmSimulator;
		try {
			// build the environment
			InputStream is = mc.getInputStream();
			MonFuncReader ui = getUI(is, printOut);
			Environment env = new Environment(ui);
			String specName = asmFile.getName().substring(0, asmFile.getName().length() - 4);
			asmSimulator = new Simulator(specName, parseJob.getAsm(), env);
			// SET THE RIGHT OUTPUT
			if (outputfromSim == null) {
				outputfromSim = new WriterAppender(new PatternLayout("%m%n"), out);
				//new WriterAppender(new XmlToTextLayout(), out)
				// TO GET THE LOGGER REDIRECTED
				//Logger.getRootLogger().addAppender(outputfromSim);
				//Logger.getLogger("org.asmeta.simulator").setAdditivity(false);
				Simulator.logger.addAppender(outputfromSim);
			}

			LocationSet currentState = asmSimulator.getCurrentState();
			printOut.println("INITIAL STATE:" + currentState.toString());
			try {
				//TODO
				//FINDBUGS: This instruction assigns a value to a local variable,
				//but the value is not read or used in any subsequent instruction.
				//Often, this indicates an error, because the value computed is never used.
				currentState = runASM(monitor, printOut, asmSimulator, currentState);
			} catch (UpdateClashException uce) {
				printOut.println("INCONSISTENT UPDATE FOUND !!! : location "
						+ uce.loc);
				printOut.println(" updated to " + uce.c1 + " != " + uce.c2);
			} catch (InvalidInvariantException iae) {
				String ax = iae.getInvariant().getName();
				printOut.println("INVARIANT " + ((ax == null) ? "" : ax)
						+ " VIOLATED !!! with UpdateSet " + iae.us);
			}
			printOut.print("FINAL STATE: " + asmSimulator.getCurrentState().toString() + "\n");
			printOut.println("run terminated");
			return Status.OK_STATUS;
		} catch (AsmModelNotFoundException e1) {
			printOut.println(asmFile.getName()
					+ " does not contain a valid model");
		} catch (Exception e1) {
			e1.printStackTrace(printOut);
		}
		return Status.CANCEL_STATUS;
	}

	protected abstract String getRunMessage();

	/**
	 * @param monitor
	 * @param printOut
	 * @param asmSimulator
	 * @param currentState
	 * @return
	 */
	final LocationSet runASM(IProgressMonitor monitor, PrintStream printOut,
			Simulator asmSimulator, LocationSet currentState) {
		UpdateSet us = null;
		while (!checkToStop(monitor, us)) {
			// not cancel, not pause -> running or perform next
			try {
				//savePreviousState(asmSimulator);//PA commented on 2018/03/22
				us = asmSimulator.run(1);
				previousState = asmSimulator.previousState; //PA 2018/03/22
			} catch (InvalidInvariantException iae) {
				printOut.println("INVARIANT violations");
				monitor.setCanceled(true);
			} catch (java.lang.RuntimeException rte) {
				if (rte.getCause() instanceof ThreadDeath) {
					printOut.println("run aborted");
					break;
				} else {
					throw rte;
				}
			}
			//printOut.print("CURRENT STATE: "
			//		+ asmSimulator.getCurrentState().toString() + "\n");
		}
		return currentState;
	}

	/**
	 * Needed for "run until trivial".
	 * @param asmSimulator
	 */
	protected void savePreviousState(Simulator asmSimulator) {
		if(stopSimulationIfUpdateSetTrivial) {
			previousState = new State(asmSimulator.getCurrentState());
		}
	}

	/**
	 * Check if the job must be stopped.
	 * 
	 * @param monitor
	 *            the monitor
	 * @param us
	 *            the update set (initially is null)
	 * 
	 * @return true, if check to stop
	 */
	protected boolean checkToStop(IProgressMonitor monitor, UpdateSet us) {
		return monitor.isCanceled() ||
			(stopSimulationIfUpdateSetEmpty && us != null && us.isEmpty()) ||
			(stopSimulationIfUpdateSetTrivial && us != null && us.isTrivial(previousState));
	}

	protected abstract MonFuncReader getUI(InputStream is, PrintStream printOut);
}