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
import static org.junit.Assert.assertTrue;

import org.asmeta.parser.Defs;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;
import org.junit.Test;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

/**
 * Test for Defs class.
 *
 */
public class DefsTest {

	Simulator sim;
	
	@Test
	public void test1() 
	throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/domains/equals.asm");
		
		Domain d1 = search("f1").getDomain();
		Domain d2 = search("f2").getDomain();
		Domain d3 = search("f3").getDomain();
		assertTrue(Defs.equals(d1, d2));
		assertTrue(!Defs.equals(d1, d3));

		Domain d4 = search("f4").getDomain();
		Domain d5 = search("f5").getDomain();
		assertTrue(!Defs.equals(d4, d5));

		Domain d6 = search("f6").getDomain();
		Domain d7 = search("f7").getDomain();
		assertTrue(!Defs.equals(d6, d7));

		Domain d9 = search("f9").getDomain();
		Domain d10 = search("f10").getDomain();
		Domain d11 = search("f11").getDomain();
		assertTrue(Defs.equals(d9, d10));
		assertTrue(!Defs.equals(d9, d11));
		
		Domain d12 = search("f12").getDomain();
		Domain d13 = search("f13").getDomain();
		Domain d14 = search("f14").getDomain();
		assertTrue(Defs.equals(d12, d13));
		assertTrue(!Defs.equals(d12, d14));
		
		Domain d15 = search("f15").getDomain();
		Domain d16 = search("f16").getDomain();
		Domain d17 = search("f17").getDomain();
		Domain d18 = search("f18").getDomain();
		assertTrue(Defs.equals(d15, d16));
		assertTrue(Defs.equals(d17, d18));
		assertTrue(!Defs.equals(d15, d18));
	}
	
	@Test
	public void test2() 
	throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/domains/test04/m2.asm");
		
		Function f1 = search("f");
		RuleDeclaration m1 = searchMacro("m1", "r_m1");
		Function f2 = 
			((LocationTerm) ((UpdateRule) m1.getRuleBody()).getLocation()).getFunction();
		assertTrue(!Defs.equals(f1, f2));
		assertEquals("m2", Defs.getAsmName(f1));
		assertEquals("m1", Defs.getAsmName(m1));
		assertEquals("m1", Defs.getAsmName(f2));
	}
	
	@Test
	public void test3() throws Exception {
		sim = Util.getSimulatorForTestSpec("test/simulator/import2/m3.asm");
		sim.run(1);
		Function f1 = search("m1", "f1");
		RuleDeclaration rm1 = searchMacro("m1", "r_m1");
		Function f2 = search("m1", "f2");
		RuleDeclaration rm2 = searchMacro("m2", "r_m2");
		Value lf1 = sim.currentState.read(new Location(f1, new Value[0]));
		Value lf2 = sim.currentState.read(new Location(f2, new Value[0]));
		assertEquals(new IntegerValue(123), lf1);
		assertEquals(new IntegerValue(456), lf2);
		assertEquals("m1", Defs.getAsmName(f1));
		assertEquals("m1", Defs.getAsmName(rm1));
		assertEquals("m1", Defs.getAsmName(f2));
		assertEquals("m2", Defs.getAsmName(rm2));
	}
	
	Asm getModule(String name) {
		for (Asm asm : sim.asmetaPackage) {
			if (asm.getName().equals(name)) {
				return asm;
			}
		}
		return null;
	}

	public Function search(String asmName, String name) {
		Asm asm = getModule(asmName);
		return Defs.searchFunction(asm, name);
		/*Collection<Function> funcs = asm.getHeaderSection().getSignature().getFunction();
		for (Object o : funcs) {
			Function f = (Function) o;
			if (f.getName().equals(name)) {
				return f;
			}
		}
		throw new RuntimeException("Function " + name + " not found");*/
	}
	
	public Function search(String name) {
		return search(sim.asmModel.getName(), name);
	}
	
	public RuleDeclaration searchMacro(String asmName, String name) {
		Asm asm = getModule(asmName);
		return Defs.searchMacro(asm, name);
		/*Collection<RuleDeclaration> rules = asm.getBodySection().getRuleDeclaration();
		for (Object o : rules) {
			RuleDeclaration r = (RuleDeclaration) o;
			if (r.getName().equals(name)) {
				return r;
			}
		}
		throw new RuntimeException("Rule " + name + " not found");*/
	}
	
	public RuleDeclaration searchMacro(String name) {
		return searchMacro(sim.asmModel.getName(), name);
	}
}
