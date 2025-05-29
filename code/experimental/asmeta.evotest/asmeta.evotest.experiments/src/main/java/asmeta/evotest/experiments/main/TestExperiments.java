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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
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

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;

public class TestExperiments {

	private static final String DATA_CSV = "data.csv";
	private static final String RANDOM_DIR = "randomtests";
	private static final String ATGT_DIR = "atgttests";
	private static final String EVOAVALLA_DIR = "evoavallatests";
	private static final String DASH = "-";
	private static final String AVALLA_EXTENSION = ".avalla";

	private static final List<String> CSV_HEADERS = List.of("asm", "n_macro", "n_conditional", "n_update", "approach",
			"exec_time", "n_scenarios", "n_step", "n_set", "n_check", "macro_coverage", "branch_coverage",
			"update_rule_coverage", "n_failing_scenarios", "n_val_error_scenarios");

	private static String jdkPath;
	private static String sourcePath;
	private static String basePath;
	private static String targetPath;
	private static String resultsPath;
	private static String randomDir;
	private static String atgtDir;
	private static String evoavallaDir;

	/**
	 * Run the tests
	 * 
	 * @param args The first argument must be the path to the Java JDK version 8.
	 *             The second argument is used to set the path of source folder
	 *             where to search for .asm specifications or directly the path of
	 *             an .asm specification. The third argument is used to set the
	 *             relative path of the target folder where to store the results.
	 * @throws Exception if any exception occurs
	 */
	public static void main(String[] args) throws Exception {
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.INFO);
		Logger.getLogger(RuleEvalWCov.class).setLevel(Level.INFO);
		Logger.getLogger(AsmetaV.class).setLevel(Level.DEBUG);

		if (args.length < 3)
			throw new RuntimeException(
					"Two arguments must be provided: \n\t-the path to the Java JDK version 8\n\t-the source folder or specification\n\t-the target folder");
		jdkPath = args[0];
		targetPath = args[1];
		sourcePath = args[2];

		// Parse the arguments and retrieve the Strings representing the needed paths
		File targetFile = new File(targetPath);
		if (!targetFile.exists()
				|| !(targetFile.getName().endsWith(ASMParser.ASM_EXTENSION) || targetFile.isDirectory()))
			throw new RuntimeException(
					targetPath + " is an invalid target file as it is not a directory or a file with .asm extension");
		basePath = targetFile.isDirectory() ? targetFile.toString()
				: targetFile.getCanonicalFile().getParentFile().toString();
		resultsPath = Paths.get(basePath).toAbsolutePath().normalize().resolve(sourcePath).normalize().toString();

		randomDir = resultsPath + File.separator + RANDOM_DIR;
		atgtDir = resultsPath + File.separator + ATGT_DIR;
		evoavallaDir = resultsPath + File.separator + EVOAVALLA_DIR;

		// delete existing results and recreate the empty directory
		File resultsDir = new File(resultsPath);
		if (resultsDir.exists())
			FileUtils.deleteDirectory(resultsDir);
		Files.createDirectories(new File(resultsPath).toPath());

		// eventually clean existing csv files and setup a new data.csv file
		cleanCsv();
		setupCsv();

		// generate avalla scenarios, validate them and compute coverage
		Path dir = Paths.get(targetFile.getAbsolutePath());
		Files.walk(dir).forEach(path -> generateTestsAndComputeCoverage(path.toString()));

		// clean the csv files except data.csv
		cleanCsv();
	}

	/**
	 * Computes the relative path from a base directory to a target path.
	 * 
	 * @param base   the base path
	 * @param target the target Path
	 * @return the resolved path string
	 */
	private static String computeRelativePath(String base, String target) {
		Path basePath = Paths.get(base).toAbsolutePath().normalize();
		Path targetPath = Paths.get(target).toAbsolutePath().normalize();
		Path relativePath = basePath.relativize(targetPath);
		return relativePath.toString();
	}

	/**
	 * Delete all the csv files in the report directory except for data.csv
	 * 
	 */
	private static void cleanCsv() {
		try (Stream<Path> files = Files.list(Path.of(resultsPath))) {
			files.forEach(path -> {
				try {
					String fileName = path.getFileName().toString();
					if (fileName.endsWith(".csv") && !fileName.equals(DATA_CSV)) {
						Files.delete(path);
						System.out.println("Deleted: " + path);
					}
				} catch (IOException e) {
					System.err.println("Failed to delete: " + path);
				}
			});
		} catch (IOException e) {
			System.err.println("Error accessing directory: " + resultsPath);
			e.printStackTrace();
		}
	}

	/**
	 * Create (or overwrite) and initialize the csv containg experimental data,
	 * named data.csv
	 * 
	 */
	private static void setupCsv() {
		File dataCsv = new File(resultsPath + File.separator + DATA_CSV);
		try {
			FileOutputStream fos = new FileOutputStream(dataCsv, false);
			PrintStream ps = new PrintStream(fos);
			ps.println(String.join(",", CSV_HEADERS));
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Determines whether the given path should be a target file for experiments. If
	 * so, generates .avalla files using ATGT, Random, and EvoAvalla. Then,
	 * validates and computes the coverage using the generated .avalla files.
	 * 
	 * @param filePath the path of the .asm file
	 */
	static void generateTestsAndComputeCoverage(String filePath) {
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
			// Create and populate the part of the row common to all executions. The key is
			// the column, the value is the cell value
			Map<String, String> row = new HashMap<>();
			collectBenchmarkInfo(asms, row);
			System.out.println("Generating test for " + file.getName());
			// Generate and validate tests
			generateTestsAndComputeCoverage(filePath, asms, row);
		}
	}

	/**
	 * Collect and write to csv the information about the macro, conditional, and
	 * update rules for the main asm in the collection
	 * 
	 * @param asmCollection the asm collection
	 * @param row           the row to be written on data.csv and to be populated
	 *                      with common information on the asm.
	 */
	private static void collectBenchmarkInfo(AsmCollection asmCollection, Map<String, String> row) {
		Asm asm = asmCollection.getMain();
		String asmName = asm.getName();
		int totCondRules = 0;
		int totUpdateRules = 0;
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd)) {
				if (r instanceof ConditionalRule)
					totCondRules++;
				if (r instanceof UpdateRule)
					totUpdateRules++;
			}
		}
		row.put("asm", String.valueOf(asmName));
		row.put("n_macro", String.valueOf(asm.getBodySection().getRuleDeclaration().size()));
		row.put("n_conditional", String.valueOf(totCondRules));
		row.put("n_update", String.valueOf(totUpdateRules));
	}

	/**
	 * Generate .avalla files for an ASM specification using ATGT, Random, and
	 * EvoAvalla. Then, validates and computes the coverage using the generated
	 * .avalla files.
	 * 
	 * @param asmPath       the path of the asm to be validated
	 * @param asmCollection the <code>AsmCollection<code> that results from parsing
	 *                      the ASM
	 * @param row           the row to be written on data.csv populated with common
	 *                      information on the asm. The key is the column, value is
	 *                      the cell value.
	 */
	private static void generateTestsAndComputeCoverage(String asmPath, AsmCollection asmCollection,
			Map<String, String> row) {
		System.out.println("Starting generation for " + asmPath);
		String asmFileName = new File(asmPath).getName().substring(0, new File(asmPath).getName().indexOf("."));

		List<Integer> stepList = null;
		try {
			stepList = runEvoAvalla(asmPath, asmFileName, row);
		} catch (Throwable e) {
			System.err.println("EvoAvalla failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		try {
			runRandom(asmPath, asmCollection, asmFileName, stepList, row);
		} catch (Throwable e) {
			System.err.println("RANDOM failed to generate a test suite that can be validated");
			e.printStackTrace();
		}

		try {
			runATGT(asmPath, asmFileName, row);
		} catch (Throwable e) {
			System.err.println("ATGT failed to generate a test suite that can be validated");
			e.printStackTrace();
		}
	}

	/**
	 * Run EvoAvalla and validate the asm using the generated scenarios- Also, copy
	 * the asm and the STDL directory to the scenarios folder to avoid compilation
	 * errors
	 * 
	 * 
	 * @param asmPath     the path of the asm to be validated
	 * @param asmFileName the name of the asm
	 * @param row         the row to be written on data.csv populated with common
	 *                    information on the asm. The key is the column, value is
	 *                    the cell value.
	 * @return a list with the number of steps in each generated avalla
	 * @throws Exception if any Exception occurs
	 */
	private static List<Integer> runEvoAvalla(String asmPath, String asmFileName, Map<String, String> row)
			throws Exception {
		String evoAavallaTestDir = evoavallaDir;
		String avallaOutputDirectory = evoAavallaTestDir + "/" + asmFileName;
		String evosuiteJarFolder = "..\\asmeta.evotest.evoasmetatg\\evosuite\\evosuite-jar";

		List<String> evoAsmetaTgArguments = List.of(DASH + EvoAsmetaTgCLI.WORKING_DIR, "./evoAvalla/",
				DASH + EvoAsmetaTgCLI.INPUT, asmPath, DASH + EvoAsmetaTgCLI.OUTPUT, avallaOutputDirectory,
				DASH + EvoAsmetaTgCLI.JAVA_PATH, jdkPath, DASH + EvoAsmetaTgCLI.EVOSUITE_VERSION, "1.0.6",
				DASH + EvoAsmetaTgCLI.EVOSUITE_PATH, evosuiteJarFolder,
				/* DASH + EvoAsmetaTgCLI.TIME_BUDGET, "1", */ DASH + EvoAsmetaTgCLI.CLEAN,
				"-DignoreDomainException=true");

		Instant start = Instant.now();
		EvoAsmetaTgCLI.main(evoAsmetaTgArguments.toArray(new String[0]));
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();

		if (EvoAsmetaTgCLI.getReturnedCode() != 0) {
			throw new IllegalStateException(
					"EvoAsmetaTgCLI returned an error code:" + EvoAsmetaTgCLI.getReturnedCode());
		}

		// The generated scenarios load ASM files by filename only. So the ASM and
		// libraries must be copied into the scenario folder.
		try {
			// Copy the ASM file
			File source = new File(basePath + "/" + asmFileName + ASMParser.ASM_EXTENSION);
			File dest = new File(avallaOutputDirectory + "/" + asmFileName + ASMParser.ASM_EXTENSION);
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

			// Copy the STDL directory and its contents
			source = new File(basePath + "/STDL");
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
		String reportCsv = resultsPath + "/report_evoavalla.csv";
		int errorsInValidation = computeCoverageFromAvalla(avallaOutputDirectory, reportCsv);

		// Write to data.csv the info about evoavalla execution
		Map<String, Integer> avallaInfo = extractAvallaInfo(avallaOutputDirectory);
		int nScenario = getNumberOfScenario(avallaOutputDirectory);
		writeToCsv(reportCsv, exeTime, nScenario, errorsInValidation, avallaInfo, row);

		return getStatementCount(avallaOutputDirectory, "step");
	}

	/**
	 * Run random test generation and validate the asm using the generated scenarios
	 * 
	 * @param asmPath       the path of the asm to be validated
	 * @param asmCollection the <code>AsmCollection<code> that results from parsing
	 *                      the ASM
	 * @param asmFileName   the name of the asm
	 * @param stepList      the list with the number of steps in the avalla
	 *                      scenarios that the random test generation will create
	 * @param row           the row to be written on data.csv populated with common
	 *                      information on the asm. The key is the column, value is
	 *                      the cell value.
	 * @throws Exception if any Exception occurs
	 */
	private static void runRandom(String asmPath, AsmCollection asmCollection, String asmFileName,
			List<Integer> stepList, Map<String, String> row) throws Exception {
		AsmTestSuite randomSuite = new AsmTestSuite();
		AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asmCollection, 1, 1);

		Instant start = Instant.now();
		if (stepList == null || stepList.isEmpty()) {
			// if EvoAvalla fails use default settings (5 tests with 5 steps each)
			randomTestGenerator.setStepNumber(5);
			randomTestGenerator.setNumberofTests(5);
			// generate the test suite
			randomSuite.addAllTest(randomTestGenerator.getTestSuite());
		} else {
			stepList.forEach(stepOccurrence -> {
				// set the number of step
				randomTestGenerator.setStepNumber(stepOccurrence);
				// generate the test suite
				randomSuite.addAllTest(randomTestGenerator.getTestSuite());
			});
		}
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();

		// compute coverage and save results in the csv file
		String reportCsv = resultsPath + "/report_random.csv";
		String avallaOutputDirectory = randomDir + File.separator + asmFileName;
		String relativePath = computeRelativePath(asmPath, avallaOutputDirectory);
		int errorsInValidation = computeCoverageFromAsmTestSuite(relativePath, randomSuite, avallaOutputDirectory,
				reportCsv);
		RuleEvalWCov.reset();

		// Write to data.csv the info about evoavalla execution
		Map<String, Integer> avallaInfo = extractAvallaInfo(avallaOutputDirectory);
		int nScenario = getNumberOfScenario(avallaOutputDirectory);
		writeToCsv(reportCsv, exeTime, nScenario, errorsInValidation, avallaInfo, row);
	}

	/**
	 * Run atgt test generation and validate the asm using the generated scenarios
	 * 
	 * @param asmPath     the path of the asm to be validated
	 * @param asmFileName the name of the asm
	 * @param row         the row to be written on data.csv populated with common
	 *                    information on the asm. The key is the column, value is
	 *                    the cell value.
	 * @throws Exception if any Exception occurs
	 */
	private static void runATGT(String asmPath, String asmFileName, Map<String, String> row)
			throws Exception, IOException {
		NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asmPath, true);

		Instant start = Instant.now();
		AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(
				List.of(CriteriaEnum.COMPLETE_RULE.criteria, CriteriaEnum.RULE_GUARD.criteria), Integer.MAX_VALUE,
				".*");
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();

		// compute coverage and save results in the csv file
		String reportCsv = resultsPath + "/report_atgt.csv";
		String avallaOutputDirectory = atgtDir + File.separator + asmFileName;
		String relativePath = computeRelativePath(asmPath, avallaOutputDirectory);
		int errorsInValidation = computeCoverageFromAsmTestSuite(relativePath, nusmvSuite, avallaOutputDirectory,
				reportCsv);
		RuleEvalWCov.reset();

		// Write to data.csv the info about evoavalla execution
		Map<String, Integer> avallaInfo = extractAvallaInfo(avallaOutputDirectory);
		int nScenario = getNumberOfScenario(avallaOutputDirectory);
		writeToCsv(reportCsv, exeTime, nScenario, errorsInValidation, avallaInfo, row);
	}

	/**
	 * Extract the number of step, check and set fot all avalla in the given
	 * directory
	 * 
	 * @param avallaOutputDirectory the path of the directory
	 * @return the total number of occurrencies of step, check and set in all the
	 *         avalla scenarios
	 * @throws IOException if any IO exception occurs
	 */
	private static Map<String, Integer> extractAvallaInfo(String avallaOutputDirectory) throws IOException {
		Map<String, Integer> avallaInfo = new HashMap<>();
		for (String statement : List.of("step", "check", "set"))
			avallaInfo.put(statement,
					getStatementCount(avallaOutputDirectory, statement).stream().reduce(0, Integer::sum));
		return avallaInfo;
	}

	/**
	 * Counts all occurrences of the given statement for each avalla file present in
	 * the given folder
	 * 
	 * @param avallaFolder path of the folder containing the avalla files
	 * @param statement    the stament to count
	 * @return the list containg the number of occurrencies for each avalla
	 * @throws IOException if an I/O error occurs.
	 */
	private static List<Integer> getStatementCount(String avallaFolder, String statement) throws IOException {
		Path avallaPath = Path.of(avallaFolder);
		List<Integer> statmentCountList = new ArrayList<>();
		Files.list(avallaPath).filter(path -> path.toString().endsWith(AVALLA_EXTENSION)).forEach(path -> {
			try (Stream<String> lines = Files.lines(path)) {
				int statementCount = (int) lines.flatMap(line -> Stream.of(line.split("\\W+")))
						.filter(word -> word.equals(statement)).count();
				statmentCountList.add(statementCount);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return statmentCountList;
	}

	/**
	 * Counts the number of avalla scenarios in a directory
	 * 
	 * @param avallaFolder path of the folder containing the avalla files
	 * @return the number of scenarios in the directory
	 * @throws IOException if an I/O error occurs.
	 */
	private static int getNumberOfScenario(String avallaFolder) throws IOException {
		Path avallaPath = Path.of(avallaFolder);
		Files.list(avallaPath).filter(path -> path.toString().endsWith(AVALLA_EXTENSION)).count();
		return (int) Files.list(avallaPath).filter(path -> path.toString().endsWith(AVALLA_EXTENSION)).count();
	}

	/**
	 * Write a row regarding the validation (with coverage) of the avalla scenarios
	 * generated by a gvan algorithm to the data.csv file.
	 * 
	 * @param reportCsv  the path of the csv file containing the coverage data to be
	 *                   extracted
	 * @param exeTime    the execcution time
	 * @param nScenario  the number of scenarios
	 * @param nScenario  the number of scenarios for which the validation resulted
	 *                   in an error
	 * @param avallaInfo the map containing the number of scenarios and total number
	 *                   of step, set and check statements in them
	 * @param row        the row to be written on data.csv populated with common
	 *                   information on the asm. The key is the column, value is the
	 *                   cell value.
	 * @throws FileNotFoundException if a file does not exists
	 * @throws IOException           if any IO Exceptio occurs
	 * @throws CsvException          if any exveption when reading a csv occurs
	 */
	private static void writeToCsv(String reportCsv, long exeTime, int nScenario, int errorsInValidation,
			Map<String, Integer> avallaInfo, Map<String, String> row)
			throws FileNotFoundException, IOException, CsvException {
		// read the csv with coverage data
		List<String[]> covRows = readCsv(reportCsv);
		// build the map representing the row
		row.put("approach", reportCsv.substring(reportCsv.indexOf("_") + 1, reportCsv.indexOf(".csv")));
		row.put("exec_time", String.valueOf(exeTime));
		row.put("n_scenarios", String.valueOf(nScenario));
		row.put("n_val_error_scenarios", String.valueOf(errorsInValidation));
		row.put("n_step", String.valueOf(avallaInfo.get("step")));
		row.put("n_set", String.valueOf(avallaInfo.get("set")));
		row.put("n_check", String.valueOf(avallaInfo.get("check")));
		// If there is only one row (headers row), no scenarios or only empty scenarios
		// were generated
		if (covRows.size() == 1) {
			row.put("macro_coverage", "0.0");
			row.put("branch_coverage", "0.0");
			row.put("update_rule_coverage", "0.0");
			row.put("n_failing_scenarios", "0");
		} else {
			int coveredMacros = 0, coveredUpdateRules = 0, coveredCondTrue = 0, coveredCondFalse = 0;
			for (String[] covRow : covRows) {
				if (row.get("asm").equals(covRow[1])) {
					coveredMacros += 1;
					coveredUpdateRules += Integer.valueOf(covRow[7]);
					coveredCondTrue += Integer.valueOf(covRow[4]);
					coveredCondFalse += Integer.valueOf(covRow[5]);
				}
			}
			// compute the coverage metrics and get the number of failing scenarios
			String failingScenarios = covRows.get(1)[8];
			float macroCoverage = ((float) coveredMacros) / Integer.valueOf(row.get("n_macro"));
			float branchCoverage = ((float) coveredCondTrue + coveredCondFalse)
					/ (2 * Integer.valueOf(row.get("n_conditional")));
			float updateRuleCoverage = ((float) coveredUpdateRules) / Integer.valueOf(row.get("n_update"));
			row.put("macro_coverage", String.valueOf(macroCoverage));
			row.put("branch_coverage", String.valueOf(branchCoverage));
			row.put("update_rule_coverage", String.valueOf(updateRuleCoverage));
			row.put("n_failing_scenarios", failingScenarios);
		}
		// build the actual row
		String dataCsv = resultsPath + File.separator + DATA_CSV;
		String[] actualRow = new String[CSV_HEADERS.size()];
		for (String key : row.keySet()) {
			int index = CSV_HEADERS.indexOf(key);
			actualRow[index] = row.get(key);
		}
		// write the actual row to csv
		try {
			FileOutputStream fos = new FileOutputStream(dataCsv, true);
			PrintStream ps = new PrintStream(fos);
			ps.println(String.join(",", actualRow));
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validate an ASM specification and compute the coverage given an
	 * <code>AsmTestSuite<code>
	 * 
	 * @param asmPath the path of the asm to be validated
	 * @param suite   the test suite
	 * @param testDir where to save the .avalla files
	 * @param csvPath where to save the experiments stats (it must be a .csv file)
	 * @return the number of scenarios for which the validation result in an error
	 * @throws Exception
	 */
	private static int computeCoverageFromAsmTestSuite(String asmPath, AsmTestSuite suite, String testDir,
			String csvPath) throws Exception {
		new File(testDir).mkdirs();
		SaveResults.saveResults(suite, asmPath, Collections.singleton(FormatsEnum.AVALLA), "",
				new File(testDir).getAbsolutePath());
		return computeCoverageFromAvalla(testDir, csvPath);
	}

	/**
	 * Validate an ASM specification and compute the coverage given a direcroty
	 * containing .avalla scenarios. If the validation of an avalla results in an
	 * exception, the valiadtion continues.
	 * 
	 * @param scenarioPath the path of the directory where to look for the .avalla
	 *                     files
	 * @param csvPath      where to save the experiments stats (it must be a .csv
	 *                     file)
	 * @return the number of scenarios for which the validation result in an error
	 * @throws IOException  if any IO Exceptio occurs
	 * @throws CsvException if any exveption when reading a csv occurs
	 */
	private static int computeCoverageFromAvalla(String scenarioPath, String csvPath) throws IOException, CsvException {
		int failingScenarios = 0;
		int errorsInValidation = 0;
		// To avoid to count the pervious scenario twice if the next one is empty, keep
		// track of the previous execution id. For the first scenario, the check is done
		// using the column name
		String previosExecId = "execution_id";
		try (Stream<Path> files = Files.list(Path.of(scenarioPath))) {
			List<Path> filesList = files.filter(path -> path.getFileName().toString().endsWith(AVALLA_EXTENSION))
					.collect(Collectors.toList());
			for (Path path : filesList) {
				System.out.println("Processing: " + path);
				try {
					AsmetaV.execValidation(path.toString(), true, csvPath);
					// Extract the value from the last column of the last row in the CSV to check if
					// the scenario fails
					List<String[]> rows = readCsv(csvPath);
					String[] lastRow = rows.getLast();
					String execId = lastRow[0];
					if (!execId.equals(previosExecId) && !lastRow[lastRow.length - 1].equals("none")) {
						failingScenarios++;
						previosExecId = execId;
					}
				} catch (Throwable e) {
					errorsInValidation++;
					System.err.println("Failed to validate the test: " + path.getFileName());
					e.printStackTrace();
				}
			}
			System.out.println("Validated all files in: " + scenarioPath);
			extractLastExecution(csvPath, failingScenarios);
		} catch (IOException e) {
			System.err.println("Error accessing directory: " + scenarioPath);
			e.printStackTrace();
		} finally {
			RuleEvalWCov.reset();
		}
		return errorsInValidation;
	}

	/**
	 * Extract form the input file the rows regarding the last execution (that
	 * mantains all aggregated data) and overwrite with them the content of the
	 * input file itself. Also put in all rows the number of scenarios that fails in
	 * the last column.
	 * 
	 * @param csvPath          the path of the target csv
	 * @param failingScenarios the number of failing scenarios
	 * @throws IOException  if any IO Exceptio occurs
	 * @throws CsvException if any exveption when reading a csv occurs
	 */
	private static void extractLastExecution(String csvPath, int failingScenarios) throws IOException, CsvException {
		// If the csv does not exists (no scenarios generated), create a dummy empty csv
		File csvFile = new File(csvPath);
		if (!csvFile.exists()) {
			csvFile.createNewFile();
			String[] headers = { "execution_id", "asm_name", "rule_signature", "tot_conditional_rules",
					"covered_true_conditional_rules", "covered_false_conditional_rules", "tot_update_rules",
					"covered_update_rules", "failing_scenarios" };
			String headersString = String.join(",", headers);
			try {
				FileOutputStream fos = new FileOutputStream(csvPath, false);
				PrintStream ps = new PrintStream(fos);
				ps.print(headersString);
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<String[]> rows = readCsv(csvPath);
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
			try {
				FileOutputStream fos = new FileOutputStream(newCsv, false);
				PrintStream ps = new PrintStream(fos);
				ps.print(extractedContent);
				ps.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	static List<String[]> readCsv(String filePath) throws IOException, CsvException {
		CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(parser).build()) {
			return reader.readAll();
		}
	}

}
