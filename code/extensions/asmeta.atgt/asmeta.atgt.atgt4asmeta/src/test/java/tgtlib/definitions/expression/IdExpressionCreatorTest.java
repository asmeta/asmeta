package tgtlib.definitions.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 */
class IdExpressionCreatorTest {

	@Test void createNewIdExpression() {
		IdExpression A1 = IdExpressionCreator.createNewIdExpression("a");
		IdExpression A2 = IdExpressionCreator.createNewIdExpression("a");
		assertNotSame(A1,A2);
	}

	@Test void createIdExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("a", null);
		IdExpression A2 = icc.createIdExpression("a", null);
		assertSame(A1,A2);
	}

	@Test void createBoolExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		assertThrows(AssertionError.class, () -> {
			IdExpression A1 = icc.createIdExpression("true", null);
		});
	}


	@Test void createNumIntExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("3", null);
		assertInstanceOf(NumericLiteral.class, A1);		
		assertEquals("3",A1.getIdString());
	}

	@Test void createNumFloatExpression() {
		IdExpressionCreator icc = new IdExpressionCreator();
		IdExpression A1 = icc.createIdExpression("3.0", null);
		assertTrue(A1 instanceof NumericLiteral, A1.getClass().getName());		
		assertEquals("3.0",A1.getIdString());
	}

	
	
}
