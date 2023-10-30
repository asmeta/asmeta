package validator.plugin.handlers;

import org.asmeta.xt.validator.AsmetaV;

;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ValidatorCoverageHandler extends ValidatorHandler {

	public ValidatorCoverageHandler() {
		super("validating (w coverage)");
	}

	@Override
	void execValidation(String path) throws Exception {
		AsmetaV.execValidation(path, true);
	}
}