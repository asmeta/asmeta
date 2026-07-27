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

/** input to be given to the model checker is a string + the test predicate
 * 
 * @author garganti
 *
 */
public class StringAndTPMCInput<Q extends TestPredicate<?,?>> extends  TestPredMCInput<Q>{

	public StringAndTPMCInput(StringBuffer spec2, Q tg) {
		super(tg);
		spec = spec2;
	}

	/**
	 * @return the spec
	 */
	public StringBuffer getSpec2() {
		return spec;
	}

	// the specification as String
	private StringBuffer spec;

}
