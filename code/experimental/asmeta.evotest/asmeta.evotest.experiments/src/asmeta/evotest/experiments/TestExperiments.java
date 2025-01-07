package asmeta.evotest.experiments;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import atgt.coverage.AsmTestSuite;
import tgtlib.util.Pair;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.xt.validator.AsmetaV;
import org.asmeta.xt.validator.RuleDeclarationUtils;
import org.asmeta.xt.validator.RuleEvalWCov;
import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;

public class TestExperiments {

	private static final String RESOURCES = "resources";
	private static final String MYASM_DIR = "myasm";
	private static final String RANDOM_DIR = "randomtests";
	private static final String ATGT_DIR = "atgttests";

	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		String targetDir = "./" + RESOURCES + "/" + MYASM_DIR + "/tests";
		computeCoverageFromAvalla(targetDir, RESOURCES + "/" + MYASM_DIR + "/report.csv");

		Path dir = Paths.get(RESOURCES);
		Files.walk(dir).forEach(path -> generateTestsAndComputeCoverage(path.toString()));
	}

	private static void generateTestsAndComputeCoverage(String filePath) {
		File file = new File(filePath);
		// Skip directories and file contained in the STDL directory
		if (file.isDirectory() || file.getParentFile().getName().equals("STDL"))
			return;
		// Look only at .asm files
		if (file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
			AsmCollection asms = null;
			try {
				asms = ASMParser.setUpReadAsm(file);
				if (asms.getMain().getMainrule() == null) {
					System.out.println("Skipping " + filePath + " because defines a module");
					return;
				}
			} catch (Exception e) {
				System.out.println("Skipping " + filePath + " because the internal asm cannot be parsed");
				return;
			}
			System.out.println("Generating test for " + file.getName());
			// Dovrei tenermi una lista di tutte le asm così che so se effettivamente sono
			// presenti nel csv, ossia non ci sono stati problemi
			generateTestsAndComputeCoverage(filePath, asms);
		}
	}

	private static void generateTestsAndComputeCoverage(String asmPath, AsmCollection asmCollection) {
		System.out.println("Starting generation for" + asmPath);
		// ottieni tutte le regole contenute nella asm e nelle asm che importa con il
		// totale di cond e update rules (è utile? per ora non ci faccio niente)
		Map<String, Pair<Integer, Integer>> allRules = new HashMap<>();
		for (Asm a : asmCollection) {
			String name = a.getName();
			if (name.equals("StandardLibrary") || name.equals("CTLLibrary") || name.equals("LTLLibrary"))
				continue;
			for (RuleDeclaration rd : a.getBodySection().getRuleDeclaration()) {
				int totCondRules = 0;
				int totUpdateRules = 0;
				for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd)) {
					if (r instanceof ConditionalRule)
						totCondRules++;
					if (r instanceof UpdateRule)
						totUpdateRules++;
				}
				allRules.put(RuleDeclarationUtils.getCompleteName(rd), new Pair<>(totCondRules, totUpdateRules));
			}
		}

		String asmFileName = new File(asmPath).getName().substring(0, new File(asmPath).getName().indexOf("."));
		try {
			// Come decidere i parametri della random simulation? Vedere quanti avalla generano gli altri metodi e con quanti step
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asmCollection, 5, 5);
			AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
			computeCoverageFromAsmTestSuite(asmPath, randomSuite, RESOURCES + "/" + RANDOM_DIR + "/" + asmFileName,
					RESOURCES + "/" + RANDOM_DIR + "/report.csv");
		} catch (Exception e) {
			System.err.println("RANDOM failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		try {
			NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asmPath, true);
			// Che criterio utilizzare?
			AsmTestSuite nusmvSuite = nusmvTestGenerator
					.generateAbstractTests(Collections.singleton(CriteriaEnum.MCDC.criteria), Integer.MAX_VALUE, ".*");
			computeCoverageFromAsmTestSuite(asmPath, nusmvSuite, RESOURCES + "/" + ATGT_DIR + "/" + asmFileName,
					RESOURCES + "/" + ATGT_DIR + "/report.csv");
		} catch (Exception e) {
			System.err.println("ATGT failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		// Chiamata al generatore che usa EvoSuite
	}

	private static void computeCoverageFromAsmTestSuite(String asmPath, AsmTestSuite suite, String testDir,
			String csvPath) throws Exception {
		new File(testDir).mkdirs();
		SaveResults.saveResults(suite, asmPath, Collections.singleton(FormatsEnum.AVALLA), "",
				new File(testDir).getAbsolutePath());
		computeCoverageFromAvalla(testDir, csvPath);
	}

	private static void computeCoverageFromAvalla(String targetDir, String csvPath) throws Exception {
		AsmetaV.execValidation(targetDir, true, csvPath);
		RuleEvalWCov.reset();
	}

}
