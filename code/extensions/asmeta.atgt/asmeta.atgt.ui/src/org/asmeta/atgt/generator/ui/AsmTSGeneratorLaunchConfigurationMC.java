package org.asmeta.atgt.generator.ui;

import java.util.List;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public class AsmTSGeneratorLaunchConfigurationMC extends AsmTSGeneratorLaunchConfiguration {

	public List<CriteriaEnum> coverageCriteria;
	
	public AsmTSGeneratorLaunchConfigurationMC(){}
	
	public AsmTSGeneratorLaunchConfigurationMC(ILaunchConfiguration configuration) {
		super(configuration);
	}


	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		System.out.println("*** usign model checker");
	}
	
	
	protected SafeGeneratorRunnableMC getJob(IWorkbenchWindow window) throws PartInitException {
		return new SafeGeneratorRunnableMC(AsmTSGeneratorLaunchConfigurationMC.this, window);
	}

}
