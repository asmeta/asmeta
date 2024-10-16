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
 * The Asmeta2JavaCLI is the entry point for the Asmetal2Java tool,
 * which translates ASM (Abstract State Machines) specifications into Java code.
 * This class provides methods to handle command-line arguments, parse ASM specifications,
 * generate Java code, and manage output directories.
 */
public class Asmeta2JavaCLI {
	
	/** Logger */
	private static final Logger logger = Logger.getLogger(Asmeta2JavaCLI.class);

	/** Input folder */
	private static final Path INPUT_DIR_PATH = Paths.get(System.getProperty("user.dir"), "input");

	/** Default output folder */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty("user.dir"), "output");
	
	/** Translator folder */
	private static final Path TRANSLATION_DIR_PATH =  Paths.get(INPUT_DIR_PATH.toString(), "translation");
	
	/** Compile folder  */
	private static final Path COMPILER_DIR_PATH =  Paths.get(INPUT_DIR_PATH.toString(), "compiler");
	
	/** Executor folder */
	private static final Path EXECUTION_DIR_PATH =  Paths.get(INPUT_DIR_PATH.toString(), "execution");
	
	/** Generator of the java class */
	static private JavaGenerator jGenerator = new JavaGenerator();
	
	/** Generator of the _Exe java class */
	static private JavaExeGenerator jGeneratorExe = new JavaExeGenerator();

	/** Default translator options */
	private static TranslatorOptions translatorOptions = new TranslatorOptions();

	/**
	 * Generates Java code from an ASM specification.
	 *
	 * @param asmspec      the path to the ASM specification file.
	 * @param userOptions  the translation options set by the user.
	 * @param outputFolder the folder where the generated Java files will be saved.
	 * @return             the result of the compilation process.
	 * @throws Exception   if an error occurs during the generation or compilation process.
	 */
	public static boolean generate(
			String asmspec,
			TranslatorOptions userOptions,
			Path outputFolder)
			throws Exception {

		File asmFile = new File(asmspec);
		if(!asmFile.exists()) {
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
	
		if(userOptions.getCompiler()) {
			try {
				File javaFile = generateFile(COMPILER_DIR_PATH, asmName , ".java", model ,userOptions);
				CompileResult result = CompilatoreJava.compile(asmName + ".java", COMPILER_DIR_PATH, true);
				if(result.getSuccess()) {
					exportFile(javaFile, outputFolder);
				}
				else {
					throw new Exception("The generated class has compilation errors." + result.toString());
				}
			} catch (Exception e) {
				logger.error("Compilation operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		if(userOptions.getTranslator()) {
			try {
				File javaFile = generateFile(TRANSLATION_DIR_PATH, asmName, ".java",  model ,userOptions);
				exportFile(javaFile, outputFolder);
			} catch (Exception e) {
				logger.error("Translation operation complited with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		if(userOptions.getExecutable()) {
			try {
				File executionJavaFile = generateFile(EXECUTION_DIR_PATH, asmName , "_Exe.java", model ,userOptions);
				exportFile(executionJavaFile, outputFolder);
			} catch (Exception e) {
				logger.error("Execution operation completed with errors: " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	private static File generateFile(Path dirPath, String name, String extension, AsmCollection model, TranslatorOptions userOptions) throws IOException {
		if (!checkPath(dirPath)) {
	        createPath(dirPath);
	      }
	    File javaFile = new File(dirPath + File.separator + name + extension);
	    deleteExisting(javaFile);
	    if(extension.equals(".java")) {
	    	logger.info("JavaGenerator: generating the .java class...");
	    	jGenerator.compileAndWrite(model.getMain(),javaFile.getCanonicalPath(),userOptions);
	    }
	    else if(extension.equals("_Exe.java")) {
	    	logger.info("JavaExeGenerator: generating the _Exe.java class...");
	    	jGeneratorExe.compileAndWrite(model.getMain(),javaFile.getCanonicalPath(),userOptions);
	    }
	    else {
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
	 * @param outputFolder the output folder path.
	 */
	  private static void exportFile(File javaInputFile, Path outputFolder){
		  logger.info("Exporting " + javaInputFile + " to: " + outputFolder.toString());
		  if(!checkPath(outputFolder)) {
			  createPath(outputFolder);
		  }
		  File javaOutFile = new File(outputFolder + File.separator + javaInputFile.getName());
		  try (
				InputStream in = new BufferedInputStream(Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(javaOutFile.toPath()))) {
			  byte[] buffer = new byte[1024];
			  int lengthRead;
			  while ((lengthRead = in.read(buffer)) > 0) {
				  out.write(buffer, 0, lengthRead);
				  out.flush();
				  }
			  } catch (Exception e){
				  logger.error("Export Failed.");
				  e.printStackTrace();
			}
		  logger.info("Export completed.");
	  }

	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 */
	private static void deleteExisting(File file){
		if (file.exists())
			file.delete();
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
				.desc(TranslatorOptions.getDescription())
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
				new HashSet<>(TranslatorOptions.getPriopertyNames());

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
		
		Path outputFolder = DEFAULT_OUTPUT_DIR_PATH;
		if(line.hasOption("output")){
			outputFolder = Paths.get(line.getOptionValue("output"));
			if(!translatorOptions.getExport()) {
				logger.info("Warning: you selected an output folder but the export option is disabled!");
				logger.info("Warning: to enable the export option type -Dexport=true");
			}
		}
	
		try {
			Boolean result = generate(
					asmspec,
					translatorOptions,
					outputFolder);
			if(result){
				logger.info("Generation succeed");
			}
			else{
				logger.error("Generation failed");
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
			Asmeta2JavaCLI main = new Asmeta2JavaCLI ();
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
