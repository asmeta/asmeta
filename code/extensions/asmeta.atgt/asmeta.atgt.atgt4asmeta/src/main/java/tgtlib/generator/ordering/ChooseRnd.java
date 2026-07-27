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
package tgtlib.generator.ordering;

import java.util.Collections;
import java.util.List;

import tgtlib.definitions.TestPredicate;

/**
 */
public class ChooseRnd<T extends TestPredicate> extends NoCollectOrderWMonitoring<T> {

	/**
	 * Constructor for ChooseRnd.
	 * @param candidates List<T>
	 */
	ChooseRnd(List<T> candidates) {
		super(candidates);
	}

	@Override
	protected void setUpIterator() {
		Collections.shuffle(candidates);
		candidatesIter = candidates.iterator();
	}

}
