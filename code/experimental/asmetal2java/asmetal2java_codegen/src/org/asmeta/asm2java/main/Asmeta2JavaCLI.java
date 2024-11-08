package org.asmeta.asm2java.main;

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
import org.apache.log4j.Logger;
import org.asmeta.asm2java.application.AsmParsingException;
import org.asmeta.asm2java.application.Translator;
import org.asmeta.asm2java.application.TranslatorImpl;

/**
 * The Asmeta2JavaCLI is the entry point for the Asmetal2Java tool, which
 * translates ASM (Abstract State Machines) specifications into Java code. This
 * class provides methods to handle command-line arguments, parse ASM
 * specifications, generate Java code, and manage output directories.
 */
public class Asmeta2JavaCLI {

	/* Constants */
	private static final String MODE = "mode";
	private static final String CLEAN = "clean";
	private static final String HELP = "help";
	private static final String OUTPUT = "output";
	private static final String INPUT = "input";
	private static final String COMPILER_VERSION = "compilerVersion";

	/** Logger */
	private static final Logger logger = Logger.getLogger(Asmeta2JavaCLI.class);

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
		String asciiart = "\n    _                       _        _ ____   _                  \n"
				+ "   / \\   ___ _ __ ___   ___| |_ __ _| |___ \\ (_) __ ___   ____ _ \n"
				+ "  / _ \\ / __| '_ ` _ \\ / _ \\ __/ _` | | __) || |/ _` \\ \\ / / _` |\n"
				+ " / ___ \\\\__ \\ | | | | |  __/ || (_| | |/ __/ | | (_| |\\ V / (_| |\n"
				+ "/_/   \\_\\___/_| |_| |_|\\___|\\__\\__,_|_|_____|/ |\\__,_| \\_/ \\__,_|\n"
				+ "                                           |__/                  \n";
		Asmeta2JavaCLI main = new Asmeta2JavaCLI();
		Options options = getCommandLineOptions();
		CommandLine line = main.parseCommandLine(args, options);
		logger.info(asciiart);
		try {
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "Asmetal2java\n\n";
				String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta "
						+ "for further information or to report an issue.";

				formatter.printHelp("Asmetal2java", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path");
			} else {
				main.execute(line);
			}
		} catch (Exception e) {
			logger.error("Generation failed");
			logger.error("An error occurred:\n" + e.getMessage());
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
	 * @throws AsmParsingException
	 */
	private void execute(CommandLine line) throws AsmParsingException, IOException {

		setGlobalProperties(line);

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

		if (translator.generate()) {
			logger.info("Generation succeed");
		} else {
			logger.error("Generation failed");
		}

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

		// clean the directories after use
		Option clean = Option.builder(CLEAN).hasArg(false)
				.desc("Delete the files used by the translator in the input folder, "
						+ "please make sure you have enabled the export property -Dexport=true")
				.build();

		// set the desired behavior
		Option mode = Option.builder(MODE).argName(MODE).type(String.class).hasArg(true)
				.desc("Set the mode of the application:\n" + translator.getModeDescription()
						+ "Note: Please use the properties: -Dtranslator, -Dexecutable, -Dwindow and -DtestGen only if you have selected the -mode custom option.")
				.build();

		// compiler version
		Option compilerVersion = Option.builder(COMPILER_VERSION).argName(COMPILER_VERSION).type(String.class)
				.hasArg(true).desc("Set the version of the Java compiler used to compile the generated classes.")
				.build();

		// translator property
		Option property = Option.builder("D").numberOfArgs(2).argName("property=value").valueSeparator('=')
				.required(false).optionalArg(false).type(String.class).desc(translator.getOptionsDescription()).build();

		options.addOption(help);
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
				logger.error("* Unknown property: " + propertyName);
				continue;
			}

			String propertyValue = properties.getProperty(propertyName);

			try {
				translator.setOptions(propertyName, propertyValue);

			} catch (Exception e) {
				logger.error("Invalid value for property " + propertyName + ": " + propertyValue);
			}
		}

	}

}
