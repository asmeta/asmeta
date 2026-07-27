/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions;

/**
 * a test sequence can be Normal or discarded.
 *
 */
public class TestSeqFSM {

	/** The current state */
	private TestSequenceState current;

	/** The initial state. */
	static TestSequenceState initialState = TestSequenceState.NORMAL;

	/**
	 * Instantiates a new test seq fsm.
	 */
	public TestSeqFSM() {
		setCurrent(initialState);
	}

	/**
	 * Sets the current.
	 * 
	 * @param newstate
	 *            the new current
	 */
	final void setCurrent(TestSequenceState newstate) {
		current = newstate;
	}

	/**
	 * Discard.
	 */
	public void discard() {
		current.discardTest(this);
	}

	/**
	 * Gets the current.
	 * 
	 * @return the current
	 */
	public TestSequenceState getCurrent() {
		return current;
	}

}
