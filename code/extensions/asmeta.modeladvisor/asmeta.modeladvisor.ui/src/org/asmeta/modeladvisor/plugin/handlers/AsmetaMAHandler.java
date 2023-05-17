package org.asmeta.modeladvisor.plugin.handlers;

import java.io.OutputStream;
import java.io.PrintStream;

import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.modeladvisor.AsmetaMA;
import org.asmeta.modeladvisor.plugin.AsmetaMAActivator;
import org.asmeta.modeladvisor.plugin.AsmetaMAConsole;
import org.asmeta.modeladvisor.plugin.AsmetaMAPreferencePage;
import org.asmeta.nusmv.AsmetaSMVOptions;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
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
public class AsmetaMAHandler extends AsmetaActionHandler {

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get the current file
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IFile path = AsmetaUtility.getEditorIFile(window);
		// TODO use Asmee Console instead (only one console for everything in asmeta)
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
			// convert the IFile to path
			asmetaMA = AsmetaMA.buildAsmetaMA(path.getRawLocation().toFile().getPath());
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
	// TODO use find console do AsmetaUtility instead
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