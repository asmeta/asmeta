package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.ui.AsmTSGeneratorLaunchConfiguration.GenerationMode;
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
public class AsmTSGeneratorLaunchShortcutMC extends AsmTSGeneratorLaunchShortcut{
	
	protected void generateTests(ILaunchConfiguration configuration, IWorkbenchWindow window)
			throws Error, PartInitException {
		AsmTSGeneratorLaunchConfiguration asmTSGeneratorLaunchConfiguration = 
				new AsmTSGeneratorLaunchConfiguration(configuration,GenerationMode.MODEL_CHECKER,filePath);
		asmTSGeneratorLaunchConfiguration.generateTests(window);
	}
}
