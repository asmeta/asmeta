package org.asmeta.nusmv.plugin.handlers;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.plugin.AsmetaSMVActivator;
import org.asmeta.nusmv.plugin.AsmetaSMVConsole;
import org.asmeta.nusmv.plugin.AsmetaSMVPreferencePage;
import org.asmeta.nusmv.util.Util;
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
abstract class AsmetaSMVHandler extends AbstractHandler {
	abstract void exec(AsmetaSMV asmetaSMV) throws Exception;

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String path = AsmetaUtility.getEditorPath(window);
		IConsoleView view = null;
		try {
			view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		AsmetaSMVConsole myConsole = findConsole(AsmetaSMVConsole.CONSOLE_NAME);
		view.display(myConsole);
		myConsole.activate();
		OutputStream out = myConsole.newOutputStream();
		PrintStream printOut = new PrintStream(out);
		System.setOut(printOut);
		//System.setErr(printOut);
		try {
			AsmetaSMV asmetaSMV = new AsmetaSMV(path);
			IPreferenceStore store = AsmetaSMVActivator.getDefault().getPreferenceStore();
			Util.keepNuSMVfile =  store.getBoolean(AsmetaSMVPreferencePage.P_KF);
			Util.setCheckConcrete(!store.getBoolean(AsmetaSMVPreferencePage.P_NC));
			Util.simplify = !store.getBoolean(AsmetaSMVPreferencePage.P_NS);
			Util.setPrintCounterExample(!store.getBoolean(AsmetaSMVPreferencePage.P_DCX));
			Util.
			String nusmvPath = store.getString(AsmetaSMVPreferencePage.P_NUSMV_PROGRAM);
			if(!nusmvPath.equals("") && new File(nusmvPath).exists()) {
				Util.setSolverPath(nusmvPath);
			}
			asmetaSMV.translation();
			asmetaSMV.createNuSMVfile();
			exec(asmetaSMV);
		}
		catch(Exception e) {
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + path + "\n\n" + e.getLocalizedMessage());
			message.setText("Parsing error");
			message.open();
		}
		return null;
	}

	private AsmetaSMVConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (name.equals(existing[i].getName())) {
				AsmetaSMVConsole console = (AsmetaSMVConsole) existing[i];
				console.clearConsole();
				return console;
			}
		}
		// no console found, so create a new one
		AsmetaSMVConsole myConsole = new AsmetaSMVConsole();
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
   }
}