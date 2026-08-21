package org.asmeta.flattener;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
	
	@Test
	void letRule4() throws Exception {
		FlattenerSetting.simplify = true;
		//Logger.getLogger(LetRuleFlattener.class).setLevel(Level.DEBUG);
		String res = flattenerTest("examples/letRule4Sluicegate.asm", LetRuleFlattener.class);
		assertFalse(res.contains("eq(top,bottom)"));
		assertFalse(res.contains("eq(top,top)"));
	}

}
