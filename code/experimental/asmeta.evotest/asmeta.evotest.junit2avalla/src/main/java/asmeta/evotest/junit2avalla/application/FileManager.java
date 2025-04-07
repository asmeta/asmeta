package asmeta.evotest.junit2avalla.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.avallascenario.ScenarioListMapper;
import asmeta.evotest.junit2avalla.avallascenario.ScenarioListMapperImpl;
import asmeta.evotest.junit2avalla.filewriter.FileWriter;
import asmeta.evotest.junit2avalla.filewriter.FileWriterImpl;
import asmeta.evotest.junit2avalla.javascenario.JUnitParseException;
import asmeta.evotest.junit2avalla.javascenario.ScenarioReader;
import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.ScenarioFile;

/**
 * The {@code FileManager} class provides methods for working with files.
 */
public class FileManager {

	/* constants */
	private static final String USER_DIR = "user.dir";
	private static final String INPUT = "input";
	private static final String OUTPUT = "output";
	private static final String GITIGNORE = ".gitignore";
	private static final String STDL = "STDL";
	private static final String LOGS_DIRECTORY = "logs";

	/** Files/Directory to exclude from cleaning. */
	private static final List<String> excludeList = List.of(GITIGNORE, STDL, LOGS_DIRECTORY);

	/** Logger */
	private final Logger logger = LogManager.getLogger(FileManager.class);

	/** Path to the input directory. */
	private static final Path DEFAULT_INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);

	/** Path to the default output directory. */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);

	/** Path to the input file. */
	private Path inputFilePath;

	/** Path to the input folder. */
	private Path inputFolderPath;

	/** Path to the output folder. */
	private Path outputFolderPath;

	/**
	 * Constructs a {@code FileManager} instance with the default input and output
	 * directories.
	 */
	public FileManager() {
		this.inputFolderPath = DEFAULT_INPUT_DIR_PATH;
		this.outputFolderPath = DEFAULT_OUTPUT_DIR_PATH;
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
	 * Set the Path to the input file.
	 * 
	 * @param inputFile String containing the input file path.
	 * @throws FileNotFoundException if the file is not found.
	 */
	void setInputFilePath(String inputFile) throws FileNotFoundException {
		this.inputFilePath = Paths.get(inputFile);
		if (Files.notExists(inputFilePath)) {
			logger.error("The indicated file doesn't exist: {}.", inputFilePath);
			throw new FileNotFoundException("Unable to find the file: " + inputFilePath);
		}
		logger.info("Found the input file: {}", inputFilePath);
	}

	/**
	 * Given a path to the input file, copies the file into the input directory and
	 * returns the newly generated file.
	 * 
	 * @param junitScenario the path to the input file (relative or absolute).
	 * @return the copied file.
	 * @throws IOException if an I/O error occurs during the file copying process.
	 */
	File retrieveInput(String junitScenario) throws IOException {
		logger.info("Retrieving the input junit file: {}", junitScenario);
		File junitFile = new File(junitScenario);
		if (!junitFile.exists()) {
			logger.error("Failed to locate the input file: {} .", junitFile);
			throw new IOException("File doesn't exist: " + junitFile.toString());
		}

		// check the input folder
		this.checkPath(inputFolderPath);

		// Copy the asm file to the input folder
		Path inputAsmPath = Paths.get(inputFolderPath.toString(), junitFile.getName());
		logger.info("Copying the junit input file from {} to {}.", junitFile, inputAsmPath);
		Files.copy(Paths.get(junitFile.getAbsolutePath()), inputAsmPath, StandardCopyOption.REPLACE_EXISTING);
		return junitFile;
	}

	/**
	 * Run the application.
	 *
	 * @param inputPath      path to the input file.
	 * @param scenarioReader instance of scenarioReader to parse the JUnit file.
	 * @throws IOException         if an I/O error occurs.
	 * @throws JUnitParseException if an error occurs during the parsing process.
	 */
	void runTheApplication(Path inputPath, ScenarioReader scenarioReader) throws IOException, JUnitParseException {

		logger.info("Processing JavaScenario...");
		List<Scenario> scenarioList;
		try {
			scenarioList = scenarioReader.readJavaScenario(inputPath);
		} catch (Exception e) {
			logger.error("Failed to parse the JUnit file: {}.", inputPath.getFileName());
			throw new JUnitParseException("Unable to parse the JUnit file: " + inputPath.getFileName(), e);
		}

		logger.info("Mapping Scenario Files...");
		ScenarioListMapper scenarioListMapper = new ScenarioListMapperImpl();
		List<ScenarioFile> scenarioFiles = scenarioListMapper.mapScenarioListToFileList(scenarioList);

		logger.info("Exporting output Files...");
		this.exportFile(scenarioFiles);

	}

	/**
	 * Sets the input directory where the junit files will be stored. Check if it
	 * exists and in case it doesn't creates a new output directory.
	 * 
	 * @param inputDir the path of the output directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setInputDir(String inputDir) throws IOException {
		this.inputFolderPath = Paths.get(inputDir);
		if (inputFolderPath.toAbsolutePath().toString()
				.equals(Paths.get(System.getProperty(USER_DIR)).toAbsolutePath().toString())
				|| inputFolderPath.toAbsolutePath().toString()
						.equals(Paths.get(System.getProperty(USER_DIR), ".").toAbsolutePath().toString())) {
			logger.warn("The current user directory can't be the path of the custom input working directory.");
			// warning: otherwise the -clean option would erase the entire project directory
			this.inputFolderPath = DEFAULT_INPUT_DIR_PATH;
		}
		logger.info("Setting the input working directory: {}", this.inputFolderPath);
		checkPath(this.inputFolderPath);
	}

	/**
	 * Sets the output directory where the generated files will be stored. Check if
	 * it exists and in case it doesn't creates a new output directory.
	 * 
	 * @param outputDir the path of the output directory.
	 * @throws IOException if an I/O error occurs.
	 */
	void setOutputDir(String outputDir) throws IOException {
		this.outputFolderPath = Paths.get(outputDir);
		logger.info("Setting the output directory: {}", this.outputFolderPath);
		checkPath(outputFolderPath);
	}

	/**
	 * Cleans the input directory by removing execution-related files.
	 */
	void cleanInputDir() {
		logger.info("Cleaning the working directory: {}", inputFolderPath);
		if (inputFolderPath.toFile().exists() && inputFolderPath.toFile().isDirectory()) {
			for (File file : inputFolderPath.toFile().listFiles()) {
				if (excludeList.contains(file.getName())) {
					continue;
				}
				this.cleanRecursively(file);
			}
		}
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
					if (f.getName().equals(LOGS_DIRECTORY))
						continue;
					cleanRecursively(f);
				}
			}
		}
		try {
			Files.delete(file.toPath());
			logger.info("Deleted: {} .", file.getAbsolutePath());
		} catch (IOException e) {
			logger.error("Failed to delete: {} \n Error message: {} .", file.getAbsolutePath(), e.getMessage());
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
		logger.info("Path {} doesn't exist.", path);
		logger.info("Creating path {} ...", path);
		Files.createDirectories(path);
		logger.info("Path {} created with success.", path);
	}

	/**
	 * Exports (copies) the specified Java file to a desired output folder.
	 *
	 * @param scenarioFiles list of Java file to be exported.
	 * @throws IOException if an I/O error occurs during the export.
	 */
	private void exportFile(List<ScenarioFile> scenarioFiles) throws IOException {
		for (ScenarioFile scenarioFile : scenarioFiles) {
			FileWriter fileWriter = new FileWriterImpl();
			boolean result = outputFolderPath == null ? fileWriter.writeToFile(scenarioFile)
					: fileWriter.writeToFile(scenarioFile, outputFolderPath);
			if (result) {
				logger.info("Generated: {} .", scenarioFile.getName());
			} else {
				logger.error("Error generating: {} .", scenarioFile.getName());
			}
		}
		logger.info("Operations completed.");
	}

}
