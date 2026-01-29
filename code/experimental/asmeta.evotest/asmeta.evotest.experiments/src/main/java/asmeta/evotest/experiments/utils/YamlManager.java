package asmeta.evotest.experiments.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Utility class for writing and reading simple YAML metadata files related to
 * scenario generation.
 */
public class YamlManager {
	public static final String FILE_NAME = "metadata.yaml";

	public static final String ASM_NAME = "asm_name";
	public static final String ASM_PATH = "asm_path";
	public static final String EXEC_TIME = "exec_time_ms";
	public static final String GENERETED_AT = "generated_at";

	/**
	 * Write metadata about scenario generation to a metadata.yaml file
	 * 
	 * @param baseFolderPath the path containing the generated scenarios
	 * @param asmName        the name of the asm target of scenario generation
	 * @param asmPath        the path to the asm (relative to baseFolderPath)
	 * @param exeTime        the execution time of the scenario generator
	 * @param generatedAt    the date and time of scenario generation
	 * @throws IOException if an I/O error occurs writing to or creating the file
	 */
	public static void write(String baseFolderPath, String asmName, String asmPath, float exeTime, String generatedAt)
			throws IOException {
		Path basePath = Path.of(baseFolderPath);
		Path targetPath = Path.of(asmPath);
		String relativeAsmPath;
		try {
			relativeAsmPath = basePath.relativize(targetPath).toString();
		} catch (IllegalArgumentException e) {
		    relativeAsmPath = targetPath.toAbsolutePath().toString(); // fallback to absolute
		}
		String content = "";
		content += ASM_NAME + ": " + asmName + System.lineSeparator();
		content += ASM_PATH + ": " + relativeAsmPath + System.lineSeparator();
		content += EXEC_TIME + ": " + exeTime + System.lineSeparator();
		content += GENERETED_AT + ": " + generatedAt;
		Files.writeString(Path.of(baseFolderPath + File.separator + FILE_NAME), content, StandardCharsets.UTF_8);
	}

	/**
	 * load a yaml file
	 * 
	 * @param yamlFile the yaml file
	 * @return the loaded yaml file as a Map
	 */
	public static Map<String, Object> load(File yamlFile) {
		try (InputStream in = new FileInputStream(yamlFile)) {
			return new Yaml().load(in); // returns Map<String, Object>
		} catch (Exception e) {
			throw new RuntimeException("Failed to load YAML from " + yamlFile.getAbsolutePath(), e);
		}
	}

}
