package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.ui.ATGTLaunchConfigurationDelegate.GenerationMode;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

//
// it executes the generation of the tests suing the model checker
//
public class ATGTLaunchShortcutMC extends ATGTLaunchShortcut{

	@Override
	protected void generateTests(ILaunchConfiguration configuration, IPath filePath, IWorkbenchWindow window)
			throws Error, PartInitException {
		ATGTActivator.log.debug("executing tests with model checker");
		ATGTLaunchConfigurationDelegate aTGTLaunchConfigurationDelegate = new ATGTLaunchConfigurationDelegate(configuration,GenerationMode.MODEL_CHECKER);
		aTGTLaunchConfigurationDelegate.generateTests(filePath,window);
	}

}
