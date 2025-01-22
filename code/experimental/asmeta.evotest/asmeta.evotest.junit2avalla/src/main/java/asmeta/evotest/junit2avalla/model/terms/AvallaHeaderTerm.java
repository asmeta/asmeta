package asmeta.evotest.junit2avalla.model.terms;

/**
 * Represents a header term in the Avalla language, which holds the name of a scenario.
 * <p>
 * This class stores a scenario name and provides methods to retrieve and modify it.
 * It is typically used to specify the header information of a scenario within the Avalla language.
 * </p>
 */
public class AvallaHeaderTerm extends AvallaTerm {

  /** The name of the scenario associated with this header term. */
  private String scenarioName;

  /**
   * Default constructor that creates an empty {@code AvallaHeaderTerm}.
   */
  public AvallaHeaderTerm() {
  }

  /**
   * Constructs an {@code AvallaHeaderTerm} with the specified scenario name.
   *
   * @param scenarioName the name of the scenario.
   */
  public AvallaHeaderTerm(String scenarioName) {
    this.scenarioName = scenarioName;
  }

  /**
   * Returns the name of the scenario associated with this header term.
   *
   * @return the scenario name.
   */
  public String getScenarioName() {
    return scenarioName;
  }

  /**
   * Sets the name of the scenario associated with this header term.
   *
   * @param scenarioName the scenario name to set.
   */
  public void setScenarioName(String scenarioName) {
    this.scenarioName = scenarioName;
  }
}
