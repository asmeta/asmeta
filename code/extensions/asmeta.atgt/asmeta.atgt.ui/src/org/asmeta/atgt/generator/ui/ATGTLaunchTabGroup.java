package org.asmeta.atgt.generator.ui;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

//
// consider the use of AbstractLaunchConfigurationTabGroup instead
//
public class ATGTLaunchTabGroup extends AbstractLaunchConfigurationTabGroup{

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[]{
				new ATGTLaunchConfigurationTabMC(),
				new ATGTLaunchConfigurationTabRnd()};
				//new CommonTab();
		setTabs(tabs);
	}

}

/*
public class ATGTLaunchTabGroup implements ILaunchConfigurationTabGroup {

	private ILaunchConfigurationTab[] tabs;
	private ILaunchConfigurationDialog dialog;

	public ATGTLaunchTabGroup() {
		tabs = new ILaunchConfigurationTab[]{
				new ATGTLaunchConfigurationTabMC(),
				new ATGTLaunchConfigurationTabRnd()};
				//new CommonTab();

	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		dialog.setActiveTab(tabs[0]);
		this.dialog = dialog;
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
		tabs[1].initializeFrom(configuration);
	}

	@Override
	public void launched(ILaunch launch) {}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		dialog.getActiveTab().performApply(configuration);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		tabs[0].setDefaults(configuration);
		tabs[1].setDefaults(configuration);
	}
}*/
