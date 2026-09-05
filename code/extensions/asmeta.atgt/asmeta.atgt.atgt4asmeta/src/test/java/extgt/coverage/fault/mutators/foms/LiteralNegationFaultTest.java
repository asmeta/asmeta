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

import static extgt.coverage.fault.mutators.foms.VariableNegationFault.VNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tgtlib.definitions.expression.AndExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;


/**
 * The Class LiteralNegationFaultTest.
 * 
 * @author garganti
 */
class LiteralNegationFaultTest extends FaultTest{


	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.LiteralNegationFault.
	 */
	@Test void forAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> mut = VNF.getExpressionMutator().getMutations(e);
		assertEquals(2, mut.size());
		assertEquals("<2, not A and B>", mut.get(0).toString());
		assertEquals("<3, A and not B>", mut.get(1).toString());
		// inside
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		mut = VNF.getExpressionMutator().getMutations(a1);
		assertEquals(4, mut.size());
		assertEquals("<4, (not A and B) and (A or B)>", mut.get(0).toString());
		assertEquals("<6, (A and not B) and (A or B)>", mut.get(1).toString());
		assertEquals("<5, (A and B) and (not A or B)>", mut.get(2).toString());
		assertEquals("<7, (A and B) and (A or not B)>", mut.get(3).toString());
	}

	@Test void forOrExpression() {
		Expression e = ExpressionsToTest.aORb;
		List<Pair<Integer, Expression>> mut = VNF.getExpressionMutator().getMutations(e);
		assertEquals(2, mut.size());
		assertEquals("<2, not A or B>", mut.get(0).toString());
		assertEquals("<3, A or not B>", mut.get(1).toString());
		// inside
		OrExpression a1 = new OrExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		mut = VNF.getExpressionMutator().getMutations(a1);
		assertEquals(4, mut.size());
		assertEquals("<4, (not A and B) or (A or B)>", mut.get(0).toString());
		assertEquals("<6, (A and not B) or (A or B)>", mut.get(1).toString());
		assertEquals("<5, (A and B) or (not A or B)>", mut.get(2).toString());
		assertEquals("<7, (A and B) or (A or not B)>", mut.get(3).toString());
	}


	/**
	 * Test of forNotExpression method, of class
	 * atgt.specification.faultcoverage.LiteralNegationFault.
	 */
	@Test void forNotExpression() {
		List<Pair<Integer, Expression>> mut = VNF.getExpressionMutator().getMutations(notA);
		assertEquals(1, mut.size());
		assertEquals("<1, A>", mut.get(0).toString());
		// inside
		NotExpression a2 = NotExpression.createNotExpression(ExpressionsToTest.aANDb);
		mut = VNF.getExpressionMutator().getMutations(a2);
		assertEquals(2, mut.size());
		assertEquals("<4, not(not A and B)>", mut.get(0).toString());
		assertEquals("<6, not(A and not B)>", mut.get(1).toString());
	}
}
