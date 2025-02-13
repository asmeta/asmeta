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
package org.asmeta.parser;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * Test for RuleEvaluater.macros attribute.
 *
 */
public class DefWithTrueEvaluatorTest extends AsmParserTest{
	
	//@BeforeClass
	public static void setUpLogger() {
		/*Logger log = Logger.getLogger("org.asmeta.parser");
		if (!log.getAllAppenders().hasMoreElements())
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
		log.setLevel(Level.ALL);
		Logger.getLogger(Utility.class).setLevel(Level.ALL);*/
	}

	@Test
	public void testFunction() throws Throwable {
		AsmCollection asmC = testOneSpec("test/errors/derBody/derBodyErr.asm");
		Asm asm = asmC.getMain();
		Function fa = Defs.searchFunction(asm, "fooA");
		System.out.println(fa.getDefinition().getBody()); // true
		Function fb = Defs.searchFunction(asm, "fooB");
		System.out.println(fb.getDefinition().getBody()); // true
	}

}
