package asmeta.ai.propgen.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import asmeta.ai.propgen.ui.Activator;
import asmeta.ai.propgen.llm.OllamaClient;
import asmeta.ai.propgen.llm.OpenAiClient;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault(PreferenceConstants.P_LLM_CHOICE, "ollama");
		store.setDefault(PreferenceConstants.P_PROPERTY_TYPE, "ltl");
		store.setDefault(PreferenceConstants.P_NUM_PROP, 3);
		store.setDefault(PreferenceConstants.P_MAX_RETIRES, 3);
		store.setDefault(PreferenceConstants.P_LLM_TIMEOUT_SECONDS, 300);
		store.setDefault(PreferenceConstants.P_API_KEY, "");
		store.setDefault(PreferenceConstants.P_LLM_HTTP_URL, "");
		store.setDefault(PreferenceConstants.P_MODEL_NAME, "");
		store.setDefault(PreferenceConstants.P_OLLAMA_API_KEY, "");
		store.setDefault(PreferenceConstants.P_OLLAMA_LLM_HTTP_URL, OllamaClient.DEFAULT_BASE_URL);
		store.setDefault(PreferenceConstants.P_OLLAMA_MODEL_NAME, OllamaClient.DEFAULT_MODEL);
		store.setDefault(PreferenceConstants.P_OPENAI_API_KEY, "");
		store.setDefault(PreferenceConstants.P_OPENAI_LLM_HTTP_URL, OpenAiClient.DEFAULT_BASE_URL);
		store.setDefault(PreferenceConstants.P_OPENAI_MODEL_NAME, OpenAiClient.DEFAULT_MODEL_NAME);
		store.setDefault(PreferenceConstants.P_LLM_SETTINGS_MIGRATED, false);
		store.setDefault(PreferenceConstants.P_DEBUG_OUTPUT, false);
	}

}
