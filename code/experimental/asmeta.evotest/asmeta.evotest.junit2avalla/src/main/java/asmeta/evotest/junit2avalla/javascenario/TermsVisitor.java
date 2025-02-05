package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

public class TermsVisitor extends VoidVisitorAdapter<Context> {

	/** Logger */
	private final Logger logger = LogManager.getLogger(TermsVisitor.class);

	/**
	 * Gets the type, name and value from a variable declaration, creates a
	 * currentJavaVariable and adds it to the variable map
	 * 
	 * @param node    the node to be visited.
	 * @param context the context containing shared data for the visitor operations
	 */
	@Override
	public void visit(VariableDeclarator node, Context context) {

		// ASM Declaration ( ASM_ATG ASM_ATG0 = new ASM_ATG() )
		if (node.getTypeAsString().endsWith(ScenarioParserUtil.ATG_CLASS_FLAG)) {
			String asmName = node.getTypeAsString();
			logger.debug("ASM DECLARATION: {}.", asmName);
			ScenarioParserUtil.buildAsmDeclaration(asmName, context);
			return;
		}

		logger.info("VARIABLE TERM: {}", node);
		context.setCurrentJavaVariable(new JavaVariableTerm());

		// set the type
		String type = node.getTypeAsString();
		logger.info("TYPE: {}", type);
		context.getCurrentJavaVariable().setType(type);

		// set the name
		String name = node.getNameAsString();
		logger.info("VARIABLE NAME: {}", name);
		context.getCurrentJavaVariable().setName(name);

		// set the value
		VariableValueVisitor variableValueVisitor = new VariableValueVisitor();
		node.accept(variableValueVisitor, context);

		// add the current variable to the variable map
		context.getVariablesMap().put(context.getCurrentJavaVariable().getName(), context.getCurrentJavaVariable());

	}

	/**
	 * 
	 * 
	 * @param node    the node to be visited.
	 * @param context the context containing shared data for the visitor operations
	 */
	@Override
	public void visit(MethodCallExpr node, Context context) {
		logger.info("METHOD CALL TERM: {}", node);

		String name = node.getNameAsString();

		// method dispatcher
		if (name.equals(ScenarioParserUtil.STEP)) {
			logger.info("STEP: {}", name);
			this.handleStepTerm(context);
		} else if (name.startsWith(ScenarioParserUtil.SET_FLAG)) {
			logger.info("SET: {}", name);
			this.handleSetTerm(node, context);
		} else if (name.startsWith(ScenarioParserUtil.GET_FLAG)) {
			logger.info("GET: {} ", name);
			// ignore the get method since without a variable assignment it is useless
		} else if (name.equals(ScenarioParserUtil.ASSERT_EQUALS)) {
			logger.info("ASSERT EQUALS: {} ", name);
			this.handleAssertEqualsTerm(node, context);
		} else if (name.equals(ScenarioParserUtil.ASSERT_TRUE) || name.equals(ScenarioParserUtil.ASSERT_FALSE)) {
			logger.info("ASSERT BOOLEAN: {}", name);
			this.handleAssertBooleanTerm(node, context);
		} else {
			logger.info("METHOD CALL TERM NAME: {}", name);
		}

	}

	private void handleStepTerm(Context context) {
		context.getScenarioManager().setStepTerm(context.getCurrentScenario());
		
		// consider the next checks
		context.setIgnoreChecks(false);
	}

	private void handleSetTerm(MethodCallExpr node, Context context) {
		context.setCurrentJavaVariable(new JavaVariableTerm());

		// set name
		context.getCurrentJavaVariable().setName(ScenarioParserUtil.buildDomainCodomain(node.getNameAsString()));

		// set value
		if (node.getArguments().size() != 1)
			throw new JUnitParseException("The argument of the set method must be 1.");
		node.getArgument(0).accept(new VariableValueVisitor(), context);

		// add term to scenario
		context.getScenarioManager().setSetTerm(context.getCurrentScenario(), context.getCurrentJavaVariable());
		
		// ignore the next checks until the next step
		context.setIgnoreChecks(true);
	}

	private void handleAssertEqualsTerm(MethodCallExpr node, Context context) {
		// only 2 or 3 arguments, otherwise stop the generation of the current scenario
		if (node.getArguments().size() <= 1 || node.getArguments().size() >= 3)
			throw new JUnitParseException("The arguments of the assertEquals method must be 2.");
		// assert equals for double can have three arguments:
		// assertEquals(double expected, double actual, double epsilon)
		
		// if true ignore the current assertion
		if(context.isIgnoreChecks()) {
			logger.info("Ignoring the current assertion: {}", node);
			return;
		}
		
		context.setCurrentJavaAssertionTerm(new JavaAssertionTerm());
		context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_EQUALS);

		// set expected
		logger.info("EXPECTED: {}", node.getArgument(0));
		context.getCurrentJavaAssertionTerm().setExpected(node.getArgument(0).toString());
		context.getCurrentJavaAssertionTerm().setPrimitive(true);
		// if it's an identifier --> set not primitive
		node.getArgument(0).accept(new VoidVisitorAdapter<Void>() {
			@Override
			public void visit(FieldAccessExpr node, Void arg) {
				logger.info("EXPECTED IDENTIFIER: {} ", node.getNameAsString());
				context.getCurrentJavaAssertionTerm().setPrimitive(false);
			}
		}, null);

		// set actual
		node.getArgument(1).accept(new AssertionActualVisitor(), context);
		
		// add term to scenario
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());

	}

	private void handleAssertBooleanTerm(MethodCallExpr node, Context context) {
		// only 1 argument, otherwise stop the generation of the current scenario
		if (node.getArguments().size() != 1)
			throw new JUnitParseException("The argument of the assertEquals method must be 1.");
		
		// if true ignore the current assertion
		if(context.isIgnoreChecks()) {
			logger.info("Ignoring the current assertion: {}", node);
			return;
		}
		
		context.setCurrentJavaAssertionTerm(new JavaAssertionTerm());

		// set type and expected
		if(node.getNameAsString().equals(ScenarioParserUtil.ASSERT_TRUE)) {
			context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_TRUE);
			context.getCurrentJavaAssertionTerm().setExpected(Boolean.toString(true));
		} else {
			context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_FALSE);
			context.getCurrentJavaAssertionTerm().setExpected(Boolean.toString(false));
		}

		// set actual
		node.getArgument(0).accept(new AssertionActualVisitor(), context);
		
		// add term to scenario
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());
	}

}
