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

package atgt.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import atgt.parser.asmeta.AsmMLoaderTest;

import org.junit.jupiter.api.Test;
import atgt.specification.ASMSpecification;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.coverage.CoverageTree;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConstCreator;

/**
 * The Class BasicRuleVisitorTest.
 * 
 * @author garganti
 */
public class BasicRuleVisitorTest extends RuleTest{

	/** The filename counter1. */
	static String filenameCounter1 = "SIS.asm";


	/**
	 * Test of forSkip method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forSkip() {
		List<NamedTerm> res = Skip.SKIP.accept(new BasicRuleVisitor());
		assertTrue(res.isEmpty());
	}

	/**
	 * Test of forAssignment method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forAssignment() {
		//UpdateRule ur = new UpdateRule(null, null);
		//List<NamedTerm> res = ur.accept(new BasicRuleVisitor());		
	}

	/**
	 * Test of forDoStatement method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forDoStatement() {
		List<NamedTerm> results = par.accept(new BasicRuleVisitor());
		assertEquals(3,results.size());
		assertEquals("T1: A or B", results.get(0).toString());
		assertEquals("T2: A and B", results.get(1).toString());
		assertEquals("F2: not(A and B)", results.get(2).toString());
	}

	/**
	 * Test of forIfThenElse method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forIfThenElse() {
		List<NamedTerm> results = new BasicRuleVisitor().forIfThenElse(if_woelse);
		assertEquals(2,results.size());
		assertEquals("T", results.get(0).getName());
		assertEquals("A or B",results.get(0).getCondition().toString());
		assertEquals("F", results.get(1).getName());
		assertEquals("not(A or B)",results.get(1).getCondition().toString());
		// else
		assertNotNull(if_wemptyelse.getElsePart());
		results = new BasicRuleVisitor().forIfThenElse(if_wemptyelse);
		assertEquals(2,results.size());
		assertEquals("T: A and B", results.get(0).toString());
		assertEquals("F: not(A and B)", results.get(1).toString());
		// nested else
		results = new BasicRuleVisitor().forIfThenElse(c_wnestedelse);
		assertEquals(4,results.size());
		assertEquals("TT: A and (A and B)", results.get(0).toString());
		assertEquals("TF: A and not(A and B)", results.get(1).toString());
		assertEquals("FT: not A and (A and B)", results.get(2).toString());
		assertEquals("FF: not A and not(A and B)", results.get(3).toString());
	}

	/**
	 * Test of forRule method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	public void testForRule() {
		// System.out.println("testForRule");

		// TODO add your test code below by replacing the default call to fail.
		// fail("The test case is empty.");
	}

	/**
	 * Test of forSpecification method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forSpecification() {
		
		ASMSpecification SP = AsmMLoaderTest.SISSpecification();

		BasicRuleVisitor brv = new BasicRuleVisitor();
		CoverageTree<AsmTestCondition> cov = brv.getTPTree(SP);
		System.out.println(cov.toString());
		for (AsmTestCondition tc : cov.allTPs()) {
			System.out.println("tc -> " + tc.toString() + ":"
					+ tc.getCondition());
		}
	}

	/**
	 * Test of forMacroCallRule method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void forMacroCallRule() {
		System.out.println("testForMacroCallRule");

		// TODO add your test code below by replacing the default call to fail.
		// fail("The test case is empty.");
	}


	/**
	 * Test of forMacroCallRule method, of class
	 * atgt.coverage.BasicRuleVisitor.
	 */
	@Test void chooseRule() {
		EnumConstCreator ecc = new  EnumConstCreator();
		IdExpression x = ecc.createIdExpression("x", null);
		Expression a = ecc.createEnumConst("a");
		IdExpression b = ecc.createEnumConst("b");
		IdExpression c = ecc.createIdExpression("c", null);
		IdExpression d = ecc.createIdExpression("d", null);
		IdExpression e = ecc.createIdExpression("e", null);
		// case 1
		//		choose x in {a,b} with TRUE  do skip
		ChooseRule cr = new ChooseRule(x, Arrays.asList(a,b),BoolType.TRUE_CONST, Skip.SKIP);
		List<NamedTerm> tps = cr.accept(new BasicRuleVisitor());
		// no tp in this case
		assertEquals(0,tps.size());
		// case 2
		//		choose x in {a,b} with c  do skip
		cr = new ChooseRule(x, Arrays.asList(a,b),c, Skip.SKIP);
		tps = cr.accept(new BasicRuleVisitor());
		// 1 tp in this case : c
		assertEquals(1,tps.size());		
		assertEquals("c or c",tps.get(0).getCondition().toString());
		// case 3 real rule
		// 	choose x in {a,b} with c  do if d then skip
		ConditionalRule ifr = new ConditionalRule(d, Skip.SKIP);
		cr = new ChooseRule(x, Arrays.asList(a,b),c, ifr);
		tps = cr.accept(new BasicRuleVisitor());
		// 1 tp in this case : c
		assertEquals(1,tps.size());		
		assertEquals("(c and d) or (c and d)",tps.get(0).getCondition().toString());
		// case 4 real rule
		// 	choose x in {a,b} with true  do if d then skip
		cr = new ChooseRule(x, Arrays.asList(a,b),BoolType.TRUE_CONST, ifr);
		tps = cr.accept(new BasicRuleVisitor());
		// 1 tp in this case
		assertEquals(1,tps.size());		
		assertEquals("d or d",tps.get(0).getCondition().toString());
		//
		//	choose x in {a,b} with true  do par if d then skip if e then skip 
		ConditionalRule ifr2 = new ConditionalRule(e, Skip.SKIP);
		DoStatement doS = new DoStatement();
		doS.addStatement(ifr);
		doS.addStatement(ifr2);
		cr = new ChooseRule(x, Arrays.asList(a,b),BoolType.TRUE_CONST, doS);
		tps = cr.accept(new BasicRuleVisitor());
		// 1 tp in this case
		assertEquals(2,tps.size());		
		assertEquals("d or d",tps.get(0).getCondition().toString());
		assertEquals("e or e",tps.get(1).getCondition().toString());
		//	choose x in {a,b} with c  do par if d then skip if e then skip 
		cr = new ChooseRule(x, Arrays.asList(a,b),c, doS);
		tps = cr.accept(new BasicRuleVisitor());
		// 1 tp in this case
		assertEquals(2,tps.size());		
		assertEquals("(c and d) or (c and d)",tps.get(0).getCondition().toString());
		assertEquals("(c and e) or (c and e)",tps.get(1).getCondition().toString());
		
	}

	
}
