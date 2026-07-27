package extgt.coverage.fault.mutators;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.ExpressionsToTest;

public class IsTermTest extends FaultTest {

	@Test
	public void testForSimpleExpressions() {		
		assertEquals(IsTerm.TermType.AND_TERM,aANDb.accept(IsTerm.instance));
		assertEquals(IsTerm.TermType.OR_TERM,aORb.accept(IsTerm.instance));
	}

	@Test 
	public void testForCompundExpressions() {
		// (a and b) and (a or b) is not a term
		AndExpression a1 = new AndExpression(aANDb, aORb);
		assertEquals(IsTerm.TermType.NO_TERM,a1.accept(IsTerm.instance));
		// (a and b) and (a and b) is AND term
		AndExpression a2 = new AndExpression(aANDb, ExpressionsToTest.aANDb);
		assertEquals(IsTerm.TermType.AND_TERM,a2.accept(IsTerm.instance));	
		// B and not a is AND term
		AndExpression a3 = new AndExpression(ExpressionsToTest.B, ExpressionsToTest.notA);
		assertEquals(IsTerm.TermType.AND_TERM,a3.accept(IsTerm.instance));			
	}

	@Test
	public void testForNegatedExpressions() {	
		// not A --> literal term
		assertEquals(IsTerm.TermType.LIT_TERM,ExpressionsToTest.notA.accept(IsTerm.instance));
		// not (a and b) -> is not a term
		assertEquals(IsTerm.TermType.NO_TERM,ExpressionsToTest.not_AandB.accept(IsTerm.instance));				
		// (a and b) and not (a and b) is not a term
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb, ExpressionsToTest.not_AandB);
		assertEquals(IsTerm.TermType.NO_TERM,a1.accept(IsTerm.instance));
	}

	@Test
	public void testForIDExpressions() {	
		// A --> literal term
		assertEquals(IsTerm.TermType.LIT_TERM,ExpressionsToTest.A.accept(IsTerm.instance));
	}
}
