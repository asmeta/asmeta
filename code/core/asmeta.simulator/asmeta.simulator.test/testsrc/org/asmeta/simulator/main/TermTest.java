package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.main.Simulator.InvariantTreament;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;

public class TermTest extends BaseTest {

	@Test
	public void test01() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/terms/existUniqueTerm.asm");
		Simulator.checkInvariants = InvariantTreament.CHECK_STOP;
		sim.run(1);
		State state = sim.getCurrentState();
		Function func = searchFunction("foo3");
		Value value = state.read(new Location(func, new Value[]{}));
		assertEquals("true", value.toString());
		func = searchFunction("foo4");
		value = state.read(new Location(func, new Value[]{}));
		assertEquals("true", value.toString());
		func = searchFunction("foo5");
		value = state.read(new Location(func, new Value[]{}));
		assertEquals("true", value.toString());
		func = searchFunction("foo6");
		value = state.read(new Location(func, new Value[]{}));
		assertEquals("true", value.toString());
	}
}
