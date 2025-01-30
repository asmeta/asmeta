package asmeta.evotest.junit2avalla.model.terms;

/**
 * Represents a load term in the Avalla language,
 * which is used to load an ASM (Abstract State Machine) specification.
 * <p>
 * This class stores the name of an ASM to be loaded and provides methods to retrieve and modify
 * this name.
 * It is typically used to specify the ASM that should be loaded during a scenario or execution.
 * </p>
 */
public class AvallaLoadTerm extends AvallaTerm {

  /** The name of the ASM (Abstract State Machine) to be loaded. */
  private String asmName;

  /**
   * Default constructor that creates an empty {@code AvallaLoadTerm}.
   */
  public AvallaLoadTerm() {
  }

  /**
   * Constructs an {@code AvallaLoadTerm} with the specified ASM name.
   *
   * @param load the name of the ASM to be loaded.
   */
  public AvallaLoadTerm(String load) {
    this.asmName = load;
  }

  /**
   * Returns the name of the ASM to be loaded.
   *
   * @return the ASM name.
   */
  public String getLoad() {
    return asmName;
  }

  /**
   * Sets the name of the ASM to be loaded.
   *
   * @param asmName the ASM name to set.
   */
  public void setLoad(String asmName) {
    this.asmName = asmName;
  }
}
