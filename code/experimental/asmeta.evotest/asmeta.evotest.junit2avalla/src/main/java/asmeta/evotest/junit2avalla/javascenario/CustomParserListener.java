package asmeta.evotest.junit2avalla.javascenario;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import asmeta.evotest.junit2avalla.model.Scenario;
import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioBaseListener;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.ActualContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.AsmDeclarationContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.AssertBooleanContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.AssertEqualsContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.BooleanActualContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.BooleanAssertionContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.ConstructorDeclarationContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.ExpectedContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.InstanceDeclarationContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.ScenarioContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.SetFunctionContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.SetVariableValueContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.StartContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.StepFunctionContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.TrycatchblockContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.ValueOfDeclarationContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.VariableDeclarationContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.VariableNameContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.VariableTypeContext;
import asmeta.evotest.junit2avalla.antlr.JavaScenarioParser.VariableValueContext;

/**
 * Parse the Junit class into a list of Scenario object (Queue of AvallaTerms).
 */
public class CustomParserListener extends JavaScenarioBaseListener {

	/** Logger */
	private final Logger logger = LogManager.getLogger(CustomParserListener.class);

	/**
	 * List of scenarios parsed and processed.
	 */
	private final List<Scenario> scenarioList;

	/**
	 * Map of declared variables within the current scenario.
	 */
	private Map<String, JavaVariableTerm> variablesMap;

	/**
	 * Map of the functions getter value.
	 */
	private Map<String, String> getterMap;

	/**
	 * The Scenario Manager manages and transforms scenario terms.
	 */
	private final ScenarioManager scenarioManager;

	/**
	 * The current scenario index.
	 */
	private int scenarioIndex;

	/**
	 * The current Java variable being processed.
	 */
	private JavaVariableTerm currentJavaVariable;

	/**
	 * The current scenario being processed.
	 */
	private Scenario currenteScenario;

	/**
	 * The current Java assertion being processed.
	 */
	private JavaAssertionTerm currentJavaAssertionTerm;

	/**
	 * {@code True} ignore the next listener event, {@code False} otherwise.
	 */
	private boolean ignoreEvents;

	/**
	 * {@code True} ignore the next assertions, {@code False} write the checks.
	 */
	private boolean ignoreChecks;

	/**
	 * Constructor for the {@code JavaScenarioListener}.
	 *
	 */
	public CustomParserListener() {
		this.scenarioManager = new ScenarioManager();
		this.scenarioList = new LinkedList<>();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Initializes the scenario index to zero.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterStart(StartContext ctx) {
		logger.debug("Parsing the Java Scenario...");
		logger.debug("Entering start: {} .", ctx.getText());
		this.scenarioIndex = 0;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Initializes the variable map and creates a new scenario object.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterScenario(ScenarioContext ctx) {
		logger.debug("Entering start_test_scenario: {} .", ctx.getText());
		logger.debug("Found a scenario, creating a new Scenario Object.");
		this.variablesMap = new HashMap<>();
		this.getterMap = new HashMap<>();
		this.currenteScenario = new Scenario();
		this.ignoreEvents = false;
		this.ignoreChecks = false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Processes the ASM declaration and adds header and load terms to the current
	 * scenario.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterAsmDeclaration(AsmDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_asmDeclaration: {} .", ctx.getText());
		String text = ctx.ASMID(0).getText();
		String asmName = text.substring(0, 1).toUpperCase().concat(text.substring(1));
		this.scenarioManager.setHeaderTerm(this.currenteScenario, asmName, this.scenarioIndex);
		this.scenarioManager.setLoadTerm(this.currenteScenario, asmName);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Creates a new empty {@link JavaVariableTerm} for the variable declaration.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterVariableDeclaration(VariableDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_variableDeclaration: {} .", ctx.getText());
		this.currentJavaVariable = new JavaVariableTerm();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Creates a new {@link JavaVariableTerm} with name and type for the declaration
	 * with valueOf static method.
	 * </p>
	 */
	@Override
	public void enterValueOfDeclaration(ValueOfDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_valueOfDeclaration: {} .", ctx.getText());
		this.currentJavaVariable = new JavaVariableTerm();
		currentJavaVariable.setType(ctx.ID(0).getText());
		currentJavaVariable.setName(ctx.ID(1).getText());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Creates a new {@link JavaVariableTerm} with name and type.
	 * </p>
	 */
	@Override
	public void enterConstructorDeclaration(ConstructorDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_constructorDeclaration: {} .", ctx.getText());
		this.currentJavaVariable = new JavaVariableTerm();
		currentJavaVariable.setType(ctx.ID(0).getText());
		currentJavaVariable.setName(ctx.ID(1).getText());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the type of the current variable.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterVariableType(VariableTypeContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_variableDeclaration_variableType: {} .", ctx.getText());
		this.currentJavaVariable.setType(ctx.getText());
		this.currentJavaVariable.setPrimitive(false);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the name of the current variable.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterVariableName(VariableNameContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_variableDeclaration_variableName: {} .", ctx.getText());
		this.currentJavaVariable.setName(ctx.getText());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the value of the current variable.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterVariableValue(VariableValueContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		String value = ctx.getText();
		logger.debug("Entering start_test_scenario_variableDeclaration_variableValue: {} .", value);
		// Set a function name with Domain -> Codomain
		value = ScenarioParserUtil.buildDomainCodomain(value);
		// example: get_function_fromDomain_STATE1 -> get_function(STATE1)
		this.currentJavaVariable.setValue(value);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Adds the current variable to the variables map.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitVariableDeclaration(VariableDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Exiting start_test_scenario_variableDeclaration: {} .", ctx.getText());
		this.variablesMap.put(this.currentJavaVariable.getName(), this.currentJavaVariable);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Adds the current variable to the variables map.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitValueOfDeclaration(ValueOfDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Exiting start_test_scenario_valueOfDeclaration: {} .", ctx.getText());
		this.variablesMap.put(this.currentJavaVariable.getName(), this.currentJavaVariable);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Adds the current variable to the variables map.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitConstructorDeclaration(ConstructorDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Exiting start_test_scenario_constructorDeclaration: {} .", ctx.getText());
		this.variablesMap.put(this.currentJavaVariable.getName(), this.currentJavaVariable);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Create a new Java variable term and set its name.
	 * </p>
	 * 
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterSetFunction(SetFunctionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		String setName = ctx.SetFunc().getText();
		logger.debug("Entering start_test_scenario_setFunction: {} .", setName);
		this.currentJavaVariable = new JavaVariableTerm();
		// Set a function with Domain -> Codomain
		setName = ScenarioParserUtil.buildDomainCodomain(setName);
		// example: set_function_fromDomain_STATE1 -> set_function(STATE1)
		currentJavaVariable.setName(setName);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Check if the variable is primitive, in this case set its value directly,
	 * otherwise look for it in the variablesList structure and assign it to it
	 * </p>
	 * 
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterSetVariableValue(SetVariableValueContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_setFunction_setVariableValue: {} .", ctx.getText());
		if (ctx.STRING() != null) {
			// String type
			String value = ctx.getText();
			logger.debug("Setting the primitive String value : {} .", value);
			ScenarioParserUtil.setStringVariableValue(value, this.currentJavaVariable);
		} else if (ctx.number() != null) {
			// if its a number (int, double, natural)
			logger.debug("Setting the number value : {} .", ctx.getText());
			ScenarioParserUtil.setIntegerVariableValue(ctx.getText(), this.currentJavaVariable);
		} else if (ctx.Boolean() != null || ctx.CHARACTER() != null) {
			// if its a primitive type (char or boolean)
			String value = ctx.getText();
			logger.debug("Setting the primitive type value : {} .", value);
			ScenarioParserUtil.setPrimitiveVariable(value, this.currentJavaVariable);
		} else if (ctx.Identifier() != null) {
			// not primitive type
			String identifier = ctx.getText();
			logger.debug("Setting the identifier : {} .", identifier);
			ScenarioParserUtil.setNotPrimitiveVariable(identifier, this.currentJavaVariable);
		} else {
			// if its a variable
			logger.debug("Setting the variable value : {} .", ctx.getText());
			logger.debug("Searching the value in the variables dictionary.");
			if (!this.variablesMap.containsKey(ctx.getText())) {
				// Stop the generation of the current scenario
				this.ignoreEvents = true;
				logger.error("Unrecognized value {}.", ctx.getText());
				return;
			}
			JavaVariableTerm javaVariableTerm = this.variablesMap.get(ctx.getText());
			if (this.getterMap.containsKey(javaVariableTerm.getValue())) {
				// search in the getters map
				String value = getterMap.get(javaVariableTerm.getValue());
				logger.debug("Found a getter value in the getterMap: {} .", value);
				this.currentJavaVariable.setValue(value);
				this.currentJavaVariable.setPrimitive(true);
			} else if (javaVariableTerm.getType().equalsIgnoreCase("double")) {
				// set it primitive to not cut it off after the dot
				this.currentJavaVariable.setValue(javaVariableTerm.getValue());
				this.currentJavaVariable.setPrimitive(true);
			} else if (javaVariableTerm.getType().equalsIgnoreCase("String")) {
				// if its a string
				ScenarioParserUtil.setStringVariableValue(javaVariableTerm.getValue(), this.currentJavaVariable);
				// primitive field managed by the setStringVariableValue() function
			} else if (javaVariableTerm.getType().equalsIgnoreCase("Integer")
					|| javaVariableTerm.getType().equalsIgnoreCase("int")) {
				// if its an integer
				ScenarioParserUtil.setIntegerVariableValue(javaVariableTerm.getValue(), this.currentJavaVariable);
				// primitive field managed by the setStringVariableValue() function
			} else {
				this.currentJavaVariable.setValue(javaVariableTerm.getValue());
				this.currentJavaVariable.setPrimitive(false);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Add the set term to the scenario and ignore the following assertions up to
	 * the step function.
	 * </p>
	 * 
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitSetFunction(SetFunctionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Exiting start_test_scenario_setFunction: {} .", ctx.getText());
		this.scenarioManager.setSetTerm(this.currenteScenario, this.currentJavaVariable);
		this.ignoreChecks = true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Enter the step function.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterStepFunction(StepFunctionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_stepFunction: {} .", ctx.getText());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Add the step term to the scenario and consider the following assertions.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitStepFunction(StepFunctionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Exiting start_test_scenario_stepFunction: {} .", ctx.getText());
		this.scenarioManager.setStepTerm(this.currenteScenario);
		this.ignoreChecks = false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Initializes the assertion term for {@code assertEquals} checks.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterAssertEquals(AssertEqualsContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_assertEquals: {} .", ctx.getText());
		this.currentJavaAssertionTerm = new JavaAssertionTerm();
		this.currentJavaAssertionTerm.setType("AssertEquals");
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the actual value in the assertion term.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterExpected(ExpectedContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_assertEquals_expected: {} .", ctx.getText());
		this.currentJavaAssertionTerm.setExpected(ctx.getText());
		// if it's an identifier --> not primitive
		this.currentJavaAssertionTerm.setPrimitive(ctx.Identifier() == null);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the expected value in the assertion term.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterActual(ActualContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_assertEquals_actual: {} .", ctx.getText());
		if (ctx.ID() != null) {
			logger.debug("parsing ID: {} .", ctx.getText());
			logger.debug("Search the term {} in the variablesMap", ctx.getText());
			if (!this.variablesMap.containsKey(ctx.getText())) {
				// Stop the generation of the current scenario
				this.ignoreEvents = true;
				logger.error("Unrecognized value {}.", ctx.getText());
				return;
			}
			String actualValue = this.variablesMap.get(ctx.getText()).getValue();
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(actualValue, this.currentJavaAssertionTerm);
			logger.debug("Actual id: {}", actualValue);
			// add the value to the getters dictionary
			if (actualValue.contains(ScenarioParserUtil.GET_FLAG)) {
				this.getterMap.put(actualValue, this.currentJavaAssertionTerm.getExpected());
				logger.debug("Saving the getter {} : {}", actualValue, this.currentJavaAssertionTerm.getExpected());
			}
		} else {
			String getter = ctx.getText();
			logger.debug("parsing Getter: {} .", getter);
			// build a getter function with Domain -> Codomain
			getter = ScenarioParserUtil.buildDomainCodomain(getter);
			// example: get_function_fromDomain_STATE1 -> get_function(STATE1)
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(getter, this.currentJavaAssertionTerm);
		}

	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Processes the assertion term for {@code assertEquals} and adds it to the
	 * scenario.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitAssertEquals(AssertEqualsContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		if (this.ignoreChecks) {
			logger.debug("Ignoring the start_test_scenario_assertEquals: {} .", ctx.getText());
			this.currentJavaAssertionTerm = null;
			return;
		}
		logger.debug("Exiting start_test_scenario_assertEquals: {} . Setting AvallaCheckTerm:", ctx.getText());
		this.scenarioManager.setCheckTerm(this.currenteScenario, this.currentJavaAssertionTerm);
	}

	@Override
	public void enterAssertBoolean(AssertBooleanContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_assertBoolean: {} .", ctx.getText());
		this.currentJavaAssertionTerm = new JavaAssertionTerm();
	}

	@Override
	public void enterBooleanAssertion(BooleanAssertionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		if (ctx.ASSERT_TRUE() != null) {
			logger.debug("parsing ASSERT_TRUE: {} .", ctx.getText());
			this.currentJavaAssertionTerm.setType(ScenarioParserUtil.ASSERT_TRUE);
			this.currentJavaAssertionTerm.setExpected(Boolean.toString(true));
		} else {
			logger.debug("parsing ASSERT_FALSE: {} .", ctx.getText());
			this.currentJavaAssertionTerm.setType(ScenarioParserUtil.ASSERT_FALSE);
			this.currentJavaAssertionTerm.setExpected(Boolean.toString(false));
		}
	}

	@Override
	public void enterBooleanActual(BooleanActualContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		String actual = ctx.getText();
		logger.debug("parsing the expected: {} .", actual);
		if (ctx.Getter() != null) {
			// Set a function with Domain -> Codomain
			actual = ScenarioParserUtil.buildDomainCodomain(actual);
			// example: get_function_fromDomain_STATE1 -> get_function(STATE1)
		} else {
			// search the ID in the dictionary
			logger.debug("Search the term {} in the variablesMap", actual);
			if (!this.variablesMap.containsKey(actual)) {
				// Stop the generation of the current scenario
				this.ignoreEvents = true;
				logger.error("Unrecognized value {} in {}.", actual, ctx.getText());
				return;
			}
			JavaVariableTerm javaVariableTerm = this.variablesMap.get(actual);
			actual = javaVariableTerm.getValue();
		}
		this.currentJavaAssertionTerm.setActual(actual);
	}

	@Override
	public void exitAssertBoolean(AssertBooleanContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		if (this.ignoreChecks) {
			logger.debug("Ignoring the start_test_scenario_assertBoolean: {} .", ctx.getText());
			this.currentJavaAssertionTerm = null;
			return;
		}
		logger.debug("Exiting start_test_scenario_assertBoolean: {} . Setting AvallaCheckTerm:", ctx.getText());
		this.scenarioManager.setCheckTerm(this.currenteScenario, this.currentJavaAssertionTerm);
	}

	@Override
	public void enterInstanceDeclaration(InstanceDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		// example: Boolean boolean2 = new Boolean(boolean1)
		logger.debug("Entering start_test_scenario_instanceDeclaration: {} .", ctx.getText());
		String name = ctx.ID(1).getText();
		String val = ctx.ID(3).getText();
		logger.debug("new variable, name : {} , value {} ", name, val);
		if (!this.variablesMap.containsKey(val)) {
			// Stop the generation of the current scenario
			this.ignoreEvents = true;
			logger.error("Unrecognized value {} in {}.", val, ctx.getText());
			return;
		}
		// get the value of the current variable and add to the map
		JavaVariableTerm javaVariableTerm = this.variablesMap.get(val);
		JavaVariableTerm newJavaVariableTerm = new JavaVariableTerm();
		newJavaVariableTerm.setName(name);
		newJavaVariableTerm.setType(javaVariableTerm.getType());
		newJavaVariableTerm.setValue(javaVariableTerm.getValue());
		this.variablesMap.put(name, newJavaVariableTerm);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Enter a try-catch block, ignore it and stop the generation of the current
	 * scenario.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterTrycatchblock(TrycatchblockContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_trycatchblock: {} .", ctx.getText());
		this.ignoreEvents = true; // ignore the next listener events
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Adds the completed scenario to the scenario list.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitScenario(ScenarioContext ctx) {
		this.scenarioList.add(this.currenteScenario);
		this.scenarioIndex += 1;
		logger.info("Scenario processing completed. Scenario added to the list.");
	}

	/**
	 * Returns the list of scenarios that have been processed by this listener.
	 *
	 * @return a list of {@link Scenario} objects.
	 */
	public List<Scenario> getScenarioList() {
		return scenarioList;
	}

	/**
	 * Returns the status of the ignoreEvents flag.
	 * 
	 * @param ctx the parse tree context.
	 * @return {@code True} if the event must be ignored and the execution stopped,
	 *         {@code False} otherwise
	 */
	private boolean ignoreListenerEvent(String ruleName) {
		if (ignoreEvents) {
			logger.debug("Ignoring the listener event for rule: {}", ruleName);
			return true;
		}
		return false;
	}

}
