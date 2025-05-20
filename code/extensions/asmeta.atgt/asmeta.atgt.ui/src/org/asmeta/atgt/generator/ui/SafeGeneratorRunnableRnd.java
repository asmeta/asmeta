package org.asmeta.atgt.generator.ui;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import atgt.coverage.AsmTestSuite;

public class SafeGeneratorRunnableRnd  extends  SafeGeneratorRunnable{

	public SafeGeneratorRunnableRnd(AsmTSGeneratorLaunchConfiguration config, IWorkbenchWindow window)
			throws PartInitException {
		super("Generation of the test suite with random simulation" , config, window);
	}

	@Override
	protected AsmTestSuite generateTestSuite() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void savetoavalla(AsmTestSuite result) {
		// TODO Auto-generated method stub
		
	}

}
