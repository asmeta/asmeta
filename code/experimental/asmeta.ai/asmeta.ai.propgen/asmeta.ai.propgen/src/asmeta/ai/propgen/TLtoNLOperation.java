package asmeta.ai.propgen;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import asmeta.ai.propgen.llm.LlmClient;

/**
 * Explains a temporal-logic property in natural language.
 */
public final class TLtoNLOperation implements AsmetaAIOperation {

	private static final String O3_PROMPT_TEMPLATE = RESOURCES + "/prompt_template_o3.txt";

	private static final Logger logger = Logger.getLogger(TLtoNLOperation.class);

	private final LlmClient llm;

	public TLtoNLOperation(LlmClient llm) {
		this.llm = llm;
	}

	@Override
	public String execute(AsmetaAIOperationRequest request) {
		return explain(request.getAsmPath(), request.getInput(), request.isRemoveComments());
	}

	public String explain(String asmPath, String property, boolean removeComments) {
		Map<String, String> substitutions = new HashMap<>();
		substitutions.put("MODEL_PLACEHOLDER", readModel(asmPath, removeComments));
		substitutions.put("TL_PROPERTY_PLACEHOLDER", property);
		String prompt = applySubstitutions(getFileContent(O3_PROMPT_TEMPLATE), substitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response;
	}
}
