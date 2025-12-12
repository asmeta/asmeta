package org.asmeta.parser.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.ParseException;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Invariant;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.ImportClause;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl;

public class AsmetaFeatureCheckerTest {

	// Marker to exclude not valid ASM files from the model_list.txt file
	private static final String MARKER = "// valid asm: 296";

	Map<String, Predicate<EObject>> featurePredicates = Map.of(
//			"IntegerDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof IntegerDomain),
//			"BooleanDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof BooleanDomain),
//			"EnumerativeDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof EnumTd),
//			"String-Monitored", x -> ((x instanceof MonitoredFunction) && ((Function) x).getCodomain() instanceof StringDomain),
//			"String-Controlled", x -> ((x instanceof ControlledFunction) && ((Function) x).getCodomain() instanceof StringDomain),
//			"AnyDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof AnyDomain),
//			"RealDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof RealDomain),
//			"AgentDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof AgentDomain),
//			"StructuredDomain", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof StructuredTd),
//			"LetRule", x -> (x instanceof LetRule),
//			"ExtendRule", x -> (x instanceof ExtendRule),
			"ForallRule", x -> (x instanceof ForallRule),
//			"MacroRuleWithParams", x -> (x instanceof MacroCallRule && !((MacroCallRule) x).getParameters().isEmpty()),
//			"Invariant", x -> (x instanceof Invariant),
//			"ChooseRule", x -> (x instanceof ChooseRule),
//			"SubsetOfInt", x -> ((x instanceof Function) && ((Function) x).getCodomain() instanceof ConcreteDomain) && 
//                    (((ConcreteDomain) ((Function) x).getCodomain()).getTypeDomain() instanceof IntegerDomain),
//            "INVAR", x -> (x instanceof InvarConstraint),
			"Modules", x -> (x instanceof ImportClause && !isStandardLibrary(((ImportClause) x).getModuleName())));

	/**
	 * Checks whether the module name is one of those of the standard libraries
	 * 
	 * @param moduleName the module name
	 * @return true when it is standard library, false if not
	 */
	public boolean isStandardLibrary(String moduleName) {
		return moduleName.endsWith("TimeLibrary") || moduleName.endsWith("StandardLibrary")
				|| moduleName.endsWith("CTLLibrary") || moduleName.endsWith("LTLLibrary")
				|| moduleName.endsWith("MAPEpatterns") || moduleName.endsWith("TimeLibrarySimple");
	}

	@Test
	public void testVisitAsm2() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof MonitoredFunction));
		File f = new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		spr = new AsmetaFeatureChecker(x -> (x instanceof OutFunction));
		// it has no monitored functions
		assertFalse(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// remove all appenders to avoid bloating the console
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
	}

	@Test
	public void testLet() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof LetRuleImpl));
		File f = new File("../../../../asm_examples/test/simulator/CaseRule02.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
	}

	@Test
	public void testExtend() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof ExtendRule));
		File f = new File("../../../../asm_examples/examples/simple_example/population.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
	}

	@Test
	public void testInvariant() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.ALL);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		// first example: check if there exists a monitored function
		AsmetaFeatureChecker spr = new AsmetaFeatureChecker(x -> (x instanceof Invariant));
		File f = new File("../../../../asm_examples/examples/ferryman/ferrymanSimulator.asm");
		System.out.println(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		// it has a monitored function
		assertTrue(spr.checkFeature(ASMParser.setUpReadAsm(f).getMain()));
		Logger.getLogger(AsmetaFeatureChecker.class).removeAllAppenders();
	}

	@Test
	public void testVisitAsmExamples() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.OFF);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		AsmetaFeatureChecker spr;

		// Read the "model_list.txt" file containing the list of ASM files to be tested,
		// but delete all lines before "// valid asm: 296"
		List<String> modelList = readFilteredModelList(Paths.get("model_list.txt"));

		// Fetch all files in the directory and all features
		for (String featureName : featurePredicates.keySet()) {
			int count = 0;
			spr = new AsmetaFeatureChecker(featurePredicates.get(featureName));
			for (String model : modelList) {
				File f = new File("../../../../asm_examples/" + model.replace("\\", File.separator));
				try {
					if (spr.checkFeature(ASMParser.setUpReadAsm(f).getMain())) {
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

	@Test
	public void testVisitAsmResults() throws Exception {
		// move to setup before class
		Logger.getLogger(AsmetaFeatureChecker.class).setLevel(Level.OFF);
		Logger.getLogger(AsmetaFeatureChecker.class).addAppender(new ConsoleAppender(new SimpleLayout()));
		AsmetaFeatureChecker spr;

		List<String> modelList = readResultsFile(Paths.get(
				"/Users/andrea/Documents/GitHub/asmeta/code/experimental/asmeta.evotest/asmeta.evotest.experiments/data/icst-26-exp/data.csv"),
				"random");

		// Fetch all files in the directory and all features
		for (String featureName : featurePredicates.keySet()) {
			int count = 0;
			spr = new AsmetaFeatureChecker(featurePredicates.get(featureName));
			for (String model : modelList) {
				File f = new File("../" + model.replace("\\", File.separator));
				try {
					if (spr.checkFeature(ASMParser.setUpReadAsm(f).getMain())) {
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
		boolean startCollecting = false;

		try (BufferedReader br = Files.newBufferedReader(filePath)) {
			String line;
			while ((line = br.readLine()) != null) {
				// Check when to start collecting lines
				if (!startCollecting) {
					if (line.trim().equals(MARKER)) {
						startCollecting = true;
					}
					continue; // skip everything until we hit the marker
				}

				// Only collect lines after the marker
				if (!line.trim().isEmpty()) {
					result.add(line.trim());
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
