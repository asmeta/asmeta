package tgtlib.definitions.expression;

import static org.junit.Assert.assertSame;

import org.junit.Test;


public class IdExpressionTest {

	@Test
	public void testCreateIdExpression() {
		//FIXME 
		assertSame(new IdExpression("a", null),new IdExpression("a", null));
	}

	
	@Test
	public void testCreateIdExpressionCC() {
		IdExpressionCreator icc = new IdExpressionCreator();
		//FIXME 
		assertSame(icc.createIdExpression("a", null),icc.createIdExpression("a", null));
	}

}
