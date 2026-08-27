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

import java.util.List;

import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.coverage.TestPredicateTreeNode;

/**
 * Un Coverage is the set of test predicate for a given coverage criterion. I
 * Coverage vengono poi raggruppate nel coverageTree.
 * 
 * it contains only test predicates (aka test conditions)
 *
 * @author Sax Rinzivillo, Angelo Gargantini
 */
public class Coverage extends AsmCoverage{

	public static CoverageTreeFactory<AsmCoverage> factory = new CoverageTreeFactory<AsmCoverage>() {

		@Override
		public AsmCoverage buildEmptyCovTree(String n) {
			return new Coverage(n);
		}
	};;

	/**
	 * Builds an empty list of test predicates. Only the nbame is assigned.
	 *
	 * @param _name
	 *            the _name
	 */
	public Coverage(String _name) {
		super(_name);
		// be tolerant accept also to be used to contain other Coverages ...
		// useful for fault based testing
		//contentType = ContentTypes.TP;
	}
	
	/**
	 * Costruisce un nuovo insieme di casi di test. Assegna alla lista dei test
	 * condition la lista <code>_testCondition</code>
	 *
	 * @param _name
	 *            the _name
	 * @param _testConditions
	 *            the _test conditions
	 */
	public Coverage(String _name, List<? extends AsmTestCondition> _testConditions) {
		this(_name);
		addTestConditions(_testConditions);
	}

	/**
	 * Aggiunge un nuovo caso di test alla lista dei test condition.
	 *
	 * @param tc
	 *            the tc
	 */
	public final void addTestCondition(AsmTestCondition tc) {
		addNode(tc);
	}
	
	/** check that only tp are added, not coverages
	 * 
	 */
	@Override
	public boolean addNode(TestPredicateTreeNode<AsmTestCondition> node){
		return super.addNode(node);
	}
	

	/**
	 * Un metodo per il visitor pattern.
	 *
	 * @param ask
	 *            the ask
	 *
	 * @return the T
	 */
	@Override
	public <T> T accept(TPTreeNodeVisitor<T> ask) {
		return ask.forCoverage(this);
	}

	/**
	 * returns the name of the coverage.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		/*
		 * StringBuffer result = new StringBuffer("COVERAGE");
		 * result.append(name); for (TestCondition tc : testConditions ){
		 * result.append(tc.toString()).append("\n"); } return
		 * result.toString();
		 */
		return this.name;
	}
}
