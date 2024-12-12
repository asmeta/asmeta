package org.asmeta.evoasmetatg.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.asm2java.application.AsmParsingException;

/**
 * The {@code FileManager} class provides methods for working with files,
 * contains all the paths to the required folders.
 */
public class FileManager {

	/** Logger */
	private static final Logger logger = LogManager.getLogger(FileManager.class);

	/** Current working directory */
	private Path workingDirPath = Paths.get(System.getProperty(TranslatorConstants.USER_DIR));

	/** Absolute path of the Asm input file. */
	private Path inputFilePath;

	/** Absolute path of the output folder */
	private Path outputFolder;
	
	/** Absolute path of the evosuite-target folder (the output of asmeta2java and the input for evosuite). */
	private Path evosuiteTargetPath = Paths.get(workingDirPath.toString(),
			TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_TARGET);
	
	/** Absolute path of the evosuite-tests folder (the output of evosuite and the input of junit2avalla). */
	private Path evosuiteTestsPath = Paths.get(workingDirPath.toString(),
			TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_TESTS);

	/** Absolute path of the Java executable used to run Evosuite. */
	private Path javaJdkPath;

	/*
	 * Absolute path of the directory containing the Evosuite jars (Defaults to
	 * ./evosuite/evosuite-jar).
	 */
	private Path evosuiteJarDirPath = Paths.get(System.getProperty(TranslatorConstants.USER_DIR),
			TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_JAR_DIR);

	/**
	 * Constructs a {@code FileManager} instance.
	 */
	public FileManager() {
		// Empty constructor
	}
	
	/**
	 * Get the path to workingDir in String type.
	 * 
	 * @return String containing the path to the workingDir.
	 */
	String getWorkingDirPathToString() {
		return workingDirPath.toString();
	}

	/**
	 * Get the path to inputFile in String type.
	 * 
	 * @return String containing the path to the inputFile.
	 */
	String getInputFilePathToString() {
		return inputFilePath.toString();
	}

	/**
	 * Get the path to outputFolder in String type.
	 * 
	 * @return String containing the path to the outputFolder.
	 */
	String getOutputFolderToString() {
		return outputFolder.toString();
	}

	/**
	 * Get the path to javaJdk folder in String type.
	 * 
	 * @return String containing the path to the javaJdk folder.
	 */
	String getJavaJdkPathToString() {
		return javaJdkPath.toString();
	}
	
	/**
	 * Get the path to evosuiteTarget in String type.
	 * 
	 * @return String containing the path to the evosuiteTarget.
	 */
	String getEvosuiteTargetPathToString() {
		return evosuiteTargetPath.toString();
	}
	
	/**
	 * Get the path to evosuiteTest in String type.
	 * 
	 * @return String containing the path to the evosuiteTest.
	 */
	String getEvosuiteTestsPathToString() {
		return evosuiteTestsPath.toString();
	}
	
	/**
	 * Get the path to evosuiteJarDir in String type.
	 * 
	 * @return String containing the path to the evosuiteJarDir.
	 */
	String getEvosuiteJarDirPathToString() {
		return evosuiteJarDirPath.toString();
	}

	/**
	 * Sets the current working directory where the application stores intermediate
	 * files
	 * 
	 * @param workingDir path to the custom working directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setWorkingDirPath(String workingDir) throws IOException {
		this.workingDirPath = Paths.get(workingDir);
		logger.info("Setting the current working directory: {}.", workingDirPath);
		// check if the directory exists and if not create a new one.
		File workingDirFile = new File(workingDirPath.toString());
		if (!workingDirFile.exists() || !workingDirFile.isDirectory()) {
			logger.warn("The specified directory doesn't exist, creating: {}.", workingDirPath);
			Files.createDirectories(workingDirPath);
		}
		// update paths based on the workingDir path.
		updatePaths();
	}

	/**
	 * Sets the input path to the file for the translation process.
	 * 
	 * @param inputPath String containing the path of the input asmeta
	 *                  specification.
	 * @throws FileNotFoundException if the file is not found.
	 * @return File of the input asmeta specification.
	 */
	File setInputFilePath(String inputPath) throws FileNotFoundException {
		File inputFile = new File(inputPath);
		// check if the file exists.
		if (!inputFile.exists() || !inputFile.isFile()) {
			logger.error("Can't find the specified input File: {} ", inputPath);
			logger.error("Please digit the absolute path of the file in question.");
			throw new FileNotFoundException(inputPath);
		}
		this.inputFilePath = inputFile.toPath();
		logger.info("Setting the input file: {}.", inputFilePath);

		// check the extension.
		if (!this.inputFilePath.toString().endsWith(TranslatorConstants.ASM_EXTENSION)) {
			logger.error("The file provided isn't an Asmeta specification: {}", inputPath);
			throw new AsmParsingException(
					"The Asmeta specification must have the " + TranslatorConstants.ASM_EXTENSION + " extension.");
		}

		// set the default output folder as the parent folder of the input file.
		setDefaultOutputFolder(inputFile);

		return inputFile;

	}

	/**
	 * Sets the output directory for the process.
	 *
	 * @param outputDir the output directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setOutputFolder(String outputDir) throws IOException {
		File outputDirFile = new File(outputDir);
		if (!outputDirFile.exists() || !outputDirFile.isDirectory()) {
			Files.createDirectories(outputDirFile.toPath());
		}
		this.outputFolder = outputDirFile.toPath();
		logger.info("Setting the output folder: {}.", this.outputFolder);
	}

	/**
	 * Sets the path to the Java jdk folder used to run Evosuite.
	 * 
	 * @param javaPath path to the java jdk folder.
	 * @throws FileNotFoundException if the file is not found.
	 * @return File of the java jdk folder.
	 */
	File setJavaPath(String javaPath) throws FileNotFoundException {
		File javaJdkFolder = new File(javaPath);
		if (!javaJdkFolder.exists() || !javaJdkFolder.isDirectory()) {
			logger.error("Java jdk directory location not valid: {}.", javaJdkFolder.getAbsolutePath());
			logger.error(
					"Please note: If your argument is a string and contains a space, put it in double quotes like \"Program Files\"");
			throw new FileNotFoundException("File not found: " + javaPath);
		}
		logger.info("Java jdk directory found at: {}.", javaJdkFolder.getAbsolutePath());
		File javaFile = new File(Paths.get(javaPath, TranslatorConstants.BIN, TranslatorConstants.JAVA_EXE).toString());
		if (!javaFile.exists() || !javaFile.isFile()) {
			logger.error("Java exe file location not valid: {}.", javaFile.getAbsolutePath());
			throw new FileNotFoundException("File not found: " + javaPath);
		}
		this.javaJdkPath = javaFile.toPath();
		logger.info("Setting the path to the java exe: {}.", this.javaJdkPath);

		return javaJdkFolder;
	}
	
	/**
	 * Sets the path to the Evosuite jar folder.
	 * 
	 * @param evosuitePath path to the evosuite jar folder.
	 * @throws FileNotFoundException if the file is not found.
	 * @return File of the evosuite jar folder.
	 */
	File setEvosuitePath(String evosuitePath) throws FileNotFoundException {
		File evosuiteFolder = new File(evosuitePath);
		if (!evosuiteFolder.exists() || !evosuiteFolder.isDirectory()) {
			logger.error("Evosuite jar directory location not valid: {}.", evosuiteFolder.getAbsolutePath());
			logger.error(
					"Please note: If your argument is a string and contains a space, put it in double quotes like \"Program Files\"");
			throw new FileNotFoundException("File not found: " + evosuitePath);
		}
		logger.info("Evosuite jar directory found at: {}.", evosuiteFolder.getAbsolutePath());
		this.evosuiteJarDirPath = evosuiteFolder.toPath();
		logger.info("Setting the path to the Evosuite jar folder: {}.", this.evosuiteJarDirPath);
		
		return evosuiteFolder;
	}

	/**
	 * Set the default output folder as the parent folder of the input file (or as
	 * the workingDirPath/output if the parent folder is not a valid directory)
	 * 
	 * @param inputFile File of the asmeta specification.
	 */
	private void setDefaultOutputFolder(File inputFile) {
		File inputFolder = inputFile.getParentFile();
		if (inputFolder != null && inputFolder.isDirectory()) {
			this.outputFolder = inputFolder.toPath();
			logger.info("Setting the default output folder: {}.", outputFolder);
		} else {
			logger.warn("The parent folder is not a valid directory: {}", inputFolder);
			this.outputFolder = Paths.get(workingDirPath.toString(), TranslatorConstants.OUTPUT);
			logger.info("Setting the default output folder: {}.", outputFolder);
		}
	}
	
	/**
	 * Update all the paths related on the workingDir path.
	 */
	private void updatePaths() {
		this.evosuiteTargetPath = Paths.get(this.getWorkingDirPathToString(),
				TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_TARGET);
		this.evosuiteTestsPath = Paths.get(this.getWorkingDirPathToString(),
				TranslatorConstants.EVOSUITE, TranslatorConstants.EVOSUITE_TESTS);
	}

}
