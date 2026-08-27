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
package tgtlib.evalcoverage;

import java.util.Vector;

import tgtlib.definitions.NavigableInputSequence;
import tgtlib.definitions.TestPredicate;

/**
 * Given a test (as input Sequence) evaluates the coverage note that it takes an
 * input sequence (and not a test sequence)
 *
 * @author garganti
 */
public interface CoverageEvaluator<S extends TestPredicate<?, ?>> {

	/**
	 * Compute coverage.
	 * 
	 * @param inputs
	 *            the test sequence containing the sequence of inputs
	 * 
	 * @return the vector< test goal>
	 */
	public Vector<S> computeCoverage(NavigableInputSequence inputs);

}
