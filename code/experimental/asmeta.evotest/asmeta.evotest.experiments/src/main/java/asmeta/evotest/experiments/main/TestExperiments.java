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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

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
	private static final String AVALLA_EXTENSION = ".avalla";
	private static final String STEP = "step";
	private static final String EXECUTION_TIME_CSV = REPORT_DIR + "/execution_time.csv";

	// TODO: remove hardcoded path to jdk
	private static final String JDK_PATH = /* "C:/Program Files/Java/jdk1.8.0_431"; */ "C:/Program Files/Java/jdk-1.8";

	/**
	 * Run the tests
	 */
	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		// clean the resource directories before executing
		cleanDir(Path.of(RESOURCES).resolve(ATGT_DIR));
		cleanDir(Path.of(RESOURCES).resolve(EVOAVALLA_DIR));
		cleanDir(Path.of(RESOURCES).resolve(RANDOM_DIR));

		// clean the csv files
		cleanCsv();

		// create e new execution_time.csv file
		setupExecutionTimeCsv();

		Path dir = Paths.get(MODELS);
		Files.walk(dir).forEach(path -> generateTestsAndComputeCoverage(path.toString()));
	}

	/**
	 * delete all the csv files in the report directory
	 */
	private static void cleanCsv() {
		try (Stream<Path> files = Files.list(Path.of(REPORT_DIR))) {
			files.filter(path -> path.getFileName().toString().endsWith(".csv")).forEach(path -> {
				try {
					Files.delete(path);
					System.out.println("Deleted: " + path);
				} catch (IOException e) {
					System.err.println("Failed to delete: " + path);
				}
			});
		} catch (IOException e) {
			System.err.println("Error accessing directory: " + REPORT_DIR);
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a directory recursively
	 * 
	 * @param pathToBeDeleted directory path to delete
	 * @throws IOException if an I/O error occurs.
	 */
	private static void cleanDir(Path pathToBeDeleted) throws IOException {
		if (!pathToBeDeleted.toFile().exists())
			return;
		Files.walkFileTree(pathToBeDeleted, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
		});
		System.out.println("cleaned the directory: " + pathToBeDeleted);
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
	 * Create (or overwrite) and initialize a new csv file for tracking the
	 * execution time
	 */
	private static void setupExecutionTimeCsv() {
		File executionTimeCsv = new File(EXECUTION_TIME_CSV);
		try (FileWriter writer = new FileWriter(executionTimeCsv, false)) {
			writer.append("AsmSpec,EvoAvalla,Random,Atgt\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * print a new row in the execution time csv file
	 * 
	 * @param asmName        name of the asm spec
	 * @param executionTimes long array of execution times
	 */
	private static void printTimeToExecutionTimeCsv(String asmName, long[] executionTimes) {
		File executionTimeCsv = new File(EXECUTION_TIME_CSV);
		try (FileWriter writer = new FileWriter(executionTimeCsv, true)) {
			StringBuilder row = new StringBuilder();
			row.append(asmName);
			for (long executionTime : executionTimes) {
				row.append("," + String.valueOf(executionTime));
			}
			row.append("\n");
			writer.write(row.toString());
		} catch (IOException e) {
			e.printStackTrace();
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

		/*
		 * This list contains the step operation count for each avalla file generated by
		 * evoAvalla for the current Asm specification.
		 */
		List<Integer> stepSettings = new LinkedList<>();

		// Run EvoAvalla before random in order to populate the stepSettings list
		long evoAvallaExeTime = 0;
		try {
			evoAvallaExeTime = runEvoAvalla(asmPath, asmFileName, stepSettings);
		} catch (Exception e) {
			System.err.println("EvoAvalla failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		long randomExeTime = 0;
		try {
			// Come decidere i parametri della random simulation? Vedere quanti avalla
			// generano gli altri metodi e con quanti step

			// ho dovuto apportare delle modifiche alla classe AsmTestGeneratorBySimulation
			// perchè sovrascriveva i test avalla generati, ora creo una istanza della
			// classe e uso sempre la stessa per creare tutti i test corrispondenti.
			// Nella classe AsmTestGeneratorBySimulation ho aggiunto la variabile
			// testNumberOffset che viene incrementata a ogni test generato in modo da non
			// sovrascrivere i futuri test

			AsmTestSuite randomSuite = new AsmTestSuite();
			AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asmCollection, 1, 1);

			Instant start = Instant.now();
			if (stepSettings.isEmpty() || evoAvallaExeTime == 0) {
				// if EvoAvalla fails use default settings
				randomTestGenerator.setStepNumber(5);
				randomTestGenerator.setTestNumer(5);
				// generate the test suite
				randomSuite.addAllTest(randomTestGenerator.getTestSuite());
			} else {
				stepSettings.forEach(stepOccurrence -> {
					// set the number of step
					randomTestGenerator.setStepNumber(stepOccurrence + 1);
					// generate the test suite
					randomSuite.addAllTest(randomTestGenerator.getTestSuite());
				});
			}
			Instant end = Instant.now();

			computeCoverageFromAsmTestSuite(asmPath, randomSuite, RESOURCES + "/" + RANDOM_DIR + "/" + asmFileName,
					REPORT_DIR + "/report_random.csv");
			RuleEvalWCov.reset();

			randomExeTime = Duration.between(start, end).toMillis();

		} catch (Exception e) {
			System.err.println("RANDOM failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		long atgtExeTime = 0;
		try {
			NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asmPath, true);
			// Che criterio utilizzare?

			Instant start = Instant.now();
			AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(
					List.of(CriteriaEnum.COMPLETE_RULE.criteria, CriteriaEnum.RULE_GUARD.criteria), Integer.MAX_VALUE,
					".*");
			Instant end = Instant.now();

			computeCoverageFromAsmTestSuite(asmPath, nusmvSuite, RESOURCES + "/" + ATGT_DIR + "/" + asmFileName,
					REPORT_DIR + "/report_atgt.csv");

			atgtExeTime = Duration.between(start, end).toMillis();
		} catch (Exception e) {
			System.err.println("ATGT failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		printTimeToExecutionTimeCsv(asmFileName, new long[] { evoAvallaExeTime, randomExeTime, atgtExeTime });

	}

	/**
	 * Run EvoAvalla and validate the asm using the generated scenarios- Also, copy
	 * the asm and the STDL directory to the scenarios folder to avoid compilation
	 * errors
	 * 
	 * 
	 * @param asmPath     the path of the asm to be validated
	 * @param asmFileName the name of the asm
	 * @return the EvoAvalla execution time in milliseconds (long)
	 * @throws Exception
	 */
	private static long runEvoAvalla(String asmPath, String asmFileName, List<Integer> stepSettings) throws Exception {
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
				/* DASH + EvoAsmetaTgCLI.TIME_BUDGET, "5", */DASH + EvoAsmetaTgCLI.CLEAN,
				"-DignoreDomainException=true");

		Instant start = Instant.now();
		EvoAsmetaTgCLI.main(evoAsmetaTgArguments.toArray(new String[0]));
		Instant end = Instant.now();

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

		// compute coverage and save results in the csv file
		// computeCoverageFromAvalla(avallaOutputDirectory, REPORT_DIR +
		// "/report_evoavalla.csv");
		computeCoverageFromSingleAvallaSeparately(avallaOutputDirectory, REPORT_DIR + "/report_evoavalla.csv");

		// extract the step settings to compare with the random
		extractStepSettings(Path.of(avallaOutputDirectory), stepSettings);

		// return the execution time of EvoAvalla
		return Duration.between(start, end).toMillis();
	}

	/**
	 * Counts all occurrences of the term step for each avalla file present in the
	 * path passed as a parameter
	 * 
	 * @param avallaFolder path of the avalla files to process.
	 * @return list of step occurrences for each avalla file.
	 * @throws IOException if an I/O error occurs.
	 */
	private static List<Integer> extractStepSettings(Path avallaFolder, List<Integer> stepsList) throws IOException {
		Files.list(avallaFolder).filter(path -> path.toString().endsWith(AVALLA_EXTENSION)).forEach(path -> {
			try (Stream<String> lines = Files.lines(path)) {
				int stepCount = (int) lines.flatMap(line -> Stream.of(line.split("\\W+")))
						.filter(word -> word.equalsIgnoreCase(STEP)).count();
				stepsList.add(stepCount);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return stepsList;
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
		// computeCoverageFromAvalla(testDir, csvPath);
		computeCoverageFromSingleAvallaSeparately(testDir, csvPath);
	}

	private static void computeCoverageFromSingleAvallaSeparately(String testDir, String csvPath) throws IOException {
		File tempCsv = new File(Path.of(csvPath).getParent().resolve("temp.csv").toString());
		try (Stream<Path> files = Files.list(Path.of(testDir))) {
			files.filter(path -> path.getFileName().toString().endsWith(AVALLA_EXTENSION)).forEach(path -> {
				System.out.println("Processing: " + path);
				try {
					AsmetaV.execValidation(path.toString(), true, tempCsv.getAbsolutePath());
				} catch (Exception e) {
					System.err.println("Failed to validate the test: " + path.getFileName());
					e.printStackTrace();
				}
			});
			System.out.println("Validated all files in: " + testDir);
			CsvProcessor.agregateTempCsv(tempCsv.getAbsolutePath(), csvPath);
		} catch (IOException e) {
			System.err.println("Error accessing directory: " + testDir);
			e.printStackTrace();
		} finally {
			RuleEvalWCov.reset();
			Files.delete(tempCsv.toPath());
		}
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
