package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;

class ExprEvaluatorIncompleteModel {

	@Test void test1() throws Exception {
		// an expression which should be always true
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or not a");
		ExpressionEvaluator ev = new ExpressionEvaluator(Collections.EMPTY_MAP,false);
		for(int i = 0 ; i <100; i++){
			assertTrue(ev.evaluate(e));
		}
	}

}
