package tgtlib.definitions.normalform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
	public PushNotTest(String start, boolean theSame, String notStart) {
		this.start = start;
		this.same = theSame;
		this.notStart = notStart;
	}

	/**
	 * Method regExValues.
	 * @return Collection
	 */
	@Parameters
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
	@Test
	public void testForExpression() throws ParseException {
		Expression expr = ExpressionParser.parseAsNewBooleanExpression(start);
		Expression result = expr.accept(PushNot.pushNot);
		System.out.println(expr + "->" + result);
		if (same) assertSame(expr, result);
		assertEquals(notStart, result.toString());
	}
}
