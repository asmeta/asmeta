package asmeta.ai.propgen.ui.services;

import asmeta.ai.propgen.llm.LlmClient;
import asmeta.ai.propgen.llm.OllamaClient;
import asmeta.ai.propgen.llm.OpenAiClient;
import asmeta.ai.propgen.ui.services.AsmetaAISettings.LlmChoice;

public class LlmClientFactory {

	public LlmClient create(AsmetaAISettings settings) {
		LlmChoice llmChoice = settings.getLlmChoice();
		if (llmChoice == LlmChoice.OPENAI) {
			return createOpenAiClient(settings);
		}
		if (llmChoice == LlmChoice.OLLAMA) {
			return createOllamaClient(settings);
		}
		throw new IllegalArgumentException("General HTTP Client is not supported yet by asmeta.ai.propgen.");
	}

	private OpenAiClient createOpenAiClient(AsmetaAISettings settings) {
		requireValue(settings.getApiKey(), "OpenAI API key");
		OpenAiClient client = new OpenAiClient(
				valueOrDefault(settings.getBaseUrl(), OpenAiClient.DEFAULT_BASE_URL),
				settings.getApiKey());
		if (!settings.getModelName().isBlank() && !OpenAiClient.DEFAULT_MODEL_NAME.equals(settings.getModelName())) {
			client.setModel(settings.getModelName());
		}
		return client;
	}

	private OllamaClient createOllamaClient(AsmetaAISettings settings) {
		OllamaClient client = new OllamaClient(
				valueOrDefault(settings.getBaseUrl(), OllamaClient.DEFAULT_BASE_URL),
				settings.getApiKey());
		if (!settings.getModelName().isBlank() && !OllamaClient.DEFAULT_MODEL_NAME.equals(settings.getModelName())) {
			client.setModel(settings.getModelName());
		}
		return client;
	}

	private static String valueOrDefault(String value, String defaultValue) {
		return value == null || value.isBlank() ? defaultValue : value;
	}

	private static void requireValue(String value, String label) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(label + " is required.");
		}
	}
}
