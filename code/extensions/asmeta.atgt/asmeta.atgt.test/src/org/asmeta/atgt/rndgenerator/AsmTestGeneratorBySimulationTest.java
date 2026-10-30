package org.asmeta.atgt.rndgenerator;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import org.asmeta.atgt.generator.AsmTestGeneratorTest;
import org.asmeta.parser.ASMParser;
import org.asmeta.simulator.InvalidValueException;
import org.asmeta.simulator.RuleEvaluatorObserver;
import org.asmeta.simulator.UpdateClashException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
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

	@Test
	public void testWithChoose() throws Exception {
		String test = generateFor("examples/simplechoose.asm", true);
		System.out.println(test);
		assertTrue(test.contains("pick $i in r_Main"));
	}

	@Test
	public void testWithChoose2() throws Exception {
		generateFor(AsmTestGeneratorTest.FILE_BASE + "examples\\fsmsemantics\\FSM_hooking.asm", true);
	}

	@Test
	public void testproblematic() throws Exception {
		generateFor(AsmTestGeneratorTest.FILE_BASE + "examples\\NeedhamSchroeder\\NeedhamSchroederWithSpy.asm", true);
	}

	@Test
	public void testproblematic2() throws Exception {
		// value out of domain: cannot assign 740 to timer --> the spec has an error in
		// it
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = true;
		try {
			generateFor(AsmTestGeneratorTest.FILE_BASE + "erinda\\FMS_IO_2.asm", true);
		} catch (InvalidValueException ie) {
			assertTrue(ie.getMessage().equals("value out of domain: cannot assign 740 to timer"));
		}
		// if it continuew wven with the error
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		String test = generateFor(AsmTestGeneratorTest.FILE_BASE + "erinda\\FMS_IO_2.asm", true);
		assertTrue(test.contains("check timer ="));
	};

	@Test
	public void testproblematicInvViolation() throws Exception {
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		// Invariant violation
		// careful they can be flacky !!!
		String test = generateFor(AsmTestGeneratorTest.FILE_BASE + "examples\\railRoadGate\\railroadGate.asm", true);
		assertTrue(test.contains("Invariant violation"));
		test = generateFor(AsmTestGeneratorTest.FILE_BASE + "examples\\ferryman\\ferrymanSimulator.asm", true);
		assertTrue(test.contains("Invariant violation"));
	}

	@Test
	public void testproblematicIncosistentUpdate() throws Exception {
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = true;
		// careful they can be flacky !!!
		String spec = "examples\\smartHome\\smartHomeNoMultiChannel.asm";
		try {
			generateFor(AsmTestGeneratorTest.FILE_BASE + spec, true);
		} catch (UpdateClashException e) {
		}
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		// careful they can be flacky !!!
		String test = generateFor(
				AsmTestGeneratorTest.FILE_BASE + spec, true);
		System.err.println(test);
		assertTrue(test.contains("Incosistent update"));
	}
	@Test
	public void testproblematic3() throws Exception {
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		String spec = "examples\\sluicegate\\sluiceGate_with_agents.asm";
		generateFor(AsmTestGeneratorTest.FILE_BASE + spec, true);
		AsmTestGeneratorBySimulation.THROW_EXCEPTION_WHEN_ERROR_FOUND = false;
		String test = generateFor(AsmTestGeneratorTest.FILE_BASE + spec, true);
		// empty test
		System.err.println(test);
		assertTrue(test.contains("Incosistent update"));
	}

	
	
	static String generateFor(String pathname, boolean shuffle) throws Exception {
		AsmCollection asm = ASMParser.setUpReadAsm(new File(pathname));
		assertNotNull(asm);
		AsmTestGeneratorBySimulation gen = new AsmTestGeneratorBySimulation(asm, 40, 1);
		gen.shuffle = shuffle;
		AsmTestSuite testSuiteException = gen.getTestSuiteException(shuffle);
		AsmTestSequence test = testSuiteException.getTests().get(0);
		int last = test.allInstructions().size() - 1;
		Map<? extends Variable, String> state = test.getState(last);
		// TODO add the assert
		// System.err.println("STATE " + state);
		//
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		toAvalla ta = new toAvalla(output, test, "filename", "scenarioname");
		ta.saveToStream();
		return output.toString();
	}

}
