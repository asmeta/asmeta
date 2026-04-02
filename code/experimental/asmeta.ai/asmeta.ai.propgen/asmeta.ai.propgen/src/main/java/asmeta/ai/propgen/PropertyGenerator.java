package asmeta.ai.propgen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import asmeta.ai.propgen.llm.LlmClient;

/**
 * Generates and transforms properties starting from an ASMETA model.
 * <p>
 * It uses an {@link LlmClient} to query a Large Language Model (LLM) with
 * predefined prompt templates.
 */
public class PropertyGenerator {

	private static final String RESOURCES = "./src/main/resources";
	private static final String O1_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o1.txt";
	private static final String O2_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o2.txt";
	private static final String O3_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o3.txt";

	public enum PropertyType {
		CTLPROP, LTLPROP
	}

	private LlmClient llm;
	
	private static Logger logger = Logger.getLogger(PropertyGenerator.class);

	/**
	 * Constructs a PropertyGenerator with the given LLM client.
	 *
	 * @param llm the client used to interact with the language model
	 */
	public PropertyGenerator(LlmClient llm) {
		this.llm = llm;
	}

	/**
	 * Generates a list of natural language properties starting from an ASMETA
	 * model.
	 *
	 * @param asmPath        path to the ASMETA model file
	 * @param nProperties    number of properties to generate
	 * @param removeComments whether to remove comments from the ASMETA model before
	 *                       sending it to the LLM
	 * @return a list of generated natural language properties
	 * @throws RuntimeException if the ASMETA file or template cannot be read, or if
	 *                          there is an error while communicating with the LLM
	 */
	public List<String> fromASMtoNL(String asmPath, int nProperties, boolean removeComments) {
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O1_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath);
		if (removeComments)
			asmContent = removeComments(asmContent);
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("N_PROP_PLACEHOLDER", String.valueOf(nProperties));
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response.lines()
				.filter(line -> !line.trim().isEmpty())
		        .toList();
	}

	/**
	 * Translates a natural language property into a CTL or LTL property for an
	 * ASMETA model.
	 *
	 * @param asmPath        path to the ASMETA model file
	 * @param property       property in natural language
	 * @param type           type of temporal logic to be used for the generated
	 *                       property
	 * @param removeComments whether to remove comments from the ASMETA model before
	 *                       sending it to the LLM
	 * @return the generated temporal logic property
	 * @throws RuntimeException if the ASMETA file or template cannot be read, or if
	 *                          there is an error while communicating with the LLM
	 */
	public String fromNLtoTL(String asmPath, String property, PropertyType type, boolean removeComments) {
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O2_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath);
		if (removeComments)
			asmContent = removeComments(asmContent);
		String propertyTypeValue = type == PropertyType.CTLPROP ? "CTL (Computation Tree Logic)"
				: "LTL (Linear Temporal Logic)";
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("PROPERTY_TYPE_PLACEHOLDER", propertyTypeValue);
		placeholdersSubstitutions.put("NL_PROPERTY_PLACEHOLDER", property);
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response;
	}

	/**
	 * Describes a temporal logic property in natural language for an ASMETA model.
	 *
	 * @param asmPath        path to the ASMETA model file
	 * @param property       property in CTL or LTL
	 * @param removeComments whether to remove comments from the ASMETA model before
	 *                       sending it to the LLM
	 * @return a list with "HOLD" or "NOT HOLD" in position 0, and the description
	 *         of the property in position 1
	 * @throws RuntimeException if the ASMETA file or template cannot be read, or if
	 *                          there is an error while communicating with the LLM
	 */
	public List<String> fromTLtoNL(String asmPath, String property, boolean removeComments) {
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O3_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath);
		if (removeComments)
			asmContent = removeComments(asmContent);
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("TL_PROPERTY_PLACEHOLDER", property);
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		logger.debug("Prompt: \n" + prompt);
		String response = llm.query(prompt);
		logger.debug("Full response: \n" + response);
		return response.lines()
				.filter(line -> !line.trim().isEmpty())
		        .toList();
	}

	// Remove comments Java-style comments
	private String removeComments(String text) {
		if (text == null)
			return null;
		text = text.replaceAll("(?s)/\\*.*?\\*/", "");
		text = text.replaceAll("(?m)//.*?$", "");
		return text;
	}

	// Substitutes all occurrences in a string according to a substitution map
	private String applySubstitutions(String template, Map<String, String> substitutions) {
		String result = template;
		for (Entry<String, String> sub : substitutions.entrySet()) {
			String placeholder = sub.getKey();
			String value = sub.getValue();
			result = result.replaceAll(placeholder, value);
		}
		return result;
	}

	// Reads the content of a file as a UTF-8 string
	private String getFileContent(String path) {
		Path filePath = Path.of(path);
		if (Files.exists(filePath))
			try {
				return Files.readString(filePath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new RuntimeException("Failed to access file: " + path, e);
			}
		throw new RuntimeException("File does not exist: " + path);
	}

}