package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// 
class TestAssignementConcretedomain {


	@Test void test() throws Exception{
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgn.asm");
		assertThrows(Exception.class, () ->	sim.run(1));
	}

	@Test void any() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgnAny.asm");
		assertThrows(Exception.class, () ->
			sim.run(1));
	}

	@Test void union() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/concreteAssgnAnyunion.asm");
		assertThrows(Exception.class, () ->
			sim.run(1));
	}

	@Test void anyWrong() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny.asm");
		assertThrows(RuntimeException.class, () ->
			sim.run(1));
	}

	@Test void anyCorrect() throws Exception {
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny2.asm");
		sim.run(1);		
		sim = Util.getSimulatorForTestSpec("test/simulator/anydomain/AssignmentAny3.asm");
		sim.run(1);		
	}
	
	
}
