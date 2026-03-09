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
abstract public class AsmTSGeneratorLaunchShortcut implements org.eclipse.debug.ui.ILaunchShortcut, org.eclipse.debug.ui.ILaunchShortcut2 {

	private static final String NEW = "New ATGT configuration";
	protected IPath filePath;

	
	// this is call when an item in the project tree is selected
	@Override
	public void launch(ISelection selection, String mode) {
		// mode is always run (decided in the plugin definition)
		// the mode if random or model checker is a field
		ATGTActivator.log.debug("AsmTSGeneratorLaunchShortcut:launch ISelection - mode:" + mode);
		ILaunchConfiguration configuration = findConfiguration();
		// selection like a node in the tree
		try {
		setFilePath(selection);
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

	private void setFilePath(ISelection selection) {
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelections = (TreeSelection) selection;
			Object select = treeSelections.getFirstElement();
			// System.out.println(select.getClass().getName());
			// for now it works only with single files
			if (select instanceof org.eclipse.core.internal.resources.File) {
				// add the path of the project
				// file path is only the last part
				// IProject prj = ((org.eclipse.core.internal.resources.File)
				// select).getProject();
				this.filePath = ((File) select).getLocation();
				ATGTActivator.log.debug("setting the path as " + filePath);
				return;
			}
		}
		ATGTActivator.log.error("not a tree selection");
	}

	// this is call when an item in the editor is selected - no sure if it can never happen (it would need a button)
	@Override
	public void launch(IEditorPart editor, String mode) {
		// mode is always run
		ATGTActivator.log.debug("AsmTSGeneratorLaunchShortcut:launch IEditorPart - mode:" + mode);
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
		ATGTActivator.log.debug("AsmTSGeneratorLaunchShortcut:chooseConfiguration");
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
		ATGTActivator.log.debug("AsmTSGeneratorLaunchShortcut:findConfiguration");
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
			//workingCopy.setAttribute(ATTR_FILEPATH, file.getFullPath().toString());
			return configuration;
		} catch (CoreException e) {
			return null;
		}
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		setFilePath(selection);
		// not clear what to return
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		throw new RuntimeException("not implemented - nver used");
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		throw new RuntimeException("not implemented - nver used");
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		throw new RuntimeException("not implemented - nver used");
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
