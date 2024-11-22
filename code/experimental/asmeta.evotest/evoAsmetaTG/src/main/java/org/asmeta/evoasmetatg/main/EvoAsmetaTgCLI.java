package org.asmeta.evoasmetatg.main;

import java.io.IOException;
import java.util.HashSet;
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
import org.asmeta.evoasmetatg.application.TranslationException;
import org.asmeta.evoasmetatg.application.Translator;
import org.asmeta.evoasmetatg.application.TranslatorImpl;

/**
 * This is the main class of the application which serves as the entry point.
 */
public class EvoAsmetaTgCLI {

	/* Constants */
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	private static final String CLEAN = "clean";
	private static final String HELP = "help";
	private static final String JAVA_PATH = "javaPath";
	private static final String EVOSUITE_VERSION = "evosuiteVersion";

	/** Logger */
	private static final Logger logger = LogManager.getLogger(EvoAsmetaTgCLI.class);

	/** Translator instance for translating the asm specification. */
	private static final Translator translator = new TranslatorImpl();

	/**
	 * The main method, which is the entry point of the application. It parses the
	 * command-line arguments, handles the help option, and triggers the execution
	 * of the main process.
	 *
	 * @param args the command-line arguments.
	 */
	public static void main(String[] args) {
		String asciiart = """
				
				 _____              _                       _       _____ ____ 
				| ____|_   _____   / \\   ___ _ __ ___   ___| |_ __ |_   _/ ___|
				|  _| \\ \\ / / _ \\ / _ \\ / __| '_ ` _ \\ / _ \\ __/ _` || || |  _ 
				| |___ \\ V / (_) / ___ \\\\__ \\ | | | | |  __/ || (_| || || |_| |
				|_____| \\_/ \\___/_/   \\_\\___/_| |_| |_|\\___|\\__\\__,_||_| \\____|
				""" ;
		EvoAsmetaTgCLI main = new EvoAsmetaTgCLI();
		Options options = getCommandLineOptions();
		CommandLine line = main.parseCommandLine(args, options);
		logger.info(asciiart);
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "EvoAsmetaTG\n\n";
				String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta "
						+ "for further information or to report an issue.";

				formatter.printHelp("EvoAsnetaTG", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path.");
			} else {
				main.execute(line);
			}
		} catch (Exception e) {
			logger.error("Generation failed.");
			logger.error("An error occurred: {}.", e.getMessage());
		} finally {
			if (line != null && line.hasOption(CLEAN)) {
				translator.clean();
			}
			logger.info("Requested operation completed.");
		}
	}

	/**
	 * Executes the main process based on the command-line arguments.
	 *
	 * @param line the parsed CommandLine object.
	 * @throws IOException
	 * @throws TranslationException 
	 * @throws AsmParsingException
	 */
	private void execute(CommandLine line) throws IOException, TranslationException {

		setGlobalProperties(line);

		// INPUT OPTION: by precondition -input option is always available and not null
		// (required)
		translator.setInput(line.getOptionValue(INPUT));

		if (line.hasOption(OUTPUT)) {
			translator.setOutput(line.getOptionValue(OUTPUT));
		}
		
		if (line.hasOption(JAVA_PATH)) {
			translator.setJavaPath(line.getOptionValue(JAVA_PATH));
		}

		if (line.hasOption(EVOSUITE_VERSION)) {
			translator.setEvosuiteVersion(line.getOptionValue(EVOSUITE_VERSION));
		}
		
		if (line.hasOption(CLEAN)) {
			translator.setClean(true);
		}

		translator.generate();
		logger.info("Generation succeed");

	}

	/**
	 * Creates and returns the available command-line options for the tool.
	 *
	 * @return the command-line options.
	 */
	private static Options getCommandLineOptions() {
		Options options = new Options();

		// print help
		Option help = new Option(HELP, "print this message");

		// input file
		Option input = Option.builder(INPUT).argName(INPUT).type(String.class).hasArg(true)
				.desc("The ASM input file (required)").build();

		// output directory
		Option output = Option.builder(OUTPUT).argName(OUTPUT).type(String.class).hasArg(true)
				.desc("The output folder (optional, defaults to `./output/`)").build();

		// java path
		Option javaPath = Option.builder(JAVA_PATH).argName(JAVA_PATH).type(String.class)
				.hasArg(true).desc("Set the path of java jdk folder used to run Evosuite.\n"
						+ " Example: \"C:\\Program Files\\Java\\jdk-1.8\"").build();
		
		// compiler version
		Option evosuiteVersion = Option.builder(EVOSUITE_VERSION).argName(EVOSUITE_VERSION).type(String.class)
				.hasArg(true).desc("Set the version of Evosuite.").build();

		// clean the directories after use
		Option clean = Option.builder(CLEAN).hasArg(false)
				.desc("Delete the files used by the translator in the input folder, "
						+ "please make sure you have enabled the export property -Dexport=true")
				.build();

		// translator property
		Option property = Option.builder("D").numberOfArgs(2).argName("property=value").valueSeparator('=')
				.required(false).optionalArg(false).type(String.class).desc(translator.getOptionsDescription()).build();

		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		options.addOption(javaPath);
		options.addOption(evosuiteVersion);
		options.addOption(clean);
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
				logger.error("* Unknown property: {}. " , propertyName);
				continue;
			}

			String propertyValue = properties.getProperty(propertyName);

			try {
				translator.setOptions(propertyName, propertyValue);

			} catch (Exception e) {
				logger.error("Invalid value for property {} : {}.", propertyName , propertyValue);
			}
		}

	}

}
