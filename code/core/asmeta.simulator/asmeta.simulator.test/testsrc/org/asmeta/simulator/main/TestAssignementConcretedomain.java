package org.asmeta.simulator.main;

import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

// 
public class TestAssignementConcretedomain {

	
	@Test(expected = Exception.class)
	public void test() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgn.asm");
		sim.run(1);
	}
	@Test(expected = Exception.class)
	public void testAny() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgnAny.asm");
		sim.run(1);
	}

	@Test(expected = Exception.class)
	public void testUnion() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgnAnyunion.asm");
		sim.run(1);		
	}

	@Test(expected = NullPointerException.class)
	public void testAnyWrong() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny.asm");
		sim.run(1);		
	}

	@Test
	public void testAnyCorrect() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny2.asm");
		sim.run(1);		
		sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny3.asm");
		sim.run(1);		
	}
	
	
}
