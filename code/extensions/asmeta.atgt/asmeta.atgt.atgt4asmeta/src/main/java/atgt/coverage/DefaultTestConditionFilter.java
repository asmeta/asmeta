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

import tgtlib.definitions.TestPredicate;
import tgtlib.generator.TestConditionFilter;

/**
 * Una implementazione dell'interfaccia <code>TestConditionFilter</code>. Se
 * un TestCondition e' stato selezionato per la verifica, allora viene eseguito.
 * 
 * ATTENZION se uno è coperto allora viene filtrato (uguale al comportamento di
 * SKipAlreadyCOvered)
 * 
 * @author Sax Rinzivillo
 */
public class DefaultTestConditionFilter implements TestConditionFilter<TestPredicate<?,?>>  {

	/**
	 * Instantiates a new default test condition filter.
	 */
	private DefaultTestConditionFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.TestConditionFilter#accept(atgt.coverage.TestCondition)
	 */
	@Override
	public boolean accept(TestPredicate tc) {
		return tc.isToVerify();
	}

	/** The Default test condition filter. */
	public static DefaultTestConditionFilter DefaultTestConditionFilter = new DefaultTestConditionFilter();
}
