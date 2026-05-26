package asmeta.ai.propgen.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Shared helpers for the provider-specific LLM preference keys.
 */
public final class LlmPreferenceSupport {

	public static final String LLM_OLLAMA = "ollama";
	public static final String LLM_OPENAI = "openai";

	private LlmPreferenceSupport() {
	}

	public static String normalizeLlmChoice(String value) {
		return LLM_OPENAI.equals(value) ? LLM_OPENAI : LLM_OLLAMA;
	}

	public static String baseUrlPreferenceFor(String llmChoice) {
		return LLM_OPENAI.equals(normalizeLlmChoice(llmChoice))
				? PreferenceConstants.P_OPENAI_LLM_HTTP_URL
				: PreferenceConstants.P_OLLAMA_LLM_HTTP_URL;
	}

	public static String modelNamePreferenceFor(String llmChoice) {
		return LLM_OPENAI.equals(normalizeLlmChoice(llmChoice))
				? PreferenceConstants.P_OPENAI_MODEL_NAME
				: PreferenceConstants.P_OLLAMA_MODEL_NAME;
	}

	public static String apiKeyPreferenceFor(String llmChoice) {
		return LLM_OPENAI.equals(normalizeLlmChoice(llmChoice))
				? PreferenceConstants.P_OPENAI_API_KEY
				: PreferenceConstants.P_OLLAMA_API_KEY;
	}

	public static void migrateLegacySettings(IPreferenceStore store) {
		if (store.getBoolean(PreferenceConstants.P_LLM_SETTINGS_MIGRATED)) {
			return;
		}

		String selectedLlm = normalizeLlmChoice(store.getString(PreferenceConstants.P_LLM_CHOICE));
		migrateLegacyValue(store, baseUrlPreferenceFor(selectedLlm), PreferenceConstants.P_LLM_HTTP_URL);
		migrateLegacyValue(store, modelNamePreferenceFor(selectedLlm), PreferenceConstants.P_MODEL_NAME);
		migrateLegacyValue(store, apiKeyPreferenceFor(selectedLlm), PreferenceConstants.P_API_KEY);
		store.setValue(PreferenceConstants.P_LLM_SETTINGS_MIGRATED, true);
	}

	private static void migrateLegacyValue(IPreferenceStore store, String targetKey, String legacyKey) {
		String legacyValue = store.getString(legacyKey);
		if (legacyValue != null && !legacyValue.isBlank()) {
			store.setValue(targetKey, legacyValue.trim());
		}
	}
}
