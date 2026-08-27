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
 * a test suite has changed !!! new test have been added.
 */

abstract public class TestSuiteChangeEvent extends java.util.EventObject {

	/**
	 * The Constructor.
	 * 
	 * @param source
	 *            the source
	 */
	public TestSuiteChangeEvent(Object source) {
		super(source);
	}

}
