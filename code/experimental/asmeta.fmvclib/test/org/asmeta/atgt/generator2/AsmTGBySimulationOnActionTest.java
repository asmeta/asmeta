package org.asmeta.atgt.generator2;

import static org.junit.Assert.*;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.junit.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

public class AsmTGBySimulationOnActionTest {

	@Test
	public void test() throws Exception {
		Environment.timeMngt = TimeMngt.auto_increment;
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/aman.asm"));
		AsmTestGeneratorBySimulation atgt = new AsmTGBySimulationOnAction(asm,3,3);
		AsmTestSuite tests = atgt.getTestSuite();
		System.out.println(tests);
		
	}

}
