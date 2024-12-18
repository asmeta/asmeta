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
import static org.junit.Assert.fail;

import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.definitions.Function;

public class AxiomsTest extends BaseTest {

	
	private void runCatchInv(Simulator sim, int ntimes) {
		// get the update set
		try{
			sim.run(ntimes);
		} catch (InvalidInvariantException e) {
			return;
		}
		fail();
	}

	
	
	@Test
	public void testInitStateViolation() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/initStateViolation.asm");
		runCatchInv(sim,10);//no step is executed
		Function foo = searchFunction("foo");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(BooleanValue.FALSE, value);
	}

	@Test
	public void testInitStateMonAxiomViolation() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/initStateMonAxiomViolation.asm",
										ASM_EXAMPLES + "test/simulator/axioms/initStateMonAxiomViolation.env");
		
		runCatchInv(sim,3);//no step is executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(BooleanValue.TRUE, value);
	}

	@Test
	public void testContrStateViolationViolation() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/contrStateViolation.asm",
										ASM_EXAMPLES + "test/simulator/axioms/contrStateViolation.env");
		runCatchInv(sim,10);//just 2 steps are executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		//Since an invariant has been violated, the execution has been stopped
		//and the last value of fooA is 2.
		assertEquals(2l, ((IntegerValue)value).getValue().longValue());
	}

	@Test
	public void testMonStateViolation() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/monStateViolation.asm",
										ASM_EXAMPLES + "test/simulator/axioms/monStateViolation.env");
		runCatchInv(sim, 10);//Just 3 steps are executed
		Function foo = searchFunction("fooA");
		Value value = sim.currentState.read(new Location(foo, new Value[0]));
		assertEquals(3l, ((IntegerValue)value).getValue().longValue());
	}

	@Test(expected = InvalidInvariantException.class)
	public void testRunUntilEmptyAxiomViolation() throws Exception {
		InvariantTreament olc_c = Simulator.checkInvariants;
		try{
			Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
			sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/runUntilEmptyAxiomViolation.asm");
			sim.runUntilEmpty();
		}finally{
			Simulator.checkInvariants = olc_c;					
		}
	}
	@Test
	public void testRunUntilEmptyAxiomViolationNoStop() throws Exception {		
		InvariantTreament olc_c = Simulator.checkInvariants;
		// no check -> continue till asked (20 states)
		Simulator.checkInvariants = InvariantTreament.NO_CHECK;
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/runUntilEmptyAxiomViolation.asm");
		sim.run(20);
		assertEquals(20, sim.getNumOfState());
		// check and continue -> stops after 9
		Simulator.checkInvariants = InvariantTreament.CHECK_CONTINUE;
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/runUntilEmptyAxiomViolation.asm");
		sim.run(20);
		assertEquals(20, sim.getNumOfState());
		Simulator.checkInvariants = olc_c;		
	}

	@Test
	public void testAxiom_example() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/axioms/axiom_example.asm",
										ASM_EXAMPLES + "test/simulator/axioms/axiom_example.env");
		runCatchInv(sim, 10);//just 2 steps are executed. After two steps the invariant is violated
		Function fooA = searchFunction("fooA");
		Value valueA = sim.currentState.read(new Location(fooA, new Value[0]));
		assertEquals(2l, ((IntegerValue)valueA).getValue().longValue());
		Function fooB = searchFunction("fooB");
		Value valueB = sim.currentState.read(new Location(fooB, new Value[0]));
		assertEquals(2l, ((IntegerValue)valueB).getValue().longValue());
	}
}
