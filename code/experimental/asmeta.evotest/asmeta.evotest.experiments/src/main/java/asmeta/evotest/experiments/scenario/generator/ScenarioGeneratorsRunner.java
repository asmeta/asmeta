package asmeta.evotest.experiments.scenario.generator;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.asmeta.parser.ASMParser;
import org.yaml.snakeyaml.Yaml;

import asmeta.evotest.experiments.utils.YamlManager;

public class ScenarioGeneratorsRunner {
	public static final String RANDOM_DIR = "randomtests";
	public static final String ATGT_DIR = "atgttests";
	public static final String EVOAVALLA_DIR = "evoavallatests";

	private static int randomCount = 0;
	private static int atgtCount = 0;
	private static int evoavallaCount = 0;

	/**
	 * Loads the YAML configuration file {@code config/generation_config.yaml} from
	 * the application resources.
	 *
	 * @return the parsed configuration as a map
	 * @throws RuntimeException if the config file cannot be found or read
	 */
	private static Map<String, Object> loadConfig() {
		try (InputStream in = ScenarioGeneratorsRunner.class.getResourceAsStream("/config/generation_config.yaml")) {
			if (in == null)
				throw new IllegalStateException("generation_config.yaml not found");
			return new Yaml().load(in); // Map<String, Object>
		} catch (Exception e) {
			throw new RuntimeException("Failed to load YAML", e);
		}
	}

	/**
	 * Entry point. Reads the generation configuration, prepares the output folder,
	 * and runs the scenario generators on one or multiple ASM files depending on
	 * the configured mode ("single", "directory", or "list").
	 *
	 * @param args unused
	 * @throws Exception if file operations fail
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		// Load configuration
		Map<String, Object> cfg = loadConfig();
		// delete existing target folder and recreate the empty directory
		String targetFolder = (String) ((Map<String, Object>) cfg.get("output")).get("target_folder");
		File resultsDir = new File(targetFolder);
		if (resultsDir.exists())
			FileUtils.deleteDirectory(resultsDir);
		Files.createDirectories(new File(targetFolder).toPath());
		// Run test generation with the input as defined in the configuration
		Map<String, Object> sourceCfg = (Map<String, Object>) cfg.get("input");
		String mode = (String) sourceCfg.get("mode");
		switch (mode) {
		case "single":
			String sourceFile = (String) sourceCfg.get("source_file");
			generateTests(sourceFile, cfg);
			break;
		case "directory":
			String sourceDir = (String) sourceCfg.get("source_dir");
			File[] children = new File(sourceDir).listFiles();
			for (File child : children) {
				sourceFile = child.getPath();
				if (sourceFile.endsWith(ASMParser.ASM_EXTENSION))
					generateTests(sourceFile, cfg);
			}
			break;
		case "list":
			sourceDir = (String) sourceCfg.get("base_dir");
			String listFile = (String) sourceCfg.get("model_list_file");
			List<String> lines = Files.readAllLines(Paths.get(listFile));
			for (String line : lines) {
				if (line.isEmpty() || line.startsWith("//"))
					continue;
				generateTests(Paths.get(sourceDir).resolve(line).toString(), cfg);
			}
			break;
		}
	}

	/**
	 * Generates test suites for the given ASM model by running the scenario
	 * generation algorithms that are enabled in the configuration. Each enabled
	 * algorithm produces its output in a separate subdirectory with a sequential
	 * index.
	 *
	 * @param asmFilePath path to the ASM file to process
	 * @param cfg         the loaded configuration map
	 */
	@SuppressWarnings("unchecked")
	private static void generateTests(String asmFilePath, Map<String, Object> cfg) {
		String asmName = new File(asmFilePath).getName().replace(".asm", "");
		String targetFolder = (String) ((Map<String, Object>) cfg.get("output")).get("target_folder");
		Map<String, Object> algorithms = (Map<String, Object>) cfg.get("algorithms");
		runRandomIfEnabled(asmFilePath, asmName, targetFolder, algorithms);
		runAtgtIfEnabled(asmFilePath, asmName, targetFolder, algorithms);
		runEvoAvallaIfEnabled(asmFilePath, asmName, targetFolder, algorithms);
	}

	/**
	 * Runs the EvoAvalla generator if enabled in the configuration. The generated
	 * test suite is stored in a dedicated subdirectory and execution metadata
	 * is recorded.
	 *
	 * @param asmFilePath  path to the ASM file
	 * @param asmName      name of the ASM (file name without extension)
	 * @param targetFolder base output directory for generated tests
	 * @param algorithms   configuration block containing EvoAvalla settings
	 */
	@SuppressWarnings("unchecked")
	private static void runEvoAvallaIfEnabled(String asmFilePath, String asmName, String targetFolder,
			Map<String, Object> algorithms) {
		Map<String, Object> evoavallaCfg = (Map<String, Object>) algorithms.get("evoavalla");
		if ((Boolean) evoavallaCfg.get("run")) {
			try {
				String jdkPath = (String) evoavallaCfg.get("jdk_path");
				String target = targetFolder + File.separator + EVOAVALLA_DIR + File.separator
						+ formatCounter(evoavallaCount) + "_" + asmName;
				int budget = (int) evoavallaCfg.get("budget");
				float exeTime = ScenarioGenerator.runEvoAvalla(asmFilePath, target, jdkPath, budget);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable e) {
				System.err.println("EvoAvalla failed to generate a test suite");
				e.printStackTrace();
			} finally {
				evoavallaCount++;
			}
		}
	}

	/**
	 * Runs the ATGT generator if enabled in the configuration. The generated
	 * test suite is stored in a dedicated subdirectory and execution metadata
	 * is recorded.
	 *
	 * @param asmFilePath  path to the ASM file
	 * @param asmName      name of the ASM (file name without extension)
	 * @param targetFolder base output directory for generated tests
	 * @param algorithms   configuration block containing ATGT settings
	 */
	@SuppressWarnings("unchecked")
	private static void runAtgtIfEnabled(String asmFilePath, String asmName, String targetFolder,
			Map<String, Object> algorithms) {
		Map<String, Object> atgtCfg = (Map<String, Object>) algorithms.get("atgt");
		if ((Boolean) atgtCfg.get("run")) {
			try {
				String target = targetFolder + File.separator + ATGT_DIR + File.separator + formatCounter(atgtCount)
						+ "_" + asmName;
				float exeTime = ScenarioGenerator.runATGT(asmFilePath, target);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable e) {
				System.err.println("ATGT failed to generate a test suite");
				e.printStackTrace();
			} finally {
				atgtCount++;
			}
		}
	}

	/**
	 * Runs the Random generator if enabled in the configuration. The generated
	 * test suite is stored in a dedicated subdirectory and execution metadata
	 * is recorded.
	 *
	 * @param asmFilePath  path to the ASM file
	 * @param asmName      name of the ASM (file name without extension)
	 * @param targetFolder base output directory for generated tests
	 * @param algorithms   configuration block containing Random generator settings
	 */
	@SuppressWarnings("unchecked")
	private static void runRandomIfEnabled(String asmFilePath, String asmName, String targetFolder,
			Map<String, Object> algorithms) {
		Map<String, Object> randomCfg = (Map<String, Object>) algorithms.get("random");
		if ((Boolean) randomCfg.get("run")) {
			try {
				String target = targetFolder + File.separator + RANDOM_DIR + File.separator + formatCounter(randomCount)
						+ "_" + asmName;
				List<Integer> stepList = (List<Integer>) randomCfg.get("step_list");
				float exeTime = ScenarioGenerator.runRandom(asmFilePath, target, stepList);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable e) {
				System.err.println("Random failed to generate a test suite");
				e.printStackTrace();
			} finally {
				randomCount++;
			}
		}
	}

	/**
	 * Formats the numeric counter as a zero-padded three-digit string
	 * (e.g., 0 → "000", 7 → "007", 42 → "042").
	 *
	 * @param counter a non-negative integer
	 * @return the formatted string
	 */
	private static String formatCounter(int coutner) {
		String countString = String.valueOf(coutner);
		if (coutner < 10)
			countString = "00" + coutner;
		else if (coutner < 100)
			countString = "0" + coutner;
		return countString;
	}

}
