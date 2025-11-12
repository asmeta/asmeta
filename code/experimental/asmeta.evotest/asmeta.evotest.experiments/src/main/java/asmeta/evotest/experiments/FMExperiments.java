package asmeta.evotest.experiments;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleEvalWCov;

import asmeta.evotest.experiments.scenario.ScenarioDataCollector;
import asmeta.evotest.experiments.scenario.generator.ScenarioGenerator;
import asmeta.evotest.experiments.scenario.generator.ScenarioGeneratorsRunner;
import asmeta.evotest.experiments.scenario.validator.ScenarioValidator;
import asmeta.evotest.experiments.utils.CsvManager;
import asmeta.mutation.mutationscore.FMMutationScoreExecutor;

public class FMExperiments {

	private static final String MODEL_LSIT = "data\\fm-short-26-exp\\model_list.txt";
	private static final String SOURCE_DIR = "..\\..\\..\\..\\asm_examples";
	private static final String TARGET_DIR = "data\\fm-short-26-exp\\scenarios";

	private static final String TEMP_CSV = "validation_data_temp.csv";
	private static final String RESULTS_CSV = "data\\fm-short-26-exp\\result.csv";

	private static final List<String> CSV_HEADERS = List.of("asm_path", "status", "total_exec_time_ms",
			"generation_exec_time_ms", "n_scenarios", "n_step", "n_set", "n_check", "n_failing_scenarios",
			"n_val_error_scenarios", "tot_mutants", "killed_mutants");

	private static final int BUDGET = 0; // Time budget in seconds

	public static void main(String[] args) throws IOException {
		// Delete existing results
		File resultsDir = new File(TARGET_DIR);
		if (resultsDir.exists())
			FileUtils.deleteDirectory(resultsDir);
		Files.createDirectories(new File(TARGET_DIR).toPath());
		new File(RESULTS_CSV).delete();

		// Temporary csv that will store validation data
		String validationCsvPath = new File(TEMP_CSV).getAbsolutePath();

		// Write the header to the csv that will contain the final results
		Path resultsCsvPath = Paths.get(RESULTS_CSV);
		String headers = String.join(",", CSV_HEADERS) + System.lineSeparator();
		Files.write(resultsCsvPath, headers.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
				StandardOpenOption.APPEND);

		// Read the file with the list of asms to be processed
		List<String> lines = Files.readAllLines(Paths.get(MODEL_LSIT));

		// For each asm in the list, generate tests, run validation, and run mutation
		int specCounter = 0;
		for (String line : lines) {

			// Skip non valid asms
			if (line.isEmpty() || line.startsWith("//"))
				continue;

			String asmPath = Paths.get(SOURCE_DIR).resolve(line).toString();
			String asmName = new File(asmPath).getName().replace(".asm", "");
			String avallaDir = TARGET_DIR + File.separator + ScenarioGeneratorsRunner.formatCounter(specCounter) + "_"
					+ asmName;

			String status = "OK";
			float executionTime = 0;
			int nScenario = 0;
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
			long elapsedTime = 0;
			// Generate test cases until all mutants are killed or time budget expired
			while (!allKilled && elapsedTime <= BUDGET * 1000) {
				try {
					// Run test generation (iteration*10 tests each with iteration*10 steps)
					executionTime += ScenarioGenerator.runRandom(asmPath, avallaDir, iteration * 10, iteration * 10,
							false);
					// Avoid future overwriting renaming the generated files
					renameAvallaScenarios(avallaDir, iteration);
				} catch (Throwable e) {
					status = "GENERATION_ERROR";
					break;
				}

				// Collect scenario data (total number of n_step, n_set, n_check)
				Map<String, Integer> avallaData = ScenarioDataCollector.collectAvallaData(avallaDir);
				nStep = avallaData.get("n_step");
				nSet = avallaData.get("n_set");
				nCheck = avallaData.get("n_check");
				nScenario = ScenarioDataCollector.getNumberOfScenario(avallaDir);
				if (nScenario == 0) {
					// It is unlikely but still possible that in the next iteration
					// some scenarios will be generated
					endTime = Instant.now();
					elapsedTime = Duration.between(startTime, endTime).toMillis();
					continue;
				}

				// Run validation, data is stored in the temporary CSV
				valErrors = ScenarioValidator.computeCoverageFromAvalla(avallaDir, validationCsvPath, false);
				// Reset rule evaluation state before processing the next suite
				RuleEvalWCov.reset();
				
				if (valErrors > 0) {
					status = "VAL_ERROR";
					break;
				}

				// Read the csv with validation data
				try {
					List<String[]> covRows = CsvManager.readCsv(validationCsvPath);
					if (covRows.size() > 1) { // Should always be true
						// We just need the number of failing scenarios (each row contains the same
						// value)
						int i = Arrays.asList(AsmetaV.HEADERS).indexOf("failing_scenarios");
						failingScenarios = Integer.valueOf(covRows.getLast()[i]);
						if (failingScenarios > 0) {
							status = "FAILING_SCENARIOS_ERROR";
							break;
						}
					}
				} catch (Throwable e) {
					status = "CSV_ERROR";
					break;
				} finally {
					// Clear temp csv file for next iteration
					Files.write(Paths.get(validationCsvPath), new byte[0]);
				}

				// Run mutation
				try {
					FMMutationScoreExecutor mutationExecutor = new FMMutationScoreExecutor();
					String absolutePath = new File(avallaDir).getAbsolutePath();
					Entry<Integer, Integer> mutationResult = mutationExecutor.computeMutationScore(absolutePath);
					killedMutants = mutationResult.getKey();
					totMutants = mutationResult.getValue();
					allKilled = killedMutants == totMutants;
				} catch (Throwable e) {
					e.printStackTrace();
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

			// Delete temporary csv
			new File(validationCsvPath).delete();

			// Write data to the csv file with the final results
			String row = String.join(",", asmPath, status, String.valueOf(elapsedTime), String.valueOf(executionTime),
					String.valueOf(nScenario), String.valueOf(nStep), String.valueOf(nSet), String.valueOf(nCheck),
					String.valueOf(failingScenarios), String.valueOf(valErrors), String.valueOf(totMutants),
					String.valueOf(killedMutants));
			row += System.lineSeparator();
			Files.write(resultsCsvPath, row.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);

			// Next specification
			specCounter++;
		}
	}

	/**
	 * Add a prefix to newly generated avalla scenario (i.e. avalla files which name
	 * does not contains the prefix)
	 * 
	 * @param target    the path to the generated files
	 * @param iteration the number of the iteration that generated the files
	 */
	public static void renameAvallaScenarios(String target, int iteration) {
		File targetDir = new File(target);
		String prefix = ScenarioGeneratorsRunner.formatCounter(iteration) + "iter_";
		File[] files = targetDir.listFiles();
		if (files == null)
			return;
		for (File f : files) {
			String name = f.getName();
			if (f.isFile() && name.endsWith(AsmetaV.SCENARIO_EXTENSION) && !name.contains("iter_")) {
				File newFile = new File(f.getParent(), prefix + f.getName());
				f.renameTo(newFile);
			}
		}
	}

}
