package asmeta.animator.ui.handlers;

import java.io.OutputStream;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.asmeta.animator.VisualizationSimulation;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaUtility;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
		AsmeeConsole mc = AsmetaUtility.findDefaultConsole();
		try {
			String path = loadModel(event);
			mc.writeMessage("PATH2  "  + path);
			// get the options
			org.asmeta.eclipse.simulator.actions.RunAction.setSimulationPrecerences();
			mc.writeMessage("PROS " +path);			
			// run the animator
			VisualizationSimulation.showView(path);					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mc.writeMessage("PROSXXXX " +e.getLocalizedMessage());
		} catch (Throwable t) {
			mc.writeMessage("PROSYYYY " +t.getLocalizedMessage());
			mc.writeMessage(t.toString());
			mc.writeMessage(t.getStackTrace().toString());
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
		mc.writeMessage("PATH "  + path);
		// SET THE RIGHT OUTPUT
		if (outputfromSim == null) {
			OutputStream out = mc.newOutputStream();
			// redirect std output to this console
//					PrintStream printOut = new PrintStream(out);
//					System.setOut(printOut);
			outputfromSim = new WriterAppender(new PatternLayout("%m%n"), out);
//			Logger.getLogger("org.asmeta.simulator").addAppender(outputfromSim);
//			Simulator.logger.addAppender(outputfromSim);
//			Simulator.logger.setLevel(Level.ALL);
		}
		return path;
	}
}
