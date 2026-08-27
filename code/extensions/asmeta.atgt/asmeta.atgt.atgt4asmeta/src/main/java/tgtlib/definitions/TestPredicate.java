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

import tgtlib.definitions.expression.Expression;

/**
 * a test predicate represents a test goal: an expression + description + other info
 * 
 * Test condition or test goal refer to the same concept.
 * To be merged with NamedTerm (only one class is really needed)
 *
 * @param <T> the generic type representing test sequences linked to this test predicate
 * @author garganti
 */
//public abstract class TestPredicate<T extends TestSequence<? extends TestPredicate<T,?>>> extends NamedTerm{
public abstract class TestPredicate<T extends TestSequence<?>,ST> extends NamedTerm{
			
	
	/** covered by the following test sequences */
	protected Collection<T> coveredBy;
	
	/**
	 * 
	 * @param name of the test predicate (descriptive)
	 * @param condition
	 */
	public TestPredicate(String name, Expression condition) {
		super(name, condition);
		// build the collection of covered by		
		coveredBy = buildCoveredBy();	
	}
	/** build covered by 
	 * @return **/
	abstract protected Collection<T> buildCoveredBy();

	
	/** return the unique ID of a test predicate*/
	abstract public String getUniqueID();
	
	/** mark this TestPredicate as infeasible */
	abstract public void markInfeasible();

	/** bind the test sequence and the test set together: it means that the test predicate is covered by the test and the test covers the test predicate
	 * @param test
	 * TODO make this final
	 */
	public /*final*/ void bindTestSeqTestPred(T test){
		// the signature of the generics must be fixed
		test.setCovers(this);	
		coveredBy.add(test);		
	}
	/** reset the data about this test predicate
	 * 	
	 */
	protected void resetCoveredCases() {
		// dovrei fare il reset anche dei tp coperti da questo?
		// cioè toglierlo da covered By?
		buildCoveredBy().clear();		
	}

	/** is to be verified???
	 * 
	 */
	public abstract boolean isToVerify();
	
	/** return the status
	 * 
	 * @return the status of this test predicate
	 */
	public abstract ST getStatus();
}
