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
package atgt.parser.trail;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.TestCondition;
import tgtlib.definitions.TestSequence;
import tgtlib.generator.MCAnalysisResult;

/**
 * Counter example parser
 * 
 * PARTE DELLE FUNZIONALITtà di TestGenerator in più ha il COverageTree.
 */
public interface CexParser<T extends TestSequence<? extends TestCondition<?>>> {

	/**
	 * take the result from the execution of the model checker and returns the
	 * analysis.
	 * 
	 * @param tcIndex
	 *            the tc index of the coverage tree
	 * @param ts
	 *            the test sequence initially empty to be filled by the parsing
	 * 
	 * @return the MC analysis result< t>
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * 
	 * TODO in futuro dovrebbe perendere un contenthadler e restituire le
	 * sequenza come è in SCR
	 */
	public MCAnalysisResult analysis(AsmCoverageTree tcIndex, T ts)
			throws atgt.parser.trail.ParseException;

}
