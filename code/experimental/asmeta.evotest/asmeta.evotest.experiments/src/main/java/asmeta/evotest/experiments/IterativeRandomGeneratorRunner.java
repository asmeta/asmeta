package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;
import org.asmeta.xt.validator.SimulatorWCov;

import asmeta.AsmCollection;
import asmeta.evotest.experiments.model.ModelDataCollector;
import asmeta.evotest.experiments.scenario.ScenarioGenerator;
import asmeta.evotest.experiments.scenario.ScenarioValidator;
import asmeta.evotest.experiments.scenario.ValidationDataCollector;
import asmeta.evotest.experiments.utils.Utils;
import asmeta.evotest.experiments.utils.YamlManager;
import asmeta.mutation.mutationscore.MutatedScenarioExecutor;

public class IterativeRandomGeneratorRunner {

	public static final String ITERATIVE_RANDOM_DIR = "iterativerandomtests";

	private static int count = 0;

	private static final int MAX_ITERATIONS = 20;

	private static final Logger LOG = Logger.getLogger(IterativeRandomGeneratorRunner.class);

	/**
	 * Entry point for generating scenario with random test generator incrementing
	 * the number of tests and steps at each iteration until coverage and mutation
	 * does not increase
	 *
	 * @param args command-line arguments
	 *             <ul>
	 *             <li>{@code args[0]} – the target folder</li>
	 *             <li>{@code args[1]} – the base directory with the asm files</li>
	 *             <li>{@code args[2]} – the path to the .txt file with the list of
	 *             asm files for which to generate random test cases</li>
	 *             <li>{@code args[3]} – time budget expressed in seconds</li>
	 *             <li>{@code args[4]} – {@code --shuffle}: optional flag that
	 *             enables shuffled, non-deterministic execution of choose rules
	 *             when present; if omitted, execution is deterministic</li>
	 * 
	 *             </ul>
	 */
	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.ERROR);
		Logger.getLogger(SimulatorWCov.class).setLevel(Level.ERROR);
		Logger.getLogger(AsmetaV.class).setLevel(Level.ERROR);
		Logger.getLogger(MutatedScenarioExecutor.class).setLevel(Level.ERROR);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));

		LOG.info("Starting Iterative Random Generation...");

		// Validate arguments
		if (args.length < 4)
			LOG.error("Missing argument.");
		String targetFolder = args[0];
		String asmExamples = args[1];
		String modelList = args[2];
		int budget;
		try {
			budget = Integer.valueOf(args[3]);
		} catch (NumberFormatException e) {
			LOG.error("Time budget must be an integer (seconds). Provided: " + args[1]);
			return;
		}
		boolean shuffle;
		if (args.length == 4) {
			shuffle = false;
		} else {
			if (args[1].equals("--shuffle")) {
				shuffle = true;
			} else {
				LOG.error("Error in second argument: it must be --shuffle or not be present.");
				return;
			}
		}

		// delete existing target folder and recreate the empty directory
		try {
			File targetFolderFile = new File(targetFolder + File.separator + ITERATIVE_RANDOM_DIR);
			if (targetFolderFile.exists()) {
				FileUtils.deleteDirectory(targetFolderFile);
				LOG.info("Old scenarios removed.");
			}
			Files.createDirectories(new File(targetFolder).toPath());
		} catch (Throwable t) {
			LOG.error("Falied to delete existing results.", t);
			return;
		}

		// For Asmeta models that use TimeLibrary, TimeMngt.use_java_time
		// may results in flaky scenarios
		Environment.timeMngt = TimeMngt.auto_increment;

		// Read the file with the list of models
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(modelList));
		} catch (IOException e) {
			LOG.error("Falied to read " + modelList + ".", e);
			return;
		}

		// For each model in the list, run random generation iteratively
		for (String line : lines) {
			if (line.isEmpty() || line.startsWith("//"))
				continue;
			String asmPath = Paths.get(asmExamples).resolve(line).toString().replace('\\', '/');
			String asmName = asmPath.substring(asmPath.lastIndexOf('/') + 1).replace(".asm", "");
			LOG.info("\nGenerating test for: " + asmName + ".");
			String avallaBaseDir = targetFolder + File.separator + ITERATIVE_RANDOM_DIR + File.separator
					+ Utils.formatCounter(count) + "_" + asmName;
			runIterativeRandom(avallaBaseDir, asmPath, asmName, budget, shuffle);
			count++;
		}
		LOG.info("\n\nGeneration completed.");
	}

	/**
	 * Run random generation for a single asm file iteratively: generated scenarios,
	 * run validation, and run mutation on correct and passing scenarios. Repeat
	 * until no improvements in coverage and mutation scores
	 * 
	 * @param avallaBaseDir the string of the path where to save the avalla files
	 * @param asmPath       the string of the path to the target asm
	 * @param asmName       the name of the target asm
	 * @param budget        the time budget (seconds)
	 * @param shuffle       if true, enables shuffled, non-deterministic execution
	 *                      of choose rules; if false, execution is deterministic
	 */
	private static void runIterativeRandom(String avallaBaseDir, String asmPath, String asmName, int budget,
			boolean shuffle) {
		LOG.info("------------------------------------");
		LOG.info("Processing ASM: " + asmName);

		new File(avallaBaseDir).mkdirs();
		// Parse the model
		AsmCollection asm;
		try {
			LOG.info("Parsing " + asmName + "...");
			asm = ASMParser.setUpReadAsm(new File(asmPath));
		} catch (Throwable t) {
			LOG.error("Error while parsing " + asmName + ".\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
			try {
				YamlManager.write(avallaBaseDir, asmName, asmPath, Float.NaN, LocalDateTime.now().toString());
			} catch (Throwable e) {
				LOG.error("Error while writing on yaml file.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
			}
			return;
		}

		// Generate mutants
		MutatedScenarioExecutor mutationExecutor;
		HashMap<String, List<AsmCollection>> allMutantions;
		try {
			LOG.info("Generating mutants...");
			mutationExecutor = new MutatedScenarioExecutor();
			allMutantions = mutationExecutor.generateMutants(asm);
		} catch (Throwable t) {
			LOG.error("Error while generating mutants " + asmName + ".\n" + t.getClass().getSimpleName() + ": "
					+ t.getMessage());
			try {
				YamlManager.write(avallaBaseDir, asmName, asmPath, Float.NaN, LocalDateTime.now().toString());
			} catch (Throwable e) {
				LOG.error("Error while writing on yaml file.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
			}
			return;
		}

		float totalExecutionTime = 0;
		float totalElapsedTime = 0;

		Map<String, String> prevCovData = new HashMap<>();
		Map<String, String> prevMutationData = new HashMap<>();

		int iteration = 1;

		// Generate test cases until time budget or max iterations reached or test suite
		// performance (cov and mutation) did not increase
		while (true) {
			if (totalElapsedTime > budget * 1000) {
				LOG.info("Time budget reached (" + totalElapsedTime / 1000 + "s elapsed).");
				break;
			}
			if (iteration > MAX_ITERATIONS) {
				LOG.info("Max iterations (" + MAX_ITERATIONS + ") reached.");
				break;
			}
			Instant startTime = Instant.now();
			Instant endTime;
			// Run test generation
			try {
				int n = iteration * 10;
				LOG.info("Generating " + n + " test scenarios with " + n + " steps...");
				float executionTime = ScenarioGenerator.runRandom(asmPath, avallaBaseDir, n, n, shuffle);
				totalExecutionTime += executionTime;
			} catch (Throwable e) {
				LOG.error("Test generation failed.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
				totalExecutionTime = Float.NaN;
				break;
			}

			// Run validation to identify and delete failing scenarios and scenario that
			// results in validation error, also rename the .avalla files generated during
			// this iteration
			LOG.info("Validating scenarios...");
			File[] files = new File(avallaBaseDir).listFiles();
			int correctGenScenarios = 0;
			for (File f : files) {
				String name = f.getName();
				if (f.isFile() && name.endsWith(AsmetaV.SCENARIO_EXTENSION) && !name.contains("iter_")) {
					try {
						List<String> failing = AsmetaV.execValidation(f.toString(), false, false);
						if (failing.size() > 0) {
							LOG.error(name + ": validation failed.");
							f.delete();
						} else {
							Path oldScenario = f.toPath();
							Path newScenario = Path.of(f.getAbsolutePath().replace(AsmetaV.SCENARIO_EXTENSION,
									"iter_" + iteration + AsmetaV.SCENARIO_EXTENSION));
							Files.move(oldScenario, newScenario, StandardCopyOption.REPLACE_EXISTING);
							correctGenScenarios++;
						}
					} catch (Throwable e) {
						LOG.error(name + ": Error in validation.\n" + e.getClass().getSimpleName() + ": "
								+ e.getMessage());
						f.delete();
					}
				}
			}

			LOG.info(correctGenScenarios + " correct test generated...");

			// If at least one correct scenario is generated, run coverage analysis and
			// mutation analysis and check if there are improvements
			if (correctGenScenarios > 0) {
				Map<String, String> modelData = new HashMap<>();
				Map<String, String> covData = new HashMap<>();
				Map<String, String> mutationData = new HashMap<>();

				// Collect model data
				try {
					LOG.info("Collecting model data...");
					modelData = ModelDataCollector.collectModelData(asm);
				} catch (Throwable t) {
					LOG.error("Error while collecting model data.\n" + t.getClass().getSimpleName() + ": "
							+ t.getMessage());
					totalExecutionTime = Float.NaN;
					break;
				}

				// Run validation to compute coverage, data is stored in a temporary CSV
				String tempCsvPath = avallaBaseDir + File.separator + "temp.csv";
				try {
					LOG.info("Computing coverage (temporary results will be stored in temp.csv)...");
					ScenarioValidator.computeCoverageFromAvalla(avallaBaseDir, tempCsvPath, shuffle);
				} catch (Throwable t) {
					LOG.error(
							"Error while computing coverage.\n" + t.getClass().getSimpleName() + ": " + t.getMessage());
					new File(tempCsvPath).delete();
					totalExecutionTime = Float.NaN;
					break;
				} finally {
					// Reset rule evaluation state before the next suite
					RuleEvalWCov.reset();
				}

				// Aggregate coverage metrics from the temporary CSV
				try {
					LOG.info("Aggregating validation data...");
					covData = ValidationDataCollector.collectCoverageData(tempCsvPath, asmName, modelData);
				} catch (Throwable t) {
					LOG.error("Error while aggregating validation data.\n" + t.getClass().getSimpleName() + ": "
							+ t.getMessage());
					totalExecutionTime = Float.NaN;
					break;
				} finally {
					new File(tempCsvPath).delete();
				}

				// Run mutation
				try {
					LOG.info("Computing mutation scores...");
					for (Entry<String, List<AsmCollection>> mutation : allMutantions.entrySet()) {
						LOG.info("Mutant operator: " + mutation.getKey());
						String mutantionName = mutation.getKey().toLowerCase() + "_score";
						List<AsmCollection> mutatedAsms = mutation.getValue();
						int nMutants = mutatedAsms.size();
						if (nMutants > 0) {
							Set<Integer> killed = mutationExecutor.computeMutationScore(mutatedAsms, avallaBaseDir);
							float mutationScore = ((float) killed.size()) / nMutants;
							mutationData.put(mutantionName, String.valueOf(mutationScore));
						} else {
							mutationData.put(mutantionName, "NaN");
						}
					}
				} catch (Throwable t) {
					LOG.error("Error while running the mutation.\n" + t.getClass().getSimpleName() + ": "
							+ t.getMessage());
					totalExecutionTime = Float.NaN;
					break;
				}

				// Check if there is at least one improvement in coverage or mutation
				if (iteration > 1 && prevCovData.equals(covData) && prevMutationData.equals(mutationData)) {
					LOG.info("No improvement in coverage and mutation.");
					break;
				}
				prevCovData = covData;
				prevMutationData = mutationData;
			} else if (iteration > 1) {
				LOG.info("No improvement in coverage and mutation.");
				break;
			}
			endTime = Instant.now();
			totalElapsedTime += Duration.between(startTime, endTime).toMillis();
			iteration++;
		}
		try {
			YamlManager.write(avallaBaseDir, asmName, asmPath, totalExecutionTime, LocalDateTime.now().toString());
		} catch (Throwable e) {
			LOG.error("Error while writing on yaml file.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}
}
