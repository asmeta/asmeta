package org.asmeta.asm2java.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
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
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

/**
 * The MainClass is the entry point for the Asmetal2Java tool,
 * which translates ASM (Abstract State Machines) specifications into Java code.
 * This class provides methods to handle command-line arguments, parse ASM specifications,
 * generate Java code, and manage output directories.
 */
public class MainClass {
	
	private static final Logger logger = Logger.getLogger(MainClass.class);

	// default output folder
	private static final String SRC_GEN = "../asmetal2java_examples/src/";

	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();
	static private JavaExeGenerator jGeneratorExe = new JavaExeGenerator();

	// default translator options
	private static TranslatorOptions translatorOptions =
			new TranslatorOptions(false, false, false);

	/**
	 * Generates Java code from an ASM specification.
	 *
	 * @param asmspec      the path to the ASM specification file.
	 * @param userOptions  the translation options set by the user.
	 * @param outputFolder the folder where the generated Java files will be saved.
	 * @return             the result of the compilation process.
	 * @throws Exception   if an error occurs during the generation or compilation process.
	 */
	public static CompileResult generate(
      String asmspec,
			TranslatorOptions userOptions,
			String outputFolder)
			throws Exception {
		//
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser

		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		File dir = asmFile.getParentFile();
		assert dir.exists() && dir.isDirectory();

		String dirCompilazione = asmFile.getParentFile().getPath() + "/compilazione";
		String dirEsecuzione = asmFile.getParentFile().getPath() + "/esecuzione";
		String dirTraduzione = asmFile.getParentFile().getPath() + "/traduzione";

		// AC
		//File javaFile = new File(SRC_GEN + File.separator + name + ".java");
		File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");
		File javaFileExe = new File(dirEsecuzione + File.separator + name + "_Exe.java");
		File javaFileExeN = new File(dirEsecuzione + File.separator + name + ".java");

		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");
		File javaFileExeT = new File(dirTraduzione + File.separator + name + "_Exe.java");

		deleteExisting(javaFile);
		deleteExisting(javaFileCompilazione);
		deleteExisting(javaFileExe);
		deleteExisting(javaFileExeN);
		deleteExisting(javaFileT);
		deleteExisting(javaFileExeT);

		System.out.println("\n\n===" + name + " ===================");

		// write java
		try {
			// Java Class
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(),
					javaFileCompilazione.getCanonicalPath(),
					userOptions);

			// EXE Class
			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExe.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(), javaFileExeN.getCanonicalPath(), userOptions);
			jGeneratorExe.compileAndWrite(model.getMain(), javaFileExeT.getCanonicalPath(), userOptions);

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}

		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
		System.out.println("Generated java file: " + javaFileCompilazione.getCanonicalPath());
		System.out.println("Generated java file: " + javaFileExeN.getCanonicalPath());

		System.out.println("Generated java file for the execution: " + javaFileExe.getCanonicalPath());

		System.out.println("All java files Generated in: " + javaFileT.getCanonicalPath());

		exportFile(javaFile, outputFolder);
		exportFile(javaFileExe, outputFolder);

		CompileResult result = CompilatoreJava.compile(name + ".java", dir);

		return result;
	}

	/**
	 * Export (Copy) the file into the desired folder.
	 *
	 * @param javaInputFile the input java file to be copied.
	 * @param outputFolder the output folder name (must exist).
	 */
  private static void exportFile(File javaInputFile, String outputFolder){
		File javaOutFile =
				new File(outputFolder + File.separator + javaInputFile.getName());
		File dir = javaOutFile.getParentFile();
		assert dir.exists() && dir.isDirectory();
		try (
				InputStream in = new BufferedInputStream(
						Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(
						Files.newOutputStream(javaOutFile.toPath()))) {

			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		} catch (NoSuchFileException e){
			logger.error("Export Failed. Please specify an existing output folder...");
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 */
	private static void deleteExisting(File file){
		if (file.exists())
			file.delete();
		assert !file.exists();
	}

	/**
	 * Creates and returns the available command-line options for the tool.
	 *
	 * @return the command-line options.
	 */
	public static Options getCommandLineOptions() {
		Options options = new Options();

		// print help
		Option help = new Option("help", "print this message");
		

		// input file
		  Option input = Option.builder("input")
				  .argName("input")
				  .type(String.class)
				  .hasArg(true)
				  .desc("The ASM input file (required)")
				  .build();
		
		// output directory
		Option output = Option.builder("output")
				.argName("output")
				.type(String.class)
				.hasArg(true)
				.desc("The output folder (optional, defaults to `./output/`)")
				.build();
		
		// property
		Option property = Option.builder("D")
				.numberOfArgs(2)
				.argName("property=value")
				.valueSeparator('=')
				.required(false)
				.optionalArg(false)
				.type(String.class)
				.desc("use value for given translator property (optional):\n"
						+ "formatter=true/false (if you want the code to be formatted),\n"
						+ " shuffleRandom=true/false (use random shuffle),\n"
						+ " optimizeSeqMacroRule=true/false"
						+ " (if true -> only those used (to improve code coverage))")
				.build();
		
		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		options.addOption(property);

		return options;
	}

	/**
	 * Parses the command-line arguments using the provided options.
	 *
	 * @param args    the command-line arguments.
	 * @param options the available command-line options.
	 * @return        the parsed CommandLine object.
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
	 * Sets global translator properties based on the command-line options.
	 *
	 * @param line the parsed CommandLine object.
	 */
	private void setGlobalProperties(CommandLine line) {
		Properties properties = line.getOptionProperties("D");
		Set<String> propertyNames =
				new HashSet<>(Arrays.asList("formatter", "shuffleRandom", "optimizeSeqMacroRule"));

        for (String propertyName : properties.stringPropertyNames()) {

            if (!propertyNames.contains(propertyName)) {
				logger.error("* Unknown property: " + propertyName);
			}

            String propertyValue = properties.getProperty(propertyName);

            try {
            	translatorOptions.setValue(propertyName, propertyValue);
				
			} catch (Exception e) {
				logger.error("Invalid value for property " + propertyName + ": " + propertyValue);
			}
		}
		
	}

	/**
	 * Executes the main process based on the command-line arguments.
	 *
	 * @param line    the parsed CommandLine object.
	 * @param options the available command-line options.
	 */
	private void execute (CommandLine line, Options options) {

		setGlobalProperties (line);
		
		String asmspec = "";

		if (line.hasOption("input")) {
			asmspec = line.getOptionValue("input");
		}else {
			logger.error("input option needs a path to the asm file");
		}
		
		String outputFolder = "";
		if(!line.hasOption("output")){
			outputFolder = "output/";
		} else {
			outputFolder = line.getOptionValue("output");
		}
	
		try {
			CompileResult compileResult = generate(
					asmspec,
					translatorOptions,
					outputFolder);
			if(compileResult.getSuccess()){
				logger.info("Generation succeed : " + compileResult.toString());
			}
			else{
				logger.error("Generation failed : " + compileResult.toString());
			}

		} catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}
		
	}

	/**
	 * The main method, which is the entry point of the application.
	 * It parses the command-line arguments, handles the help option,
	 * and triggers the execution of the main process.
	 *
	 * @param args the command-line arguments.
	 */
	public static void main(String[] args) {
		
		try {
			MainClass main = new MainClass ();
			Options options = getCommandLineOptions();
			CommandLine line = main.parseCommandLine(args, options);
			logger.info("Performing requested operation ...");
			if (line == null || line.hasOption("help") || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort				
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "Asmetal2java\n\n";
				String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta "
						+ "for information or to report problems";
				 
				formatter.printHelp("Asmetal2java",header, options, footer , false);
			}else if(!line.hasOption("input")){
				logger.error("Please specify the asm input file path");
			}
			else{
			main.execute(line, options);
			}
			logger.info("Requested operation completed.");
		}
		catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);

	}

}
