package asmeta.asm2java.main;

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

import asmeta.asm2java.application.AsmParsingException;
import asmeta.asm2java.application.SetupException;
import asmeta.asm2java.application.TranslationException;
import asmeta.asm2java.application.Translator;
import asmeta.asm2java.application.TranslatorImpl;

/**
 * The Asmeta2JavaCLI is the entry point for the Asmetal2Java application, which
 * translates ASM (Abstract State Machines) specifications into Java code. This
 * class provides methods to handle command-line arguments, parse ASM
 * specifications, generate Java code, and manage output directories.
 */
public class Asmeta2JavaCLI {

	private static final String GENERATION_FAILED = "Generation failed!";
	/* Constants */
	public static final String MODE = "mode";
	public static final String CLEAN = "clean";
	public static final String HELP = "help";
	public static final String WORKING_DIR = "workingDir";
	public static final String OUTPUT = "output";
	public static final String INPUT = "input";
	public static final String COMPILER_VERSION = "compilerVersion";
	private static final String DEBUG_LOG = "debug.log";
	private static final String LOGS = "logs";
	private static final String LOGFILE = "--logfile";

	/** Logger */
	private final Logger logger = LogManager.getLogger(Asmeta2JavaCLI.class);

	/** Translator instance for translating the asm specification. */
	private final Translator translator = new TranslatorImpl();

	/**
	 * Return code: <br>
	 * -1: The application didn't started yet.<br>
	 * 0: The application terminated without errors.<br>
	 * 1: The application terminated with errors. 2: The application terminated with
	 * errors related to the setup process. 3: The application terminated with
	 * errors related to the translation process. 4: The application terminated with
	 * errors related to the parsing process.
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
	 *         process. 4: The application terminated with errors related to the
	 *         parsing process.
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
		Asmeta2JavaCLI asmeta2JavaCLI = new Asmeta2JavaCLI();
		asmeta2JavaCLI.executeCLI(args);
	}

	/**
	 * Execute the Command Line Application (CLI) process.
	 * 
	 * @param args the command-line arguments.
	 */
	private void executeCLI(String[] args) {
		String asciiart = "\n    _                       _        _ ____   _                  \n"
				+ "   / \\   ___ _ __ ___   ___| |_ __ _| |___ \\ (_) __ ___   ____ _ \n"
				+ "  / _ \\ / __| '_ ` _ \\ / _ \\ __/ _` | | __) || |/ _` \\ \\ / / _` |\n"
				+ " / ___ \\\\__ \\ | | | | |  __/ || (_| | |/ __/ | | (_| |\\ V / (_| |\n"
				+ "/_/   \\_\\___/_| |_| |_|\\___|\\__\\__,_|_|_____|/ |\\__,_| \\_/ \\__,_|\n"
				+ "                                           |__/                  \n";
		Options options = getCommandLineOptions(translator.getModeDescription(), translator.getOptionsDescription());
		CommandLine line = this.parseCommandLine(args, options);
		logger.info(asciiart);
		HelpFormatter formatter = new HelpFormatter();
		// Do not sort
		formatter.setOptionComparator(null);
		// Header and footer strings
		String header = "Asmetal2Java\n\n";
		String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta "
				+ "for further information or to report an issue.";
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				formatter.printHelp("Asmetal2Java", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path with -{} <path/to/file.asm>.", INPUT);
				updateReturnCode(1); // error code
			} else {
				updateReturnCode(0); // ok code
				this.executeTranslation(line);
			}
		} catch (AsmParsingException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A parsing error occurred: {}", e.getMessage(), e);
			logger.warn("Please check the asmeta specification provided");
			updateReturnCode(4); // parsing error code
		} catch (SetupException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A setup error occurred: {}", e.getMessage(), e);
			logger.warn("Please check the parameters provided and consult the help message with -help:");
			formatter.printHelp("Asmetal2Java", header, options, footer, false);
			updateReturnCode(2); // setup error code
		} catch(TranslationException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A translation error occurred: {}", e.getMessage(), e);
			logger.info("Please report an isssue to the Asmeta team: https://github.com/asmeta/asmeta");
			updateReturnCode(3); // translation error code
		} catch (Exception e) {
			logger.error(GENERATION_FAILED);
			logger.error("An error occurred: {}.", e.getMessage(), e);
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
	 * @throws AsmParsingException if an error occurs during the parsing operation.
	 * @throws TranslationException if an error occurs during the translation operation.
	 * @throws SetupException      if an error occurs during the setup operation.
	 */
	private void executeTranslation(CommandLine line) throws AsmParsingException, SetupException, TranslationException {

		setGlobalProperties(line);

		if (line.hasOption(WORKING_DIR)) {
			translator.setWorkingDir(line.getOptionValue(WORKING_DIR));
		}

		// INPUT OPTION: by precondition -input option is always available and not null
		// (required)
		translator.setInput(line.getOptionValue(INPUT));

		if (line.hasOption(OUTPUT)) {
			translator.setOutput(line.getOptionValue(OUTPUT));
		}

		if (line.hasOption(MODE)) {
			translator.setMode(line.getOptionValue(MODE));
		}

		if (line.hasOption(COMPILER_VERSION)) {
			translator.setCompilerVersion(line.getOptionValue(COMPILER_VERSION));
		}

		// generate the translation
		translator.generate();
		logger.info("Generation succeed");

	}

	/**
	 * Creates and returns the available command-line options for the tool.
	 *
	 * @return the command-line options.
	 */
	private static Options getCommandLineOptions(String modeDescription, String optionsDescription) {
		Options options = new Options();

		// print help
		Option help = new Option(HELP, "print this message");

		// custom working directory
		Option workingDir = Option.builder(WORKING_DIR).argName(WORKING_DIR).type(String.class).hasArg(true)
				.desc("Custom working directory path (optional, defaults to `./input`)").build();

		// input file
		Option input = Option.builder(INPUT).argName(INPUT).type(String.class).hasArg(true)
				.desc("The ASMETA specification input file (required)").build();

		// output directory
		Option output = Option.builder(OUTPUT).argName(OUTPUT).type(String.class).hasArg(true)
				.desc("Custom output folder path (optional, defaults to `./output/`)").build();

		// clean the directories after use
		Option clean = Option.builder(CLEAN).hasArg(false)
				.desc("Delete the files used by the translator in the input folder, "
						+ "please make sure you have enabled the export property -Dexport=true")
				.build();

		// set the desired behavior
		Option mode = Option.builder(MODE).argName(MODE).type(String.class).hasArg(true)
				.desc("Set the mode of the application:\n" + modeDescription
						+ "Note: Please use the properties: -Dtranslator, -Dexecutable, -Dwindow and -DtestGen only if you have selected the -mode custom option.")
				.build();

		// compiler version
		Option compilerVersion = Option.builder(COMPILER_VERSION).argName(COMPILER_VERSION).type(String.class)
				.hasArg(true).desc("Set the version of the Java compiler used to compile the generated classes.")
				.build();

		// translator property
		Option property = Option.builder("D").numberOfArgs(2).argName("property=value").valueSeparator('=')
				.required(false).optionalArg(false).type(String.class).desc(optionsDescription).build();

		options.addOption(help);
		options.addOption(workingDir);
		options.addOption(input);
		options.addOption(output);
		options.addOption(clean);
		options.addOption(mode);
		options.addOption(compilerVersion);
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
				logger.error("* Unknown property: {}.", propertyName);
				continue;
			}

			String propertyValue = properties.getProperty(propertyName);

			try {
				translator.setOptions(propertyName, propertyValue);

			} catch (Exception e) {
				logger.error("Invalid value for property {}: {}", propertyName, propertyValue);
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
