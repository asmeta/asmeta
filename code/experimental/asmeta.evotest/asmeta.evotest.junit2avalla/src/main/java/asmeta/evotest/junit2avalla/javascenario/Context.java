package asmeta.evotest.junit2avalla.javascenario;

import java.util.HashMap;
import java.util.Map;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
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
	 * Initialize the context.
	 */
	void initContext() {
		this.scenarioManager = new ScenarioManager();
		this.updateContext();
		this.scenarioIndex = 0;
	}
	
	/**
	 * Update the context for a new scenario.
	 */
	void updateContext() {
		this.currentScenario = new Scenario();
		this.variablesMap = new HashMap<>();
		this.getterMap = new HashMap<>();
		this.ignoreEvents = false;
		this.ignoreChecks = false;
		this.scenarioIndex ++;
	}

}
