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
package atgt.specification.statement;

// TODO: Auto-generated Javadoc
/**
 * A general statement Generalize ifThenElse statements, do statements and
 * assignment.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

abstract public class BasicRule {

	/**
	 * Accept.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the t
	 */
	abstract public <T> T accept(RuleVisitor<T> ask);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return accept(new StatementToStringVisitor());
	}
}
