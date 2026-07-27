package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class ExprEvaluatorIncompleteModel {

	@Test
	public void test1() throws ParseException {
		// an expression which should be always true
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or not a");
		ExpressionEvaluator ev = new ExpressionEvaluator(Collections.EMPTY_MAP,false);
		for(int i = 0 ; i <100; i++){
			assertTrue(ev.evaluate(e));
		}
	}

}
