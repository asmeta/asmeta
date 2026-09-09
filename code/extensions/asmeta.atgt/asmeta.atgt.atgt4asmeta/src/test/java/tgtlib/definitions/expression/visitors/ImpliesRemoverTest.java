package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

class ImpliesRemoverTest {

	@Test void test1() {
		String e = "a implies b";
		check(e,"not a or b");
	}

	@Test void test2() {
		String e = "not(a implies b)";
		check(e,"not(not a or b)");
	}

	private void check(String e1, String e2){
		try {
			Expression ee1 = ExpressionParser.parseAsNewBooleanExpression(e1);
			Expression ee2 = ExpressionParser.parseAsNewBooleanExpression(e2);
			Expression ee1e = ee1.accept(ImpliesRemover.instance);
			assertFalse(ee1e.toString().contains("implies"));
			assertEquals(ee2, ee1e);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}
}
