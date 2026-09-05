package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.BooleanVar;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.Variable;

// test with enums
// switch1 and switch2 ON;OFF
public class ExpressionEvaluatorTestEnums {

	Expression expr;

	ExpressionEvaluator ev;
	
	String result;
	
	static EnumConstCreator ecc = new EnumConstCreator();
	
	static Collection<Variable> vars = new ArrayList<Variable>();
	
	static{
		EnumConst e = ecc.createEnumConst("ON");
		assertInstanceOf(EnumConst.class, e);
		EnumType onOf = new EnumType("sw");
		onOf.addElement(ecc.createEnumConst("OFF"));
		onOf.addElement(e);
		IdExpression v1 = ecc.createIdExpression("switch1", null);
		assertInstanceOf(IdExpression.class, v1);
		vars.add(newOnOfvariable(v1,onOf));
		vars.add(newOnOfvariable(ecc.createIdExpression("switch2", null),onOf));
	}

	public void initExpressionEvaluatorTestEnums(String expression, String map, String result) throws ParseException{		
		expr = ExpressionParser.parse(expression,ecc);
		Collection<BooleanVar> vars = IDExprCollector.getBoolVarsFromId(expr);
		String[] maps = map.split(",");
		Map<Variable, String> state = new HashMap<Variable, String>();
		for(Variable v:vars){
			for(String assi: maps){
				String[] sassi = assi.split("->");
				if (sassi[0].equals(v.getName())){
					state.put(v, sassi[1]);
				}
			}
			
		}
		ev = new ExpressionEvaluator(state);
		this.result = result;
	}

	private static Variable newOnOfvariable(final IdExpression v1,final Type type) {
		return new Variable() {	
			
			@Override
			public int hashCode() {
				// get the ID hashcode: two variable with the same ID must be the same
				return v1.hashCode();
			}

			@Override
			public boolean isControlled() {
				return false;
			}
			
			@Override
			public Expression getValue() {
				return null;
			}
			
			@Override
			public Type getType() {
				return type;
			}

			@Override
			public String getName() {
				return v1.getIdString();
			}

			@Override
			public IdExpression getIdExpression() {
				return v1;
			}
		};
	}

	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				// IDS
				{ "switch1", "", "error" },
				{ "ON", "", "error" },
				// EQ 
				{ "ON == ON", "", "true" }, 
				{ "ON == OFF", "", "false" }, 
				{ "switch1 == switch1", "", "true" }, 
				{ "switch1 == switch2", "", "error" }, 
				{ "switch1 == switch2", "switch1->ON", "error" }, 
				{ "switch1 == switch2", "switch2->ON", "error" }, 
				{ "switch1 == switch2", "switch1->OFF,switch2->ON", "false" }, 
				{ "switch1 == switch2", "switch1->OFF,switch2->OFF", "true" }, 
				{ "switch1 == ON", "", "error" }, 
				{ "switch1 == ON", "switch1->OFF", "false" }, 
				{ "switch1 == ON", "switch1->ON", "true" }, 
				{ "ON == switch1", "switch1->ON", "true" }, 
				// OR 
				{ "switch1 == ON or switch2 == OFF", "", "error" } 
		};
		return Arrays.asList(data);
	}

	@MethodSource("data") @ParameterizedTest
	public void test(String expression, String map, String result) throws ParseException {
		initExpressionEvaluatorTestEnums(expression, map, result);
		try{
			Boolean res = expr.accept(ev);
			assertEquals(result,res.toString());
		}catch (ModelIncomplete e){
			assertEquals("error",result);			
		}

	}

}
