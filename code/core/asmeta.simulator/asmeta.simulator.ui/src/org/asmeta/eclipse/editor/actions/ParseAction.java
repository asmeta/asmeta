package org.asmeta.eclipse.editor.actions;

import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.parser.ParserResultLogger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it. 
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ParseAction implements IWorkbenchWindowActionDelegate {
	
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public ParseAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Override
	public void run(IAction action) {
		// get the current document as file (IFile)
		IEditorPart part = window.getActivePage().getActiveEditor();
		// get the file
		IFile file = AsmetaUtility.getEditorIFile(window);
		try {
			// open the console !!
			IConsoleView view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
			AsmeeConsole mc = AsmetaUtility.findDefaultConsole();
			view.display(mc);
			// run the action
			// 1. create the runner
			Job parseJob = new ParseJob(file, mc);
			// 2. clear the markers
			clearMarkers(file);
			// run the parse
			parseJob.schedule();
			// wait for it
			parseJob.join();
			// if there is an error
			// DIC 10 AG : non funziona: reports always no error
			ParserResultLogger result = ParseJob.parseResult;
			if ((result != null) && result.lastParseError != null){
				mc.writeMessage("errors in parsing");
				updateMarkers(part,file,
						result.lastParseError.currentToken.beginLine,
						result.lastParseError.currentToken.beginColumn,
						result.lastParseError.currentToken.endColumn,
						result.lastParseError.currentToken.toString().replace('\n', ' '));
			} 
		} catch (PartInitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@Override
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	/**
	 * udpate markers on the document to be edited rawMsg: int line number where the
	 * problem is, message esempio 1,dioodw
	 */
	public void updateMarkers(IEditorPart part,IResource resource, int line, int startColumn, int endColumn, String msg) {
		// install a new marker
		try {
			if (line <= 0) {
				// no line number so trigger an external box !
				ErrorDialog.openError(part.getSite().getShell(), "Unlocalized compilation error",
						"Unlocalized compilation error", new Status(IStatus.ERROR, "org.asmeta.eclipse", 0, msg, null));
				return;
			}
			IMarker m = resource.createMarker(IMarker.PROBLEM);
			m.setAttribute(IMarker.LINE_NUMBER, line);
			m.setAttribute(IMarker.CHAR_START, startColumn);
			m.setAttribute(IMarker.CHAR_END, endColumn);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		} catch (CoreException e) {
		}
	}

	/**
	 * clear markers for a resource
	 * 
	 * @throws CoreException
	 */
	public void clearMarkers(IResource resource) throws CoreException {
		resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
	}

	
}