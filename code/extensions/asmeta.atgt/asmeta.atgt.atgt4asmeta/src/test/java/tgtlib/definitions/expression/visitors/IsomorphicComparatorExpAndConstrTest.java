package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.TreeSet;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConstCreator;

class IsomorphicComparatorExpAndConstrTest {

	private void test(String e1, String e2, int expectedResult) throws ParseException {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression exp1 = ExpressionParser.parse(e1, idcreator);
		idcreator = new EnumConstCreator();
		Expression exp2 = ExpressionParser.parse(e2, idcreator);
		IsomorphicComparatorExpAndConstr comp = new IsomorphicComparatorExpAndConstr();
		assertEquals(comp.compare(new Expression[]{exp1}, new Expression[]{exp2}), expectedResult);
	}

	private void test(String[] e1, String[] e2, int expectedResult) throws ParseException {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression[] exp1 = new Expression[e1.length];
		for(int i = 0; i < exp1.length; i++) {
			exp1[i] = ExpressionParser.parse(e1[i], idcreator);
		}
		idcreator = new EnumConstCreator();
		Expression[] exp2 = new Expression[e2.length];
		for(int i = 0; i < exp2.length; i++) {
			exp2[i] = ExpressionParser.parse(e2[i], idcreator);
		}
		IsomorphicComparatorExpAndConstr comp = new IsomorphicComparatorExpAndConstr();
		assertEquals(comp.compare(exp1, exp2), expectedResult);
	}

	@Test void compareAnd() throws Exception {
		test("a and b", "b and a", 0);
	}

	@Test void compareXor() throws Exception {
		test("a xor b", "b xor a", 0);
	}

	@Test void compareXor2() throws Exception {
		test("a2 xor a3", "a3 xor a4", 0);
	}

	@Test void compareXor2bis() throws Exception {
		test("a3 xor a4", "a2 xor a3", 0);
	}

	@Test void compareXor2ter() throws Exception {
		test("a2 and a3", "a3 and a4", 0);
	}

	@Test void compare1() throws Exception {
		test("(a and b) or c", "b and a or c", 0);
	}

	@Test void withConstraints() throws Exception {
		String[] e1 = {"(a and b) or c", "c"};
		String[] e2 = {"b and a or c", "c"};
		test(e1, e2, 0);
	}

	@Test void withConstraints2() throws Exception {
		String[] e1 = {"(a and b) or c", "c"};
		String[] e2 = {"b and a or c", "b"};
		test(e1, e2, 1);
	}

	@Test void withConstraints3() throws Exception {
		String[] e1 = {"(a and b) or c", "true"};
		String[] e2 = {"b and a or c", "true"};
		test(e1, e2, 0);
	}

	@Test void withConstraints4() throws Exception {
		String[] e1 = {"(a and b) or c", "true", "a"};
		String[] e2 = {"b and a or c", "true", "b"};
		test(e1, e2, 0);
	}

	@Test void withConstraints5() throws Exception {
		String[] e1 = {"(a and b) or c", "true", "a"};
		String[] e2 = {"b and a or c", "true", "a"};
		test(e1, e2, 1);
	}

	@Test void withConstraints6() throws Exception {
		TreeSet<Expression[]> uniqueSpecs = new TreeSet<Expression[]>(new IsomorphicComparatorExpAndConstr());
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression exp1 = ExpressionParser.parse("a and b", idcreator);
		uniqueSpecs.add(new Expression[]{exp1});
		Expression exp2 = ExpressionParser.parse("c and b", idcreator);
		uniqueSpecs.add(new Expression[]{exp2});
		assertEquals(1, uniqueSpecs.size());
	}

	@Test void withConstraints7() throws Exception {
		TreeSet<Expression[]> uniqueSpecs = new TreeSet<Expression[]>(new IsomorphicComparatorExpAndConstr());
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression exp1 = ExpressionParser.parse("a and b", idcreator);
		uniqueSpecs.add(new Expression[]{exp1});
		Expression exp2 = ExpressionParser.parse("c or b", idcreator);
		uniqueSpecs.add(new Expression[]{exp2});
		assertEquals(2, uniqueSpecs.size());
	}

	@Test void withConstraints8() throws Exception {
		String[] e1 = {"a11 and a23", "true"};
		String[] e2 = {"a8 and a15", "true"};
		test(e1, e2, 0);
	}

	@Test void withConstraints9() throws Exception {
		String[] e1 = {"a4", "not a0"};
		String[] e2 = {"a4", "not a1"};
		test(e1, e2, 0);
	}

	@Test void withConstraints10() throws Exception {
		String[] e1 = {"a0", "not a0"};
		String[] e2 = {"a0", "not a1"};
		test(e1, e2, 1);
	}

	@Test void withConstraints10bis() throws Exception {
		String[] e1 = {"a0", "not a1"};
		String[] e2 = {"a0", "not a0"};
		test(e1, e2, 1);
	}

	@Test void withConstraints11() throws Exception {
		String[] e1 = {"G19", "true"};
		String[] e2 = {"G37", "true"};
		String[] e3 = {"a47", "true"};
		test(e1, e2, 0);
		test(e1, e3, 0);
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression trueExpression = ExpressionParser.parse("true", idcreator);
		TreeSet<CoupleExpressions> uniqueSpecs = new TreeSet<CoupleExpressions>();
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e1[0], idcreator), trueExpression));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e2[0], idcreator), trueExpression));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e3[0], idcreator), trueExpression));
		assertEquals(1, uniqueSpecs.size());
		assertEquals("G19", uniqueSpecs.iterator().next().getExpression().toString());
	}

	@Test void withConstraints12() throws Exception {
		String[] e1 = {"G19", "true"};
		String[] e2 = {"G37", "true"};
		String[] e3 = {"(a47)", "true"};
		String[] e4 = {"(a33)", "true"};
		String[] e5 = {"G44", "true"};
		test(e1, e2, 0);
		test(e1, e3, 0);
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression trueExpression = BoolType.TRUE_CONST;
		TreeSet<CoupleExpressions> uniqueSpecs = new TreeSet<CoupleExpressions>();
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e1[0], idcreator), BoolType.TRUE_CONST));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e2[0], idcreator), BoolType.TRUE_CONST));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e3[0], idcreator), trueExpression));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e4[0], idcreator), trueExpression));
		uniqueSpecs.add(new CoupleExpressions(ExpressionParser.parse(e5[0], idcreator), trueExpression));
		assertEquals(1, uniqueSpecs.size());
		assertEquals("G19", uniqueSpecs.iterator().next().getExpression().toString());
	}

	@Test void withConstraints13() throws Exception {
		String[] e1 = {"a0 and a2", "a1 implies a0"};
		String[] e2 = {"a0 and a2", "a1 implies a2"};
		test(e1, e2, 0);
	}
}

class CoupleExpressions implements Comparable<CoupleExpressions> {
	private Expression[] exprConstr;

	public CoupleExpressions(Expression exp, Expression constr) {
		exprConstr = new Expression[2];
		exprConstr[0] = exp;
		exprConstr[1] = constr;
	}

	public Expression getExpression() {
		return exprConstr[0];
	}

	public Expression getConstraint() {
		return exprConstr[1];
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof CoupleExpressions) {
			return new IsomorphicComparatorExpAndConstr().compare(exprConstr, ((CoupleExpressions)obj).exprConstr)==0;
		}
		return false;
	}

	@Override
	public int compareTo(CoupleExpressions other) {
		return new IsomorphicComparatorExpAndConstr().compare(exprConstr, other.exprConstr);
	}

	@Override
	public int hashCode() {
		return 1;
	}
}