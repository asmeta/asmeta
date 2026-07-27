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


/** like a tree of tp, but visitable by a coverage/coverage tree visitor
 *
 * @author garganti
 *
 */
public interface VisitableTPTreeNode {
	/**
	 * Accept.
	 *
	 * @param ask
	 *            the ask
	 *
	 * @return the t
	 */
	public <T> T accept(TPTreeNodeVisitor<T> ask);


}
