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
package atgt.coverage.tpstatus;

import static atgt.coverage.tpstatus.TestConditionState.TODO;

/**
 * represents the FSM with change of state accepts the method to the test
 * condition an compute its state,
 */
public class TestConditionFSM {

	// current state
	/** The current. */
	private TestConditionState current;

	/** The initial state. */
	static TestConditionState initialState = TODO;

	/**
	 * Lo stato precedente a quello corrente. Questo campo permette di ritornare
	 * allo stato precedente. Nel caso in cui un test viene selezionato e poi
	 * deselezionato si ritorna allo stato precedente alla selezione. Se il test
	 * era gia' stato verificato, si conserva il risultato dopo la deselezione.
	 * IT CAN BE NULL
	 */

	// protected TestConditionState previous;
	TestConditionState previous;

	/**
	 * Instantiates a new test condition fsm.
	 */
	public TestConditionFSM() {
		setCurrent(initialState);
	}

	/**
	 * Sets the state.
	 * 
	 * @param newstate
	 *            the new state
	 */
	void setState(TestConditionState newstate) {
		previous = getCurrent();
		setCurrent(newstate);
	}

	/**
	 * restore to the previous state.
	 */
	void restoreState() {
		setCurrent(previous);
	}

	/**
	 * Selezione/deseleziona il test per la verifica.
	 * 
	 * @param b
	 *            the b
	 */
	public void setToVerify(boolean b) {
		current.setSelected(b, this);
		// if(getStatus()!= getStatus().setSelected(b)){
		// this.status = getStatus().setSelected(b);
		// }
	}

	/**
	 * Sets the running.
	 */
	public void setRunning() {
		current.setRunning(this);
	}

	/**
	 * is assertion violated because the test is found ? (if running).
	 * 
	 * @param b
	 *            the b
	 */
	public void setCovered(boolean b) {
		current.setCovered(b, this);
	}

	/**
	 * Mark infeasible.
	 */
	public void markInfeasible() {
		current.setInfeasible(this);
	}

	/**
	 * Sets the current.
	 * 
	 * @param current
	 *            the new current
	 */
	private void setCurrent(TestConditionState current) {
		this.current = current;
	}

	/**
	 * Gets the current.
	 * 
	 * @return the current
	 */
	public TestConditionState getCurrent() {
		return current;
	}

	/**
	 * Gets the previous.
	 * 
	 * @return the previous
	 */
	public TestConditionState getPrevious() {
		return previous;
	}
}