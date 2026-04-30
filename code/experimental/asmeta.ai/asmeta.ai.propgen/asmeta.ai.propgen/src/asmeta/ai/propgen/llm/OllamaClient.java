package asmeta.ai.propgen.llm;

import java.util.List;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.response.Model;
import io.github.ollama4j.models.response.OllamaResult;

/**
 * Ollama-backed implementation of {@link LlmClient}.
 */
public class OllamaClient extends HttpLlmClient {

	private static final String DEFAULT_BASE_URL = "https://ollama.com";
	private static final String DEFAULT_MODEL = "gpt-oss:20b";
	private static final int DEFAULT_TIMEOUT = 300;

	private final OllamaAPI client;
	private String model;
	
	
	/**
	 * Builds the endpoint using the Ollama default base url and timeout.
	 *
	 * @param apiKey  the bearer token
	 */
	public OllamaClient(String apiKey) {
		this(DEFAULT_BASE_URL, apiKey);
	}
	
	/**
	 * Builds the endpoint using the default timeout.
	 *
	 * @param baseUrl the Ollama server base URL
	 * @param apiKey  the bearer token
	 */
	public OllamaClient(String baseUrl, String apiKey) {
		this(baseUrl, apiKey, DEFAULT_TIMEOUT);
	}
	
	/**
	 * Builds the endpoint using the Ollama default base url.
	 *
	 * @param apiKey  the bearer token
	 * @param timeout the timeout in seconds
	 */
	public OllamaClient(String apiKey, int timeout) {
		this(DEFAULT_BASE_URL, apiKey, timeout);
	}

	/**
	 * Builds the endpoint.
	 *
	 * @param baseUrl the Ollama server base URL
	 * @param apiKey  the bearer token
	 * @param timeout the timeout in seconds
	 */
	public OllamaClient(String baseUrl, String apiKey, int timeout) {
		super(baseUrl, apiKey);
		this.model = DEFAULT_MODEL;
		this.client = new OllamaAPI(baseUrl);
		this.client.setBearerAuth(apiKey);
		this.client.setRequestTimeoutSeconds(timeout);
	}

	/**
	 * @return the currently configured model name
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @return the details of currently configured model
	 */
	public String getModelDetails() {
		try {
			return this.client.getModelDetails(this.model).toString();
		} catch (Exception e) {
			throw new RuntimeException("Unable to retrieve Ollama model details", e);
		}
	}

	/**
	 * Sets the model after checking that it is available on the server.
	 *
	 * @param modelName the model name
	 * @throws LlmException if the model is not available or the server cannot be
	 *                      queried
	 */
	public void setModel(String modelName) {
		List<Model> models;
		try {
			models = client.listModels();
		} catch (Exception e) {
			throw new RuntimeException("Unable to retrieve Ollama model list", e);
		}
		boolean found = models.stream().anyMatch(model -> model.getName().equals(modelName));
		if (!found) {
			String message = "Model not available on Ollama server: " + modelName;
			message += "\n" + "Available models:";
			for (Model model : models) {
				message += "\n" + model.getName();
			}
			throw new RuntimeException(message);
		}
		this.model = modelName;
	}


	@Override
	public String query(String prompt) {
		if (!this.client.ping())
			throw new RuntimeException("Ollama server is not reachable");
		try {
			OllamaResult result = client.generate(model, prompt, null);
			return result.getResponse();
		} catch (Exception e) {
			throw new RuntimeException("Ollama request failed", e);
		}
	}
}