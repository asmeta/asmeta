package extgt.spin.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.generator.ExternalToolTGen;
import tgtlib.generator.StringAndTPMCInput;
import tgtlib.generator.TrailNotFoundException;
import tgtlib.util.SimpleCmdExecutor;

public abstract class SpinTestGenerator<Q extends TestPredicate<T,?>, T extends tgtlib.definitions.TestSequence<Q>>
		extends ExternalToolTGen<Q, T, StringAndTPMCInput<Q>> {

	/** build a new Spin test Generator*/
	protected SpinTestGenerator(TestSequenceFactory<T, Q> q) {
		super(q);
	}

	/** Logger for this class. */
	private static final Logger logger = Logger.getLogger(SpinTestGenerator.class);

	@Override
	public void initResources() {
		super.initResources();
	}
	
	/**
	 * TODO move to spin 
	 * runs the step 3 and 4 of spin <BR>
	 * STEP 3: run executable produced by compilation of promela code spin <BR>
	 * STEP 4: analyses the results: run spin -t (if the trail has been found to
	 * get the result of the execution of spin -t). <BR>
	 * NOTE: the name of the trial is not enough to discover by the name of the
	 * exec: adding "trail" is not enough it can be discovered by the name of
	 * the spec by adding "trail" spin program is necessary to run spin -t
	 * 
	 * @param runCmds
	 *            the run cmds: the exec name + options, like -i .... (it can contain also the time command)
	 * @param spinProgram
	 *            the spin program
	 * @param specFileName
	 *            the name of the file containing the PROMELA spec - with the
	 *            directory
	 * @param cmd TODO
	 * @return the spin execution result
	 */
	public static SpinExecutionResult runSpin(List<String> runCmds,
			String spinProgram, String specFileName, SimpleCmdExecutor cmd) {
		// 3 running the code
		SpinExecutionResult step3Result;
		try {
			logger.debug("before running command " + runCmds);
			cmd.runCommand(tempDir, true, false, runCmds);
			logger.debug("after running command " + runCmds);
			File output = cmd.getOutput();
			if (output == null) return null;
			InputStream step3out = new FileInputStream(output);
			// testgen.fireTestConditionStepCompleted(tc, execname + " " +
			// runOpt + " completed");
			step3Result = SpinExecutionResult.analyzeExecution(step3out);
			// set the time
			step3Result.timeElapsed = cmd.getElapsedTime();
			// if the assertion is not violated, this is the result
			// do not close, so one can still read
			// it must be closed somewhere else, however !!!
			if (step3Result.getResult() != SpinExecutionResult.ResultKind.ASSERTION_VIOLATED)
				return step3Result;
			else
				step3out.close();
		} catch (Exception t) {
			logger.error(t);
			return null;
		}
		// 4 simulating
		// assertion has been found (by the execution phase 3)
		try {
			// check if trail exists
		
			String trailFileName = specFileName + ".trail";

			// NOTE THAT THE trail may be generated without the full path in the
			// version 4.3 of spin
			if (new File(tempDir, trailFileName).exists()) {
				// try the -r - S version
				replayFromPan(runCmds.get(0));				
				//replayTrailMinusT(spinProgram, specFileName);				
				File output = SimpleCmdExecutor.CMD.getOutput();
				SpinExecutionResult result = new SpinExecutionResult(
						step3Result, new FileInputStream(output));
				return result;
			} else {
				throw new TrailNotFoundException(tempDir, trailFileName);
			}
		} catch (Exception t) {
			logger.error(t);
			return null;
		}
	}

	/** instead using the spin -t use the replay option of pan
	 * @param execProgram 
	 * 
	 */
	private static void replayFromPan(String execProgram) {
		String[] cmds = {execProgram,"-r","-S"};
		SimpleCmdExecutor.CMD.runCommand(tempDir, true, false, cmds);
	}

	/** original version that call spin -t 
	 * 
	 * @param spinProgram
	 * @param specFileName
	 */
	private static void replayTrailMinusT(String spinProgram, String specFileName) {
		// build the strings
		// spin -t xxx
		ArrayList<String> commands = new ArrayList<String>();
		commands.add(spinProgram);
		commands.add("-t");
		commands.add(specFileName);
		SimpleCmdExecutor.CMD.runCommand(tempDir, true, false, commands
				.toArray(new String[commands.size()]));
	}
}
