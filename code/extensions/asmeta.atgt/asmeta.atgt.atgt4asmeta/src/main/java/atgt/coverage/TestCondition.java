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

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import atgt.coverage.tpstatus.TestConditionFSM;
import atgt.coverage.tpstatus.TestConditionState;
import tgtlib.definitions.TestSequence;
import tgtlib.definitions.expression.Expression;

/**
 * TestCondition = test predicate [googenough et alt.] + description + data
 * about coverage. In scrtgtool is also called test goal
 */
@SuppressWarnings("unchecked")
public /*abstract*/ class TestCondition<T extends TestSequence<?>> extends tgtlib.definitions.TestPredicate<T,TestConditionState> {
	
	
	private static Logger log = Logger.getLogger(TestCondition.class);

	/**
	 * Lo stato del test condition: e' stato verificato? E' stato coperto da un
	 * altro test predicate?.
	 * nascosta in modo che cambimanti passino da qui e possano
	 * essere comunicati ai listener 
	 */
	protected TestConditionFSM status;

	/** La lista dei listener registrati per la gestione degli eventi di testing. */
	protected EventListenerList listenerList;

	/** The test condition event. */
	protected TestConditionEvent testConditionEvent;

	/**
	 * I risultati della verifica per il test. (se è stato trovato il test Seq
	 * specificatamente per questo test condition
	 */
	protected T testSequence;

	/**
	 * Instantiates a new test condition.
	 * 
	 * @param _name
	 *            the _name
	 * @param _condition
	 *            the _condition
	 */
	public TestCondition(String _name, Expression _condition) {
		super(_name, _condition);
		this.listenerList = new EventListenerList();
		// status = TO_DO;
		this.status = new TestConditionFSM();
	}

	@Override
	protected Collection<T> buildCoveredBy() {
		// to allow synchronized modification
		// and ordering among test predicates
		return Collections.synchronizedSortedSet(new TreeSet<T>());
	}

	/**
	 * reset the information about this test condition.
	 */
	public void reset() {
		super.resetCoveredCases();
		// status = TO_DO;
		this.status = new TestConditionFSM();
	}

	/**
	 * Accept.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the t
	 */
	public <T> T accept(CoveragesVisitorI<T> ask) {
		// TODO: the visitor works only for the subclass AsmtestConfiyion
		return ask.forTestCondition((AsmTestCondition)this);
	}

	/**
	 * Controlla se il test condition ??? stato selezionato per la verifica.
	 * 
	 * @return true, if checks if is to verify
	 */
	@Override
	public boolean isToVerify() {
		return (getStatus() == TestConditionState.Queued);
	}

	/**
	 * Selezione/deseleziona il test per la verifica.
	 * 
	 * @param b
	 *            the b
	 */
	public void setToVerify(boolean b) {
		status.setToVerify(b);
		fireTestConditionStateChanged();
	}

	/**
	 * Due test condition sono uguali se hanno lo stesso nome. O lo stesso ID ??
	 * TODO TODO
	 * 
	 * @return the status description
	 */
	/*
	 * public boolean equals(Object o){ if (o instanceof TestCondition) return
	 * getName().equals(((TestCondition)o).getName()); return false; }
	 */

	public String getStatusDescription() {
		return getStatus().toString();
	}

	/**
	 * L'esecuzione della verifica ha provocato una violazione del dell'assert?.
	 * 
	 * @return true, if checks if is assert violated
	 */
	public boolean isAssertViolated() {
		return (getStatus() == TestConditionState.AssertViolated);
	}

	/**
	 * set running of this test condition.
	 */
	public void setRunning() {
		this.status.setRunning();
		fireTestConditionStateChanged();
	}

	/**
	 * set covered.
	 * 
	 * @param b
	 *            the b
	 */
	public void setAssertViolated(boolean b) {
		// deprecato ??? :: solo da usare con false. Usa bind invece
		// assert b == false;
		
		// if(getStatus() != getStatus().setCovered(b)){
		// this.status = getStatus().setCovered(b);
		status.setCovered(b);
		fireTestConditionStateChanged();
	}

	// TODO delete this method ?
	/**
	 * Gets the test result.
	 * 
	 * @return the test result that is generated for this tp
	 */
	@Deprecated
	// use covered by instead
	public T getTestResult() {
		return this.testSequence;
	}

	/**
	 * lega test condition e test case Aggiunge il test sequence che copre
	 * questo test condition. e aggiunge al test sequence che copre questo
	 * 
	 * @param tc
	 *            the tc
	 * @param testCase
	 *            the test case
	 */

	@Override
	public void bindTestSeqTestPred(T testCase) {		
		log.debug("binding "+ testCase + " as test covering " + this.getName());
		this.testSequence = testCase;
		this.coveredBy.add(testCase);
		this.status.setCovered(true);
		testCase.setCovers(this);
		// WRONG: do not set generated for here
		// do it when generating
		// testCase.setGeneratedFor(this);
		this.fireTestConditionStateChanged();
	}

	/**
	 * Ritorna una collezione di test seq che coprono questo caso di test.
	 * 
	 * @return the collection< asm test sequence>
	 */
	public Collection<T> allCoveredBy() {
		return this.coveredBy;
	}

	/**
	 * Adds the test condition listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void addTestConditionListener(TestConditionListener l) {
		this.listenerList.add(TestConditionListener.class, l);
	}

	/**
	 * Removes the test condition listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void removeTestConditionListener(TestConditionListener l) {
		this.listenerList.remove(TestConditionListener.class, l);
	}

	/**
	 * Fire test condition state changed.
	 */
	public void fireTestConditionStateChanged() {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interesati l'evento che si ???
		// verificato. Comincia dalla fine della lista.
		this.testConditionEvent = new TestConditionEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestConditionListener.class) {
				((TestConditionListener) listeners[i + 1])
						.TestConditionStateChanged(this.testConditionEvent);
			}
		}
	}

	// it must be a valid ID as defined in spintrail.jj
	// for example no @
	/** The ID. */
	private String ID = "tc_" + Integer.toHexString(hashCode());

	/* get unique ID that can be use as reference to the object */
	/**
	 * Gets the unique id.
	 * 
	 * @return the unique id
	 */
	@Override
	public String getUniqueID() {
		return this.ID;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	@Override
	public TestConditionState getStatus() {
		return this.status.getCurrent();
	}

	/**
	 * Gets the previous status.
	 * 
	 * @return the previous status
	 */
	public TestConditionState getPreviousStatus() {
		return this.status.getPrevious();
	}

	/**
	 * set this test condition unfeasible.
	 */
	@Override
	public void markInfeasible() {
		this.status.markInfeasible();
		fireTestConditionStateChanged();
	}
}