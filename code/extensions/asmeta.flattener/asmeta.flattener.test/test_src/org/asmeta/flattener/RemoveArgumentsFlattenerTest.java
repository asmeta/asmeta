package org.asmeta.flattener;


import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class RemoveArgumentsFlattenerTest extends FlattenerTest {

	@Test
	void derivedArgs() throws Exception {
		String outputAsStr = flattenerTest("./examples/derivedLocArgs.asm", RemoveArgumentsFlattener.class);
		assertFalse(outputAsStr.contains("moveExists(move)"));
	}

	@Test
	void macroArgs() throws Exception {
		flattenerTest("./examples/macroArgs.asm", RemoveArgumentsFlattener.class);
	}

	@Test
	void rushHour() throws Exception {
		flattenerTest("../../../../asm_examples/examples/RushHour/RushHour.asm", RemoveArgumentsFlattener.class);
	}

	@Test
	void coffeeVendingMachine() throws Exception {
		flattenerTest(examplesDir + "examples/coffeeVendingMachine/coffeeVendingMachine.asm", RemoveArgumentsFlattener.class);
	}
}
