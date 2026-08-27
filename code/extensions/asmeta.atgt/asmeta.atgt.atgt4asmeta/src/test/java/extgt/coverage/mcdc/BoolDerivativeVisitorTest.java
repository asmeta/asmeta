package extgt.coverage.mcdc;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.util.Pair;

@RunWith(Parameterized.class)
public class BoolDerivativeVisitorTest {

	private String expression;
	
	Map<String, Pair<Expression,Expression>> results;
	
	EnumConstCreator ecc = new EnumConstCreator();
	
	public BoolDerivativeVisitorTest(String expression, String r) throws ParseException{
		this.expression = expression;
		results = new HashMap<>();
		String[] maps = r.split(";");
		for(String map: maps){
			int indexOf = map.indexOf("->");
			String var = map.substring(0, indexOf);
			// read the expected expressions
			String[] esp = map.substring(indexOf+2).split(",");
			Expression et = ExpressionParser.parse(esp[0],ecc);
			Expression ef = ExpressionParser.parse(esp[1],ecc);
			Pair<Expression,Expression> expxvar = new Pair<Expression, Expression>(et, ef);
			results.put(var,expxvar);
		}
	}
	
	@Parameters
	public static Collection regExValues() {
	 return Arrays.asList(new Object[][] {
	  {"a", "a->true,false"},
	  {"a and b", "a->true and b,false and b;b->a and true,a and false"},
	 });
	}

	
	@Test
	public void test() throws ParseException {
		// read the expression
		Expression e = ExpressionParser.parse(expression,ecc);
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		for(Pair<IdExpression, Pair<NamedTerm, NamedTerm>> p: result){
			Expression var = p.getFirst();
			System.out.println(result);
			Pair<Expression, Expression> pair = results.get(var.toString());
			System.out.println(pair + "" + results);
			assertEquals(pair.getFirst(),p.getSecond().getFirst().getCondition());
			assertEquals(pair.getSecond(),p.getSecond().getSecond().getCondition());
		}
		
	}

}
