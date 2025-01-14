package asmeta.junit2avalla.filewriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.junit2avalla.model.ScenarioFile;

/**
 * The {@code FileWriterImpl} class provides methods to write a
 * {@link ScenarioFile} to a specified output folder or a default folder in the
 * project root directory. It implements the {@link FileWriter} interface.
 */
public class FileWriterImpl implements FileWriter {

	private final Logger log = LogManager.getLogger(FileWriterImpl.class);

	/**
	 * The default output path where files will be written if no specific folder is
	 * provided. This is the "output" folder within the project root.
	 */
	private static final Path DEFAULT_OUTPUT_PATH = Paths.get(System.getProperty("user.dir"), "output");

	/**
	 * No args constructor.
	 */
	public FileWriterImpl() {
		// Empty constructor
	}

	/**
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@Override
	public boolean writeToFile(ScenarioFile scenarioFile) throws IOException {
		return write(scenarioFile, DEFAULT_OUTPUT_PATH);
	}

	/**
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@Override
	public boolean writeToFile(ScenarioFile scenarioFile, Path outputFolder) throws IOException {
		return write(scenarioFile, outputFolder);
	}

	/**
	 * Writes the given {@link ScenarioFile} to the specified output folder. Ensures
	 * that the folder exists and creates it if necessary.
	 *
	 * @param scenarioFile The scenario file to be written.
	 * @param outputFolder The folder where the file will be saved.
	 * @return {@code true} if the file was successfully written, {@code false}
	 *         otherwise.
	 * @throws IOException 
	 * @throws RuntimeException if an I/O error occurs during directory creation or
	 *                          file writing.
	 */
	private boolean write(ScenarioFile scenarioFile, Path outputFolder) throws IOException {

		log.info("Exporting: {} to the folder: {}", scenarioFile.getName(), outputFolder);

		if (!checkPath(outputFolder)) {
			createPath(outputFolder);
		}

		Path fullOutputPath = outputFolder.resolve(scenarioFile.getName() + scenarioFile.getExtension());
		log.info("Output file path: {} .", fullOutputPath);

		log.info("Writing file to path: {} .", fullOutputPath);
		Files.write(fullOutputPath, scenarioFile.getText().getBytes(StandardCharsets.UTF_8));
		log.info("File writing operation completed with success.");

		return true;
	}

	/**
	 * Checks if the given path exists.
	 *
	 * @param path The path to check.
	 * @return {@code true} if the path exists, {@code false} otherwise.
	 */
	private boolean checkPath(Path path) {
		return Files.exists(path);
	}

	/**
	 * Creates a directory at the specified path if it does not exist. Prints a
	 * message if the directory is created or an error occurs.
	 *
	 * @param path The path where the directory should be created.
	 * @throws IOException 
	 */
	private void createPath(Path path) throws IOException {
		log.info("Path {} doesn't exist.", path);
		log.info("Creating path {} ...", path);
		Files.createDirectories(path);
		log.info("Path {} created with success.", path);
	}
}
