package tgtlib.definitions.normalform.dnf;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.definitions.expression.ExpressionsToTest;

import org.junit.jupiter.api.Test;

/**
 */
class DNFExpressionTest extends ExpressionsToTest {

	@Test void parse() {
		assertEquals("[a]", DNFExpression.parse("a").toString());
		assertEquals("[a, b]", DNFExpression.parse("a + b").toString());
		assertEquals("[~a~b, ~cd]", DNFExpression.parse("!a!b + !cd").toString());
		assertEquals("[a, bc]", DNFExpression.parse("a + bc").toString());
	}

	@Test void constructor() {
		DNFExpression dnfExpression = new DNFExpression(A);
		assertEquals(1, dnfExpression.getTerms().size());
		assertEquals(1, dnfExpression.getTerms().get(0).size());		
		assertEquals("A", dnfExpression.toString());
	}

	@Test void makeExpression1() {
		assertEquals("a", new DNFExpression("a").toString());
	}

	@Test void makeExpression() {
		assertEquals("(a and b) and c", new DNFExpression("abc")
				.getEqExpression().toString());
	}

	@Test void size() {
		assertEquals(2, new DNFExpression("a + b").getTerms().size());
	}

	@Test void getTerms() {
		assertEquals("[a]", new DNFExpression("a").getTerms().toString());
		assertEquals("[~a~b, ~cd]",
				new DNFExpression(DNFExpression.parse("!a!b + !cd")).getTerms()
						.toString());
	}

	@Test void testtoString() {
		DNFExpression exp1 = new DNFExpression("a");
		assertEquals("a", exp1.toString());
		assertEquals("a", exp1.toString(true));
		exp1 = new DNFExpression("a + b");
		assertEquals("a + b", exp1.toString());
		assertEquals("a + b", exp1.toString(true));
		exp1 = new DNFExpression("ab");
		assertEquals("ab", exp1.toString());
		assertEquals("a & b", exp1.toString(true));
		exp1 = new DNFExpression("ab + cd");
		assertEquals("ab + cd", exp1.toString());
		assertEquals("a & b + c & d", exp1.toString(true));
	}

}
