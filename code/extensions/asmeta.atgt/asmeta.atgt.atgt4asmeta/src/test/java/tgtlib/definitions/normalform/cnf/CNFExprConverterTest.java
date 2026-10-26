package tgtlib.definitions.normalform.cnf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tgtlib.definitions.expression.BinaryExpression.mkBinExpr;
import static tgtlib.definitions.expression.Operator.AND;
import static tgtlib.definitions.expression.Operator.OR;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.normalform.NFExpressionConverter;

/**
 */
class CNFExprConverterTest extends ExpressionsToTest {

	/**
	 * Method setUpBeforeClass.
	 * @throws Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test void forNotExpression() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(not_AandB);
		// !A or ! B
		assertEquals("[~A~B]", res.toString());
		//not (a or b)
		Expression not1 = UnaryExpression.mkUnExpr(Operator.NOT, aORb);
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(not1);
		assertEquals("[~A, ~B]", res.toString());
		
	}

	@Test void forXorExpression() throws Exception {
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("a xor b");
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(axorb);
		assertEquals("[ab, ~b~a]", res.toString());
	}

	@Test void forExpressions() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(aANDb);
		assertEquals("[A, B]", res.toString());
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(aORb);
		assertEquals("[AB]", res.toString());
	}

	@Test void forIdExpression() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(A);
		assertEquals("[A]", res.toString());
	}

	// what happens if I have not a and a??
	@Test void forContradiction() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, notA));
		assertEquals("[A, ~A]", res.toString());
	}

	// what happens if A and false?
	@Test void falsEandTRUE() {
		CNFExpression res;
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, BoolType.TRUE_CONST));
		assertEquals("[A]", res.toString());
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.FALSE_CONST));
		assertEquals("[A]", res.toString());
	}

	@Test void testTRUE() {
		assertThrows(CNFException.class, () ->
			CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.TRUE_CONST)));
	}

	@Test void testFALSE() {
		assertThrows(CNFException.class, () ->
			CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, BoolType.FALSE_CONST)));
	}

	// very particular combinations with xor
	@Test void xor() {		
		Expression res = NFExpressionConverter.getXorSimpl(A, B, true);
		assertEquals("(A and not B) or (not A and B)", res.toString());
		res = NFExpressionConverter.getXorSimpl(A, B, true);
		
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.FALSE_CONST));
		assertEquals("[A]", res.toString());
	}

	@Test void forEqExpression() throws Exception {
		//
		Expression eq = ExpressionParser.parseAsNewBooleanExpression("a==b");
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(eq);
		assertEquals("[a~b, b~a]", res.toString());
		//not (a or b)
		eq = ExpressionParser.parseAsNewBooleanExpression("(not a) == (not b)");
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(eq);
		assertEquals("[~ab, ~ba]", res.toString());
		
	}
	
	
}
