/*******************************************************************************
 * Copyright (c) 2010 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UndefValueTest extends BaseTest {

	@Test void testinconUpdateWithUndef1() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef1.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}

	@Test void testinconUpdateWithUndef2() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef2.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}

	@Test void testinconUpdateWithUndef3() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef3.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}

	@Test void testinconUpdateWithUndef4() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef4.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}

	@Test void testinconUpdateWithUndef5() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef5.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}

	@Test void testinconUpdateWithUndef6() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/inconUpdateWithUndef.asm",
					ASM_EXAMPLES + "test/env/inconUpdateWithUndef6.env");
		assertThrows(org.asmeta.simulator.UpdateClashException.class, () ->
			sim.run(1));
	}
}