package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;

class ExpressionRenamerTest {

	private void test(String original, String prefix, String expOutput) throws ParseException {
		ExpressionRenamer renamer = new ExpressionRenamer(prefix);
		Expression exp = ExpressionParser.parse(original, new EnumConstCreator());
		String output = exp.accept(renamer).toString();
		assertEquals(expOutput, output);
	}

	@Test void test1() throws Exception {
		test("a and b", "PRE", "PREa and PREb");
	}

	@Test void test2() throws Exception {
		test("a and b or c", "PRE", "(PREa and PREb) or PREc");
	}

	@Test void test3() throws Exception {
		test("a implies c", "PRE", "PREa implies PREc");
	}
}