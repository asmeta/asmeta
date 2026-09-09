package tgtlib.definitions.normalform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import tgtlib.definitions.expression.IdExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.NotIDExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.normalform.dnf.DNFExpression;

/**
 */
class TermTest {

	static IdExpressionCreator icc = new IdExpressionCreator();

	@Test void parseRight() {
		assertEquals("abc", DNFExpression.parse("abc", icc).toString());
		assertEquals("a~bc", DNFExpression.parse("a!bc", icc).toString());
		assertEquals("~a~b~cd", DNFExpression.parse("!a!b!cd", icc).toString());
	}

	@Test void parseWrong1() {
		assertThrows(RuntimeException.class, () ->
			DNFExpression.parse("a + b", icc));
	}

	@Test void parseWrong2() {
		assertThrows(RuntimeException.class, () ->
			DNFExpression.parse("a2", icc));
	}

	@Test void duplicated() {
		IdUNotIdExpression a = icc.createIdExpression("a", null);
		NotExpression nota = (NotExpression) UnaryExpression.mkUnExpr(Operator.NOT, a);
		assertSame(a, nota.getOperand());
		assertThrows(java.lang.AssertionError.class, () -> {
			// adding in term t3 both a and not a
			new Term(Arrays.asList(a, (IdUNotIdExpression) nota));
		});
	}

	@Test void merge() {
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
