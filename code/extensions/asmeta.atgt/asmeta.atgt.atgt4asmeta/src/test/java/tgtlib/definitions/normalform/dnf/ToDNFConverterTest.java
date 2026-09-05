package tgtlib.definitions.normalform.dnf;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.normalform.BoolNFExpression;

/**
 */
class ToDNFConverterTest extends ExpressionsToTest{

	/**
	 * Method setUpBeforeClass.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test void forAndDNFExpression() {
		BoolNFExpression aandb = new DNFExpression("ab");
		BoolNFExpression res = DNFExprConverter.getDNF(aandb.getEqExpression());
		assertEquals(res, aandb);
		aandb = new DNFExpression("abcd");
		res = DNFExprConverter.getDNF(aandb.getEqExpression());
		assertEquals(res, aandb);
	}

	@Test void forOrDNFExpression() {
		BoolNFExpression aorb = new DNFExpression("a + b");
		BoolNFExpression res = DNFExprConverter.getDNF(aorb.getEqExpression());
		assertEquals(res, aorb);
		// more complex
		aorb = new DNFExpression("ab + cd");
		res = DNFExprConverter.getDNF(aorb.getEqExpression());
		assertEquals(res, aorb);
	}

	@Test void forNotExpression() {
		BoolNFExpression res = DNFExprConverter.getDNF(not_AandB);
		assertEquals("!A + !B", res.toString());
		BoolNFExpression a = new DNFExpression("ab + cd");
		Expression nota = UnaryExpression.mkUnExpr(Operator.NOT, a.getEqExpression());
		res = DNFExprConverter.getDNF(nota);
		assertEquals("!a!c + !a!d + !b!c + !b!d",res.toString());
	}

	@Test void forExpressions() {
		BoolNFExpression res = DNFExprConverter.getDNF(aANDb);
		assertEquals("AB", res.toString());
		res = DNFExprConverter.getDNF(aORb);
		assertEquals("A + B", res.toString());
	}

	@Test void forIdExpression() {
		BoolNFExpression res = DNFExprConverter.getDNF(A);
		assertEquals("A", res.toString());
	}
}
