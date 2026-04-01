package asmeta.ai.propgen.llm;

/**
 * Interface for a text-based LLM client. Implementations accept a plain text
 * prompt and return a plain text reply.
 */
public interface LlmClient {

	/**
	 * Sends a prompt to the model and returns the generated text.
	 *
	 * @param prompt the prompt
	 * @return the generated text
	 */
	String query(String prompt);
}