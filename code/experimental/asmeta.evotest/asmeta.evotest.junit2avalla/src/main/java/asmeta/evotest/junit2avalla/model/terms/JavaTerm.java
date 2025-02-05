package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class representing a generic Java term.
 * <p>
 * {@code JavaTerm} extends the {@code Term} class and provides a common structure
 * for Java-specific terms, including a type field that describes the term's type.
 * It serves as a base class for more specialized Java terms.
 * </p>
 */
@Getter
@Setter
public abstract class JavaTerm extends Term {

  /** The type of the Java term. */
  private String type;

}
