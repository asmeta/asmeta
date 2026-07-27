/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/

package extgt.coverage.fault.mutators.foms;

import static extgt.coverage.fault.mutators.foms.OperatorReferenceFault.ORF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class OperatorReferenceFaultTest.
 * 
 * @author garganti
 */
public class OperatorReferenceFaultTest extends FaultTest{

	/**
	 * Test of forIdExpression method, of class
	 * atgt.specification.faultcoverage.OperatorReferenceFault.
	 */
	@org.junit.Test
	public void testForIdExpression() {
		List<Pair<Integer, Expression>> ris = ORF.getExpressionMutator(A).getMutations(A);
		assertTrue(ris.isEmpty());
	}

	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.OperatorReferenceFault.
	 */
	@org.junit.Test
	public void testForAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> l1 = ORF.getExpressionMutator().getMutations(e);
		assertEquals("[<1, A or B>]", l1.toString());
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb,ExpressionsToTest.aORb);
		List<Pair<Integer, Expression>> l2 = ORF.getExpressionMutator().getMutations(a1);
		assertEquals(3, l2.size());
		assertEquals("<1, (A and B) or (A or B)>", l2.get(0).toString());
		assertEquals("<2, (A or B) and (A or B)>", l2.get(1).toString());
		assertEquals("<3, (A and B) and (A and B)>", l2.get(2).toString());
	}

	/**
	 * Test of forOrExpression method, of class
	 * atgt.specification.faultcoverage.OperatorReferenceFault.
	 */
	@Test
	public void testForOrExpression() {
		Expression e = ExpressionsToTest.aORb;
		List<Pair<Integer, Expression>> l1 = ORF.getExpressionMutator().getMutations(e);
		assertEquals("[<1, A and B>]", l1.toString());
		OrExpression a1 = new OrExpression(ExpressionsToTest.aANDb,ExpressionsToTest.aORb);
		List<Pair<Integer, Expression>> l2 = ORF.getExpressionMutator().getMutations(a1);
		assertEquals(3, l2.size());
		assertEquals("<1, (A and B) and (A or B)>", l2.get(0).toString());
		assertEquals("<2, (A or B) or (A or B)>", l2.get(1).toString());
		assertEquals("<3, (A and B) or (A and B)>", l2.get(2).toString());
	}

	/**
	 * Test of forNotExpression method, of class
	 * atgt.specification.faultcoverage.OperatorReferenceFault.
	 */
	@Test
	public void testForNotExpression() {
		Expression e = ExpressionsToTest.notA;
		List<Pair<Integer, Expression>> l1 = ORF.getExpressionMutator().getMutations(e);
		assertEquals("[]", l1.toString());
		Expression e1 = ExpressionsToTest.not_AandB;
		l1 = ORF.getExpressionMutator().getMutations(e1);
		assertEquals("[<2, not(A or B)>]", l1.toString());
	}
}
