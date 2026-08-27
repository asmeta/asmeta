package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class ExpressionRenamerTest {

	private void test(String original, String prefix, String expOutput) throws ParseException {
		ExpressionRenamer renamer = new ExpressionRenamer(prefix);
		Expression exp = ExpressionParser.parse(original, new EnumConstCreator());
		String output = exp.accept(renamer).toString();
		assertEquals(expOutput, output);
	}

	@Test
	public void test1() throws ParseException {
		test("a and b", "PRE", "PREa and PREb");
	}

	@Test
	public void test2() throws ParseException {
		test("a and b or c", "PRE", "(PREa and PREb) or PREc");
	}

	@Test
	public void test3() throws ParseException {
		test("a implies c", "PRE", "PREa implies PREc");
	}
}