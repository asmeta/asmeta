package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BooleanVar;

// see module atgt_boolean becuase it uses parser
public class IDExprCollectorTest {

	@Test
	public void test1() throws ParseException {
		// repeated id
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or not a");
		List<IdExpression> l = IDExprCollector.getIdsAsList(e);
		assertEquals(1,l.size());
	}

	@Test
	public void testTF() throws ParseException {
		// repeated id
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or true");
		List<IdExpression> l = IDExprCollector.getIdsAsList(e);
		assertEquals(1,l.size());
	}

	
	@Test
	public void test2() throws ParseException {
		Expression e1 = ExpressionParser.parseAsNewBooleanExpression("a or b");
		Expression e2 = ExpressionParser.parseAsNewBooleanExpression("a or b");
		Collection<IdExpression> l = IDExprCollector.collectIds(Arrays.asList(e1,e2));
		assertEquals(2,l.size());
	}

	@Test
	public void test3() throws ParseException {
		Expression e1 = ExpressionParser.parseAsNewBooleanExpression("a or b");
		Collection<BooleanVar> l1 = IDExprCollector.getBoolVarsFromId(e1);
		Expression e2 = ExpressionParser.parseAsNewBooleanExpression("a or b");
		Collection<BooleanVar> l2 = IDExprCollector.getBoolVarsFromId(e2, l1);
		assertEquals(0,l2.size());
	}

	
	@Test
	public void test4() throws ParseException {
		Expression e1 = ExpressionParser.parseAsNewBooleanExpression("a or b");
		Collection<BooleanVar> l1 = IDExprCollector.getBoolVarsFromId(e1);
		Expression e2 = ExpressionParser.parseAsNewBooleanExpression("a or b or c");
		Collection<BooleanVar> l2 = IDExprCollector.getBoolVarsFromId(e2, l1);
		assertEquals(1,l2.size());
	}

	@Test
	public void test5TF() throws ParseException {
		Expression e1 = ExpressionParser.parseAsNewBooleanExpression("a or true");
		Collection<BooleanVar> l1 = IDExprCollector.getBoolVarsFromId(e1);
		assertEquals(1,l1.size());
	}

}
