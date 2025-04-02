package asmeta.evotest.junit2avalla.javascenario;

import java.util.LinkedList;
import java.util.List;
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

	/** List of scenarios parsed and processed. */
	private final List<Scenario> scenarioList;
	
	/** Context containing shared data for the visitor operations */
	private final Context context;

	/**
	 * Constructor for the {@code JavaScenarioListener}.
	 */
	public CustomParserListener() {
		this.scenarioList = new LinkedList<>();
		this.context = new Context();
		this.context.initContext();
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
		String asmName = ctx.ASMID(0).getText();
		ScenarioParserUtil.buildAsmDeclaration(asmName, context);
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
		context.setCurrentJavaVariable(new JavaVariableTerm());
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
		context.setCurrentJavaVariable(new JavaVariableTerm());
		context.getCurrentJavaVariable().setType(ctx.ID(0).getText());
		context.getCurrentJavaVariable().setName(ctx.ID(1).getText());
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
		context.setCurrentJavaVariable(new JavaVariableTerm());
		context.getCurrentJavaVariable().setType(ctx.ID(0).getText());
		context.getCurrentJavaVariable().setName(ctx.ID(1).getText());
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
		context.getCurrentJavaVariable().setType(ctx.getText());
		context.getCurrentJavaVariable().setPrimitive(false);
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
		context.getCurrentJavaVariable().setName(ctx.getText());
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
		context.getCurrentJavaVariable().setValue(value);
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
		context.getVariablesMap().put(context.getCurrentJavaVariable().getName(), context.getCurrentJavaVariable());
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
		context.getVariablesMap().put(context.getCurrentJavaVariable().getName(), context.getCurrentJavaVariable());
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
		context.getVariablesMap().put(context.getCurrentJavaVariable().getName(), context.getCurrentJavaVariable());
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
		context.setCurrentJavaVariable(new JavaVariableTerm());
		// Set a function with Domain -> Codomain
		setName = ScenarioParserUtil.buildDomainCodomain(setName);
		// example: set_function_fromDomain_STATE1 -> set_function(STATE1)
		context.getCurrentJavaVariable().setName(setName);
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
			ScenarioParserUtil.setStringVariableValue(value, context.getCurrentJavaVariable());
		} else if (ctx.number() != null) {
			// if its a number (int, double, natural)
			logger.debug("Setting the number value : {} .", ctx.getText());
			ScenarioParserUtil.setIntegerVariableValue(ctx.getText(), context.getCurrentJavaVariable());
		} else if (ctx.Boolean() != null || ctx.CHARACTER() != null) {
			// if its a primitive type (char or boolean)
			String value = ctx.getText();
			logger.debug("Setting the primitive type value : {} .", value);
			ScenarioParserUtil.setPrimitiveVariable(value, context.getCurrentJavaVariable());
		} else if (ctx.Identifier() != null) {
			// not primitive type
			String identifier = ctx.getText();
			logger.debug("Setting the identifier : {} .", identifier);
			ScenarioParserUtil.setNotPrimitiveVariable(identifier, context.getCurrentJavaVariable());
		} else {
			// if its a variable
			logger.debug("Setting the variable value : {} .", ctx.getText());
			logger.debug("Searching the value in the variables dictionary.");
			if (!context.getVariablesMap().containsKey(ctx.getText())) {
				// Stop the generation of the current scenario
				context.setIgnoreEvents(true);
				logger.error("Unrecognized value {}.", ctx.getText());
				return;
			}
			setVariableValue(ctx);
		}
	}

	/**
	 * Sets the value of a variable that is present in the variable list.
	 * 
	 * @param ctx the parse tree context.
	 */
	private void setVariableValue(SetVariableValueContext ctx) {
		JavaVariableTerm javaVariableTerm = context.getVariablesMap().get(ctx.getText());
		if (context.getGetterMap().containsKey(javaVariableTerm.getValue())) {
			// search in the getters map
			String value = context.getGetterMap().get(javaVariableTerm.getValue());
			logger.debug("Found a getter value in the getterMap: {} .", value);
			ScenarioParserUtil.setPrimitiveVariable(value, context.getCurrentJavaVariable());
		} else if (javaVariableTerm.getType().equalsIgnoreCase(Double.class.getSimpleName())) {
			// set it primitive to not cut it off after the dot
			ScenarioParserUtil.setPrimitiveVariable(javaVariableTerm.getValue(), context.getCurrentJavaVariable());
		} else if (javaVariableTerm.getType().equalsIgnoreCase(String.class.getSimpleName())) {
			// if its a string
			ScenarioParserUtil.setStringVariableValue(javaVariableTerm.getValue(), context.getCurrentJavaVariable());
		} else if (javaVariableTerm.getType().equalsIgnoreCase(Integer.class.getSimpleName())
				|| javaVariableTerm.getType().equalsIgnoreCase(int.class.getSimpleName())) {
			// if its an integer
			ScenarioParserUtil.setIntegerVariableValue(javaVariableTerm.getValue(), context.getCurrentJavaVariable());
			// primitive field managed by the setStringVariableValue() function
		} else {
			// not primitive
			ScenarioParserUtil.setNotPrimitiveVariable(javaVariableTerm.getValue(), context.getCurrentJavaVariable());
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
		context.getScenarioManager().setSetTerm(context.getCurrentScenario(), context.getCurrentJavaVariable());
		context.setIgnoreChecks(true);
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
		context.getScenarioManager().setStepTerm(context.getCurrentScenario());
		context.setIgnoreChecks(false);
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
		context.setCurrentJavaAssertionTerm(new JavaAssertionTerm());
		context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_EQUALS);
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
		context.getCurrentJavaAssertionTerm().setExpected(ctx.getText());
		// if it's an identifier --> not primitive
		context.getCurrentJavaAssertionTerm().setPrimitive(ctx.Identifier() == null);
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
			if (!context.getVariablesMap().containsKey(ctx.getText())) {
				// Stop the generation of the current scenario
				context.setIgnoreEvents(true);
				logger.error("Unrecognized value {}.", ctx.getText());
				return;
			}
			String actualValue = context.getVariablesMap().get(ctx.getText()).getValue();
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(actualValue, context.getCurrentJavaAssertionTerm());
			logger.debug("Actual id: {}", actualValue);
			// add the value to the getters dictionary
			if (actualValue.contains(ScenarioParserUtil.GET_FLAG)) {
				context.getGetterMap().put(actualValue, context.getCurrentJavaAssertionTerm().getExpected());
				logger.debug("Saving the getter {} : {}", actualValue, context.getCurrentJavaAssertionTerm().getExpected());
			}
		} else {
			String getter = ctx.getText();
			logger.debug("parsing Getter: {} .", getter);
			// build a getter function with Domain -> Codomain
			getter = ScenarioParserUtil.buildDomainCodomain(getter);
			// example: get_function_fromDomain_STATE1 -> get_function(STATE1)
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(getter, context.getCurrentJavaAssertionTerm());
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
		if (context.isIgnoreChecks()) {
			logger.debug("Ignoring the start_test_scenario_assertEquals: {} .", ctx.getText());
			return;
		}
		logger.debug("Exiting start_test_scenario_assertEquals: {} . Setting AvallaCheckTerm:", ctx.getText());
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Initializes the assertion term for {@code assertBoolean} checks.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterAssertBoolean(AssertBooleanContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		logger.debug("Entering start_test_scenario_assertBoolean: {} .", ctx.getText());
		context.setCurrentJavaAssertionTerm(new JavaAssertionTerm());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Sets the assertion type and expected field in the assertion term.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterBooleanAssertion(BooleanAssertionContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		if (ctx.ASSERT_TRUE() != null) {
			logger.debug("parsing ASSERT_TRUE: {} .", ctx.getText());
			context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_TRUE);
			context.getCurrentJavaAssertionTerm().setExpected(Boolean.toString(true));
		} else {
			logger.debug("parsing ASSERT_FALSE: {} .", ctx.getText());
			context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_FALSE);
			context.getCurrentJavaAssertionTerm().setExpected(Boolean.toString(false));
		}
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
			if (!context.getVariablesMap().containsKey(actual)) {
				// Stop the generation of the current scenario
				context.setIgnoreEvents(true);
				logger.error("Unrecognized value {} in {}.", actual, ctx.getText());
				return;
			}
			JavaVariableTerm javaVariableTerm = context.getVariablesMap().get(actual);
			actual = javaVariableTerm.getValue();
		}
		context.getCurrentJavaAssertionTerm().setActual(actual);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Processes the assertion term for {@code assertBoolean} and adds it to the
	 * scenario.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitAssertBoolean(AssertBooleanContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		if (context.isIgnoreChecks()) {
			logger.debug("Ignoring the start_test_scenario_assertBoolean: {} .", ctx.getText());
			return;
		}
		logger.debug("Exiting start_test_scenario_assertBoolean: {} . Setting AvallaCheckTerm:", ctx.getText());
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Set a variable term declared with an instance constructor.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterInstanceDeclaration(InstanceDeclarationContext ctx) {
		if (ignoreListenerEvent(ctx.getText()))
			return;
		// example: Boolean boolean2 = new Boolean(boolean1)
		logger.debug("Entering start_test_scenario_instanceDeclaration: {} .", ctx.getText());
		String name = ctx.ID(1).getText();
		String val = ctx.ID(3).getText();
		logger.debug("new variable, name : {} , value {} ", name, val);
		if (!context.getVariablesMap().containsKey(val)) {
			// Stop the generation of the current scenario
			context.setIgnoreEvents(true);
			logger.error("Unrecognized value {} in {}.", val, ctx.getText());
			return;
		}
		// get the value of the current variable and add to the map
		JavaVariableTerm javaVariableTerm = context.getVariablesMap().get(val);
		JavaVariableTerm newJavaVariableTerm = new JavaVariableTerm();
		newJavaVariableTerm.setName(name);
		newJavaVariableTerm.setType(javaVariableTerm.getType());
		newJavaVariableTerm.setValue(javaVariableTerm.getValue());
		context.getVariablesMap().put(name, newJavaVariableTerm);
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
		context.setIgnoreEvents(true); // ignore the next listener events
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
		this.scenarioList.add(context.getCurrentScenario());
		logger.info("Scenario {} parsed correctly and added to the list.", context.getScenarioIndex());
		context.updateContext();
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
		if (context.isIgnoreEvents()) {
			logger.debug("Ignoring the listener event for rule: {}", ruleName);
			return true;
		}
		return false;
	}

}
