package org.asmeta.flattener;

import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.junit.jupiter.api.Test;

class ForallRuleFlattenerTest extends FlattenerTest {

	@Test
	void forallChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void rushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void enumerative() throws Exception {
		flattenerTest("./examples/forallRule3.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void limitingCase() throws Exception {
		flattenerTest("./examples/forallRule.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void abstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void conwayGameOfLife() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLife.asm", false, ForallRuleFlattener.class);
	}

	@Test
	void conwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", false, ForallRuleFlattener.class);
	}
}
