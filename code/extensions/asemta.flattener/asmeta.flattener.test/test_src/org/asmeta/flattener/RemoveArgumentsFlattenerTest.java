package org.asmeta.flattener;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemoveArgumentsFlattenerTest extends FlattenerTest {

	@Test
	public void testDerivedArgs() throws Exception {
		String outputAsStr = flattenerTest("./examples/derivedLocArgs.asm", RemoveArgumentsFlattener.class);
		assertTrue(!outputAsStr.contains("moveExists(move)"));
	}

	@Test
	public void testMacroArgs() throws Exception {
		flattenerTest("./examples/macroArgs.asm", RemoveArgumentsFlattener.class);
	}

	@Test
	public void testRushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", RemoveArgumentsFlattener.class);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm", RemoveArgumentsFlattener.class);
	}
}
