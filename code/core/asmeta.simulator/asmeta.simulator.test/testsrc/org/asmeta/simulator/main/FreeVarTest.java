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

import org.asmeta.simulator.VarFinder;
import org.asmeta.simulator.VariableSet;
import org.junit.Test;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

/**
 * Test for VarFinder class.
 *
 */
public class FreeVarTest {

	@Test
	public void test01() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free01.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$x"), 
				freeSet);
	}

	@Test
	public void test02() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free02.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$x", "$y"), 
				freeSet);
	}
	
	@Test
	public void test03() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free03.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$x", "$y", "$z"), 
				freeSet);
	}

	@Test
	public void test04() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free04.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$z"), 
				freeSet);
	}

	@Test
	public void test05() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free05.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$x", "$y"), 
				freeSet);
	}

	@Test
	public void test06() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free06.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet(), 
				freeSet);
	}

	@Test
	public void test07() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free07.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$y", "$z"), 
				freeSet);
	}

	@Test
	public void test08() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free08.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$x"), 
				freeSet);
	}

	@Test
	public void test09() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec(
				"test/simulator/free_vars/free09.asm");
		MacroCallRule mRule = (MacroCallRule) sim.asmModel.getMainrule().getRuleBody();
		UpdateRule uRule = (UpdateRule) mRule.getCalledMacro().getRuleBody();
		Term term = uRule.getUpdatingTerm();
		VarFinder finder = new VarFinder();
		VariableSet freeSet = finder.visit(term);
		assertEquals(
				new VariableSet("$z"), 
				freeSet);
	}

}
