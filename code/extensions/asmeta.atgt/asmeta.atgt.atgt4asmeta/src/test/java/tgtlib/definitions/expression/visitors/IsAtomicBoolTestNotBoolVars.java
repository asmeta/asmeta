package tgtlib.definitions.expression.visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class IsAtomicBoolTestNotBoolVars {
	
	static EnumConstCreator ecc = new EnumConstCreator();
	
	@Test
	public void testBooleanExpressions() {
		EnumConst a1 = ecc.createEnumConst("a1");
		IdExpression a = ecc.createIdExpression("a", null);
		BinaryExpression expr = BinaryExpression.mkBinExpr(a, Operator.EQ, a1);
		Boolean actualResult = expr.accept(IsAtomicBool.isAtomicBool);
		assertEquals(true, actualResult);
	}

}
