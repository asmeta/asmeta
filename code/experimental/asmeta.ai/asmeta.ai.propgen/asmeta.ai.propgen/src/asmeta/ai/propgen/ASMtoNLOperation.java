package asmeta.ai.propgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import asmeta.ai.propgen.llm.LlmClient;

/**
 * Generates natural-language properties from an ASMETA model.
 */
public final class ASMtoNLOperation implements AsmetaAIOperation {

	private static final String O1_PROMPT_TEMPLATE = RESOURCES + "/prompt_template_o1.txt";

	private static final Logger logger = Logger.getLogger(ASMtoNLOperation.class);

	private final LlmClient llm;

	public ASMtoNLOperation(LlmClient llm) {
		this.llm = llm;
	}

	@Override
	public String execute(AsmetaAIOperationRequest request) {
		List<String> properties = generate(request.getAsmPath(), request.getNumberOfProperties(),
				request.isRemoveComments());
		return String.join(System.lineSeparator(), properties);
	}

	public List<String> generate(String asmPath, int numberOfProperties, boolean removeComments) {
		Map<String, String> substitutions = new HashMap<>();
		substitutions.put("MODEL_PLACEHOLDER", readModel(asmPath, removeComments));
		substitutions.put("N_PROP_PLACEHOLDER", String.valueOf(numberOfProperties));
		String prompt = applySubstitutions(getFileContent(O1_PROMPT_TEMPLATE), substitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response.lines().filter(line -> !line.trim().isEmpty()).toList();
	}
}
