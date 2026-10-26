package org.asmeta.flattener.rule;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.asmeta.flattener.FlattenerTest;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleINVARFlattener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ChooseRuleINVARFlattenerTest extends FlattenerTest {
	


	@Test
	void choose() throws Exception {
		String res = flattenerTest("./examples/chooseRuleSimple.asm", true, ChooseRuleINVARFlattener.class);
		System.out.println(res);
		
	}

	// This test is disabled because the current implementation of ChooseRuleINVARFlattener does not handle the case where none of the conditions are satisfied.
	@Test @Disabled
	void chooseWithNone() throws Exception {
		flattenerTest("./examples/chooseRuleIfNone.asm", true, ChooseRuleINVARFlattener.class);
	}

	@Test
	void forallChoose() throws Exception {
		assertThrows(AssertionError.class, () ->
			flattenerTest("./examples/forallChooseRule.asm", true, ChooseRuleINVARFlattener.class));
	}

}
