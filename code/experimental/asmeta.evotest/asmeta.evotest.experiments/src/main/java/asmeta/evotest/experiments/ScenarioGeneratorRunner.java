package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.main.Simulator;
import org.yaml.snakeyaml.Yaml;

import asmeta.evotest.evoasmetatg.main.EvoAsmetaTgCLI;
import asmeta.evotest.experiments.scenario.ScenarioGenerator;
import asmeta.evotest.experiments.utils.Utils;
import asmeta.evotest.experiments.utils.YamlManager;

public class ScenarioGeneratorRunner {
	public static final String RANDOM_DIR = "randomtests";
	public static final String ATGT_DIR = "atgttests";
	public static final String EVOAVALLA_DIR = "evoavallatests";
	
	private static final String CONFIG_PATH = "data/generation_config.yaml";

	private static int randomCount = 0;
	private static int atgtCount = 0;
	private static int evoavallaCount = 0;
	
	private static final Logger LOG = Logger.getLogger(ScenarioGeneratorRunner.class);

	/**
	 * Loads the YAML configuration file {@code config/generation_config.yaml} from
	 * the application resources.
	 *
	 * @return the parsed configuration as a map
	 * @throws RuntimeException if the config file cannot be found or read
	 */
	private static Map<String, Object> loadConfig() throws Exception {
		Class<ScenarioGenerator> runningClass = ScenarioGenerator.class;
		String className = runningClass.getName().replace('.', '/');
		String classJar = runningClass.getResource("/" + className + ".class").toString();
		boolean runningFromJar = classJar.startsWith("jar:");
		Path configPath;
		if (runningFromJar) {
		    configPath = Paths.get("generation_config.yaml").toAbsolutePath();
		} else {
			configPath = Paths.get(CONFIG_PATH).toAbsolutePath();
		}
		LOG.info("Loading confifuration file: " + configPath);
	    if (!Files.exists(configPath)) {
	        throw new IllegalStateException("Configuration file not found at: " + configPath);
	    }
	    try (InputStream in = Files.newInputStream(configPath)) {
	        return new Yaml().load(in);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to load YAML from " + configPath, e);
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
	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.ERROR);
		Logger.getLogger(EvoAsmetaTgCLI.class).setLevel(Level.INFO);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));
		
		LOG.info("Starting test generation...");
		
		// Load configuration
		Map<String, Object> cfg;
		try {
			cfg = loadConfig();
		} catch (Throwable t) {
			LOG.error("Falied to load configuration file.", t);
			return;
		}
		
		// delete existing target folder and recreate the empty directory
		try {
			String targetFolder = (String) ((Map<String, Object>) cfg.get("output")).get("target_folder");
			File targetFolderFile = new File(targetFolder);
			if (targetFolderFile.exists()) {
				FileUtils.deleteDirectory(targetFolderFile);
				LOG.info("Old scenarios removed.");
			}
			Files.createDirectories(new File(targetFolder).toPath());
		} catch (Throwable t) {
			LOG.error("Falied to delete existing results.", t);
			return;
		}
		
		Environment.timeMngt = TimeMngt.auto_increment;
		
		// Run test generation with the input as defined in the configuration
		Map<String, Object> sourceCfg = (Map<String, Object>) cfg.get("input");
		String mode = (String) sourceCfg.get("mode");
		LOG.info("Starting generation with mode: "+ mode + ".");
		switch (mode) {
		case "single":
			String sourceFile = (String) sourceCfg.get("source_file");
			LOG.info("Generating test for: " + sourceFile + ".");
			generateTests(sourceFile, cfg);
			break;
		case "directory":
			String sourceDir = (String) sourceCfg.get("source_dir");
			File[] children = new File(sourceDir).listFiles();
			for (File child : children) {
				sourceFile = child.getPath();
				if (sourceFile.endsWith(ASMParser.ASM_EXTENSION)) {
					LOG.info("Generating test for: " + sourceFile + ".");
					generateTests(sourceFile, cfg);
				}
			}
			break;
		case "list":
			sourceDir = (String) sourceCfg.get("base_dir");
			String listFile = (String) sourceCfg.get("model_list_file");
			List<String> lines;
			try {
				lines = Files.readAllLines(Paths.get(listFile));
			} catch (IOException e) {
				LOG.error("Falied to read " + listFile + ".", e);
				return;
			}
			for (String line : lines) {
				if (line.isEmpty() || line.startsWith("//"))
					continue;
				sourceFile = Paths.get(sourceDir).resolve(line).toString().replace('\\', '/');
				LOG.info("Generating test for: " + sourceFile + ".");
				generateTests(sourceFile, cfg);
			}
			break;
		default:
			LOG.info(mode + " is not supported, use: single, directory, or list");
		}
		LOG.info("Test generation completed");
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
		File asmFile = new File(asmFilePath);
		if (!asmFile.exists()) {
			LOG.error("File not found: " + asmFile.getAbsolutePath());
			return;
		}
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
			LOG.info("Running EvoAvalla...");
			String jdkPath = (String) evoavallaCfg.get("jdk_path");
			String target = targetFolder + File.separator + EVOAVALLA_DIR + File.separator
					+ Utils.formatCounter(evoavallaCount) + "_" + asmName;
			new File(target).mkdirs();
			try {
				int budget = (int) evoavallaCfg.get("budget");
				boolean shuffle = (boolean) evoavallaCfg.get("shuffle");
				float exeTime = ScenarioGenerator.runEvoAvalla(asmFilePath, target, jdkPath, budget, shuffle);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable t) {
				LOG.error("EvoAvalla failed to generate a test suite.\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
				try {
					YamlManager.write(target, asmName, asmFilePath, Float.NaN, LocalDateTime.now().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			LOG.info("Running ATGT...");
			String target = targetFolder + File.separator + ATGT_DIR + File.separator + Utils.formatCounter(atgtCount)
			+ "_" + asmName;
			new File(target).mkdirs();
			try {
				float exeTime = ScenarioGenerator.runATGT(asmFilePath, target);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable t) {
				LOG.error("ATGT failed to generate a test suite.\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
				try {
					YamlManager.write(target, asmName, asmFilePath, Float.NaN, LocalDateTime.now().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			LOG.info("Running Random...");
			String target = targetFolder + File.separator + RANDOM_DIR + File.separator + Utils.formatCounter(randomCount)
			+ "_" + asmName;
			List<Integer> stepList = (List<Integer>) randomCfg.get("step_list");
			new File(target).mkdirs();
			try {
				boolean shuffle = (boolean) randomCfg.get("shuffle");
				float exeTime = ScenarioGenerator.runRandom(asmFilePath, target, stepList, shuffle);
				YamlManager.write(target, asmName, asmFilePath, exeTime, LocalDateTime.now().toString());
			} catch (Throwable t) {
				LOG.error("Random failed to generate a test suite.\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
				try {
					YamlManager.write(target, asmName, asmFilePath, Float.NaN, LocalDateTime.now().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} finally {
				randomCount++;
			}
		}
	}

}
