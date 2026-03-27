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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.asmeta.simulator.InvalidValueException;
import org.junit.jupiter.api.Test;

class OutofBoundTest extends BaseTest {

	@Test void outOfBound() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/simulator/outofbound.asm");
		sim.run(3);
		try {
			sim.run(1);
		}catch(InvalidValueException e) {
			assertEquals("value out of domain: cannot assign 4 to foo", e.getMessage());
		}
	}
}