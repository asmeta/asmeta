package extgt.coverage.fault.mutators.foms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.FaultTest;

public class VariableReferenceFaultTest extends FaultTest {
	static VariableReferenceFault vrf = VariableReferenceFault.VRF;
	
	@Test
	public void testForEmptyId() {
		// 
		List<Pair<Integer, Expression>> mut = VariableReferenceFault.VRF.getExpressionMutator(A).getMutations(A);
		assertTrue(mut.isEmpty());
	}

	@Test
	public void testForIdExpression() {
		ExpressionMutator em = vrf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B));
		List<Pair<Integer, Expression>> ris = em.getMutations(A);
		assertEquals(1, ris.size());
		assertEquals(B, ris.get(0).getSecond());
		assertEquals("<1, B>", ris.get(0).toString());
	}

	@Test
	public void testForId1Expression() {
		ExpressionMutator em = vrf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A));
		List<Pair<Integer, Expression>> ris = em.getMutations(A);
		//no mutation is possible
		assertEquals(0, ris.size());
	}

	@Test
	public void testForAndExpression() {
		ExpressionMutator em = vrf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B));
		List<Pair<Integer, Expression>> ris = em.getMutations(aANDb);
		assertEquals(2, ris.size());
		assertEquals("<2, B and B>", ris.get(0).toString());
		assertEquals("<3, A and A>", ris.get(1).toString());
		// if the set of ids is bigger
		em = vrf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C));
		ris = em.getMutations(aANDb);
		assertEquals(4, ris.size());
		assertEquals("<2, B and B>", ris.get(0).toString());
		assertEquals("<2, C and B>", ris.get(1).toString());
		assertEquals("<3, A and A>", ris.get(2).toString());
		assertEquals("<3, A and C>", ris.get(3).toString());
	}

	@Test
	public void testComplexExpression() {
		ExpressionMutator em = vrf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B));
		List<Pair<Integer, Expression>> ris = em.getMutations(not_AandB);
		System.out.println(ris.toString());
		assertEquals(2, ris.size());
		assertEquals("<4, not(B and B)>", ris.get(0).toString());
		assertEquals("<6, not(A and A)>", ris.get(1).toString());
		//
		AndExpression a1 = new AndExpression(aANDb, aORb);
		// (A and B) and (A or B)
		System.out.println(a1.toString());
		ris = em.getMutations(a1);
		System.out.println(ris.toString());
		assertEquals(4, ris.size());
		assertEquals("<4, (B and B) and (A or B)>", ris.get(0).toString());
		assertEquals("<6, (A and A) and (A or B)>", ris.get(1).toString());
		assertEquals("<5, (A and B) and (B or B)>", ris.get(2).toString());
		assertEquals("<7, (A and B) and (A or A)>", ris.get(3).toString());
	}

	@Test
	public void testNotExpression() {
		vrf.getExpressionMutator(notA);
		List<Pair<Integer, Expression>> ris = VariableReferenceFault.VRF.buildMutatorGetMutations(notA);
		System.out.println(ris.toString());
		assertEquals(0,ris.size());
		//
		ris = VariableReferenceFault.VRF.getExpressionMutator(notA_andB).getMutations(notA_andB); 
		// 
		assertEquals(2, ris.size());
		assertEquals("<2, B and B>", ris.get(0).toString());
		assertEquals("<3, not A and not A>", ris.get(1).toString());
	}

	@Test
	public void testNotIdExpression() throws ParseException {
		// 
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and b");
		List<Pair<Integer, Expression>> ris = VariableReferenceFault.VRF.buildMutatorGetMutations(e);
		assertEquals(2,ris.size());
		assertEquals("<2, b and b>", ris.get(0).toString());
		assertEquals("<3, not a and not a>", ris.get(1).toString());
		// if it contains and
		e = ExpressionParser.parseAsNewBooleanExpression("not a or b");
		ris = VariableReferenceFault.VRF.buildMutatorGetMutations(e);
		assertEquals(2,ris.size());
		assertEquals("<2, b or b>", ris.get(0).toString());
		assertEquals("<3, not a or not a>", ris.get(1).toString());		
	}
}
