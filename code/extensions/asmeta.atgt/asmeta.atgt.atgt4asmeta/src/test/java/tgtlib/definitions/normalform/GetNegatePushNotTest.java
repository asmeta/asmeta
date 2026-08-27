package tgtlib.definitions.normalform;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

/**
 */
@RunWith(Parameterized.class)
public class GetNegatePushNotTest {
	String start;
	String eqStart;
	String notStart;

	/**
	 * Constructor for GetNegatePushNotTest.
	 * @param start String
	 * @param eqStart String
	 * @param notStart String
	 */
	public GetNegatePushNotTest(String start, String eqStart, String notStart) {
		this.start = start;
		this.eqStart = eqStart;
		this.notStart = notStart;
	}

	/**
	 * Method regExValues.
	 * @return Collection<String[]>
	 */
	@Parameters
	public static Collection<String[]> regExValues() {
		return Arrays
				.asList(new String[][] {
						{ "not (a or b) xor c", "not(a or b) xor c", "(not a and not b) = c" },
						{ "(a or not b) xor c", "(a or not b) xor c", "(a or not b) = c" },
						{ "a xor b", "a xor b", "a = b" },
						{ "not a xor c", "not a xor c", "not a = c" },
						{ "a == b", "a = b", "a != b" },
						{ "a != b", "a != b", "a = b" },
						{ "a && b", "a and b", "not a or not b" },
						{ "a && b && c", "(a and b) and c",
								"(not a or not b) or not c" },
						{ "a || b", "a or b", "not a and not b" },
						{ "a || b || c", "(a or b) or c", "(not a and not b) and not c" },
						{ "a", "a", "not a" }, 
						{ "not a", "not a", "a" },
						{ "not(not a)", "not(not a)", "not a" },
						{ "not(a && b)", "not(a and b)", "a and b" },
						{ "(a || b) && c", "(a or b) and c", "(not a and not b) or not c" },
						{ "(a && b) || c", "(a and b) or c", "(not a or not b) and not c" } });
	}

	/**
	 * Method testForExpression.
	 * @throws ParseException
	 * @throws tgtlib.definitions.expression.parser.ParseException 
	 */
	@Test
	public void testForExpression() throws ParseException {
		Expression expr = ExpressionParser.parseAsNewBooleanExpression(start);
		assertEquals(eqStart, expr.toString());
		Expression result = expr.accept(GetNegatePushNot.pushAndNegate);
		assertEquals(notStart, result.toString());
	}

}
