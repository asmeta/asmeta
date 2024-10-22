package org.asmeta.asm2java.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.asmeta.asm2java.evosuite.JavaAtgGenerator;
import org.asmeta.asm2java.evosuite.JavaTestGenerator;
import org.asmeta.asm2java.evosuite.RulesImpl;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

/**
 * The Asmeta2JavaCLI is the entry point for the Asmetal2Java tool, which
 * translates ASM (Abstract State Machines) specifications into Java code. This
 * class provides methods to handle command-line arguments, parse ASM
 * specifications, generate Java code, and manage output directories.
 */
public class Asmeta2JavaCLI {

	private static final String MODE = "mode";

	private static final String CLEAN = "clean";

	private static final String HELP = "help";

	private static final String ATG_EXTENSION = "_ATG.java";

	private static final String WIN_EXTENSION = "_Win.java";

	private static final String EXE_EXTENSION = "_Exe.java";

	private static final String JAVA_EXTENSION = ".java";

	private static final String USER_DIR = "user.dir";

	private static final String TEST_GEN = "testGen";

	private static final String EXECUTION = "execution";

	private static final String COMPILER = "compiler";

	private static final String TRANSLATION = "translation";

	private static final String OUTPUT = "output";

	private static final String INPUT = "input";

	/** Logger */
	private static final Logger logger = Logger.getLogger(Asmeta2JavaCLI.class);

	/** Input folder */
	private static final Path INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);

	/** Default output folder */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);

	/** Translator folder */
	private static final Path TRANSLATION_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), TRANSLATION);

	/** Compile folder */
	private static final Path COMPILER_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), COMPILER);

	/** Executor folder */
	private static final Path EXECUTION_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), EXECUTION);
	
	/** TestGen folder */
	private static final Path TESTGEN_DIR_PATH = Paths.get(INPUT_DIR_PATH.toString(), TEST_GEN);

	/** Generator of the java class */
	private static JavaGenerator jGenerator = new JavaGenerator();

	/** Generator of the _Exe java class */
	private static JavaExeGenerator jGeneratorExe = new JavaExeGenerator();
	
	/** Generator of the _Win java class */
	private static JavaWindowGenerator jGeneratorWin = new JavaWindowGenerator();
	
	/** Instance of the RulesImpl, a Map {name:Rule} collection containing the rules of the Asmeta specification */
	private static RulesImpl rulesImpl = new RulesImpl();
	
	/** Generator of the java class used for test generation */
	private static JavaTestGenerator jGeneratorTest = new JavaTestGenerator(rulesImpl);
	
	/** Generator of the _ASM java class */
	private static JavaAtgGenerator jGeneratorAtg = new JavaAtgGenerator(rulesImpl);
	
	/** Default translator options */
	private static TranslatorOptions translatorOptions = new TranslatorOptions();

	/**
	 * Generates Java code from an ASM specification.
	 *
	 * @param asmspec      the path to the ASM specification file.
	 * @param userOptions  the translation options set by the user.
	 * @param outputFolder the folder where the generated Java files will be saved.
	 * @return the result of the compilation process.
	 * @throws Exception if an error occurs during the generation or compilation
	 *                   process.
	 */
	public static boolean generate(String asmspec, TranslatorOptions userOptions, Path outputFolder) throws Exception {

		File asmFile = new File(asmspec);
		if (!asmFile.exists()) {
			logger.error("Failed to locate the input file:" + asmFile.toString());
			throw new RuntimeException("File doesn't exist: " + asmFile.toString());
		}
		String asmFileName = asmFile.getName();
		String asmName = asmFileName.substring(0, asmFileName.lastIndexOf("."));

		// Copy the asm file to the input folder
		Path inputAsmPath = Paths.get(INPUT_DIR_PATH.toString(), asmFile.getName());
		Files.copy(Paths.get(asmFile.getAbsolutePath()), inputAsmPath, StandardCopyOption.REPLACE_EXISTING);

		// Parse the specification using the Asmeta parser
		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);

		if (userOptions.getCompiler()) {
			try {
				File javaFile = generateFile(COMPILER_DIR_PATH, asmName, JAVA_EXTENSION, model, userOptions);
				CompileResult result = CompilatoreJava.compile(asmName + JAVA_EXTENSION, COMPILER_DIR_PATH, true);
				if (result.getSuccess()) {
					exportFile(javaFile, outputFolder);
				} else {
					throw new Exception("The generated class has compilation errors." + result.toString());
				}
			} catch (Exception e) {
				logger.error("Compilation operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}

		if (userOptions.getTranslator()) {
			try {
				File javaFile = generateFile(TRANSLATION_DIR_PATH, asmName, JAVA_EXTENSION, model, userOptions);
				exportFile(javaFile, outputFolder);
			} catch (Exception e) {
				logger.error("Translation operation complited with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}

		if (userOptions.getExecutable()) {
			try {
				File executionJavaFile = generateFile(EXECUTION_DIR_PATH, asmName, EXE_EXTENSION, model, userOptions);
				exportFile(executionJavaFile, outputFolder);
			} catch (Exception e) {
				logger.error("Execution operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		if (userOptions.getWindow()) {
			try {
				File winJavaFile = generateFile(EXECUTION_DIR_PATH, asmName, WIN_EXTENSION, model, userOptions);
				exportFile(winJavaFile, outputFolder);
			} catch (Exception e) {
				logger.error("Window operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		if (userOptions.getTestGen()) {
			try {
				
				if (!checkPath(TESTGEN_DIR_PATH)) {
					createPath(TESTGEN_DIR_PATH);
				}
								
				File testGenJavaFile = new File(TESTGEN_DIR_PATH + File.separator + asmName + JAVA_EXTENSION);
				deleteExisting(testGenJavaFile);
				logger.info("JavaGenerator: generating the .java class...");
				jGeneratorTest.compileAndWrite(model.getMain(), testGenJavaFile.getCanonicalPath(), userOptions);
				logger.info("Generated java file: " + testGenJavaFile.getCanonicalPath());
				
				
				File atgJavaFile = generateFile(TESTGEN_DIR_PATH, asmName, ATG_EXTENSION, model, userOptions);

				exportFile(testGenJavaFile, outputFolder);
				exportFile(atgJavaFile, outputFolder);
			} catch (Exception e) {
				logger.error("TestGen operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * Generate the java file.
	 * 
	 * @param dirPath Path of the directory where the file needs to be located.
	 * @param name String containing the file name.
	 * @param extension String containing the file extension.
	 * @param model The asm specification parsed.
	 * @param userOptions Translator options.
	 * @return the generated File
	 * @throws IOException 
	 */
	private static File generateFile(Path dirPath, String name, String extension, AsmCollection model,
			TranslatorOptions userOptions) throws IOException {
		if (!checkPath(dirPath)) {
			createPath(dirPath);
		}
		File javaFile = new File(dirPath + File.separator + name + extension);
		deleteExisting(javaFile);
		if (extension.equals(JAVA_EXTENSION)) {
			logger.info("JavaGenerator: generating the .java class...");
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
		} else if (extension.equals(EXE_EXTENSION)) {
			logger.info("JavaExeGenerator: generating the _Exe.java class...");
			jGeneratorExe.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
		} else if (extension.equals(WIN_EXTENSION)) {
			logger.info("JavaExeGenerator: generating the _Win.java class...");
			jGeneratorWin.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
		} else if (extension.equals(ATG_EXTENSION)) {
			logger.info("JavaExeGenerator: generating the _ASM.java class...");
			jGeneratorAtg.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
		} else {
			logger.error("Extension " + extension + " not valid");
			throw new RuntimeException("Extension " + extension + " not valid");
		}
		logger.info("Generated java file: " + javaFile.getCanonicalPath());
		return javaFile;
	}

	/**
	 * Checks if the given path exists.
	 *
	 * @param path The path to check.
	 * @return {@code true} if the path exists, {@code false} otherwise.
	 */
	private static boolean checkPath(Path path) {
		return Files.exists(path);
	}

	/**
	 * Creates a directory at the specified path if it does not exist.
	 *
	 * @param path The path where the directory should be created.
	 */
	private static void createPath(Path path) {
		try {
			logger.info("Path " + path + " doesn't exist.");
			logger.info("Creating path " + path + " ...");
			Files.createDirectories(path);
			logger.info("Path" + path + " created with success.");
		} catch (IOException e) {
			logger.error("Failed to create path: path");
			throw new RuntimeException("Failed to create path: " + path, e);
		}
	}

	/**
	 * Export (Copy) the file into the desired folder.
	 *
	 * @param javaInputFile the input java file to be copied.
	 * @param outputFolder  the output folder path.
	 */
	private static void exportFile(File javaInputFile, Path outputFolder) {
		logger.info("Exporting " + javaInputFile + " to: " + outputFolder.toString());
		if (!checkPath(outputFolder)) {
			createPath(outputFolder);
		}
		File javaOutFile = new File(outputFolder + File.separator + javaInputFile.getName());
		try (InputStream in = new BufferedInputStream(Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(javaOutFile.toPath()))) {
			deleteExisting(javaOutFile);
			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		} catch (IOException e) {
			logger.error("Export Failed.");
            e.printStackTrace(); 
        } catch (Exception e) {
			logger.error("Export Failed.");
			e.printStackTrace();
		}
		logger.info("Export completed.");
	}

	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	  @throws IOException if an IO error occurs (and execution should be stopped).
	 */
	private static void deleteExisting(File file) throws IOException {
		if (file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
                logger.error("File not found, skipping: " + file.getPath());
			}
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
				.desc("Set the mode of the application:\n"
						+ "-mode translator : translate the asm file to a java file (default).\n"
						+ "-mode generateExe : translate the asm file to a java file and generate an executable java class\n"
						+ "-mode generateWin : translate the asm file to a java file and generate an executable java class with a Grapical User Interace (GUI)\n"
						+ "-mode testGen: generate a test class suited for test generation with Evosuite\n"
						+ "-mode custom : set a custom behavior by adding properties with -D (see help)\n"
						+ "Note: Please use the properties: -Dtranslator, -Dexecutable, -Dwindow and -DtestGen only if you have selected the -mode custom option.")
				.build();

		// translator property
		Option property = Option.builder("D").numberOfArgs(2).argName("property=value").valueSeparator('=')
				.required(false).optionalArg(false).type(String.class).desc(TranslatorOptions.getDescription()).build();

		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		options.addOption(clean);
		options.addOption(mode);
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
		Set<String> propertyNames = new HashSet<>(TranslatorOptions.getPriopertyNames());

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
	private void execute(CommandLine line, Options options) {

		setGlobalProperties(line);

		String asmspec = "";
		if (line.hasOption(INPUT)) {
			asmspec = line.getOptionValue(INPUT);
		} else {
			logger.error("input option needs a path to the asm file");
		}

		Path outputFolder = DEFAULT_OUTPUT_DIR_PATH;
		if (line.hasOption(OUTPUT)) {
			outputFolder = Paths.get(line.getOptionValue(OUTPUT));
			if (!translatorOptions.getExport()) {
				logger.info("Warning: you selected an output folder but the export option is disabled!");
				logger.info("Warning: to enable the export option type -Dexport=true");
			}
		}

		if (line.hasOption(MODE)) {
			switch (line.getOptionValue(MODE)) {
			case "translator":
				translatorOptions.setValue("translator", "true");
				//translatorOptions.setValue("compiler", "false");
				translatorOptions.setValue("executable", "false");
				translatorOptions.setValue("window", "false");
				translatorOptions.setValue(TEST_GEN, "false");
				break;
			case "generateExe":
				translatorOptions.setValue("translator", "true");
				//translatorOptions.setValue("compiler", "false");
				translatorOptions.setValue("executable", "true");
				translatorOptions.setValue("window", "false");
				translatorOptions.setValue(TEST_GEN, "false");
				break;
			case "generateWin":
				translatorOptions.setValue("translator", "true");
				//translatorOptions.setValue("compiler", "false");
				translatorOptions.setValue("executable", "false");
				translatorOptions.setValue("window", "true");
				translatorOptions.setValue(TEST_GEN, "false");
				break;
			case TEST_GEN:
				translatorOptions.setValue("translator", "false");
				//translatorOptions.setValue("compiler", "false");
				translatorOptions.setValue("executable", "false");
				translatorOptions.setValue("window", "false");
				translatorOptions.setValue(TEST_GEN, "true");
			case "custom":
				break;
			}
		}

		try {
			Boolean result = generate(asmspec, translatorOptions, outputFolder);
			if (result) {
				logger.info("Generation succeed");
			} else {
				logger.error("Generation failed");
			}

		} catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}

		if (line.hasOption(CLEAN)) {
			if (INPUT_DIR_PATH.toFile().exists() && INPUT_DIR_PATH.toFile().isDirectory()) {
				for (File file : INPUT_DIR_PATH.toFile().listFiles()) {
					if (!file.getName().equals("STDL") && !file.getName().equals(".gitignore")) {
						cleanRecursively(file);
					}
				}
			}
		}

	}

	/**
	 * Delete all the files inside a directory and then delete the directory.
	 * 
	 * @param file or directory to delete.
	 */
	private void cleanRecursively(File file) {

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					cleanRecursively(f);
				}
			}
		}

		if (file.delete()) {
			logger.info("Deleted: " + file.getAbsolutePath());
		} else {
			logger.error("Failed to delete: " + file.getAbsolutePath());
		}

	}

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

		try {
			Asmeta2JavaCLI main = new Asmeta2JavaCLI();
			Options options = getCommandLineOptions();
			CommandLine line = main.parseCommandLine(args, options);
			logger.info(asciiart);
			if (line == null || line.hasOption(HELP) || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "Asmetal2java\n\n";
				String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta "
						+ "for information or to report problems";

				formatter.printHelp("Asmetal2java", header, options, footer, false);
			} else if (!line.hasOption(INPUT)) {
				logger.error("Please specify the asm input file path");
			} else {
				main.execute(line, options);
			}
			logger.info("Requested operation completed.");
		} catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);

	}

}
