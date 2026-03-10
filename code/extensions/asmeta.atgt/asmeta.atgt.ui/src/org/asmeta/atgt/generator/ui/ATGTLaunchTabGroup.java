package org.asmeta.atgt.generator.ui;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTabGroup;


public class ATGTLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		fTabs = new ILaunchConfigurationTab[] { 
				new ATGTLaunchConfigurationTabMC(), 
				new ATGTLaunchConfigurationTabRnd() }; 
		      // new CommonTab();
	}
}

abstract class ATGTLaunchConfigurationTab extends AbstractLaunchConfigurationTab{
	
	public static final String ATTR_FILE_PATH = "atgt.fileWorkspacePath";
	public static final String GENERATION_MODE = "atgt.generationMode";

	protected void setAsmetaFile(ILaunchConfigurationWorkingCopy configuration){
		IFile currentFileFromWorkbench = ATGTUtils.getCurrentFileFromWorkbench();
		ATGTActivator.log.debug("apply - setting file " + currentFileFromWorkbench);
		// store the path as string
		configuration.setAttribute(ATTR_FILE_PATH, currentFileFromWorkbench.getLocation().toString());		
	}

}

