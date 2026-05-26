package asmeta.ai.propgen.ui.preferences;

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
}
