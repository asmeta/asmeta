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

import tgtlib.specification.Specification;

/**
 * A CoverageBuilder represents a method to obtain a tree of test predicates. Every
 * coverage must implement the method getTPTree computing a test tree starting
 * from a specification
 * 
 * @author garganti
 */
public interface CoverageBuilder<S extends Specification, C extends CoverageTree<?>> {

	/**
	 * it builds the tree (it does not assign the ID).
	 * 
	 * @param spec
	 *            the specification to be analyzed
	 * 
	 * @return the tree done in this way:
	 */
	public abstract C getTPTree(S spec);

	/**
	 * returns the unique prefix for the test goal generated for this coverage.
	 * 
	 * @return the coverage prefix
	 */
	public abstract String getCoveragePrefix();

}
