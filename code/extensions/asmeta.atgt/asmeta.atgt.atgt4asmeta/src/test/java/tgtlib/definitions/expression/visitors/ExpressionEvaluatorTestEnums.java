package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.location.AsmVariable;

// test with enums
// switch1 and switch2 ON;OFF
public class ExpressionEvaluatorTestEnums {
	
	static EnumConstCreator ecc = new EnumConstCreator();
	
	static Collection<AsmVariable> allvars = new ArrayList<>();
	
	final static EnumType ONOFFTYPE;
	
	static{
		// define types and variables
		EnumConst e = ecc.createEnumConst("ON");
		assertInstanceOf(EnumConst.class, e);
		ONOFFTYPE = new EnumType("SWITCH TYPE");
		ONOFFTYPE.addElement(ecc.createEnumConst("OFF"));
		ONOFFTYPE.addElement(e);
		// variables
		IdExpression v1 = ecc.createIdExpression("switch1", ONOFFTYPE);
		assertInstanceOf(IdExpression.class, v1);
		assertFalse(v1 instanceof EnumConst, "v1 is not an enum const");
		allvars.add(new AsmVariable(v1,null));
		IdExpression idExpression = ecc.createIdExpression("switch2", ONOFFTYPE);
		allvars.add(new AsmVariable(idExpression,null));	
	}

	private Map<Variable, String> buildState(String map, Collection<tgtlib.definitions.expression.type.Variable> vars) {
		String[] maps = map.split(",");
		//System.err.println("state=" + Arrays.asList(maps));
		//System.err.println("vars=" + Arrays.asList(vars));
		Map<Variable, String> state = new HashMap<Variable, String>();
		for (Variable v : vars) {
			for (String assi : maps) {
				String[] sassi = assi.split("->");
				if (sassi[0].equals(v.getName())) {
					state.put(v, sassi[1]);
				}
			}

		}
		//System.err.println("state=" + state);
		return state;
	}

	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				// IDS - cannot be computed
				{ "switch1", "", "error" },
				{ "ON", "", "error" },
				// EQ between enum constants
				{ "ON == ON", "", "true" }, 
				{ "ON == OFF", "", "false" },
				// EQ between IDS and variables
				{ "switch1 == switch1", "", "true" }, 
				{ "switch1 == switch2", "", "modelincomplete" }, 
				{ "switch1 == switch2", "switch1->ON", "modelincomplete" }, 
				{ "switch1 == switch2", "switch2->ON", "modelincomplete" }, 
				{ "switch1 == switch2", "switch1->OFF,switch2->ON", "false" }, 
				{ "switch1 == switch2", "switch1->OFF,switch2->OFF", "true" }, 
				{ "switch1 == ON", "", "modelincomplete" }, 
				{ "switch1 == ON", "switch1->OFF", "false" }, 
				{ "switch1 == ON", "switch1->ON", "true" }, 
				{ "ON == switch1", "switch1->ON", "true" },
				// OR 
				{ "switch1 == ON or switch2 == OFF", "", "modelincomplete" },
				{ "switch1 == ON or switch2 == OFF", "switch1->ON", "true" },
				{ "switch1 == ON or switch2 == OFF", "switch1->OFF,switch2->OFF", "true" },
				{ "switch1 == ON or switch2 == OFF", "switch1->OFF,switch2->ON", "false" }
		};
		return Arrays.asList(data);
	}

	@MethodSource("data") @ParameterizedTest
	public void test(String expression, String map, String result) throws ParseException {
		ExpressionEvaluator ev = null;
		Map<Variable, String> state = null;
		Expression expr = null;
		try {
			// get the expression with the parser (it can be something like "switch1 == ON" or "switch1 == switch2", but also "ON" (wrong))
			expr = ExpressionParser.parse(expression,ecc);
			// get the (enum) variables from the expression
			Collection<tgtlib.definitions.expression.type.Variable> vars = getEnumVarsFromId(expr);
			// build the state from the map
			state = buildState(map, vars);
			//System.err.println("test " + expression + " with state map " + state.toString() + " and expected result " + result);
			assertNotNull(state);
			ev = new ExpressionEvaluator(state);
			Boolean res = expr.accept(ev);
			// not error is expected, so check the result of the evaluation
			assertFalse(result.equals("error"), "error not expected in test " + expression + " with state " + map +":" + state);
			assertEquals(result, res.toString());		
		} catch (ModelIncomplete e) {
			// first case, the model is incomplete, so the result must be "modelincomplete"
			assertEquals("modelincomplete", result, " error for " + expression + " with state map " + state.toString());
		} catch (Throwable e) {
			e.printStackTrace();
			assertEquals("error", result, " error for " + expression + " with state map " + state);
		}
	}

	
	private static List<tgtlib.definitions.expression.type.Variable> getEnumVarsFromId(Expression e) {
		assert allvars!= null;
		Collection<IdExpression> ids = e.accept(IDExprCollector.instance);
		//System.err.println("getEnumVarsFromId: ids=" + ids);
		// some id is ids have no type 
		List<tgtlib.definitions.expression.type.Variable>  result = new ArrayList<>(allvars);		
		//
		buildNewVar: for(final IdExpression id : ids){
			//System.err.println("id=" + id + " type=" + id.getType() + " " + id.getClass());
			// skip enum constants, they are not variables
			if (id instanceof EnumConst)
				continue;
			// check if already defined in allvars
			for (Variable v: allvars){
				if (v.getName().equals(id.getIdString())) 
					continue buildNewVar;
			}
			//System.err.println("new variable found: " + id + " type=" + id.getType() + " " + id.getClass());
			assert false : "new variable found: " + id + " type=" + id.getType() + " " + id.getClass();
		}
		return result;
	}

	
}
