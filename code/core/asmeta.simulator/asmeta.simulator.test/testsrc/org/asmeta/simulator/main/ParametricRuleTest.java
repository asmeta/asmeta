package org.asmeta.simulator.main;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.asmeta.parser.Defs;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.readers.MonFuncReader;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

public class ParametricRuleTest {
	
	// with a simulated user tha opens the window
	class SimulatedUser extends MonFuncReader{
		@Override
		public Value readValue(Location location, State state) {
			if (location.getSignature().getName().equals("sgnOpenWindow"))
				return BooleanValue.TRUE;
			return BooleanValue.FALSE;
		}
		
	}


	@Test
	public void testSelfisNotNull() throws Exception {
		Environment env = new Environment(new SimulatedUser());
		String modelPath = TestOneSpec.FILE_BASE +
				"test/simulator/parametricrule/ECA.asm";
		Simulator sim = Simulator.createSimulator(modelPath,env);
		
		sim.doOneStep();
		for (Entry<Location, Value> e: sim.getCurrentState().getContrLocs().entrySet()) {
			// self is not null
			if (Defs.isSelf(e.getKey().getSignature())) {
				assertNotNull(e.getValue());
			}	
		}
	}

}
