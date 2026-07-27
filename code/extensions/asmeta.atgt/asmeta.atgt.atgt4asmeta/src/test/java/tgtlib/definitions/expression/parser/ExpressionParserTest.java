package tgtlib.definitions.expression.parser;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumConstCreator;

// non boolean exressions
public class ExpressionParserTest {

	@Test
	public void testParseNumbers() throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parse("-3",ecc);
		assertTrue(e instanceof NegExpression);
		assertTrue(((NegExpression)e).getOperand() instanceof NumericLiteral);
		
	}
	
	@Test
	public void testParse2Booleans() throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertTrue(e instanceof IdExpression);
		Expression e2 = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertSame(e, e2);
	}
	@Test
	public void testParseReparseOK() throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertTrue(e instanceof IdExpression);
		Expression e2 = ExpressionParser.parse("a",ecc);
		assertSame(e, e2);
	}
	@Test(expected=RuntimeException.class)
	public void testParseReparseWrong() throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		Expression e = ExpressionParser.parseAsBooleanExpression("a",ecc);
		assertTrue(e instanceof IdExpression);
		IdExpression e2 = ecc.createIdExpression("a", new BoundType("t",3,5));
	}
	@Test
	public void testParseNotNot() throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		// negation expressions
		// fix the parser !!!
		Expression e = ExpressionParser.parseAsBooleanExpression("not not UML",ecc);
		assertTrue(e instanceof IdExpression);
		IdExpression e2 = ecc.createIdExpression("a", new BoundType("t",3,5));
	}
	
	

}
