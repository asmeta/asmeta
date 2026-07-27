package tgtlib.definitions.expression.type;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import tgtlib.definitions.expression.IdExpression;


/**
 */
public class EnumConstTest {
	
	@Test
	public void testGetBoolExpr() {
		EnumConstCreator ecc = new EnumConstCreator();
		assertSame(BoolType.FALSE_CONST,ecc.createIdExpression("false", null));
		assertSame(BoolType.FALSE_CONST,ecc.createIdExpression("FALSE", null));
		assertSame(BoolType.TRUE_CONST,ecc.createIdExpression("true", null));
		assertSame(BoolType.TRUE_CONST,ecc.createIdExpression("TRUE", null));
	}

	@Test
	public void testCreateEnumConst1() {
		EnumConstCreator ecc = new EnumConstCreator();
		EnumConst A = ecc.createEnumConst("A");
		EnumConst A2 = ecc.createEnumConst("A");
		assertSame(A, A2);		
	}
	@Test
	public void testCreateEnumConst2() {
		EnumConstCreator ecc = new EnumConstCreator();
		EnumConst A = ecc.createEnumConst("B");
		IdExpression A2 = ecc.createIdExpression("B", null);
		assertSame(A, A2);		
	}
	@Test(expected=RuntimeException.class)
	public void testCreateEnumConst3() {
		EnumConstCreator ecc = new EnumConstCreator();
		ecc.createIdExpression("C", null);
		ecc.createEnumConst("C");		
	}

}
