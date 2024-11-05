package asmeta.junit2avalla.filewriter;

import asmeta.junit2avalla.model.ScenarioFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code FileWriterImpl} class provides methods to write a {@link ScenarioFile} to a specified
 * output folder or a default folder in the project root directory. It implements the
 * {@link FileWriter} interface.
 */
public class FileWriterImpl implements FileWriter {

  private static final Logger log = LogManager.getLogger(FileWriterImpl.class);

  /**
   * The default output path where files will be written if no specific folder is provided. This is
   * the "output" folder within the project root.
   */
  private static final Path DEFAULT_OUTPUT_PATH = Paths.get(
      System.getProperty("user.dir"), "output");

  /**
   * Constructs a {@code FileWriter} instance.
   */
  public FileWriterImpl() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean writeToFile(ScenarioFile scenarioFile) {
    return write(scenarioFile, DEFAULT_OUTPUT_PATH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean writeToFile(ScenarioFile scenarioFile, Path outputFolder) {
    return write(scenarioFile, outputFolder);
  }

  /**
   * Writes the given {@link ScenarioFile} to the specified output folder. Ensures that the folder
   * exists and creates it if necessary.
   *
   * @param scenarioFile The scenario file to be written.
   * @param outputFolder The folder where the file will be saved.
   * @return {@code true} if the file was successfully written, {@code false} otherwise.
   * @throws RuntimeException if an I/O error occurs during directory creation or file writing.
   */
  private boolean write(ScenarioFile scenarioFile, Path outputFolder) {

    log.info("Exporting: {} to the folder: {}", scenarioFile.getName(), outputFolder);

    if (!checkPath(outputFolder)) {
      createPath(outputFolder);
    }

    Path fullOutputPath = outputFolder.resolve(
        scenarioFile.getName() + scenarioFile.getExtension());
    log.info("Output file path: {} .", fullOutputPath);

    boolean result = false;
    try {
      log.info("Writing file to path: {} .", fullOutputPath);
      Files.write(fullOutputPath, scenarioFile.getText().getBytes(StandardCharsets.UTF_8));
      log.info("File writing operation completed with success.");
      result = true;
    } catch (IOException e) {
      log.error("Error writing to file: {} .", fullOutputPath);
      throw new RuntimeException("Error writing to file: " + fullOutputPath, e);
    }

    return result;
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
   * Creates a directory at the specified path if it does not exist. Prints a message if the
   * directory is created or an error occurs.
   *
   * @param path The path where the directory should be created.
   */
  private void createPath(Path path) {
    try {
      log.info("Path {} doesn't exist.", path);
      log.info("Creating path {} ...", path);
      Files.createDirectories(path);
      log.info("Path {} created with success.", path);
    } catch (IOException e) {
      log.error("Failed to create path: {} .", path);
      throw new RuntimeException("Failed to create path: " + path, e);
    }
  }
}
