package tgtlib.definitions.normalform.cnf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

// Tesitin via ISCAS
public class CNFExprConverterTseitinTest {

	@Test
	public void testForTsietinXorExpression() throws ParseException {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("(not x || (a xor b)) and (not (a xor b) || x)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("[ab, ~b~a]", res.toString());
	}

	@Test
	public void testForTsiet2AndExpression() throws ParseException {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("(a and b)|| (a and b)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("", res.toString());
	}
	@Test
	public void testForTsiet2NotAndExpression() throws ParseException {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("", res.toString());
	}
	
	@Test
	public void withAndExpression2() throws ParseException{
		Expression x = ExpressionParser.parseAsNewBooleanExpression("a && not b");
		CNFExpression f = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(x);
		assertEquals("[a~and0, ~b~and0, ~aband0, and0]", f.toString());
	}

	

}
