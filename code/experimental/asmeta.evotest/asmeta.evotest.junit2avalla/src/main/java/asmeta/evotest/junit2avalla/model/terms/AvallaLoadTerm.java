package asmeta.evotest.junit2avalla.model.terms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a load term in the Avalla language,
 * which is used to load an ASM (Abstract State Machine) specification.
 * <p>
 * This class stores the name of an ASM to be loaded and provides methods to retrieve and modify
 * this name.
 * It is typically used to specify the ASM that should be loaded during a scenario or execution.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class AvallaLoadTerm extends AvallaTerm {

  /** The name of the ASM (Abstract State Machine) to be loaded. */
  private String asmName;

}
