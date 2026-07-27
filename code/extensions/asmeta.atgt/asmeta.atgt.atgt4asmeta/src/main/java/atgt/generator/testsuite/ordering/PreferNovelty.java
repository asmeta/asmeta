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

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import atgt.coverage.AddTestsEvent;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.TestSuiteChangeEvent;
import atgt.coverage.TestSuiteChangeListener;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TestSuite;
import tgtlib.generator.ordering.NoCollectOrderWMonitoring;
import tgtlib.specification.Specification;

/** original TP processor which does not collect the TPS, but it orders them in order of novelty
 * the order is updates when a new test is added to the test suite.
 * A NwiseTcComparator will be used.
 * 
 * @author garganti
 *
 */
public class PreferNovelty<S extends Specification, TS extends TestSuite,TestSuiteChangeObservable, TC extends TestPredicate<?,?>> extends NoCollectOrderWMonitoring<TC>
		implements TestSuiteChangeListener {

	/** Logger for this class. */
	static final Logger log = Logger.getLogger(PreferNovelty.class);

	// the asm tests has been modified, reordering is necessary
	protected boolean toOrder;

	protected NWiseTcComparator nc = null; 

	/**
	 * 
	 * @param ts: the test suite to which the tests will be added
	 * @param spec: the spec (to count the ranges)
	 */
	public PreferNovelty(TS result, S specification, List<TC> cand) {
		super(cand);
		((AsmTestSuite) result).addTestSuiteChangeListener(this);
		this.nc = NWiseTcComparatorFactory.getInstance(specification);
		log.debug("using as comparator" + this.nc.getClass()+":\n"+this.nc.toString());
		toOrder = true;
	}

		
	
	@Override
	protected void setUpIterator() {
		// REORDER THE TESTS
		reorder();
		// get the new iterator
		candidatesIter = candidates.iterator();
	}
	/** reodrder the collection
	 * 
	 */
	private void reorder(){
		log.debug("reordering the candidates");
		Collections.sort(candidates, nc);
		toOrder = false;		
	}
	/**
	 * if the collection is again to order, build another iterator
	 */
	@Override
	public TC next() {
		if (toOrder) {
			// REORDER THE TESTS
			reorder();
		}
		TC result = super.next();
		log.debug("next test condition "+ (result == null ? " ==> null (last)" : result + (result instanceof EqTestCondition ? " with evalution " + nc.evaluate((EqTestCondition) result):"")));
		return result;
	}

	@Override
	public void testSuiteChange(TestSuiteChangeEvent evt) {
		if (evt instanceof AddTestsEvent) {
			log.debug("added a test sequence - update hits and reorder");
			AddTestsEvent nt_evt = (AddTestsEvent) evt;
			AsmTestSuite ts = nt_evt.getTestSuite();
			// update the hits
			for (AsmTestSequence ats : ts)
				nc.update(ats);
			// set to reorder
			toOrder = true;
		}

	}

}
