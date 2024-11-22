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

	/** Absolute path of the Asm input file. */
	private String inputFilePath;

	/** Name of the Asm input file. */
	private String asmName;

	/** Absolute path of the output folder */
	private String outputFolder;

	/** Absolute path of the Java executable used to run Evosuite. */
	private String javaExe;

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

	/** indicates whether to clean the folders {@code true} or not {@code false} */
	private boolean clean;

	/**
	 * No args constructor. Create a new instance of Options and initialize the
	 * clean parameter to false.
	 */
	public TranslatorImpl() {
		this.options = new OptionsImpl();
		this.clean = false;
	}

	@Override
	public void setInput(String inputPath) throws FileNotFoundException {
		File inputFile = new File(inputPath);
		if (!inputFile.exists() || !inputFile.isFile()) {
			logger.error("Can't find the specified input File: {} ", inputPath);
			logger.error("Please digit the absolute path of the file in question.");
			throw new FileNotFoundException(inputPath);
		}
		this.inputFilePath = inputFile.getAbsolutePath();
		this.asmName = inputFile.getName().replace(TranslatorConstants.ASM, "");
		logger.info("Setting the input file: {}.", inputFilePath);
		logger.debug("inputFile: {}", inputFile);
		logger.debug("asmName: {}", asmName);
		File inputFolder = inputFile.getParentFile();
		if (inputFolder.isDirectory()) {
			this.outputFolder = inputFolder.getAbsolutePath();
			logger.info("Setting the default output folder: {}.", outputFolder);
		}
	}

	@Override
	public void setOutput(String outputDir) throws IOException {
		File outputDirFile = new File(outputDir);
		if (!outputDirFile.exists() || !outputDirFile.isDirectory()) {
			Files.createDirectories(outputDirFile.toPath());
		}
		outputFolder = outputDirFile.getAbsolutePath();
		logger.info("Setting the output folder: {}.", this.outputFolder);
	}

	@Override
	public void setJavaPath(String javaPath) throws FileNotFoundException {
		File javaJdkFolder = new File(javaPath);
		if (!javaJdkFolder.exists() || !javaJdkFolder.isDirectory()) {
			logger.error("Java jdk directory location not valid: {}.", javaJdkFolder.getAbsolutePath());
			logger.error("Please note: If your argument is a string and contains a space, put it in double quotes like \"Program Files\"");
			throw new FileNotFoundException("File not found: " + javaPath);
		}
		logger.info("Java jdk directory found at: {}.", javaJdkFolder.getAbsolutePath());
		File javaFile = new File(Paths.get(javaPath, TranslatorConstants.BIN, TranslatorConstants.JAVA_EXE).toString());
		if (!javaFile.exists() || !javaFile.isFile()) {
			logger.error("Java exe file location not valid: {}.", javaFile.getAbsolutePath());
			throw new FileNotFoundException("File not found: " + javaPath);
		}
		this.javaExe = javaFile.getAbsolutePath();
		logger.info("Setting the path to the java exe: {}.", this.javaExe);

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

		// check consistency between java and evosuite version
		if (!checkJavaConsistency()) {
			logger.error("Found Inconsistency between java and evosuite versions.");
			throw new TranslationException("There is no consistency between the java version " + javaVersion
					+ " and the Evosuite version " + evosuiteVersion + " that uses the version " + compilerVersion);
		}

		// translate to Java
		logger.info("Running Asmetal2Java:");
		Asmeta2JavaCLI.main(buildAsmeta2JavaOptions().toArray(new String[0]));

		// executing Evouite
		logger.info("Running Evosuite:\n" + TranslatorConstants.EVOSUITE_ASCII_ART);
		List<String> commands = buildEvosuiteOptions();
		logger.info("List of Evosuite options: {}", commands);

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.inheritIO(); // show the output on the console
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
			throw new TranslationException("Evosuite error." + e.getMessage());
		} finally {
			logger.info("Cleaning the compiled files in: {}.", TranslatorConstants.EVOSUITE_TARGET);
			cleanFolder(TranslatorConstants.EVOSUITE_TARGET);
		}

		// translate to Avalla
		logger.info("Running Junit2Avalla:\n");
		Junit2AvallaCLI.main(buildJunit2AvallaOptions().toArray(new String[0]));

	}

	@Override
	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@Override
	public void clean() {
		logger.debug("Cleaning the resources...");
		cleanFolderManagingException(TranslatorConstants.EVOSUITE_TESTS);
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

		List<String> listOfOptions = new LinkedList<>();

		listOfOptions.addAll(
				List.of(TranslatorConstants.ASMETA2JAVA_INPUT, inputFilePath, TranslatorConstants.ASMETA2JAVA_OUTPUT,
						TranslatorConstants.EVOSUITE_TARGET, TranslatorConstants.MODE, TranslatorConstants.TEST_GEN));

		listOfOptions.addAll(options.getCLIStringOptions());

		if (clean) {
			listOfOptions.add(TranslatorConstants.CLEAN);
		}
		if (options.isCompiler()) {
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

		listOfOptions.addAll(List.of(javaExe, TranslatorConstants.JAR, evosuiteVersion, TranslatorConstants.TARGET,
				TranslatorConstants.EVOSUITE_TARGET, TranslatorConstants.CLASS, asmName + TranslatorConstants.ATG,
				TranslatorConstants.CRITERION, TranslatorConstants.LINE_BRANCH, TranslatorConstants.DMINIMIZE_TRUE,
				TranslatorConstants.DASSERTION_STRATEGY_ALL));

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

		String inputFile = TranslatorConstants.EVOSUITE_TESTS + File.separator + asmName
				+ TranslatorConstants.JUNIT_TEST_EXTENSION;

		listOfOptions.addAll(List.of(TranslatorConstants.JUNIT2AVALLA_INPUT, inputFile,
				TranslatorConstants.JUNIT2AVALLA_OUTPUT, outputFolder));

		if (clean) {
			listOfOptions.add(TranslatorConstants.CLEAN);
		}

		logger.info("List of Junit2Avalla options: {}.", listOfOptions);

		return listOfOptions;
	}

	/**
	 * Clean the compiled .class files required by Evosutie.
	 * 
	 * @param folder String containing the folder name.
	 * @throws IOException
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
	 * @return {@code true} if there is consistency between the java versions,
	 *         {@code false} otherwise.
	 */
	private boolean checkJavaConsistency() {
		return javaVersion.equals(compilerVersion);
	}

}
