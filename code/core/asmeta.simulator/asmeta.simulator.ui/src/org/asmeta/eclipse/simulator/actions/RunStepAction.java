package org.asmeta.eclipse.simulator.actions;

import org.asmeta.eclipse.simulator.jobs.RunJob;
import org.asmeta.eclipse.simulator.jobs.RunStepJob;
import org.eclipse.core.resources.IFile;

public class RunStepAction extends RunAction {

	@Override
	protected RunJob getJob(IFile file) {
		return new RunStepJob(file);
	}
}