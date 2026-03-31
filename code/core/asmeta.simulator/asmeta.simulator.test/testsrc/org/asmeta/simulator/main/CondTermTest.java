package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.Test;

import asmeta.definitions.Function;

class CondTermTest extends BaseTest {

	@Test void test01() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/incompleteCondTerm.asm");
		sim.run(1);
		State s = sim.getCurrentState();
		Function f1 = searchFunction("result1");
		Function f2 = searchFunction("result2");
		Value af1 = s.read(new Location(f1, new Value[0]));
		Value af2 = s.read(new Location(f2, new Value[0]));
		assertEquals(new IntegerValue(1), af1);
		assertEquals(UndefValue.UNDEF, af2);
	}

}