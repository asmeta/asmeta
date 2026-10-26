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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.parser.asmgofer.AsmGoferLoader;

/**
 * The Class ParseGoferFiles.
 */
public class ParseGoferFiles {

	/** The Cruise control. */
	public static File CruiseControl;

	/** The SIS. */
	public static File SIS;


	static{
		try {
			loadfiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeAll
	static void loadfiles() throws IOException {
		CruiseControl = ExampleLoader.getFileSpec("cruiseControl.gs");
		SIS = ExampleLoader.getFileSpec("sis.gs");
	}

	/**
	 * Test existence.
	 */
	@Test void existence() {
		assertTrue(CruiseControl.exists(), "cruise control "+ CruiseControl.getAbsolutePath() + "not found");
		assertTrue(SIS.exists(), "SIS not found ");
	}

	/**
	 * Test syntax.
	 */
	@Test void syntax() {
		assertTrue(checkGoferSyntax(CruiseControl));
		assertTrue(checkGoferSyntax(SIS));
	}

	/**
	 * Check gofer syntax.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return true, if successful
	 */
	private boolean checkGoferSyntax(File f) {
		try {
			AsmGoferLoader loader = new AsmGoferLoader();
			loader.read(f);
			return true;
		} catch (AsmParseException ep) {
			return false;
		} catch (FileNotFoundException e) {
			return true;
		}
	}
}
