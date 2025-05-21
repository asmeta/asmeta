package org.asmeta.eclipse.simulator.jobs;

import java.io.InputStream;
import java.io.PrintStream;

import org.asmeta.simulator.readers.InteractiveMFReader;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.util.Parser;
import org.eclipse.core.resources.IFile;
/** run step by step computation until is canceled */
public class RunStepJob extends RunJob {

	/**
	 * the file to run
	 */
	public RunStepJob(IFile asmFile) {
		super(asmFile, false);
	}

	@Override
	protected MonFuncReader getUI(InputStream is, PrintStream printOut) {
		InteractiveMFReader interactiveMFReader = new InteractiveMFReader(is, printOut);
		// like random simulation
		// check if undef are allowed in preferences
		interactiveMFReader.allowUndefValues = allowUndefValuesMonitored;
		return interactiveMFReader;
	}

	@Override
	protected String getRunMessage() {
		return "Running interactively " + (allowUndefValuesMonitored ? "undef values are allowed" : "");
	}
}