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
package atgt.project.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.junit.Test;

import atgt.coverage.TestCondition;
import atgt.project.AsmProject;
import tgtlib.definitions.TestSequence;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadTestsAndTpsTest.
 */
public class ReadTestsAndTpsTest {

	/**
	 * Test ex1.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	@Test
	public void testEx1() throws ParseException, FileNotFoundException {

		AsmProject pro = new AsmProject();
		pro.addExtraTps(getEx1());
		assertEquals(2, pro.getTestSuite().size());
		for (TestSequence ts : pro.getTestSuite()) {
			((atgt.coverage.AsmTestSequence) ts).toVideo();
		}
		// print coverages info

		System.out.println(pro.getTestTree().toString());
		for (TestCondition tp : pro.getTestTree().allTPs()) {
			System.out.println(tp.toString());
		}
	}

	/**
	 * Gets the ex1.
	 * 
	 * @return the ex1
	 */
	public File getEx1() {
		URL ex1 = this.getClass().getResource("ex1.tst");
		return new File(ex1.getFile());
	}

}
