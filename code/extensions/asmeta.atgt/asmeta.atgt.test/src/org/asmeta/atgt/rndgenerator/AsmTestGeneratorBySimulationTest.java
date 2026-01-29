package org.asmeta.atgt.rndgenerator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.asmeta.atgt.generator.AsmTestGeneratorTest;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.testseqexport.toAvalla;
import tgtlib.definitions.expression.type.Variable;

public class AsmTestGeneratorBySimulationTest {

	@Test
	public void testRandom2() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/RegistroDiCassav4.asm"));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm, 4, 4);
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
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testRandomChoosOne() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File("examples/forallChooseRule_flat.asm"));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm, 4, 4);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		// bug outMess=Scegli il tipo di pizza desiderata:
		Map<? extends Variable, String> state = test.getState(1);
		// TODO
		System.err.println(state);
	}

	@Test
	public void testRandomExtend() throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(
				new File(AsmTestGeneratorTest.FILE_BASE + "test\\simulator\\domains\\concreteDomain2.asm"));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm, 4, 4);
		AsmTestSequence test = gen.getTestSuite().getTests().get(0);
		// bug outMess=Scegli il tipo di pizza desiderata:
		int last = test.allInstructions().size() - 1;
		Map<? extends Variable, String> state = test.getState(last);
		// TODO add the assert
		System.err.println("STATE " + state);
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		toAvalla ta = new toAvalla(output, test, "filename", "scenarioname");
		ta.saveToStream();
		System.out.println(output.toString());
	}

}
