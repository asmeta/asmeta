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

import java.util.Hashtable;
import java.util.Iterator;

import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.util.IterableIterator;

/**
 * Contenitore per i coverage da creare per una data una specifica � il
 * contenitore globale contiene Coverage.
 * 
 * non contiene direttamente test predicates
 * 
 * @author Angelo Gargantini
 */
public class AsmCoverageTree extends AsmCoverage{
	/**
	 * Una hash table per la ricerca veloce dei test condition dei vari criteri
	 * di copertura.
	 */
	protected TPIndex testConditionsIndex;

	/**
	 * Instantiates a new coverage tree.
	 * 
	 * @param _name
	 *            the _name
	 */
	public AsmCoverageTree(String _name) {
		super(_name);
		// be tolerant, accept also TPs
		super.contentType = ContentTypes.COVERAGE_TREE;
	}

	/**
	 * return the factory for this coverage
	 * 
	 * @return
	 */	
	public final static CoverageTreeFactory<AsmCoverage> factory = new CoverageTreeFactory<AsmCoverage>() {

		@Override
		public AsmCoverageTree buildEmptyCovTree(String n) {
			return new AsmCoverageTree(n);
		}
	};

	

	/**
	 * All coverages.
	 * 
	 * @return the list< test predicate tree node>
	 */
	public Iterable<VisitableTPTreeNode> allCoverages() {
		return new IterableIterator<VisitableTPTreeNode>(
				new Iterator<VisitableTPTreeNode>() {			
			Iterator<?> i = subTP.iterator();
			@Override
			public boolean hasNext() {
				return i.hasNext();
			}

			@Override
			public VisitableTPTreeNode next() {
				return (VisitableTPTreeNode) i.next();
			}

			@Override
			public void remove() {
				i.remove();
				
			}
		});
	}

	/**
	 * Adds the coverage.
	 * 
	 * @param c
	 *            the c
	 */
	public void addCoverage(AsmCoverage c) {
		this.addNode(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.coverage.TestPredicateTreeNode#accept(atgt.specification
	 * .coverage.TPTreeNodeVisitor)
	 */
	@Override
	public <T> T accept(TPTreeNodeVisitor<T> ask) {
		return ask.forCoverageTree(this);
	}

	/**
	 * Costruisce la hashTable per la ricerca dei test condition.
	 */
	private void buildTestConditionIndex() {
		this.testConditionsIndex = accept(ToTpIndex.INSTANCE);
	}

	/**
	 * Gets the test condition index, i.e. an hash table with all the test
	 * conditions in the coverage tree (including the subtrees)
	 * 
	 * 
	 * @return the test condition index
	 * 
	 * @deprecated use the iterator instead
	 */
	@Deprecated
	public Hashtable<String, AsmTestCondition> getTestConditionIndex() {
		if (this.testConditionsIndex == null)
			buildTestConditionIndex();
		return this.testConditionsIndex;
	}

	/**
	 * Cerca nell'indice dei test condition il test con l'ID specificato. Nel
	 * caso in cui l'indice non esiste, allora ne crea uno.
	 * 
	 * @param testCase
	 *            the test case unique ID
	 * 
	 * @return the test condition with that ID
	 */
	public TestCondition findTestCondition(String testCaseID) {
		if (this.testConditionsIndex == null)
			buildTestConditionIndex();
		return this.testConditionsIndex.get(testCaseID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}
}
