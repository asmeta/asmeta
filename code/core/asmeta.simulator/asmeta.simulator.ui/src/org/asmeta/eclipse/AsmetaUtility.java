package org.asmeta.eclipse;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.eclipse.editor.preferences.PreferenceConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;

public class AsmetaUtility {

	/**
	 * find the default console of this editor where to send messages if there is
	 * not ASMEE console yet, it creates a new one.
	 */
	public static AsmeeConsole findDefaultConsole() {
		return findConsole(AsmeeConsole.class);
	}


	/**
	 *  find the console given its name and its class (subclass of asmeta console). 
	 * if it does not exist it will create one (with the empty constructor) and activate it
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @param clazz the clazz
	 * @return the t
	 */
	public static <T extends AsmetaConsole> T findConsole(Class<T> clazz) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (IConsole element : existing)
			if (element.getClass().equals(clazz))
				return (T) element;
		// no console found, so create a new one
		// not that message console could be enough, but it is not writable !!!
		T myConsole = null;
		try {
			myConsole = clazz.getDeclaredConstructor().newInstance();			
			conMan.addConsoles(new IConsole[] { myConsole });
			myConsole.activate();
			return myConsole;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e.getMessage());
		}
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
		// Logger.getLogger(Simulator.class).setLevel(Level.toLevel(simulatorDebuglevel));
		Logger.getLogger("org.asmeta.simulator").setLevel(Level.toLevel(simulatorDebuglevel));
//		}
	}

	// this method is to find the path of the active window
	// it also saves the content of the window
	// return null if an editro path is not found
	// TODO merge with the followgin one
	public static String getEditorPath(IWorkbenchWindow window) throws Error {
		String path = null;
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			IEditorPart activeEditor = page.getActiveEditor();
			if (activeEditor != null) {
				// save the file
				activeEditor.doSave(new NullProgressMonitor());
				// get the path
				IEditorInput editorInput = activeEditor.getEditorInput();
				if (editorInput instanceof org.eclipse.ui.part.FileEditorInput) {
					path = ((org.eclipse.ui.part.FileEditorInput) editorInput).getURI().getPath();
				} else if (editorInput instanceof org.eclipse.ui.ide.FileStoreEditorInput) {
					// Implements an IEditorInput instance appropriate for IFileStore elements that
					// represent files that are not part of the current workspace.
					path = ((org.eclipse.ui.ide.FileStoreEditorInput) editorInput).getURI().getPath();
				} else {
					throw new Error("Unknown editor " + editorInput.getClass().getSimpleName());
				}
				return path;
			}
			return null;
		}
		return null;
	}

	/**
	 * Gets the editor as Ifile.
	 *
	 * @param window the window
	 * @return the editor I file
	 * @throws Error the error
	 */
	public static IFile getEditorIFile(IWorkbenchWindow window) throws Error {
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
