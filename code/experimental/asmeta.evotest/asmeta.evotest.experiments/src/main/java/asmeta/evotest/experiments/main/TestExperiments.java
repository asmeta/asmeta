package asmeta.evotest.experiments.main;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.evotest.evoasmetatg.main.EvoAsmetaTgCLI;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import atgt.coverage.AsmTestSuite;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
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

	private static final String RESOURCES = "src/main/resources";
	private static final String MYASM_DIR = "myasm";
	private static final String RANDOM_DIR = "randomtests";
	private static final String ATGT_DIR = "atgttests";
	private static final String EVOAVALLA_DIR = "evoavallatests";
	private static final String DASH = "-";

	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		String targetDir = "./" + RESOURCES + "/" + MYASM_DIR + "/tests";
		//computeCoverageFromAvalla(targetDir, RESOURCES + "/" + MYASM_DIR + "/report.csv");

		Path dir = Paths.get(RESOURCES);
		Files.walk(dir).forEach(path -> generateTestsAndComputeCoverage(path.toString()));
	}

	private static void generateTestsAndComputeCoverage(String filePath) {
		File file = new File(filePath);
		// Skip directories and file contained in the STDL directory
		if (file.isDirectory() || file.getParentFile().getName().equals("STDL")  || file.getParentFile().getParentFile().getName().equals(EVOAVALLA_DIR))
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
				allRules.put(RuleDeclarationUtils.getCompleteName(rd), Pair.of(totCondRules, totUpdateRules));
			}
		}

		String asmFileName = new File(asmPath).getName().substring(0, new File(asmPath).getName().indexOf("."));
		try {
			// Come decidere i parametri della random simulation? Vedere quanti avalla
			// generano gli altri metodi e con quanti step
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asmCollection, 5, 5);
			AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
			computeCoverageFromAsmTestSuite(asmPath, randomSuite, RESOURCES + "/" + RANDOM_DIR + "/" + asmFileName,
					RESOURCES + "/" + RANDOM_DIR + "/report_random.csv");
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
					RESOURCES + "/" + ATGT_DIR + "/report_atgt.csv");
		} catch (Exception e) {
			System.err.println("ATGT failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		// Chiamata al generatore che usa EvoSuite
		// Problema 1: EvoAvalla attualmente supporta solo asm che iniziano con la
		// lettera maiuscola
		// Problema 2: se chiamo direttamente computeCoverageFromAvalla() si aspetta di
		// trovare la specifica nella cartella del progetto in considerazione,
		// (esempio per pillbox cerca la specifica .asm in
		// evoavallatests/pillbox_1/pillbox_1.asm).
		// Per evitare eccezioni per copio la specifica manualmente, sistemando la
		// posizione delle STDL e del nome
		try {

			String evoAavallaTestDir = "./" + RESOURCES + "/" + EVOAVALLA_DIR;
			String avallaOutputDirectory = evoAavallaTestDir + "/" + asmFileName;

			List<String> evoAsmetaTgArguments = List.of(DASH + EvoAsmetaTgCLI.WORKING_DIR, "./evoAvalla/",
					DASH + EvoAsmetaTgCLI.INPUT, asmPath, DASH + EvoAsmetaTgCLI.OUTPUT, avallaOutputDirectory,
					DASH + EvoAsmetaTgCLI.JAVA_PATH, "C:\\Program Files\\Java\\jdk-1.8",
					DASH + EvoAsmetaTgCLI.EVOSUITE_VERSION, "1.0.6", DASH + EvoAsmetaTgCLI.EVOSUITE_PATH,
					"..\\asmeta.evotest.evoAsmetaTG\\evosuite\\evosuite-jar", DASH + EvoAsmetaTgCLI.TIME_BUDGET, "10",
					DASH + EvoAsmetaTgCLI.CLEAN, "-DignoreDomainException=true");

			EvoAsmetaTgCLI.main(evoAsmetaTgArguments.toArray(new String[0]));

			if (EvoAsmetaTgCLI.getReturnedCode() != 0) {
				throw new IllegalStateException(
						"EvoAsmetaTgCLI returned an error code:" + EvoAsmetaTgCLI.getReturnedCode());
			}
			
			computeCoverageFromAvalla(avallaOutputDirectory, evoAavallaTestDir + "/report_evoavalla.csv");

		} catch (Exception e) {
			System.err.println("EvoAvalla failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

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
