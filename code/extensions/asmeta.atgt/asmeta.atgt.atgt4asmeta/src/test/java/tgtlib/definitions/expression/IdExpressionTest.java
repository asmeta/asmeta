package tgtlib.definitions.expression;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;


class IdExpressionTest {

	@Test void createIdExpression() {
		//FIXME - one should not use this constructor
		assertNotSame(new IdExpression("a", null),new IdExpression("a", null));
	}


	@Test void createIdExpressionCC() {
		IdExpressionCreator icc = new IdExpressionCreator();
		//FIXME 
		assertSame(icc.createIdExpression("a", null),icc.createIdExpression("a", null));
	}

}
