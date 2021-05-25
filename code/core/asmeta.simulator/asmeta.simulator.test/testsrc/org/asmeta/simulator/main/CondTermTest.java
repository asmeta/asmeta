package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;
import org.asmeta.parser.ParseException;
import org.asmeta.parser.Utility;

public class CondTermTest extends BaseTest {

	@Test
	public void test01() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/incompleteCondTerm.asm");
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