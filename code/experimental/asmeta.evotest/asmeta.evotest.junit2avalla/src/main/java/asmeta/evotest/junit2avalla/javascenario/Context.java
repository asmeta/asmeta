package asmeta.evotest.junit2avalla.javascenario;

import java.util.Map;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
	private Scenario currenteScenario;

	/** The current Java assertion being processed. */
	private JavaAssertionTerm currentJavaAssertionTerm;

	/** {@code True} ignore the next listener event, {@code False} otherwise. */
	private boolean ignoreEvents;

	/** {@code True} ignore the next assertions, {@code False} write the checks. */
	private boolean ignoreChecks;

}
