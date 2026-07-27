package tgtlib.definitions.expression.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.visitors.IDExprCollector;

public class BooleanExpressionParserTest {


	@Test
	public void testIff() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a iff b");
		assertTrue(e.getClass().getSimpleName(), e instanceof BinaryExpression);
	}

	@Test
	public void testNotID() throws ParseException {
		// not id
		Expression e = ExpressionParser.parseAsNewBooleanExpression("!A");
		assertTrue(e instanceof IdUNotIdExpression);
	}

	
	@Test
	public void testParseExpressionOr() throws ParseException {
		String aOrB = "a or b";
		Expression e = ExpressionParser.parseAsNewBooleanExpression(aOrB);
		assertEquals(aOrB, e.toString());
	}

	@Test
	public void testParseExpressionImplies() throws ParseException {
		String aImpliesB = "a implies b";
		Expression e = ExpressionParser.parseAsNewBooleanExpression(aImpliesB);
		assertEquals(aImpliesB, e.toString());
	}

	@Test
	public void testParseExpressionImplies2() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a implies b or c");
		assertEquals("a implies (b or c)", e.toString());
	}
	@Test
	public void testParseExpressionEqual() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> c");
		assertTrue(e instanceof EqualsExpression);
		assertEquals("a = c", e.toString());
	}
	@Test
	public void testParseExpressionEqual2() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> (b or c)");
		assertEquals("a = (b or c)", e.toString());
	}

	@Test
	public void testParseExpressionEqual3() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> b or c");
		assertEquals("a = (b or c)", e.toString());
	}
	
	
	// if a write a wrong expression, it should rise an exception
	@Test(expected=ParseException.class)
	public void testParseExpression2() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a b");
	}

	// parse two or more expressions
	@Test
	public void testParseMoreExpressions() throws ParseException {
		EnumConstCreator idcreator = new EnumConstCreator(); 
		Expression e = ExpressionParser.parseAsBooleanExpression("e",idcreator);
		Expression e2 = ExpressionParser.parseAsBooleanExpression("e",idcreator);
		assertTrue(e instanceof IdExpression);
		assertSame(e, e2);
	}
	@Test
	public void testmoreExpression() throws ParseException {
		  EnumConstCreator idc = new EnumConstCreator();
		  Expression expr = ExpressionParser.parseAsBooleanExpression("(a and b) and a", idc);
		  Set<BooleanVar> ids = new TreeSet<>(IDExprCollector.getBoolVarsFromId(expr));
		  Expression expr2 = ExpressionParser.parseAsBooleanExpression("(a and b) and a",idc);
		  ids.addAll(IDExprCollector.getBoolVarsFromId(expr2));
		  assertEquals(2,ids.size());
	 }
	
}
