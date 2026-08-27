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
package tgtlib.definitions.expression.type;

/**
 * A class to handle variables and constants types.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 * @version $Revision: 1.0 $
 */

abstract public class Type {

	/** Name declaration. */
	protected String name;

	/**
	 * number of elements in this type.
	 * 
	
	 * 
	 * @return the int */
	public abstract int range();

	/**
	 * The Constructor.
	 * 
	 * @param _name
	 *            Type name
	 */
	public Type(String _name) {
		this.name = _name;
	}

	/**
	 * Returns the type name.
	 * 
	
	 * @return the name */
	public String getName() {
		return this.name;
	}

	/**
	 * Two types are equals iff they have the same name.
	 * 
	 * @param o
	 *            the o
	 * 
	
	 * @return true, if equals */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Type)
			return (((Type) o).getName().equals(this.name));
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * A method for visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	
	 * @return the T */
	abstract public <T> T accept(TypeVisitorI<T> ask);
}
