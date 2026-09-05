package tgtlib.definitions.expression.visitors;


import tgtlib.definitions.expression.Expression;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.type.EnumConstCreator;

class IsomorphicComparatorTest {

	@Test void compareAnd() throws Exception {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression a1 = ExpressionParser.parse("a and b", idcreator);
		Expression a2 = ExpressionParser.parse("b and a", idcreator);
		IsomorphicComparator comp = new IsomorphicComparator();
		assertEquals(0, comp.compare(a1, a2));
	}

	@Test void compare1() throws Exception {
		EnumConstCreator idcreator = new EnumConstCreator();
		Expression a1 = ExpressionParser.parse("(a and b) or c", idcreator);
		Expression a2 = ExpressionParser.parse("b and a or c", idcreator);
		IsomorphicComparator comp = new IsomorphicComparator();
		assertEquals(0, comp.compare(a1, a2));
	}
}