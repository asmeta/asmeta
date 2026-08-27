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
package atgt.coverage;


/**
 * TODO to be removed ???
 * 
 * The Class TestResult.
 * represents the result of a test generation
 * It is not observable, since we assume that the 
 * test suite is observable and it notifies any state change in any of
 * its test sequences
 */
public abstract class TestResult extends tgtlib.definitions.TestSequence<AsmTestCondition>{

	/**
	 * Instantiates a new test result.
	 */
	public TestResult(AsmTestCondition tp) {
		super(tp);
	}

	/**
	 * Instantiates a new test result.
	 */
	public TestResult(TestCondition<AsmTestSequence> tp) {
		super(tp);
	}

	
}
