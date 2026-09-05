package tgtlib.definitions.normalform.dnf;

import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

class DNFExprConverterTest {


	// read this expression which was problematic 
	/**
	 * Method testexr1.
	 * @throws ParseException
	 */
	@Test void testexr1() throws Exception {		
		Expression expr = ExpressionParser.parseAsNewBooleanExpression("((not(a && b) && ((((d && not e) && not f) || ((not d && e) && not f)) || ((not d && not e) && not f))) && (((((a && c) && (d || e)) && h) || ((a && (d || e)) && not h)) || (b && (e || f))))))");
		DNFExpression res = DNFExprConverter.getDNF(expr);
		System.out.println(res.toString());
	}

}
