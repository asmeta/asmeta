package extgt.coverage.fault.mutators.foms;

import static extgt.coverage.fault.mutators.foms.MissingSubExpressionFault.MSF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import extgt.coverage.fault.mutators.FaultTest;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.util.Pair;

class MissingSubExpressionFaultTest extends FaultTest {

	@Test void test1() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(a or b) and b");
		List<Pair<Integer, Expression>> faults = MSF.getExpressionMutator().getMutations(e);
		System.out.println(e + "->" + faults);
		assertEquals(4, faults.size());
		assertEquals("<2, b and b>", faults.get(0).toString());
		assertEquals("<2, a and b>", faults.get(1).toString());
		assertEquals("<1, a or b>", faults.get(2).toString());
	}

}
