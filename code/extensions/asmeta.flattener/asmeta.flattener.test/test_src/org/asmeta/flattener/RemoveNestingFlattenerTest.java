package org.asmeta.flattener;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.junit.Test;

public class RemoveNestingFlattenerTest extends FlattenerTest {

	@Test
	public void testForall() throws Exception {
		flattenerTest("./examples/nestedIfForall.asm", RemoveNestingFlattener.class);
	}
	
	@Test
	public void testChoose() throws Exception {
		flattenerTest("./examples/nestedIfChoose.asm", RemoveNestingFlattener.class);
	}
	
	@Test
	public void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", RemoveNestingFlattener.class);
	}

	@Test
	public void testLet() throws Exception {
		flattenerTest("./examples/nestedIfLet.asm", RemoveNestingFlattener.class);
	}

	@Test
	public void testForallRuleChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", RemoveNestingFlattener.class);
	}

	@Test
	public void testMacroNesting() throws Exception {
		flattenerTest("./examples/macroNesting.asm", RemoveNestingFlattener.class);
	}

	@Test
	public void testMacroArgs() throws Exception {
		flattenerTest("./examples/macroArgs.asm", RemoveNestingFlattener.class);
	}
}
