package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a check term in the Avalla language,
 * which compares two terms: a left term and a right term.
 * <p>
 * This class stores two string values: the left term and the right term,
 * and provides methods to retrieve and modify these values.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class AvallaCheckTerm extends AvallaTerm {

  /** The left term to be compared. It's usually a variable name */
  private String leftTerm;

  /** The right term to be compared. It's usually a value */
  private String rightTerm;

}
