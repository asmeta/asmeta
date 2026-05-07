package asmeta.ai.propgen.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				String.valueOf(FrameworkUtil.getBundle(getClass()).getBundleId()));
		
		store.setDefault(PreferenceConstants.P_LLM_CHOICE, "http");
		store.setDefault(PreferenceConstants.P_PROPERTY_TYPE, "ltl");
		store.setDefault(PreferenceConstants.P_NUM_PROP, 3);
		store.setDefault(PreferenceConstants.P_API_KEY, "");
		store.setDefault(PreferenceConstants.P_LLM_HTTP_URL, "https://localhost:11434/api/generate");
		store.setDefault(PreferenceConstants.P_MODEL_NAME, "gpt-5-nano");
	}

}
