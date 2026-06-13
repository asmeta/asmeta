package org.asmeta.validator.plugin.handlers;

import org.asmeta.validator.ui.preferences.PreferenceConstants;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

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
		// read the preferences to see if mutation is rqeusted
		ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, String.valueOf(FrameworkUtil.getBundle(getClass()).getBundleId()));
		boolean mutation = store.getBoolean(PreferenceConstants.P_COMPUTE_MUTATIONSCORE);
		if (! mutation)
			AsmetaV.execValidation(path, AsmetaV.computeCoverage);
		else
			AsmetaV.execValidation(path, AsmetaV.computeMutationScore);
	}
}