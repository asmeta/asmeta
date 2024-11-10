package asmeta.evoasmetatg.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.asm2java.application.FileManager;
import org.asmeta.asm2java.config.ModeConstantsConfig;
import org.asmeta.asm2java.main.Asmeta2JavaCLI;

import asmeta.evoasmetatg.config.Options;
import asmeta.evoasmetatg.config.OptionsImpl;
import asmeta.junit2avalla.main.Junit2AvallaCLI;

public class TranslatorImpl implements Translator {

	private static final String LINE_BRANCH = "LINE:BRANCH";

	private static final String DASSERTION_STRATEGY_ALL = "-Dassertion_strategy=all";

	private static final String DMINIMIZE_TRUE = "-Dminimize=true";

	private static final String CRITERION = "-criterion";

	private static final String EVOSUITE_TESTS = "evosuite-tests";

	private static final String ASM = ".asm";
	
	private static final String ATG = FileManager.ATG;
	
	private static final String JUNIT_TEST_EXTENSION = ATG + "_ESTest.java";

	private static final String CLASS = "-class";

	private static final String TARGET = "-target";

	private static final String JAR = "-jar";

	private static final String JAVA_9 = "9";

	private static final String JAVA_8 = "8";

	private static final String EVOSUITE_1_2_0_JAR = "evosuite-1.2.0.jar";

	private static final String EVOSUITE_1_0_6_JAR = "evosuite-1.0.6.jar";

	private static final String DASH = "-";

	private static final String TEST_GEN = ModeConstantsConfig.TEST_GEN;

	private static final String MODE = DASH + Asmeta2JavaCLI.MODE;

	private static final String EVOSUITE_TARGET = "evosuite-target";

	private static final String ASMETA2JAVA_OUTPUT = DASH + Asmeta2JavaCLI.OUTPUT;

	private static final String ASMETA2JAVA_INPUT = DASH + Asmeta2JavaCLI.INPUT;

	private static final String JUNIT2AVALLA_OUTPUT = DASH + Junit2AvallaCLI.OUTPUT;

	private static final String JUNIT2AVALLA_INPUT = DASH + Junit2AvallaCLI.INPUT;

	private static final String CLEAN = "-clean";

	private static final String COMPILER_VERSION = DASH + Asmeta2JavaCLI.COMPILER_VERSION;
	
	private static final String EVOSUITE_ASCII_ART = """
			 _____                      _ _       
			| ____|_   _____  ___ _   _(_) |_ ___ 
			|  _| \\ \\ / / _ \\/ __| | | | | __/ _ \\
			| |___ \\ V / (_) \\__ \\ |_| | | ||  __/
			|_____| \\_/ \\___/|___/\\__,_|_|\\__\\___|
			""";

	/** Logger */
	private static final Logger logger = LogManager.getLogger(TranslatorImpl.class);

	/** Absolute path of the Asm input file */
	private String inputFilePath;
	
	private String asmName;

	/** Absolute path of the output folder */
	private String outputFolder;

	/** Absolute path of the Java executable used to run Evosuite. */
	private String javaExe;

	/** CLI Options */
	private Options options;

	private String evosuiteVersion;

	private String compilerVersion;

	private boolean clean;

	/**
	 * No args constructor. Create a new instance of Options.
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
		this.asmName = inputFile.getName().replace(ASM, "");
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
		File javaFile = new File(javaPath);
		if(!javaFile.exists() || !javaFile.isFile()) {
			throw new FileNotFoundException(javaPath);
		}
		this.javaExe = javaFile.getAbsolutePath();
		logger.info("Setting the path to the java exe: {}.", this.javaExe);
	}

	@Override
	public void setOptions(String propertyName, String propertyValue) {
		logger.info("Setting the option: {} to : {}.", propertyName , propertyValue);
		options.setValue(propertyValue, Boolean.parseBoolean(propertyValue));
	}

	@Override
	public void setEvosuiteVersion(String evosuiteVersion) {
		int version = Integer.parseInt((evosuiteVersion).replace(".", ""));
		switch (version) {
		case 106:
			this.compilerVersion = JAVA_8;
			this.evosuiteVersion = EVOSUITE_1_0_6_JAR;
			break;
		case 120:
			this.compilerVersion = JAVA_9;
			this.evosuiteVersion = EVOSUITE_1_2_0_JAR;
			break;
		default:
			logger.error("Evosuite version not valid: {}.", evosuiteVersion);
			throw new IllegalArgumentException("Unexpected value: " + version);
		}
		logger.info("Setting the evosuite version: {} using: {}." ,evosuiteVersion, this.evosuiteVersion);
		logger.info("Setting the java compiler version: {}.", this.compilerVersion);
	}

	@Override
	public void generate() throws TranslationException, IOException {
		
		// translate to java
		logger.info("Running Asmetal2Java:");
		Asmeta2JavaCLI.main(buildAsmeta2JavaOptions().toArray(new String[0]));

		// executing evouite
		logger.info("Running Evosuite:\n" + EVOSUITE_ASCII_ART);
		List<String> commands = buildEvosuiteOptions();
		logger.info("List of Evosuite options: {}", commands);

		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.inheritIO(); // show the output on the console
		try {
			Process process = pb.start();
			process.waitFor();
		} catch (InterruptedException e) {
			/* Clean up whatever needs to be handled before interrupting */
			Thread.currentThread().interrupt();
			throw new TranslationException("Evosuite error." + e.getMessage());
		}

		// translate to avalla
		logger.info("Running Junit2Avalla:\n");
		Junit2AvallaCLI.main(buildJunit2AvallaOptions().toArray(new String[0]));

	}

	private List<String> buildAsmeta2JavaOptions() {
		
		List<String> listOfOptions = new LinkedList<>();
		
		listOfOptions.addAll(List.of(ASMETA2JAVA_INPUT, inputFilePath, ASMETA2JAVA_OUTPUT, EVOSUITE_TARGET,
				MODE, TEST_GEN));
		
		listOfOptions.addAll(options.getCLIStringOptions());
		
		if (clean) {
			listOfOptions.add(CLEAN);
		}
		if(options.isCompiler()) {
			listOfOptions.add(COMPILER_VERSION);
			listOfOptions.add(compilerVersion);
		}
		
		logger.info("List of Asmeta2Java options: {}.", listOfOptions);
		
		return listOfOptions;
	}
	
	private List<String> buildEvosuiteOptions(){
		
		return Arrays.asList(javaExe, JAR, evosuiteVersion, TARGET, EVOSUITE_TARGET,
				CLASS, asmName + ATG , CRITERION, LINE_BRANCH, DMINIMIZE_TRUE,
				DASSERTION_STRATEGY_ALL);
	}

	private List<String> buildJunit2AvallaOptions() {
		
		List<String> listOfOptions = new LinkedList<>();
		
		String inputFile = EVOSUITE_TESTS + File.separator + asmName + JUNIT_TEST_EXTENSION;

		listOfOptions.addAll(List.of(JUNIT2AVALLA_INPUT, inputFile,	JUNIT2AVALLA_OUTPUT, outputFolder));
		
		if (clean) {
			listOfOptions.add(CLEAN);
		}
		
		logger.info("List of Junit2Avalla options: {}.", listOfOptions);
		
		return listOfOptions;
	}

	@Override
	public void setClean(boolean clean) {
		this.clean = clean;
	}
	
	@Override
	public void clean() {
		// TODO: clean up resources
	}
	

	@Override
	public List<String> getOptionNames() {
		return options.getOptionNames();
	}
	

	@Override
	public String getOptionsDescription() {
		return options.getDescription();
	}

}
