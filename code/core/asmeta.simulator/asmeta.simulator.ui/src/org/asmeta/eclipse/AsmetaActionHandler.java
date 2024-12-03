package org.asmeta.eclipse;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.simulator.main.Simulator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * abstract class for action handlers for asmeta actions
 */
public abstract class AsmetaActionHandler extends AbstractHandler {	
	
	protected AsmetaConsole console;
	private Class<? extends AsmetaConsole> consoleClass;
	private String action;
	private boolean addStdOut;
	
	/**
	 * Instantiates a new asmeta action handler.
	 *
	 * @param consoleClass the console class
	 * @param action the action for messages - use gerund like validationg...
	 */
	protected AsmetaActionHandler(Class<? extends AsmetaConsole> consoleClass, String action, boolean addStdOut) {
		this.consoleClass = consoleClass;
		this.action = action;
		this.addStdOut = addStdOut;
	}
	

	@Override
	public final Object execute(ExecutionEvent event) throws ExecutionException {
		// get and open the console
		console = AsmetaUtility.findConsole(consoleClass);
		setOutput(console);
		// get the current file
		File path = null;
		try {
			IFile ifile = getModelPath(event, console);
			path = ifile.getRawLocation().makeAbsolute().toFile();
			assert path.exists();
		} catch (Exception e) {
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while parsing " + path + "\n\n" + e.getLocalizedMessage());
			message.open();
			//
			e.printStackTrace();
		}
		// should never happen
		if (path == null) {
			console.writeMessage("path not found");
			return null;
		}
//		mc.writeMessage("PATH " + path);
		// set off the messages so every plugin with activate what it wants
		Logger.getRootLogger().setLevel(Level.OFF);
		setUpLoggers();
		// execute action
		try {
			console.writeMessage( action + " on " + path);			
			executeAction(path);			
		} catch (Throwable t) {
			// show in the console
			console.writeMessage("Error " + t.getLocalizedMessage());
			// show as message
			Display d = Display.getDefault();
			Shell shell = new Shell(d);
			MessageBox message = new MessageBox(shell);
			message.setMessage("Error while executing action on " + path + "\n\n" + t.getLocalizedMessage());
			message.setText("Action error");
			message.open();
			t.printStackTrace();
		}
		return null;
	}

	protected abstract void executeAction(File path) throws Exception;


	/**
	 * get model path and activate console view 
	 *
	 * @param event   the event
	 * @param console the console
	 * @return the string
	 * @throws Exception the exception
	 */
	protected IFile getModelPath(ExecutionEvent event, AsmetaConsole console) throws Exception {
		/*
		 * 		// get the path for the current editor file asmeta
		IWorkbench workbench = PlatformUI.getWorkbench();
		// IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IWorkbenchWindow window = workbench == null ? null : workbench.getActiveWorkbenchWindow();		
		 */
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// get the file
		IFile path = AsmetaUtility.getEditorPath(window);
		// open the simulator console
		IConsoleView view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		view.display(console);
		return path;
	}

	// to distiinughis with the other appenders
	class AsmetaWriterAppender extends WriterAppender{
		public AsmetaWriterAppender(PatternLayout patternLayout, OutputStream out) {
			super(patternLayout,out);
		}
		
	}
	// the appender for this action
	private AsmetaWriterAppender consoleAppender;

	// set the right output to the logger
	private void setOutput(AsmetaConsole mc) {
		// SET THE RIGHT OUTPUT
		// find all the appenders
		Logger log = Logger.getRootLogger();
		// Delete all the appenders of the root logger except a single ConsoleAppender
		// org.eclipse.xtext.logging.EclipseLogAppender
		Enumeration<Appender> allAppenders = log.getAllAppenders();
		// if there is one ConsoleAppender
/*			if (Collections.list(allAppenders).stream().filter(x -> (x instanceof ConsoleAppender)).count() > 1) {
			// should a ConsoleAppendere
			java.util.Optional<?> app = Collections.list(allAppenders).stream()
					.filter(x -> (x instanceof ConsoleAppender)).findFirst();
			if (app != null) {
				ConsoleAppender newConsoleApp;
				if (!app.isEmpty()) {
					ConsoleAppender consoleApp = (ConsoleAppender) app.get();
					newConsoleApp = new ConsoleAppender(consoleApp.getLayout(), consoleApp.getTarget());					
				} else {
					PatternLayout l = new org.apache.log4j.PatternLayout();
					newConsoleApp = new ConsoleAppender(l);
				}
				log.removeAllAppenders();
				log.addAppender(newConsoleApp);
			}
		}*/
		OutputStream out = mc.newOutputStream();
		ArrayList<Appender> list = Collections.list(allAppenders);
		// if it is the first time, set up the appender
		if (consoleAppender == null) {
			consoleAppender = new AsmetaWriterAppender(new PatternLayout("%m%n"), out);
			consoleAppender.setName(this.action);
// 			only the simulator ... why?
//			Logger.getLogger("org.asmeta.simulator").addAppender(outputfromSim);
//			Simulator.logger.addAppender(outputfromSim);
		} else {			
			// if it contains already the appender ok
			// if (list.contains(consoleAppender)) return;
		}
		// remove all the console appenders to avoid cross messages among cosoles
		for(Appender a: list) {
			System.err.println("appender " +  a.getName() +  " " +a.getClass());
			// remove all the appenders that are asmeta appenders exepct this one
			if ((a instanceof AsmetaWriterAppender) && (a != consoleAppender)) {
				log.removeAppender(a);					
			}
		}
		// stronger version: remove all the appenders except this one
//		if (list.size() > 0) log.removeAllAppenders();
		// add this one
		if (!list.contains(consoleAppender)) log.addAppender(consoleAppender);
		// to be done every time? It seems so 
		// redirect std output to this console
		// alcuni plugin scrivono sullo standard output quindi va catturato
		if (addStdOut) {
			PrintStream printOut = new PrintStream(out);
			System.setOut(printOut);
//			System.setErr(printOut);
		}
	}

	// decide which logger must be activated.
	// this is done at every action, so if the user changes the preferences
	protected abstract void setUpLoggers();

}
