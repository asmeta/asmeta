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

public class AsmTSGeneratorLaunchShortcut implements org.eclipse.debug.ui.ILaunchShortcut {

	@Override
	public void launch(ISelection selection, String mode) {
		System.out.println("AsmTSGeneratorLaunchShortcut:launch ISelection");
		ILaunchConfiguration configuration = findConfiguration(mode);
		// selection like a node in the tree
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelections = (TreeSelection) selection;
			Object select = treeSelections.getFirstElement();
			System.out.println(select.getClass().getName());
			if (select instanceof org.eclipse.core.internal.resources.File) {
				// add the path of the project
				// file path is only the last part
				// IProject prj = ((org.eclipse.core.internal.resources.File)
				// select).getProject();
				IPath filePath = ((File) select).getLocation();
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();
				try {
					new AsmTSGeneratorLaunchConfiguration(configuration).generateTests(filePath,window);
				} catch (PartInitException | Error e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		System.out.println("AsmTSGeneratorLaunchShortcut:launch IEditorPart");
		ILaunchConfiguration configuration = findConfiguration(mode);
		// Locates a launchable entity in the given active editor, and launches an
		// application in the specified mode. This launch configuration shortcut is
		// responsible for progress reporting as well as error handling, in the event
		// that a launchable entity cannot be found, or launching fails.
		IPath fullPath = AsmetaUtility.getEditorIFile(editor).getFullPath();
		try {
			new AsmTSGeneratorLaunchConfiguration(configuration).generateTests(fullPath,editor.getSite().getWorkbenchWindow());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getCurrent().getActiveShell(),
				labelProvider);
		dialog.setElements(configList.toArray());

		dialog.setMessage("&Select existing configuration:"/*
															 * LauncherMessages.
															 * JavaLaunchShortcut_2
															 */);
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}
	
	private ILaunchConfiguration findConfiguration(String mode) {
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
				workingCopy = type.newInstance(null, "new");
				//workingCopy.setAttribute(ATTR_FILEPATH, filepath);
				// workingCopy.setMappedResources(new IResource[] { file });
				configuration = workingCopy.doSave();
				//DebugUITools.launch(configuration, mode);
				//return;
			}
			workingCopy = configuration.getWorkingCopy();
			//workingCopy.setAttribute(ATTR_FILEPATH, filepath);
			configuration = workingCopy.doSave();
			//DebugUITools.launch(configuration, mode);
			return configuration;
		} catch (CoreException e) {
			return null;
		}
	}

}
