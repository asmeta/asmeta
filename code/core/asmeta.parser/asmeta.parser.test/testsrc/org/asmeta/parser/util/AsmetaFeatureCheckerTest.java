package org.asmeta.parser.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.parser.ParseException;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Asm;
import asmeta.structure.ImportClause;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl;

public class AsmetaFeatureCheckerTest {

	private static final Path EXPERIMENT_DIR = Path.of(
			"../../../../code/experimental/asmeta.evotest/asmeta.evotest.experiments/data/icst-27-exp");
	private static final Path MODEL_LIST_FILE = EXPERIMENT_DIR.resolve("model_list.txt");

	Map<String, Predicate<EObject>> featurePredicates = Map.ofEntries(
			Map.entry("IntegerDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof IntegerDomain)),
			Map.entry("BooleanDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof BooleanDomain)),
			Map.entry("EnumerativeDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof EnumTd)),
			Map.entry("String-Monitored", x -> ((x instanceof MonitoredFunction) && ((Function) x).getCodomain() instanceof StringDomain)),
			Map.entry("String-Controlled", x -> ((x instanceof ControlledFunction) && ((Function) x).getCodomain() instanceof StringDomain)),
			Map.entry("AnyDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof AnyDomain)),
			Map.entry("RealDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof RealDomain)),
			Map.entry("AgentDomain", x -> (x instanceof AgentDomain || (x instanceof ConcreteDomain cd && cd.getTypeDomain() instanceof AgentDomain))),
			Map.entry("StructuredDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof StructuredTd)),
			Map.entry("LetRule", x -> (x instanceof LetRule)),
			Map.entry("ExtendRule", x -> (x instanceof ExtendRule)),
			Map.entry("ForallRule", x -> (x instanceof ForallRule)),
			Map.entry("MacroRuleWithParams", x -> (x instanceof MacroCallRule && !((MacroCallRule) x).getParameters().isEmpty())),
			Map.entry("Invariant", x -> (x instanceof Invariant)),
			Map.entry("ChooseRule", x -> (x instanceof ChooseRule)),
			Map.entry("SubsetOfInt", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof ConcreteDomain)
					&& (((ConcreteDomain) ((Function) x).getCodomain()).getTypeDomain() instanceof IntegerDomain)),
			Map.entry("INVAR", x -> (x instanceof InvarConstraint)),
			Map.entry("Modules", x -> (x instanceof ImportClause ic && !AsmetaParserUtility.isAsmetaLibrary(ic.getModuleName()))));

	@BeforeAll
	static void setUpLogger(){
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
	}

	@AfterAll
	static void restoreLogger(){
		// remove all appenders to avoid bloating the console
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
	}

	@Test void visitAsm2() throws Exception {
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof MonitoredFunction));
		File f = new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		//System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		spr = new AsmetaFeatureChecker(x -> (x instanceof OutFunction));
		// it has no monitored functions
		assertFalse(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
	}

	@Test void let() throws Exception {
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof LetRuleImpl));
		File f = new File("../../../../asm_examples/test/simulator/CaseRule02.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
	}

	@Test void extend() throws Exception {
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof ExtendRule));
		File f = new File("../../../../asm_examples/examples/simple_example/population.asm");
		Logger.getLogger(AsmetaFeatureChecker.class).debug(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
	}

	@Test void invariant() throws Exception {
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof Invariant));
		File f = new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		Logger.getLogger(AsmetaFeatureChecker.class).debug(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
	}

	@Test void visitAsmExamples() throws Exception {
		List<String> modelList = readFilteredModelList(MODEL_LIST_FILE);
		List<String> evoavallaModels = readFilteredModelList(EXPERIMENT_DIR.resolve(
				"model_lists/model_list_evoavalla_budget_60_valid.txt"));
		List<String> atgtModels = readFilteredModelList(EXPERIMENT_DIR.resolve(
				"model_lists/model_list_atgt_valid.txt"));
		List<String> randomModels = readFilteredModelList(EXPERIMENT_DIR.resolve(
				"model_lists/model_list_random_valid.txt"));

		// Parse each model once and reuse it for all features.
		Map<String, Asm> parsedModels = new LinkedHashMap<>();
		for (String model : modelList) {
			if (parsedModels.containsKey(model)) {
				continue;
			}
			File file = Path.of("../../../../asm_examples").resolve(model).toFile();
			try {
				parsedModels.put(model, ASMParser.setUpReadAsm(file).getMain());
			} catch (ParseException | IndexOutOfBoundsException e) {
				System.out.println("-> ERROR processing file " + file + ": " + e.getMessage());
			}
		}
		System.out.println("General model list: " + modelList.size() + " ASMs; parsed: " + parsedModels.size());

		List<String> featureNames = new ArrayList<>(featurePredicates.keySet());
		Collections.sort(featureNames);
		for (String feature : featureNames) {
			AsmetaFeatureChecker checker = new AsmetaFeatureChecker(featurePredicates.get(feature));
			int total = 0;
			int evoavalla = 0;
			int atgt = 0;
			int random = 0;
			for (String model : parsedModels.keySet()) {
				if (checker.checkFeature(parsedModels.get(model))) {
					total++;
					if (evoavallaModels.contains(model)) evoavalla++;
					if (atgtModels.contains(model)) atgt++;
					if (randomModels.contains(model)) random++;
				}
			}

			System.out.println("\n" + feature);
			System.out.println("  +-- General models with this feature: " + total + "/" + modelList.size());
			System.out.println("  `-- Valid models among those with this feature");
			String evoavallaPercent = "N/A";
			String atgtPercent = "N/A";
			String randomPercent = "N/A";
			if (total > 0) {
				evoavallaPercent = String.format(Locale.ROOT, "%.0f%%", 100.0 * evoavalla / total);
				atgtPercent = String.format(Locale.ROOT, "%.0f%%", 100.0 * atgt / total);
				randomPercent = String.format(Locale.ROOT, "%.0f%%", 100.0 * random / total);
			}
			System.out.println("      +-- atgt: " + atgt + "/" + total + " (" + atgtPercent + ")");
			System.out.println("      +-- random: " + random + "/" + total + " (" + randomPercent + ")");
			System.out.println("      `-- evoavalla: " + evoavalla + "/" + total + " (" + evoavallaPercent + ")");
		}
	}

	/**
	 * Prints the number of models containing each feature in the selected model list.
	 */
	@Test void visitAsmFeatureCounts() throws Exception {
		List<String> modelList = readFilteredModelList(MODEL_LIST_FILE);

		for (String featureName : featurePredicates.keySet()) {
			AsmetaFeatureChecker checker = new AsmetaFeatureChecker(featurePredicates.get(featureName));
			int count = 0;
			for (String model : modelList) {
				File file = Path.of("../../../../asm_examples").resolve(model).toFile();
				try {
					if (checker.checkFeature(ASMParser.setUpReadAsm(file).getMain())) {
						count++;
					}
				} catch (ParseException | IndexOutOfBoundsException e) {
					System.out.println("-> ERROR processing file " + file + ": " + e.getMessage());
				}
			}
			Logger.getLogger(AsmetaFeatureChecker.class).info(
					"Total ASMs with " + featureName + ": " + count + " out of " + modelList.size());
		}
	}

	// Old method used for ICST 2026 to fill the green/red dots in the table in the paper,
	// now replaced by visitAsmExamples() with the different model list .txt files
	@Test void visitAsmResults() throws Exception {
		AsmetaFeatureChecker spr;

		List<String> modelList = readResultsFile(Path.of(
				"../../../../code/experimental/asmeta.evotest/asmeta.evotest.experiments/data/icst-26-exp/data.csv"),
				"random");

		// Fetch all files in the directory and all features
		for (String featureName : featurePredicates.keySet()) {
			int count = 0;
			spr = new AsmetaFeatureChecker(featurePredicates.get(featureName));
			for (String model : modelList) {
				File f = new File("../" + model.replace("\\", File.separator));
				try {
					// Skip ASM parsing for missing files (e.g., renamed or deleted)
					if (!f.exists()) {
						System.out.println("-> ERROR processing file: " + f + " does not exist");
					}else if (spr.checkFeature(ASMParser.setUpReadAsm(f).getMain())) {
						count++;
					}
				} catch (ParseException e) {
					System.out.println("-> ERROR processing file: " + e.getMessage());
				} catch (IndexOutOfBoundsException e) {
					System.out.println("-> ERROR processing file: " + e.getMessage());
				}
			}
			System.out.println("Total ASMs with " + featureName + ": " + count + " out of " + modelList.size());
		}
	}

	/**
	 * Reads the model list and exclude those having problems, created just for
	 * testing purposes, etc.
	 * 
	 * @param filePath the path of the model list file
	 * @return the list of all paths for all models to be considered
	 * @throws IOException
	 */
	public static List<String> readFilteredModelList(Path filePath) throws IOException {
		List<String> result = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(filePath)) {
			String line;
			while ((line = br.readLine()) != null) {
				String trimmed = line.trim();
				if (!trimmed.isEmpty() && !trimmed.startsWith("//")) {
					String model = Path.of(trimmed.replace('\\', '/')).normalize().toString();
					if (!result.contains(model)) {
						result.add(model);
					}
				}
			}
		}

		return result;
	}

	public static List<String> readResultsFile(Path filePath, String method) throws IOException {
		List<String> result = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(filePath)) {
			String line;
			int lineCount = 0;
			while ((line = br.readLine()) != null) {
				if (lineCount == 0) {
					lineCount++;
					continue; // skip header
				}
				if (line.trim().split(",")[8].equals("OK") && line.trim().split(",")[7].equals(method))
					result.add(line.trim().split(",")[0]);
				lineCount++;
			}
		}

		return result;
	}

}
