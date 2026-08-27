package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class RandomLogicExpressionBuilderTest {


	@Test
	public void testNext1() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a && (b || c)");
		//
		List<IdExpression> IDS = IDExprCollector.getIdsAsList(e);
		assertNotNull(IDS.get(0));
		assertNotNull(IDS.get(1));
		assertNotNull(IDS.get(2));
		assertTrue(IDS.size() == 3);
		RandomLogicExpressionBuilder rndb = new RandomLogicExpressionBuilder(IDS,3, false);		
		for(int i = 0; i < 20 ; i++){
			assertTrue(rndb.hasNext());
			Expression ne = rndb.next();
		}
	}

}
