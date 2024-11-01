package asmeta.junit2avalla.javascenario;

import asmeta.junit2avalla.model.Scenario;
import asmeta.junit2avalla.model.terms.JavaArgumentTerm;
import asmeta.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.junit2avalla.model.terms.JavaVariableTerm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import asmeta.junit2avalla.antlr.JavaScenarioBaseListener;
import asmeta.junit2avalla.antlr.JavaScenarioParser.ActualContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.AsmDeclarationContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.AssertEqualsContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.ExpectedContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.ScenarioContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.SetFunctionContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.SetVariableValueContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.StartContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.StepFunctionContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.TrycatchblockContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.VariableDeclarationContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.VariableNameContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.VariableTypeContext;
import asmeta.junit2avalla.antlr.JavaScenarioParser.VariableValueContext;

public class JavaScenarioListener extends JavaScenarioBaseListener {

	/** Logger */
	private static final Logger log = LogManager.getLogger(JavaScenarioListener.class);

	/**
	 * List of scenarios parsed and processed.
	 */
	private final List<Scenario> scenarioList;

	/**
	 * Map of declared variables within the current scenario.
	 */
	private Map<String, JavaVariableTerm> variablesList;

	/**
	 * The Scenario Manager interface to manage and transform scenario terms.
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
	 * {@code True} ignore the checks, {@code False} write the checks.
	 */
	private boolean ignoreChecks;

	/**
	 * Constructor for the {@code JavaScenarioListener}.
	 *
	 * @param stepFunctionArgsList List of {@link JavaArgumentTerm} representing
	 *                             arguments for the step function.
	 */
	public JavaScenarioListener() {
		this.scenarioManager = new ScenarioManagerImpl();
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
		log.info("Parsing the Java Scenario...");
		log.debug("Entering start: {} .", ctx.getText());
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
		log.debug("Entering start_test_scenario: {} .", ctx.getText());
		log.info("Found a scenario, creating a new Scenario Object.");
		this.variablesList = new HashMap<>();
		this.currenteScenario = new Scenario();
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
		log.debug("Entering start_test_scenario_asmDeclaration: {} .", ctx.getText());
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
		log.debug("Entering start_test_scenario_variableDeclaration: {} .", ctx.getText());
		this.currentJavaVariable = new JavaVariableTerm();
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
		log.debug("Entering start_test_scenario_variableDeclaration_variableType: {} .", ctx.getText());
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
		log.debug("Entering start_test_scenario_variableDeclaration_variableName: {} .", ctx.getText());
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
		log.debug("Entering start_test_scenario_variableDeclaration_variableValue: {} .", ctx.getText());
		this.currentJavaVariable.setValue(ctx.getText());
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
		log.debug("Exiting start_test_scenario_variableDeclaration: {} .", ctx.getText());
		this.variablesList.put(this.currentJavaVariable.getName(), this.currentJavaVariable);
	}
	
	@Override
	public void enterSetFunction(SetFunctionContext ctx) {
		log.debug("Entering start_test_scenario_setFunction: {} .", ctx.getText());
		this.currentJavaVariable = new JavaVariableTerm();
		currentJavaVariable.setName(ctx.SetFunc().getText());
	}


	@Override
	public void enterSetVariableValue(SetVariableValueContext ctx) {
		log.debug("Entering start_test_scenario_setFunction_setVariableValue: {} .", ctx.getText());
		if(!ctx.INT().isEmpty() || ctx.STRING() != null) {
			log.debug("Setting the primitive type value : {} .", ctx.getText());
			this.currentJavaVariable.setValue(ctx.getText());
			this.currentJavaVariable.setPrimitive(true);
		} else {
			log.debug("Setting the variable value : {} .", ctx.getText());
			log.debug("Searching the value in the vaiables dictonay : {} .", this.variablesList.get(ctx.getText()));
			this.currentJavaVariable.setValue(this.variablesList.get(ctx.getText()).getValue());
			this.currentJavaVariable.setPrimitive(false);
		}
	}
	
	@Override
	public void exitSetFunction(SetFunctionContext ctx) {
		log.debug("Exiting start_test_scenario_setFunction: {} .", ctx.getText());
		this.scenarioManager.setSetTerm(this.currenteScenario, this.currentJavaVariable);
		this.ignoreChecks = true;
	}


	/**
	 * {@inheritDoc}
	 * <p>
	 * Initializes the current variables list and resets the argument index.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void enterStepFunction(StepFunctionContext ctx) {
		log.debug("Entering start_test_scenario_stepFunction: {} .", ctx.getText());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Processes a step function, setting the necessary terms in the scenario.
	 * </p>
	 *
	 * @param ctx the parse tree context.
	 */
	@Override
	public void exitStepFunction(StepFunctionContext ctx) {
		log.debug("Exiting start_test_scenario_stepFunction: {} .", ctx.getText());
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
		if(this.ignoreChecks) {
			log.debug("Ignoring the start_test_scenario_assertEquals: {} .", ctx.getText());
			return;
		}
		log.debug("Entering start_test_scenario_assertEquals: {} .", ctx.getText());
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
	public void enterActual(ActualContext ctx) {
		if(this.ignoreChecks) {
			log.debug("Ignoring the start_test_scenario_assertEquals_actual: {} .", ctx.getText());
			return;
		}
		log.debug("Entering start_test_scenario_assertEquals_actual: {} .", ctx.getText());
		this.currentJavaAssertionTerm.setActual(ctx.getText());
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
	public void enterExpected(ExpectedContext ctx) {
		if(this.ignoreChecks) {
			log.debug("Ignoring the start_test_scenario_assertEquals_expected: {} .", ctx.getText());
			return;
		}
		log.debug("Entering start_test_scenario_assertEquals_expected: {} .", ctx.getText());
		this.currentJavaAssertionTerm.setExpected(ctx.getText());
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
		if(this.ignoreChecks) {
			log.debug("Ignoring the start_test_scenario_assertEquals: {} .", ctx.getText());
			return;
		}
		log.debug("Exiting start_test_scenario_assertEquals: {} . Setting AvallaCheckTerm:", ctx.getText());
		this.scenarioManager.setCheckTerm(this.currenteScenario, this.currentJavaAssertionTerm);
	}
	
	

	@Override
	public void enterTrycatchblock(TrycatchblockContext ctx) {
		log.debug("Entering start_test_scenario_trycatchblock: {} .", ctx.getText());
		// TODO: stop the parsing of the current scenario
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
		log.info("Scenario processing completed. Scenario added to the list.");
	}

	/**
	 * Returns the list of scenarios that have been processed by this listener.
	 *
	 * @return a list of {@link Scenario} objects.
	 */
	public List<Scenario> getScenarioList() {
		return scenarioList;
	}
}
