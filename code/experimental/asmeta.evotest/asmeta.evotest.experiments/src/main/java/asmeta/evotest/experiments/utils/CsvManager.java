package asmeta.evotest.experiments.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import asmeta.evotest.experiments.AnalysisRunner.STATUS;

/**
 * Utility class for creating, reading, and appending to a CSV file that stores
 * experimental data.
 */
public class CsvManager {

	public static final List<String> CSV_HEADERS = List.of("asm_path", "asm_name", "n_macro", "n_update", "n_forall",
			"n_branch", "n_rule", "approach", "status", "exec_time_ms", "n_scenarios", "n_step", "n_set", "n_check",
			"macro_coverage", "update_rule_coverage", "forall_rule_coverage", "branch_coverage", "rule_coverage",
			"n_failing_scenarios", "n_val_error_scenarios", "casemutator_score", "chooserulemutator_score",
			"condnegator_score", "condremover_score", "forallmutator_score", "partoseqmutator_score",
			"seqtoparmutator_score", "ruleremover_score");

	private String csvPath;
	private File csvFile;
	private String csvName;

	/**
	 * Creates a manager for a specific CSV file path and initializes the file
	 * (creating or overwriting it) with the header row defined in CSV_HEADERS.
	 *
	 * @param csvPath path to the CSV file to manage
	 * @throws Exception
	 */
	public CsvManager(String csvPath) throws Exception {
		this.csvPath = csvPath;
		setup();
	}

	/**
	 * Reads an entire CSV file into memory.
	 *
	 * @param filePath path to the CSV file
	 * @return a list of rows; each row is a {@code String[]} representing the
	 *         fields in that row
	 * @throws Exception if the file cannot be opened or parsed
	 */
	public static List<String[]> readCsv(String filePath) throws Exception {
		CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();
		CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(parser).build();
		List<String[]> lines = reader.readAll();
		reader.close();
		return lines;
	}

	/**
	 * Creates the managed CSV file and writes the header line defined in
	 * CSV_HEADERS. Existing content at the target path is overwritten.
	 */
	public void setup() throws Exception {
		csvFile = new File(this.csvPath);
		csvName = csvFile.getName();
		FileOutputStream fos = new FileOutputStream(csvFile, false);
		PrintStream ps = new PrintStream(fos);
		ps.println(String.join(",", CSV_HEADERS));
		ps.close();
	}

	/**
	 * Deletes all {@code .csv} files located in the same directory as the managed
	 * CSV file, except for the managed file itself.
	 */
	public void clean() throws Exception {
		String dir = csvFile.getParent();
		Iterator<Path> iterator = Files.list(Path.of(dir)).iterator();
		while (iterator.hasNext()) {
			Path path = iterator.next();
			String fileName = path.getFileName().toString();
			if (fileName.endsWith(".csv") && !fileName.equals(csvName)) {
				Files.delete(path);
			}
		}
	}

	/**
	 * Returns the path of the parent directory that contains the managed CSV file.
	 *
	 * @return the parent directory path as a string
	 */
	public String getParentDir() {
		return csvFile.getParent();
	}

	/**
	 * Appends a single row of experimental data to the managed CSV file. The row is
	 * built by merging values from {@code modelData}, {@code scenarioData}, and
	 * {@code covData}, plus the explicit parameters. Values are written in the
	 * order specified by CSV_HEADERS.
	 *
	 * @param modelData          map with model-level counters (e.g.,
	 *                           {@code n_macro}, {@code n_update}, ...)
	 * @param scenarioData       map with scenario-level counters (e.g.,
	 *                           {@code n_step}, {@code n_set}, {@code n_check})
	 * @param covData            map with coverage metrics (e.g.,
	 *                           {@code macro_coverage}, {@code rule_coverage}, ...)
	 * @param mutationData       map with mutation scores (e.g.,
	 *                           {@code condremover_score},
	 *                           {@code casemutator_score}, ...)
	 * @param asmName            name of the ASM under test
	 * @param asmPath            path of the ASM under test
	 * @param approach           name of the generation or validation approach used
	 * @param status             the status
	 * @param exeTime            execution time in milliseconds
	 * @param nScenario          number of generated scenarios
	 * @param failing            number of failing scenarios (if <0, the value in
	 *                           {@code covData} map with key
	 *                           {@code "n_failing_scenarios"} is kept)
	 * @param errorsInValidation number of scenarios with validation errors
	 * @throws Exception
	 */
	public void writeData(Map<String, String> modelData, Map<String, Integer> scenarioData, Map<String, String> covData,
			Map<String, String> mutationData, String asmName, String asmPath, String approach, STATUS status,
			float exeTime, int nScenario, int failing, int errorsInValidation) throws Exception {
		// construct a row as a Map
		Map<String, String> row = new HashMap<String, String>();
		for (Entry<String, String> entry : modelData.entrySet())
			row.put(entry.getKey(), entry.getValue());
		for (Entry<String, Integer> entry : scenarioData.entrySet())
			row.put(entry.getKey(), entry.getValue().toString());
		for (Entry<String, String> entry : covData.entrySet())
			row.put(entry.getKey(), entry.getValue());
		for (Entry<String, String> entry : mutationData.entrySet())
			row.put(entry.getKey(), entry.getValue());
		row.remove("asm");
		row.put("asm_name", asmName);
		row.put("asm_path", asmPath);
		row.put("approach", approach);
		row.put("status", status.getCsvValue());
		row.put("exec_time_ms", String.valueOf(exeTime));
		row.put("n_scenarios", String.valueOf(nScenario));
		if (failing >= 0)
			row.put("n_failing_scenarios", String.valueOf(failing)); // will
		row.put("n_val_error_scenarios", String.valueOf(errorsInValidation));
		// build the actual row (string array)
		String[] actualRow = new String[CSV_HEADERS.size()];
		for (String key : row.keySet()) {
			int index = CSV_HEADERS.indexOf(key);
			actualRow[index] = row.get(key);
		}
		// write the actual row to csv
		FileOutputStream fos = new FileOutputStream(csvPath, true);
		PrintStream ps = new PrintStream(fos);
		ps.println(String.join(",", actualRow));
		ps.close();
	}

}
