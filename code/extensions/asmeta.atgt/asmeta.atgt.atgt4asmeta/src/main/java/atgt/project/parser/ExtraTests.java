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

import java.util.ArrayList;

import atgt.coverage.AsmTestSequence;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraTests.
 */
public class ExtraTests {

	/** The tests. */
	java.util.List<AsmTestSequence> tests = new ArrayList<AsmTestSequence>();

	/** The defined tps. */
	UserDefinedTps definedTps;

	/**
	 * Adds the test sequence.
	 * 
	 * @param ts
	 *            the ts
	 */
	public void addTestSequence(AsmTestSequence ts) {
		tests.add(ts);

	}

	/**
	 * Adds the coverage.
	 * 
	 * @param udtp
	 *            the udtp
	 */
	public void addCoverage(UserDefinedTps udtp) {
		definedTps = udtp;
	}

	/**
	 * Gets the tests.
	 * 
	 * @return the tests
	 */
	public java.util.List<AsmTestSequence> getTests() {
		return tests;
	}

	/**
	 * Gets the defined tps.
	 * 
	 * @return the defined tps
	 */
	public UserDefinedTps getDefinedTps() {
		return definedTps;
	}

}
