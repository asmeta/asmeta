package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code Argument} class represents a function argument with a name and an indicator of whether
 * it is a primitive type or not.
 */
@Getter
@Setter
@NoArgsConstructor
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

}
