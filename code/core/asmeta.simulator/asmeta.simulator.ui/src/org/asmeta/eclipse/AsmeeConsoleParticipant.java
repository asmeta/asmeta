package org.asmeta.eclipse;

import org.asmeta.eclipse.simulator.jobs.RunJob;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;

public class AsmeeConsoleParticipant implements IConsolePageParticipant {
	
	
	public boolean firstStop = false;
	public static RunJob runJob;

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
    	Action stop = new Action("STOP") { 
			@Override
			public void run() {
				if (runJob != null) {
					// second stop: stop the thread
					if (firstStop){
						firstStop = false;
						runJob.getThread().stop();
					} else {
						runJob.cancel();
						firstStop = true;
					}
				}
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
}