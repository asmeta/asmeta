package asmeta.junit2avalla.filewriter;

import asmeta.junit2avalla.model.ScenarioFile;
import java.nio.file.Path;

/**
 * Interface for writing {@link ScenarioFile} objects to a file.
 */
public interface FileWriter {

  /**
   * Writes the specified {@link ScenarioFile} to a default location.
   *
   * @param scenarioFile the {@link ScenarioFile} to write
   * @return {@code true} if the write operation was successful, {@code false} otherwise
   */
  boolean writeToFile(ScenarioFile scenarioFile);

  /**
   * Writes the specified {@link ScenarioFile} to the given output path.
   *
   * @param scenarioFile the {@link ScenarioFile} to write
   * @param outputFolder the {@link Path} representing the desired output location
   * @return {@code true} if the write operation was successful, {@code false} otherwise
   */
  boolean writeToFile(ScenarioFile scenarioFile, Path outputFolder);
}
