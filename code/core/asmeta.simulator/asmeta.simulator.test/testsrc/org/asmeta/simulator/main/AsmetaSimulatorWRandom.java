package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.readers.RandomMFReader;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;

class AsmetaSimulatorWRandom extends BaseTest{

	@Test void noUndefMonitored() throws Exception {
		AsmCollection asmc = ASMParser.setUpReadAsm(new File(ASM_EXAMPLES + "test/simulator/UseUndefMon.asm"));
		Simulator asm = new Simulator("UseUndefMon", asmc, new Environment(new RandomMFReader()));
		for (int i = 0; i < 10; i++) {
			asm.doOneStep();
			assertEquals("c=0", asm.getCurrentState().toString());
		}
	}

	@Test void undefMonitored() throws Exception {
		AsmCollection asmc = ASMParser.setUpReadAsm(new File(ASM_EXAMPLES + "test/simulator/UseUndefMon.asm"));
		RandomMFReader monFuncReader = new RandomMFReader();
		monFuncReader.allowUndefValues = true;
		Simulator asm = new Simulator("UseUndefMon", asmc, new Environment(monFuncReader));
		int i;
		for (i = 0; i < 100000; i++) {
			asm.doOneStep();
			if (!"c=0".equals(asm.getCurrentState().toString())) break;
		}
		assertNotEquals(100000, i);
	}

}
