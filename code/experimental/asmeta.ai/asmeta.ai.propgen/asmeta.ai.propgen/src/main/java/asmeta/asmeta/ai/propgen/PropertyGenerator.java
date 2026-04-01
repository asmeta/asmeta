package asmeta.asmeta.ai.propgen;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

import asmeta.structure.Asm;

public abstract class PropertyGenerator {

	protected Asm asm;
	OpenAIClient client;

	public PropertyGenerator() {
		client = OpenAIOkHttpClient.builder()
				// Configures using the `openai.apiKey`, `openai.orgId`, `openai.projectId`,
				// `openai.webhookSecret` and `openai.baseUrl` system properties
				// Or configures using the `OPENAI_API_KEY`, `OPENAI_ORG_ID`,
				// `OPENAI_PROJECT_ID`, `OPENAI_WEBHOOK_SECRET` and `OPENAI_BASE_URL`
				// environment variables
				.fromEnv()
				.apiKey("")
				.build();
	}

}
