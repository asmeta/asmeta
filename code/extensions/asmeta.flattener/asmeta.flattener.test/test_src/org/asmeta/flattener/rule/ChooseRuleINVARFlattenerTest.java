package org.asmeta.flattener.rule;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.asmeta.flattener.FlattenerTest;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ChooseRuleINVARFlattenerTest extends FlattenerTest {
	


	@Test
	void choose() throws Exception {
		String res = flattenerTest("./examples/chooseRuleSimple.asm", true, ChooseRuleINVARFlattener.class);
		System.out.println(res);
		
	}

	@Test
	void chooseWithNone() throws Exception {
		flattenerTest("./examples/chooseRuleIfNone.asm", false, ChooseRuleINVARFlattener.class);
	}

	@Test
	void forallChoose() throws Exception {
		assertThrows(AssertionError.class, () ->
			flattenerTest("./examples/forallChooseRule.asm", false, ChooseRuleINVARFlattener.class));
	}

}
