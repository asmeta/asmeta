package asmeta.ai.propgen.ui.services;

import org.eclipse.jface.preference.IPreferenceStore;

import asmeta.ai.propgen.PropertyType;
import asmeta.ai.propgen.ui.preferences.LlmPreferenceSupport;
import asmeta.ai.propgen.ui.preferences.PreferenceConstants;

public class AsmetaAISettings {

	public enum LlmChoice {
		OLLAMA, OPENAI
	}

	private final LlmChoice llmChoice;
	private final String baseUrl;
	private final String modelName;
	private final String apiKey;
	private final PropertyType propertyType;
	private final int numberOfProperties;
	private final int maxRetries;
	private final int requestTimeoutSeconds;
	private final boolean debugOutput;

	private AsmetaAISettings(LlmChoice llmChoice, String baseUrl, String modelName, String apiKey,
			PropertyType propertyType, int numberOfProperties, int maxRetries, int requestTimeoutSeconds,
			boolean debugOutput) {
		this.llmChoice = llmChoice;
		this.baseUrl = baseUrl;
		this.modelName = modelName;
		this.apiKey = apiKey;
		this.propertyType = propertyType;
		this.numberOfProperties = numberOfProperties;
		this.maxRetries = maxRetries;
		this.requestTimeoutSeconds = requestTimeoutSeconds;
		this.debugOutput = debugOutput;
	}

	public static AsmetaAISettings fromPreferenceStore(IPreferenceStore store) {
		LlmPreferenceSupport.migrateLegacySettings(store);
		String llmChoiceValue = LlmPreferenceSupport.normalizeLlmChoice(store.getString(PreferenceConstants.P_LLM_CHOICE));
		return new AsmetaAISettings(
				parseLlmChoice(llmChoiceValue),
				store.getString(LlmPreferenceSupport.baseUrlPreferenceFor(llmChoiceValue)).trim(),
				store.getString(LlmPreferenceSupport.modelNamePreferenceFor(llmChoiceValue)).trim(),
				store.getString(LlmPreferenceSupport.apiKeyPreferenceFor(llmChoiceValue)).trim(),
				parsePropertyType(store.getString(PreferenceConstants.P_PROPERTY_TYPE)),
				store.getInt(PreferenceConstants.P_NUM_PROP),
				store.getInt(PreferenceConstants.P_MAX_RETIRES),
				store.getInt(PreferenceConstants.P_LLM_TIMEOUT_SECONDS),
				store.getBoolean(PreferenceConstants.P_DEBUG_OUTPUT));
	}

	public LlmChoice getLlmChoice() {
		return llmChoice;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getModelName() {
		return modelName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public int getNumberOfProperties() {
		return numberOfProperties;
	}
	
	public int getMaxRetries() {
		return maxRetries;
	}

	public int getRequestTimeoutSeconds() {
		return requestTimeoutSeconds;
	}

	public boolean isDebugOutput() {
		return debugOutput;
	}

	private static LlmChoice parseLlmChoice(String value) {
		if ("openai".equals(value)) {
			return LlmChoice.OPENAI;
		}
		if ("ollama".equals(value)) {
			return LlmChoice.OLLAMA;
		}
		return LlmChoice.OLLAMA;
	}

	private static PropertyType parsePropertyType(String value) {
		if ("ctl".equals(value)) {
			return PropertyType.CTLPROP;
		}
		return PropertyType.LTLPROP;
	}
}
