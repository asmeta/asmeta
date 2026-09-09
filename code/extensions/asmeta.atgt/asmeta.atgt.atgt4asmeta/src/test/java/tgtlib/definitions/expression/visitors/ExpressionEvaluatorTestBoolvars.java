package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.Variable;

public class ExpressionEvaluatorTestBoolvars {
	private static final String INCOMPL_M = "INCOMPLETE_MODEL";

	@BeforeAll
	static void initLogger() {
		Logger.getLogger(ExpressionEvaluator.class).setLevel(Level.DEBUG);
	}

	Expression expr;
	ExpressionEvaluator ev;
	String result;

	public void initExpressionEvaluatorTestBoolvars(String expression, String map,
			String result) throws ParseException {
		EnumConstCreator ecc = new EnumConstCreator();
		expr = ExpressionParser.parse(expression, ecc);
		Collection<BooleanVar> vars = IDExprCollector.getBoolVarsFromId(expr);
		String[] maps = map.split(",");
		Map<Variable, String> state = new HashMap<Variable, String>();
		for (Variable v : vars) {
			for (String assi : maps) {
				String[] sassi = assi.split("->");
				if (sassi[0].equals(v.getName())) {
					state.put(v, sassi[1]);
				}
			}

		}
		ev = new ExpressionEvaluator(state, state.keySet());
		this.result = result;
	}

	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				// constants
				{ "false", "", "false" },
				{ "true", "", "true" },
				// IDS
				{ "a", "", INCOMPL_M },
				{ "a", "a->true", "true" },
				{ "a", "a->false", "false" },
				{ "a", "b->false", INCOMPL_M },
				// EQ
				{ "a == a", "", "true" },
				{ "true == true", "", "true" },
				{ "true == false", "", "false" },
				{ "a == true", "a->true", "true" },
				{ "true == a", "a->true", "true" },
				{ "a == false", "a->true", "false" },
				{ "a == b", "", INCOMPL_M },
				{ "a == b", "a->true", INCOMPL_M },
				{ "a == b", "b->true", INCOMPL_M },
				{ "a == b", "a->false,b->false", "true" },
				// OR
				{ "a or b", "a->true", "true" },
				{ "a or b", "a->false,b->false", "false" },
				{ "a or b", "b->true", INCOMPL_M },
				// complex
				{ "(((false and true) or false) or e_1)",
						"e_1->false,e_0->true", "false" },
				{ "((not e_0 or e_1) and e_0)", "e_1->false,e_0->true", "false" },
				{
						"(((false and true) or false) or e_1) xor ((not e_0 or e_1) and e_0)",
						"e_1->false,e_0->true", "false" },
				{ "((e_1 and not e_1) or e_1) xor not(not e_1 or e_1)",
						"e_1->false", "false" },
						// complex with equals = and different
				{"not((e_1 xor e_0) or false)!=true","",INCOMPL_M},
				{"not((e_1 xor e_0) or false)!=true","e_0->false,e_1->true","true"},
				{"not((e_1 xor e_0) or false)==true","",INCOMPL_M},
				{"not((e_1 xor e_0) or false)==true","e_0->false,e_1->true","false"},
				{"not((true and e_1) xor e_2)","e_1->false,e_2->true","false"},
				{"not(e_0 xor e_1) and e_0","e_1->false,e_0->true,phi0->true","false"},
				{"phi0 and e_0","e_1->false,e_0->true,phi0->true","true"}
		};
		return Arrays.asList(data);
	}

	@MethodSource("data") @ParameterizedTest
	public void test(String expression, String map, String result) throws ParseException {
		initExpressionEvaluatorTestBoolvars(expression, map, result);
		try {
			Boolean res = ev.evaluate(expr);
			assertEquals(result, res.toString());
		} catch (ModelIncomplete e) {
			assertEquals(INCOMPL_M, result);
		}
	}
}
