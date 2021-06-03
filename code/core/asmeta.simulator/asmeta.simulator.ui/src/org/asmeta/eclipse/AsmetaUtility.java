package org.asmeta.eclipse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.asmeta.eclipse.editor.preferences.PreferenceConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;

public class AsmetaUtility {

	
	/** find the default console of this editor where to send messages
	 * if there is not ASMEE console yet, it creates a new one.
	 */
	public static AsmeeConsole findDefaultConsole() {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if ( existing[i] instanceof AsmeeConsole) { 
				return (AsmeeConsole) existing[i];
			}
		}
		// no console found, so create a new one
		// not that message console could be enough, but it is not writable !!!
		AsmeeConsole myConsole = new AsmeeConsole();
		conMan.addConsoles(new IConsole[] { myConsole });
		myConsole.activate();
		return myConsole;
	}

	public static void setUpLogger(IPreferenceStore store) {
		// for debugging
		// get the preference
		// angelo 27/2/21. do no loger use external file 
//		Boolean useexternalFile = store.getBoolean(PreferenceConstants.P_DEBUG_USE_EXTERNAL_FILE);
//		if (useexternalFile){
//			String fileConf = store.getString(PreferenceConstants.P_DEBUG_EXTERNAL_FILE);
//			//PropertyConfigurator.configureAndWatch("log4j.asmee.prop");
//			PropertyConfigurator.configureAndWatch(fileConf);
//		} else{		
			String parserDebuglevel = store.getString(PreferenceConstants.P_DEBUG_PARSER);
			// note that parser uses this logger
			Logger.getLogger("org.asmeta.parser").setLevel(Level.toLevel(parserDebuglevel));
			String simulatorDebuglevel = store.getString(PreferenceConstants.P_DEBUG_SIMULATOR);
			//Logger.getLogger(Simulator.class).setLevel(Level.toLevel(simulatorDebuglevel));
			Logger.getLogger("org.asmeta.simulator").setLevel(Level.toLevel(simulatorDebuglevel));
//		}
	}
	
	// this method is to find the path of the active window
	// it also saves the content of the window
	//  TODO merge with the followgin one
	public static String getEditorPath(IWorkbenchWindow window) throws Error {
		String path = null;
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		// save the file
		activeEditor.doSave(new NullProgressMonitor());
		// get the path
		IEditorInput editorInput = activeEditor.getEditorInput();
		if (editorInput instanceof org.eclipse.ui.part.FileEditorInput) {
			path = ((org.eclipse.ui.part.FileEditorInput) editorInput).getURI().getPath();
		} else if (editorInput instanceof org.eclipse.ui.ide.FileStoreEditorInput) {
			//Implements an IEditorInput instance appropriate for IFileStore elements that represent files that are not part of the current workspace.
			path = ((org.eclipse.ui.ide.FileStoreEditorInput) editorInput).getURI().getPath();
		} else {
			throw new Error("Unknown editor " + editorInput.getClass().getSimpleName());
		}
		return path;
	}
	
	/**
	 * Gets the editor as Ifile.
	 *
	 * @param window the window
	 * @return the editor I file
	 * @throws Error the error
	 */
	public static IFile  getEditorIFile(IWorkbenchWindow window) throws Error {
		IEditorPart activeEditor = window.getActivePage().getActiveEditor();
		return getEditorIFile(activeEditor);
	}

	/**
	 * @param activeEditor
	 * @return
	 * @throws Error
	 */
	public static IFile getEditorIFile(IEditorPart activeEditor) throws Error {
		// save the file
		activeEditor.doSave(new NullProgressMonitor());
		// get the path
		IEditorInput editorInput = activeEditor.getEditorInput();
		if (editorInput instanceof org.eclipse.ui.part.FileEditorInput) {
			return ((org.eclipse.ui.part.FileEditorInput) editorInput).getFile();
		} else {
			throw new Error("Unknown editor " + editorInput.getClass().getSimpleName());
		}
	}	
}
