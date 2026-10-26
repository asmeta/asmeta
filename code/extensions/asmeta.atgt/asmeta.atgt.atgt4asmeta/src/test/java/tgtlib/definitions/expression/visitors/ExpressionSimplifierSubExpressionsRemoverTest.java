package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;

class ExpressionSimplifierSubExpressionsRemoverTest {

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

	@Test void test1() throws Exception {
		String[] vars = new String[] { "a" };
		test("a and b", "a", vars);
	}

	@Test void test2() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("a and (b or (c and d))", "a", vars);
	}

	@Test void test3() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("e and (f or (c and d))", "true", vars);
	}

	@Test void test4() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("e and (e or (c and d))", "true", vars);
	}

	@Test void test5() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(a and (e or f)) and (b or (c and d))", "a", vars);
	}

	@Test void test6() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(a and (c or d)) and (b or (c and d))", "(a and (c or d)) and (b or (c and d))", vars);
	}

	@Test void test7() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(a and b) or c", "true", vars);
	}

	@Test void test8() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(a or b) and c", "a or b", vars);
	}

	@Test void test9() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("not(b and c)", "true", vars);
	}

	@Test void test10() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("not(b or c)", "not b", vars);
	}

	@Test void test11() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(a and (e or f)) and !(b or (c and d))", "a and not b", vars);
	}

	@Test void test12() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("b implies c", "true", vars);
	}

	@Test void test13() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("c implies a", "true", vars);
	}

	@Test void test14() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("a implies b", "a implies b", vars);
	}

	@Test void test15() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("a implies not (b or c)", "a implies not b", vars);
	}

	@Test void test16() throws Exception {
		String[] vars = new String[] { "a", "b" };
		test("(not a) and c", "not a", vars);
	}
}
