package asmeta.evotest.evoasmetatg.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.asmetal2java.codegen.main.Asmeta2JavaCLI;
import asmeta.evotest.evoasmetatg.config.Options;
import asmeta.evotest.evoasmetatg.config.OptionsImpl;
import asmeta.evotest.junit2avalla.main.Junit2AvallaCLI;

/**
 * The {@code TranslatorImpl} class provides an implementation of the
 * {@link Translator} interface.
 */
public class TranslatorImpl implements Translator {

	/** Logger */
	private final Logger logger = LogManager.getLogger(TranslatorImpl.class);

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
	public void setWorkingDir(String workingDir) throws SetupException {
		try {
			fileManager.setWorkingDirPath(workingDir);
		} catch (IOException e) {
			logger.error("Failed to set the working directory path");
			throw new SetupException("Unable to set the working directory path", e);
		}
	}

	@Override
	public void setInput(String inputPath) throws SetupException {
		File inputFile = null;
		try {
			inputFile = fileManager.setInputFilePath(inputPath);
		} catch (FileNotFoundException e) {
			logger.error("Failed to set the input directory path");
			throw new SetupException("Unable to set the input directory path", e);
		}
		// extract the ASM name from the file.
		this.asmName = inputFile.getName().replace(TranslatorConstants.ASM_EXTENSION, "");
		logger.debug("inputFile: {}", inputFile);
		logger.debug("asmName: {}", asmName);
	}

	@Override
	public void setOutput(String outputDir) throws SetupException {
		try {
			fileManager.setOutputFolder(outputDir);
		} catch (IOException e) {
			logger.error("Failed to set the output directory path");
			throw new SetupException("Unable to set the output directory path", e);
		}
	}

	@Override
	public void setJavaPath(String javaPath) throws SetupException {
		// set the java jdk folder path
		File javaJdkFolder = null;
		try {
			javaJdkFolder = fileManager.setJavaPath(javaPath);
		} catch (FileNotFoundException e) {
			logger.error("Failed to set the version of Java");
			throw new SetupException("Unable to set the version of Java", e);
		}

		// set the javaVersion
		this.javaVersion = getJdkVersion(javaJdkFolder);
		logger.info("Setting the java jdk version to: {}.", this.javaVersion);
	}

	@Override
	public void setEvosuitePath(String evosuitePath) throws SetupException {
		// set the evosuite jar folder path
		File evosuiteJarFolder = null;
		try {
			evosuiteJarFolder = fileManager.setEvosuitePath(evosuitePath);
		} catch (FileNotFoundException e) {
			logger.error("Failed to set the path to the evosuite folder");
			throw new SetupException("Unable to set the path to the folder of Evosuite jars", e);
		}

		// check if the given folder contains the current evosuite.jar
		for (File file : evosuiteJarFolder.listFiles()) {
			if (file.getName().equals(this.evosuiteVersion)) {
				logger.info("Evosuite jarfile: {} found inside the custom folder: {}.", this.evosuiteVersion,
						evosuiteJarFolder);
				// OK, the custom folder contains the evosuite.jar of interest.
				return;
			}
		}
		logger.error("Failed to locate the {} inside the custom folder,{}.", this.evosuiteVersion, evosuiteJarFolder);
		throw new SetupException(
				"Unable to access jarfile " + this.evosuiteVersion + " inside: " + evosuiteJarFolder);

	}

	@Override
	public void setOptions(String propertyName, String propertyValue) {
		logger.debug("Setting the option: {} to: {}.", propertyName, propertyValue);
		options.setValue(propertyName, Boolean.parseBoolean(propertyValue));
	}

	@Override
	public void setEvosuiteVersion(String evosuiteVersion) throws SetupException {
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
			logger.error("Valid evosuite versions are: 1.0.6, 1.2.0");
			throw new SetupException("Unexpected value: " + version);
		}
		logger.info("Setting the evosuite version: {} using: {}.", evosuiteVersion, this.evosuiteVersion);
		logger.info("Setting the java compiler version: {}.", this.compilerVersion);
	}

	@Override
	public void setTimeBudget(String timeBudget) throws SetupException {
		if (Integer.parseInt(timeBudget) < 1) {
			logger.error("Error while setting the timeBudget for Evosuite.");
			throw new SetupException("Time must be greater than 0.");
		}
		logger.info("Setting the timeBudget for Evosuite: {}.", timeBudget);
		this.searchBudget = timeBudget;
	}

	@Override
	public void generate() throws TranslationException {

		// check consistency between java and Evosuite version
		logger.info("Checking consistency between java and evosuite versions.");
		checkJavaConsistency();

		// translate to Java
		logger.info("Running Asmetal2Java:");
		executeAsmetal2Java();

		// executing Evouite
		logger.info("Running Evosuite:\n" + TranslatorConstants.EVOSUITE_ASCII_ART);
		executeEvosuite();

		// translate to Avalla
		logger.info("Running Junit2Avalla:\n");
		executeJunit2Avalla();
	}

	@Override
	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@Override
	public void clean() {
		logger.debug("Cleaning the resources...");
		cleanFolder(fileManager.getEvosuiteTestsPathToString());
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
		 * java.exe -jar <evosuiteJar> -target <workingDir>/evosuite/evosuite-target
		 * -class <evosuiteJavaInputFile>
		 * -Dtest_dir="<<workingDir>/evosuite/evosuite-tests>" -criterion LINE:BRANCH
		 * -Dminimize=true -Dassertion_strategy=all
		 * -Dreport_dir="<<workingDir>/evosuite/evosuite-report>"
		 */
		listOfOptions.addAll(List.of(fileManager.getJavaExePathToString(), TranslatorConstants.JAR, evosuiteJar,
				TranslatorConstants.TARGET, evosuiteTargetDir, TranslatorConstants.CLASS, evosuiteJavaInputFile,
				evosuiteTestsOption, TranslatorConstants.CRITERION, TranslatorConstants.LINE_BRANCH,
				TranslatorConstants.DMINIMIZE_TRUE, TranslatorConstants.DASSERTION_STRATEGY_ALL, evosuiteReportOption));

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

		String junit2AvallaWorkingDir = Paths
				.get(fileManager.getWorkingDirPathToString(), TranslatorConstants.JUNIT2AVALLA).toString();

		listOfOptions.addAll(List.of(TranslatorConstants.JUNIT2AVALLA_WORKING_DIR, junit2AvallaWorkingDir,
				TranslatorConstants.JUNIT2AVALLA_INPUT, junitInputFile, TranslatorConstants.JUNIT2AVALLA_OUTPUT,
				fileManager.getOutputFolderToString()));

		if (clean) {
			listOfOptions.add(TranslatorConstants.CLEAN);
		}

		logger.info("List of Junit2Avalla options: {}.", listOfOptions);

		return listOfOptions;
	}
	
	/**
	 * Execute the Asmetal2Java service
	 * 
	 * @throws TranslationException if an error occurs during the translation process.
	 */
	private void executeAsmetal2Java() throws TranslationException {
		try {
			Asmeta2JavaCLI.main(buildAsmeta2JavaOptions().toArray(new String[0]));
		} catch (RuntimeException e) {
			// catch the un-checked exceptions and throw the custom TranslationException
			logger.error("Runtime exception in Asmetal2Java: stopping the generation.");
			throw new TranslationException("Runtime exception in the Asmetal2Java service: ", e);
		}
		int returnCode = Asmeta2JavaCLI.getReturnedCode();
		if (returnCode != 0) {
			logger.error("Asmetal2Java terminated with errors: stopping the generation.");
			throw new TranslationException("Asmetal2Java exited with code: " + returnCode);
		}
	}

	/**
	 * Run Evosuite with a process builder.
	 * 
	 * @throws TranslationException if there is an error during the process.
	 */
	private void executeEvosuite() throws TranslationException {
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
			logger.error("Process was interrupted", e);
			// Clean up whatever needs to be handled before interrupting
			Thread.currentThread().interrupt();
			throw new TranslationException("Evosuite error. Process was interrupted.", e);
		} catch (IOException e) {
		    logger.error("I/O error during process execution", e);
		    throw new TranslationException("Evosuite I/O error.", e);
		} finally {
			// in any case clean the .class files
			logger.info("Cleaning the compiled files in: {}.", fileManager.getEvosuiteTargetPathToString());
			cleanFolder(fileManager.getEvosuiteTargetPathToString());
		}
	}
	
	/**
	 * Execute the Junit2Avalla service
	 * 
	 * @throws TranslationException if an error occurs during the translation process.
	 */
	private void executeJunit2Avalla() throws TranslationException {
		try {
			Junit2AvallaCLI.main(buildJunit2AvallaOptions().toArray(new String[0]));
		} catch (RuntimeException e) {
			// catch the un-checked exceptions and throw the custom TranslationException
			logger.error("Runtime exception in Junit2Avalla: stopping the generation.");
			throw new TranslationException("Runtime exception in the Junit2Avalla service: ", e);
		}
		int returnCode = Junit2AvallaCLI.getReturnedCode();
		if (returnCode != 0) {
			logger.error("Junit2Avalla terminated with errors: stopping the generation.");
			throw new TranslationException("Junit2AvallaCLI exited with code: " + returnCode);
		}
	}

	/**
	 * Clean the compiled .class files required by Evosutie.
	 * Don't stop the execution in case of errors.
	 * 
	 * @param folder String containing the folder name.
	 */
	private void cleanFolder(String folder) {
		try {
			File evosuiteTarget = new File(folder);
			logger.info("Cleaning the folder: {}.", evosuiteTarget.getAbsolutePath());
			if (evosuiteTarget.exists() && evosuiteTarget.isDirectory()) {
				for (File file : evosuiteTarget.listFiles()) {
					logger.debug("Cleaning the file: {}. ", file);
					Files.delete(file.toPath());
				}
			}
		} catch (IOException e) {
			// Don't stop the execution in case of errors.
			logger.error("Failed to clean the folder: {}, because of: {} ", folder, e.getMessage());
		}

	}

	/**
	 * Get java version of selected jave exe. Try to run java -version command first
	 * and then extract the version from process builder output, but if that fails
	 * extract java version directly from jdk folder
	 *
	 * @param javaJdkFolderPath path to the jdk folder.
	 * @return String containing the java version.
	 * @throws SetupException if occur an error getting the java version. 
	 */
	private String getJdkVersion(File javaJdkFolderPath) throws SetupException {

		List<String> command = List.of(fileManager.getJavaExePathToString(), TranslatorConstants.VERSION);
		ProcessBuilder processBuilder = new ProcessBuilder(command);

		try {
			// java -version uses the stderr stram
			Process process = processBuilder.start();
			String standardOutput = new String(process.getInputStream().readAllBytes());
			String errorOutput = new String(process.getErrorStream().readAllBytes());
			String output = standardOutput.isEmpty() ? errorOutput.stripTrailing() : standardOutput.stripTrailing();

			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new SetupException("Process builder error: Process exited with code " + exitCode);
			}

			logger.info("Executing the proces builder command:\n{} {}\n{}", fileManager.getJavaExePathToString(),
					TranslatorConstants.VERSION, output);
			javaVersion = getVersionFromOutput(output);
			if (javaVersion == null) {
				throw new SetupException(
						"Unable to extract Java version from the output of the process builder " + exitCode);
			}
			return extractVersionFromOutput(javaVersion);
		} catch (InterruptedException e) {
			logger.warn("Process was interrupted while retrieving JDK version", e);
			/* Clean up whatever needs to be handled before interrupting */
			Thread.currentThread().interrupt();
		} catch (IOException | SetupException e) {
			logger.warn("Failed to retrieve JDK version, fallback to folder extraction", e);
		}

		// fallback to folder extraction
		return extractJdkVersionFromFolder(javaJdkFolderPath);
	}

	/**
	 * Get the java version string (example: 1.8.3) from the java -version command
	 * output.
	 * 
	 * @param processBuilderOutput output string of the java -version process
	 *                             builder command.
	 * @return String containing only the java version, {@code null} if no version
	 *         found.
	 */
	private String getVersionFromOutput(String processBuilderOutput) {

		logger.info("Extracting the java version...");
		logger.debug("from:\n{}", processBuilderOutput);

		/*
		 * Pattern Explanation:
		 * 
		 * java version\\s+ : Matches the literal text "java version" followed by one or
		 * more spaces. 
		 * \" : Matches the opening double quote character (escaped with
		 * \). 
		 * ( : Starts capturing group 1, which will contain the entire version
		 * string. 
		 * \\d+ : Matches one or more digits (the major version number).
		 * (\\.\\d+)* : Matches zero or more sequences of a dot (.) followed by one or
		 * more digits (used for minor and patch version numbers). 
		 * (_\\d+)? : Matches an optional underscore (_) followed by one or more digits (used
		 *  for build numbers like in "1.8.0_411"). 
		 * ) : Ends capturing group 1. 
		 * \" : Matches the closing double quote character.
		 */
		Pattern versionPattern = Pattern.compile("java version\\s+\"(\\d+(\\.\\d+)*(_\\d+)?)\"");
		Matcher matcher = versionPattern.matcher(processBuilderOutput);

		if (matcher.find()) {
			// return the version found example: 1.8.3
			String version = matcher.group(1);
			logger.info("Found a match: {}", version);
			return version;
		} else {
			logger.error("Failed to extract Java version from the output of process builder: {}", processBuilderOutput);
			return null;
		}
	}

	/**
	 * Extract the java jdk version from the java version string.
	 * 
	 * @param version String containing the java version (ex: 1.8.3)
	 * @return String containing the recognized java version.
	 * @throws SetupException if the version is not recognized from the
	 *                                  application.
	 */
	private String extractVersionFromOutput(String version) throws SetupException {
		if (version.startsWith("1.8")) {
			return TranslatorConstants.JAVA_8;
		} else if (version.startsWith("9.")) {
			return TranslatorConstants.JAVA_9;
		} else {
			logger.error("Problem while setting the java jdk version from version output: {}.", version);
			throw new SetupException("jdk version not recognized: " + version);
		}
	}

	/**
	 * Extract the java jdk version from the jdk folder.
	 * 
	 * @param javaJdkFolderPath path to the jdk folder.
	 * @return String containing recognized the java version.
	 * @throws SetupException if the version is not recognized from the
	 *                                  application.
	 */
	private String extractJdkVersionFromFolder(File javaJdkFolderPath) throws SetupException {
		String jdk = javaJdkFolderPath.getName();
		if (jdk.contains("jdk-1.8")) {
			return TranslatorConstants.JAVA_8;
		} else if (jdk.contains("jdk-9")) {
			return TranslatorConstants.JAVA_9;
		} else {
			logger.error("Problem while setting the java jdk version from jdk folder: {}.", jdk);
			throw new SetupException("jdk version not recognized: " + jdk);
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
