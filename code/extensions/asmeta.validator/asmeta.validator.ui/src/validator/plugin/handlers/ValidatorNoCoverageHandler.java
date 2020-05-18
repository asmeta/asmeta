package validator.plugin.handlers;

import org.asmeta.xt.validator.AsmetaV;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ValidatorNoCoverageHandler extends ValidatorHandler {

	@Override
	void execValidation(String path) throws Exception {
		AsmetaV.execValidation(path, false);
	}
}