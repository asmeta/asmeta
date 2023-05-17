package validator.plugin.handlers;

import java.io.OutputStream;
import java.io.PrintStream;

import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
abstract class ValidatorHandler extends AsmetaActionHandler {

	private OutputStream out;

	abstract void execValidation(String path) throws Exception;

	/**
	 * the command has been executed, so extract extract the needed information from
	 * the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {		
		// get the path for the current editor file asmeta
		IWorkbench workbench = PlatformUI.getWorkbench();
		// IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();		
		String path = AsmetaUtility.getEditorPath(window);
		// set the simulator preferences
		org.asmeta.eclipse.simulator.actions.RunAction.setSimulationPrecerences();
		// get the console
		IConsoleView view = null;
		try {
			view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		AsmetaVConsole myConsole = findConsole(AsmetaVConsole.CONSOLE_NAME);
		view.display(myConsole);
		myConsole.activate();
		if (out == null) {
			out = myConsole.newOutputStream();
			PrintStream printOut = new PrintStream(out);
			System.setOut(printOut);
			System.setErr(printOut);
			System.out.println("path " + path);
		}
		try {
			execValidation(path);
		} catch (Exception e) {
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + path + "\n\n" + e.getLocalizedMessage());
			message.setText("Parsing error");
			message.open();
			e.printStackTrace();
		}
		return null;
	}

	/** find the console given its name
	 * 
	 * @param name
	 * @return
	 */
	private AsmetaVConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (AsmetaVConsole) existing[i];
		// no console found, so create a new one
		AsmetaVConsole myConsole = new AsmetaVConsole();
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}