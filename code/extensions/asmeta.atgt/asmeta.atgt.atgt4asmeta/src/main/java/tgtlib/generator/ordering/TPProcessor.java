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

import tgtlib.definitions.TestPredicate;

/***
 * processes all the selected tps to extract one at the time It's a sort of
 * iterator
 * 
 * @author garganti
 * 
 * @param <T>
 */
abstract public class TPProcessor<T extends TestPredicate<?,?>> {
		
	/**
	 * returns the next (feasible) test predicate.
	 * <P>
	 * it returns null, if no more in the list
	 */
	abstract public T next();

	/**
	 * removes from the underlying collection the last element returned by the
	 * calling the next method
	 */
	abstract public void remove();

	/**
	 * restart the iteration from the beginning
	 */
	abstract public void reset();

}
