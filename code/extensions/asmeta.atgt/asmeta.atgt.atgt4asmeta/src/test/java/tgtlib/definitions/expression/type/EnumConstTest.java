package tgtlib.definitions.expression.type;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


import tgtlib.definitions.expression.IdExpression;

import org.junit.jupiter.api.Test;


/**
 */
class EnumConstTest {

	@Test void getBoolExpr() {
		EnumConstCreator ecc = new EnumConstCreator();
		assertSame(BoolType.FALSE_CONST,ecc.createIdExpression("false", null));
		assertSame(BoolType.FALSE_CONST,ecc.createIdExpression("FALSE", null));
		assertSame(BoolType.TRUE_CONST,ecc.createIdExpression("true", null));
		assertSame(BoolType.TRUE_CONST,ecc.createIdExpression("TRUE", null));
	}

	@Test void createEnumConst1() {
		EnumConstCreator ecc = new EnumConstCreator();
		EnumConst A = ecc.createEnumConst("A");
		EnumConst A2 = ecc.createEnumConst("A");
		assertSame(A, A2);		
	}

	@Test void createEnumConst2() {
		EnumConstCreator ecc = new EnumConstCreator();
		EnumConst A = ecc.createEnumConst("B");
		IdExpression A2 = ecc.createIdExpression("B", null);
		assertSame(A, A2);		
	}

	@Test void createEnumConst3() {
		EnumConstCreator ecc = new EnumConstCreator();
		ecc.createIdExpression("C", null);
		assertThrows(RuntimeException.class, () ->
			ecc.createEnumConst("C"));
	}

}
