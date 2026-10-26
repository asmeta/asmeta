package atgt.translator;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.definitions.expression.CaseExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;

class ExpressionToSPINVisitorTest {

	@Test void forCaseExpression() {
		EnumConstCreator icc = new EnumConstCreator();
		EnumConst A = icc.createEnumConst("A");
		EnumConst B = icc.createEnumConst("B");
		EnumConst C = icc.createEnumConst("C");
		EnumType et  = new EnumType("TEMP", A,B,C);
		IdExpression comparedTermAsExp = icc.createIdExpression("x", et);
		// case x : A -> 0 , B -> 1
		CaseExpression caseExpression = new CaseExpression(comparedTermAsExp);
		caseExpression.addCase(A, icc.createIdExpression("0", null));
		caseExpression.addCase(B, icc.createIdExpression("1", null));
		caseExpression.addCase(C, icc.createIdExpression("2", null));
		StringBuffer result = ExpressionToSPINVisitor.SINGLETON.forCaseExpression(caseExpression);
		System.out.println(result);
		assertEquals("(x == A -> 0 : (x == B -> 1 : (2)))", result.toString());
	}

}
