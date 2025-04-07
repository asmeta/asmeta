package asmeta.evotest.junit2avalla.model.terms;

/**
 * Represents a Java variable term, extending the {@code JavaArgumentTerm} class.
 * <p>
 * This class is used to define a variable in a Java context, storing its value as a string.
 * It provides methods to retrieve and set the value of the variable.
 * </p>
 */
public class JavaVariableTerm extends JavaArgumentTerm {

  /** The value of the variable. */
  private String value;
  
  /**
   * Default constructor for the {@code JavaVariableTerm} class.
   * Initializes an empty variable without a value.
   */
  public JavaVariableTerm() {
	// Empty constructor
  }

  /**
   * Returns the value of the variable.
   *
   * @return the variable's value as a {@code String}.
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the variable.
   *
   * @param value the value of the variable.
   */
  public void setValue(String value) {
    this.value = value;
  }

}

