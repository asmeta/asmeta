package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;

import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.ScenarioGenerator;
import asmeta.evotest.experiments.utils.YamlManager;

public class RandomGeneratorRunner {

	public static final String RANDOM_ATGT_DIR = "random4atgttests";
	public static final String RANDOM_EVOAVALLA_DIR = "random4eatests";

	private static final Logger LOG = Logger.getLogger(RandomGeneratorRunner.class);

	private static final List<String> DEFAULT_SOURCE_DIRS = List.of(
			ScenarioGeneratorRunner.ATGT_DIR,
			ScenarioGeneratorRunner.EVOAVALLA_DIR);

	/**
	 * Entry point for generating scenario with random test generator with number of
	 * tests and steps copied from existing test suites in a given base directory
	 * (containing subfolders {@code atgttests/} and {@code evoavallatests/}).
	 *
	 * @param args command-line arguments
	 *             <ul>
	 *             <li>{@code args[0]} – the base directory with the existing test
	 *             suites</li>
	 *             <li>{@code args[1]} – the base directory with the asm files</li>
	 *             <li>{@code args[2]} – the path to the .txt file with the list of
	 *             asm files for which to generate random test cases</li>
	 *             <li>optional {@code --shuffle} flag, enabling shuffled,
	 *             non-deterministic execution of choose rules</li>
	 *             <li>{@code --folders folder1,folder2,...} – optional comma-separated
	 *             list of source folders instead of the default folders</li>
	 *             </ul>
	 */
	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));
		
		// Validate arguments
		if (args.length < 3) {
			LOG.error("Missing argument.");
			return;
		}
		String resultsDir = args[0];
		String asmExamples = args[1];
		String modelList = args[2];
		boolean shuffle = false;
		List<String> sourceDirs = new ArrayList<>(DEFAULT_SOURCE_DIRS);
		for (int i = 3; i < args.length; i++) {
			switch (args[i]) {
			case "--shuffle":
				shuffle = true;
				break;
			case "--folders":
				if (++i >= args.length || args[i].isBlank()) {
					LOG.error("Missing value for --folders.");
					return;
				}
				sourceDirs = Arrays.stream(args[i].split(","))
						.map(String::trim).filter(folder -> !folder.isEmpty()).toList();
				if (sourceDirs.isEmpty()) {
					LOG.error("The --folders list cannot be empty.");
					return;
				}
				break;
			default:
				LOG.error("Unknown argument: " + args[i]
						+ ". Use --shuffle and/or --folders folder1,folder2.");
				return;
			}
		}
		
		
		// Read the file with the list of models
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(modelList));
		} catch (IOException e) {
			LOG.error("Falied to read " + modelList + ".", e);
			return;
		}
		
		Environment.timeMngt = TimeMngt.auto_increment;
		
		// For each model in the list, run random generation
		for (String line : lines) {
			if (line.isEmpty() || line.startsWith("//"))
				continue;
			String asmPath = Paths.get(asmExamples).resolve(line).toString().replace('\\', '/');
			String asmName = asmPath.substring(asmPath.lastIndexOf('/') + 1).replace(".asm", "");
			LOG.info("\nGenerating test for: " + asmName + ".");
			for (String sourceDir : sourceDirs)
				runRandom(resultsDir, asmPath, asmName, sourceDir, shuffle);
		}
	}

	private static void runRandom(String resultsDir, String asmPath, String asmName, String sourceFolder,
			boolean shuffle) {
		// Initialize input and output dirs
		Path sourcePath = Paths.get(resultsDir).resolve(sourceFolder);
		Path targetPath = Paths.get(resultsDir).resolve(targetFolderFor(sourceFolder));
		LOG.info("Copying number of tests and steps from " + sourceFolder);
		File[] testSuites = sourcePath.toFile().listFiles();
		if (testSuites == null) {
			LOG.error("Source folder not found or not readable: " + sourcePath);
			return;
		}
		boolean found = false;
		// Search the test suite corresponding to the given asm
		for (File tsFile : testSuites) {
			String tsDir = tsFile.getPath();
			String suiteName = tsFile.getName();
			int separator = suiteName.indexOf("_");
			if (separator < 0)
				continue;
			String tsAsmName = suiteName.substring(separator + 1);
			String prefix = suiteName.substring(0, separator + 1);
			String fullTargetDir = targetPath.resolve(prefix + asmName).toString();
			if (tsAsmName.equals(asmName)) {
				try {
					File targetDirFile = new File(fullTargetDir);
					if (targetDirFile.exists()) {
						FileUtils.deleteDirectory(targetDirFile);
						LOG.info("Old scenarios removed.");
					}
					// Collect number of steps in each test and run test generation
					List<Integer> stepList = ScenarioDataCollector.getStatementCount(tsDir, "step");
					LOG.info("Generating " + stepList.size() + " scenarios with steps: " + stepList);
					float execTime = ScenarioGenerator.runRandom(asmPath, fullTargetDir, stepList, shuffle);
					YamlManager.write(fullTargetDir, asmName, asmPath, execTime, LocalDateTime.now().toString());
					found = true;
				} catch (Throwable e) {
					LOG.error("Not found or exception.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
				}
				// In case there is an ASM with the same name as the target, we will still generate the test suites.
				// In a subsequent call, they will be overwritten. TODO: We could use the path from the YAML.
				// break;
			}
		}
		if (!found) {
			LOG.error("For the given specification, the algorithm to be `copied` did not generate any scenario"
					+ ", or random generation resulted in an error.");
			try {
				String fullTargetDir = targetPath.resolve("ERR_" + asmName).toString();
				new File(fullTargetDir).mkdirs();
				YamlManager.write(fullTargetDir, asmName, asmPath, Float.NaN, LocalDateTime.now().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String targetFolderFor(String sourceFolder) {
		if (ScenarioGeneratorRunner.ATGT_DIR.equals(sourceFolder))
			return RANDOM_ATGT_DIR;
		if (ScenarioGeneratorRunner.EVOAVALLA_DIR.equals(sourceFolder))
			return RANDOM_EVOAVALLA_DIR;
		return "random4" + Paths.get(sourceFolder).getFileName();
	}
}
	
