package tgtlib.definitions.expression.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import tgtlib.definitions.expression.BinaryExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.visitors.IDExprCollector;

class BooleanExpressionParserTest {


	@Test void iff() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a iff b");
		assertTrue(e instanceof BinaryExpression, e.getClass().getSimpleName());
	}

	@Test void notID() throws Exception {
		// not id
		Expression e = ExpressionParser.parseAsNewBooleanExpression("!A");
		assertInstanceOf(IdUNotIdExpression.class, e);
	}


	@Test void parseExpressionOr() throws Exception {
		String aOrB = "a or b";
		Expression e = ExpressionParser.parseAsNewBooleanExpression(aOrB);
		assertEquals(aOrB, e.toString());
	}

	@Test void parseExpressionImplies() throws Exception {
		String aImpliesB = "a implies b";
		Expression e = ExpressionParser.parseAsNewBooleanExpression(aImpliesB);
		assertEquals(aImpliesB, e.toString());
	}

	@Test void parseExpressionImplies2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a implies b or c");
		assertEquals("a implies (b or c)", e.toString());
	}

	@Test void parseExpressionEqual() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> c");
		assertInstanceOf(EqualsExpression.class, e);
		assertEquals("a = c", e.toString());
	}

	@Test void parseExpressionEqual2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> (b or c)");
		assertEquals("a = (b or c)", e.toString());
	}

	@Test void parseExpressionEqual3() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a <=> b or c");
		assertEquals("a = (b or c)", e.toString());
	}


	// if a write a wrong expression, it should rise an exception
	@Test void parseExpression2() {
		assertThrows(ParseException.class, () -> {
			Expression e = ExpressionParser.parseAsNewBooleanExpression("a b");
		});
	}

	// parse two or more expressions
	@Test void parseMoreExpressions() throws Exception {
		EnumConstCreator idcreator = new EnumConstCreator(); 
		Expression e = ExpressionParser.parseAsBooleanExpression("e",idcreator);
		Expression e2 = ExpressionParser.parseAsBooleanExpression("e",idcreator);
		assertInstanceOf(IdExpression.class, e);
		assertSame(e, e2);
	}

	@Test void testmoreExpression() throws Exception {
		  EnumConstCreator idc = new EnumConstCreator();
		  Expression expr = ExpressionParser.parseAsBooleanExpression("(a and b) and a", idc);
		  Set<BooleanVar> ids = new TreeSet<>(IDExprCollector.getBoolVarsFromId(expr));
		  Expression expr2 = ExpressionParser.parseAsBooleanExpression("(a and b) and a",idc);
		  ids.addAll(IDExprCollector.getBoolVarsFromId(expr2));
		  assertEquals(2,ids.size());
	 }
	
}
