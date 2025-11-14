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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.generator.ScenarioGenerator;
import asmeta.evotest.experiments.scenario.generator.ScenarioGeneratorsRunner;
import asmeta.mutation.mutationscore.FMMutationScoreExecutor;

public class FMExperiments {

	private static final String MODEL_LIST = "data\\fm-short-26-exp\\model_list.txt";
	private static final String TARGET_DIR = "data\\fm-short-26-exp\\scenarios";
	private static final String RESULTS_CSV = "data\\fm-short-26-exp\\result.csv";
	private static final String SCENARIOS_ZIP = "data\\fm-short-26-exp\\scenarios.zip";

	/*	 
	// When building the jar private static final String 
	private static final String MODEL_LIST = "model_list.txt";
	private static final String TARGET_DIR = "scenarios";
	private static final String RESULTS_CSV = "result.csv";
	private static final String SCENARIOS_ZIP = "scenarios.zip";
	*/

	private static final List<String> CSV_HEADERS = List.of("asm_path", "status", "total_exec_time_ms",
			"generation_exec_time_ms", "n_correct_scenarios", "n_step", "n_set", "n_check", "n_failing_scenarios",
			"n_val_error_scenarios", "tot_mutants", "killed_mutants");

	private static final Logger LOG = Logger.getLogger(FMExperiments.class);

	public static void main(String[] args) {
		// Set logging and silence stdout
		LOG.setLevel(Level.INFO);
		Logger.getLogger(Simulator.class).setLevel(Level.ERROR);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ERROR);
		Logger.getLogger(AsmetaV.class).setLevel(Level.ERROR);
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		System.setOut(new PrintStream(OutputStream.nullOutputStream()));

		LOG.info("Starting FMExperiments...");

		// Parse args
		if (args.length < 2) {
			LOG.error("Two arguments are required:\n" 
					+ "\t(i)  the absolute path to the asm_examples folder\n"
					+ "\t(ii) the time budget expressed in seconds\n\n"
					+ "example: java -jar fm-experiments-runner.jar absolute/path/to/asm_examples 60");
			return;
		}
		String sourceDir = args[0];
		int budget;
		try {
			budget = Integer.valueOf(args[1]);
		} catch (NumberFormatException e) {
			LOG.error("Time budget must be an integer (seconds). Provided: " + args[1], e);
			return;
		}
		LOG.info("ASM source directory: " + sourceDir);
		LOG.info("Time budget per model: " + budget + " seconds");

		// Delete existing results
		File scenariosDir = new File(TARGET_DIR);
		Path resultsCsvPath = Paths.get(RESULTS_CSV);
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
			LOG.error("Falied to write delete existing results or writing header to csv.", e);
			return;
		}

		// Read the file with the list of asms to be processed
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(MODEL_LIST));
			LOG.info("Loaded " + lines.size() + " entries from model list.");
		} catch (IOException e) {
			LOG.error("Falied to load " + MODEL_LIST + ".", e);
			return;
		}

		// For Asmeta models that use TimeLibrary, TimeMngt.use_java_time
		// may results in flaky scenarios
		Environment.timeMngt = TimeMngt.auto_increment;

		int specCounter = 0;
		// For each asm in the list: generate tests -> run validation -> run mutation
		for (String line : lines) {
			// Skip commented asms
			if (line.isEmpty() || line.startsWith("//"))
				continue;

			try {
				String asmPath = Paths.get(sourceDir).resolve(line).toString().replace('\\', '/');
				String asmName = new File(asmPath).getName().replace(".asm", "");
				String avallaDir = TARGET_DIR + File.separator + ScenarioGeneratorsRunner.formatCounter(specCounter)
						+ "_" + asmName;

				LOG.info("------------------------------------");
				LOG.info("Processing ASM: " + asmName);

				String status = "OK";
				long executionTime = 0;
				long elapsedTime = 0;
				int nCorrectScenario = 0;
				int nStep = 0;
				int nSet = 0;
				int nCheck = 0;
				int valErrors = 0;
				int failingScenarios = 0;
				int killedMutants = 0;
				int totMutants = 0;

				int iteration = 1;

				boolean allKilled = false;
				Instant startTime = Instant.now();
				Instant endTime = startTime;
				// Generate test cases until all mutants are killed or time budget expired
				while (!allKilled && elapsedTime <= budget * 1000) {
					try {
						// Run test generation
						int n = iteration * 10;
						LOG.info("Generating " + n + " test scenarios with " + n + " steps...");
						executionTime += ScenarioGenerator.runRandom(asmPath, avallaDir, n, n, false);
					} catch (Throwable e) {
						LOG.error("Test generation failed.", e);
						status = "GENERATION_ERROR";
						break;
					}

					// Run validation to identify and delete failing scenarios and scenario that
					// results in validation error
					LOG.info("Validating scenarios...");
					String prefix = ScenarioGeneratorsRunner.formatCounter(iteration) + "iter_";
					File[] files = new File(avallaDir).listFiles();
					for (File f : files) {
						String name = f.getName();
						// Validate and rename only the .avalla files generated during this iteration
						if (f.isFile() && name.endsWith(AsmetaV.SCENARIO_EXTENSION) && !name.contains("iter_")) {
							try {
								List<String> failing = AsmetaV.execValidation(f.toString(), false, false);
								if (failing.size() > 0) {
									LOG.error(name + ": validation failed.");
									failingScenarios++;
									f.delete();
								}
							} catch (Throwable e) {
								LOG.error(name + ": Error in validation.", e);
								valErrors++;
								f.delete();
							}
							// Avoid future overwriting renaming the generated files
							File newFile = new File(f.getParent(), prefix + f.getName());
							f.renameTo(newFile);
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
						if (nCorrectScenario == 0) {
							// It is unlikely but still possible that in the next iteration
							// some scenarios will be generated
							endTime = Instant.now();
							elapsedTime = Duration.between(startTime, endTime).toMillis();
							continue;
						}
					} catch (Throwable e) {
						LOG.error("Falied to collect data for " + asmName + ".", e);
					}

					// Run mutation
					LOG.info("Running mutation...");
					try {
						FMMutationScoreExecutor mutationExecutor = new FMMutationScoreExecutor();
						String absolutePath = new File(avallaDir).getAbsolutePath();
						Entry<Integer, Integer> mutationResult = mutationExecutor.computeMutationScore(absolutePath);
						killedMutants = mutationResult.getKey();
						totMutants = mutationResult.getValue();
						allKilled = totMutants != 0 && killedMutants == totMutants;
					} catch (Throwable e) {
						LOG.error("Mutation analysis failed.", e);
						status = "MUTATION_ERROR";
						break;
					}

					endTime = Instant.now();
					elapsedTime = Duration.between(startTime, endTime).toMillis();
					iteration++;
				}
				// We may exit from the while via break, we need to update elapsedTime
				endTime = Instant.now();
				elapsedTime = Duration.between(startTime, endTime).toMillis();

				// Mutation was never able to generate a mutant
				if (totMutants == 0 && nCorrectScenario != 0)
					status = "MUTATION_ERROR";

				// Write data to the csv file with the final results
				LOG.info("Wrting row to csv...");
				try {
					String row = String.join(",", asmPath, status, String.valueOf(elapsedTime),
							String.valueOf(executionTime), String.valueOf(nCorrectScenario), String.valueOf(nStep),
							String.valueOf(nSet), String.valueOf(nCheck), String.valueOf(failingScenarios),
							String.valueOf(valErrors), String.valueOf(totMutants), String.valueOf(killedMutants));
					row += System.lineSeparator();
					Files.write(resultsCsvPath, row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
							StandardOpenOption.APPEND);
				} catch (Throwable e) {
					LOG.error("Falied to write data for " + asmName + " to csv.", e);
				}
			} catch (Throwable t) {
				LOG.error("Unexpected error while processing ASM line '" + line + "'. Continuing with next ASM.", t);
			}

			// Next specification
			specCounter++;
		}

		// Zip scenario directory (if scenarios.zip already exists, it overwrites it)
		LOG.info("Zipping scenarios directory...");
		Path targetDir = Paths.get(TARGET_DIR);
		Path zipFile = Paths.get(SCENARIOS_ZIP);
		// Delete original scenario directory
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
			LOG.error("Falied while zipping or deleting the generated scenarios.", e);
		}
	}
}
