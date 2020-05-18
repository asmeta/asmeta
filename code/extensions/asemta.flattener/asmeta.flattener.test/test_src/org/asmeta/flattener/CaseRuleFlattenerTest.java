package org.asmeta.flattener;

import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.junit.Test;

public class CaseRuleFlattenerTest extends FlattenerTest {

	@Test
	public void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", CaseRuleFlattener.class);
	}

	@Test
	public void testAbstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", CaseRuleFlattener.class);
	}
}
