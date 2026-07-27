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
 * The Interface TPTreeNodeVisitor.
 */
public interface TPTreeNodeVisitor<T> {

	/**
	 * For coverage tree.
	 *
	 * @param c
	 *            the c
	 *
	 * @return the t
	 */
	public abstract T forCoverageTree(AsmCoverageTree c);

	/**
	 * For coverage.
	 *
	 * @param c
	 *            the c
	 *
	 * @return the t
	 */
	public abstract T forCoverage(Coverage c);

}
