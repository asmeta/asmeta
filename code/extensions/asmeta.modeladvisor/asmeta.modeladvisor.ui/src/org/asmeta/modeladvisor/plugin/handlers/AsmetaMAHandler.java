package org.asmeta.modeladvisor.plugin.handlers;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.asmeta.modeladvisor.AsmetaMA;
import org.asmeta.modeladvisor.plugin.AsmetaMAActivator;
import org.asmeta.modeladvisor.plugin.AsmetaMAConsole;
import org.asmeta.modeladvisor.plugin.AsmetaMAPreferencePage;
import org.asmeta.nusmv.AsmetaSMVOptions;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AsmetaMAHandler extends AbstractHandler {

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		URI uri;
		IEditorInput editorInput = window.getActivePage().getActiveEditor().getEditorInput();
		if (editorInput instanceof org.eclipse.ui.part.FileEditorInput) {
			uri = ((org.eclipse.ui.part.FileEditorInput) editorInput).getURI();
		} else if (editorInput instanceof org.eclipse.ui.ide.FileStoreEditorInput) {
			uri = ((org.eclipse.ui.ide.FileStoreEditorInput) editorInput).getURI();
		} else {
			throw new Error("Unknown editor " + editorInput.getClass().getSimpleName());
		}		
		Path path = Paths.get(uri);
		IConsoleView view = null;
		try {
			view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		AsmetaMAConsole myConsole = findConsole(AsmetaMAConsole.CONSOLE_NAME);
		view.display(myConsole);
		myConsole.activate();
		OutputStream out = myConsole.newOutputStream();
		PrintStream printOut = new PrintStream(out);
		System.setOut(printOut);
		// System.setErr(printOut);
		AsmetaMA asmetaMA = null;
		try {
			asmetaMA = AsmetaMA.buildAsmetaMA(path);
			IPreferenceStore store = AsmetaMAActivator.getDefault().getPreferenceStore();
			asmetaMA.setMetapropertiesExecution(store.getBoolean(AsmetaMAPreferencePage.MP1),
					store.getBoolean(AsmetaMAPreferencePage.MP2), store.getBoolean(AsmetaMAPreferencePage.MP3),
					store.getBoolean(AsmetaMAPreferencePage.MP4), store.getBoolean(AsmetaMAPreferencePage.MP5),
					store.getBoolean(AsmetaMAPreferencePage.MP6), store.getBoolean(AsmetaMAPreferencePage.MP7));
			AsmetaSMVOptions.setPrintNuSMVoutput(store.getBoolean(AsmetaMAPreferencePage.SHOW_SMV_OUTPUT));
		} catch (Exception e) {
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + path + "\n\n" + e.getLocalizedMessage());
			message.setText("Parsing error");
			message.open();
			return null;
		}
		// print a small message 
		printOut.println("model advising " + path);
		printOut.flush();
		try {
			asmetaMA.runCheck();
		} catch (Exception e) {
			e.printStackTrace();
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error during the execution of the model advisor");
			message.setText("Execution error");
			message.open();
		}
		return null;
	}

	private AsmetaMAConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (name.equals(existing[i].getName())) {
				AsmetaMAConsole console = (AsmetaMAConsole) existing[i];
				console.clearConsole();
				return console;
			}
		}
		// no console found, so create a new one
		AsmetaMAConsole myConsole = new AsmetaMAConsole();
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}