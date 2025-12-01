package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.main.Simulator;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.ScenarioGenerator;
import asmeta.evotest.experiments.utils.Utils;
import asmeta.mutation.mutationscore.FMMutationScoreExecutor;

public class FMExperiments {
	/*
	private static final String MODEL_LIST = "data\\fm-short-26-exp\\model_list.txt";
	private static final String TARGET_DIR = "data\\fm-short-26-exp\\scenarios";
	private static final String RESULTS_CSV = "data\\fm-short-26-exp\\result.csv";
	private static final String SCENARIOS_ZIP = "data\\fm-short-26-exp\\scenarios.zip";
	*/
	// When building the jar
	private static final String MODEL_LIST = "model_list.txt";
	private static final String TARGET_DIR = "scenarios";
	private static final String RESULTS_CSV = "result.csv";
	private static final String SCENARIOS_ZIP = "scenarios.zip";
	
	private static final List<String> CSV_HEADERS = List.of("asm_path", "iteration", "status", "iteration_exec_time_ms",
			"generation_exec_time_ms", "correct_scenarios", "n_step", "n_set", "n_check", "failing_scenarios",
			"validation_error_scenarios", "tot_mutants", "n_killed_mutants_cumulativa",
			"idx_killed_mutants_cumulative");

	private static final int MAX_ITERATIONS = 20;
	private static final int MAX_ITERATIONS_WITHOUT_SCENARIOS = 5;

	private static final Logger LOG = Logger.getLogger(FMExperiments.class);

	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		Logger.getLogger(AsmetaV.class).setLevel(Level.ERROR);
		Logger.getLogger(FMMutationScoreExecutor.class).setLevel(Level.INFO);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));

		LOG.info("Starting FMExperiments...");

		// Parse args
		if (args.length < 2) {
			LOG.error("Two arguments are required:\n"
					+ "\t(i)   the absolute path to the asm_examples folder or to a single .asm file\n"
					+ "\t(ii)  the time budget expressed in seconds\n"
					+ "\t(iii) (optional) the numerical prefix of the directory that stores the generated scenarios (only for single .asm file)\n\n"
					+ "example:             java -jar fm-experiments-runner.jar absolute/path/to/asm_examples 60\n"
					+ "example single file: java -jar fm-experiments-runner.jar absolute/path/to/asm_examples/model.asm 60 1");
			return;
		}
		String source = args[0];
		int budget;
		try {
			budget = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			LOG.error("Time budget must be an integer (seconds). Provided: " + args[1]);
			return;
		}
		int numericalPrefix = 0;
		if (args.length == 3) {
			try {
				numericalPrefix = Integer.valueOf(args[2]);
			} catch (NumberFormatException e) {
				LOG.error("Prefix must be an integer (numerical prefix). Provided: " + args[2]);
				return;
			}
		}
		LOG.info("ASM source: " + source);
		LOG.info("Time budget per model: " + budget + " seconds");

		// For Asmeta models that use TimeLibrary, TimeMngt.use_java_time
		// may results in flaky scenarios
		Environment.timeMngt = TimeMngt.auto_increment;

		File sourceFile = new File(source);
		File scenariosDir = new File(TARGET_DIR);
		Path resultsCsvPath = Paths.get(RESULTS_CSV);
		if (sourceFile.isDirectory()) {
			// Delete existing results
			try {
				if (scenariosDir.exists()) {
					FileUtils.deleteDirectory(scenariosDir);
				}
				Files.createDirectories(new File(TARGET_DIR).toPath());
				new File(RESULTS_CSV).delete();
				LOG.info("Old results removed.");
				// Write the header to the csv that will contain the final results
				String headers = String.join(",", CSV_HEADERS) + System.lineSeparator();
				Files.write(resultsCsvPath, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			} catch (IOException e) {
				LOG.error("Falied to delete existing results or writing header to csv.", e);
				return;
			}
			// Read the file with the list of asms to be processed
			List<String> lines;
			try {
				lines = Files.readAllLines(Paths.get(MODEL_LIST));
			} catch (IOException e) {
				LOG.error("Falied to load " + MODEL_LIST + ".", e);
				return;
			}
			// For each asm in the list: generate tests -> run validation -> run mutation
			int specCounter = 0;
			for (String line : lines) {
				// Skip commented asms
				if (!line.isEmpty() && !line.startsWith("//")) {
					try {
						String asmPath = Paths.get(source).resolve(line).toString().replace('\\', '/');
						evaluateAsm(budget, resultsCsvPath, specCounter, asmPath);
					} catch (Throwable t) {
						LOG.error(
								"Unexpected error while processing ASM line '" + line + "'. Continuing with next ASM.\n"
										+ t.getClass().getSimpleName() + ": " + t.getMessage());
					}
					// Next specification
					specCounter++;
				}
			}
			Path targetDir = Paths.get(TARGET_DIR);
			Path zipFile = Paths.get(SCENARIOS_ZIP);
			cleanUpAndZip(targetDir, zipFile);
		} else {
			try {
				// Write the header to the csv that will contain the final results (only if it
				// is empty)
				File resultsCsvFile = new File(resultsCsvPath.toString());
				if (resultsCsvFile.exists() && resultsCsvFile.length() == 0) {
					String headers = String.join(",", CSV_HEADERS) + System.lineSeparator();
					Files.write(resultsCsvPath, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
							StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				LOG.error("Falied to write header to csv.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
				return;
			}
			String asmName = new File(source).getName().replace(".asm", "");
			String prefix = Utils.formatCounter(numericalPrefix);
			Path targetDir = Paths.get(TARGET_DIR, prefix + "_" + asmName);
			Path zipFile = Paths.get(TARGET_DIR, prefix + "_" + asmName + ".zip");
			try {
				evaluateAsm(budget, resultsCsvPath, numericalPrefix, source);
			} catch (Throwable t) {
				LOG.error("Unexpected error while processing ASM '" + source + "'.\n" + t.getClass().getSimpleName()
						+ ": " + t.getMessage());
			}
			cleanUpAndZip(targetDir, zipFile);
		}
	}

	/**
	 * Zip generated scenarios and then delete them, If the .zip file already
	 * exists, it is overwritten
	 * 
	 * @param targetDir
	 * @param zipFile
	 */
	protected static void cleanUpAndZip(Path targetDir, Path zipFile) {
		LOG.info("Zipping scenarios directory...");
		try {
			try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipFile))) {
				Files.walk(targetDir).filter(path -> !Files.isDirectory(path)).forEach(path -> {
					ZipEntry zipEntry = new ZipEntry(targetDir.relativize(path).toString());
					try {
						zs.putNextEntry(zipEntry);
						Files.copy(path, zs);
						zs.closeEntry();
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
				});
			}
			// Delete original scenario directory
			Files.walk(targetDir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
		} catch (Throwable e) {
			LOG.error("Falied while zipping or deleting the generated scenarios.\n" + e.getClass().getSimpleName()
					+ ": " + e.getMessage());
		}
	}

	/**
	 * Evaluate a single asm file: generated scenarios, run validation, and run
	 * mutation on correct and passing scenarios
	 * 
	 * @param budget         the time budget (seconds)
	 * @param resultsCsvPath the path of the csv with the final results
	 * @param numercalPrefix the prefix of the folder in which generated scenarios
	 *                       are stored
	 * @param asmPath        the string of the path to the target asm
	 */
	private static void evaluateAsm(int budget, Path resultsCsvPath, int numercalPrefix, String asmPath) {
		String asmName = new File(asmPath).getName().replace(".asm", "");
		String prefix = Utils.formatCounter(numercalPrefix);
		String avallaBaseDir = TARGET_DIR + File.separator + prefix + "_" + asmName;

		LOG.info("------------------------------------");
		LOG.info("Processing ASM: " + asmName);

		// Generate mutants
		LOG.info("Mutating the ASM...");
		FMMutationScoreExecutor mutationExecutor = new FMMutationScoreExecutor();
		List<AsmCollection> mutants;
		try {
			mutants = mutationExecutor.generateMutants(asmPath);
		} catch (Throwable e) {
			throw new RuntimeException(
					"Mutation analysis failed.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
		}
		LOG.info("Generated " + mutants.size() + " mutants");

		String status = "OK";
		long totalExecutionTime = 0;
		long totalElapsedTime = 0;
		int totalCorrectScenario = 0;
		int totalStep = 0;
		int totalSet = 0;
		int totalCheck = 0;
		int totalValErrors = 0;
		int totalFailingScenarios = 0;

		int iteration = 1;

		// The set with the mutants killed so far
		Set<Integer> allKilledMutants = new HashSet<>();
		boolean allKilled = false;
		// Generate test cases until all mutants are killed or time budget expired or
		// max iterations reached or the max iteration with no file generated is reached
		while (!allKilled && totalElapsedTime <= budget * 1000 && iteration <= MAX_ITERATIONS
				&& !(totalCorrectScenario == 0 && iteration > MAX_ITERATIONS_WITHOUT_SCENARIOS)) {
			String avallaDir = avallaBaseDir + File.separator + "iteration"
					+ Utils.formatCounter(iteration);

			Instant startTime = Instant.now();
			Instant endTime;

			long executionTime = 0;
			long elapsedTime = 0;
			int nCorrectScenario = 0;
			int nStep = 0;
			int nSet = 0;
			int nCheck = 0;
			int valErrors = 0;
			int failingScenarios = 0;

			try {
				// Run test generation
				int n = iteration * 10;
				LOG.info("Generating " + n + " test scenarios with " + n + " steps...");
				executionTime = (long) ScenarioGenerator.runRandom(asmPath, avallaDir, n, n, false);
				totalExecutionTime += executionTime;
				int nGeneratedScenario = ScenarioDataCollector.getNumberOfScenario(avallaDir);
				LOG.info(nGeneratedScenario + " test generated...");
			} catch (Throwable e) {
				LOG.error("Test generation failed.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
				status = "GENERATION_ERROR";
				break;
			}

			// Run validation to identify and delete failing scenarios and scenario that
			// results in validation error
			LOG.info("Validating scenarios...");
			File[] files = new File(avallaDir).listFiles();
			for (File f : files) {
				String name = f.getName();
				// Validate and rename only the .avalla files generated during this iteration
				if (f.isFile() && name.endsWith(AsmetaV.SCENARIO_EXTENSION) && !name.contains("iter_")) {
					try {
						List<String> failing = AsmetaV.execValidation(f.toString(), false, false);
						if (failing.size() > 0) {
							LOG.error(name + ": validation failed.");
							totalFailingScenarios++;
							failingScenarios++;
							f.delete();
						}
					} catch (Throwable e) {
						LOG.error(name + ": Error in validation.\n" + e.getClass().getSimpleName() + ": "
								+ e.getMessage());
						totalValErrors++;
						valErrors++;
						f.delete();
					}
				}
			}

			// Collect scenario data (total number of step, set and check)
			LOG.info("Collecting scenarios data...");
			try {
				Map<String, Integer> avallaData = ScenarioDataCollector.collectAvallaData(avallaDir);
				nStep = avallaData.get("n_step");
				nSet = avallaData.get("n_set");
				nCheck = avallaData.get("n_check");
				nCorrectScenario = ScenarioDataCollector.getNumberOfScenario(avallaDir);
				totalStep += nStep;
				totalSet += nSet;
				totalCheck += nCheck;
				totalCorrectScenario += nCorrectScenario;
			} catch (Throwable e) {
				LOG.error("Falied to collect data for " + asmName + ".\n" + e.getClass().getSimpleName() + ": "
						+ e.getMessage());
			}

			if (nCorrectScenario != 0) {
				// Run mutation
				LOG.info("Running mutation...");
				try {
					String absolutePath = new File(avallaDir).getAbsolutePath();
					mutationExecutor.computeMutationScore(mutants, allKilledMutants, absolutePath);
					LOG.info(allKilledMutants.size() + " mutants killed so far");
					allKilled = mutants.size() != 0 && allKilledMutants.size() == mutants.size();
				} catch (Throwable e) {
					LOG.error("Mutation analysis failed.\n" + e.getClass().getSimpleName() + ": " + e.getMessage());
					status = "MUTATION_ERROR";
					break;
				}
			} else {
				LOG.info("No scenario generated at this iteration, skipping mutation...");
			}

			endTime = Instant.now();
			elapsedTime = Duration.between(startTime, endTime).toMillis();
			totalElapsedTime += elapsedTime;

			// Write data to the csv file with the final results
			LOG.info("Writing row to csv...");
			try {
				String killedMutantsList = allKilledMutants.stream().map(String::valueOf)
						.collect(Collectors.joining(";"));
				String row = String.join(",", asmPath, "IT" + iteration, status, String.valueOf(elapsedTime),
						String.valueOf(executionTime), String.valueOf(nCorrectScenario), String.valueOf(nStep),
						String.valueOf(nSet), String.valueOf(nCheck), String.valueOf(failingScenarios),
						String.valueOf(valErrors), String.valueOf(mutants.size()),
						String.valueOf(allKilledMutants.size()), killedMutantsList);
				row += System.lineSeparator();
				Files.write(resultsCsvPath, row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			} catch (Throwable e) {
				LOG.error("Falied to write data for " + asmName + " to csv.\n" + e.getClass().getSimpleName() + ": "
						+ e.getMessage());
			}
			iteration++;
		}

		// Write data to the csv file with the final results
		LOG.info("Wrting aggregate row to csv...");
		try {
			String killedMutantsList = allKilledMutants.stream().map(String::valueOf).collect(Collectors.joining(";"));
			String row = String.join(",", asmPath, "ALL", status, String.valueOf(totalElapsedTime),
					String.valueOf(totalExecutionTime), String.valueOf(totalCorrectScenario), String.valueOf(totalStep),
					String.valueOf(totalSet), String.valueOf(totalCheck), String.valueOf(totalFailingScenarios),
					String.valueOf(totalValErrors), String.valueOf(mutants.size()),
					String.valueOf(allKilledMutants.size()), killedMutantsList);
			row += System.lineSeparator();
			Files.write(resultsCsvPath, row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (Throwable e) {
			LOG.error("Falied to write data for " + asmName + " to csv.\n" + e.getClass().getSimpleName() + ": "
					+ e.getMessage());
		}
	}
}
