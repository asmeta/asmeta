package asmeta.evotest.junit2avalla.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.application.SetupException;
import asmeta.evotest.junit2avalla.application.TranslationException;
import asmeta.evotest.junit2avalla.application.Translator;
import asmeta.evotest.junit2avalla.application.TranslatorImpl;
import asmeta.evotest.junit2avalla.javascenario.JUnitParseException;
import asmeta.evotest.junit2avalla.javascenario.ParserType;

/**
 * This is the main class of the application which serves as the entry point.
 */
public class Junit2AvallaCLI {

	/* Constants */
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	public static final String WORKING_DIR = "workingDir";
	public static final String CLEAN = "clean";
	public static final String PARSER = "parser";
	public static final String HELP = "help";
	private static final String DEBUG_LOG = "debug.log";
	private static final String LOGS = "logs";
	private static final String LOGFILE = "--logfile";
	private static final String GENERATION_FAILED = "Generation failed!";

	/** Logger */
	private final Logger logger = LogManager.getLogger(Junit2AvallaCLI.class);

	/** Translator */
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
	 * The main method is the entry point of the application.
	 *
	 * @param args An array of {@code String} arguments passed from the command
	 *             line.
	 */
	public static void main(String[] args) {
		// Set logger properties before creating an instance of any logger.
		setLoggerProperties(args);
		Junit2AvallaCLI junit2AvallaCLI = new Junit2AvallaCLI();
		junit2AvallaCLI.executeCLI(args);
	}

	/**
	 * Execute the Command Line Application (CLI) process.
	 * 
	 * @param args the command-line arguments.
	 */
	private void executeCLI(String[] args) {
		String asciiart = """

				     _             _ _   ____     _             _ _
				    | |_   _ _ __ (_) |_|___ \\   / \\__   ____ _| | | __ _
				 _  | | | | | '_ \\| | __| __) | / _ \\ \\ / / _` | | |/ _` |
				| |_| | |_| | | | | | |_ / __/ / ___ \\ V / (_| | | | (_| |
				 \\___/ \\__,_|_| |_|_|\\__|_____/_/   \\_\\_/ \\__,_|_|_|\\__,_|
				 """;
		Options options = getCommandLineOptions();
		CommandLine line = this.parseCommandLine(args, options);
		logger.info(asciiart);
		HelpFormatter formatter = new HelpFormatter();
		// Do not sort
		formatter.setOptionComparator(null);
		// Header and footer strings
		String header = "Junit2Avalla\n\n";
		String footer = "\n";
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				formatter.printHelp("Junit2Avalla", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path with -{} <path/to/file.asm>.", INPUT);
				updateReturnCode(1); // error code
			} else {
				updateReturnCode(0); // ok code
				this.executeTranslation(line);
			}
		} catch (SetupException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A setup error occurred: {}", e.getMessage(), e);
			logger.warn("Please check the parameters provided and consult the help message with -help:");
			formatter.printHelp("Junit2Avalla", header, options, footer, false);
			updateReturnCode(2); // setup error code
		} catch (JUnitParseException e) {
			logger.error(GENERATION_FAILED);
			logger.error("A parse error occurred: {}", e.getMessage(), e);
			logger.info("Please report an isssue to the Asmeta team: https://github.com/asmeta/asmeta");
			updateReturnCode(4); // parse error code
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
	 * Creates and returns the available command-line options for the tool.
	 *
	 * @return the command-line options.
	 */
	public static Options getCommandLineOptions() {
		Options options = new Options();

		// print help
		Option help = new Option(HELP, "print this message");

		// input file
		Option workingDir = Option.builder(WORKING_DIR).argName(WORKING_DIR).type(String.class).hasArg(true)
				.desc("The working directory for intermediate files (optional, Defaults to `./input/`)").build();

		// input file
		Option input = Option.builder(INPUT).argName(INPUT).type(String.class).hasArg(true)
				.desc("The Java input file (required)").build();

		// output directory
		Option output = Option.builder(OUTPUT).argName(OUTPUT).type(String.class).hasArg(true)
				.desc("The output folder (optional, defaults to `./output/`)").build();

		// parser
		Option parser = Option.builder(PARSER).argName(PARSER).type(String.class).hasArg(true)
				.desc(ParserType.getDescrition() + " (optional, defaults to customParser)").build();

		// clean option
		Option clean = Option.builder(CLEAN).hasArg(false).desc("Clean the input and the stepFunctionArgs files.")
				.build();

		options.addOption(help);
		options.addOption(workingDir);
		options.addOption(input);
		options.addOption(output);
		options.addOption(parser);
		options.addOption(clean);

		return options;
	}

	/**
	 * Parses the command-line arguments using the provided options.
	 *
	 * @param args    the command-line arguments.
	 * @param options the available command-line options.
	 * @return the parsed CommandLine object.
	 */
	public CommandLine parseCommandLine(String[] args, Options options) {
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
	 * Executes the main process based on the command-line arguments.
	 *
	 * @param line the parsed CommandLine object.
	 * @throws SetupException       if there are errors during the setup process.
	 * @throws TranslationException if there was an error during the generation
	 *                              process.
	 * @throws JUnitParseException  if an error occurs during the parsing process.
	 */
	private void executeTranslation(CommandLine line) throws SetupException, TranslationException, JUnitParseException {

		if (line.hasOption(WORKING_DIR)) {
			translator.setWorkingDir(line.getOptionValue(WORKING_DIR));
		}

		// INPUT OPTION: by precondition -input option is always available and not null
		// (required)
		translator.setInput(line.getOptionValue(INPUT));

		if (line.hasOption(OUTPUT)) {
			translator.setOutput(line.getOptionValue(OUTPUT));
		}

		if (line.hasOption(PARSER)) {
			translator.setParser(line.getOptionValue(PARSER));
		}

		translator.generate();

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