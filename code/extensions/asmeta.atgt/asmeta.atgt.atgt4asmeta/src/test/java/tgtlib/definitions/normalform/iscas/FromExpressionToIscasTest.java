package tgtlib.definitions.normalform.iscas;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;

class FromExpressionToIscasTest {

	@BeforeAll
	static void setUpLogging() {
		Logger.getLogger(FromExpressionToIscas.class).setLevel(Level.DEBUG);
	}

	@Test void forXOrExpressionSimple() throws Exception {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("e_1 xor (not e_1)");
		fii.getBits(e);
		String iscas = fii.getIscas();
		//System.out.println(iscas);
		assertTrue(iscas.contains("xor0 = XOR(e_1, not_e_1)"));
	}

	@Test void forOrExpression() throws Exception {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not(not e_1 or e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}

	@Test void forAndExpression() throws Exception {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(e_1 and not e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}


	@Test void forXOrExpression() throws Exception {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(e_1 and not e_1) xor not(not e_1 or e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}
	
}
