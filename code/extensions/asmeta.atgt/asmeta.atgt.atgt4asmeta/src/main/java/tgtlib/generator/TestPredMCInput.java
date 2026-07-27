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
package tgtlib.generator;

import tgtlib.definitions.TestPredicate;

/** input as only a test predicate
 * 
 * @author garganti
 *
 */
public class TestPredMCInput<Q extends TestPredicate<?,?>> implements  MCInput<Q>{

	public TestPredMCInput(Q tg) {
		tc = tg;
	}
	// the test goal
	public Q tc;

}
