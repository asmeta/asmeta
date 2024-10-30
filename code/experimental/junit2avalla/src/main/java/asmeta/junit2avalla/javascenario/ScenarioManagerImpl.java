package asmeta.junit2avalla.javascenario;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.junit2avalla.model.terms.JavaVariableTerm;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code ScenarioManagerImpl} class implements the {@link ScenarioManager} interface. It
 * manages the addition of various terms (header, load, set, step, and check) to the Avalla
 * scenario.
 */
public class ScenarioManagerImpl implements ScenarioManager {

  private static final Logger log = LogManager.getLogger(ScenarioManagerImpl.class);

  /**
   * Default constructor for the {@code ScenarioManagerImpl} class.
   */
  public ScenarioManagerImpl() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeaderTerm(Scenario avallaScenario, String asmName, int scenarioIndex) {
    AvallaHeaderTerm avallaHeaderTerm = new AvallaHeaderTerm();
    avallaHeaderTerm.setScenarioName(retrieveAsmName(asmName) + "_scenario" + scenarioIndex);
    log.debug("Set AvallaHeaderTerm_name: {} .", avallaHeaderTerm.getScenarioName());
    avallaScenario.add(avallaHeaderTerm);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLoadTerm(Scenario avallaScenario, String asmName) {
    AvallaLoadTerm avallaLoadTerm = new AvallaLoadTerm();
    avallaLoadTerm.setLoad(retrieveAsmName(asmName));
    log.debug("Set AvallaLoadTerm_load: {} .", avallaLoadTerm.getLoad());
    avallaScenario.add(avallaLoadTerm);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSetTerm(Scenario avallaScenario, List<JavaVariableTerm> variablesList) {
    for (JavaVariableTerm javaVariable : variablesList) {
      AvallaSetTerm avallaSetTerm = new AvallaSetTerm();
      avallaSetTerm.setName(javaVariable.getName());
      log.debug("Set AvallaSetTerm_name: {} .", avallaSetTerm.getName());
      avallaSetTerm.setValue(retrieveValue(javaVariable));
      log.debug("Set AvallaSetTerm_value: {} .", avallaSetTerm.getValue());
      avallaScenario.add(avallaSetTerm);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStepTerm(Scenario avallaScenario) {
    AvallaStepTerm avallaStepTerm = new AvallaStepTerm();
    avallaScenario.add(avallaStepTerm);
    log.debug("Set AvallaStepTerm: step .");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCheckTerm(Scenario avallaScenario, JavaAssertionTerm javaAssertionTerm) {
    AvallaCheckTerm avallaCheckTerm = new AvallaCheckTerm();
    avallaCheckTerm.setLeftTerm(retrieveExpected(javaAssertionTerm.getExpected()));
    log.debug("Set AvallaCheckTerm_leftTerm: {} .", avallaCheckTerm.getLeftTerm());
    avallaCheckTerm.setRightTerm(retrieveActual(javaAssertionTerm.getActual()));
    log.debug("Set AvallaCheckTerm_rightTerm: {} .", avallaCheckTerm.getRightTerm());
    avallaScenario.add(avallaCheckTerm);
  }

  /**
   * Retrieves the value of a {@link JavaVariableTerm}, adjusting its value if it's a non-primitive
   * type.
   *
   * @param javaVariable the variable whose value needs to be retrieved.
   * @return the processed value as a string.
   */
  private String retrieveValue(JavaVariableTerm javaVariable) {
    String value = javaVariable.getValue();
    return javaVariable.isPrimitive()
        ? value.replaceAll("\"", "")
        : value.substring(value.lastIndexOf('.') + 1);
  }

  /**
   * Retrieves the ASM name from the given ASM name string.
   *
   * @param asmName the ASM name string.
   * @return the processed ASM name.
   */
  private String retrieveAsmName(String asmName) {
    return asmName.substring(0, asmName.lastIndexOf("_ASM"));
  }

  /**
   * Retrieves the actual value from a given string, adjusting its format if necessary.
   *
   * @param actual the actual value string.
   * @return the processed actual value.
   */
  private String retrieveActual(String actual) {
    return actual.substring(actual.lastIndexOf(".") + 1);
  }

  /**
   * Retrieves the expected value from a given string, adjusting its format if necessary.
   *
   * @param expected the expected value string.
   * @return the processed expected value.
   */
  private String retrieveExpected(String expected) {
    return expected.substring(
        expected.lastIndexOf(".get_") + 5).replaceAll("\\(\\)", "");
  }

}
