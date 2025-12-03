package org.asmeta.atgt.rndgenerator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Map;

import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
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
		// if 0 steps, only 1 state
		int nSteps = 0;
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm, nSteps, 1);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		test.getState(nSteps); // No exception
		try {
			test.getState(nSteps + 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
		// if 1 step, 2 states
		nSteps = 1;
		gen = new AsmTestGeneratorBySimulation(asm, nSteps, 1);
		test = gen.getTestSuite().getTests().get(0);
		test.getState(nSteps); // No exception
		try {
			test.getState(nSteps + 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
		// if 10 step, 11 states
		nSteps = 10;
		gen = new AsmTestGeneratorBySimulation(asm, nSteps, 1);
		test = gen.getTestSuite().getTests().get(0);
		test.getState(nSteps); // No exception
		try {
			test.getState(nSteps + 1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
	}
	@Test
	public void testRandomChoosOne() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/forallChooseRule_flat.asm"));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm,4,4);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		// bug outMess=Scegli il tipo di pizza desiderata:
		Map<? extends Variable, String> state = test.getState(1);
		// TODO
		System.err.println(state);
	}

}
