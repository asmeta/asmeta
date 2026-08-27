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

import atgt.coverage.tpstatus.TestConditionState;
import tgtlib.generator.TestConditionFilter;

/**
 * Una implementazione dell'interfaccia <code>TestConditionFilter</code>. Se
 * un TestCondition e' stato selezionato per la verifica, allora viene eseguito.
 * Se è coperto ma era prima da verificare, lo accetta lo stesso
 * 
 * @author AG
 */
public class DoQuequedUCovered implements TestConditionFilter<TestCondition<?>>  {

	/**
	 * Instantiates a new do quequed u covered.
	 */
	private DoQuequedUCovered() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.TestConditionFilter#accept(atgt.coverage.TestCondition)
	 */
	@Override
	public boolean accept(TestCondition<?> tc) {
		return (tc.getStatus() == TestConditionState.Queued || (tc
				.getPreviousStatus() == TestConditionState.Queued));
	}

	/** The Do quequed u covered. */
	public static DoQuequedUCovered DoQuequedUCovered = new DoQuequedUCovered();
}
