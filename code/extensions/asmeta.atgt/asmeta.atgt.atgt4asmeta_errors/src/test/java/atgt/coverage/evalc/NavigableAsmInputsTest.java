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
package atgt.coverage.evalc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import atgt.coverage.AsmTestSequence;
import atgt.coverage.eval.ExpressionEvaluatorSeqTest;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigableAsmInputsTest.
 */
public class NavigableAsmInputsTest {

	// TEMP to ensure the assert
	static {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
	}

	/**
	 * Test get inputs si s1.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testGetInputsSIS1() throws ParseException, IOException {
		String[][] a = { { "waterPressure", "10" } };
		test(1, a);
	}

	/**
	 * Test get inputs si s2.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testGetInputsSIS2() throws ParseException, IOException {
		// questo fallisce perchè inserisco un controlled e createAsmTestseuqenc enon distingue
		String[][] a = { { "waterPressure", "10" }, { "pressure", "TooLow" } };
		test(1, a);
	}

	/**
	 * Test get inputs si s3.
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	@Test
	public void testGetInputsSIS3() throws ParseException, IOException {
		String[][] a = { { "waterPressure", "10" }, { "reset", "off" } };
		test(2, a);
	}

	/**
	 * Test.
	 * 
	 * @param n
	 *            the n
	 * @param a
	 *            the a
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	private void test(int n, String[][] a) throws ParseException, IOException {
		ASMSpecification spec = ExampleLoader.getSpec("sis.gs");
		assertNotNull(spec);
		AsmTestSequence test = ExpressionEvaluatorSeqTest.createTestSequence(a);
		NavigableAsmInputs ni = new NavigableAsmInputs(test, spec);
		assertEquals(n, ni.getInputs().size());
	}
}
