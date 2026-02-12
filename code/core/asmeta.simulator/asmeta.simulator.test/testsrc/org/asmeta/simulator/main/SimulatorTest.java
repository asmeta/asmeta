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

import org.asmeta.parser.util.Defs;
import org.junit.After;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;

/**
 * Base class for several tests.
 *
 */
public class SimulatorTest {
	
	final static String BASE = "../../../asm_examples/";
	
	Simulator sim;
	
	public Function searchFunction(String name) {
		return Defs.searchFunction(sim.asmModel, name);
		/*Collection<?> funcs = sim.asmModel.getHeaderSection().getSignature().getFunction();
		for (Object o : funcs) {
			Function f = (Function) o;
			if (f.getName().equals(name)) {
				return f;
			}
		}
		throw new RuntimeException("Function " + name + " not found");*/
	}
	
	public Domain searchDomain(String name) {
		return Defs.searchDomain(sim.asmModel, name);
		/*Collection<?> doms = sim.asmModel.getHeaderSection().getSignature().getDomain();
		for (Object o : doms) {
			Domain d = (Domain) o;
			if (d.getName().equals(name)) {
				return d;
			}
		}
		throw new RuntimeException("Domain " + name + " not found");*/
	}
	
	public RuleDeclaration searchMacro(String name) {
		return Defs.searchMacro(sim.asmModel, name);
		/*Collection<?> rules = sim.asmModel.getBodySection().getRuleDeclaration();
		for (Object o : rules) {
			RuleDeclaration r = (RuleDeclaration) o;
			if (r.getName().equals(name)) {
				return r;
			}
		}
		throw new RuntimeException("Rule " + name + " not found");*/
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
