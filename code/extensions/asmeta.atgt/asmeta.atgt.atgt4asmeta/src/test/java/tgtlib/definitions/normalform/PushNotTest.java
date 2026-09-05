package tgtlib.definitions.normalform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.Collection;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

/**
 */
public class PushNotTest {

	String start;
	boolean same;
	String notStart;

	/**
	 * Constructor for PushNotTest.
	 * @param start String
	 * @param theSame boolean
	 * @param notStart String
	 */
	public void initPushNotTest(String start, boolean theSame, String notStart) {
		this.start = start;
		this.same = theSame;
		this.notStart = notStart;
	}

	/**
	 * Method regExValues.
	 * @return Collection
	 */
	public static Collection regExValues() {
	  return Arrays.asList(new Object[][] {
	   {"not (a && b)",false, "not a or not b"},
	   {"not (a || b)",false, "not a and not b"},
	   {"not (a == b)",false, "a != b"},
	   {"not (a iff b)",false, "a != b"},
	   {"a && b && c",true, "(a and b) and c"},
	   {"a || b",true, "a or b"},
	   {"a || b || c",true, "(a or b) or c"},
	   {"a",true,"a"},
		{"not a",true,"not a"},
		{"not(not a)",false,"a"},
		{"(a || b) && c",true, "(a or b) and c"},
		{"(a && b) || c",true, "(a and b) or c"}
	   });
	 }

	/**
	 * Method testForExpression.
	 * @throws ParseException
	 */
	@MethodSource("regExValues") @ParameterizedTest
	public void testForExpression(String start, boolean theSame, String notStart) throws Exception {
		initPushNotTest(start, theSame, notStart);
		Expression expr = ExpressionParser.parseAsNewBooleanExpression(start);
		Expression result = expr.accept(PushNot.pushNot);
		System.out.println(expr + "->" + result);
		if (same) assertSame(expr, result);
		assertEquals(notStart, result.toString());
	}
}
