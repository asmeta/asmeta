package asmeta.animator.ui.handlers;

import java.io.OutputStream;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.animator.VisualizationSimulation;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.asmeta.simulator.main.Simulator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AnimatorHandler extends AbstractHandler {

	private WriterAppender outputfromSim;

	public AnimatorHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			String path = loadModel(event);
			// get the options
			org.asmeta.eclipse.simulator.actions.RunAction.setSimulationPrecerences();
			// run the animator
			VisualizationSimulation.showView(path);					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String loadModel(ExecutionEvent event) throws Exception {		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// get the file
		String path = AsmetaUtility.getEditorPath(window);
		// open the simulator console
		IConsoleView view = (IConsoleView) window.getActivePage().showView(IConsoleConstants.ID_CONSOLE_VIEW);
		AsmeeConsole mc = AsmetaUtility.findDefaultConsole();
		view.display(mc);
		// SET THE RIGHT OUTPUT
		if (outputfromSim == null) {
			OutputStream out = mc.newOutputStream();
			// redirect std output to this console
//					PrintStream printOut = new PrintStream(out);
//					System.setOut(printOut);
			outputfromSim = new WriterAppender(new PatternLayout("%m%n"), out);
//			Logger.getLogger("org.asmeta.simulator").addAppender(outputfromSim);
			Logger simulatorLogger = Logger.getLogger(Simulator.class);
			if (!simulatorLogger.getAllAppenders().hasMoreElements())
				simulatorLogger.addAppender(outputfromSim);
			Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		}
		return path;
	}
}
