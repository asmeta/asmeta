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
package tgtlib.definitions;

/**
 * 
 * @author garganti
 *
 * @param <Q> test sequences
 * @param <T>
 */
//public interface TestSequenceFactory <Q extends TestSequence<T>, T extends TestPredicate<Q>> {
public interface TestSequenceFactory <Q extends TestSequence<? extends T>, T extends TestPredicate<? extends Q,?>> {
//public interface TestSequenceFactory <Q extends TestSequence<?>> {
	/** build a new Test sequence 
	 * 
	 * @param n
	 * @param expression
	 * @return
	 */
	Q buildTestSequence(T tp);
	// TODO: it could be simplified as Q buildTestSequence(TestPredicate<? extends Q,?> tp);

}
