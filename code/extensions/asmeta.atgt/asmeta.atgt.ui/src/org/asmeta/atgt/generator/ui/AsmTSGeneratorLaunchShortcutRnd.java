package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.ui.AsmTSGeneratorLaunchConfiguration.GenerationMode;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

// it executes the generation of the tests
// it has two subclasses
//
public class AsmTSGeneratorLaunchShortcutRnd extends AsmTSGeneratorLaunchShortcut {

	@Override
	protected void generateTests(ILaunchConfiguration configuration, IWorkbenchWindow window)
			throws Error, PartInitException {
		ATGTActivator.log.info("executing tests with random (file: " + filePath + ")");
		AsmTSGeneratorLaunchConfiguration asmTSGeneratorLaunchConfiguration = 
				new AsmTSGeneratorLaunchConfiguration(configuration,GenerationMode.RANDOM,filePath);
		asmTSGeneratorLaunchConfiguration.generateTests(window);
	}

}
