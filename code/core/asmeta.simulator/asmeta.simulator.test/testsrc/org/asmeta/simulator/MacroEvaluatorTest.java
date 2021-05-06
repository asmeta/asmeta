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
package org.asmeta.simulator;

import java.util.HashMap;

import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Util;
import org.junit.Assert;
import org.junit.Test;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Test for RuleEvaluater.macros attribute.
 *
 */
public class MacroEvaluatorTest {

	private Simulator sim;

	@Test
	public void test01() throws Throwable {
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro09.asm");
		sim.run(1);
		HashMap<String, Rule> macros = RuleEvaluator.macros;
		Assert.assertEquals(4, macros.size());
		Assert.assertTrue(macros.containsKey("macro09::r_foo[\"hello\"]"));
		Assert.assertTrue(macros.containsKey("macro09::r_bar[\"hello\",macro10::punc]"));
		Assert.assertTrue(macros.containsKey("macro09::r_odd[macro10::func(\"hello\"),macro10::punc,true]"));
		Assert.assertTrue(macros.containsKey("macro09::r_fool[<<macro10::r_macro>>]"));
		
	}

}
