package org.asmeta.eclipse.simulator.actions;

import org.asmeta.eclipse.simulator.jobs.RunJob;
import org.asmeta.eclipse.simulator.jobs.RunRndJob;
import org.eclipse.core.resources.IFile;

public class RunRndAction extends RunAction {

	@Override
	protected RunJob getJob(IFile file) {
		return  new RunRndJob(file);
	}
}