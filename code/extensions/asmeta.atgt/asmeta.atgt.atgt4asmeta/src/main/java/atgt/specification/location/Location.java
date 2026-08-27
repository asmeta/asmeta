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
package atgt.specification.location;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.Type;

/**
 * A generic memory location. Contains common methods for variables and
 * constants. And function applications , like f(3)
 * 
 * @author Sax Rinzivillo
 */

abstract public class Location extends AsmTerm {

	/**
	 * Is this variable monitored? There exist a statement that assign a value
	 * to this variable?.
	 */
	static public enum VarKind {
		/** The MONITORED. */
		MONITORED,
		/** The CONTROLLED. */
		CONTROLLED
		// TODO. what if it is controlled???
	}

	/** The var kind. */
	protected VarKind varKind;

	/**
	 * Create a new Location.
	 * 
	 * @param _name
	 *            Name of location
	 * @param _type
	 *            Type of location
	 * @param _value
	 *            Initial value of location
	 */
	@Deprecated
	public Location(IdExpression _name, Type _type, Expression _value) {
		super(_name, _type, _value);
	}

	/**
	 * Create a new Location.
	 * 
	 * @param _name
	 *            Name of location
	 * @param _value
	 *            Initial value of location
	 */
	public Location(IdExpression _name, Expression _value) {
		super(_name, _value);
		assert _name.getType() != null;
	}

	/**
	 * Is the variable monitored?.
	 * 
	 * @return true, if checks if is monitored
	 */
	public boolean isMonitored() {
		return varKind == VarKind.MONITORED;
	}

	/**
	 * Is the variable controlled?.
	 * 
	 * @return true, if checks if is controlled
	 */
	@Override
	public boolean isControlled() {
		return varKind == VarKind.CONTROLLED;
	}

	/**
	 * Something attempt to change the variable value.
	 */
	public void setMonitored() {
		varKind = VarKind.MONITORED;
	}

	/**
	 * Something attempt to read the variable value.
	 */
	public void setControlled() {
		varKind = VarKind.CONTROLLED;
	}

	/**
	 * Gets the var kind.
	 * 
	 * @return the varKind
	 */
	public VarKind getVarKind() {
		return varKind;
	}
}
