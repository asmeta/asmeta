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

/** the input taken by the model checker to produce the tests. 
 * The input changes in part or totally by changing the single test goal or test requirenet of type Q
 * 
 * @author garganti
 *
 */
public interface MCInput <Q extends TestPredicate<?,?>>{

}
