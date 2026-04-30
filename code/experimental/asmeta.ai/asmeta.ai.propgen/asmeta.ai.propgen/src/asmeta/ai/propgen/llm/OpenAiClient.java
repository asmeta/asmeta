package asmeta.ai.propgen.llm;

import java.util.stream.Collectors;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

/**
 * OpenAI-backed implementation of {@link LlmClient}.
 */
public class OpenAiClient extends HttpLlmClient {

	private static final String DEFAULT_BASE_URL = "https://api.openai.com/v1";
	private static final ChatModel DEFAULT_MODEL = ChatModel.GPT_5_NANO;
	private static final String KEY_ENV_VAR = "OPENAI_API_KEY";
	private static final String BASE_URL_ENV_VAR = "OPENAI_BASE_URL";

	private final OpenAIClient client;
	private ChatModel model;

	/**
	 * Builds the endpoint from environment variables.
	 */
	public OpenAiClient() {
		this(resolveEnvOrDefault(BASE_URL_ENV_VAR, DEFAULT_BASE_URL), resolveEnv(KEY_ENV_VAR));
	}

	/**
	 * Builds the endpoint using the default OpenAI base URL.
	 *
	 * @param apiKey the OpenAI API key
	 */
	public OpenAiClient(String apiKey) {
		this(DEFAULT_BASE_URL, apiKey);
	}

	/**
	 * Builds the endpoint with an explicit base URL and API key.
	 *
	 * @param baseUrl the API base URL
	 * @param apiKey  the API key
	 */
	public OpenAiClient(String baseUrl, String apiKey) {
		super(baseUrl, apiKey);
		this.model = DEFAULT_MODEL;
		this.client = OpenAIOkHttpClient.builder()
				.baseUrl(baseUrl)
				.apiKey(apiKey)
				.build();
	}

	/**
	 * @return the currently configured model
	 */
	public ChatModel getModel() {
		return model;
	}

	/**
	 * Sets the model to use for future requests.
	 *
	 * @param model the model to use
	 */
	public void setModel(ChatModel model) {
		this.model = model;
	}

	@Override
	public String query(String prompt) {
		try {
			ResponseCreateParams params = ResponseCreateParams.builder().model(model).input(prompt).build();
			Response response = client.responses().create(params);
			return response.output().stream()
					.flatMap(item -> item.message().stream())
					.flatMap(message -> message.content().stream())
					.flatMap(content -> content.outputText().stream())
					.map(outputText -> outputText.text()).collect(Collectors.joining());
		} catch (Exception e) {
			throw new RuntimeException("OpenAI request failed", e);
		}
	}

	private static String resolveEnv(String envName) {
		String value = System.getenv(envName);
		if (value == null || value.isBlank()) {
			throw new RuntimeException("Missing required environment variable: " + envName);
		}
		return value;
	}

	private static String resolveEnvOrDefault(String envName, String defaultValue) {
		String value = System.getenv(envName);
		return (value == null || value.isBlank()) ? defaultValue : value;
	}
}
