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
package atgt.generator;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.generator.testsuite.AsmTestSuiteGenerator;
import tgtlib.definitions.TestSequenceFactory;
import tgtlib.generator.ExternalToolTGen;
import tgtlib.generator.MCInput;

/**
 * The Class AsmTestSeqGenerator represents test sequence generators target for
 * ASM (they provide the running env and the parsing functionalities) Each model
 * checker should extend this in the future could be similar to (SCR)
 * ExtrnalToolTestGenerator and have other fields (temp...)
 */

public abstract class AsmTestSeqGenerator<Q extends MCInput<? extends AsmTestCondition>> extends
		ExternalToolTGen<AsmTestCondition,AsmTestSequence,Q> {

	
	protected AsmTestSeqGenerator() {
		super(new TestSequenceFactory<AsmTestSequence, AsmTestCondition>(){

			@Override
			public AsmTestSequence buildTestSequence(AsmTestCondition tp) {
				return new AsmTestSequence(tp);
			}
			
		});
	}

	/** The testgen. 
	 * TODO to be deleted (used only for fire conditions)*/
	protected AsmTestSuiteGenerator testgen;

	/**
	 * Sets the test generator.
	 * 
	 * @param tg
	 *            the test generator which uses this generator it may be useful
	 *            to communicate with the real generator TestListener
	 */
	public void setTestGenerator(AsmTestSuiteGenerator tg) {
		testgen = tg;

	}		

}
