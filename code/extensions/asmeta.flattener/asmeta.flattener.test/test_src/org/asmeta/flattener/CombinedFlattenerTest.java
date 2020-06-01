package org.asmeta.flattener;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.junit.Test;

public class CombinedFlattenerTest extends FlattenerTest {

	@Test
	public void testConwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", MacroCallRuleFlattener.class,ForallRuleFlattener.class);
		//flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", ALL_FLATTENERS);
	}

	@Test
	public void testForallRuleChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", MacroCallRuleFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	public void testForallRuleChoose2() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	public void testForallRuleChoose3() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class, ForallRuleFlattener.class, ChooseRuleFlattener.class);
	}

	@Test(expected = AssertionError.class)
	public void testForallRuleChoose4() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", ChooseRuleFlattener.class);
	}

	@Test(expected = AssertionError.class)
	public void testCombinedFlattenerAll() throws Exception {
		flattenerTestAllCombinations("./examples/forallRuleChoose.asm", ALL_FLATTENERS);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTicTacToeAll() throws Exception {
		flattenerTest(examplesDir + "examples/ticTacToe/ticTacToe_simulator.asm",
				ALL_FLATTENERS);
	}

	@Test
	public void testCombinedRushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm",
				MacroCallRuleFlattener.class,
				ForallRuleFlattener.class,
				RemoveNestingFlattener.class,
				LetRuleFlattener.class,
				RemoveArgumentsFlattener.class
				);
	}

	@Test
	public void testForallChoose() throws Exception {
		flattenerTest("./examples/forallChooseRule.asm", ChooseRuleFlattener.class, ForallRuleFlattener.class);
	}

	@Test
	public void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", CaseRuleFlattener.class, RemoveNestingFlattener.class);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				RemoveArgumentsFlattener.class,
				LetRuleFlattener.class,
				ChooseRuleFlattener.class,
				ForallRuleFlattener.class,
				MacroCallRuleFlattener.class
				);
	}
	
	@Test
	public void testAbstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", ALL_FLATTENERS);
	}

	@Test
	public void testChooseRule() throws Exception {
		flattenerTest("./examples/chooseRule.asm", ALL_FLATTENERS);
	}

	@Test
	public void testMacroChooseRule() throws Exception {
		flattenerTest("./examples/macroRuleChoose.asm", ALL_FLATTENERS);
	}

	@Test
	public void testTicTacToe() throws Exception {
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
