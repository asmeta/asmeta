package asmeta.evotest.junit2avalla.javascenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class AssertionActualVisitor extends VoidVisitorAdapter<Context> {

	/** Logger */
	private final Logger logger = LogManager.getLogger(AssertionActualVisitor.class);

	@Override
	public void visit(NameExpr node, Context context) {
		logger.info("ACTUAL VARIABLE: {}.", node);
		if (context.getVariablesMap().containsKey(node.getNameAsString())) {
			String value = context.getVariablesMap().get(node.getNameAsString()).getValue();
			ScenarioParserUtil.buildNaturalGetter(value, context.getCurrentJavaAssertionTerm());
			if(value.startsWith(ScenarioParserUtil.GET_FLAG)) {
				// add the value to the getters dictionary
				context.getGetterMap().put(context.getCurrentJavaAssertionTerm().getActual(), context.getCurrentJavaAssertionTerm().getExpected());
			}
		} else {
			logger.error("Variable not found int he variable Map: {}", node.getNameAsString());
			throw new JUnitParseException("Unable to set the actual variable, variable not found int he variable Map: "
					+ node.getNameAsString());
		}
	}

	@Override
	public void visit(MethodCallExpr node, Context context) {
		logger.info("ACTUAL METHOD CALL: {}.", node);
		if (node.getNameAsString().contains(ScenarioParserUtil.GET_FLAG)) {
			// build a getter function with Domain -> Codomain
			String getter = ScenarioParserUtil.buildDomainCodomain(node.getNameAsString());
			// build a getter function with natural type
			ScenarioParserUtil.buildNaturalGetter(getter, context.getCurrentJavaAssertionTerm());
		} else {
			logger.error("Method call not supported: {}", node.getNameAsString());
			throw new JUnitParseException("Method call not supported: " + node.getNameAsString());
		}

	}

}
