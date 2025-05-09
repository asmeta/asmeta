package asmeta.evotest.evoasmetatg.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.evoasmetatg.application.SetupException;
import asmeta.evotest.evoasmetatg.application.TranslationException;
import asmeta.evotest.evoasmetatg.application.Translator;
import asmeta.evotest.evoasmetatg.application.TranslatorImpl;
import asmeta.evotest.junit2avalla.javascenario.ParserType;
import asmeta.evotest.junit2avalla.main.Junit2AvallaCLI;

/**
 * This is the main class of the application which serves as the entry point.
 */
public class EvoAsmetaTgCLI {

	/* Constants */
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	public static final String WORKING_DIR = "workingDir";
	public static final String CLEAN = "clean";
	public static final String HELP = "help";
	public static final String JAVA_PATH = "javaPath";
	public static final String EVOSUITE_PATH = "evosuitePath";
	public static final String EVOSUITE_VERSION = "evosuiteVersion";
	public static final String TIME_BUDGET = "timeBudget";
	private static final String GENERATION_FAILED = "Generation failed!";
	private static final String DEBUG_LOG = "debug.log";
	private static final String LOGS = "logs";
	private static final String LOGFILE = "--logfile";

	/** Logger */
	private final Logger logger = LogManager.getLogger(EvoAsmetaTgCLI.class);

	/** Translator instance for translating the asm specification. */
	private final Translator translator = new TranslatorImpl();

	/**
	 * Return code: <br>
	 * -1: The application didn't started yet.<br>
	 * 0: The application terminated without errors.<br>
	 * 1: The application terminated with errors. 2: The application terminated with
	 * errors related to the setup process. 3: The application terminated with
	 * errors related to the translation process.
	 */
	private static int returnCode = -1;

	/**
	 * Get the code returned by the application.
	 * 
	 * @return -1: The application didn't started yet.<br>
	 *         0: The application terminated without errors.<br>
	 *         1: The application terminated with errors. 2: The application
	 *         terminated with errors related to the setup process. 3: The
	 *         application terminated with errors related to the translation
	 *         process.
	 */
	public static int getReturnedCode() {
		return returnCode;
	}

	/**
	 * The main method, which is the entry point of the application. It parses the
	 * command-line arguments, handles the help option, and triggers the execution
	 * of the main process.
	 *
	 * @param args the command-line arguments.
	 */
	public static void main(String[] args) {
		// Set logger properties before creating an instance of any logger.
		setLoggerProperties(args);
		EvoAsmetaTgCLI evoAsmetaTgCLI = new EvoAsmetaTgCLI();
		evoAsmetaTgCLI.executeCLI(args);
	}

	/**
	 * Execute the Command Line Application (CLI) process.
	 * 
	 * @param args the command-line arguments.
	 */
	private void executeCLI(String[] args) {
		String asciiart = """

				 _____              _                       _       _____ ____
				| ____|_   _____   / \\   ___ _ __ ___   ___| |_ __ |_   _/ ___|
				|  _| \\ \\ / / _ \\ / _ \\ / __| '_ ` _ \\ / _ \\ __/ _` || || |  _
				| |___ \\ V / (_) / ___ \\\\__ \\ | | | | |  __/ || (_| || || |_| |
				|_____| \\_/ \\___/_/   \\_\\___/_| |_| |_|\\___|\\__\\__,_||_| \\____|
				""";
		logger.info(asciiart);
		Options options = getCommandLineOptions(translator.getOptionsDescription());
		CommandLine line = this.parseCommandLine(args, options);
		HelpFormatter formatter = new HelpFormatter();
		// Do not sort
		formatter.setOptionComparator(null);
		// Header and footer strings
		String header = "EvoAsmetaTG\n\n";
		String footer = "Note: If your argument is a string and contains a space, put it in double quotes like \"Program Files\"\n"
				+ "This project is part of Asmeta, see https://github.com/asmeta/asmeta "
				+ "for further information or to report an issue.";
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				formatter.printHelp("EvoAsmetaTG", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path with -{} <path/to/file.asm>.", INPUT);
				updateReturnCode(2); // setup error code
			} else if (!line.hasOption(JAVA_PATH)) {
				logger.error("Please specify the path to the jdk folder with -{} <path/to/jdk/>.", JAVA_PATH);
				updateReturnCode(2); // setup error code
			} else if (!line.hasOption(EVOSUITE_VERSION)) {
				logger.error("Please specify the version of Evosuite with: -{} <version>.", EVOSUITE_VERSION);
				updateReturnCode(2); // setup error code
			} else {
				updateReturnCode(0); // ok code
				this.executeGeneration(line);
			}
		} catch (SetupException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A setup error occurred: {}", e.getMessage(), e);
			logger.warn("Please check the parameters provided and consult the help message with -help:");
			formatter.printHelp("EvoAsmetaTG", header, options, footer, false);
			updateReturnCode(2); // setup error code
		} catch (TranslationException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A translation error occurred: {}", e.getMessage(), e);
			logger.info("Please report an isssue to the Asmeta team: https://github.com/asmeta/asmeta");
			updateReturnCode(3); // translation error code
		} catch (Exception e) {
			logger.error(GENERATION_FAILED);
			logger.error("An error occurred: {}", e.getMessage(), e);
			updateReturnCode(1); // error code
		} finally {
			if (line != null && line.hasOption(CLEAN)) {
				translator.clean();
			}
			logger.info("Requested operation completed.");
		}
	}

	/**
	 * Update the static field returnCode from a non-static method.
	 * 
	 * @param code the code returned by the application.
	 */
	private static synchronized void updateReturnCode(int code) {
		returnCode = code;
	}

	/**
	 * Executes the main process based on the command-line arguments.
	 *
	 * @param line the parsed CommandLine object.
	 * @throws SetupException       if there are errors during the setup process.
	 * @throws TranslationException if there was an error during the generation
	 *                              process.
	 */
	private void executeGeneration(CommandLine line) throws SetupException, TranslationException {

		// set the properties with the -D format
		setGlobalProperties(line);

		if (line.hasOption(WORKING_DIR)) {
			translator.setWorkingDir(line.getOptionValue(WORKING_DIR));
		}

		// INPUT OPTION: by precondition -input option is always available and not null
		translator.setInput(line.getOptionValue(INPUT));

		// JAVA_PATH OPTION: by precondition -javaPath option is always available and
		// not null
		translator.setJavaPath(line.getOptionValue(JAVA_PATH));

		// EVOSUITE_VERSION OPTION: by precondition -evosuiteVersion option is always
		// available and not null
		translator.setEvosuiteVersion(line.getOptionValue(EVOSUITE_VERSION));

		if (line.hasOption(EVOSUITE_PATH)) {
			translator.setEvosuitePath(line.getOptionValue(EVOSUITE_PATH));
		}

		if (line.hasOption(OUTPUT)) {
			translator.setOutput(line.getOptionValue(OUTPUT));
		}

		if (line.hasOption(CLEAN)) {
			translator.setClean(true);
		}

		if (line.hasOption(TIME_BUDGET)) {
			translator.setTimeBudget(line.getOptionValue(TIME_BUDGET));
		}
		
		if (line.hasOption(Junit2AvallaCLI.PARSER)) {
			translator.setParserType(line.getOptionValue(Junit2AvallaCLI.PARSER));
		}

		translator.generate();
		logger.info("Generation succeed");

	}

	/**
	 * Creates and returns the available command-line options for the tool.
	 *
	 * @param propertyDescription the description of the option "property".
	 * @return the command-line options.
	 */
	private static Options getCommandLineOptions(String propertyDescription) {
		Options options = new Options();

		// print help
		Option help = new Option(HELP, "print this message");

		// custom working directory
		Option workingDir = Option.builder(WORKING_DIR).argName(WORKING_DIR).type(String.class).hasArg(true)
				.desc("Custom working directory path (optional, defaults to `.`)").build();

		// input file
		Option input = Option.builder(INPUT).argName(INPUT).type(String.class).hasArg(true)
				.desc("Set the path to the ASM input file (required).").build();

		// output directory
		Option output = Option.builder(OUTPUT).argName(OUTPUT).type(String.class).hasArg(true)
				.desc("The output folder (optional, default to `./output/`)").build();

		// java path
		Option javaPath = Option.builder(JAVA_PATH).argName(JAVA_PATH).type(String.class).hasArg(true)
				.desc("Set the path of java jdk folder used to run Evosuite (required).\n"
						+ " Example: \"C:\\Program Files\\Java\\jdk-1.8\"")
				.build();

		// Evosuite path
		Option evosuitePath = Option.builder(EVOSUITE_PATH).argName(EVOSUITE_PATH).type(String.class).hasArg(true)
				.desc("Set the path of Evosuite jar folder (optional, defaults to `./evosuite/evosuite-jar`).").build();

		// compiler version
		Option evosuiteVersion = Option.builder(EVOSUITE_VERSION).argName(EVOSUITE_VERSION).type(String.class)
				.hasArg(true).desc("Set the version of Evosuite to use for test scenarios generation (required).")
				.build();

		// clean the directories after use
		Option clean = Option.builder(CLEAN).hasArg(false)
				.desc("Delete all intermediate files created and processed by the application.").build();

		// timeBudget property
		Option timeBudget = Option.builder(TIME_BUDGET).argName(TIME_BUDGET).type(Integer.class).hasArg(true)
				.desc("Set the time budget allocated for the Evosuite process.").build();
		
		// parser type property
		Option parser = Option.builder(Junit2AvallaCLI.PARSER).argName(Junit2AvallaCLI.PARSER).type(String.class).hasArg(true)
				.desc(ParserType.getDescrition() + " (optional, defaults to customParser)").build();

		// translator property
		Option property = Option.builder("D").numberOfArgs(2).argName("property=value").valueSeparator('=')
				.required(false).optionalArg(false).type(String.class).desc(propertyDescription).build();

		options.addOption(help);
		options.addOption(workingDir);
		options.addOption(input);
		options.addOption(output);
		options.addOption(javaPath);
		options.addOption(evosuitePath);
		options.addOption(evosuiteVersion);
		options.addOption(clean);
		options.addOption(timeBudget);
		options.addOption(parser);
		options.addOption(property);
		

		return options;
	}

	/**
	 * Parses the command-line arguments using the provided options.
	 *
	 * @param args    the command-line arguments.
	 * @param options the available command-line options.
	 * @return the parsed CommandLine object.
	 */
	private CommandLine parseCommandLine(String[] args, Options options) {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			logger.error("Failed to parse commandline arguments.");
		}
		return line;
	}

	/**
	 * Sets global translator properties based on the command-line options.
	 *
	 * @param line the parsed CommandLine object.
	 */
	private void setGlobalProperties(CommandLine line) {
		logger.info("Parsing the global properties:");
		Properties properties = line.getOptionProperties("D");
		Set<String> propertyNames = new HashSet<>(translator.getOptionNames());

		for (String propertyName : properties.stringPropertyNames()) {

			if (!propertyNames.contains(propertyName)) {
				logger.error("* Unknown property: {}. ", propertyName);
				continue;
			}

			String propertyValue = properties.getProperty(propertyName);

			try {
				translator.setOptions(propertyName, propertyValue);

			} catch (Exception e) {
				logger.error("Invalid value for property {}: {}.", propertyName, propertyValue);
			}
		}

	}

	/**
	 * Set the logger properties by Main Argument Lookup:
	 * <p>
	 * - set a custom path for the log file in the user-defined workingDir.
	 * 
	 * @param args the command-line arguments.
	 */
	private static void setLoggerProperties(String[] args) {
		List<String> argList = Arrays.asList(args);

		int index = argList.indexOf("-" + WORKING_DIR);

		// only if a custom working directory is selected
		if (index != -1 && index + 1 < argList.size()) {
			Path logFilePath = Paths.get(argList.get(index + 1), LOGS, DEBUG_LOG);
			// generates the argument --logfile <workingDir>/logs/debug.log
			try {
				Class.forName("org.apache.logging.log4j.core.lookup.MainMapLookup")
						.getDeclaredMethod("setMainArguments", String[].class)
						.invoke(null, (Object) new String[] { LOGFILE, logFilePath.toString() });
			} catch (final ReflectiveOperationException e) {
				// Log4j Core is not used.
			}
		}
	}

}
