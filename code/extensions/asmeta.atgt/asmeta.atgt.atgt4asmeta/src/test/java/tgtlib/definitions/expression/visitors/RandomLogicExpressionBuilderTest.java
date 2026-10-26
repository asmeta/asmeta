package tgtlib.definitions.expression.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;

class RandomLogicExpressionBuilderTest {


	@Test void next1() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a && (b || c)");
		//
		List<IdExpression> IDS = IDExprCollector.getIdsAsList(e);
		assertNotNull(IDS.get(0));
		assertNotNull(IDS.get(1));
		assertNotNull(IDS.get(2));
		assertEquals(3, IDS.size());
		RandomLogicExpressionBuilder rndb = new RandomLogicExpressionBuilder(IDS,3, false);		
		for(int i = 0; i < 20 ; i++){
			assertTrue(rndb.hasNext());
			Expression ne = rndb.next();
		}
	}

}
