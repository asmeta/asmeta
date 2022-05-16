package validator.plugin.handlers;

import java.io.File;

import org.asmeta.animator.VisualizationSimulation;
import org.asmeta.xt.validator.AsmetaFromAvallaBuilder;


/**
 * validate the scenario with the animator
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
		File asmPath = builder.getTempAsmPath();
		// call the animator on it
		VisualizationSimulation.showView(asmPath);
	}
}