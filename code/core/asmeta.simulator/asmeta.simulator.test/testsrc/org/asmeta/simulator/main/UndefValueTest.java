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

import org.junit.Test;

public class UndefValueTest extends BaseTest {

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef1() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef1.env");
		sim.run(1);
	}

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef2() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef2.env");
		sim.run(1);
	}

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef3() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef3.env");
		sim.run(1);
	}

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef4() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef4.env");
		sim.run(1);
	}

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef5() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef5.env");
		sim.run(1);
	}

	@Test(expected = org.asmeta.simulator.UpdateClashException.class)
	public void testinconUpdateWithUndef6() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/inconUpdateWithUndef.asm",
				BASE + "test/env/inconUpdateWithUndef6.env");
		sim.run(1);
	}
}