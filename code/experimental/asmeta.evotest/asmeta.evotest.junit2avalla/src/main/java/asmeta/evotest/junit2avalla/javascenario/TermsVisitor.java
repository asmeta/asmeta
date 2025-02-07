package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import asmeta.evotest.junit2avalla.model.terms.JavaAssertionTerm;
import asmeta.evotest.junit2avalla.model.terms.JavaVariableTerm;

/**
 * Visitor class responsible for processing JavaParser AST nodes related to
 * variable declarations and method calls within a JUnit test class.
 */
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

		logger.debug("VARIABLE TERM: {}", node);
		context.setCurrentJavaVariable(new JavaVariableTerm());

		// set the type
		String type = node.getTypeAsString();
		logger.debug("TYPE: {}", type);
		context.getCurrentJavaVariable().setType(type);

		// set the name
		String name = node.getNameAsString();
		logger.debug("VARIABLE NAME: {}", name);
		context.getCurrentJavaVariable().setName(name);

		// set the value
		VariableValueVisitor variableValueVisitor = new VariableValueVisitor();
		node.accept(variableValueVisitor, context);

		// add the current variable to the variable map
		context.getVariablesMap().put(context.getCurrentJavaVariable().getName(), context.getCurrentJavaVariable());

	}

    /**
     * Processes a method call by identifying its type and handling it accordingly.
     * 
     * @param node    the method call expression node to be visited.
     * @param context the context containing shared data for the visitor operations.
     */
	@Override
	public void visit(MethodCallExpr node, Context context) {
		logger.debug("METHOD CALL TERM: {}", node);

		String name = node.getNameAsString();

		// method dispatcher
		if (name.equals(ScenarioParserUtil.STEP)) {
			logger.debug("STEP: {}", name);
			this.handleStepTerm(context);
		} else if (name.startsWith(ScenarioParserUtil.SET_FLAG)) {
			logger.debug("SET: {}", name);
			this.handleSetTerm(node, context);
		} else if (name.startsWith(ScenarioParserUtil.GET_FLAG)) {
			logger.debug("GET: {} ", name);
			// ignore the get method since without a variable assignment it is useless
		} else if (name.equals(ScenarioParserUtil.ASSERT_EQUALS)) {
			logger.debug("ASSERT EQUALS: {} ", name);
			this.handleAssertEqualsTerm(node, context);
		} else if (name.equals(ScenarioParserUtil.ASSERT_TRUE) || name.equals(ScenarioParserUtil.ASSERT_FALSE)) {
			logger.debug("ASSERT BOOLEAN: {}", name);
			this.handleAssertBooleanTerm(node, context);
		} else {
			logger.debug("METHOD CALL TERM NAME: {}", name);
		}

	}

    /**
     * Handles a step method call, updating the scenario context accordingly.
     * 
     * @param context the context containing shared data for the visitor operations.
     */
	private void handleStepTerm(Context context) {
		context.getScenarioManager().setStepTerm(context.getCurrentScenario());
		
		// consider the next checks
		context.setIgnoreChecks(false);
	}

    /**
     * Handles a method call representing a variable assignment using a "set" method.
     * 
     * @param node    the method call expression node representing the assignment.
     * @param context the context containing shared data for the visitor operations.
     */
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

    /**
     * Handles an assertEquals method call by extracting expected and actual values
     * and storing them in the scenario context.
     * 
     * @param node    the method call expression node representing the assertion.
     * @param context the context containing shared data for the visitor operations.
     */
	private void handleAssertEqualsTerm(MethodCallExpr node, Context context) {
		// only 2 or 3 arguments, otherwise stop the generation of the current scenario
		if (node.getArguments().size() <= 1 || node.getArguments().size() >= 3)
			throw new JUnitParseException("The arguments of the assertEquals method must be 2.");
		// assert equals for double can have three arguments:
		// assertEquals(double expected, double actual, double epsilon)
		
		// if true ignore the current assertion
		if(context.isIgnoreChecks()) {
			logger.debug("Ignoring the current assertion: {}", node);
			return;
		}
		
		context.setCurrentJavaAssertionTerm(new JavaAssertionTerm());
		context.getCurrentJavaAssertionTerm().setType(ScenarioParserUtil.ASSERT_EQUALS);

		// set expected
		logger.debug("EXPECTED: {}", node.getArgument(0));
		context.getCurrentJavaAssertionTerm().setExpected(node.getArgument(0).toString());
		context.getCurrentJavaAssertionTerm().setPrimitive(true);
		// if it's an identifier --> set not primitive
		node.getArgument(0).accept(new VoidVisitorAdapter<Void>() {
			@Override
			public void visit(FieldAccessExpr node, Void arg) {
				logger.debug("EXPECTED IDENTIFIER: {} ", node.getNameAsString());
				context.getCurrentJavaAssertionTerm().setPrimitive(false);
			}
		}, null);

		// set actual
		node.getArgument(1).accept(new AssertionActualVisitor(), context);
		
		// add term to scenario
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());

	}

    /**
     * Handles an assertTrue or assertFalse method call by extracting the actual
     * value and storing the assertion result in the scenario context.
     * 
     * @param node    the method call expression node representing the boolean assertion.
     * @param context the context containing shared data for the visitor operations.
     */
	private void handleAssertBooleanTerm(MethodCallExpr node, Context context) {
		// only 1 argument, otherwise stop the generation of the current scenario
		if (node.getArguments().size() != 1)
			throw new JUnitParseException("The argument of the assertEquals method must be 1.");
		
		// if true ignore the current assertion
		if(context.isIgnoreChecks()) {
			logger.debug("Ignoring the current assertion: {}", node);
			return;
		}
		
		// Retrieve the argument expression.
	    Expression assertionArgument = node.getArgument(0);
	    
	    // Inspect the structure of the expression.
	    if (assertionArgument instanceof BinaryExpr) {
	        // If it's a binary expression (e.g., int5 == int2),
	    	// Skip processing 
	    	logger.debug("Skipping the current check because the argument is a binary expression");
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
		try {
			assertionArgument.accept(new AssertionActualVisitor(), context);
		} catch (JUnitParseException e) {
			// if a JUnitParseException exception occurs, skip the current check only
			logger.debug("Skipping the current check because: {}", e.getMessage());
			return;
		}
		
		// add term to scenario
		context.getScenarioManager().setCheckTerm(context.getCurrentScenario(), context.getCurrentJavaAssertionTerm());
	}

}
