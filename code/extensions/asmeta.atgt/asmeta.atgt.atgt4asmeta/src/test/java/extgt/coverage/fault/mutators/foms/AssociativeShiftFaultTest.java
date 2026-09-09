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

import static extgt.coverage.fault.mutators.foms.AssociativeShiftFault.ASF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import tgtlib.definitions.expression.AndExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class AssociativeShiftFaultTest.
 * 
 * @author garganti
 */
class AssociativeShiftFaultTest extends FaultTest {

	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.AssociativeShiftFault.
	 */
	@Test void forAndorExpression() {
		AndExpression a1 = new AndExpression(aANDb, aORb);
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator(a1).getMutations(a1);
		System.out.println(a1 + "->" + asf1);
		assertEquals(1,asf1.size());
		assertEquals("<1, ((A and B) and A) or B>",asf1.get(0).toString());
	}

	@Test void forNotAndorExpression() {
		Expression a1 = NotExpression.createNotExpression(new AndExpression(aANDb, aORb));
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator(a1).getMutations(a1);
		System.out.println(a1 + "->" + asf1);
		assertEquals(1,asf1.size());
		assertEquals("<2, not(((A and B) and A) or B)>",asf1.get(0).toString());
	}


	@Test void fororAndExpression() {
		Expression a1 = new OrExpression(ExpressionsToTest.aANDb, ExpressionsToTest.C);
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator(a1).getMutations(a1);
		System.out.println(a1 + "->" + asf1);
		assertEquals(1,asf1.size());
		assertEquals("<1, A and (B or C)>",asf1.get(0).toString());
	}

	@Test void forAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> asf1 = ASF.getExpressionMutator(e).getMutations(e);		
		System.out.println(ExpressionsToTest.aANDb + "->" + asf1);
		assertTrue(asf1.isEmpty());
	}

	/** For
	 	example, x1 \/ not x2 /\ (x3 /\ x4) is an ASF of (x1 \/ not x2) /\ (x3 /\ x4).
	 */
	@Test void chen() {
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator(chenExpr).getMutations(chenExpr);		
		System.out.println(asf1);
		assertEquals(1,asf1.size());
		assertEquals("<1, x1 or (not x2 and (x3 and x4))>", asf1.get(0).toString());
	}

	/** For an expression inside another one
	 * @throws ParseException 
	 * @throws  
	 */
	@Test void chen2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not (a or (b and c))");
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator().getMutations(e);		
		System.out.println(asf1);
		assertEquals(1,asf1.size());
		assertEquals("<2, not((a or b) and c)>", asf1.get(0).toString());
	}

	// two expressions
	@Test void deep2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(a or (b and (c or d)))");
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator().getMutations(e);		
		System.out.println(asf1);
		assertEquals(2,asf1.size());
		assertEquals("<1, (a or b) and (c or d)>", asf1.get(0).toString());
		assertEquals("<3, a or ((b and c) or d)>", asf1.get(1).toString());
	}

	//a specification that is not changed 
	@Test void noFault() {
		List<Pair<Integer, Expression>> asf1 =  ASF.getExpressionMutator().getMutations(aANDb);		
		System.out.println(asf1);
		assertEquals(0,asf1.size());
		asf1 =  ASF.getExpressionMutator().getMutations(aORb);		
		assertEquals(0,asf1.size());
	}
}
