package asmeta.evotest.experiments.scenario.validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.evotest.experiments.utils.CsvManager;

public class ScenarioValidator {

	private static final Logger LOG = Logger.getLogger(ScenarioValidator.class);

	/**
	 * Validate an ASM specification and compute the coverage given a directory
	 * containing .avalla scenarios. If the validation of an avalla results in an
	 * exception, the validation continues.
	 * 
	 * @param scenarioPath the path of the directory where to look for the .avalla
	 *                     files
	 * @param csvPath      where to save the experiments stats (it must be a .csv
	 *                     file)
	 * @param shuffle      if false, run choose rules deterministically (always
	 *                     choose first element)
	 * @return the number of scenarios for which the validation resulted in an error
	 * @throws Exception 
	 */
	public static int computeCoverageFromAvalla(String scenarioPath, String csvPath, boolean shuffle) throws Exception {
		// To avoid to count a scenario twice if the following one is empty, keep
		// track of the previous execution id. For the first scenario, the check is done
		// using the column name
		String previosExecId = "execution_id";
		int failingScenarios = 0;
		int errorsInValidation = 0;
		try (Stream<Path> files = Files.list(Path.of(scenarioPath))) {
			List<Path> filesList = files
					.filter(path -> path.getFileName().toString().endsWith(AsmetaV.SCENARIO_EXTENSION))
					.collect(Collectors.toList());
			for (Path path : filesList) {
				LOG.info("Processing: " + path);
				try {
					AsmetaV.execValidation(path.toString(), true, csvPath, shuffle);
					// Extract the value from the last column of the last row in the CSV to check if
					// the scenario fails
					List<String[]> rows = CsvManager.readCsv(csvPath);
					String[] lastRow = rows.getLast();
					String execId = lastRow[0];
					if (!execId.equals(previosExecId) && !lastRow[lastRow.length - 1].equals("none")) {
						failingScenarios++;
						previosExecId = execId;
					}
				} catch (Throwable t) {
					errorsInValidation++;
					LOG.error("Failed to validate the test: " + path.getFileName() + "\n" + t.getClass().getSimpleName()
							+ ": " + t.getMessage());
				}
			}
			LOG.info("Validated all files in: " + scenarioPath);
			extractLastExecution(csvPath, failingScenarios);
		}
		return errorsInValidation;
	}

	/**
	 * Extract form the input file the rows regarding the last execution (that store
	 * all aggregated data) and overwrite with them the content of the input file
	 * itself. Also put the number of scenarios that fails in the last column of
	 * each row.
	 * 
	 * @param csvPath          the path of the target csv
	 * @param failingScenarios the number of failing scenarios
	 * @throws Exception if any exception occurs when reading the csv
	 */
	private static void extractLastExecution(String csvPath, int failingScenarios) throws Exception {
		// If the csv does not exists (no scenarios generated), create a dummy empty csv
		File csvFile = new File(csvPath);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
			String[] headers = AsmetaV.HEADERS;
			String headersString = String.join(",", headers);
			FileOutputStream fos = new FileOutputStream(csvPath, false);
			PrintStream ps = new PrintStream(fos);
			ps.print(headersString);
			ps.close();
		}
		List<String[]> rows = CsvManager.readCsv(csvPath);
		String extractedContent = String.join(",", rows.getFirst()) + "\n";
		String lastExecId = rows.getLast()[0];
		// Skip if the csv is empty (all empty scenarios or no scenarios)
		if (!lastExecId.equals("execution_id")) {
			for (String[] row : rows) {
				if (row[0].equals(lastExecId)) {
					// if the signature contains commas, surround with double quotes
					if (row[2].contains(","))
						row[2] = "\"" + row[2] + "\"";
					row[row.length - 1] = String.valueOf(failingScenarios);
					extractedContent += String.join(",", row) + "\n";
				}
			}
			File newCsv = new File(csvPath);
			FileOutputStream fos = new FileOutputStream(newCsv, false);
			PrintStream ps = new PrintStream(fos);
			ps.print(extractedContent);
			ps.close();
		}
	}

}
