package asmeta.evotest.junit2avalla.model.terms;

/**
 * The {@code Argument} class represents a function argument with a name and an indicator of whether
 * it is a primitive type or not.
 */
public class JavaArgumentTerm extends JavaTerm {

  /**
   * The name of the Argument.
   */
  private String name;

  /**
   * Flag indicating whether the argument is of a primitive type.
   * <ul>
   *   <li><b>Primitive:</b> 'int' | 'double' | 'float' | 'boolean' | 'char' | 'String' .</li>
   *   <li><b>ComplexType:</b> Identifier '.' Identifier ('.' Identifier)* .</li>
   * </ul>
   */
  private boolean isPrimitive;

  /**
   * Default constructor for the {@code Argument} class. Initializes an empty argument.
   */
  public JavaArgumentTerm() {
	// Empty constructor
  }

  /**
   * Returns the name of the argument.
   *
   * @return the argument's name as a {@code String}.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the Argument.
   *
   * @param name the name of the argument.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns whether the argument is a primitive type.
   *
   * @return {@code true} if the argument is a primitive type, {@code false} otherwise.
   */
  public boolean isPrimitive() {
    return isPrimitive;
  }

  /**
   * Sets whether the argument is a primitive type.
   *
   * @param primitive {@code true} if the argument is a primitive type, {@code false} otherwise.
   */
  public void setPrimitive(boolean primitive) {
    isPrimitive = primitive;
  }

}
