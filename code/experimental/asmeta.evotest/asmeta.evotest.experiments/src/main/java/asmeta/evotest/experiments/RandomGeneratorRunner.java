package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;

import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.ScenarioGenerator;
import asmeta.evotest.experiments.utils.YamlManager;

public class RandomGeneratorRunner {

	public static final String RANDOM_ATGT_DIR = "random4atgttests";
	public static final String RANDOM_EVOAVALLA_DIR = "random4eatests";

	private static final Logger LOG = Logger.getLogger(RandomGeneratorRunner.class);

	public enum CopiedAlgorithm {
		ATGT, EVO_AVALLA
	}

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
	 *             <li>{@code args[3]} – {@code --shuffle}: optional flag that
	 *             enables shuffled, non-deterministic execution of choose rules
	 *             when present; if omitted, execution is deterministic</li>
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
		if (args.length < 3)
			LOG.error("Missing argument.");
		String resultsDir = args[0];
		String asmExamples = args[1];
		String modelList = args[2];
		boolean shuffle;
		if (args.length == 3) {
			shuffle = false;
		} else {
			if (args[3].equals("--shuffle")) {
				shuffle = true;
			} else {
				LOG.error("Error in second argument: it must be --shuffle or not be present.");
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
		// For each model in the list, run random generation
		for (String line : lines) {
			if (line.isEmpty() || line.startsWith("//"))
				continue;
			String asmPath = Paths.get(asmExamples).resolve(line).toString().replace('\\', '/');
			String asmName = asmPath.substring(asmPath.lastIndexOf('/') + 1).replace(".asm", "");
			LOG.info("\nGenerating test for: " + asmName + ".");
			runRandom(resultsDir, asmPath, asmName, CopiedAlgorithm.ATGT, shuffle);
			runRandom(resultsDir, asmPath, asmName, CopiedAlgorithm.EVO_AVALLA, shuffle);
		}
	}

	private static void runRandom(String resultsDir, String asmPath, String asmName, CopiedAlgorithm alg, boolean shuffle) {
		// Initialize input and output dirs
		String targetDir = resultsDir + File.separator;
		String sourceDir = resultsDir + File.separator;
		if (alg == CopiedAlgorithm.ATGT) {
			LOG.info("Copying number of tests and steps from ATGT");
			targetDir += RANDOM_ATGT_DIR;
			sourceDir += ScenarioGeneratorRunner.ATGT_DIR;
		} else {
			LOG.info("Copying number of tests and steps from EvoAvalla");
			targetDir += RANDOM_EVOAVALLA_DIR;
			sourceDir += ScenarioGeneratorRunner.EVOAVALLA_DIR;
		}
		File[] testSuites = new File(sourceDir).listFiles();
		boolean found = false;
		// Search the test suite corresponding to the given asm
		for (File tsFile : testSuites) {
			String tsDir = tsFile.getPath();
			String tsAsmName = tsDir.substring(tsDir.indexOf("_") + 1);
			String prefix = tsDir.substring(tsDir.lastIndexOf(File.separator) + 1, tsDir.indexOf("_") + 1);
			String fullTargetDir = targetDir + File.separator + prefix + asmName;
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
				break;
			}
		}
		if (!found) {
			LOG.error("For the given specification, the algorithm to be `copied` did not generate any scenario"
					+ ", or random generation resulted in an error.");
			try {
				String fullTargetDir = targetDir + File.separator + "ERR_" + asmName;
				new File(fullTargetDir).mkdirs();
				YamlManager.write(fullTargetDir, asmName, asmPath, Float.NaN, LocalDateTime.now().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
