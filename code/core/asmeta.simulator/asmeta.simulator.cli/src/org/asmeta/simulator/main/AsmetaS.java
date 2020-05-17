package org.asmeta.simulator.main;

import java.io.File;
import java.io.FileNotFoundException;

import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateClashException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

import asmeta.cli.ASMFileFilter;
import asmeta.cli.AsmetaCLI;

/** main class to build the program AsmetaS */

public class AsmetaS extends AsmetaCLI {

	@Option(name = "-shuffle", usage = "random choose")
	private boolean shuffle;

	@Option(name = "-rnd", usage = "random simulation")
	private boolean rndSimulation;

	@Option(name = "-env", usage = "the environment file ")
	private String envPath;

	@Option(name = "-ne", usage = "run until the update set is empty")
	private boolean runUntilEmpty;

	@Option(name = "-nt", usage = "run until the update set is trivial")
	private boolean runUntilTrivial;

	// @Option(name = "-cov", usage = "run until update set is empty")
	// private boolean coverage;

	@Option(name = "-n", usage = "run for n steps (100 default)")
	private int nSteps = 100;

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
		AsmetaS sim = new AsmetaS();
		sim.run(args);
	}

	/**
	 * Stampa un messaggio d'errore e termina il programma.
	 * 
	 * @param msg
	 *            messaggio d'errore
	 */
	static private void error(String msg) {
		// System.out.println(msg);
		// System.exit(-1);
		throw new RuntimeException(msg);
	}

	@Override
	protected void runWith(File asmFile) throws CmdLineException {
		ASMFileFilter filter = new ASMFileFilter();
		if (!filter.accept(asmFile)) {
			throw new CmdLineException("Error:  " + asmFile.toString() + " is not an asm file.");
		}
		try {
			Simulator sim = null;
			if (rndSimulation)
				sim = Simulator.createSimulatorRnd(asmFile.getPath());
			else
				sim = Simulator.createSimulator(asmFile.getPath(), envPath);
			// set shuffle
			if (shuffle)
				sim.setShuffleFlag(true);
			else
				sim.setShuffleFlag(false);
			// run
			if (runUntilEmpty) {
				sim.runUntilEmpty();
			} else if (runUntilTrivial) {
				sim.runUntilTrivial();
			} else {
				try {
					sim.run(nSteps);
				} catch (InvalidInvariantException iae) {
					// Simulator.logger.info("invariant (name "
					// + iae.violated.getName() + ") violated");
					// Simulator.logger.fatal("<UpdateSet>" + iae.us
					// + "</UpdateSet>");
				} catch (UpdateClashException uce) {
					Simulator.logger.fatal(uce.loc + " updated to " + uce.c1 + " and " + uce.c2);
				}
				System.out.println("Final state:\n" + sim.getCurrentState().getContrLocsState());
			}
		} catch (FileNotFoundException e) {
			error("Error: file not found " + e.getMessage());
		} catch (AsmModelNotFoundException e) {
			error("Error: model not found " + e.getMessage());
		} catch (MainRuleNotFoundException e) {
			error("Error: main rule not found " + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
