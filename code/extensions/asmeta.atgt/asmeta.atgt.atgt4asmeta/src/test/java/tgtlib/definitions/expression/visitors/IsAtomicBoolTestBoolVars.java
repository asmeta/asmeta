package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
@RunWith(Parameterized.class)

public class IsAtomicBoolTestBoolVars {
	
	Expression expr;
	
	private boolean result;
	
	public IsAtomicBoolTestBoolVars(String expression, boolean result) throws ParseException {
		expr = ExpressionParser.parseAsNewBooleanExpression(expression);
		this.result = result;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				// constants
				{ "false", true },
				{ "true", true},
				// IDS
				{ "a", true},
				// EQ
				{ "a == a", false},
				{ "a == true", true},
				{ "true == a", true},
				{ "a == false", true },
				{ "a == b", false},
				// NEQ
				{ "a != a", false},
				{ "a != true", true},
				{ "true != a", true},
				{ "a != false", true },
				{ "a != b", false},
				// BINARY
				{ "a or b", false},
				{ "a or b", false},
				// complex
				{ "(((false and true) or false) or e_1)", false},
				{ "((not e_0 or e_1) and e_0)", false }
		};
		return Arrays.asList(data);
	}


	@Test
	public void testBooleanExpressions() {
		Boolean actualResult = expr.accept(IsAtomicBool.isAtomicBool);
		assertEquals(result, actualResult);
	}

}
