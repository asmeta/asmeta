package org.asmeta.eclipse.simulator.actions;

import org.asmeta.eclipse.AsmeeActivator;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.eclipse.editor.preferences.PreferenceConstants;
import org.asmeta.eclipse.simulator.jobs.RunJob;
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
		// get the preferences
		IPreferenceStore store = AsmeeActivator.getDefault().getPreferenceStore();
		RuleEvaluator.isShuffled = store.getBoolean(PreferenceConstants.P_SHUFFLE);
		TimeMngt.use_java_time = store.getBoolean(PreferenceConstants.P_USE_SYSTEMTIME);
		Simulator.checkInvariants = store.getBoolean(PreferenceConstants.P_CHECK_AXIOMS);
		RunJob.stopSimulationIfUpdateSetEmpty = store.getBoolean(PreferenceConstants.P_STOP_UPDATESET_EMPTY);
		RunJob.stopSimulationIfUpdateSetTrivial = store.getBoolean(PreferenceConstants.P_STOP_UPDATESET_TRIVIAL);

		AsmetaUtility.setUpLogger(store);

		System.out.println("running " + action.getDescription());
		
		// get the current document as file (IFile)
		IEditorPart part = window.getActivePage().getActiveEditor();
		// save the file
		part.doSave(new NullProgressMonitor());
		IEditorInput input = part.getEditorInput();
		
		IFile file = ((IFileEditorInput) input).getFile();
		System.out.println("running " + file.getName());
		
		RunJob runjob = getJob(file);

		runjob.schedule();
/*		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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