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

import static extgt.coverage.fault.mutators.foms.MissingVariableFault.MVF;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionsToTest;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.FaultTest;

/**
 * The Class MissingVariableFaultTest.
 * 
 * @author garganti
 */
public class MissingVariableFaultTest extends FaultTest {

	/**
	 * Test of forAndExpression method, of class
	 * atgt.specification.faultcoverage.LiteralNegationFault.
	 */
	@Test
	public void testForAndExpression() {
		Expression e = ExpressionsToTest.aANDb;
		List<Pair<Integer, Expression>> aAndBFault = MVF.getExpressionMutator().getMutations(e);
		System.out.println(ExpressionsToTest.aANDb + "->" + aAndBFault);
		assertEquals(2, aAndBFault.size());
		assertEquals("<1, B>", aAndBFault.get(0).toString());
		assertEquals("<1, A>", aAndBFault.get(1).toString());
		// complex faults
		AndExpression a1 = new AndExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		List<Pair<Integer, Expression>> mutations = MVF.getExpressionMutator().getMutations(a1);
		assertEquals(4, mutations.size());
		assertEquals("<2, B and (A or B)>", mutations.get(0).toString());
		assertEquals("<2, A and (A or B)>", mutations.get(1).toString());
		assertEquals("<3, (A and B) and B>", mutations.get(2).toString());
		assertEquals("<3, (A and B) and A>", mutations.get(3).toString());
	}

	@Test
	public void testForOrExpression() {
		Expression e = ExpressionsToTest.aORb;
		List<Pair<Integer, Expression>> aorBFault = MVF.getExpressionMutator(e).getMutations(e);
		System.out.println(ExpressionsToTest.aANDb + "->" + aorBFault);
		assertEquals(2, aorBFault.size());
		assertEquals("<1, B>", aorBFault.get(0).toString());
		assertEquals("<1, A>", aorBFault.get(1).toString());
		// complex faults
		OrExpression a1 = new OrExpression(ExpressionsToTest.aANDb, ExpressionsToTest.aORb);
		List<Pair<Integer, Expression>> mutations = MVF.getExpressionMutator().getMutations(a1);
		assertEquals(4, mutations.size());
		assertEquals("<2, B or (A or B)>", mutations.get(0).toString());
		assertEquals("<2, A or (A or B)>", mutations.get(1).toString());
		assertEquals("<3, (A and B) or B>", mutations.get(2).toString());
		assertEquals("<3, (A and B) or A>", mutations.get(3).toString());
	}

	@Test
	public void testNotExpression(){
		List<Pair<Integer, Expression>> mut = MVF.getExpressionMutator().getMutations(notA);
		assertEquals(0, mut.size());
		mut = MVF.getExpressionMutator().getMutations(not_AandB);
		assertEquals(2, mut.size());
		assertEquals("<2, not B>", mut.get(0).toString());
		assertEquals("<2, not A>", mut.get(1).toString());
	}

	@Test
	public void test1() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(a or b) and b");
		List<Pair<Integer, Expression>> faults = MVF.getExpressionMutator().getMutations(e);
		System.out.println(ExpressionsToTest.aANDb + "->" + faults);
		assertEquals(3, faults.size());
		assertEquals("<2, b and b>", faults.get(0).toString());
		assertEquals("<2, a and b>", faults.get(1).toString());
		assertEquals("<1, a or b>", faults.get(2).toString());
	}

	//it does not terminate. Why?
	//The expression in bigTest2 is bigger, but it terminates,
	@Test
	public void bigTest() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not(not(G1365 and not G4612) and not(not(not(G1332 and not G4602) and not(not(not(G1390 and not G4592) and not(not(not(G1430 and not G4582) and not(not(not(not((((G389 and G2780) or (not(not(G4311 and G4314) and not(G4308 and G4315)) and (G400 and G2776))) or ((not(not(G4319 and G4322) and not(G4316 and G4323)) and (G411 and G2772)) and not(not(G4311 and G4314) and not(G4308 and G4315)))) or (((not(not(G4327 and G4330) and not(G4324 and G4331)) and not(not(G4319 and G4322) and not(G4316 and G4323))) and (G374 and G2767)) and not(not(G4311 and G4314) and not(G4308 and G4315)))) and not G4572) and not(not(not(not G1478 and not G4562) and not(not(not(not(((G400 and G2776) or (not(not(G4319 and G4322) and not(G4316 and G4323)) and (G411 and G2772))) or ((not(not(G4327 and G4330) and not(G4324 and G4331)) and not(not(G4319 and G4322) and not(G4316 and G4323))) and (G374 and G2767))) and not G4552) and not(G1412 and not G4555)) and not G4565)) and not G4575)) and not G4585)) and not G4595)) and not G4605)) and not G4615))");
		assert e != null;
		System.out.println(e);
		List<Pair<Integer, Expression>> faults = MVF.getExpressionMutator().getMutations(e);
	}

	@Test
	public void bigTest2() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("(not G1886 and not G1903) or (((((((G3151 and G503) or (not(not(G4647 and G4650) and not(G4644 and G4651)) and (G3147 and G514))) or ((not(not(G4655 and G4658) and not(G4652 and G4659)) and not(not(G4647 and G4650) and not(G4644 and G4651))) and (G3143 and G523))) or (((not(not(G4663 and G4666) and not(G4660 and G4667)) and not(not(G4647 and G4650) and not(G4644 and G4651))) and (G3139 and G534)) and not(not(G4655 and G4658) and not(G4652 and G4659)))) or ((((not(not(G4671 and G4674) and not(G4668 and G4675)) and not(not(G4663 and G4666) and not(G4660 and G4667))) and not(not(G4647 and G4650) and not(G4644 and G4651))) and G3137) and not(not(G4655 and G4658) and not(G4652 and G4659)))) or (G1857 and G54)) and not(not(G1704 and not G4684) and not((((G3165 or (not G3165 and (G3161 and G479))) or ((not(not(G4631 and G4634) and not(G4628 and G4635)) and (G490 and G3155)) and not G3165)) or ((not(not(G4639 and G4642) and not(G4636 and G4643)) and not(not(G4631 and G4634) and not(G4628 and G4635))) and not G3165)) and not G4687)))");
		List<Pair<Integer, Expression>> faults = MVF.getExpressionMutator().getMutations(e);
		System.out.println(faults.size());
	}
}