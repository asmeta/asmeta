/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package tgtlib.reduction;

import org.junit.jupiter.api.BeforeEach;

import tgtlib.generator.TestPredicate4Test;
import tgtlib.generator.TestSequence4Test;

public class ReductionTest {

	/** The tc1. */
	protected TestPredicate4Test tc1;
	/** The tc2. */
	protected TestPredicate4Test tc2;
	/** The tc3. */
	protected TestPredicate4Test tc3;
	/** The tr1. */
	protected TestSequence4Test tr1;
	/** The tr2. */
	protected TestSequence4Test tr2;
	/** The tr3. */
	protected TestSequence4Test tr3;
	/** The tr4. */
	protected TestSequence4Test tr4;

	/**
	 * Creates the t cs.
	 */
	@BeforeEach void createTCs() {
		tc1 = new TestPredicate4Test("tc1",null);
		tc2 = new TestPredicate4Test("tc2",null);
		tc3 = new TestPredicate4Test("tc3",null);
		// tr1 -> tc1 e tc2
		tr1 = getTS(tc1, tc2);
		// tr2 -> tc2 e tc3
		tr2 = getTS(tc2, tc3);
		// tr3 -> tc1 e tc3
		tr3 = getTS(tc1, tc3);
		// tr4 ->tc1
		tr4 = getTS(tc1);
	}

	TestSequence4Test getTS(TestPredicate4Test... tcs){
		TestSequence4Test t = new TestSequence4Test(null);
		for (TestPredicate4Test tp : tcs)
			tp.bindTestSeqTestPred(t);
		return t;
		
	}
}
