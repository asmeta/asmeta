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
package tgtlib.coverage;

import tgtlib.definitions.NamedTerm;

/**
 * represents a node in the tree of the coverage tree or test predicate tree
 * Every node can be a real tree (internal node) or a leaf in ATGT can be also a
 * coverage that represents a node with only leaf.
 *
 * @author garganti
 */
// <T extends Term,Q extends NamedTerm<T>>{
public interface TestPredicateTreeNode <Q extends NamedTerm>{
	
	
	/** returns the name or a synthetic description of this node
	 * 
	 * @return the name of the node 
	 */
	public String getName();


}
