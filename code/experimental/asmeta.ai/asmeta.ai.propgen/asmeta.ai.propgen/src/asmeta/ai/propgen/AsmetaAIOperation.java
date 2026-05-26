package asmeta.ai.propgen;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Executable AsmetaAI operation.
 */
public interface AsmetaAIOperation {

	String RESOURCES = "./resources";

	/**
	 * Executes the operation with the given request data.
	 *
	 * @param request data needed to run the operation
	 * @return generated output
	 */
	String execute(AsmetaAIOperationRequest request);

	default String readModel(String asmPath, boolean removeComments) {
		String asmContent = getFileContent(asmPath);
		if (removeComments) {
			return removeComments(asmContent);
		}
		return asmContent;
	}

	default String applySubstitutions(String template, Map<String, String> substitutions) {
		String result = template;
		for (Entry<String, String> sub : substitutions.entrySet()) {
			result = result.replace(sub.getKey(), sub.getValue());
		}
		return result;
	}

	default String getFileContent(String path) {
		Path filePath = Path.of(path);
		if (Files.exists(filePath)) {
			try {
				return Files.readString(filePath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new RuntimeException("Failed to access file: " + path, e);
			}
		}
		String resourcePath = path;
		if (resourcePath.startsWith(RESOURCES)) {
			resourcePath = resourcePath.substring(RESOURCES.length());
		}
		try {
			InputStream is = getClass().getResourceAsStream(resourcePath);
			if (is != null) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read resource: " + resourcePath, e);
		}
		throw new RuntimeException(
				"File does not exist. Tried filesystem path: " + path + " and classpath resource: " + resourcePath);
	}

	default String removeComments(String text) {
		if (text == null) {
			return null;
		}
		text = text.replaceAll("(?s)/\\*.*?\\*/", "");
		return text.replaceAll("(?m)//.*?$", "");
	}
}
