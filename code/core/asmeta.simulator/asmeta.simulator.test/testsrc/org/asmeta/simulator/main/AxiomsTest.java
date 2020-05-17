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
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;

public class AxiomsTest extends BaseTest {

	@Test
	public void testInitStateViolation() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/initStateViolation.asm");
		sim.run(10);//no step is executed
		Function foo = searchFunction("foo");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(BooleanValue.FALSE, value);
	}

	@Test
	public void testInitStateMonAxiomViolation() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/initStateMonAxiomViolation.asm",
										BASE + "test/simulator/axioms/initStateMonAxiomViolation.env");
		
		sim.run(3);//no step is executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(BooleanValue.TRUE, value);
	}

	@Test
	public void testContrStateViolationViolation() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/contrStateViolation.asm",
										BASE + "test/simulator/axioms/contrStateViolation.env");
		sim.run(10);//just 2 steps are executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		//Since an invariant has been violated, the execution has been stopped
		//and the last value of fooA is 2.
		assertEquals(2l, ((IntegerValue)value).getValue().longValue());
	}

	@Test
	public void testMonStateViolation() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/monStateViolation.asm",
										BASE + "test/simulator/axioms/monStateViolation.env");
		sim.run(10);//Just 3 steps are executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(3l, ((IntegerValue)value).getValue().longValue());
	}

	@Test
	public void testRunUntilEmptyAxiomViolation() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/runUntilEmptyAxiomViolation.asm");
		sim.runUntilEmpty();
	}

	@Test
	public void testAxiom_example() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/axioms/axiom_example.asm",
										BASE + "test/simulator/axioms/axiom_example.env");
		sim.run(10);//just 2 steps are executed. After two steps the invariant is violated
		Function fooA = searchFunction("fooA");
		Value valueA = sim.currentState.read(new Location(fooA, new Value[0]));
		assertEquals(2l, ((IntegerValue)valueA).getValue().longValue());
		Function fooB = searchFunction("fooB");
		Value valueB = sim.currentState.read(new Location(fooB, new Value[0]));
		assertEquals(2l, ((IntegerValue)valueB).getValue().longValue());
	}
}
