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

import tgtlib.generator.TestConditionFilter;

/**
 * Una implementazione dell'interfaccia <code>TestConditionFilter</code>. Se
 * un TestCondition e' stato selezionato per la verifica, allora viene eseguito
 * solo se non già coperto da altri.
 * 
 * E' utile? Può essere non empty e toVerify? Quando non è empty
 * automaticamente non cambia stato? da verificare AG
 * 
 * @author Sax Rinzivillo
 */
public class SkipCoveredTCFilter implements TestConditionFilter<TestCondition<?>> {

	/** The Skip covered tc filter. */
	public static SkipCoveredTCFilter SkipCoveredTCFilter = new SkipCoveredTCFilter(true);

	private boolean checkCoveredByEmptyness;

	
	/**
	 * Instantiates a new skip covered tc filter.
	 */
	private SkipCoveredTCFilter(boolean checkCoveredByEmptyness) {
		this.checkCoveredByEmptyness = checkCoveredByEmptyness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.TestConditionFilter#accept(atgt.coverage.TestCondition)
	 */
	@Override
	public boolean accept(TestCondition tc) {
		return (tc.isToVerify() && ( !checkCoveredByEmptyness || tc.allCoveredBy().isEmpty()));
	}

}
