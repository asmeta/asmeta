/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * The Class ParseSpecsAsmm.
 */
public class ParseSpecsAsmm {

	// rendi il path di ricerca delle ASM in modo che si possa testare anche da
	// un altro progetto....

	/** The Constant CC_ASM. */
	public static final File CC_ASM = getFileSpec("cruiseControl.asm");

	/** The Constant SIS_ABSTRACT. */
	public static final File SIS_ABSTRACT = getFileSpec("SIS_nowp_onlymain.asm");

	/** The SIS. */
	public static File SIS = getFileSpec("SIS.asm");

	
	static final String combsubdir = "combinatorial/";
	
	/** The T p4. */
	public static final File TP4 = getFileSpec(combsubdir +"threepowerfour.asm");

	/** The Basic billing system. */
	public static File BasicBillingSystem = getFileSpec(combsubdir +"bbs.asm");

	/** The T p2_3_4_4. */
	public static File TP2_3_4_4 = getFileSpec(combsubdir +"t_3_3_4_4_4_4.asm");

	/** The T p2_3_4_4. */
	public static File TCASBOOL = getFileSpec(combsubdir +"TCAS2boolean.asm");

	/** The asmfiles. */
	public static File[] asmfiles = { BasicBillingSystem, SIS, TP4 };

	/**
	 * Test existence.
	 */
	@Test
	public void testExistence() {
		for (File f : asmfiles)
			assertTrue(f.getName() + " exists ", f.exists());
	}

	public static File getFileSpec(String s) {
		try {
			File file = ExampleLoader.getFileSpec(s);
			assert file.exists();
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Test syntax.
	 */
	@Test
	public void testSyntax() {
		/*
		 * assertTrue(checkAsmSyntax(BasicBillingSystem));
		 * assertTrue(checkAsmSyntax(SIS)); assertTrue(checkAsmSyntax(TP4));
		 */
		assertTrue(checkAsmSyntax(TP2_3_4_4));
		assertTrue(checkAsmSyntax(CC_ASM));
		assertTrue(checkAsmSyntax(TCASBOOL));
	}


	/**
	 * Check asm syntax.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return true, if successful
	 * @throws Exception
	 */
	private boolean checkAsmSyntax(File f) {
		try {
			org.asmeta.parser.ASMParser.setUpReadAsm(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
