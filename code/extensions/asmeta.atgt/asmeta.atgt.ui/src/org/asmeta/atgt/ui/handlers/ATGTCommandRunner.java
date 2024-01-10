package org.asmeta.atgt.ui.handlers;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationManager;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupExtension;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class ATGTCommandRunner extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String groupIdentifier = "org.asmeta.atgt.shortcut";
		Shell shell = window.getShell();
		final int[] result = new int[1];
		Runnable r = new Runnable() {
			/**
			 * @see java.lang.Runnable#run()
			 */
			public void run() {				
				ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager(); 

				try {
					ILaunchConfiguration [] configs = manager.getLaunchConfigurations();
					Arrays.asList(configs).forEach( c -> {
						
					System.out.println(c.getClass() + " " + c.toString());
					System.out.println(c.getName());
					try {
						System.out.println(c.getAttributes());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					);
					System.out.println(Arrays.toString(configs));
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				LaunchConfigurationManager launchConfigurationManager = DebugUIPlugin.getDefault()
						.getLaunchConfigurationManager();


				ILaunchGroup[] launchGroup = launchConfigurationManager.getLaunchGroups();
				Arrays.asList(launchGroup).forEach( c ->{ 
				System.out.println(c.getClass());
				System.out.println(c.getIdentifier());
				System.out.println(c.getLabel());
				System.out.println(c.getMode());
				});
				//launchConfigurationManager.getLaunchGroup();
				
				
				//LaunchConfigurationsDialog dialog = new LaunchConfigurationsDialog(shell, launchGroup[0]);
				//dialog.setOpenMode(LaunchConfigurationsDialog.LAUNCH_CONFIGURATION_DIALOG_OPEN_ON_SELECTION);
//                    dialog.setInitialSelection(selection);
//                    dialog.setInitialStatus(status);
				//result[0] = dialog.open();
			}
		};
		BusyIndicator.showWhile(DebugUIPlugin.getStandardDisplay(), r);
		return result[0];
	}
}
