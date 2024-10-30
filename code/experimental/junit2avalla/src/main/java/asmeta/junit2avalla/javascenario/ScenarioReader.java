package asmeta.junit2avalla.javascenario;

import java.nio.file.Path;
import java.util.List;
import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;

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
   * @param javaArgumentTermList the Argument list of the stepFunction
   * @return a list of {@link Scenario} objects parsed from the file,
   * or an empty list if an error occurs
   */
  public List<Scenario> readJavaScenario(Path path, List<JavaArgumentTerm> javaArgumentTermList);

}
