package validator.plugin.handlers;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.eclipse.AsmetaConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;
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

	protected ValidatorHandler(String action) {
		super(AsmetaVConsole.class, action,true);
	}


	abstract void execValidation(String path) throws Exception;

	
	protected void executeAction(File path) throws Exception{
		execValidation(path.getAbsolutePath());
	}

	protected void setUpLoggers() {
		// set the simulator preferences
		org.asmeta.eclipse.simulator.actions.RunAction.setSimulationPrecerences();
		Logger.getLogger(AsmetaFromAvallaBuilder.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.INFO);
		Simulator.logger.setLevel(Level.INFO);		
	}

}