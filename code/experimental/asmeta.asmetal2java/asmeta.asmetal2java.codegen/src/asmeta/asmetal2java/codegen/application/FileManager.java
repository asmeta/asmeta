package asmeta.asmetal2java.codegen.application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.AsmCollection;
import asmeta.asmetal2java.codegen.generator.AsmToJavaGenerator;
import asmeta.asmetal2java.codegen.compiler.CompileResult;
import asmeta.asmetal2java.codegen.compiler.Compiler;
import asmeta.asmetal2java.codegen.compiler.CompilerImpl;
import asmeta.asmetal2java.codegen.config.Mode;
import asmeta.asmetal2java.codegen.config.ModeConstantsConfig;
import asmeta.asmetal2java.codegen.config.TranslatorOptions;
import asmeta.asmetal2java.codegen.evosuite.DomainNotSupportedException;
import asmeta.asmetal2java.codegen.generator.Generators;

/**
 * The {@code FileManager} class provides methods for working with files.
 */
public class FileManager {

	/* constants */
	private static final String JAVA_EXTENSION = ".java";
	public static final String ATG = "_ATG";
	private static final String ATG_EXTENSION = ATG + JAVA_EXTENSION;
	private static final String WIN_EXTENSION = "_Win" + JAVA_EXTENSION;
	private static final String EXE_EXTENSION = "_Exe" + JAVA_EXTENSION;
	private static final String JAVA = "JAVA";
	private static final String USER_DIR = "user.dir";
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	private static final String STDL = "STDL";
	private static final String SLASH = "/";
	private static final String GITIGNORE = ".gitignore";
	private static final String LOGS_DIRECTORY = "logs";

	/** Files/Directory to exclude from cleaning. */
	private static final List<String> excludeList = List.of(GITIGNORE, STDL, LOGS_DIRECTORY);

	/** List of required Stdl Libraries. */
	private static final List<String> requiredStdlLibraries = List.of("StandardLibrary.asm", "LTLLibrary.asm",
			"CTLLibrary.asm");

	/** Logger */
	private final Logger logger = LogManager.getLogger(FileManager.class);

	/** Path to the default input working directory. */
	private static final Path DEFAULT_INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);

	/** Path to the default output directory. */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);

	/** Compiler instance for compiling the java translation */
	private static final Compiler compilerJava = new CompilerImpl();

	/** The version of the Java compiler used to compile the generated classes. */
	private String compilerVersion = "17";

	/** Path to the actual input working directory. */
	private Path inputFolder = DEFAULT_INPUT_DIR_PATH;

	/** Path to the actual output folder. */
	private Path outputFolder = DEFAULT_OUTPUT_DIR_PATH;

	/** Path to the translation folder. */
	private Path translationDirPath;

	/** Path to the compiler folder. */
	private Path compilerDirPath;

	/** Path to the exe folder. */
	private Path exeDirPath;

	/** Path to the win folder. */
	private Path winDirPath;

	/** Path to the test generation folder. */
	private Path testGenDirPath;

	/**
	 * Constructs a {@code FileManager} instance.
	 */
	FileManager() {
		updateInputSubFolders();
	}

	/**
	 * Set the input folder path.
	 * 
	 * @param inputWorkingDir String containing the path of the custom input working
	 *                        directory.
	 */
	void setInputFolder(String inputWorkingDir) {
		Path inputWorkingDirPath = Paths.get(inputWorkingDir);
		if (inputWorkingDirPath.toAbsolutePath().toString()
				.equals(Paths.get(System.getProperty(USER_DIR)).toAbsolutePath().toString())
				|| inputWorkingDirPath.toAbsolutePath().toString()
						.equals(Paths.get(System.getProperty(USER_DIR), ".").toAbsolutePath().toString())) {
			logger.warn("The current user directory can't be the path of the custom input working directory.");
			// warning: otherwise the -clean option would erase the entire project directory
			inputWorkingDirPath = DEFAULT_INPUT_DIR_PATH;
			logger.info("Seting the working directory to: {}", inputWorkingDirPath);
		}
		if (inputWorkingDirPath.equals(DEFAULT_INPUT_DIR_PATH)) {
			logger.info("The path of the custom input working directory is the same as the default one.");
			return;
		}
		this.inputFolder = inputWorkingDirPath;
		updateInputSubFolders();
	}

	/**
	 * Update the path of subfolders within the input folder (call this method every
	 * time the input folder path is changed).
	 */
	private void updateInputSubFolders() {
		translationDirPath = Paths.get(inputFolder.toString(), ModeConstantsConfig.TRANSLATOR);
		compilerDirPath = Paths.get(inputFolder.toString(), ModeConstantsConfig.COMPILER);
		exeDirPath = Paths.get(inputFolder.toString(), ModeConstantsConfig.GENERATE_EXE);
		winDirPath = Paths.get(inputFolder.toString(), ModeConstantsConfig.GENERATE_WIN);
		testGenDirPath = Paths.get(inputFolder.toString(), ModeConstantsConfig.TEST_GEN);
	}

	/**
	 * Given a string containing the path to the input file, returns the asmeta
	 * specification or copies the file into the input directory and returns the
	 * newly generated file, if the copyAsm option is enabled.
	 * 
	 * @param asmspec the path to the input file (relative or absolute).
	 * @param copyAsm the translator option copyAsm (Indicates to copy the asm
	 *                specification files to another folder to be processed.)
	 * @return the retrieved asmeta file.
	 * @throws IOException    if an I/O error occurs.
	 * @throws SetupException if an error occurs during the setup process.
	 */
	File retrieveInput(String asmspec, boolean copyAsm) throws IOException, SetupException {
		File asmFile = new File(asmspec);
		if (!asmFile.exists()) {
			logger.error("Failed to locate the input file: {}.", asmFile);
			throw new SetupException("File doesn't exist: " + asmFile.toString());
		}

		// if the copyAsm option is disabled, return the current file without coping
		if (!copyAsm) {
			return asmFile;
		}
		// else copy the asmFile to the working directory

		// Check if the input directory exists and if it contains the required
		// libraries.
		// If not, creates a new input directory and adds the missing libraries.
		if (!checkInputDir()) {
			logger.error("Failed to check the input directory: {}.", inputFolder);
			throw new SetupException("Unable to set input working directory correctly");
		}

		// Copy the asm file to the input folder
		Path inputAsmPath = Paths.get(inputFolder.toString(), asmFile.getName());
		logger.info("Copying the {} to: {}.", asmFile, inputAsmPath);
		Files.copy(Paths.get(asmFile.getAbsolutePath()), inputAsmPath, StandardCopyOption.REPLACE_EXISTING);
		return inputAsmPath.toFile();
	}

	/**
	 * Generates a Java file in the specified directory, using the given name,
	 * extension, model, and translator options.
	 * 
	 * @param name        the name of the file to be generated.
	 * @param model       the parsed ASM specification.
	 * @param userOptions the translator options to be applied.
	 * @param mode        the mode for the translation process.
	 * @return the generated file.
	 * @throws IOException                 if an I/O error occurs during file
	 *                                     generation.
	 * @throws DomainNotSupportedException if a domain is not supported by the Atg
	 *                                     generator.
	 */
	File generateFile(String name, AsmCollection model, TranslatorOptions userOptions, Mode mode)
			throws IOException, DomainNotSupportedException {

		File javaFile = null;
		AsmToJavaGenerator javaGenerator = null;

		switch (mode) {
		case TRANSLATOR_MODE:
			checkPath(translationDirPath);
			javaFile = new File(translationDirPath + File.separator + name + JAVA_EXTENSION);
			// Generate the Java translation.
			javaGenerator = Generators.getJavaGenerator();
			break;
		case COMPILER_MODE:
			checkPath(compilerDirPath);
			javaFile = new File(compilerDirPath + File.separator + name + JAVA_EXTENSION);
			// Generate the Java translation.
			javaGenerator = Generators.getJavaGenerator();
			break;
		case GENERATE_EXE_MODE:
			checkPath(exeDirPath);
			javaFile = new File(exeDirPath + File.separator + name + EXE_EXTENSION);
			// Generate an executable of the generated Java class.
			javaGenerator = Generators.getJavaExeGenerator();
			break;
		case GENERATE_WIN_MODE:
			checkPath(winDirPath);
			javaFile = new File(winDirPath + File.separator + name + WIN_EXTENSION);
			// Generate an executable of the generated Java class with a Graphical User
			// Interface (GUI).
			javaGenerator = Generators.getJavaWindowGenerator();
			break;
		case TRANSLATOR_TEST_MODE:
			checkPath(testGenDirPath);
			javaFile = new File(testGenDirPath + File.separator + name + JAVA_EXTENSION);
			// Generate a specific translation for test generation.
			javaGenerator = Generators.getJavaTestGenerator();
			break;
		case TEST_GEN_MODE:
			checkPath(testGenDirPath);
			javaFile = new File(testGenDirPath + File.separator + name + ATG_EXTENSION);
			// Generate a class designed for generating tests for the translated Java class.
			javaGenerator = Generators.getJavaAtgGenerator();
			break;
		default:
			throw new IllegalArgumentException("Unrecognized mode: " + mode);
		}
		deleteExisting(javaFile);
		// Generate the translation.
		generate(model, userOptions, javaFile, javaGenerator);

		return javaFile;
	}

	/**
	 * Generate the translation.
	 * 
	 * @param model              the model of the parsed ASM.
	 * @param userOptions        the TranslationOptions selected by the user.
	 * @param javaFile           the java file to generate.
	 * @param asmToJavaGenerator instance of the generator to use.
	 * @throws IOException                 if an I/O error occurs.
	 * @throws DomainNotSupportedException if a domain is not supported by the Atg
	 *                                     generator.
	 */
	private void generate(AsmCollection model, TranslatorOptions userOptions, File javaFile,
			AsmToJavaGenerator asmToJavaGenerator) throws IOException, DomainNotSupportedException {
		logger.info("Generating {}.", javaFile);
		asmToJavaGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), JAVA, userOptions);
	}

	/**
	 * Compiles the file with the given name and returns the result of the
	 * compilation process.
	 * 
	 * @param javaFile the java file to compile.
	 * @throws IOException          if an I/O error occurs.
	 * @throws TranslationException if the the result of the compilation is not
	 *                              successful.
	 */
	void compileFile(File javaFile) throws IOException, TranslationException {
		logger.info("JavaCompiler: compiling the .java class...");
		checkPath(compilerDirPath);
		CompileResult result = compilerJava.compileFile(javaFile, compilerDirPath, compilerVersion);
		if (!result.getSuccess()) {
			logger.error("Failed to compile the file {}.", javaFile.getName());
			throw new TranslationException("Unable to compile the file, the file has compilation errors: " + result);
		}
		logger.info("File {} compiled with no errors.", javaFile.getName());
	}

	/**
	 * Compile the given files in the output directory and returns the result of the
	 * compilation process.
	 * 
	 * @param files list of files to compile.
	 * @throws IOException if an I/O error occur.
	 * @throw TranslationException if the the result of the compilation is not
	 *        successful.
	 */
	void compileExportedFiles(List<File> files) throws TranslationException, IOException {
		if (files.isEmpty()) {
			logger.error("No files to compile.");
			throw new TranslationException("Unable to compile the files, the files list is empty");
		}
		CompileResult compilerResult = compilerJava.compileFiles(files, outputFolder, compilerVersion);
		String filesString = files.stream().map(File::getName).collect(Collectors.joining(", "));
		if (!compilerResult.getSuccess()) {
			logger.error("Failed to compile the file {}", filesString);
			throw new TranslationException("Unable to compile the exported files: " + filesString
					+ ".\nThe file has compilation errors:\n" + compilerResult);
		}
		logger.info("File {} compiled with no errors.", filesString);
	}

	/**
	 * Exports (copies) the specified Java file to a desired output folder.
	 *
	 * @param javaInputFile the Java file to be exported.
	 * @return javaOutFile the exported file.
	 * @throws IOException if an I/O error occurs.
	 */
	File exportFile(File javaInputFile) throws IOException {
		File javaOutFile = new File(outputFolder + File.separator + javaInputFile.getName());
		logger.info("Exporting {} to: {}.", javaInputFile, outputFolder);
		checkPath(outputFolder);
		try (InputStream in = new BufferedInputStream(Files.newInputStream(javaInputFile.toPath()));
				OutputStream out = new BufferedOutputStream(Files.newOutputStream(javaOutFile.toPath()))) {
			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}
		return javaOutFile;
	}

	/**
	 * Cleans the input directory by removing execution-related files.
	 */
	void cleanInputDir() {
		if (inputFolder.toFile().exists() && inputFolder.toFile().isDirectory()) {
			for (File file : inputFolder.toFile().listFiles()) {
				if (excludeList.contains(file.getName())) {
					continue;
				}
				this.cleanRecursively(file);
			}
		}
	}

	/**
	 * Sets the output directory where the generated files will be stored. Create a
	 * new directory if it doesn't exists.
	 * 
	 * @param outputDir the path of the output directory.
	 * @throws IOException
	 */
	void setOutputDir(String outputDir) throws IOException {
		this.outputFolder = Paths.get(outputDir);
		// check if exists and if not create a new directory
		checkPath(outputFolder);
	}

	/**
	 * Sets the version of the java compiler.
	 * 
	 * @param javaVersion the java version.
	 * @throws SetupException if an error occurs during the setup process.
	 */
	void setCompilerVersion(String javaVersion) throws SetupException {
		try {
			int javaVersionInt = Integer.parseInt(javaVersion);
			if (javaVersionInt < 1 || javaVersionInt > 21) {
				throw new NumberFormatException();
			}
			this.compilerVersion = javaVersion;
			logger.info("Setting the version of Java compiler to: {}.", javaVersion);
		} catch (NumberFormatException e) {
			logger.error("Failed to set the version of Java compiler: {}, uses the default one: {}.", javaVersion,
					javaVersion);
			logger.error("Please enter a valid java version {}.", e.getMessage());
			throw new SetupException("Unable to set the version of Java compiler to: " + javaVersion);
		}

	}

	/**
	 * Check if the input directory exists and if it contains the required
	 * libraries. If not, creates a new input directory and adds the missing
	 * libraries.
	 * 
	 * @return {@code True} if the operation completes with success, {@code False}
	 *         otherwise.
	 */
	private boolean checkInputDir() {
		File inputDir = new File(inputFolder.toString());

		// if the input directory doesn't exist, creates a new one.
		if (!inputDir.exists()) {
			logger.info("input directory not found, creating: {}.", inputDir);
			if (!inputDir.mkdirs()) {
				logger.error("failed to create a new input directory: {}.", inputDir);
				// return false if the mkdir fails.
				return false;
			}
		}

		// check if the input directory contains the STDL folder.
		for (File file : inputDir.listFiles()) {
			if (file.isDirectory() && file.getName().equals(STDL)) {
				logger.info("Found the {} folder: {}.", STDL, file);
				// OK, the input folder exists and contains the STDL folder
				// check if it contains all the required libraries, add the missing ones
				// and stop the execution.
				return checkLibraries(file);
			}
		}

		// the STDL folder not exists.
		// creates the STDL folder.
		Path stdlPath = Paths.get(inputFolder.toString(), STDL);
		File stdl = new File(stdlPath.toString());
		logger.info("STDL directory not found, creating: {}.", stdl);
		if (!stdl.mkdir()) {
			logger.error("failed to create a new STDL directory: {}.", stdl);
			// return false if the mkdir fails.
			return false;
		}

		// populates the STDL folder
		for (String library : requiredStdlLibraries) {
			// don't use File.separator because execution in jar will fail.
			String resourceStdlFilePath = STDL + SLASH + library;
			Path inputStdlFilePath = Paths.get(stdl.toString(), library);
			if (!copyResourceFile(resourceStdlFilePath, inputStdlFilePath)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check if the STDL folder contains all the requires libraries, and if not add
	 * the ones missing.
	 * 
	 * @param stdlFolder File of the STDL folder.
	 * @return {@code True} if the operation completes with success, {@code False}
	 *         otherwise.
	 */
	private boolean checkLibraries(File stdlFolder) {

		// list of requiredStdlLibraries already in the STDL input folder.
		List<String> stdlLibraries = Arrays.stream(stdlFolder.list()).toList();

		for (String library : requiredStdlLibraries) {
			// if the current STDL input folder doesn't contain the required library.
			if (!stdlLibraries.contains(library)) {
				logger.info("The {} is missing...", library);
				// don't use File.separator because execution in jar will fail.
				String resourceStdlFilePath = STDL + SLASH + library;
				Path inputStdlFilePath = Paths.get(stdlFolder.toString(), library);
				logger.info("Adding the {} to the {} folder: {}.", library, STDL, inputStdlFilePath);
				if (!copyResourceFile(resourceStdlFilePath, inputStdlFilePath)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Copy the specified resource file to the desired folder.
	 * 
	 * @param inputFilePath  String path to the resource file to copy (please don't
	 *                       use the "\" character or the File.separator in the
	 *                       path).
	 * @param outputFilePath Path to the output file.
	 * @return {@code True} if the operation completes with success, {@code False}
	 *         otherwise.
	 */
	private boolean copyResourceFile(String inputFilePath, Path outputFilePath) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFilePath);
				OutputStream outputStream = new FileOutputStream(outputFilePath.toString());) {
			logger.info("Copying the resource file: {} to: {}.", inputFilePath, outputFilePath);
			if (inputStream == null) {
				throw new FileNotFoundException("Resource not found in classpath: " + inputFilePath);
			}
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			logger.error("Copying resource file operation failed.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Recursively deletes files and directories.
	 * 
	 * @param file the file or directory to delete.
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

		try {
			Files.delete(file.toPath());
			logger.info("Deleted: {}.", file.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Failed to delete: {}\nError message: {}.", file.getAbsolutePath(), e.getMessage());
		}

	}

	/**
	 * Checks whether the specified path exists, and if not, creates it.
	 *
	 * @param path the path to check.
	 * @throws IOException if an IO error occurs.
	 */
	private void checkPath(Path path) throws IOException {
		if (!Files.exists(path)) {
			this.createPath(path);
		}
	}

	/**
	 * Creates a directory at the specified path.
	 *
	 * @param path The path where the directory should be created.
	 * @throws IOException if an IO error occurs.
	 */
	private void createPath(Path path) throws IOException {
		logger.info("Path: {} doesn't exist.", path);
		logger.info("Creating path: {}...", path);
		Files.createDirectories(path);
		logger.info("Path: {} created with success.", path);
	}

	/**
	 * Check if the file exists and delete it.
	 *
	 * @param file the file to delete.
	 * @throws IOException if an IO error occurs.
	 */
	private void deleteExisting(File file) throws IOException {
		if (file.exists()) {
			try {
				Files.delete(file.toPath());
			} catch (NoSuchFileException e) {
				logger.error("File not found, skipping: {}.", file.getPath());
			}
		}
	}

}
