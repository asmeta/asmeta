package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.ui.ATGTLaunchConfigurationDelegate.GenerationMode;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

//
// it executes the generation of the tests suing the model checker 
//
public class ATGTLaunchShortcutMC extends ATGTLaunchShortcut{
	
	protected void generateTests(ILaunchConfiguration configuration, IWorkbenchWindow window)
			throws Error, PartInitException {
		ATGTLaunchConfigurationDelegate aTGTLaunchConfigurationDelegate = 
				new ATGTLaunchConfigurationDelegate(configuration,GenerationMode.MODEL_CHECKER,filePath);
		aTGTLaunchConfigurationDelegate.generateTests();
	}
}
