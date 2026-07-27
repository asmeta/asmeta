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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import atgt.specification.ASMSpecification;
import tgtlib.definitions.NamedTerm;
import tgtlib.specification.ParseException;

/**
 * Every decision must be takena and not 
 * 
 * @author garganti
 */
public class DecisionVisitorTest extends RuleTest{

	/**
	 * Test of forSkip method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	//@Test
	public void testForSkip() {
		System.out.println("testForSkip");

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

	/**
	 * Test of forAssignment method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	public void testForAssignment() {
		System.out.println("testForAssignment");

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

	/**
	 * Test of forDoStatement method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	public void testForDoStatement() {
		System.out.println("testForDoStatement");

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

	/**
	 * Test of forIfThenElse method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	@Test
	public void testForIfThenElse() {
		List<NamedTerm> results = DecisionVisitor.computeDecisions.forIfThenElse(if_woelse);
		assertEquals(1,results.size());
		assertEquals("T", results.get(0).getName());
		assertEquals("A or B",results.get(0).getCondition().toString());
		// else
		assertNotNull(if_wemptyelse.getElsePart());
		results = DecisionVisitor.computeDecisions.forIfThenElse(if_wemptyelse);
		assertEquals(1,results.size());
		assertEquals("T: A and B", results.get(0).toString());
		// nested else
		results = DecisionVisitor.computeDecisions.forIfThenElse(c_wnestedelse);
		assertEquals(1,results.size());
		assertEquals("TT: A and (A and B)", results.get(0).toString());
	}

	/**
	 * Test of forMacroCallRule method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	public void testForMacroCallRule() {
		System.out.println("testForMacroCallRule");

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

	/**
	 * Test of forRule method, of class
	 * atgt.coverage.DecisionVisitor.
	 */
	public void testForRule() {
		System.out.println("testForRule");

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

	/**
	 * Test of forSpecification method, of class
	 * atgt.coverage.DecisionVisitor.
	 * @throws ParseException 
	 */
	public void testForSpecification() throws ParseException {

		String filenameCounter1 = "SIS.asm";

		ASMSpecification SP;
		java.io.File f = new java.io.File(filenameCounter1);
		atgt.parser.asmeta.AsmetaLLoader xmipar = new atgt.parser.asmeta.AsmetaLLoader();

		SP = xmipar.read(f);

		List<NamedTerm> decs = DecisionVisitor.computeDecisions.analyze(SP);
		for (NamedTerm tc : decs) {
			System.out.println("decision -> " + tc.toString());
		}

		// TODO add your test code below by replacing the default call to fail.
		fail("The test case is empty.");
	}

}
