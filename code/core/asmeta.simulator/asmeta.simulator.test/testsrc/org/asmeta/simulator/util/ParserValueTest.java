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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.TupleValue;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;

// test case for the Libaray specification 
public class ParserValueTest {

	private static final String ASM_EXAMPLES = "../../../../asm_examples/";
	
	static Domain bookDom;

	@BeforeClass
	public static void setupLogger() throws Exception {
		//AsmParserTest.setUpLogger();
		// Scrivi caso di test per la library
		File libraryF = new File(ASM_EXAMPLES +"examples/library/Library.asm");
		assertTrue(libraryF.exists());
		AsmCollection asms = ASMParser.setUpReadAsm(libraryF);
		Asm asm = asms.getMain();
		try {
			bookDom = Defs.searchDomain(asm, "Book");
		} catch (Exception e) {
		}
		assertNotNull(bookDom);
	}

	@Test(expected = InputMismatchException.class)
	public void testVisitProductDomain1() throws InputMismatchException {
		// input mismatch con solo una stringa
		Parser p = new Parser("(\"a\")");
		TupleValue v;
		v = (TupleValue) p.visit(bookDom);
		assertNotNull(v);
		System.out.println(v.toString());
	}

	@Test(expected = InputMismatchException.class)
	public void testVisitProductDomain2() throws InputMismatchException {
		Parser p = new Parser("(543543543)");
		p.visit(bookDom);
	}

	// con dati GIUSTi
	@Test
	public void testVisitProductDomain3() throws InputMismatchException {
		Parser p = new Parser("(\"a\",\"a\",\"a\",\"a\",4)");
		p.visit(bookDom);
	}
}
