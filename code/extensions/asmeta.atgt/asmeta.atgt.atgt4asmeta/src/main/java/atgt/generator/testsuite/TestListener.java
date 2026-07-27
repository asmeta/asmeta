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
package atgt.generator.testsuite;

import java.util.EventListener;

import atgt.coverage.TestEvent;

// TODO: Auto-generated Javadoc
/**
 * listen to TestEvent like: coverage started, completed, errors ....
 */
public interface TestListener extends EventListener {

	/**
	 * Test condition started.
	 * 
	 * @param te
	 *            the te
	 */
	public void TestConditionStarted(TestEvent te);

	/**
	 * Test condition completed.
	 * 
	 * @param te
	 *            the te
	 */
	public void TestConditionCompleted(TestEvent te);

	/**
	 * Test condition step completed.
	 * 
	 * @param te
	 *            the te
	 */
	public void TestConditionStepCompleted(TestEvent te);

	/**
	 * Test condition error.
	 * 
	 * @param te
	 *            the te
	 */
	public void TestConditionError(TestEvent te);

	/**
	 * Coverage completed.
	 * 
	 * @param te
	 *            the te
	 */
	public void CoverageCompleted(TestEvent te);

	/**
	 * Coverages completed.
	 * 
	 * @param te
	 *            the te
	 */
	public void CoveragesCompleted(TestEvent te);

}
