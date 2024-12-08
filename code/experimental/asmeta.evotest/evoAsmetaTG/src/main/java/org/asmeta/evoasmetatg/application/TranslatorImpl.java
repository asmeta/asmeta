package org.asmeta.evoasmetatg.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.asm2java.main.Asmeta2JavaCLI;
import org.asmeta.evoasmetatg.config.Options;
import org.asmeta.evoasmetatg.config.OptionsImpl;
import org.asmeta.junit2avalla.main.Junit2AvallaCLI;

/**
 * The {@code TranslatorImpl} class provides an implementation of the
 * {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(TranslatorImpl.class);

	/** Name of the Asm input file. */
	private String asmName;

	/** CLI Options. */
	private Options options;

	/** Version of evosuite. */
	private String evosuiteVersion;

	/** Version of the Java compiler that compiles test files for Evosuite. */
	private String compilerVersion;

	/** Version of the java jdk specified by the user */
	private String javaVersion;

	/** Time that Evosuite spends on test generation. */
	private String searchBudget;

	/** Indicates whether to clean the folders {@code true} or not {@code false} */
	private boolean clean;

	/** File manager instance for handling file operations. */
	private FileManager fileManager;

	/**
	 * No args constructor. Create a new instance of Options, FileManager and
	 * initialize the clean parameter to false.
	 */
	public TranslatorImpl() {
		this.options = new OptionsImpl();
		this.fileManager = new FileManager();
		this.clean = false;
	}

	@Override
	public void setWorkingDir(String workingDir) throws IOException {
		fileManager.setWorkingDirPath(workingDir);
	}

	@Override
	public void setInput(String inputPath) throws FileNotFoundException {
		File inputFile = fileManager.setInputFilePath(inputPath);
		// extract the ASM name from the file.
		this.asmName = inputFile.getName().replace(TranslatorConstants.ASM_EXTENSION, "");
		logger.debug("inputFile: {}", inputFile);
		logger.debug("asmName: {}", asmName);
	}

	@Override
	public void setOutput(String outputDir) throws IOException {
		fileManager.setOutputFolder(outputDir);
	}

	@Override
	public void setJavaPath(String javaPath) throws FileNotFoundException {
		// set the java jdk folder path
		File javaJdkFolder = fileManager.setJavaPath(javaPath);

		// set the javaVersion
		this.javaVersion = extractJdkVersion(javaJdkFolder);
		logger.info("Setting the java jdk version to: {}.", this.javaVersion);
	}

	@Override
	public void setOptions(String propertyName, String propertyValue) {
		logger.info("Setting the option: {} to : {}.", propertyName, propertyValue);
		options.setValue(propertyValue, Boolean.parseBoolean(propertyValue));
	}

	@Override
	public void setEvosuiteVersion(String evosuiteVersion) {
		int version = Integer.parseInt((evosuiteVersion).replace(".", ""));
		switch (version) {
		case 106:
			this.compilerVersion = TranslatorConstants.JAVA_8;
			this.evosuiteVersion = TranslatorConstants.EVOSUITE_1_0_6_JAR;
			break;
		case 120:
			this.compilerVersion = TranslatorConstants.JAVA_9;
			this.evosuiteVersion = TranslatorConstants.EVOSUITE_1_2_0_JAR;
			break;
		default:
			logger.error("Evosuite version not valid: {}.", evosuiteVersion);
			throw new IllegalArgumentException("Unexpected value: " + version);
		}
		logger.info("Setting the evosuite version: {} using: {}.", evosuiteVersion, this.evosuiteVersion);
		logger.info("Setting the java compiler version: {}.", this.compilerVersion);
	}

	@Override
	public void setTimeBudget(String timeBudget) {
		if (Integer.parseInt(timeBudget) < 0) {
			logger.error("Error while setting the timeBudget for Evosuite.");
			throw new IllegalArgumentException("Time must be greater than 0.");
		}
		logger.info("Setting the timeBudget for Evosuite: {}.", timeBudget);
		this.searchBudget = timeBudget;
	}

	@Override
	public void generate() throws TranslationException, IOException {

		// check consistency between java and Evosuite version
		checkJavaConsistency();

		// translate to Java
		logger.info("Running Asmetal2Java:");
		Asmeta2JavaCLI.main(buildAsmeta2JavaOptions().toArray(new String[0]));
		int returnCode = Asmeta2JavaCLI.getReturnedCode();
		if (returnCode != 0) {
			logger.error("Stopping the generation.");
			throw new TranslationException("Asmetal2Java exited with code: " + returnCode);
		}

		// executing Evouite
		logger.info("Running Evosuite:\n" + TranslatorConstants.EVOSUITE_ASCII_ART);
		executeEvosuite();

		// translate to Avalla
		logger.info("Running Junit2Avalla:\n");
		Junit2AvallaCLI.main(buildJunit2AvallaOptions().toArray(new String[0]));
		returnCode = Junit2AvallaCLI.getReturnedCode();
		if (returnCode != 0) {
			logger.error("Stopping the generation.");
			throw new TranslationException("Junit2AvallaCLI exited with code: " + returnCode);
		}
	}

	@Override
	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@Override
	public void clean() {
		logger.debug("Cleaning the resources...");
		cleanFolderManagingException(fileManager.getEvosuiteTestsPathToString());
		// Don't clean the statistics.csv TranslatorConstants.EVOSUITE_REPORT
	}

	@Override
	public List<String> getOptionNames() {
		return options.getOptionNames();
	}

	@Override
	public String getOptionsDescription() {
		return options.getDescription();
	}

	/**
	 * Build the option for the {@code Asmeta2Java} CLI.
	 * 
	 * @return list of String containing the desired options.
	 */
	private List<String> buildAsmeta2JavaOptions() {

		// Asmeta specification input file
		String asmSpecInputFile = fileManager.getInputFilePathToString();

		// Working directory for the asmeta2java service
		// <workingDir>/asmeta2java
		String asmeta2javaWorkingDir = Paths
				.get(fileManager.getWorkingDirPathToString(), TranslatorConstants.ASMETA2JAVA).toString();

		// Output directory for the asmeta2java service
		// <workingDir>/evosuite/evosuite-target>
		String asmeta2javaOutput = fileManager.getEvosuiteTargetPathToString();

		List<String> listOfOptions = new LinkedList<>();

		/*
		 * -workingDir <asmeta2javaWorkingDir> -input <asmSpecInputFile> -output
		 * <asmeta2javaOutput> -mode testGen
		 */
		listOfOptions.addAll(List.of(TranslatorConstants.ASMETA2JAVA_WORKING_DIR, asmeta2javaWorkingDir,
				TranslatorConstants.ASMETA2JAVA_INPUT, asmSpecInputFile, TranslatorConstants.ASMETA2JAVA_OUTPUT,
				asmeta2javaOutput, TranslatorConstants.MODE, TranslatorConstants.TEST_GEN));

		// -D<property>=<value>
		listOfOptions.addAll(options.getCLIStringOptions());

		if (clean) {
			// -clean
			listOfOptions.add(TranslatorConstants.CLEAN);
		}

		if (options.isCompiler()) {
			// -compilerVersion <compilerVersion>
			listOfOptions.add(TranslatorConstants.COMPILER_VERSION);
			listOfOptions.add(compilerVersion);
		}

		logger.info("List of Asmeta2Java options: {}.", listOfOptions);

		return listOfOptions;
	}

	/**
	 * Build the option for the {@code Evosuite} jar command.
	 * 
	 * 
	 * @return list of String containing the desired options.
	 */
	private List<String> buildEvosuiteOptions() {

		List<String> listOfOptions = new LinkedList<>();

		// Set the location of the evosuite-tests directory where the junit files will
		// be generated
		// -Dtest_dir="<<workingDir>/evosuite/evosuite-tests>"
		String evosuiteTestsOption = TranslatorConstants.EVOSUITE_TEST_DIR_OPTION + TranslatorConstants.EQ
				+ TranslatorConstants.DOUBLE_QUOTES + fileManager.getEvosuiteTestsPathToString()
				+ TranslatorConstants.DOUBLE_QUOTES;

		// Set the location of the report.csv file
		// -Dreport_dir="<<workingDir>/evosuite/evosuite-report>"
		String evosuiteReportOption = TranslatorConstants.EVOSUITE_REPORT_DIR + TranslatorConstants.EQ
				+ TranslatorConstants.DOUBLE_QUOTES + Paths.get(fileManager.getWorkingDirPathToString(),
						TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_REPORT).toString()
				+ TranslatorConstants.DOUBLE_QUOTES;

		// Set the java input class (add _ATG to the asmeta specification file name)
		String evosuiteJavaInputFile = asmName + TranslatorConstants.ATG;
		
		// Set the target directory of evosuite
		// <workingDir>/evosuite/evosuite-target
		String evosuiteTargetDir = fileManager.getEvosuiteTargetPathToString();

		// Set the location of the current evosuite jar
		String evosuiteJar = fileManager.getEvosuiteJarDirPathToString() + File.separator + evosuiteVersion;

		/*
		 * java.exe -jar <evosuiteJar> -target <workingDir>/evosuite/evosuite-target -class
		 * <evosuiteJavaInputFile> -Dtest_dir="<<workingDir>/evosuite/evosuite-tests>"
		 * -criterion LINE:BRANCH -Dminimize=true -Dassertion_strategy=all
		 * -Dreport_dir="<<workingDir>/evosuite/evosuite-report>"
		 */
		listOfOptions.addAll(List.of(fileManager.getJavaJdkPathToString(), TranslatorConstants.JAR, evosuiteJar,
				TranslatorConstants.TARGET, evosuiteTargetDir, TranslatorConstants.CLASS,
				evosuiteJavaInputFile, evosuiteTestsOption, TranslatorConstants.CRITERION,
				TranslatorConstants.LINE_BRANCH, TranslatorConstants.DMINIMIZE_TRUE,
				TranslatorConstants.DASSERTION_STRATEGY_ALL, evosuiteReportOption));

		// Set the search budget option
		// -Dsearch_budget=<searchBudget>
		if (this.searchBudget != null) {
			listOfOptions.add(TranslatorConstants.SEARCH_BUDGET.concat(this.searchBudget));
		}

		return listOfOptions;
	}

	/**
	 * Build the option for the {@code Junit2Avalla} CLI.
	 * 
	 * @return list of String containing the desired options.
	 */
	private List<String> buildJunit2AvallaOptions() {

		List<String> listOfOptions = new LinkedList<>();

		// Set the location of the junit input file
		String junitInputFile = fileManager.getEvosuiteTestsPathToString() + File.separator + asmName
				+ TranslatorConstants.JUNIT_TEST_EXTENSION;
		
		String junit2AvallaWorkingDir = Paths.get(fileManager.getWorkingDirPathToString(), TranslatorConstants.JUNIT2AVALLA).toString();

		listOfOptions.addAll(List.of(TranslatorConstants.JUNIT2AVALLA_WORKING_DIR, junit2AvallaWorkingDir, TranslatorConstants.JUNIT2AVALLA_INPUT, junitInputFile,
				TranslatorConstants.JUNIT2AVALLA_OUTPUT, fileManager.getOutputFolderToString()));

		if (clean) {
			listOfOptions.add(TranslatorConstants.CLEAN);
		}

		logger.info("List of Junit2Avalla options: {}.", listOfOptions);

		return listOfOptions;
	}

	/**
	 * Run Evosuite with a process builder.
	 * 
	 * @throws IOException          if there is an I/O error.
	 * @throws TranslationException if there is an error during the process.
	 */
	private void executeEvosuite() throws IOException, TranslationException {
		List<String> commands = buildEvosuiteOptions();
		logger.info("List of Evosuite options: {}", commands);

		ProcessBuilder pb = new ProcessBuilder(commands);
		// show the output on the console
		pb.inheritIO();
		/*
		 * remove JAVA_HOME environment variable from local process because Evosuite by
		 * default will run the Java version specified in JAVA_HOME environment
		 * variable, so we need to remove it to run the desired Java version. This
		 * change is local and will not affect the system environment variable
		 */
		pb.environment().remove(TranslatorConstants.JAVA_HOME);
		try {
			Process process = pb.start();
			int exitCode = process.waitFor();
			logger.info("Process exited with code: {}", exitCode);

			if (exitCode != 0) {
				throw new TranslationException("Evosuite error: Process exited with code " + exitCode);
			}
		} catch (InterruptedException e) {
			/* Clean up whatever needs to be handled before interrupting */
			Thread.currentThread().interrupt();
			throw new TranslationException("Evosuite error. " + e.getMessage());
		} finally {
			logger.info("Cleaning the compiled files in: {}.", fileManager.getEvosuiteTargetPathToString());
			cleanFolder(fileManager.getEvosuiteTargetPathToString());
		}
	}

	/**
	 * Clean the compiled .class files required by Evosutie.
	 * 
	 * @param folder String containing the folder name.
	 * @throws IOException if there is an I/O error.
	 */
	private void cleanFolder(String folder) throws IOException {
		File evosuiteTarget = new File(folder);
		logger.info("Cleaning the folder: {}.", evosuiteTarget.getAbsolutePath());
		if (evosuiteTarget.exists() && evosuiteTarget.isDirectory()) {
			for (File file : evosuiteTarget.listFiles()) {
				logger.debug("Cleaning the file: {}. ", file);
				Files.delete(file.toPath());
			}
		}
	}

	/**
	 * Clean the folder managing the exception with a try catch block.
	 * 
	 * @param folder to clean.
	 */
	private void cleanFolderManagingException(String folder) {
		try {
			cleanFolder(folder);
		} catch (IOException e) {
			logger.error("Failed to clean the folder: {}, because of: {} ", folder, e.getMessage());
		}
	}

	/**
	 * Extract the java jdk version from the jdk folder.
	 * 
	 * @param javaJdkFolderPath path to the jdk folder.
	 * @return String containing the java version.
	 */
	private String extractJdkVersion(File javaJdkFolderPath) {
		String jdk = javaJdkFolderPath.getName();
		if (jdk.contains("jdk-1.8")) {
			return TranslatorConstants.JAVA_8;
		} else if (jdk.contains("jdk-9")) {
			return TranslatorConstants.JAVA_9;
		} else {
			logger.error("Problem while setting the java jdk version: {}.", jdk);
			throw new IllegalArgumentException("jdk version not recognized: " + jdk);
		}
	}

	/**
	 * Check if the java version passed by the user is the same as the compiler
	 * version set by Evosuite.
	 * 
	 * @throws TranslationException if there is no consistency between the java
	 *                              versions.
	 */
	private void checkJavaConsistency() throws TranslationException {
		if (!javaVersion.equals(compilerVersion)) {
			logger.error("Found Inconsistency between java and evosuite versions.");
			throw new TranslationException("There is no consistency between the java version " + javaVersion
					+ " and the Evosuite version " + evosuiteVersion + " that uses the version " + compilerVersion);
		}
	}

}
