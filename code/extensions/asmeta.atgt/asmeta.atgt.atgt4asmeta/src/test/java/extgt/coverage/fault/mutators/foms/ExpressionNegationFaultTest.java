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

import static extgt.coverage.fault.mutators.foms.ExpressionNegationFault.ENF;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class ExpressionNegationFaultTest.
 * 
 * @author garganti
 */
public class ExpressionNegationFaultTest extends FaultTest {

	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 */
	@Test
	public void testForAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> mut = ENF.getExpressionMutator().getMutations(e);
		assertEquals(1, mut.size());
		assertEquals("<1, not(A and B)>", mut.get(0).toString());
		//
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		mut = ENF.getExpressionMutator().getMutations(a1);
		assertEquals(3, mut.size());
		assertEquals("<1, not((A and B) and (A or B))>", mut.get(0).toString());
		assertEquals("<2, not(A and B) and (A or B)>", mut.get(1).toString());
		assertEquals("<3, (A and B) and not(A or B)>", mut.get(2).toString());
	}

	/**
	 * Test of forOrExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 */
	@Test
	public void testForOrExpression() {
		Expression e = ExpressionsToTest.aORb;
		List<Pair<Integer, Expression>> mut = ENF.getExpressionMutator().getMutations(e);
		assertEquals(1, mut.size());
		assertEquals("<1, not(A or B)>", mut.get(0).toString());
		//
		OrExpression a1 = new OrExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		mut = ENF.getExpressionMutator().getMutations(a1);
		assertEquals(3, mut.size());
		assertEquals("<1, not((A and B) or (A or B))>", mut.get(0).toString());
		assertEquals("<2, not(A and B) or (A or B)>", mut.get(1).toString());
		assertEquals("<3, (A and B) or not(A or B)>", mut.get(2).toString());
		// with a not
		a1 = new OrExpression(A, NotExpression.createNotExpression(a1));
		// A or not((A and B) or (A or B))
		mut = ENF.getExpressionMutator().getMutations(a1);
		assertEquals(4, mut.size());
		assertEquals("<1, not(A or not((A and B) or (A or B)))>", mut.get(0).toString());
		assertEquals("<3, A or ((A and B) or (A or B))>", mut.get(1).toString());
		assertEquals("<9, A or not(not(A and B) or (A or B))>", mut.get(2).toString());
		assertEquals("<13, A or not((A and B) or not(A or B))>", mut.get(3).toString());
	}

	/**
	 * Test of forNotExpression method, of class
	 * atgt.specification.faultcoverage.ExpressionNegationFault.
	 * @throws ParseException 
	 */
	@Test
	public void testForNotExpression() throws ParseException {
		NotExpression ne = NotExpression.createNotExpression(aORb);
		// not (a or b)
		List<Pair<Integer, Expression>> mut = ENF.getExpressionMutator().getMutations(ne);		
		assertEquals(1, mut.size());
		assertEquals("<1, A or B>", mut.get(0).toString());
		// not not
		ne = NotExpression.createNotExpression(ne);
		// not (not (A or B)
		mut = ENF.getExpressionMutator().getMutations(ne);
		assertEquals(1, mut.size());
		assertEquals("<2, not(A or B)>", mut.get(0).toString());
		// particular case
		Expression enn = ExpressionParser.parseAsNewBooleanExpression("a and (not (c or b))");
		mut = ENF.getExpressionMutator().getMutations(enn);
		assertEquals(2, mut.size());
		assertEquals("<1, not(a and not(c or b))>", mut.get(0).toString());
		// particular case
		enn = ExpressionParser.parseAsNewBooleanExpression("not(a and (not (c or b)))");
		mut = ENF.getExpressionMutator().getMutations(enn);
		assertEquals(2, mut.size());
		//assertEquals("<1, not(A or B)>", mut.get(0).toString());
	}
	
	/**
	 * For example, not (x1 \/not x2) /\ (x3 /\ x4) is an ENF of (x1 \/ not x2) /\ (x3 /\ x4). 
	 */
	@Test
	public void testChenExpression() {
		List<Pair<Integer, Expression>> enf_e1 = ENF.getExpressionMutator(chenExpr).getMutations(chenExpr);
		System.out.println(chenExpr + "->" + enf_e1);
		assertEquals(3, enf_e1.size());
		String str = chenExpr.toString();
		str.contains("not(x1 or not x2) and (x3 and x4)");
	}
}
