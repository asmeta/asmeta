package org.asmeta.simulator.main;

import static org.junit.Assert.fail;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.readers.RandomMFReader;
import org.junit.Test;

import asmeta.AsmCollection;

public class AsmetaSimulatorWRTest extends BaseTest{

	@Test
	public void testDoOneStep() throws Exception {
		AsmCollection asmc = ASMParser.setUpReadAsm(new File(ASM_EXAMPLES + "examples/simple_example/AdvancedClock.asm"));

		AsmetaSimulatorWR asm = new AsmetaSimulatorWR("AdvancedClock", asmc, new Environment(new RandomMFReader()));
		asm.doOneStep();
		System.out.println(asm.previousState);
		System.out.println("new state::");
		System.out.println(asm.getCurrentState());
		asm.doOneStep();
		System.out.println(asm.getCurrentState());
		System.out.println("rollback::");
		asm.rollBack();
		System.out.println(asm.getCurrentState());
		asm.doOneStep();
		System.out.println(asm.getCurrentState());

	}

}
