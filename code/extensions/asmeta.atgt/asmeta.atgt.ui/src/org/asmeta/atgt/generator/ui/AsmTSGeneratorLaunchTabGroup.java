package org.asmeta.atgt.generator.ui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTabGroup;

public class AsmTSGeneratorLaunchTabGroup implements ILaunchConfigurationTabGroup {

	private ILaunchConfigurationTab[] tabs;

	public AsmTSGeneratorLaunchTabGroup() {
		tabs = new ILaunchConfigurationTab[]{ 
				new AsmTSGeneratorTab(), 
				new AsmTGSimulatorGenTab()};
				//new CommonTab();
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		dialog.setActiveTab(tabs[0]);
	}

	@Override
	public void dispose() {
	}

	@Override
	public ILaunchConfigurationTab[] getTabs() {
		return tabs;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		tabs[0].initializeFrom(configuration);
	}

	@Override
	public void launched(ILaunch launch) {
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		tabs[0].performApply(configuration);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		tabs[0].setDefaults(configuration);
	}
}
