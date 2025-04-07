package asmeta.evotest.experiments.main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;

class CsvProcessor {

	private static int exec_id = 0;

	/**
	 * aggregate all the executions of the temp csv file and save them to the out
	 * csv file
	 * 
	 * @param tempCsv path pf the input csv to process
	 * @param outCsv path of the output csv
	 */
	static void agregateTempCsv(String tempCsv, String outCsv) {

		try {
			List<String[]> rows = readCsv(tempCsv);
			List<String[]> processedData = processCsv(rows);
			appendToCsv(outCsv, processedData);
			System.out.println("Csv file processed: " + outCsv);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			exec_id += 1;
		}
	}

	/**
	 * Reads a CSV file and returns a list of rows.
	 *
	 * @param filePath The path to the CSV file.
	 * @return A list of string arrays, where each array represents a row in the
	 *         CSV.
	 * @throws IOException  If an error occurs while reading the file.
	 * @throws CsvException
	 */
	private static List<String[]> readCsv(String filePath) throws IOException, CsvException {
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			return reader.readAll();
		}
	}

	/**
	 * Processes the CSV data by modifying execution_id and aggregating duplicate
	 * rows.
	 *
	 * @param rows List of CSV rows.
	 * @return A new list of processed CSV rows.
	 */
	private static List<String[]> processCsv(List<String[]> rows) {
		if (rows.isEmpty())
			return Collections.emptyList();

		Map<String, AggregatedRow> groupedData = new HashMap<>();

		for (int i = 1; i < rows.size(); i++) { // Skip the header row
			String[] row = rows.get(i);
			if (row.length < 9)
				continue; // Ignore invalid rows

			// Modify execution_id: "exec_" + asm_name (column 1)
			row[0] = "exec_" + row[1] + "_" + exec_id;

			// Key for grouping rows (execution_id, asm_name, rule_signature)
			String key = row[0] + "_" + row[1] + "_" + row[2];

			// If the key exists, update the values
			groupedData.putIfAbsent(key, new AggregatedRow(row));
			groupedData.get(key).update(row);
		}

		// Convert the map to a list of rows
		List<String[]> processedData = new ArrayList<>();
		for (AggregatedRow aggregatedRow : groupedData.values()) {
			processedData.add(aggregatedRow.toArray());
		}

		return processedData;
	}

	/**
	 * Appends the processed rows to an existing CSV file without overwriting.
	 *
	 * @param filePath The path to the output CSV file.
	 * @param data     The list of processed CSV rows to be appended.
	 * @throws IOException If an error occurs while writing to the file.
	 */
	private static void appendToCsv(String filePath, List<String[]> data) throws IOException {
		boolean fileExists = Files.exists(Paths.get(filePath));

		try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(filePath, true)) // Append mode
				/*.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER) // Disable quotes*/
				.build()) {

			if (!fileExists) {
				// If the file does not exist, write the header first
				String[] header = { "execution_id", "asm_name", "rule_signature", "tot_conditional_rules",
						"covered_true_conditional_rules", "covered_false_conditional_rules", "tot_update_rules",
						"covered_update_rules", "failing_scenarios" };
				writer.writeNext(header, false); // Write header without quotes
			}

			writer.writeAll(data, false); // Write data without header and without quotes
		}
	}

	/**
	 * A helper class to aggregate data for duplicate rows.
	 */
	static class AggregatedRow {
		String executionId, asmName, ruleSignature;
		int[] maxValues = new int[5]; // For columns 3, 4, 5, 6, 7
		Set<String> failingScenarios = new HashSet<>();

		/**
		 * Constructor that initializes an aggregated row with the first row's values.
		 *
		 * @param row The initial row data.
		 */
		AggregatedRow(String[] row) {
			this.executionId = row[0];
			this.asmName = row[1];
			this.ruleSignature = row[2];
			for (int i = 0; i < 5; i++) {
				maxValues[i] = Integer.parseInt(row[i + 3]);
			}
			addFailingScenario(row[8]);
		}

		/**
		 * Updates the aggregated values with a new row.
		 *
		 * @param row The new row data.
		 */
		void update(String[] row) {
			for (int i = 0; i < 5; i++) {
				maxValues[i] = Math.max(maxValues[i], Integer.parseInt(row[i + 3]));
			}
			addFailingScenario(row[8]);
		}

		/**
		 * Adds a failing scenario to the set, ignoring "none" values.
		 *
		 * @param scenario The failing scenario to add.
		 */
		void addFailingScenario(String scenario) {
			if (!"none".equalsIgnoreCase(scenario)) {
				failingScenarios.add(scenario);
			}
		}

		/**
		 * Converts the aggregated row into a string array for CSV writing.
		 *
		 * @return A string array representing the aggregated row.
		 */
		String[] toArray() {
			String[] row = new String[9];
			row[0] = executionId;
			row[1] = asmName;
			row[2] = ruleSignature;
			for (int i = 0; i < 5; i++) {
				row[i + 3] = String.valueOf(maxValues[i]);
			}
			row[8] = failingScenarios.isEmpty() ? "none" : String.join(";", failingScenarios);
			return row;
		}
	}
}