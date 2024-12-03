package org.asmeta.eclipse;

import org.asmeta.eclipse.simulator.jobs.RunJob;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;

// buttons to stop or pause the simulator (or other tools??)
public class AsmeeConsoleParticipant implements IConsolePageParticipant {
		
	// name of the action
	private static final String STOP = "STOP";
	
	private static boolean firstStop = false;

	// currently running jaob
	private static RunJob runJob;

	// in a console, only one job can run at a time
	//it can be set by this method
	// if there is another one, it should be stopped
	public static void setJobRunning(RunJob job) {
		// if there a job already running stop is
		// TWO STEPS are needed
		stopCurrentJob();
		stopCurrentJob();
		runJob = job;
	} 	

	@Override
	public void activated() {
		// TODO Auto-generated method stub
	}

	@Override
	public void deactivated() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(IPageBookViewPage page, IConsole console) {
		// stop the simulation
    	Action stop = new Action(STOP) { 
			@Override
			public void run() {
				stopCurrentJob();
			}
    	};
    	stop.setToolTipText("halt simulation");
    	stop.setImageDescriptor(AsmeeActivator.getImageDescriptor("terminate.gif"));
    	page.getSite().getActionBars().getToolBarManager().add(stop);
    	// pause the simulation
    	Action pause = new Action("PAUSE") { 
			@Override
			public void run() {
				if (runJob != null) {
					runJob.sleep();
				}
			}
    	};
    	pause.setToolTipText("pause simulation");
    	pause.setImageDescriptor(AsmeeActivator.getImageDescriptor("pause.gif"));
    	page.getSite().getActionBars().getToolBarManager().add(pause);
    	// nest step
    	/*Action next= new Action("NEXT") { 
			public void run() {
				if (runJob != null) runJob.sleep();
			}
    	};
    	pause.setToolTipText("pause simulation");
    	pause.setImageDescriptor(AsmeeActivator.getImageDescriptor("pause.gif"));
    	page.getSite().getActionBars().getToolBarManager().add(pause);*/
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	// stop current job if any
	// it does two things: stop the thread (if pressed the first time) and (2) it cancel the job
	// AG 05/2024 it is not clear why we need two steps cancelling the job
	private static void stopCurrentJob() {
		//System.err.println("STOPPING JOB");
		if (runJob != null) {
			//System.err.println("an existing JOB");
			// second stop: stop the thread
			if (firstStop){
				//System.err.println("FIRST STOP");
				firstStop = false;
				Thread thread = runJob.getThread();
				if (thread != null) {
					try {
						thread.stop();
					} catch (ThreadDeath td) {
						// ignore for now
						// TODO
						// fix as suggested in 
						// https://docs.oracle.com/javase/8/docs/technotes/guides/concurrency/threadPrimitiveDeprecation.html
					}
				}
			} else {
				//System.err.println("NOT FIRST STOP");
				runJob.cancel();
				firstStop = true;
			}
		}
	}
}