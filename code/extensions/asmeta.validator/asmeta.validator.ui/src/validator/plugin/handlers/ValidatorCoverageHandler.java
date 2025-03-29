package validator.plugin.handlers;

import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;

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
		RuleEvalWCov.reset();
		AsmetaV.execValidation(path, true);
	}
}