package asmeta.evotest.junit2avalla.model.terms;

/**
 * Abstract class representing a generic Java term.
 * <p>
 * {@code JavaTerm} extends the {@code Term} class and provides a common structure
 * for Java-specific terms, including a type field that describes the term's type.
 * It serves as a base class for more specialized Java terms.
 * </p>
 */
public abstract class JavaTerm extends Term {

  /** The type of the Java term. */
  private String type;

  /**
   * Sets the type of the Java term.
   *
   * @param type the type of the Java term as a {@code String}.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Returns the type of the Java term.
   *
   * @return the type of the Java term as a {@code String}.
   */
  public String getType() {
    return type;
  }
}
