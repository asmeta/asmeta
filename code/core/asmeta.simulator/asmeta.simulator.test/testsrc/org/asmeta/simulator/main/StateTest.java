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

import java.util.Arrays;
import java.util.HashSet;

import org.asmeta.parser.Defs;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.domains.Domain;

/**
 * Test for State class.
 *
 */
public class StateTest {
	
	Simulator sim;

	@Test
	public void testReadDomain() 
	throws Exception {
		ReserveValue.nextSuffix = 0;
		
		sim = Util.getSimulatorForTestSpec("test/simulator/domains/concreteDomain.asm");
		sim.run(1);
		
		Domain dom = Defs.searchDomain(sim.asmModel, "B");
		SetValue actual = sim.currentState.read(dom);
		ReserveValue[] oo = new ReserveValue[]{
				new ReserveValue("B!1"),
				new ReserveValue("B!2"),
				new ReserveValue("B!3")
		};
		SetValue expected = new SetValue(new HashSet<Value>(Arrays.asList(oo)));
		assertEquals(expected, actual);
		
		dom = Defs.searchDomain(sim.asmModel, "A");
		actual = sim.currentState.read(dom);
		oo = new ReserveValue[]{
				new ReserveValue("B!1"),
				new ReserveValue("B!2"),
				new ReserveValue("B!3"),
				new ReserveValue("A!1")
		};
		expected = new SetValue(new HashSet<Value>(Arrays.asList(oo)));
		assertEquals(expected, actual);
	}
}
