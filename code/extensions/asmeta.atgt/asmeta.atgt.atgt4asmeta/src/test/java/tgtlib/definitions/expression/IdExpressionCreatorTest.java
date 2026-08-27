package tgtlib.definitions.expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 */
public class IdExpressionCreatorTest {

	@Test
	public void testCreateNewIdExpression() {
		IdExpression A1 = IdExpressionCreator.createNewIdExpression("a");
		IdExpression A2 = IdExpressionCreator.createNewIdExpression("a");
		assertNotSame(A1,A2);
	}

	@Test
	public void testCreateIdExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("a", null);
		IdExpression A2 = icc.createIdExpression("a", null);
		assertSame(A1,A2);
	}
	@Test(expected=AssertionError.class)
	public void testCreateBoolExpression(){
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("true", null);
	}


	@Test
	public void testCreateNumIntExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("3", null);
		assertTrue(A1 instanceof NumericLiteral);		
		assertEquals("3",A1.getIdString());
	}

	@Test
	public void testCreateNumFloatExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("3.0", null);
		assertTrue(A1.getClass().getName(), A1 instanceof NumericLiteral);		
		assertEquals("3.0",A1.getIdString());
	}

	
	
}
