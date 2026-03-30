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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.RuleEvaluator;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import asmeta.definitions.Function;


class AgentsTest extends BaseTest {

	@BeforeAll
	static void setuplog(){		
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.DEBUG);
	}

	@Test void test01() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/agents/agents.asm");
		sim.run(1);
		Function foo1 = searchFunction("foo1");
		Value v1 = sim.currentState.read(new Location(foo1, new Value[0]));
		Function foo2 = searchFunction("foo2");
		Value v2 = sim.currentState.read(new Location(foo2, new Value[0]));
		assertEquals(BooleanValue.FALSE, v1);
		assertEquals(BooleanValue.FALSE, v2);
	}

	@Test void test02() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/agents/agents2.asm");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
		//Function foo = searchFunction("foo");
		//Value v = sim.currentState.read(new Location(foo, new Value[0]));
		//assertEquals(BooleanValue.FALSE, v);
	}
}
