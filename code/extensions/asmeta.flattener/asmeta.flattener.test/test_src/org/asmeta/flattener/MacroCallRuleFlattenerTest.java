package org.asmeta.flattener;

import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.junit.jupiter.api.Test;

class MacroCallRuleFlattenerTest extends FlattenerTest {

	@Test
	void macroRule() throws Exception {
		flattenerTest("./examples/macroRule.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void forallChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void rushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				false, MacroCallRuleFlattener.class);
	}

	@Test
	void conwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void gilbreathCardTrickForAsmetaSMV() throws Exception {
		flattenerTest(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrickForAsmetaSMV.asm",
				false, MacroCallRuleFlattener.class);
	}

	@Test
	void gilbreathCardTrick() throws Exception {
		flattenerTest(examplesDir + "examples/GilbreathCardTrick/GilbreathCardTrick.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void macroChoose() throws Exception {
		flattenerTest("examples/macroChoose.asm", false, MacroCallRuleFlattener.class);
	}

	@Test
	void macroSwitchChoose() throws Exception {
		flattenerTest("examples/macroSwitchChoose.asm", false, MacroCallRuleFlattener.class);
	}

//	@Test
//	void macroSluiceGate() throws Exception {
//		String result = flattenerTest(examplesDir + "examples\\sluicegate\\sluiceGateMotorCtl.asm", MacroCallRuleFlattener.class);
//		System.out.println("MacroCallRuleFlattenerTest.macroSluiceGate result: " + result);
//	}
}
