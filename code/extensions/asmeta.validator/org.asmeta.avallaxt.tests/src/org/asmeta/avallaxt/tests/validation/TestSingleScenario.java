package org.asmeta.avallaxt.tests.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class TestSingleScenario extends TestParserAndValidation {

	@Test
	public void checkExampleBlockNoError() {
		assertSame(checkPossibleFaults("example/block1.avalla"),PossibleFaults_NONE);
		assertTrue(checkPossibleFaults("example/block2.avalla") == PossibleFaults_NONE);
		assertTrue(checkPossibleFaults("example/block3.avalla") == PossibleFaults_NONE);
	}

	@Test
	public void checkExampleBlockErrors() {
		// with semantic errors
		assertEquals("ERROR block blocco5 does not exist in this scenario",checkPossibleFaults("example/block_w1.avalla"));
		assertEquals("ERROR block blocco1 declared multiple times",checkPossibleFaults("example/block_w2.avalla"));
		assertEquals("ERROR block blocco1 declared multiple times",checkPossibleFaults("example/block_w3.avalla"));
		assertEquals("ERROR scenario blockNOTEXISTS does not exist",checkPossibleFaults("example/block_w4.avalla"));
		assertEquals("ERROR scenario block1 does not contain block blocco5",checkPossibleFaults("example/block_w5.avalla"));
	}

	@Test
	public void checkExampleLift() {
		assertEquals("ERROR Asm spec should end with asm",checkPossibleFaults("example/lift_wrong1.avalla"));
		// it may be \\ instead of / in windows - use dth strandrd name
		assertEquals("ERROR File LiftNotExists.asm does not exist as example"+ File.separator+"LiftNotExists.asm",checkPossibleFaults("example/lift_wrong2.avalla"));
		assertEquals(checkPossibleFaults("example/lift_shared.avalla"),PossibleFaults_NONE);
	}

	@Test
	public void checkBattleShip() {
		assertEquals(checkPossibleFaults("example/battleship1.avalla"),PossibleFaults_NONE);
		assertEquals(checkPossibleFaults("example/battleship2.avalla"),PossibleFaults_NONE);
		assertEquals(checkPossibleFaults("example/battleship3.avalla"),PossibleFaults_NONE);
	}

	@Test
	public void checkSLE() {
		assertEquals(checkPossibleFaults("../../../../asm_examples/examples/fsmsemantics/Sle/testEven1.avalla"),PossibleFaults_NONE);
	}

	// PROBLEMATICS
	@Test
	public void testproblematic() throws IOException {
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
		//boolean test = (checkPossibleFaults("example/abz2020/CarSystem006scenario003.avalla") == PossibleFaults_NONE);
	}

	@Test
	public void checkImportImport() {
		assertTrue(checkPossibleFaults("example/importimport.avalla") == PossibleFaults_NONE);
	}

	
	@Test
	public void checkForall() {
		assertTrue(checkPossibleFaults("example/lift_forall.avalla") == PossibleFaults_NONE);
	}

	
	@Test
	public void checkForFlaky() {
		String baseFolder = "../asmeta.validator.test/scenariosfortest/flaky/coffee_vending_machine/";
		// correct scenario
		assertSame(checkPossibleFaults(baseFolder + "/scenario_noflaky.avalla"),PossibleFaults_NONE);
		// correct scenario with a pick of a variable defined in a choose rule that defines more than one variable
		assertSame(checkPossibleFaults(baseFolder + "/scenario_noflaky2.avalla"),PossibleFaults_NONE);
		// with a missing $
		assertSame(checkPossibleFaults(baseFolder + "/scenario_noflaky_PARS_ERR.avalla"), PossibleFaults_Parser);
		// with a pick of a variable in a non existing rule
		String error_msg = checkPossibleFaults(baseFolder + "/scenario_noflaky_VAL_ERR.avalla");
		assertNotEquals(error_msg, PossibleFaults_NONE);
		assertNotEquals(error_msg, PossibleFaults_Parser);
		assertTrue(error_msg.length() > 0);
		// with a pick of a variable not matching any choose variable in the asm
		error_msg = checkPossibleFaults(baseFolder + "/scenario_noflaky_VAL_ERR2.avalla");
		assertNotEquals(error_msg, PossibleFaults_NONE);
		assertNotEquals(error_msg, PossibleFaults_Parser);
		assertTrue(error_msg.length() > 0);
	}

	
}
