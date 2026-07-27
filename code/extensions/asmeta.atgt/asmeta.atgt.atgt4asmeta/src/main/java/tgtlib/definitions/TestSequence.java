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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.type.Variable;

/**
 * this type represents a test sequence, i.e. s (possible) empty list of states,
 * where every state is a list of assignments
 * 
 * it keeps track only of the test predicates covered by itself
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
public abstract class TestSequence<T extends TestPredicate<? extends TestSequence<T>, ?>>
		implements InputSequence {
	
	
	private static final Logger logger = Logger.getLogger(TestSequence.class);

	/** set of the test predicates covered by this one */
	protected SortedSet<T> coveredCases;

	/** the test condition it is generated for. */
	protected TestPredicate<? extends TestSequence<T>, ?> generatedForTC;
	
	/**
	 * Instantiates a new test sequence.
	 */
	public TestSequence(TestPredicate<? extends TestSequence<T>, ?> generatedFor) {
		logger.debug("generating a test sequence for " + (generatedFor== null? "tp not defined " : generatedFor.getName()));
		this.coveredCases = Collections.synchronizedSortedSet(new TreeSet<T>());
		generatedForTC = generatedFor;
	}

	
	/**
	 * Method getGeneratedFor.
	 * 
	 * @return T
	 */
	public TestPredicate<? extends TestSequence<T>, ?> getGeneratedFor() {
		return generatedForTC;
	}

	/**
	 * Method setGeneratedFor.
	 * 
	 * @param t
	 *            T
	 */
	public void setGeneratedFor(TestPredicate<? extends TestSequence<T>, ?> t) {
		logger.debug("setting " + this.toString() + " now generated for " + t.getName());
		assert generatedForTC == null || generatedForTC == t : 
			"generated for changed "+ generatedForTC.getName() + "->" + t.getName();
		generatedForTC = t;
	}

	/**
	 * Da usare con cautela. Mi serve quando genero per un TP ma mi serve per un altro come modello. Ad esempio come witness della collezione 
	 * 
	 * @param t
	 *            T
	 */
	public void changeGeneratedFor(TestPredicate<? extends TestSequence<T>, ?> t) {
		assert generatedForTC != null;
		generatedForTC = t;
	}

	
	
	/**
	 * Tp covered.
	 * 
	 * 
	 * @return the collection< test condition>
	 */
	public Collection<T> tpCovered() {
		return this.coveredCases;
	}

	/**
	 * add a to the tp covered by this.
	 * 
	 * @param a
	 *            the new test predicate to be added
	 */
	public void setCovers(T a) {
		coveredCases.add(a);
	}

	// for some reason, the previous one is not enough at compile time
	public void setCovers(Object a) {
		coveredCases.add((T) a);
	}

	/**
	 * Sets the cover all.
	 * 
	 * @param tgCovered
	 *            the new cover all
	 */
	public void setCoverAll(Collection<T> tgCovered) {
		coveredCases.addAll(tgCovered);
	}

	/**
	 * return the coverage info: the list of all the tP covered by this.
	 * 
	 * 
	 * @return the string buffer
	 */
	public StringBuffer coverageInfo() {
		StringBuffer info = new StringBuffer();
		// coverage information
		info.append("COVERS");
		for (T testGoal : coveredCases) {
			// do no change
			info.append(' ').append(testGoal.getUniqueID());
		}
		return info;
	}

	/** The fsm representing the state of this test sequence */
	private TestSeqFSM fsm = new TestSeqFSM();

	/**
	 * this test is useless.
	 */
	public void discardTest() {
		fsm.discard();
	}

	/**
	 * Gets the state.
	 * 
	 * 
	 * @return the state
	 */
	public TestSequenceState getState() {
		return fsm.getCurrent();
	}

	/**
	 * Method toString.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return coveredCases.toString();
	}

	/**
	 * add a new state
	 */
	abstract public void addState();

	/**
	 * add an assignment for a variable
	 * 
	 * @param varS
	 *            variable name
	 * @param val
	 *            var value
	 * @Deprecated use hat with variables pass the variable itself
	 */
	final public void addAssignment(String varS, String val) {
		// add assignment as variable
		addAssignment(getVar(varS), val);
	}

	/**
	 * add an assignment for a variable
	 * 
	 * @param varS
	 *            variable name
	 * @param val
	 *            var value
	 */
	abstract public void addAssignment(Variable varS, String val);

	/**
	 * from string to variable
	 * 
	 * @deprecated . it should be used the addAssignment without strings
	 */
	@Deprecated
	abstract protected Variable getVar(String s);

	/**
	 * the number of states
	 * 
	 */
	abstract public int numberOfStates();

	/** return the state number stateNum */
	abstract public Map<? extends Variable, String> getState(int stateNum);
}
