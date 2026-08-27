package tgtlib.definitions.normalform.dnf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tgtlib.definitions.expression.ExpressionsToTest;

/**
 */
public class DNFExpressionTest extends ExpressionsToTest {

	@Test
	public void testParse() {
		assertEquals("[a]", DNFExpression.parse("a").toString());
		assertEquals("[a, b]", DNFExpression.parse("a + b").toString());
		assertEquals("[~a~b, ~cd]", DNFExpression.parse("!a!b + !cd").toString());
		assertEquals("[a, bc]", DNFExpression.parse("a + bc").toString());
	}

	@Test
	public void testConstructor() {
		DNFExpression dnfExpression = new DNFExpression(A);
		assertEquals(1, dnfExpression.getTerms().size());
		assertEquals(1, dnfExpression.getTerms().get(0).size());		
		assertEquals("A", dnfExpression.toString());
	}

	@Test
	public void testMakeExpression1() {
		assertEquals("a", new DNFExpression("a").toString());
	}
	@Test
	public void testMakeExpression() {
		assertEquals("(a and b) and c", new DNFExpression("abc")
				.getEqExpression().toString());
	}

	@Test
	public void testSize() {
		assertEquals(2, new DNFExpression("a + b").getTerms().size());
	}

	@Test
	public void testGetTerms() {
		assertEquals("[a]", new DNFExpression("a").getTerms().toString());
		assertEquals("[~a~b, ~cd]",
				new DNFExpression(DNFExpression.parse("!a!b + !cd")).getTerms()
						.toString());
	}
	@Test
	public void testtoString() {
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
