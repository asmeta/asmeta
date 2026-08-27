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
package extgt.coverage.combinatorial;


import tgtlib.coverage.CoverageTree;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.TestPredicate;
import tgtlib.specification.Specification;

/**
 * builds the test predicates for PairwiseCovBuilder.
 */
public abstract class PairwiseCovBuilder<S extends Specification, P extends TestPredicate<?,?>,C extends CoverageTree<? extends P>> extends CombinatorialCovBuilder<S, C> {

	
	protected PairEqTestCondFactory<P> petFact;
	
	protected CoverageTreeFactory<? extends C> covFactory;

	/**
	 * with its own monitored data extractor.
	 * 
	 * @param monDatExt
	 *            the mon dat ext
	 */
	protected PairwiseCovBuilder(MonitorDataExtractor<S> monDatExt, PairEqTestCondFactory<P> pf, CoverageTreeFactory<? extends C> cf) {
		super(monDatExt);
		petFact = pf;
		covFactory = cf;
	}
}
