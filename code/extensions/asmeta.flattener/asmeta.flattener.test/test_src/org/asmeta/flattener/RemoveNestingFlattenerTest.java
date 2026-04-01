package org.asmeta.flattener;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.junit.jupiter.api.Test;

class RemoveNestingFlattenerTest extends FlattenerTest {

	@Test
	void forall() throws Exception {
		flattenerTest("./examples/nestedIfForall.asm", RemoveNestingFlattener.class);
	}

	@Test
	void choose() throws Exception {
		flattenerTest("./examples/nestedIfChoose.asm", RemoveNestingFlattener.class);
	}

	@Test
	void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", RemoveNestingFlattener.class);
	}

	@Test
	void let() throws Exception {
		flattenerTest("./examples/nestedIfLet.asm", RemoveNestingFlattener.class);
	}

	@Test
	void forallRuleChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class);
	}

	@Test
	void macroNesting() throws Exception {
		flattenerTest("./examples/macroNesting.asm", RemoveNestingFlattener.class);
	}

	@Test
	void macroArgs() throws Exception {
		flattenerTest("./examples/macroArgs.asm", RemoveNestingFlattener.class);
	}
}
