package extgt.coverage.combinatorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.util.Pair;

class CombinatorialCovBuilderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test void makeEqExpression() {
		EnumConstCreator iec = new EnumConstCreator();
		IdExpression var = iec.createIdExpression("A", null);
		EnumConst val = iec.createEnumConst("1");
		// test pairs
		Pair<IdExpression, EnumConst> p1 = new Pair<IdExpression, EnumConst>(var,val);
		Pair<IdExpression, EnumConst> p2 = new Pair<IdExpression, EnumConst>(var,val);
		assertEquals(p1, p2);
		assertEquals(p1.hashCode(), p2.hashCode());
		//
		Expression eq1 = CombinatorialCovBuilder.makeEqExpression(var, val);
		Expression eq2 = CombinatorialCovBuilder.makeEqExpression(var, val);
		assertSame(eq1, eq2);
	}

	@Test void makeAndExpression() {
		fail("Not yet implemented");
	}

}
