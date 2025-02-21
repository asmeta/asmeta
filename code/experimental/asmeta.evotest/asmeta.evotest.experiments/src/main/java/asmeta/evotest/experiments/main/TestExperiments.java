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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import org.asmeta.xt.validator.RuleEvalWCov;
import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;

public class TestExperiments {

	private static final String REPORT_DIR = "report";
	private static final String BENCHMARK_CSV = REPORT_DIR + "/benchmark.csv";
	private static final String RESOURCES = "src/main/resources";
	private static final String MODELS = RESOURCES + "/models";
	private static final String RANDOM_DIR = "randomtests";
	private static final String ATGT_DIR = "atgttests";
	private static final String EVOAVALLA_DIR = "evoavallatests";
	private static final String DASH = "-";

	// TODO: remove hardcoded path to jdk
	private static final String JDK_PATH = /*"C:/Program Files/Java/jdk1.8.0_431"; */ "C:/Program Files/Java/jdk-1.8";

	/**
	 * Run the tests
	 */
	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		Path dir = Paths.get(MODELS);
		Files.walk(dir).forEach(path -> generateTestsAndComputeCoverage(path.toString()));
	}

	/**
	 * Determines whether the given path should be a target file for experiments. If
	 * so, generates .avalla files using ATGT, Random, and EvoAvalla. Then,
	 * validates and computes the coverage using the generated .avalla files.
	 * 
	 * @param filePath the path of the .asm file
	 */
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
					System.err.println("Skipping " + filePath + " because defines a module");
					return;
				}
			} catch (Exception e) {
				System.err.println("Skipping " + filePath + " because the internal asm cannot be parsed");
				e.printStackTrace();
				return;
			}
			System.out.println("Collecting benchmark info");
			collectBenchmarkInfo(asms);
			System.out.println("Generating test for " + file.getName());
			generateTestsAndComputeCoverage(filePath, asms);
		}
	}

	/**
	 * Collect and write to csv the information about the macro, conditional, and
	 * update rules for all asms in a collection
	 * 
	 * @param asmCollection
	 */
	private static void collectBenchmarkInfo(AsmCollection asmCollection) {
		List<String> headers = List.of("asm_name", "rule_signature", "tot_conditional_rules", "tot_update_rules");
		List<String[]> rows = new ArrayList<>();
		// get all the macro rules in the asm and in the imported asm along with the
		// total number of contidional
		// and uptade rules in it
		for (Asm asm : asmCollection) {
			String asmName = asm.getName();
			if (asmName.equals("StandardLibrary") || asmName.equals("CTLLibrary") || asmName.equals("LTLLibrary"))
				continue;
			for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
				String completeRuleName = rd.getName();
				String signature = completeRuleName.substring(completeRuleName.lastIndexOf(':') + 1);
				signature = signature.contains(",") ? "\"" + signature + "\"" : signature;
				int totCondRules = 0;
				int totUpdateRules = 0;
				for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd)) {
					if (r instanceof ConditionalRule)
						totCondRules++;
					if (r instanceof UpdateRule)
						totUpdateRules++;
				}
				rows.add(new String[] { asmName, signature, String.valueOf(totCondRules),
						String.valueOf(totUpdateRules) });
			}
		}
		// Print to csv
		try {
			boolean fileExists = Files.exists(Paths.get(BENCHMARK_CSV));
			FileOutputStream fos = new FileOutputStream(BENCHMARK_CSV, true);
			PrintStream ps = new PrintStream(fos);
			// If the file did not exist or is empty, start printing the headers
			if (!fileExists || Files.size(Paths.get(BENCHMARK_CSV)) == 0)
				ps.println(String.join(",", headers));
			for (String[] row : rows)
				ps.println(String.join(",", row));
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate .avalla files for an ASM specification using ATGT, Random, and
	 * EvoAvalla. Then, validates and computes the coverage using the generated
	 * .avalla files.
	 * 
	 * @param asmPath       the path of the asm to be validated
	 * @param asmCollection the <code>AsmCollection<code> that results from parsing
	 *                      the ASM
	 */
	private static void generateTestsAndComputeCoverage(String asmPath, AsmCollection asmCollection) {
		System.out.println("Starting generation for " + asmPath);
		String asmFileName = new File(asmPath).getName().substring(0, new File(asmPath).getName().indexOf("."));
		try {
			// Come decidere i parametri della random simulation? Vedere quanti avalla
			// generano gli altri metodi e con quanti step
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asmCollection, 5, 5);
			AsmTestSuite randomSuite = randomTestGenerator.getTestSuite();
			computeCoverageFromAsmTestSuite(asmPath, randomSuite, RESOURCES + "/" + RANDOM_DIR + "/" + asmFileName,
					REPORT_DIR + "/report_random.csv");
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
					REPORT_DIR + "/report_atgt.csv");
		} catch (Exception e) {
			System.err.println("ATGT failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		try {
			runEvoAvalla(asmPath, asmFileName);
		} catch (Exception e) {
			System.err.println("EvoAvalla failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

	}

	/**
	 * Run EvoAvalla and validate the asm using the generated scenarios-
	 * Also, copy the asm and the STDL directory to the scenarios folder to avoid compilation errors
	 * 
	 * 
	 * @param asmPath the path of the asm to be validated
	 * @param asmFileName the name of the asm
	 * @throws Exception
	 */
	private static void runEvoAvalla(String asmPath, String asmFileName) throws Exception {
		// Chiamata al generatore che usa EvoSuite
		// Problema 1: EvoAvalla attualmente supporta solo asm che iniziano con la
		// lettera maiuscola
		// Problema 2: se chiamo direttamente computeCoverageFromAvalla() si aspetta di
		// trovare la specifica nella cartella del progetto in considerazione,
		// (esempio per pillbox cerca la specifica .asm in
		// evoavallatests/pillbox_1/pillbox_1.asm).
		// Per evitare eccezioni per copio la specifica manualmente, sistemando la
		// posizione delle STDL e del nome
		
		String evoAavallaTestDir = "./" + RESOURCES + "/" + EVOAVALLA_DIR;
		String avallaOutputDirectory = evoAavallaTestDir + "/" + asmFileName;

		List<String> evoAsmetaTgArguments = List.of(DASH + EvoAsmetaTgCLI.WORKING_DIR, "./evoAvalla/",
				DASH + EvoAsmetaTgCLI.INPUT, asmPath, DASH + EvoAsmetaTgCLI.OUTPUT, avallaOutputDirectory,
				DASH + EvoAsmetaTgCLI.JAVA_PATH, JDK_PATH, DASH + EvoAsmetaTgCLI.EVOSUITE_VERSION, "1.0.6",
				DASH + EvoAsmetaTgCLI.EVOSUITE_PATH, "..\\asmeta.evotest.evoasmetatg\\evosuite\\evosuite-jar",
				DASH + EvoAsmetaTgCLI.TIME_BUDGET, "10", DASH + EvoAsmetaTgCLI.CLEAN,
				"-DignoreDomainException=true");

		EvoAsmetaTgCLI.main(evoAsmetaTgArguments.toArray(new String[0]));

		if (EvoAsmetaTgCLI.getReturnedCode() != 0) {
			throw new IllegalStateException(
					"EvoAsmetaTgCLI returned an error code:" + EvoAsmetaTgCLI.getReturnedCode());
		}

		// Copy the ASM and the libraries in the scenario folder
		try {
			// Copy the ASM file
			File source = new File(MODELS + "/" + asmFileName + ASMParser.ASM_EXTENSION);
			File dest = new File(avallaOutputDirectory + "/" + asmFileName + ASMParser.ASM_EXTENSION);
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

			// Copy the STDL directory and its contents
			source = new File(MODELS + "/STDL");
			dest = new File(avallaOutputDirectory + "/STDL");
			Files.createDirectories(dest.toPath());

			// Walk the file tree and copy each file and directory
			Path sourcePath = source.toPath();
			Path destPath = dest.toPath();
			Files.walk(sourcePath).forEach(path -> {
				try {
					Path targetPath = destPath.resolve(sourcePath.relativize(path));
					Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			System.out.println("ASM file and STDL directory copied successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

		computeCoverageFromAvalla(avallaOutputDirectory, REPORT_DIR + "/report_evoavalla.csv");
	}

	/**
	 * Validate an ASM specification and compute the coverage given an
	 * <code>AsmTestSuite<code>
	 * 
	 * @param asmPath the path of the asm to be validated
	 * @param suite   the test suite
	 * @param testDir where to save the .avalla files
	 * @param csvPath where to save the experiments stats (it must be a .csv file)
	 * @throws Exception
	 */
	private static void computeCoverageFromAsmTestSuite(String asmPath, AsmTestSuite suite, String testDir,
			String csvPath) throws Exception {
		new File(testDir).mkdirs();
		SaveResults.saveResults(suite, asmPath, Collections.singleton(FormatsEnum.AVALLA), "",
				new File(testDir).getAbsolutePath());
		computeCoverageFromAvalla(testDir, csvPath);
	}

	/**
	 * Validate an ASM specification and compute the coverage given the .avalla
	 * scenarios
	 * 
	 * @param scenarioPath the path of the directory where to look for the .avalla
	 *                     files or the path of a single .avalla file
	 * @param csvPath      where to save the experiments stats (it must be a .csv
	 *                     file)
	 * @throws Exception
	 */
	private static void computeCoverageFromAvalla(String scenarioPath, String csvPath) throws Exception {
		AsmetaV.execValidation(scenarioPath, true, csvPath);
		RuleEvalWCov.reset();
	}

}
