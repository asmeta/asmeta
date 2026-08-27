package extgt.coverage.fault.mutators.foms;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.FaultTest;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class ClauseDisjunctionFTest extends FaultTest{
	static ClauseDisjunctionF cdf = ClauseDisjunctionF.CDF;

	public ClauseDisjunctionFTest() {
		
	}

	@Test
	public void testForAndExpression() {
		IdUNotIdExpression[] a = { A, B, C };
		ExpressionMutator mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a));
		//assertEquals(3,cdf.fev.getIds().size());
		List<Pair<Integer, Expression>> ris = mut.getMutations(aANDb);
		assertEquals(4,ris.size());
		assertEquals("<2, (A or B) and B>", ris.get(0).toString());
		assertEquals("<2, (A or C) and B>", ris.get(1).toString());
		assertEquals("<3, A and (B or A)>", ris.get(2).toString());
		assertEquals("<3, A and (B or C)>", ris.get(3).toString());		
	}

	@Test
	public void testForOrExpression() {
		IdUNotIdExpression[] a = { A, B, C };
		ExpressionMutator mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a));
		// a or b
		List<Pair<Integer, Expression>> ris = mut.getMutations(aORb);
		System.out.println(ris.toString());
		assertEquals(1,ris.size());
		assertEquals("<1, (A or B) or C>", ris.get(0).toString());
		IdUNotIdExpression[] a1 = { A, B, C, D };
		// add another one
		mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a1));
		ris = mut.getMutations(aORb);
		assertEquals(2,ris.size());
		System.out.println(ris.toString());
		assertEquals("[<1, (A or B) or C>, <1, (A or B) or D>]", ris.toString());
	}

	@Test
	public void testIdExpression() {
		IdUNotIdExpression[] a = { A, B };
		ExpressionMutator mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a));
		List<Pair<Integer, Expression>> ris = mut.getMutations(A);
		System.out.println(ris.toString());
		assertEquals("[<1, A or B>]", ris.toString());
		IdUNotIdExpression[] a1 = { A, B, D };
		// add another one
		mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a1));
		ris = mut.getMutations(A);
		assertEquals("[<1, A or B>, <1, A or D>]", ris.toString());
	}

	@Test
	public void testComplexExpression() {
		IdUNotIdExpression[] a = { A, B, C };
		ExpressionMutator mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a));
		List<Pair<Integer, Expression>> ris = mut.getMutations(not_AandB);
		System.out.println(ris.toString());
		assertEquals(4, ris.size());
		assertEquals("<4, not((A or B) and B)>", ris.get(0).toString());
		assertEquals("<4, not((A or C) and B)>", ris.get(1).toString());
		assertEquals("<6, not(A and (B or A))>", ris.get(2).toString());
		assertEquals("<6, not(A and (B or C))>", ris.get(3).toString());
		//
		AndExpression a1 = new AndExpression(aANDb, aORb);
		// (A and B) and (A or B)
		System.out.println(a1.toString());
		ris = mut.getMutations(a1);
		System.out.println(ris.toString());
		assertEquals(5, ris.size());
		assertEquals("<4, ((A or B) and B) and (A or B)>", ris.get(0).toString());
		assertEquals("<4, ((A or C) and B) and (A or B)>", ris.get(1).toString());
		assertEquals("<6, (A and (B or A)) and (A or B)>", ris.get(2).toString());
		assertEquals("<6, (A and (B or C)) and (A or B)>", ris.get(3).toString());
		assertEquals("<3, (A and B) and ((A or B) or C)>", ris.get(4).toString());
	}

	@Test
	public void testNotExpression() {
		IdUNotIdExpression[] a = { notA, B };
		ExpressionMutator mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a));
		List<Pair<Integer, Expression>> ris = mut.getMutations(notA);
		System.out.println(ris.toString());
		assertEquals("[<1, not A or B>]", ris.toString());
		IdUNotIdExpression[] a1 = { notA, B, D };
		// add another one
		mut = ClauseDisjunctionF.CDF.getExpressionMutator(Arrays.asList(a1));
		ris = mut.getMutations(notA);
		assertEquals(2,ris.size());
		assertEquals("[<1, not A or B>, <1, not A or D>]", ris.toString());
	}

	@Test
	public void testNotIdExpression() throws ParseException {
		// 
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and b");
		List<Pair<Integer, Expression>> ris = ClauseDisjunctionF.CDF.buildMutatorGetMutations(e);
		assertEquals(2,ris.size());
		assertEquals("<2, (not a or b) and b>", ris.get(0).toString());
		assertEquals("<3, not a and (b or not a)>", ris.get(1).toString());
		// if it contains and
		e = ExpressionParser.parseAsNewBooleanExpression("not a or b");
		ris = ClauseDisjunctionF.CDF.buildMutatorGetMutations(e);
		assertEquals(0,ris.size());
	}	
}
