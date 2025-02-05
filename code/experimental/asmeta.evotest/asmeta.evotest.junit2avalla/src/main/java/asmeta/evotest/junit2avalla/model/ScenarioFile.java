package asmeta.evotest.junit2avalla.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a scenario file used in the Avalla framework.
 * A scenario file has a specific extension, name, and associated text content.
 */
@Getter
@Setter
@NoArgsConstructor
public class ScenarioFile {

  /**
   * The default file extension for all scenario files.
   */
  public static final String EXTENSION = ".avalla";

  /**
   * The name of the scenario file.
   */
  private String name;

  /**
   * The text of the scenario file.
   */
   private String text;
}
