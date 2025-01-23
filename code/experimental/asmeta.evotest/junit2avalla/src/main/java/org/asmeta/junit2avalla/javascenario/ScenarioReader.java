package org.asmeta.junit2avalla.javascenario;

import java.nio.file.Path;
import java.util.List;

import org.asmeta.junit2avalla.model.Scenario;

/**
 * The {@code ScenarioReader} interface defines the contract for reading and parsing
 * Java scenario files.
 */
public interface ScenarioReader {

  /**
   * Reads a java scenario from the file at the specified {@code path}
   * and parses its content to retrieve a list of {@link Scenario} objects.
   *
   * @param path the {@link Path} to the file containing the scenario
   * @return a list of {@link Scenario} objects parsed from the file,
   * or an empty list if an error occurs
   */
  public List<Scenario> readJavaScenario(Path path);

}
