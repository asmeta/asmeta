package tgtlib.definitions.normalform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.junit.Test;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.NotIDExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.normalform.dnf.DNFExpression;

/**
 */
public class TermTest {

	static IdExpressionCreator icc = new IdExpressionCreator();

	@Test
	public void testParseRight() {
		assertEquals("abc", DNFExpression.parse("abc", icc).toString());
		assertEquals("a~bc", DNFExpression.parse("a!bc", icc).toString());
		assertEquals("~a~b~cd", DNFExpression.parse("!a!b!cd", icc).toString());
	}

	@Test(expected = RuntimeException.class)
	public void testParseWrong1() {
		DNFExpression.parse("a + b", icc);
	}

	@Test(expected = RuntimeException.class)
	public void testParseWrong2() {
		DNFExpression.parse("a2", icc);
	}

	@Test(expected = java.lang.AssertionError.class)
	public void testDuplicated() {
		IdUNotIdExpression a = icc.createIdExpression("a", null);
		NotExpression nota = (NotExpression) UnaryExpression.mkUnExpr(Operator.NOT, a);
		assertSame(a,nota.getOperand());
		// adding in term t3 both a and not a
		new Term(Arrays.asList(a,(IdUNotIdExpression)nota));
	}

	@Test
	public void testMerge() {
		IdExpression a = icc.createIdExpression("a", null);
		IdExpression b = icc.createIdExpression("b", null);
		NotIDExpression nota = new NotIDExpression(a);
		Term t1 = new Term(a);
		assertEquals(1, t1.merge(t1).size());
		// a + not a
		Term t2 = new Term(nota);
		assertNull(t1.merge(t2));
		// a + not a and b
		t2.addLiteral(b);
		assertNull(t1.merge(t2));
	}

}
