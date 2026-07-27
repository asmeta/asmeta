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

/**
 * visitor of coverage, coverage tree (as superclass) and tps.
 */
public interface CoveragesVisitorI<T> extends TPTreeNodeVisitor<T> {

	/**
	 * For test condition.
	 * 
	 * @param tc
	 *            the tc
	 * 
	 * @return the t
	 */
	public T forTestCondition(AsmTestCondition tc);
}
