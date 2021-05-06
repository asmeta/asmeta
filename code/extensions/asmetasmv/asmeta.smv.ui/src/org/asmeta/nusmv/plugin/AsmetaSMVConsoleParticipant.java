package org.asmeta.nusmv.plugin;

import org.asmeta.nusmv.AsmetaSMV;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;

public class AsmetaSMVConsoleParticipant implements IConsolePageParticipant {

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
				if(AsmetaSMV.proc != null) {
					AsmetaSMV.proc.destroy();
				}
			}
    	};
    	stop.setToolTipText("Stop model checking");
    	stop.setImageDescriptor(AsmetaSMVActivator.getImageDescriptor("icons/terminate.gif"));
    	page.getSite().getActionBars().getToolBarManager().add(stop);
	}

	@Override
	public Object getAdapter(Class adapter) {
		throw new RuntimeException("not implemented");
	}
}