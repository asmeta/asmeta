package asmeta.evotest.experiments.dataprep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import asmeta.mutation.mutationscore.MutatedScenarioExecutor;

public class CollectMutantNumber {

	private static final String ASM_EXAMPLES = "../../../../asm_examples";

	private static final String LIST = "data/nfm-26-exp/model_list.txt";
	private static final String CSV = "data/nfm-26-exp/data.csv";

	public static final List<String> NEW_CSV_HEADERS = List.of("casemutator_count", "chooserulemutator_count",
			"condnegator_count", "condremover_count", "forallmutator_count", "partoseqmutator_count",
			"seqtoparmutator_count", "ruleremover_count");

	public static void main(String[] args) throws Exception {
		ASMParser.getResultLogger().setLevel(Level.ERROR);
		// Read the file with the list of the models
		List<String> txtLines;
		try {
			txtLines = Files.readAllLines(Paths.get(LIST));
			System.out.println("Loaded " + txtLines.size() + " entries from model list.");
		} catch (IOException e) {
			System.out.println("Falied to load " + LIST + ".\n" + e.getMessage());
			return;
		}

		// Read CSV as text
		List<String> csvLines;
		try {
			csvLines = Files.readAllLines(Paths.get(CSV));
		} catch (IOException e) {
			System.err.println("Failed to read CSV: " + e.getMessage());
			return;
		}

		// Process each ASM
		for (String line : txtLines) {
			if (line.isEmpty() || line.startsWith("//"))
				continue;

			String asmPath = Paths.get(ASM_EXAMPLES).resolve(line).toString().replace('\\', '/');

			File asmFile = new File(asmPath);

			System.out.println("Processing " + asmPath);
			AsmCollection asm = ASMParser.setUpReadAsm(asmFile);
			List<String> mutantCounts = countMutants(asm);

			// Build CSV suffix
			StringBuilder suffix = new StringBuilder();
			for (String count : mutantCounts) {
				suffix.append(",").append(count);
			}

			// Update CSV lines
			for (int i = 1; i < csvLines.size(); i++) {
				String csvLine = csvLines.get(i);
				String csvAsmPath = csvLine.substring(csvLine.indexOf("asm_examples"));
				String modelAsmPath = asmPath.substring(asmPath.indexOf("asm_examples"));
				if (csvAsmPath.startsWith(modelAsmPath)) {
					csvLines.set(i, csvLine + suffix.toString());
				}
			}
		}

		// Change header row
		StringBuilder suffix = new StringBuilder();
		for (String column_name : NEW_CSV_HEADERS) {
			suffix.append(",").append(column_name);
		}
		csvLines.set(0, csvLines.get(0) + suffix.toString());

		// Write updated CSV back to disk
		try {
			System.out.println("Writing CSV: " + CSV);
			Files.write(Paths.get(CSV), csvLines);
		} catch (IOException e) {
			System.err.println("Failed to write CSV: " + e.getMessage());
		}
	}

	private static List<String> countMutants(AsmCollection asm) {
		MutatedScenarioExecutor mutationExecutor = new MutatedScenarioExecutor();
		List<String> counts = new ArrayList<>(NEW_CSV_HEADERS.size());
		HashMap<String, Integer> countByOperator = new HashMap<>();
		try {
			HashMap<String, List<AsmCollection>> allMutations = mutationExecutor.generateMutants(asm);
			// Build a lookup: operatorName -> count
			for (Entry<String, List<AsmCollection>> entry : allMutations.entrySet()) {
				String op = entry.getKey().toLowerCase(); // e.g., "casemutator"
				int n = entry.getValue().size();
				countByOperator.put(op, n);
				System.out.println("\t" + op + ": " + n);
			}
		} catch (Exception e) {
			for (int i = 0; i < NEW_CSV_HEADERS.size(); i++) {
				counts.add("null");
			}
			return counts;
		}
		// Emit counts in the SAME order as NEW_CSV_HEADERS 
		// (outside the try so in case of error the process interrupts)
		for (String header : NEW_CSV_HEADERS) {
			String op = header.replace("_count", "");
			int index = countByOperator.get(op);
			counts.add(String.valueOf(index));
		}
		return counts;
	}

}
