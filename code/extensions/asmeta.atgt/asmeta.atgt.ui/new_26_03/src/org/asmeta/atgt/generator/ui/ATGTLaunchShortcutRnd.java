package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.ui.ATGTLaunchConfigurationDelegate.GenerationMode;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

// it executes the generation of the tests
// it has two subclasses
//
public class ATGTLaunchShortcutRnd extends ATGTLaunchShortcut {

	@Override
	protected void generateTests(ILaunchConfiguration configuration, IWorkbenchWindow window)
			throws Error, PartInitException {
		ATGTActivator.log.info("executing tests with random (file: " + filePath + ")");
		ATGTLaunchConfigurationDelegate aTGTLaunchConfigurationDelegate = 
				new ATGTLaunchConfigurationDelegate(configuration,GenerationMode.RANDOM,filePath);
		aTGTLaunchConfigurationDelegate.generateTests();
	}

}
