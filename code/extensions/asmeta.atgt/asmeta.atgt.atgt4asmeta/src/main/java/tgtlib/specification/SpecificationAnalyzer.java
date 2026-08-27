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
package tgtlib.specification;

/**
 * analyze a specification an return an objetc of type T
 */
public interface SpecificationAnalyzer<T, S extends Specification> {

	/**
	 * Analyze.
	 * 
	 * @param specification
	 *            the specification to be anlyzed
	 * 
	 * @return the t
	 */
	public T analyze(S specification);

}
