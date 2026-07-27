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

import static extgt.coverage.fault.mutators.foms.RelationalOperatorFault.ROF;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class ExpressionNegationFaultTest.
 * 
 * @author garganti
 */
public class ROFTest extends FaultTest {

	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 */
	@Test
	public void testForAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> rof1 = ROF.getExpressionMutator(e).getMutations(e);
		System.out.println(ExpressionsToTest.aANDb + "->" + rof1);
		assertTrue(rof1.isEmpty());
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		List<Pair<Integer, Expression>> rof2 = ROF.getExpressionMutator(a1).getMutations(a1);
		System.out.println(a1 + "->" + rof2);
		assertTrue(rof2.isEmpty());
	}

	/**
	 * Test of forOrExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 */
	@Test
	public void testForOrExpression() {
		Expression e = ExpressionsToTest.aORb;
		System.out.println(ExpressionsToTest.aORb + "->" + ROF.getExpressionMutator(e).getMutations(e));
		OrExpression a1 = new OrExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		System.out.println(a1 + "->" + ROF.getExpressionMutator(a1).getMutations(a1));

	}

	/**
	 * Test of forNotExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 */
	@Test
	public void testForNotExpression() {
		NotExpression ne = NotExpression.createNotExpression(ExpressionsToTest.aORb);
		System.out.println(ne + "->" + ROF.getExpressionMutator(ne).getMutations(ne));
	}
}
