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
import static org.asmeta.simulator.main.BaseTest.*;

import org.asmeta.parser.util.RulePrinter;
import org.asmeta.simulator.RuleSubstitution;
import org.asmeta.simulator.TermAssignment;
import org.asmeta.simulator.TermSubstitution;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.junit.Test;

import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Test for RuleSubstitution class.
 * 
 */
public class RuleSubstTest {


	private String subst(String filePath) throws Exception {
		Simulator sim = Simulator.createSimulator(filePath);
		MacroCallRule rFoo = (MacroCallRule) sim.asmModel.getMainrule()
				.getRuleBody();
		MacroCallRule rBar = (MacroCallRule) rFoo.getCalledMacro()
				.getRuleBody();
		Rule rule = rBar.getCalledMacro().getRuleBody();
		TermAssignment macroAssignment = new TermAssignment();
		macroAssignment.put(rBar.getCalledMacro().getVariable(),
				rBar.getParameters());
		RuleSubstitution subst = new RuleSubstitution(macroAssignment,new RuleFactory());
		// NOTE: remember to reset class variables
		TermSubstitution.varSuffix = 0;
		Rule newRule = subst.visit(rule);
		return new RulePrinter(false).visit(newRule);
	}

	@Test
	public void test01() throws Exception {
		String actual = subst(BaseTest.ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst01.asm");
		assertEquals("f:=$y", actual);
	}

	@Test
	public void test02() throws Exception {
		String actual = subst(BaseTest.ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst02.asm");
		assertEquals("g($x):=plus(f,$z)", actual);
	}

	@Test
	public void test03() throws Exception {
		String actual = subst(BaseTest.ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst03.asm");
		assertEquals("par g($x):=plus(f,$z) skip endpar", actual);
	}

	@Test
	public void test04() throws Exception {
		String actual = subst(BaseTest.ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst04.asm");
		assertEquals("seq g($x):=plus(f,$z) f:=f endseq", actual);
	}

	@Test
	public void test05() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst05.asm");
		assertEquals("if eq(0,0) then f:=$z else skip endif", actual);
	}

	@Test
	public void test06() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst06.asm");
		assertEquals("switch $x case f:skip otherwise skip endswitch", actual);
	}

	@Test
	public void test07() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst07.asm");
		assertEquals(
				"let($x!1=$x,$l=77)in par g(0):=plus($x!1,$x) skip endpar endlet",
				actual);
	}

	@Test
	public void test08() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst08.asm");
		assertEquals("forall $z!1 in Integer with true do f:=$y", actual);
	}

	@Test
	public void test09() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst09.asm");
		assertEquals(
				"choose $z!1 in Integer with eq(g(0),$z) do f:=$y ifnone skip",
				actual);
	}

	@Test
	public void test10() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst10.asm");
		assertEquals("r_wer[$x,$y]", actual);
	}

	@Test
	public void test11() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst11.asm");
		assertEquals("extend A with $l do extend A with $z!1 do skip", actual);
	}

	@Test
	public void test12() throws Exception {
		String actual = subst(ASM_EXAMPLES
				+ "test/simulator/rule_subst/rulesubst12.asm");
		assertEquals("let($x!1=123)in run($x!1)[$x] endlet", actual);
	}

	@Test
	public void testWhileRuleSubstitution() throws Exception {
		//TODO what does it mean?
		// String actual = subst(BASE
		//		+ "test/simulator/rule_subst/whileRuleSubstitution.asm");
		//TODO
	}

}
