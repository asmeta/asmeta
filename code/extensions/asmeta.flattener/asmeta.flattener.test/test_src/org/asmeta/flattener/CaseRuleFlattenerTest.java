package org.asmeta.flattener;

import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.junit.jupiter.api.Test;

class CaseRuleFlattenerTest extends FlattenerTest {

	@Test
	void testCase() throws Exception {
		flattenerTest("./examples/nestedIfCase.asm", CaseRuleFlattener.class);
	}

	@Test
	void abstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", CaseRuleFlattener.class);
	}
}
