package org.asmeta.junit2avalla.main;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.junit2avalla.application.Translator;
import org.asmeta.junit2avalla.application.TranslatorImpl;

/**
 * This is the main class of the application which serves as the entry point.
 */
public class Junit2AvallaCLI {

	/* Constants */
	public static final String INPUT = "input";
	public static final String OUTPUT = "output";
	public static final String CLEAN = "clean";
	public static final String HELP = "help";

	/** Logger */
	private static final Logger log = LogManager.getLogger(Junit2AvallaCLI.class);

	/** Translator */
	private static final Translator translator = new TranslatorImpl();	
	
	/**
	 * Return code: <br>
	 * -1: The application didn't started yet.<br>
	 * 0: The application terminated without errors.<br>
	 * 1: The application terminated with errors.
	 */
	private static int returnCode = -1;

	/**
	 * Get the code returned by the application.
	 * 
	 * @return -1: The application didn't started yet.<br>
	 *         0: The application terminated without errors.<br>
	 *         1: The application terminated with errors.
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
		String asciiart =  """
				
				     _             _ _   ____     _             _ _       
				    | |_   _ _ __ (_) |_|___ \\   / \\__   ____ _| | | __ _ 
				 _  | | | | | '_ \\| | __| __) | / _ \\ \\ / / _` | | |/ _` |
				| |_| | |_| | | | | | |_ / __/ / ___ \\ V / (_| | | | (_| |
				 \\___/ \\__,_|_| |_|_|\\__|_____/_/   \\_\\_/ \\__,_|_|_|\\__,_|
				 """;

		Junit2AvallaCLI main = new Junit2AvallaCLI();
		Options options = getCommandLineOptions();
		CommandLine line = main.parseCommandLine(args, options);
		log.info(asciiart);
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "AvallaToJava\n\n";
				String footer = "\n";

				formatter.printHelp("AvallaToJava", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				log.error("Please specify the asm input file path with -{} <path/to/file.asm>.", INPUT);
				returnCode = 1; // error code
			} else {
				returnCode = 0; // ok code
				main.execute(line);
			}
		} catch (Exception e) {
			log.error("Generation failed");
			log.error("An error occurred, {}", e.getMessage(), e);
			returnCode = 1; // error code
		}  finally {
			if (line != null && line.hasOption(CLEAN)) {
				translator.clean();
			}
			log.info("Requested operation completed.");
		}

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
		Option input = Option.builder(INPUT).argName(INPUT).type(String.class).hasArg(true)
				.desc("The Java input file (required)").build();

		// output directory
		Option output = Option.builder(OUTPUT).argName(OUTPUT).type(String.class).hasArg(true)
				.desc("The output folder (optional, defaults to `./output/`)").build();

		// clean option
		Option clean = Option.builder(CLEAN).hasArg(false)
				.desc("Clean the input and the stepFunctionArgs files.").build();

		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
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
			log.error("Failed to parse commandline arguments.");
		}
		return line;
	}

	/**
	 * Executes the main process based on the command-line arguments.
	 *
	 * @param line    the parsed CommandLine object.
	 * @throws IOException 
	 */
	private void execute(CommandLine line) throws IOException {

		// INPUT OPTION: by precondition -input option is always available and not null
		// (required)
		translator.setInput(line.getOptionValue(INPUT));
		
		if (line.hasOption(OUTPUT)) {
			translator.setOutput(line.getOptionValue(OUTPUT));
		}

		translator.generate();

	}


}