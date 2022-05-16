/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;

import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.TermAssignment;
import org.asmeta.simulator.TermSubstitution;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;


/**
 * Test for TermSubstitution class.
 *
 */
public class TermSubstTest {
	
	@BeforeClass
	public static void setupLogger() throws Exception{
		//AsmParserTest.setUpLogger();
	}

	private String subst(String filePath) throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(filePath);
		MacroCallRule rFoo = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		MacroCallRule rBar = (MacroCallRule) rFoo.getCalledMacro().getRuleBody();
		UpdateRule uRule = (UpdateRule) rBar.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		TermAssignment macroAssignment = new TermAssignment();
		macroAssignment.put(rBar.getCalledMacro().getVariable(), rBar.getParameters());
		TermSubstitution subst = new TermSubstitution(macroAssignment);
		TermSubstitution.varSuffix = 0;
		TermSubstitution.ruleFactory = new RuleFactory();
		Term newTerm = subst.visit(term);
		return AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(newTerm);
	}
	
	@Test
	public void test01() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst01.asm");
		assertEquals(
				"plus($x,$y)", 
				actual);
	}
	
	@Test
	public void test02() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst02.asm");
		assertEquals(
				"g(plus($x,$y),0)", 
				actual);
	}

	@Test
	public void test03() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst03.asm");
		assertEquals(
				"if eq($z,7) then mult(2,$z) else 77 endif", 
				actual);
	}

	@Test
	public void test04() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst04.asm");
		assertEquals(
				"switch $y case $z:0 case $x:1 otherwise 2 endswitch", 
				actual);
	}

	@Test
	public void test05() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst05.asm");
		assertEquals(
				"let($x!1=$x,$y!2=$y,$z!3=$z) in plus($x!1,$z) endlet", 
				actual);
	}

	@Test
	public void test06() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst06.asm");
		assertEquals(
				"(exist $x in Natural with eq($x,plus($y,$z)))", 
				actual);
	}

	@Test
	public void test07() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst07.asm");
		assertEquals(
				"{$x!1 in {1,2},$d in {3,4}| neq($x!1,$d) : mult($x!1,$d)}", 
				actual);
	}

	@Test
	public void test08() throws Exception {
		String actual = subst("test/simulator/term_subst/termsubst08.asm");
		assertEquals(
				"let($x!1=$y,$y!2=plus($x,$y)) in {$z!3 in Integer| iff(eq($x!1,$z!3),eq(plus($x,$y),$z)) : $z!3} endlet", 
				actual);
	}

}
