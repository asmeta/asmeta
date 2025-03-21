package asmeta.evotest.junit2avalla.javascenario;

import java.util.HashMap;
import java.util.Map;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

/**
 * Represents the parsing context used during the parsing of a test scenario.
 */
public class Context {

	/** Map of declared variables within the current scenario. */
	private Map<String, JavaVariableTerm> variablesMap;

	/** Map of the functions getter value. */
	private Map<String, String> getterMap;

	/** The Scenario Manager manages and transforms scenario terms. */
	private ScenarioManager scenarioManager;

	/** The current scenario index. */
	private int scenarioIndex;

	/** The current Java variable being processed. */
	private JavaVariableTerm currentJavaVariable;

	/** The current scenario being processed. */
	private Scenario currentScenario;

	/** The current Java assertion being processed. */
	private JavaAssertionTerm currentJavaAssertionTerm;

	/** {@code True} ignore the next listener event, {@code False} otherwise. */
	private boolean ignoreEvents;

	/** {@code True} ignore the next assertions, {@code False} write the checks. */
	private boolean ignoreChecks;
	
    /**
     * Initializes the context by setting up the scenario manager and updating the context.
     */
	void initContext() {
		this.scenarioManager = new ScenarioManager();
		this.updateContext();
		this.scenarioIndex = 0;
	}
	
    /**
     * Updates the context for a new scenario.
     * 
     * <p>This method resets the current scenario, reinitializes the variable and getter maps,
     * and updates control flags.</p>
     */
	void updateContext() {
		this.currentScenario = new Scenario();
		this.variablesMap = new HashMap<>();
		this.getterMap = new HashMap<>();
		this.ignoreEvents = false;
		this.ignoreChecks = false;
		this.scenarioIndex ++;
	}
	
	Context() {
	}

	Map<String, JavaVariableTerm> getVariablesMap() {
		return variablesMap;
	}

	void setVariablesMap(Map<String, JavaVariableTerm> variablesMap) {
		this.variablesMap = variablesMap;
	}

	Map<String, String> getGetterMap() {
		return getterMap;
	}

	void setGetterMap(Map<String, String> getterMap) {
		this.getterMap = getterMap;
	}

	ScenarioManager getScenarioManager() {
		return scenarioManager;
	}

	void setScenarioManager(ScenarioManager scenarioManager) {
		this.scenarioManager = scenarioManager;
	}

	int getScenarioIndex() {
		return scenarioIndex;
	}

	void setScenarioIndex(int scenarioIndex) {
		this.scenarioIndex = scenarioIndex;
	}

	JavaVariableTerm getCurrentJavaVariable() {
		return currentJavaVariable;
	}

	void setCurrentJavaVariable(JavaVariableTerm currentJavaVariable) {
		this.currentJavaVariable = currentJavaVariable;
	}

	Scenario getCurrentScenario() {
		return currentScenario;
	}

	void setCurrentScenario(Scenario currentScenario) {
		this.currentScenario = currentScenario;
	}

	JavaAssertionTerm getCurrentJavaAssertionTerm() {
		return currentJavaAssertionTerm;
	}

	void setCurrentJavaAssertionTerm(JavaAssertionTerm currentJavaAssertionTerm) {
		this.currentJavaAssertionTerm = currentJavaAssertionTerm;
	}

	boolean isIgnoreEvents() {
		return ignoreEvents;
	}

	void setIgnoreEvents(boolean ignoreEvents) {
		this.ignoreEvents = ignoreEvents;
	}

	boolean isIgnoreChecks() {
		return ignoreChecks;
	}

	void setIgnoreChecks(boolean ignoreChecks) {
		this.ignoreChecks = ignoreChecks;
	}

}
