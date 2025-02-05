package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Java variable term, extending the {@code JavaArgumentTerm} class.
 * <p>
 * This class is used to define a variable in a Java context, storing its value as a string.
 * It provides methods to retrieve and set the value of the variable.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class JavaVariableTerm extends JavaArgumentTerm {

  /** The value of the variable. */
  private String value;

}

