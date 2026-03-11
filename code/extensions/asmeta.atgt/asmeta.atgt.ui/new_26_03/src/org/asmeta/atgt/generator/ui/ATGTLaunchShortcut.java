package org.asmeta.atgt.generator.ui;

import java.util.Arrays;
import java.util.List;

import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.debug.ui.ILaunchShortcut2;
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
abstract public class ATGTLaunchShortcut implements ILaunchShortcut {

	private static final String NEW = "New ATGT configuration";
	protected IPath filePath;

	
	// this is call when an item in the project tree is selected
	@Override
	public void launch(ISelection selection, String mode) {
		// mode is always run (decided in the plugin definition)
		// the mode if random or model checker is a field
		ATGTActivator.log.debug("ATGTLaunchShortcut:launch ISelection - mode:" + mode);
		ILaunchConfiguration configuration = findConfiguration();
		// selection like a node in the tree
		try {
			filePath = ATGTUtils.toIFile(selection).getLocation();
			ATGTActivator.log.debug("generate tests for " + filePath);
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();
			ATGTActivator.log.debug("generate tests for " + filePath);
			generateTests(configuration, window);
		} catch (PartInitException | Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// this is call when an item in the editor is selected
	// not sure if it can never happen (it would need a button)
	@Override
	public void launch(IEditorPart editor, String mode) {
		// mode is always run
		ATGTActivator.log.debug("ATGTLaunchShortcut:launch IEditorPart - mode:" + mode);
		ILaunchConfiguration configuration = findConfiguration();
		// Locates a launchable entity in the given active editor, and launches an
		// application in the specified mode. This launch configuration shortcut is
		// responsible for progress reporting as well as error handling, in the event
		// that a launchable entity cannot be found, or launching fails.
		// IPath fullPath = AsmetaUtility.getEditorIFile(editor).getFullPath();
		try {
			ATGTActivator.log.debug("generate tests for " + filePath);
			generateTests(configuration, editor.getSite().getWorkbenchWindow());
		} catch (PartInitException | Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList) {
		ATGTActivator.log.debug("ATGTLaunchShortcut:chooseConfiguration");
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getCurrent().getActiveShell(),
				labelProvider);
		dialog.setElements(configList.toArray());

		dialog.setMessage("&Select existing configuration:"/*
															 * LauncherMessages. JavaLaunchShortcut_2
															 */);
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	private ILaunchConfiguration findConfiguration() {
		ATGTActivator.log.debug("ATGTLaunchShortcut:findConfiguration");
		ILaunchConfigurationWorkingCopy workingCopy;
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = launchManager.getLaunchConfigurationType("org.asmeta.atgt.asmSpec");
		try {
			ILaunchConfiguration[] configurations = launchManager.getLaunchConfigurations(type);
			int candidateCount = configurations.length;
			ILaunchConfiguration configuration = null;
			if (candidateCount == 1) {
				configuration = (ILaunchConfiguration) configurations[0];
			} else if (candidateCount > 1) {
				configuration = chooseConfiguration(Arrays.asList(configurations));
			} else if (candidateCount == 0) {
				workingCopy = type.newInstance(null, NEW);
				// workingCopy.setAttribute(ATTR_FILEPATH, this.filePath.toOSString());
				// workingCopy.setMappedResources(new IResource[] { file });
				configuration = workingCopy.doSave();
				// DebugUITools.launch(configuration, mode);
				// return;
			}
			workingCopy = configuration.getWorkingCopy();
			configuration = workingCopy.doSave();
			// DebugUITools.launch(configuration, mode);
			return configuration;
		} catch (CoreException e) {
			return null;
		}
	}

	/**
	 * it does not take the file path since it is a field
	 * @param configuration
	 * @param window
	 * @throws Error
	 * @throws PartInitException
	 */
	protected abstract void generateTests(ILaunchConfiguration configuration, IWorkbenchWindow window)
			throws Error, PartInitException;

}
