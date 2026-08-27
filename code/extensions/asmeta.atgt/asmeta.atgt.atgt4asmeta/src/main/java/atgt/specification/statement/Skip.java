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
 * The reserved command in ASM Maybe it's possible to delete this class. It does
 * nothing. But it could be useful for "leggibilita'"
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class Skip extends BasicRule {

	/**
	 * Instantiates a new skip.
	 */
	private Skip() {
	}

	/** The Constant SKIP. */
	public static final Skip SKIP = new Skip();

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forSkip(this);
	}

}
