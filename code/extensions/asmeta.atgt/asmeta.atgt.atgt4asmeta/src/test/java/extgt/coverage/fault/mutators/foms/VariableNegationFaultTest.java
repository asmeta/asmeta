package extgt.coverage.fault.mutators.foms;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.FaultTest;

public class VariableNegationFaultTest extends FaultTest {
	static VariableNegationFault vnf = VariableNegationFault.VNF;
	static ExpressionMutator mut = vnf.getExpressionMutator();

	@Test
	public void testForIdExpression() {
		List<Pair<Integer, Expression>> ris = mut.getMutations(A);
		assertEquals(1, ris.size());
		assertEquals("not A", ris.get(0).getSecond().toString());
		assertEquals(1, ris.get(0).getFirst().intValue());
	}

	@Test
	public void testForAndExpression() {
		List<Pair<Integer, Expression>> ris = mut.getMutations(aANDb);
		assertEquals(2, ris.size());
		assertEquals("<2, not A and B>", ris.get(0).toString());
		assertEquals("<3, A and not B>", ris.get(1).toString());
	}

	@Test
	public void testForOrExpression() {
		List<Pair<Integer, Expression>> ris = mut.getMutations(aORb);
		assertEquals(2, ris.size());
		assertEquals("<2, not A or B>", ris.get(0).toString());
		assertEquals("<3, A or not B>", ris.get(1).toString());
	}

	@Test
	public void testForExample() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a or ( a and b)");
		List<Pair<Integer, Expression>> ris = mut.getMutations(e);
		assertEquals(3, ris.size());
		assertEquals("<2, not a or (a and b)>", ris.get(0).toString());
		assertEquals("<5, a or (not a and b)>", ris.get(1).toString());
		assertEquals("<7, a or (a and not b)>", ris.get(2).toString());
	}

	@Test
	public void testForNotExpression() {
		List<Pair<Integer, Expression>> ris = mut.getMutations(notA);
		assertEquals(1, ris.size());
		assertEquals("<1, A>", ris.get(0).toString());
		ris = mut.getMutations(not_AandB);
		// (1) not  -> (2) and -> (4) A , (6) B
		assertEquals(2, ris.size());
		assertEquals("<4, not(not A and B)>", ris.get(0).toString());
		assertEquals("<6, not(A and not B)>", ris.get(1).toString());
		// 
		NotExpression not_AorB = NotExpression.createNotExpression(aORb);
		ris = mut.getMutations(not_AorB);
		// (1) not  -> (2) and -> (4) A , (6) B
		assertEquals(2, ris.size());
		assertEquals("<4, not(not A or B)>", ris.get(0).toString());
		assertEquals("<6, not(A or not B)>", ris.get(1).toString());
		//
		ris = mut.getMutations(notA_andB);
		// 
		assertEquals(2, ris.size());
		assertEquals("<2, A and B>", ris.get(0).toString());
		assertEquals("<3, not A and not B>", ris.get(1).toString());
	}

	@Test
	public void testForComplexExpression() {
		//  (x1 \/ not x2) /\ (x3 /\ x4)
		List<Pair<Integer, Expression>> ris = mut.getMutations(chenExpr);
		assertEquals(4, ris.size());
		assertEquals("<4, (not x1 or not x2) and (x3 and x4)>", ris.get(0).toString());
		assertEquals("<6, (x1 or x2) and (x3 and x4)>", ris.get(1).toString());
		assertEquals("<5, (x1 or not x2) and (not x3 and x4)>", ris.get(2).toString());
		assertEquals("<7, (x1 or not x2) and (x3 and not x4)>", ris.get(3).toString());
	}
}
