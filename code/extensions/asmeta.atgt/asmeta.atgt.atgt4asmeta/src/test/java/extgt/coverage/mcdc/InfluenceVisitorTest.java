package extgt.coverage.mcdc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tgtlib.definitions.NamedTerm;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.util.Pair;

class InfluenceVisitorTest {

	@Test void forIdExpression() {
		IdExpressionCreator idcc = new IdExpressionCreator();
		IdExpression id = idcc.createIdExpression("a", null);
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = id.accept(BoolDerivativeVisitor.instance);
		assertEquals("<a,<T,true>,<F,false>>",toString(result.get(0)));
		assertEquals(1,result.size());
	}

	@Test void forNotExpression1() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		assertEquals("<a,<T,not true>,<F,not false>>",toString(result.get(0)));
		assertEquals(1,result.size());
	}

	@Test void forNotExpression2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not (a and b)");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		assertEquals("<a,<T,not(true and b)>,<F,not(false and b)>>",toString(result.get(0)));
		assertEquals("<b,<T,not(a and true)>,<F,not(a and false)>>",toString(result.get(1)));
		assertEquals(2,result.size());
	}

	private String toString(Pair<IdExpression, Pair<NamedTerm, NamedTerm>> p) {
		String toString = "";
		toString += "<" + p.getFirst() + ",";
		toString += "<"+ p.getSecond().getFirst().getName() + "," + p.getSecond().getFirst().getCondition() + ">," ;
		toString += "<"+ p.getSecond().getSecond().getName() + "," + p.getSecond().getSecond().getCondition() + ">" ;
		toString += ">";
		return toString;
	}


	@Test void forAndExpression() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a and b");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		//assertEquals("[<1T, 1F>, <2T, 2F>]",result.toString());
		assertEquals("<a,<T,true and b>,<F,false and b>>",toString(result.get(0)));
		assertEquals("<b,<T,a and true>,<F,a and false>>",toString(result.get(1)));
		assertEquals(2,result.size());
		assertEquals("a", result.get(0).getFirst().toString());
		//assertEquals("<1T,1F>", result.get(0).getSecond().toString());
		assertEquals("<T: true and b, F: false and b>", result.get(0).getSecond().toString());
		assertEquals("true and b", result.get(0).getSecond().getFirst().getCondition().toString());
		assertEquals("false and b", result.get(0).getSecond().getSecond().getCondition().toString());
		//assertEquals("true and b", resultI.next().getCondition().toString());		
		//assertEquals("false and b", resultI.next().getCondition().toString());		
		//assertEquals("a and true", resultI.next().getCondition().toString());		
		//assertEquals("a and false", resultI.next().getCondition().toString());		
	}

	@Test void forXorExpression() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a xor b");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		//assertEquals("[<1T, 1F>, <2T, 2F>]",result.toString());
		assertEquals("<a,<T,true xor b>,<F,false xor b>>",toString(result.get(0)));
		assertEquals("<b,<T,a xor true>,<F,a xor false>>",toString(result.get(1)));
		assertEquals(2,result.size());
	}


	@Test void forOrExpression() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or b");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		assertEquals("<a,<T,true or b>,<F,false or b>>",toString(result.get(0)));
		assertEquals("<b,<T,a or true>,<F,a or false>>",toString(result.get(1)));
		assertEquals(2,result.size());
	}

	@Test void forOrExpression2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or b or c");
		List<Pair<IdExpression, Pair<NamedTerm, NamedTerm>>> result = e.accept(BoolDerivativeVisitor.instance);
		assertEquals("<a,<T,(true or b) or c>,<F,(false or b) or c>>",toString(result.get(0)));
		assertEquals("<b,<T,(a or true) or c>,<F,(a or false) or c>>",toString(result.get(1)));
		assertEquals("<c,<T,(a or b) or true>,<F,(a or b) or false>>",toString(result.get(2)));
		assertEquals(3,result.size());
	}

}
