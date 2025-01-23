package org.asmeta.junit2avalla.model;

/**
 * Represents a scenario file used in the Avalla framework.
 * A scenario file has a specific extension, name, and associated text content.
 */
public class ScenarioFile {

  /**
   * The default file extension for all scenario files.
   */
  private static final String EXTENSION = ".avalla";

  /**
   * The name of the scenario file.
   */
  private String name;

  /**
   * The text of the scenario file.
   */
  private String text;

  /**
   * No args constructor for {@code ScenarioFile}.
   * Initializes an empty scenario file without a name or text.
   */
  public ScenarioFile() {
	  // Empty constructor
  }

  /**
   * Get the name of the scenario file.
   *
   * @return the name of the scenario file as a {@code String}.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the scenario file.
   *
   * @param name the name to be set for the scenario file.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the text content of the scenario file.
   *
   * @return the text content of the scenario file as a {@code String}.
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text content of the scenario file.
   *
   * @param text the text to be set for the scenario file.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Get the file extension of the scenario file.
   *
   * @return the file extension as a {@code String}.
   */
  public String getExtension() {
    return EXTENSION;
  }
}
