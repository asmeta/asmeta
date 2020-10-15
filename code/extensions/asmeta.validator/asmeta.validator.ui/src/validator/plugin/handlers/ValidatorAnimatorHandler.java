package validator.plugin.handlers;

import org.asmeta.animator.VisualizationSimulation;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;
import org.asmeta.xt.validator.AsmetaV;


/**
 * 
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ValidatorAnimatorHandler extends ValidatorHandler {

	@Override
	void execValidation(String path) throws Exception {
		// build the ASM for the avalla
		AsmetaFromAvallaBuilder builder = new AsmetaFromAvallaBuilder(path);
		builder.save();
		String asmPath = builder.getTempAsmPath();
		// call the animator on it
		VisualizationSimulation.showView(asmPath);
	}
}