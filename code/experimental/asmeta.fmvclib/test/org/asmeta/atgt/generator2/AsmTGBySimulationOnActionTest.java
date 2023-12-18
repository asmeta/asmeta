package org.asmeta.atgt.generator2;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.junit.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSuite;

public class AsmTGBySimulationOnActionTest {

	@Test
	public void test() throws Exception {
		String Aman0 = "../../../../asmeta_based_applications/fMVC/AMAN/model/aman0.asm";
		
		Environment.timeMngt = TimeMngt.auto_increment;
		AsmCollection asm = ASMParser.setUpReadAsm(new File(Aman0));
		List<String> asList = Arrays.asList("action");
		AsmTestGeneratorBySimulation atgt = new AsmTGBySimulationOnAction(asm,3,3, asList);
		AsmTestSuite tests = atgt.getTestSuite();
		System.out.println(tests);
		
	}

}
