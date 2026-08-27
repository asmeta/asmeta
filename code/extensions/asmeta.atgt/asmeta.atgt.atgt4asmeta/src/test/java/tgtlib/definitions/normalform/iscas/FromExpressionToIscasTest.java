package tgtlib.definitions.normalform.iscas;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class FromExpressionToIscasTest {
	
	@BeforeClass
	public static void setUpLogging() {
		Logger.getLogger(FromExpressionToIscas.class).setLevel(Level.DEBUG);
	}

	@Test
	public void testForXOrExpressionSimple() throws ParseException {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("e_1 xor (not e_1)");
		fii.getBits(e);
		String iscas = fii.getIscas();
		//System.out.println(iscas);
		assertTrue(iscas.contains("xor0 = XOR(e_1, not_e_1)"));
	}

	@Test
	public void testForOrExpression() throws ParseException {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not(not e_1 or e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}

	@Test
	public void testForAndExpression() throws ParseException {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(e_1 and not e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}

	
	@Test
	public void testForXOrExpression() throws ParseException {
		FromExpressionToIscas fii = new FromExpressionToIscas();
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(e_1 and not e_1) xor not(not e_1 or e_1)");
		fii.getBits(e);
		System.out.println(fii.getIscas());
	}
	
}
