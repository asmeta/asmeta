package org.asmeta.flattener;

import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.junit.Test;

public class ChooseRuleFlattenerTest extends FlattenerTest {

	@Test
	public void testChoose() throws Exception {
		flattenerTest("./examples/chooseRule.asm", ChooseRuleFlattener.class);
	}

	@Test
	public void testChooseWithNone() throws Exception {
		flattenerTest("./examples/chooseRuleIfNone.asm", ChooseRuleFlattener.class);
	}

	@Test(expected = AssertionError.class)
	public void testForallChoose() throws Exception {
		flattenerTest("./examples/forallChooseRule.asm", ChooseRuleFlattener.class);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm",
				ChooseRuleFlattener.class);
	}
}
