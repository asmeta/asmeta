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
package atgt.combinatorial;

import atgt.coverage.AsmTestCondition;
import extgt.coverage.combinatorial.EqTestCondition;
import tgtlib.coverage.CoverageTreeFactory;

/**
 * The Class PairwiseCoverage or Nwise coverarage it accepts only EqTestConditions
 * it represents the n-wise coverage (with also n = 2)
 */
public class NWiseCoverage extends atgt.combinatorial.CombinatorialCoverage {

	/**
	 * Instantiates a new pairwise coverage.
	 * 
	 * @param _name
	 *            the _name
	 */
	protected NWiseCoverage(String _name) {
		super(_name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.Coverage#addTestCondition(atgt.coverage.TestCondition)
	 */
	@Override
	public boolean addNode(AsmTestCondition tc) {
		assert ( tc instanceof EqTestCondition);
		// only NWiseEqTestCondition or PairEqTestCondition
		if (!(tc instanceof NWiseEqTestCondition) && ! (tc instanceof PairEqTestCondition))
			throw new RuntimeException("adding a tc of type "+tc.getClass() + " to a NWiseCoverage");
		// FIXME 
		// PairEqTestCondition cpuld be accepted
		return super.addNode(tc);
	}
	
	static public CoverageTreeFactory<NWiseCoverage> factory = new CoverageTreeFactory<NWiseCoverage>() {
		
		@Override
		public NWiseCoverage buildEmptyCovTree(String name) {
			return new NWiseCoverage(name);
		}
	};

}
