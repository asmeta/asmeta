package extgt.coverage.fault.mutators.foms;

import static extgt.coverage.fault.mutators.foms.MissingSubExpressionFault.MSF;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import extgt.coverage.fault.mutators.FaultTest;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;

public class MissingSubExpressionFaultTest extends FaultTest {

	@Test
	public void test1() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(a or b) and b");
		List<Pair<Integer, Expression>> faults = MSF.getExpressionMutator().getMutations(e);
		System.out.println(e + "->" + faults);
		assertEquals(4, faults.size());
		assertEquals("<2, b and b>", faults.get(0).toString());
		assertEquals("<2, a and b>", faults.get(1).toString());
		assertEquals("<1, a or b>", faults.get(2).toString());
	}

}
