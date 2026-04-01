package asmeta.ai.propgen.llm;

/**
 * Base class for LLM clients accessed over HTTP.
 */
public abstract class HttpLlmClient implements LlmClient {

	protected String baseUrl;
	protected String apiKey;

	protected HttpLlmClient(String baseUrl, String apiKey) {
		this.baseUrl = baseUrl;
		this.apiKey = apiKey;
	}

}