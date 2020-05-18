package validator.plugin.handlers;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
abstract class ValidatorHandler extends AbstractHandler {
	private static final String ASMETAV_CONSOLE = "AsmetaV";

	abstract void execValidation(String path) throws Exception;

	/**
	 * the command has been executed, so extract extract the needed information from
	 * the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbench workbench = PlatformUI.getWorkbench();
		// IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window == null ? null : window.getActivePage();
		IEditorPart editor = activePage == null ? null : activePage.getActiveEditor();
		// save automatically
		editor.doSave(new NullProgressMonitor());
		//
		IEditorInput input = editor == null ? null : editor.getEditorInput();
		IPath ipath = input instanceof FileEditorInput ? ((FileEditorInput) input).getPath() : null;
		if (ipath == null) {
			System.err.println(" path not found");
			throw new Error("Unknown editor " + input.getClass().getSimpleName());
		}
		// convert to path as string (do not use ospath)
		String path = ipath.toString(); 
		IConsoleView view = null;
		try {
			view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		AsmetaVConsole myConsole = findConsole(ASMETAV_CONSOLE);
		view.display(myConsole);
		myConsole.activate();
		OutputStream out = myConsole.newOutputStream();
		PrintStream printOut = new PrintStream(out);
		System.setOut(printOut);
		System.setErr(printOut);
		System.out.println("path " + path);
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