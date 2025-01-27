package org.asmeta.junit2avalla.model.terms;

/**
 * Represents a set term in the Avalla language, which holds a name and a corresponding value.
 * <p>
 * This class is used to define a term with a specific name and value, typically representing
 * a setting or configuration within a scenario in the Avalla framework.
 * </p>
 */
public class AvallaSetTerm extends AvallaTerm {

  /**
   * The name of the Set term.
   */
  private String name;

  /**
   * The value of the Set term.
   */
  private String value;

  /**
   * Default constructor for the {@code AvallaSetTerm} class.
   * Initializes an empty {@code AvallaSetTerm} with no name or value.
   */
  public AvallaSetTerm() {
  }

  /**
   * Constructs an {@code AvallaSetTerm} with the specified name and value.
   *
   * @param name  the name of the Set term.
   * @param value the value of the Set term.
   */
  public AvallaSetTerm(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Sets the name of the Set term.
   *
   * @param name the name to set for this Set term.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the value of the Set term.
   *
   * @param value the value to set for this Set term.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Returns the name of the Set term.
   *
   * @return the name of the Set term.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the value of the Set term.
   *
   * @return the value of the Set term.
   */
  public String getValue() {
    return value;
  }
}
