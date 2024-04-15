/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;


public class LilliTesiTest extends BaseTest {

	@Test
	public void test01() throws Exception {
		// carica il simulatore
		// con anche l'ambiente per le monitorate
		// dsiabilita la lazy evaluation
		boolean old = TermEvaluator.allowLazyEval; 
		TermEvaluator.allowLazyEval = false;
		sim = Simulator.createSimulator(ASM_EXAMPLES+"test/tesililli/zwaveProtocol_join_mitm_duplicate_signature.asm",
				ASM_EXAMPLES+"test/tesililli/zwaveProtocol_join_mitm_duplicate_signature.env");
		// faccio un passo
		sim.run(1);
		// interrogo lo stato o col debugger o con il print
		System.out.println(sim.getCurrentState());
		sim.run(1);
		System.out.println(sim.getCurrentState());
		sim.run(1);
		System.out.println(sim.getCurrentState());
		sim.run(1);
		System.out.println(sim.getCurrentState());
		// interrogo
		List<Function> grants0 = new ArrayList<>();
		Collection<?> funcs = sim.asmModel.getHeaderSection().getSignature().getFunction();
		for (Object o : funcs) {
			Function f = (Function) o;
			if (f.getName().equals("grantS0")) {
				grants0.add(f);
			}
		}
		for (Function f:grants0) {
			System.out.println(f.getName() + " " + f.getDomain());
			System.out.println(sim.currentState.read(f));
		}
		/*System.out.println(foo1.getDomain());
		Value v1 = sim.currentState.read(new Location(foo1, new Value[0]));
		Function foo2 = searchFunction("foo2");
		Value v2 = sim.currentState.read(new Location(foo2, new Value[0]));
		assertEquals(BooleanValue.FALSE, v1);
		assertEquals(BooleanValue.FALSE, v2);*/
		// ripristina lazy
		TermEvaluator.allowLazyEval = old;
	}
	
}
