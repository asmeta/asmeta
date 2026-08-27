package tgtlib.definitions.normalform.cnf;

import static org.junit.Assert.assertEquals;
import static tgtlib.definitions.expression.BinaryExpression.mkBinExpr;
import static tgtlib.definitions.expression.Operator.AND;
import static tgtlib.definitions.expression.Operator.OR;

import org.junit.BeforeClass;
import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.normalform.NFExpressionConverter;

/**
 * test o the naive converter
 * 
 */
public class CNFExprConverterNaiveTest extends ExpressionsToTest {

	/**
	 * Method setUpBeforeClass.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testForNotExpression() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(not_AandB);
		// !A or ! B
		assertEquals("[~A~B]", res.toString());
		//not (a or b)
		Expression not1 = UnaryExpression.mkUnExpr(Operator.NOT, aORb);
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(not1);
		assertEquals("[~A, ~B]", res.toString());
		
	}

	@Test
	public void testForXorExpression() throws ParseException {
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("a xor b");
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(axorb);
		assertEquals("[ab, ~b~a]", res.toString());
	}
	
	@Test
	public void testForExpressions() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(aANDb);
		assertEquals("[A, B]", res.toString());
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(aORb);
		assertEquals("[AB]", res.toString());
	}

	@Test
	public void testForIdExpression() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(A);
		assertEquals("[A]", res.toString());
	}

	// what happens if I have not a and a??
	@Test
	public void testForContradiction() {
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, notA));
		assertEquals("[A, ~A]", res.toString());
	}

	// what happens if A and false?
	@Test
	public void testFALSEandTRUE() {
		CNFExpression res;
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, BoolType.TRUE_CONST));
		assertEquals("[A]", res.toString());
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.FALSE_CONST));
		assertEquals("[A]", res.toString());
	}
	@Test(expected=CNFException.class)
	public void testTRUE() {
		CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.TRUE_CONST));
	}
	@Test(expected=CNFException.class)
	public void testFALSE() {
		CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, AND, BoolType.FALSE_CONST));
	}
	
	// very particular combinations with xor
	@Test
	public void testXOR() {		
		Expression res = NFExpressionConverter.getXorSimpl(A, B, true);
		assertEquals("(A and not B) or (not A and B)", res.toString());
		res = NFExpressionConverter.getXorSimpl(A, B, true);
		
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(mkBinExpr(A, OR, BoolType.FALSE_CONST));
		assertEquals("[A]", res.toString());
	}
	@Test
	public void testForEqExpression() throws ParseException {
		//
		Expression eq = ExpressionParser.parseAsNewBooleanExpression("a==b");
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(eq);
		assertEquals("[a~b, b~a]", res.toString());
		//not (a or b)
		eq = ExpressionParser.parseAsNewBooleanExpression("(not a) == (not b)");
		res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(eq);
		assertEquals("[~ab, ~ba]", res.toString());
		
	}
	
	@Test
	public void testOrOrInside() throws ParseException {
		// already as CNF
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and (b or c or d)");		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a, bcd]", res.toString());
	}


	// both cases return an empty 
	@Test
	public void testXOrtau() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a xor not a");		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[]", res.toString());
	}

	@Test
	public void testOrTau() throws ParseException {
		// already as CNF
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or not a");		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[]", res.toString());
	}

	@Test
	public void testXOrConstra() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a xor a");		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a, ~a]", res.toString());
	}

	@Test
	public void testAndConstra() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and not a");		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a, ~a]", res.toString());
	}

	@Test
	public void testComplex() throws ParseException {
		String eBug = "(((false and true) or false) or e_1) xor ((not e_0 or e_1) and e_0)";
		Expression id = ExpressionParser.parseAsNewBooleanExpression(eBug);		
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[e_1~e_0, e_1e_0, ~e_1~e_0]", res.toString());
	}

	@Test
	public void testBigExpression() throws ParseException {
		String bigExp = "not ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((!ID13) or (!ID6)) or (!ID8)) or (!ID12)) or (!ID0)) and (((((!ID2) or (!ID14)) or (!ID12)) or (!ID7)) or (!ID5))) and (((((!ID2) or (!ID13)) or (!ID10)) or (!ID8)) or (!ID3))) and (((((!ID13) or (!ID10)) or (!ID9)) or (!ID0)) or (!ID4))) and (((((!ID2) or (!ID13)) or (!ID10)) or (!ID8)) or (!ID5))) and (((((!ID2) or (!ID13)) or (!ID9)) or (!ID6)) or (!ID11))) and (((((!ID14) or (!ID12)) or (!ID7)) or (!ID5)) or (!ID0))) and (((((!ID11) or (!ID7)) or (!ID5)) or (!ID15)) or (!ID0))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID6)) or (!ID7))) and (((((!ID13) or (!ID1)) or (!ID6)) or (!ID12)) or (!ID7))) and (((((!ID13) or (!ID6)) or (!ID11)) or (!ID7)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID6)) or (!ID8)) or (!ID12))) and (((((!ID9) or (!ID1)) or (!ID12)) or (!ID3)) or (!ID15))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID7)) or (!ID5))) and (((((!ID13) or (!ID10)) or (!ID9)) or (!ID1)) or (!ID3))) and (((((!ID2) or (!ID14)) or (!ID6)) or (!ID12)) or (!ID7))) and (((((!ID13) or (!ID10)) or (!ID9)) or (!ID1)) or (!ID5))) and (((((!ID2) or (!ID13)) or (!ID10)) or (!ID9)) or (!ID4))) and (((((!ID13) or (!ID1)) or (!ID12)) or (!ID3)) or (!ID7))) and (((((!ID13) or (!ID10)) or (!ID9)) or (!ID1)) or (!ID6))) and (((((!ID10) or (!ID9)) or (!ID15)) or (!ID0)) or (!ID4))) and (((((!ID13) or (!ID10)) or (!ID6)) or (!ID8)) or (!ID0))) and (((((!ID2) or (!ID6)) or (!ID8)) or (!ID12)) or (!ID15))) and (((((!ID13) or (!ID10)) or (!ID1)) or (!ID7)) or (!ID5))) and (((((!ID2) or (!ID10)) or (!ID8)) or (!ID5)) or (!ID15))) and (((((!ID13) or (!ID10)) or (!ID7)) or (!ID0)) or (!ID4))) and (((((!ID2) or (!ID6)) or (!ID11)) or (!ID7)) or (!ID15))) and (((((!ID2) or (!ID9)) or (!ID11)) or (!ID3)) or (!ID15))) and (((((!ID13) or (!ID9)) or (!ID1)) or (!ID12)) or (!ID3))) and (((((!ID13) or (!ID9)) or (!ID11)) or (!ID5)) or (!ID0))) and (((((!ID13) or (!ID9)) or (!ID1)) or (!ID12)) or (!ID5))) and (((((!ID14) or (!ID9)) or (!ID6)) or (!ID12)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID8)) or (!ID12)) or (!ID5))) and (((((!ID10) or (!ID1)) or (!ID6)) or (!ID7)) or (!ID15))) and (((((!ID2) or (!ID13)) or (!ID8)) or (!ID12)) or (!ID3))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID9)) or (!ID3))) and (((((!ID1) or (!ID6)) or (!ID12)) or (!ID7)) or (!ID15))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID9)) or (!ID5))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID9)) or (!ID6))) and (((((!ID2) or (!ID13)) or (!ID10)) or (!ID7)) or (!ID4))) and (((((!ID2) or (!ID14)) or (!ID9)) or (!ID6)) or (!ID12))) and (((((!ID2) or (!ID13)) or (!ID11)) or (!ID7)) or (!ID5))) and (((((!ID14) or (!ID10)) or (!ID9)) or (!ID3)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID12)) or (!ID7)) or (!ID4))) and (((((!ID2) or (!ID13)) or (!ID6)) or (!ID11)) or (!ID7))) and (((((!ID1) or (!ID12)) or (!ID3)) or (!ID7)) or (!ID15))) and (((((!ID12) or (!ID7)) or (!ID15)) or (!ID0)) or (!ID4))) and (((((!ID13) or (!ID10)) or (!ID1)) or (!ID3)) or (!ID7))) and (((((!ID9) or (!ID6)) or (!ID11)) or (!ID15)) or (!ID0))) and (((((!ID13) or (!ID9)) or (!ID6)) or (!ID11)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID11)) or (!ID3)) or (!ID7))) and (((((!ID2) or (!ID10)) or (!ID8)) or (!ID3)) or (!ID15))) and (((((!ID2) or (!ID9)) or (!ID12)) or (!ID15)) or (!ID4))) and (((((!ID2) or (!ID8)) or (!ID12)) or (!ID3)) or (!ID15))) and (((((!ID10) or (!ID9)) or (!ID1)) or (!ID6)) or (!ID15))) and (((((!ID13) or (!ID9)) or (!ID11)) or (!ID3)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID10)) or (!ID6)) or (!ID8))) and (((((!ID2) or (!ID10)) or (!ID6)) or (!ID8)) or (!ID15))) and (((((!ID10) or (!ID1)) or (!ID7)) or (!ID5)) or (!ID15))) and (((((!ID13) or (!ID8)) or (!ID12)) or (!ID3)) or (!ID0))) and (((((!ID14) or (!ID10)) or (!ID9)) or (!ID5)) or (!ID0))) and (((((!ID11) or (!ID3)) or (!ID7)) or (!ID15)) or (!ID0))) and (((((!ID14) or (!ID10)) or (!ID3)) or (!ID7)) or (!ID0))) and (((((!ID13) or (!ID11)) or (!ID7)) or (!ID5)) or (!ID0))) and (((((!ID10) or (!ID6)) or (!ID8)) or (!ID15)) or (!ID0))) and (((((!ID2) or (!ID10)) or (!ID9)) or (!ID15)) or (!ID4))) and (((((!ID2) or (!ID10)) or (!ID7)) or (!ID15)) or (!ID4))) and (((((!ID10) or (!ID1)) or (!ID3)) or (!ID7)) or (!ID15))) and (((((!ID10) or (!ID9)) or (!ID1)) or (!ID5)) or (!ID15))) and (((((!ID2) or (!ID14)) or (!ID10)) or (!ID3)) or (!ID7))) and (((((!ID13) or (!ID11)) or (!ID3)) or (!ID7)) or (!ID0))) and (((((!ID14) or (!ID10)) or (!ID7)) or (!ID5)) or (!ID0))) and (((((!ID10) or (!ID7)) or (!ID15)) or (!ID0)) or (!ID4))) and (((((!ID14) or (!ID10)) or (!ID6)) or (!ID7)) or (!ID0))) and (((((!ID13) or (!ID10)) or (!ID8)) or (!ID5)) or (!ID0))) and (((((!ID2) or (!ID13)) or (!ID9)) or (!ID11)) or (!ID5))) and (((((!ID2) or (!ID12)) or (!ID7)) or (!ID15)) or (!ID4))) and (((((!ID2) or (!ID13)) or (!ID9)) or (!ID11)) or (!ID3))) and (((((!ID14) or (!ID9)) or (!ID12)) or (!ID5)) or (!ID0))) and (((((!ID2) or (!ID9)) or (!ID11)) or (!ID5)) or (!ID15))) and (((((!ID2) or (!ID14)) or (!ID12)) or (!ID3)) or (!ID7))) and (((((!ID2) or (!ID8)) or (!ID12)) or (!ID5)) or (!ID15))) and (((((!ID2) or (!ID11)) or (!ID7)) or (!ID5)) or (!ID15))) and (((((!ID13) or (!ID1)) or (!ID12)) or (!ID7)) or (!ID5))) and (((((!ID9) or (!ID11)) or (!ID5)) or (!ID15)) or (!ID0))) and (((((!ID13) or (!ID9)) or (!ID1)) or (!ID6)) or (!ID12))) and (((((!ID10) or (!ID8)) or (!ID3)) or (!ID15)) or (!ID0))) and (((((!ID13) or (!ID8)) or (!ID12)) or (!ID5)) or (!ID0))) and (((((!ID6) or (!ID11)) or (!ID7)) or (!ID15)) or (!ID0))) and (((((!ID2) or (!ID14)) or (!ID9)) or (!ID12)) or (!ID5))) and (((((!ID13) or (!ID10)) or (!ID1)) or (!ID6)) or (!ID7))) and (((((!ID9) or (!ID1)) or (!ID12)) or (!ID5)) or (!ID15))) and (((((!ID2) or (!ID14)) or (!ID9)) or (!ID12)) or (!ID3))) and (((((!ID2) or (!ID13)) or (!ID9)) or (!ID12)) or (!ID4))) and (((((!ID10) or (!ID9)) or (!ID1)) or (!ID3)) or (!ID15))) and (((((!ID6) or (!ID8)) or (!ID12)) or (!ID15)) or (!ID0))) and (((((!ID10) or (!ID8)) or (!ID5)) or (!ID15)) or (!ID0))) and (((((!ID8) or (!ID12)) or (!ID3)) or (!ID15)) or (!ID0))) and (((((!ID9) or (!ID1)) or (!ID6)) or (!ID12)) or (!ID15))) and (((((!ID1) or (!ID12)) or (!ID7)) or (!ID5)) or (!ID15))) and (((((!ID14) or (!ID12)) or (!ID3)) or (!ID7)) or (!ID0))) and (((((!ID2) or (!ID11)) or (!ID3)) or (!ID7)) or (!ID15))) and (((((!ID9) or (!ID12)) or (!ID15)) or (!ID0)) or (!ID4))) and (((((!ID13) or (!ID9)) or (!ID12)) or (!ID0)) or (!ID4))) and (((((!ID8) or (!ID12)) or (!ID5)) or (!ID15)) or (!ID0))) and (((((!ID14) or (!ID6)) or (!ID12)) or (!ID7)) or (!ID0))) and (((((!ID2) or (!ID9)) or (!ID6)) or (!ID11)) or (!ID15))) and (((((!ID13) or (!ID12)) or (!ID7)) or (!ID0)) or (!ID4))) and (((((!ID13) or (!ID10)) or (!ID8)) or (!ID3)) or (!ID0))) and (((((!ID14) or (!ID10)) or (!ID9)) or (!ID6)) or (!ID0))) and (((((!ID9) or (!ID11)) or (!ID3)) or (!ID15)) or (!ID0))) and (((((!ID14) or (!ID9)) or (!ID12)) or (!ID3)) or (!ID0)))";
		Expression id = ExpressionParser.parseAsNewBooleanExpression(bigExp);
		CNFExpression res = CNFExprConverterNaive.instance.getCNFExprConverter().getCNF(id);
		System.out.println(res);
	}
}
