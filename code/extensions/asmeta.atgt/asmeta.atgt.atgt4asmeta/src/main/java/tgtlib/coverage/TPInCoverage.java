/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.coverage;

import tgtlib.definitions.NamedTerm;

/**
 * a test predicate in the coverage tree.
 * used composition instead of inheritance 
 * 
 * 
 * @author garganti
 * 
 */
//public class TPInCoverage<T extends Term,Q extends NamedTerm<T>> implements TestPredicateTreeNode<T,Q> {
public class TPInCoverage<Q extends NamedTerm> implements TestPredicateTreeNode<Q> {

	public TPInCoverage(Q nt) {
		testPredicate = nt;
	}

	public Q testPredicate;

	@Override
	public String getName() {
		return testPredicate.getName();
	}
	@Override
	public String toString(){
		return testPredicate.getName();
	}
	
	
}
