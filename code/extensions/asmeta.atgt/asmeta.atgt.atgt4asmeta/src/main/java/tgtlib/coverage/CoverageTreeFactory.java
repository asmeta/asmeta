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
package tgtlib.coverage;

/** to build empty coverage tree
 * 
 * @author garganti
 *
 * @param <C>
 */
public abstract class CoverageTreeFactory<C extends CoverageTree<?>> {

	/** build an empty Coverage with name name
	 * 
	 * @param name
	 * @return
	 */
	public abstract C buildEmptyCovTree(String name);

}
