package org.asmeta.atgt.generator.ui;

import java.util.Arrays;
import java.util.List;

import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

// it executes the generation of the tests
// it has two subclasses
//
public class AsmTSGeneratorLaunchShortcutRnd extends AsmTSGeneratorLaunchShortcut {

	@Override
	protected void generateTests(ILaunchConfiguration configuration, IPath filePath, IWorkbenchWindow window)
			throws Error, PartInitException {
		System.err.println("executing tests with random");
		
	}

}
