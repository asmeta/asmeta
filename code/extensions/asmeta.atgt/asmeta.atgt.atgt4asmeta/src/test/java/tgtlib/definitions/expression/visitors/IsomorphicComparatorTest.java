package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class IsomorphicComparatorTest {

	@Test
	public void testCompareAnd() throws ParseException {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression a1 = ExpressionParser.parse("a and b", idcreator);
		Expression a2 = ExpressionParser.parse("b and a", idcreator);
		IsomorphicComparator comp = new IsomorphicComparator();
		assertTrue(comp.compare(a1, a2) == 0);
	}

	@Test
	public void testCompare1() throws ParseException {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression a1 = ExpressionParser.parse("(a and b) or c", idcreator);
		Expression a2 = ExpressionParser.parse("b and a or c", idcreator);
		IsomorphicComparator comp = new IsomorphicComparator();
		assertTrue(comp.compare(a1, a2) == 0);
	}
}