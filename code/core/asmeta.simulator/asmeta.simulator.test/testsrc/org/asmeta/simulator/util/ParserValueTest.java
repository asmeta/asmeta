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
package org.asmeta.simulator.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.Defs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import asmeta.AsmCollection;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;

// test case for the Libaray specification 
class ParserValueTest {

	private static final String ASM_EXAMPLES = "../../../../asm_examples/";

	static Domain bookDom;

	@BeforeAll
	static void setupLogger() throws Exception {
		// AsmParserTest.setUpLogger();
		// Scrivi caso di test per la library
		File libraryF = new File(ASM_EXAMPLES + "examples/library/Library.asm");
		assertTrue(libraryF.exists());
		AsmCollection asms = ASMParser.setUpReadAsm(libraryF);
		Asm asm = asms.getMain();
		try {
			bookDom = Defs.searchDomain(asm, "Book");
		} catch (Exception e) {
		}
		assertNotNull(bookDom);
	}

	@Test
	void visitProductDomain1() {
		Parser p = new Parser("(\"a\")");
		assertThrows(InputMismatchException.class, () -> p.visit(bookDom));
	}

	@Test
	void visitProductDomain2() {
		Parser p = new Parser("(543543543)");
		assertThrows(InputMismatchException.class, () -> p.visit(bookDom));
	}

	// con dati GIUSTi
	@Test
	void visitProductDomain3() throws Exception {
		Parser p = new Parser("(\"a\",\"a\",\"a\",\"a\",4)");
		p.visit(bookDom);
	}
}
