package org.asmeta.flattener;

import org.asmeta.flattener.rule.LetRuleFlattener;
import org.junit.Test;

public class LetRuleFlattenerTest extends FlattenerTest {

	@Test
	public void testLetRule() throws Exception {
		flattenerTest("./examples/letRule.asm", LetRuleFlattener.class);
	}

	@Test
	public void testLetRule2() throws Exception {
		flattenerTest("./examples/letRule2.asm", LetRuleFlattener.class);
	}

	@Test
	public void testLetRule3() throws Exception {
		flattenerTest("./examples/letRule3.asm", LetRuleFlattener.class);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm", LetRuleFlattener.class);
	}
}
