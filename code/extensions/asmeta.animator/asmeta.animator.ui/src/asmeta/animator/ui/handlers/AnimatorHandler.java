package asmeta.animator.ui.handlers;

import java.io.File;

import org.apache.log4j.Level;
import org.asmeta.animator.VisualizationSimulation;
import org.asmeta.eclipse.AsmeeConsole;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.simulator.main.Simulator;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AnimatorHandler extends AsmetaActionHandler {

	public AnimatorHandler() {
		super(AsmeeConsole.class,"animating", false);
	}

	@Override
	protected void executeAction(File path) throws Exception {
		// run the animator
		VisualizationSimulation.showView(path);
	}

	protected void setUpLoggers() {
		// get the options
		org.asmeta.eclipse.simulator.actions.RunAction.setSimulationPrecerences();
		// Simulator.logger.setLevel(Level.ALL);
		Simulator.logger.setLevel(Level.INFO);
	}

}
