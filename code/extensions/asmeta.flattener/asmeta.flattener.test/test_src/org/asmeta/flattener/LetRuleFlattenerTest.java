package org.asmeta.flattener;

import org.asmeta.flattener.rule.LetRuleFlattener;
import org.junit.jupiter.api.Test;

class LetRuleFlattenerTest extends FlattenerTest {

	@Test
	void letRule() throws Exception {
		flattenerTest("./examples/letRule.asm", LetRuleFlattener.class);
	}

	@Test
	void letRule2() throws Exception {
		flattenerTest("./examples/letRule2.asm", LetRuleFlattener.class);
	}

	@Test
	void letRule3() throws Exception {
		flattenerTest("./examples/letRule3.asm", LetRuleFlattener.class);
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm", LetRuleFlattener.class);
	}
}
