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
 * a test suite has changed:
 *  new test have been added.
 */

public class AddTestsEvent extends TestSuiteChangeEvent {

	AsmTestSuite added; 
	
	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * @param added the tests added to the test suite in the project
	 */
	public AddTestsEvent(Object source,AsmTestSuite added) {
		super(source);
		this.added = added;
	}

	/**
	 * Gets the test suite.
	 * 
	 * @return the test suite which has been added
	 */
	public AsmTestSuite getTestSuite() {
		return added;
	}

}
