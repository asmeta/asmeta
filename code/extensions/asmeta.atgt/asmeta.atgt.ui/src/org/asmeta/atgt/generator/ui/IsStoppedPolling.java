package org.asmeta.atgt.generator.ui; 

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job; 

public class IsStoppedPolling extends Job { 
	private IProgressMonitor monitorToCheck; 
	private AsmTSGeneratorLaunchConfiguration tg; 
	private Boolean stopped; 

	public IsStoppedPolling(String name, IProgressMonitor monitorToCheck, Boolean stopped, AsmTSGeneratorLaunchConfiguration tg) { 
		super(name); 
		this.monitorToCheck = monitorToCheck; 
		this.tg = tg; 
		this.stopped = stopped; 
		// TODO Auto-generated constructor stub 
	} 

	@Override 
	protected IStatus run(IProgressMonitor monitor) { 
		// TODO Auto-generated method stub 
		while (!stopped) { 
			if (monitorToCheck.isCanceled()) { 
				try { 
					throw new InterruptedException(); 
				} catch (InterruptedException e) { 
					stopped = true; 
					//tg.stopGeneration(); 
				} 
				return Status.CANCEL_STATUS; 

			} 
		} 
		return Status.CANCEL_STATUS; 
	} 
} 