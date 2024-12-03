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

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.asmeta.parser.Defs;
import org.junit.After;
import org.junit.BeforeClass;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;

/**
 * Base class for several tests.
 *
 */
public class BaseTest {
	
	protected final static String ASM_EXAMPLES = "../../../../asm_examples/";

	
	@BeforeClass
	public static void checkDirectory() {
		  assertTrue(Files.isDirectory(Paths.get(ASM_EXAMPLES)));
	}
	
	protected Simulator sim;

	public Function searchFunction(String name) {
		return Defs.searchFunction(sim.asmModel, name);
	}

	public Domain searchDomain(String name) {
		return Defs.searchDomain(sim.asmModel, name);
	}

	public RuleDeclaration searchMacro(String name) {
		return Defs.searchMacro(sim.asmModel, name);
	}

	@After
	public void tearDown() throws Exception {
	}

}
