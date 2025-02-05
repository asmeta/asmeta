package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a set term in the Avalla language, which holds a name and a corresponding value.
 * <p>
 * This class is used to define a term with a specific name and value, typically representing
 * a setting or configuration within a scenario in the Avalla framework.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class AvallaSetTerm extends AvallaTerm {

  /**
   * The name of the Set term.
   */
  private String name;

  /**
   * The value of the Set term.
   */
  private String value;

}
