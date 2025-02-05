package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a header term in the Avalla language, which holds the name of a scenario.
 * <p>
 * This class stores a scenario name and provides methods to retrieve and modify it.
 * It is typically used to specify the header information of a scenario within the Avalla language.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class AvallaHeaderTerm extends AvallaTerm {

  /** The name of the scenario associated with this header term. */
  private String scenarioName;

}
