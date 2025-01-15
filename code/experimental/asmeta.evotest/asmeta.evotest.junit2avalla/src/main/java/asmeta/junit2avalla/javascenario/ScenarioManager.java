package asmeta.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.terms.AvallaCheckTerm;
import asmeta.junit2avalla.model.terms.AvallaHeaderTerm;
import asmeta.junit2avalla.model.terms.AvallaLoadTerm;
import asmeta.junit2avalla.model.terms.AvallaSetTerm;
import asmeta.junit2avalla.model.terms.AvallaStepTerm;
import asmeta.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.junit2avalla.model.terms.JavaVariableTerm;

/**
 * The {@code ScenarioManager} class defines the methods required to manage and
 * modify Avalla scenarios. It provides a series of methods to add various types
 * of terms (header, load, set, step, and check) to a scenario.
 */
class ScenarioManager {

	/** Logger */
	private final Logger log = LogManager.getLogger(ScenarioManager.class);

	/**
	 * Default no args constructor.
	 */
	ScenarioManager() {
		// Empty constructor
	}

	/**
	 * Adds a {@link AvallaHeaderTerm} to the specified scenario.
	 *
	 * @param avallaScenario the scenario to which the header term is added.
	 * @param asmName        the name of the ASM specification.
	 * @param scenarioIndex  the ID of the scenario.
	 */
	void setHeaderTerm(Scenario avallaScenario, String asmName, int scenarioIndex) {
		AvallaHeaderTerm avallaHeaderTerm = new AvallaHeaderTerm();
		avallaHeaderTerm.setScenarioName(retrieveAsmName(asmName) + "_scenario" + scenarioIndex);
		log.debug("Set AvallaHeaderTerm_name: {} .", avallaHeaderTerm.getScenarioName());
		avallaScenario.add(avallaHeaderTerm);
	}

	/**
	 * Adds a {@link AvallaLoadTerm} to the specified scenario.
	 *
	 * @param avallaScenario the scenario to which the load term is added.
	 * @param asmName        the name of the ASM specification to be loaded.
	 */
	void setLoadTerm(Scenario avallaScenario, String asmName) {
		AvallaLoadTerm avallaLoadTerm = new AvallaLoadTerm();
		avallaLoadTerm.setLoad(retrieveAsmName(asmName));
		log.debug("Set AvallaLoadTerm_load: {} .", avallaLoadTerm.getLoad());
		avallaScenario.add(avallaLoadTerm);
	}

	/**
	 * Adds a {@link AvallaSetTerm} object to the specified scenario.
	 *
	 * @param avallaScenario   the scenario to which the set terms are added.
	 * @param javaVariableTerm the variables to set in the scenario.
	 */
	void setSetTerm(Scenario avallaScenario, JavaVariableTerm javaVariableTerm) {
		AvallaSetTerm avallaSetTerm = new AvallaSetTerm();
		avallaSetTerm.setName(retrieveSetter(javaVariableTerm.getName()));
		log.debug("Set AvallaSetTerm_name: {} .", avallaSetTerm.getName());
		avallaSetTerm.setValue(retrieveValue(javaVariableTerm));
		log.debug("Set AvallaSetTerm_value: {} .", avallaSetTerm.getValue());
		avallaScenario.add(avallaSetTerm);
	}

	/**
	 * Adds an {@link AvallaStepTerm} to the specified scenario. This term
	 * represents a step in the execution of the ASM.
	 *
	 * @param avallaScenario the scenario to which the step term is added.
	 */
	void setStepTerm(Scenario avallaScenario) {
		AvallaStepTerm avallaStepTerm = new AvallaStepTerm();
		avallaScenario.add(avallaStepTerm);
		log.debug("Set AvallaStepTerm: step .");
	}

	/**
	 * Adds an {@link AvallaCheckTerm} to the specified scenario. This term is used
	 * to check the equality of two values.
	 *
	 * @param avallaScenario    the scenario to which the check term is added.
	 * @param javaAssertionTerm a {@link JavaAssertionTerm} containing the expected
	 *                          and actual values to be checked.
	 */
	void setCheckTerm(Scenario avallaScenario, JavaAssertionTerm javaAssertionTerm) {
		AvallaCheckTerm avallaCheckTerm = new AvallaCheckTerm();
		avallaCheckTerm.setLeftTerm(retrieveExpected(javaAssertionTerm.getExpected()));
		log.debug("Set AvallaCheckTerm_leftTerm: {} .", avallaCheckTerm.getLeftTerm());
		avallaCheckTerm.setRightTerm(retrieveActual(javaAssertionTerm));
		log.debug("Set AvallaCheckTerm_rightTerm: {} .", avallaCheckTerm.getRightTerm());
		avallaScenario.add(avallaCheckTerm);
	}

	/**
	 * Retrieves the value of a {@link JavaVariableTerm}.
	 *
	 * @param javaVariable the variable whose value needs to be retrieved.
	 * @return the processed value as a string.
	 */
	private String retrieveValue(JavaVariableTerm javaVariable) {
		String value = javaVariable.getValue();
		return javaVariable.isPrimitive() ? value.replace("\"", "") : value.substring(value.lastIndexOf('.') + 1);
	}

	/**
	 * Retrieves the ASM name from the given ASM name string.
	 *
	 * @param asmName the ASM name string.
	 * @return the processed ASM name.
	 */
	private String retrieveAsmName(String asmName) {
		return asmName.substring(0, asmName.lastIndexOf("_ATG"));
	}

	/**
	 * Retrieves the actual value from a given string. If the value is not
	 * primitive, extract the last id of the identifier.
	 *
	 * @param actual the actual value string.
	 * @return the processed actual value.
	 */
	private String retrieveActual(JavaAssertionTerm javaAssertionTerm) {
		String actual = javaAssertionTerm.getActual();
		if (actual.contains("abstract_")){
			// if it's an abstract type remove the double quotes and the abstract_ flag
			actual = actual.replace("\"", "");
			actual = actual.replace("abstract_", "");
		}
		return javaAssertionTerm.isPrimitive() ? actual : actual.substring(actual.lastIndexOf(".") + 1);
	}

	/**
	 * Retrieves the expected value from a given string.
	 *
	 * @param expected the expected value string.
	 * @return the processed expected value.
	 */
	private String retrieveExpected(String expected) {
		return expected.contains(".get_") ? expected.substring(expected.lastIndexOf(".get_") + 5).replace("()", "") : expected;
	}

	/**
	 * Retrieves the setter variable name value from a given string.
	 *
	 * @param setter the setter value string.
	 * @return the processed expected value.
	 */
	private String retrieveSetter(String setter) {
		return setter.substring(setter.lastIndexOf(".set_") + 5);
	}

}
