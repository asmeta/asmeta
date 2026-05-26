package asmeta.ai.propgen.llm;

/**
 * Base class for LLM clients accessed over HTTP.
 */
public abstract class HttpLlmClient implements LlmClient {

	protected String baseUrl;

	protected HttpLlmClient(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
