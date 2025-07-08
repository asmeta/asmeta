package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.readers.RandomMFReader;
import org.junit.Test;

import asmeta.AsmCollection;

public class AsmetaSimulatorWRandom extends BaseTest{

	@Test
	public void testNoUndefMonitored() throws Exception {
		AsmCollection asmc = ASMParser.setUpReadAsm(new File(ASM_EXAMPLES + "test/simulator/UseUndefMon.asm"));
		Simulator asm = new Simulator("UseUndefMon", asmc, new Environment(new RandomMFReader()));
		for (int i = 0; i < 10; i++) {
			asm.doOneStep();
			assertEquals("c=0", asm.getCurrentState().toString());
		}
	}
	@Test
	public void testUndefMonitored() throws Exception {
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
