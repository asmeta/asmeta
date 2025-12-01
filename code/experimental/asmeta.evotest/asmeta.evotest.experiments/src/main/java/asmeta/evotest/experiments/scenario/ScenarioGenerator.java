package asmeta.evotest.experiments.scenario;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
import org.asmeta.parser.ASMParser;
import org.asmeta.xt.validator.AsmetaV;

import asmeta.AsmCollection;
import asmeta.evotest.evoasmetatg.main.EvoAsmetaTgCLI;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestSuite;

public class ScenarioGenerator {

	private static final int DEFAULT_RANDOM_STEPS = 5;
	private static final int DEFAULT_RANDOM_TESTS = 5;

	private static final List<AsmCoverageBuilder> ATGT_CRITERIA = List.of(CriteriaEnum.COMPLETE_RULE.criteria,
			CriteriaEnum.RULE_GUARD.criteria);

	private static final String EVOAVALLA_WORKING_DIR = "./evoAvalla/";
	private static final String DASH = "-";

	/**
	 * Run random test generation.
	 * 
	 * @param asmPath   the path of the asm to be validated
	 * @param targetDir the path of the target directory (where to store the
	 *                  results)
	 * @param nTest     the number of generated test
	 * @param nStep     the number of step in each test
	 * @param shuffle   if false, run choose rules deterministically (always choose
	 *                  first element)
	 * @return random execution time in ms
	 * @throws Exception if any Exception occurs
	 */
	public static float runRandom(String asmPath, String targetDir, int nTest, int nStep, boolean shuffle)
			throws Exception {
		List<Integer> stepList = new ArrayList<>();
		for (int i = 0; i < nTest; i++)
			stepList.add(nStep);
		return runRandom(asmPath, targetDir, stepList, shuffle);
	}

	/**
	 * Run random test generation.
	 * 
	 * @param asmPath   the path of the asm to be validated
	 * @param targetDir the path of the target directory (where to store the
	 *                  results)
	 * @param stepList  the list with the number of steps for each scenario to be
	 *                  generated
	 * @param shuffle   if false, run choose rules deterministically (always choose
	 *                  first element)
	 * @return random execution time in ms
	 * @throws Exception if any Exception occurs
	 */
	public static float runRandom(String asmPath, String targetDir, List<Integer> stepList, boolean shuffle)
			throws Exception {
		// Parse the asm
		AsmCollection asms = ASMParser.setUpReadAsm(new File(asmPath));
		// Run random test generation
		AsmTestSuite randomSuite = new AsmTestSuite();
		AsmTestGeneratorBySimulation randomTestGenerator = new AsmTestGeneratorBySimulation(asms, 1, 1);
		Instant start = Instant.now();
		if (stepList == null || stepList.isEmpty()) {
			randomTestGenerator.setStepNumber(DEFAULT_RANDOM_STEPS);
			randomTestGenerator.setNumberofTests(DEFAULT_RANDOM_TESTS);
			randomSuite.addAllTest(randomTestGenerator.getTestSuite(shuffle));
		} else {
			stepList.forEach(stepOccurrence -> {
				randomTestGenerator.setStepNumber(stepOccurrence);
				randomSuite.addAllTest(randomTestGenerator.getTestSuite(shuffle));
			});
		}
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();
		saveToAvalla(asmPath, randomSuite, targetDir, false);
		return exeTime;
	}

	/**
	 * Run nusmv test generation.
	 * 
	 * @param asmPath   the path of the asm to be validated
	 * @param targetDir the path of the target directory (where to store the
	 *                  results)
	 * @return nusmv test generator execution time in ms
	 * @throws Exception if any Exception occurs
	 */
	public static float runATGT(String asmPath, String targetDir) throws Exception {
		NuSMVtestGenerator nusmvTestGenerator = new NuSMVtestGenerator(asmPath, true);
		Instant start = Instant.now();
		AsmTestSuite nusmvSuite = nusmvTestGenerator.generateAbstractTests(ATGT_CRITERIA, Integer.MAX_VALUE, ".*");
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();
		saveToAvalla(asmPath, nusmvSuite, targetDir, true);
		return exeTime;
	}

	/**
	 * Save an test suite into avalla scenarios (with .avalla extension)
	 * 
	 * @param asmPath      the path of the asm to be validated
	 * @param suite        the test suite
	 * @param testDir      where to save the .avalla files
	 * @param keepLastStep if true, keep the last step statement, otherwise, remove
	 *                     it along with all following statements
	 */
	private static void saveToAvalla(String asmPath, AsmTestSuite suite, String testDir, boolean keepLastStep) {
		File outputFile = new File(testDir);
		outputFile.mkdirs();
		String outputDir = outputFile.getAbsolutePath();
		Path basePath = Path.of(testDir);
		Path targetPath = Path.of(asmPath);
		String relativeAsmPath;
		try {
			relativeAsmPath = basePath.relativize(targetPath).toString();
		} catch (IllegalArgumentException e) {
			relativeAsmPath = targetPath.toAbsolutePath().toString(); // fallback to absolute
		}
		SaveResults.saveResults(suite, relativeAsmPath, Collections.singleton(FormatsEnum.AVALLA), "", outputDir,
				keepLastStep);
	}

	/**
	 * Run EvoAvalla.
	 * 
	 * 
	 * @param asmPath   the path of the asm to be validated
	 * @param targetDir the path of the target directory (where to store the
	 *                  results)
	 * @param jdkPath   path to jdk 1.8
	 * @param budget    EvoSuite budget
	 * @param shuffle   if false, run choose rules deterministically (always choose
	 *                  first element)
	 * @return evoavalla execution time in ms
	 * @throws Exception if any Exception occurs
	 */
	public static float runEvoAvalla(String asmPath, String targetDir, String jdkPath, int budget, boolean shuffle)
			throws Exception {
		// If running from jar, check the presence of evosuite-1.0.6.jar in the same
		// directory
		Class<ScenarioGenerator> runningClass = ScenarioGenerator.class;
		String className = runningClass.getName().replace('.', '/');
		String classJar = runningClass.getResource("/" + className + ".class").toString();
		String evosuiteJarFolder;
		boolean runningFromJar = classJar.startsWith("jar:");
		if (runningFromJar) {
			File evosuiteJar = new File("./evosuite-1.0.6.jar");
			if (!evosuiteJar.exists())
				throw new RuntimeException("Missing dependency: 'evosuite-1.0.6.jar'"
						+ " was not found in the application directory. Please make sure 'evosuite-1.0.6.jar'"
						+ " is located in the same folder as the application's JAR file.");
			evosuiteJarFolder = new File(runningClass.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParentFile().toString();
		} else {
			evosuiteJarFolder = "..\\asmeta.evotest.evoasmetatg\\evosuite\\evosuite-jar";
		}
		// Run evoavalla
		List<String> evoAsmetaTgArguments = List.of(DASH + EvoAsmetaTgCLI.WORKING_DIR, EVOAVALLA_WORKING_DIR,
				DASH + EvoAsmetaTgCLI.INPUT, asmPath, DASH + EvoAsmetaTgCLI.OUTPUT, targetDir,
				DASH + EvoAsmetaTgCLI.JAVA_PATH, jdkPath, DASH + EvoAsmetaTgCLI.EVOSUITE_VERSION, "1.0.6",
				DASH + EvoAsmetaTgCLI.EVOSUITE_PATH, evosuiteJarFolder, DASH + EvoAsmetaTgCLI.TIME_BUDGET,
				String.valueOf(budget), DASH + EvoAsmetaTgCLI.CLEAN, "-DignoreDomainException=true",
				"-DshuffleRandom=" + (shuffle ? "true" : "false"));
		Instant start = Instant.now();
		EvoAsmetaTgCLI.main(evoAsmetaTgArguments.toArray(new String[0]));
		Instant end = Instant.now();
		long exeTime = Duration.between(start, end).toMillis();
		if (EvoAsmetaTgCLI.getReturnedCode() != 0) {
			throw new IllegalStateException(
					"EvoAsmetaTgCLI returned an error code:" + EvoAsmetaTgCLI.getReturnedCode());
		}
		// The generated scenarios load ASM files by filename only.
		// Change the load statement to match the correct model path.
		Files.walk(Paths.get(targetDir)).forEach(filePath -> {
			try {
				fixLoadStatement(filePath.toString(), asmPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		// Delete temporary directories
		// Walk the tree in reverse order: files first, directories last
		Files.walk(Paths.get(EVOAVALLA_WORKING_DIR)).sorted(Comparator.reverseOrder()).forEach(path -> {
			File file = path.toFile();
			if (file.isDirectory() && file.listFiles().length == 0) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException("Failed to delete " + path, e);
				}
			}
		});
		return exeTime;
	}

	/**
	 * Replace the load statement of a scenario with the correct relative path to
	 * the asm.
	 * 
	 * @param scenarioPath path of the scenario
	 * @param asmPath      path of the asm
	 * @throws IOException if an I/O error occurs
	 */
	private static void fixLoadStatement(String scenarioPath, String asmPath) throws IOException {
		if (scenarioPath.endsWith(AsmetaV.SCENARIO_EXTENSION)) {
			Path scenario = Paths.get(scenarioPath);
			Path scenarioFolder = scenario.getParent();
			Path asm = Paths.get(asmPath);
			// Compute relative path from scenario's parent folder to asm file
			String relativePath;
			try {
				relativePath = scenarioFolder.relativize(asm).toString();
			} catch (IllegalArgumentException e) {
				relativePath = asm.toAbsolutePath().toString(); // fallback to absolute
			}
			relativePath = relativePath.replace('\\', '/'); // OS-independent text
			// Read content
			String content = Files.readString(scenario, StandardCharsets.UTF_8);
			// Modify load statement
			String regex = "(?m)^\\s*load\\s+.*\\s*$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(content);
			String newContent = m.replaceAll("load " + relativePath + System.lineSeparator());
			// Write content back
			Files.writeString(scenario, newContent, StandardCharsets.UTF_8);
		}
	}

}
