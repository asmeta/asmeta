package org.asmeta.flattener.rule;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.asmeta.flattener.FlattenerTest;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.junit.jupiter.api.Test;

class ChooseRuleFlattenerTest extends FlattenerTest {

	@Test
	void choose() throws Exception {
		flattenerTest("./examples/chooseRule.asm", false, ChooseRuleFlattener.class);
	}

	@Test
	void chooseWithNone() throws Exception {
		flattenerTest("./examples/chooseRuleIfNone.asm", false, ChooseRuleFlattener.class);
	}

	@Test
	void forallChoose() throws Exception {
		assertThrows(AssertionError.class, () ->
			flattenerTest("./examples/forallChooseRule.asm", false, ChooseRuleFlattener.class));
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				false, ChooseRuleFlattener.class);
	}
}
