package tgtlib.definitions.normalform.cnf;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;

// Tesitin via ISCAS
class CNFExprConverterTseitinTest {

	@Test void forTsietinXorExpression() throws Exception {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("(not x || (a xor b)) and (not (a xor b) || x)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("[ab, ~b~a]", res.toString());
	}

	@Test void forTsiet2AndExpression() throws Exception {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("(a and b)|| (a and b)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("", res.toString());
	}

	@Test void forTsiet2NotAndExpression() throws Exception {		
		Expression axorb = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		CNFExpression res = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(axorb);
		// TODO
		assertEquals("", res.toString());
	}

	@Test void withAndExpression2() throws Exception{
		Expression x = ExpressionParser.parseAsNewBooleanExpression("a && not b");
		CNFExpression f = CNFExprConverterTseitinViaIscas.instance.getCNFExprConverter().getCNF(x);
		assertEquals("[a~and0, ~b~and0, ~aband0, and0]", f.toString());
	}

	

}
