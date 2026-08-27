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
package atgt.generator.testsuite.ordering;

import java.util.List;

import org.apache.log4j.Logger;

import atgt.combinatorial.CombinatorialTestCondition;
import atgt.coverage.TestSuiteChangeEvent;
import atgt.coverage.TestSuiteChangeListener;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSuite;
import tgtlib.specification.Specification;

/** 
 * extended TP processor which is aware also of the TP collected so far
 * @author garganti
 *
 */
public class PreferNoveltyCollect<S extends Specification, TS extends TestSuite,TestSuiteChangeObservable, TC extends TestPredicate<?,?>> extends PreferNovelty
		implements TestSuiteChangeListener, TPCollectedChangeListener<CombinatorialTestCondition> {

	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(PreferNoveltyCollect.class);

	/**
	 * 
	 * @param ts: the test suite to which the tests wil be added
	 * @param spec: the spec (to count the ranges)
	 */
	public PreferNoveltyCollect(TS ts, S spec,List<TC> cand) {
		super(ts,spec,cand);		
		log.debug("creating");
	}

	@Override
	public void reset(){
		log.debug("resetting the collection");
		super.reset();
	}
	
	@Override
	public void TPAdded(CombinatorialTestCondition tc) {
		log.debug("adding tc");
		EqTestCondition etc = (EqTestCondition) tc;
		for (int i = 0; i < etc.size(); i++) this.nc.update(etc.getVar(i).toString(), etc.getVal(i).toString());
		// set to reorder
		toOrder = true;		
	}
	
	@Override
	public void testSuiteChange(TestSuiteChangeEvent evt) {
		
	}

}
