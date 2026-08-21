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
package atgt.specification.type;

import tgtlib.definitions.expression.type.Type;
import tgtlib.definitions.expression.type.TypeVisitorI;

/**
 * Handles abstract type - in the past it was used to handle dummy types.
 */
public class AbstractType extends Type {

	/**
	 * Instantiates a new dummy type.
	 * 
	 * @param _name
	 *            the _name
	 */
	public AbstractType(String _name) {
		super(_name);
	}

	/**
	 * A methdo for visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	@Override
	public <T> T accept(TypeVisitorI<T> ask) {
		throw new RuntimeException("for abstracttype");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.type.Type#range()
	 */
	@Override
	public int range() {
		throw new RuntimeException("range for abstracttype");
	}

}
