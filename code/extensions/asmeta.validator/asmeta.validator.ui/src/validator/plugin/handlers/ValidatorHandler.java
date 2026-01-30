package validator.plugin.handlers;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.eclipse.AsmetaActionHandler;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;

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