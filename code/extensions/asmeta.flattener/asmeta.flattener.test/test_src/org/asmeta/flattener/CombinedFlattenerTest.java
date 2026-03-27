package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.junit.jupiter.api.Test;

class CombinedFlattenerTest extends FlattenerTest {

	@Test
	void conwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", MacroCallRuleFlattener.class,ForallRuleFlattener.class);
		//flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", ALL_FLATTENERS);
	}

	@Test
	void forallRuleChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", MacroCallRuleFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	void forallRuleChoose2() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	void forallRuleChoose3() throws Exception {
		//flattenerTest("./examples/forallRuleChoose.asm",ChooseRuleFlattener.class);
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class, ForallRuleFlattener.class, ChooseRuleFlattener.class);
	}

	// I'm not use it is expected to fail this one or it should pass
	@Test
	void forallRuleChoose4() throws Exception {
		assertThrows(AssertionError.class, () ->
			flattenerTest("./examples/forallRuleChoose.asm", ChooseRuleFlattener.class));
	}

	@Test
	void combinedFlattenerAll() throws Exception {
		assertThrows(AssertionError.class, () ->
			flattenerTestAllCombinations("./examples/forallRuleChoose.asm", ALL_FLATTENERS));
	}

	@Test
	void ticTacToeAll() throws Exception {
		assertThrows(RuntimeException.class, () ->
			flattenerTest(examplesDir + "examples/ticTacToe/ticTacToe_simulator.asm",
				ALL_FLATTENERS));
	}

	@Test
	void combinedRushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm",
				MacroCallRuleFlattener.class,
				ForallRuleFlattener.class,
				RemoveNestingFlattener.class,
				LetRuleFlattener.class,
				RemoveArgumentsFlattener.class
				);
	}

	@Test
	void forallChoose() throws Exception {
		flattenerTest("./examples/forallChooseRule.asm", ChooseRuleFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", CaseRuleFlattener.class, RemoveNestingFlattener.class);
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				RemoveArgumentsFlattener.class,
				LetRuleFlattener.class,
				ChooseRuleFlattener.class,
				ForallRuleFlattener.class,
				MacroCallRuleFlattener.class
				);
	}

	@Test
	void abstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", ALL_FLATTENERS);
	}

	@Test
	void chooseRule() throws Exception {
		flattenerTest("./examples/chooseRule.asm", ALL_FLATTENERS);
	}

	@Test
	void macroChooseRule() throws Exception {
		flattenerTest("./examples/macroRuleChoose.asm", ALL_FLATTENERS);
	}

	@Test
	void ticTacToe() throws Exception {
		flattenerTest(examplesDir + "examples/ticTacToe/ticTacToe_simulator.asm",
				MacroCallRuleFlattener.class,
				ForallRuleFlattener.class,
				RemoveNestingFlattener.class,
				//ChooseRuleFlattener.class,
				LetRuleFlattener.class,
				RemoveArgumentsFlattener.class,
				CaseRuleFlattener.class);
	}
}
