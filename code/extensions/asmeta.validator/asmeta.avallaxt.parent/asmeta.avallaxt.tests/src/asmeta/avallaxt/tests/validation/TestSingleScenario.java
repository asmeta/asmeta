package asmeta.avallaxt.tests.validation;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class TestSingleScenario extends TestParserAndValidation {

	@Test
	public void checkExampleBlock() {
		assertTrue(test("example/block1.avalla", PossibleFaults.NONE));
		assertTrue(test("example/block2.avalla", PossibleFaults.NONE));
		assertTrue(test("example/block3.avalla", PossibleFaults.NONE));
	}

	@Test
	public void checkExampleBlockErrors() {
		// with semantic errors
		// assertTrue(test("example/block_w1.avalla", PossibleFaults.OTHERS));
		// assertTrue(test("example/block_w2.avalla", PossibleFaults.OTHERS));
		// assertTrue(test("example/block_w3.avalla", PossibleFaults.OTHERS));
		// assertTrue(test("example/block_w4.avalla", PossibleFaults.OTHERS));
		assertTrue(test("example/block_w5.avalla", PossibleFaults.OTHERS));
	}

	@Test
	public void checkExampleLift() {
		assertTrue(test("example/lift_wrong1.avalla", PossibleFaults.NOTEXISTS));
		assertTrue(test("example/lift_w1.avalla", PossibleFaults.NOTEXISTS));
		assertTrue(test("example/lift_shared.avalla", PossibleFaults.NONE));
	}

	@Test
	public void checkBattleShip() {
		assertTrue(test("example/battleship1.avalla", PossibleFaults.NOTEXISTS));
		assertTrue(test("example/battleship2.avalla", PossibleFaults.NOTEXISTS));
	}

	@Test
	public void checkSLE() {
		assertTrue(test("..\\..\\..\\..\\asm_examples\\examples\\fsmsemantics\\Sle\\testEven1.avalla",PossibleFaults.NONE));
		assertTrue(test("../../../../asm_examples/examples/fsmsemantics/Sle/testEven1.avalla",PossibleFaults.NONE));
	}

	// PROBLEMATICS
	@Test
	public void testproblematic() throws IOException {
		test("../../../../asm_examples/DAS/TrafficMonitoringSystem/scenario1.avalla", PossibleFaults.NONE);
		// test("../../../../asm_examples/examples/fsmsemantics/Sle/testEven1.avalla",
		// PossibleFaults.NONE);// SOLVED
	}
	
	// ABZ2020
	@Test
	public void testABZ2020() throws IOException {
		//test("example/abz2020/scenarios/scenario001CarSystem001.avalla", PossibleFaults.NONE);
		//test("example/abz2020/scenarios/wExecBlock.avalla", PossibleFaults.NONE);
		// test("../../../../asm_examples/examples/fsmsemantics/Sle/testEven1.avalla",
		// PossibleFaults.NONE);// SOLVED
		test("example/abz2020/CarSystem006scenario003.avalla", PossibleFaults.NONE);
	}

	@Test
	public void checkImportImport() {
		assertTrue(test("example/importimport.avalla", PossibleFaults.NONE));
	}

	
	
	
	
}
