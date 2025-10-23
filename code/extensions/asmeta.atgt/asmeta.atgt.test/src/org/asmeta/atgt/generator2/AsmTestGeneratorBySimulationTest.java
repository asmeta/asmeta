package org.asmeta.atgt.generator2;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.testseqexport.toAvalla;
import tgtlib.definitions.expression.type.Variable;

public class AsmTestGeneratorBySimulationTest {

	@Test
	public void testRandom2() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/RegistroDiCassav4.asm"));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm,4,4);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		// bug outMess=Scegli il tipo di pizza desiderata:
		Map<? extends Variable, String> state = test.getState(1);
		// TODO
		System.err.println(state);
	}
	@Test
	public void testRandomNSTEP() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/RegistroDiCassav4.asm"));
		assertNotNull(asm);
		// if 0, only 1 state
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm,0,1);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		assertEquals(test.allInstructions().size(), 1);
		//
		gen = new AsmTestGeneratorBySimulation(asm,1,1);
		test = gen.getTestSuite().getTests().get(0);
		assertEquals(test.allInstructions().size(), 2);

		toAvalla to = new
		
	}

}
