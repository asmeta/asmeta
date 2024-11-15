package org.asmeta.junit2avalla.javascenario;

import org.asmeta.junit2avalla.model.Scenario;
import org.asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import org.asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import org.asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import org.asmeta.junit2avalla.model.terms.AvallaSetTerm;
import org.asmeta.junit2avalla.model.terms.AvallaStepTerm;
import org.asmeta.junit2avalla.model.terms.JavaAssertionTerm;
import org.asmeta.junit2avalla.model.terms.JavaVariableTerm;

/**
 * The {@code ScenarioManager} interface defines the methods required to manage and modify scenarios
 * within the Avalla framework. It provides a series of methods to add various types of terms
 * (header, load, set, step, and check) to a scenario.
 */
public interface ScenarioManager {

  /**
   * Adds a {@link AvallaHeaderTerm} to the specified scenario.
   *
   * @param avallaScenario the scenario to which the header term is added.
   * @param asmName        the name of the ASM specification.
   * @param scenarioName   the name (or ID) of the scenario.
   */
  void setHeaderTerm(Scenario avallaScenario, String asmName, int scenarioName);

  /**
   * Adds a {@link AvallaLoadTerm} to the specified scenario.
   *
   * @param avallaScenario the scenario to which the load term is added.
   * @param asmName        the name of the ASM specification to be loaded.
   */
  void setLoadTerm(Scenario avallaScenario, String asmName);

  /**
   * Adds a {@link AvallaSetTerm} object to the specified scenario.
   *
   * @param avallaScenario the scenario to which the set terms are added.
   * @param variablesList  the variables to set in the scenario.
   */
  void setSetTerm(Scenario avallaScenario, JavaVariableTerm javaVariableTerm);

  /**
   * Adds an {@link AvallaStepTerm} to the specified scenario. This term represents a step in the
   * execution of the ASM.
   *
   * @param avallaScenario the scenario to which the step term is added.
   */
  void setStepTerm(Scenario avallaScenario);

  /**
   * Adds an {@link AvallaCheckTerm} to the specified scenario. This term is used to check the
   * equality of two values.
   *
   * @param avallaScenario    the scenario to which the check term is added.
   * @param javaAssertionTerm a {@link JavaAssertionTerm} containing the expected and actual values
   *                          to be checked.
   */
  void setCheckTerm(Scenario avallaScenario, JavaAssertionTerm javaAssertionTerm);
}
