package org.asmeta.atgt.generator.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;

public abstract class ATGTLaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	public static final String ATTR_FILE_PATH = "atgt.filepath";
	public static final String GENERATION_MODE = "atgt.generationMode";


    protected void setAsmetaFile(ILaunchConfigurationWorkingCopy configuration) {
		IFile currentFileFromWorkbench = ATGTUtils.getCurrentFileFromWorkbench();
		ATGTActivator.log.debug("apply - setting file " + currentFileFromWorkbench);
		// store the path as string
		configuration.setAttribute(ATTR_FILE_PATH, currentFileFromWorkbench.getLocation().toString());
	}

}
