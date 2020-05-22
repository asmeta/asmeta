package org.asmeta.flattener;

import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.junit.Test;

public class ForallRuleFlattenerTest extends FlattenerTest {

	@Test
	public void testForallChoose() throws Exception {
		flattenerTest("./examples/forallRuleChoose.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testRushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testEnumerative() throws Exception {
		flattenerTest("./examples/forallRule3.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testLimitingCase() throws Exception {
		flattenerTest("./examples/forallRule.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testAbstractDomain() throws Exception {
		flattenerTest("./examples/abstractDomain.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testConwayGameOfLife() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLife.asm", ForallRuleFlattener.class);
	}

	@Test
	public void testConwayGameOfLifeAgents() throws Exception {
		flattenerTest(examplesDir + "examples/conwayGameOfLife/gameOfLifeAgents.asm", ForallRuleFlattener.class);
	}
}
