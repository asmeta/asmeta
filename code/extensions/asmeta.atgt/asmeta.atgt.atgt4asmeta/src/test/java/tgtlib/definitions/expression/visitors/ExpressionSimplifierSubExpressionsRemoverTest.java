package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class ExpressionSimplifierSubExpressionsRemoverTest {

	private void test(String original, String expected, String[] vars) throws ParseException {
		EnumConstCreator constCreator = new EnumConstCreator();
		Expression exp = ExpressionParser.parse(original, constCreator);
		Set<IdExpression> idsToKeep = new HashSet<IdExpression>();
		for (String var : vars) {
			idsToKeep.add((IdExpression) ExpressionParser.parse(var, constCreator));
		}
		ExpressionSimplifierSubExpressionsRemover simplifier = new ExpressionSimplifierSubExpressionsRemover(idsToKeep);
		Expression outExp = simplifier.simplify(exp);
		assertEquals(expected, outExp.toString());
	}

	@Test
	public void test1() throws ParseException {
		String[] vars = new String[] { "a" };
		test("a and b", "a", vars);
	}

	@Test
	public void test2() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("a and (b or (c and d))", "a", vars);
	}

	@Test
	public void test3() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("e and (f or (c and d))", "true", vars);
	}

	@Test
	public void test4() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("e and (e or (c and d))", "true", vars);
	}

	@Test
	public void test5() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(a and (e or f)) and (b or (c and d))", "a", vars);
	}

	@Test
	public void test6() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(a and (c or d)) and (b or (c and d))", "(a and (c or d)) and (b or (c and d))", vars);
	}

	@Test
	public void test7() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(a and b) or c", "true", vars);
	}

	@Test
	public void test8() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(a or b) and c", "a or b", vars);
	}

	@Test
	public void test9() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("not(b and c)", "true", vars);
	}

	@Test
	public void test10() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("not(b or c)", "not b", vars);
	}

	@Test
	public void test11() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(a and (e or f)) and !(b or (c and d))", "a and not b", vars);
	}

	@Test
	public void test12() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("b implies c", "true", vars);
	}

	@Test
	public void test13() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("c implies a", "true", vars);
	}

	@Test
	public void test14() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("a implies b", "a implies b", vars);
	}

	@Test
	public void test15() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("a implies not (b or c)", "a implies not b", vars);
	}

	@Test
	public void test16() throws ParseException {
		String[] vars = new String[] { "a", "b" };
		test("(not a) and c", "not a", vars);
	}
}
