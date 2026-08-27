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

public class ClauseConjunctionFTest extends FaultTest {
	
	static ClauseConjunctionF ccf = ClauseConjunctionF.CCF;
	
	@Test
	public void testForMutations() {
		//
		List<Pair<Integer, Expression>> ris = ccf.buildMutatorGetMutations(aANDb);
		assertTrue(ris.isEmpty());
		AndExpression a1 = new AndExpression(aANDb, aORb);
		// (A and B) and (A or B)
		ris = ccf.buildMutatorGetMutations(a1);
		System.out.println(ris.toString());
		assertEquals(2, ris.size());
		assertEquals("<5, (A and B) and ((A and B) or B)>", ris.get(0).toString());
		assertEquals("<7, (A and B) and (A or (B and A))>", ris.get(1).toString());		
	}
		
	// A, B and C as IDS, and also D
	@Test
	public void testForAndExpression() {
		ExpressionMutator mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C));
		List<Pair<Integer, Expression>> ris = mut.getMutations(aANDb);
		System.out.println(ris.toString());
		assertEquals("[<1, (A and B) and C>]", ris.toString());
		// add another one
		mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C,D));
		ris = mut.getMutations(aANDb);
		System.out.println(ris.toString());
		assertEquals("[<1, (A and B) and C>, <1, (A and B) and D>]", ris.toString());
	}

	@Test
	public void testForOrExpression() {
		ExpressionMutator mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C));
		List<Pair<Integer, Expression>> ris = mut.getMutations(aORb);
		System.out.println(ris.toString());
		assertEquals(4,ris.size());
		assertEquals("<2, (A and B) or B>", ris.get(0).toString());
		assertEquals("<2, (A and C) or B>", ris.get(1).toString());
		assertEquals("<3, A or (B and A)>", ris.get(2).toString());
		assertEquals("<3, A or (B and C)>", ris.get(3).toString());
		// add another one
		mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C,D));
		ris = mut.getMutations(aORb);
		assertEquals(6,ris.size());
		System.out.println(ris.toString());
		assertEquals("[<2, (A and B) or B>, <2, (A and C) or B>, <2, (A and D) or B>, <3, A or (B and A)>, <3, A or (B and C)>, <3, A or (B and D)>]", ris.toString());
	}

	@Test
	public void testIdExpression() {
		ExpressionMutator mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B));
		List<Pair<Integer, Expression>> ris = mut.getMutations(A);
		System.out.println(ris.toString());
		assertEquals("[<1, A and B>]", ris.toString());
		// add another one
		mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,D));
		ris = mut.getMutations(A);
		System.out.println(ris.toString());
		assertEquals("[<1, A and B>, <1, A and D>]", ris.toString());
	}

	@Test
	public void testComplexExpression() {
		ExpressionMutator mut = ccf.getExpressionMutator(Arrays.asList((IdUNotIdExpression)A,B,C));
		List<Pair<Integer, Expression>> ris = mut.getMutations(not_AandB);
		System.out.println(ris.toString());
		assertEquals("[<2, not((A and B) and C)>]", ris.toString());
		//
		AndExpression a1 = new AndExpression(aANDb, aORb);
		// (A and B) and (A or B)
		System.out.println(a1.toString());
		ris = mut.getMutations(a1);
		System.out.println(ris.toString());
		assertEquals(5, ris.size());
		assertEquals("<2, ((A and B) and C) and (A or B)>", ris.get(0).toString());
		assertEquals("<5, (A and B) and ((A and B) or B)>", ris.get(1).toString());
		assertEquals("<5, (A and B) and ((A and C) or B)>", ris.get(2).toString());
		assertEquals("<7, (A and B) and (A or (B and A))>", ris.get(3).toString());
		assertEquals("<7, (A and B) and (A or (B and C))>", ris.get(4).toString());
	}

	@Test
	public void testNotExpression() throws ParseException {
		ExpressionMutator mut = ccf.getExpressionMutator(Arrays.asList(notA,B));
		List<Pair<Integer, Expression>> ris = mut.getMutations(notA);
		System.out.println(ris.toString());
		assertEquals("[<1, not A and B>]", ris.toString());
		// add another one
		mut = ccf.getExpressionMutator(Arrays.asList(notA,B,D));
		ris = mut.getMutations(notA);
		assertEquals(2,ris.size());
		System.out.println(ris.toString());
		assertEquals("[<1, not A and B>, <1, not A and D>]", ris.toString());
		// 
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not (a or b) and c)");
		ris = ccf.buildMutatorGetMutations(e);
		assertEquals(6,ris.size());
		assertEquals("<8, not((a and b) or b) and c>", ris.get(0).toString());
		assertEquals("<8, not((a and c) or b) and c>", ris.get(1).toString());
		assertEquals("<12, not(a or (b and a)) and c>", ris.get(2).toString());
		assertEquals("<12, not(a or (b and c)) and c>", ris.get(3).toString());
		assertEquals("<3, not(a or b) and (c and a)>", ris.get(4).toString());
		assertEquals("<3, not(a or b) and (c and b)>", ris.get(5).toString());
		// if it contain and
		e = ExpressionParser.parseAsNewBooleanExpression("not (a and b) and c)");
		ris = ccf.buildMutatorGetMutations(e);
		assertEquals(3,ris.size());
		assertEquals("<4, not((a and b) and c) and c>", ris.get(0).toString());
		assertEquals("<3, not(a and b) and (c and a)>", ris.get(1).toString());
		assertEquals("<3, not(a and b) and (c and b)>", ris.get(2).toString());
		//
		ris = ccf.buildMutatorGetMutations(not_AandB);
		assertEquals(0,ris.size());
	}

	@Test
	public void testNotIdExpression() throws ParseException {
		// 
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a or b");
		List<Pair<Integer, Expression>> ris = ccf.buildMutatorGetMutations(e);
		assertEquals(2,ris.size());
		assertEquals("<2, (not a and b) or b>", ris.get(0).toString());
		assertEquals("<3, not a or (b and not a)>", ris.get(1).toString());
		// if it contain and
		e = ExpressionParser.parseAsNewBooleanExpression("not a and b");
		ris = ccf.buildMutatorGetMutations(e);
		assertEquals(0,ris.size());
	}
}
