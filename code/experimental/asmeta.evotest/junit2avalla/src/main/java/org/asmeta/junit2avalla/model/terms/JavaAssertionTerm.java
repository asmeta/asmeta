package org.asmeta.junit2avalla.model.terms;

/**
 * Represents a Java assertion term used to compare an actual value with an expected value in the
 * Java context.
 * <p>
 * This class is part of the Java-specific term hierarchy and is used to define assertions where an
 * actual result is compared to an expected result. It provides methods to retrieve and set both the
 * actual and expected values.
 * </p>
 */
public class JavaAssertionTerm extends JavaTerm {

  /**
   * The actual value to be compared in the assertion.
   */
  private String actual;

  /**
   * The expected value to compare against the actual value in the assertion.
   */
  private String expected;

  /**
   * Default constructor for the {@code JavaAssertionTerm} class. Initializes an empty assertion
   * term without specifying the actual or expected values.
   */
  public JavaAssertionTerm() {
	// Empty constructor
  }

  /**
   * Returns the actual value to be compared in the assertion.
   *
   * @return the actual value as a {@code String}.
   */
  public String getActual() {
    return actual;
  }

  /**
   * Sets the actual value to be compared in the assertion.
   *
   * @param actual the actual value to set.
   */
  public void setActual(String actual) {
    this.actual = actual;
  }

  /**
   * Returns the expected value to compare against the actual value in the assertion.
   *
   * @return the expected value as a {@code String}.
   */
  public String getExpected() {
    return expected;
  }

  /**
   * Sets the expected value to compare against the actual value in the assertion.
   *
   * @param expected the expected value to set.
   */
  public void setExpected(String expected) {
    this.expected = expected;
  }
}
