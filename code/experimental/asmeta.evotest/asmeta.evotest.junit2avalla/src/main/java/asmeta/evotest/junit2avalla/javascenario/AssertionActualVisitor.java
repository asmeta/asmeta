package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This class is a visitor that processes actual variables and method calls in
 * assertions within a parsed Java scenario. It extends
 * {@link VoidVisitorAdapter} with a {@link Context} parameter to traverse and
 * modify an Abstract Syntax Tree (AST) using JavaParser.
 * 
 * <p>
 * When a variable or method call is unsupported, an error is logged, and a
 * {@link JUnitParseException} is thrown.
 * </p>
 */
public class AssertionActualVisitor extends VoidVisitorAdapter<Context> {

	/** Logger */
	private final Logger logger = LogManager.getLogger(AssertionActualVisitor.class);

	/**
	 * Processes a {@link NameExpr} (variable name) found in the AST.
	 * 
	 * @param node    the variable name expression node
	 * @param context the parsing context
	 * @throws JUnitParseException if the variable is not found in the context
	 */
	@Override
	public void visit(NameExpr node, Context context) {
		logger.debug("ACTUAL VARIABLE: {}.", node);
		if (context.getVariablesMap().containsKey(node.getNameAsString())) {
			String value = context.getVariablesMap().get(node.getNameAsString()).getValue();
			ScenarioParserUtil.buildNaturalGetter(value, context.getCurrentJavaAssertionTerm());
			if (value.startsWith(ScenarioParserUtil.GET_FLAG)) {
				// add the value to the getters dictionary
				context.getGetterMap().put(context.getCurrentJavaAssertionTerm().getActual(),
						context.getCurrentJavaAssertionTerm().getExpected());
			}
		} else {
			logger.debug("Variable not found int he variable Map: {}", node.getNameAsString());
			throw new JUnitParseException("Unable to set the actual variable, variable not found int he variable Map: "
					+ node.getNameAsString());
		}
	}

	/**
	 * Processes a {@link MethodCallExpr} (method call expression) found in the AST.
	 * 
	 * @param node    the method call expression node
	 * @param context the parsing context
	 * @throws JUnitParseException if the method call is not supported
	 */
	@Override
	public void visit(MethodCallExpr node, Context context) {
		logger.debug("ACTUAL METHOD CALL: {}.", node);
		if (node.getNameAsString().contains(ScenarioParserUtil.GET_FLAG)) {
			// build a getter function with Domain -> Codomain
			String getter = ScenarioParserUtil.buildDomainCodomain(node.getNameAsString());
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(getter, context.getCurrentJavaAssertionTerm());
		} else {
			logger.debug("Method call not supported: {}", node.getNameAsString());
			throw new JUnitParseException("Method call not supported: " + node.getNameAsString());
		}
	}

}
