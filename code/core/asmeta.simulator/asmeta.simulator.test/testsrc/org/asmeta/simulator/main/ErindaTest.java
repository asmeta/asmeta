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

import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;


public class ErindaTest extends BaseTest {

	@Test
	public void test01() throws Exception {
		sim = Simulator.createSimulator(BASE+"test/simulator/agents/agents.asm");
		sim.run(1);
		Function foo1 = searchFunction("foo1");
		Value v1 = sim.currentState.read(new Location(foo1, new Value[0]));
		Function foo2 = searchFunction("foo2");
		Value v2 = sim.currentState.read(new Location(foo2, new Value[0]));
		assertEquals(BooleanValue.FALSE, v1);
		assertEquals(BooleanValue.FALSE, v2);
	}
	
	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void test02() throws Exception {
		sim = Simulator.createSimulator(BASE+"test/simulator/agents/agents2.asm");
		sim.run(1);
		//Function foo = searchFunction("foo");
		//Value v = sim.currentState.read(new Location(foo, new Value[0]));
		//assertEquals(BooleanValue.FALSE, v);
	}
}
