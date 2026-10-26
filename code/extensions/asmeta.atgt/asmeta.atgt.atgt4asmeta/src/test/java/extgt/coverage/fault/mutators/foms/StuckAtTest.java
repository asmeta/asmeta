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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class StuckAtTest.
 * 
 * @author garganti
 */
class StuckAtTest extends FaultTest {

	/**
	 * Test for and expression.
	 */
	@Test void forAndExpression() {
		System.out.println("testForAndExpression");
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> mutations = StuckAt.STUCK_AT0.getExpressionMutator().getMutations(e);
		System.out.println(ExpressionsToTest.aANDb + "->" + mutations);
		assertEquals("[<1, false>, <2, false and B>, <3, A and false>]", mutations.toString());

	}

	/**
	 * Test for not expression.
	 */
	@Test void forNotExpression() {
		System.out.println("testForNotExpression");
		Expression e = ExpressionsToTest.notA;
		System.out.println(ExpressionsToTest.notA + "->" + StuckAt.STUCK_AT0.getExpressionMutator().getMutations(e));
		Expression e1 = ExpressionsToTest.not_AandB;
		// assertEquals("[true]",notA.accept(StuckAt0));
		System.out.println(ExpressionsToTest.not_AandB + "->"
				+ StuckAt.STUCK_AT0.getExpressionMutator(e1).getMutations(e1));
	}
}
