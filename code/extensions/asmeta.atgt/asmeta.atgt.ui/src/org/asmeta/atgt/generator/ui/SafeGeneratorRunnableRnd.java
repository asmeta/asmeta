package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.rndgenerator.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnableRnd  extends  SafeGeneratorRunnable{

	private int nSteps;
	private int nTests;

	public SafeGeneratorRunnableRnd(AsmTSGeneratorLaunchConfiguration config, IWorkbenchWindow window)
			throws PartInitException {
		super("Generation of the test suite with random simulation" , config, window);
		this.nSteps = config.nSteps;
		this.nTests = config.nTests;
	}

	@Override
	protected AsmTestSuite generateTestSuite() throws Exception {
		AsmCollection model = ASMParser.setUpReadAsm(config.asmetaSpecPath.toFile());
		mc.writeMessage("generating randomly " + nTests + " tests of length "+ nSteps);
		AsmTestGeneratorBySimulation tg = new AsmTestGeneratorBySimulation(model, nSteps, nTests);
		return tg.getTestSuite();
	}

	@Override
	protected void savetoavalla(AsmTestSuite result) {
		mc.writeMessage("saving to avalla");
		String stringForFile = "random";
		SaveResults.saveResults(result, config.asmetaSpecPath.toString(), config.formats, stringForFile, false);
	}

}
