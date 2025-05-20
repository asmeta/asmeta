package org.asmeta.atgt.generator.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IWorkbenchWindow;

public class AsmTSGeneratorLaunchConfigurationRnd extends AsmTSGeneratorLaunchConfiguration {
	
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		System.out.println("*** RANDOM");
	}

	@Override
	protected Job getJob(IWorkbenchWindow window) {
		//return new SafeGeneratorRunnableRnd(AsmTSGeneratorLaunchConfigurationRnd.this, window);
		return null;
	}

}
