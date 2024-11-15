package org.asmeta.junit2avalla.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asmeta.junit2avalla.avallascenario.ScenarioListMapper;
import org.asmeta.junit2avalla.avallascenario.ScenarioListMapperImpl;
import org.asmeta.junit2avalla.filewriter.FileWriter;
import org.asmeta.junit2avalla.filewriter.FileWriterImpl;
import org.asmeta.junit2avalla.javascenario.ScenarioReader;
import org.asmeta.junit2avalla.javascenario.ScenarioReaderImpl;
import org.asmeta.junit2avalla.model.Scenario;
import org.asmeta.junit2avalla.model.ScenarioFile;

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
	
	/** Files/Directory to exclude from cleaning. */
	private static final List<String> excludeList = List.of(GITIGNORE,STDL);

	/** Logger */
	private static final Logger logger = LogManager.getLogger(FileManager.class);
	
	/** Path to the input directory. */
	private static final Path INPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), INPUT);
	
	/** Path to the default output directory. */
	private static final Path DEFAULT_OUTPUT_DIR_PATH = Paths.get(System.getProperty(USER_DIR), OUTPUT);
	
    /** Path to the output folder. */
    private Path outputFolder;

    /**
     * Constructs a {@code FileManager} instance with the default output directory.
     */
	public FileManager() {
		this.outputFolder = DEFAULT_OUTPUT_DIR_PATH;
	}
	
	/**
     * Given a path to the input file, copies the file into the
     * input directory and returns the newly generated file.
     * 
     * @param file the path to the input file (relative or absolute).
     * @return the copied file.
     * @throws IOException if an I/O error occurs during the file copying process.
     */
	File retrieveInput(Path junitScenario) throws IOException {
		File junitFile = junitScenario.toFile();
		if (!junitFile.exists()) {
			logger.error("Failed to locate the input file: {} .", junitFile);
			throw new IOException("File doesn't exist: " + junitFile.toString());
		}
		// Copy the asm file to the input folder
		Path inputAsmPath = Paths.get(INPUT_DIR_PATH.toString(), junitFile.getName());
		Files.copy(Paths.get(junitFile.getAbsolutePath()), inputAsmPath, StandardCopyOption.REPLACE_EXISTING);
		return junitFile;
	}
	
	/**
	 * Run the application.
	 *
	 * @param inputPath            path to the input file.
	 * @throws IOException 
	 */
	void runTheApplication(Path inputPath) throws IOException {

		logger.info("Processing JavaScenario...");
		ScenarioReader scenarioReader = new ScenarioReaderImpl();
		List<Scenario> scenarioList = scenarioReader.readJavaScenario(inputPath);

		logger.info("Mapping Scenario Files...");
		ScenarioListMapper scenarioListMapper = new ScenarioListMapperImpl();
		List<ScenarioFile> scenarioFiles = scenarioListMapper.mapScenarioListToFileList(scenarioList);

		logger.info("Exporting output Files...");
		this.exportFile(scenarioFiles);
		
	}
		
    /**
     * Sets the output directory where the generated files will be stored.
     * Check if it exists and in case it doesn't creates a new output directory.
     * 
     * @param outputDir the path of the output directory.
     * @throws IOException 
     */
	void setOutputDir(String outputDir) throws IOException {
		this.outputFolder = Paths.get(outputDir);
		checkPath(outputFolder);
	}
	
    /**
     * Cleans the input directory by removing execution-related files.
     */
	void cleanInputDir() {
		if (INPUT_DIR_PATH.toFile().exists() && INPUT_DIR_PATH.toFile().isDirectory()) {
			for (File file : INPUT_DIR_PATH.toFile().listFiles()) {
				if(excludeList.contains(file.getName())) {
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
		if( !Files.exists(path) ) {
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
     * @param javaFile the Java file to be exported.
     * @throws IOException if an I/O error occurs during the export.
     */
	private void exportFile(List<ScenarioFile> scenarioFiles) throws IOException {
		for (ScenarioFile scenarioFile : scenarioFiles) {
			FileWriter fileWriter = new FileWriterImpl();
			boolean result = outputFolder == null ? fileWriter.writeToFile(scenarioFile)
					: fileWriter.writeToFile(scenarioFile, outputFolder);
			if (result) {
				logger.info("Generated: {} .", scenarioFile.getName());
			} else {
				logger.error("Error generating: {} .", scenarioFile.getName());
			}
		}
		logger.info("Operations completed.");
	}
	
	

}
