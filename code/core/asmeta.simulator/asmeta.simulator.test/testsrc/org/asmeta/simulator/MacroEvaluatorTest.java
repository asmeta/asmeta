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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.asmeta.simulator.main.Simulator;
import org.asmeta.simulator.main.Util;
import org.junit.jupiter.api.Test;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Test for RuleEvaluater.macros attribute.
 *
 */
class MacroEvaluatorTest {

	private Simulator sim;

	@Test void test01() throws Throwable {
		// ge the macros before
		HashMap<String, Rule> macros = RuleEvaluator.macros;
		int oldSize = macros.size();
		sim = Util.getSimulatorForTestSpec("test/simulator/macro/macro09.asm");
		sim.run(1);
		assertEquals(4 + oldSize, macros.size());
		assertTrue(macros.containsKey("macro09::r_foo[\"hello\"]"));
		assertTrue(macros.containsKey("macro09::r_bar[\"hello\",macro10::punc]"));
		assertTrue(macros.containsKey("macro09::r_odd[macro10::func(\"hello\"),macro10::punc,true]"));
		assertTrue(macros.containsKey("macro09::r_fool[<<macro10::r_macro>>]"));
		
	}

}
