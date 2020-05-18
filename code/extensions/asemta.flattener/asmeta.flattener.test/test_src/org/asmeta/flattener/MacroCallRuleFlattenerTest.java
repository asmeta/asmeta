package org.asmeta.flattener;

import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.junit.Test;

public class MacroCallRuleFlattenerTest extends FlattenerTest {

	@Test
	public void testMacroRule() throws Exception {
		flattenerTest("./examples/macroRule.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testForallChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testRushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				MacroCallRuleFlattener.class);
	}

	@Test
	public void testConwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testGilbreathCardTrickForAsmetaSMV() throws Exception {
		flattenerTest(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrickForAsmetaSMV.asm",
				MacroCallRuleFlattener.class);
	}

	@Test
	public void testGilbreathCardTrick() throws Exception {
		flattenerTest(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrick.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testMacroChoose() throws Exception {
		flattenerTest("examples/macroChoose.asm", MacroCallRuleFlattener.class);
	}

	@Test
	public void testMacroSwitchChoose() throws Exception {
		flattenerTest("examples/macroSwitchChoose.asm", MacroCallRuleFlattener.class);
	}
}
