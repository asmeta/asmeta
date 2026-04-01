package asmeta.ai.propgen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import asmeta.ai.propgen.llm.LlmClient;

public class PropertyGenerator {
	
	private static final String RESOURCES = "./src/main/resources";
	private static final String O1_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o1.txt";
	private static final String O2_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o2.txt";
	private static final String O3_PROMPT_TEMPLATE = RESOURCES + "/simple_prompt_template_o3.txt";
	
	public enum PropertyType {
		CTLPROP, LTLPROP
	}

	private LlmClient llm;

	public PropertyGenerator(LlmClient llm) {
		this.llm = llm;
	}
	
	public List<String> fromASMtoNL(String asmPath, int nProperties){
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O1_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath); // TODO: rimuovere i commenti?
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("N_PROP_PLACEHOLDER", String.valueOf(nProperties));
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		System.out.println(prompt);
		String response = llm.query(prompt);
		return response.lines().toList();
	}
	
	public String fromNLtoTL(String asmPath, String property, PropertyType type){
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O2_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath); // TODO: rimuovere i commenti?
		String propertyTypeValue = type == PropertyType.CTLPROP? "CTL (Computation Tree Logic)" : "LTL (Linear Temporal Logic)";
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("PROPERTY_TYPE_PLACEHOLDER", propertyTypeValue);
		placeholdersSubstitutions.put("NL_PROPERTY_PLACEHOLDER", property);
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		System.out.println(prompt);
		String response = llm.query(prompt);
		return response;
	}
	
	public List<String> fromTLtoNL(String asmPath, String property){
		Map<String, String> placeholdersSubstitutions = new HashMap<>();
		String template = getFileContent(O3_PROMPT_TEMPLATE);
		String asmContent = getFileContent(asmPath); // TODO: rimuovere i commenti?
		placeholdersSubstitutions.put("MODEL_PLACEHOLDER", asmContent);
		placeholdersSubstitutions.put("TL_PROPERTY_PLACEHOLDER", property);
		String prompt = applySubstitutions(template, placeholdersSubstitutions);
		System.out.println(prompt);
		String response = llm.query(prompt);
		return response.lines().toList();
	}
	
	private String applySubstitutions(String template, Map<String, String> substitutions) {
		String result = template;
		for (Entry<String, String> sub : substitutions.entrySet()) {
			String placeholder = sub.getKey();
			String value = sub.getValue();
			result = result.replaceAll(placeholder, value);
		}
		return result;
	}
	
	private String getFileContent(String path) {
		Path filePath = Path.of(path);
		if (Files.exists(filePath))
			try {
				return Files.readString(filePath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new RuntimeException("Failed to access " + path, e);
			}
		throw new RuntimeException("File does not exist:" + path);
	}

}
