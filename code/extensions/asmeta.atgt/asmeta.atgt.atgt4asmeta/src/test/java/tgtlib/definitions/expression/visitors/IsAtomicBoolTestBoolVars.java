package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class IsAtomicBoolTestBoolVars {
	
	Expression expr;
	
	private boolean result;

	public void initIsAtomicBoolTestBoolVars(String expression, boolean result) throws ParseException {
		expr = ExpressionParser.parseAsNewBooleanExpression(expression);
		this.result = result;
	}

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


	@MethodSource("data") @ParameterizedTest
	public void testBooleanExpressions(String expression, boolean result) throws ParseException {
		initIsAtomicBoolTestBoolVars(expression, result);
		Boolean actualResult = expr.accept(IsAtomicBool.isAtomicBool);
		assertEquals(result, actualResult);
	}

}
