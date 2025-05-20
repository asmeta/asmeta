package org.asmeta.simulator.main;

import static org.junit.Assert.*;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.readers.RandomMFReader;
import org.junit.Test;

public class ParametricRuleTest {

	@Test
	public void test() throws Exception {
		Environment env = new Environment(
				new RandomMFReader());
				//new InteractiveMFReader(System.in, System.out));				
		Simulator sim = Simulator.createSimulator(TestOneSpec.FILE_BASE +
				"\\test\\simulator\\parametricrule\\ECA.asm",env);
		
		for(int i = 0 ; i < 10; i++) {
			sim.doOneStep();
			System.out.println(sim.getCurrentState());
		}


	}

}
