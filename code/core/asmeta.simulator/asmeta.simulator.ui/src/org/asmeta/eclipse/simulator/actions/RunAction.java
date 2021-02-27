package org.asmeta.eclipse.simulator.actions;

import java.util.concurrent.TimeUnit;

import org.asmeta.eclipse.AsmeeActivator;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.eclipse.editor.preferences.PreferenceConstants;
import org.asmeta.eclipse.simulator.jobs.RunJob;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public abstract class RunAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	
	/**
	 * The constructor.
	 */
	public RunAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Override
	public void run(IAction action) {
		IPreferenceStore store = setSimulationPrecerences();
		AsmetaUtility.setUpLogger(store);
		AsmeeConsole console = AsmetaUtility.findDefaultConsole();
		// get the current ASM (and save the file)
		IFile file = AsmetaUtility.getEditorIFile(window);
		console.writeMessage("simulating " + file.getName());
		// run job
		RunJob runjob = getJob(file);
		runjob.schedule();
/*		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
	 * 
	 * @return the preference store
	 */
	static public IPreferenceStore setSimulationPrecerences() {
		// get the preferences
		IPreferenceStore store = AsmeeActivator.getDefault().getPreferenceStore();
		RuleEvaluator.isShuffled = store.getBoolean(PreferenceConstants.P_SHUFFLE);
		//
		Environment.timeMngt = TimeMngt.valueOf(store.getString(PreferenceConstants.P_TIME_MNGT));
		String timeunit = store.getString(PreferenceConstants.P_TIME_UNIT);
		switch(timeunit) {
		case PreferenceConstants.AUTO : Environment.currentTimeUnit = null; break;
		case PreferenceConstants.MILLIS_STRING : Environment.currentTimeUnit = TimeUnit.MILLISECONDS; break;
		case PreferenceConstants.SECONDS_STRING: Environment.currentTimeUnit = TimeUnit.SECONDS; break;
		case PreferenceConstants.MINUTES_STRING: Environment.currentTimeUnit = TimeUnit.MINUTES; break;
		case PreferenceConstants.HOUR_STRING: Environment.currentTimeUnit = TimeUnit.HOURS; break;
			//MILLISECONDS"Environment.currentTimeUnit = TimeUnit.MILLISECONDS;
		default: throw new RuntimeException();
		}
		Environment.auto_increment_delta = store.getInt(PreferenceConstants.P_AUTO_DELTA);
		
		Simulator.checkInvariants = store.getBoolean(PreferenceConstants.P_CHECK_AXIOMS);
		RunJob.stopSimulationIfUpdateSetEmpty = store.getBoolean(PreferenceConstants.P_STOP_UPDATESET_EMPTY);
		RunJob.stopSimulationIfUpdateSetTrivial = store.getBoolean(PreferenceConstants.P_STOP_UPDATESET_TRIVIAL);
		return store;
	}

	/** get the job for this action
	 * @param file
	 * @return
	 */
	abstract protected RunJob getJob(IFile file);


	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@Override
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}