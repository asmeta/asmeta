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
 * Handles enumeration types in Gopher.<BR>
 * 
 * <PRE>
 * 
 * Ex: data Switch = On | Off
 * 
 * </PRE>
 * 
 * NOTE: Booleans are a special case of enumeration
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class EnumType extends ElementsType {

	/**
	 * Instantiates a new enum type.
	 *
	 * @param _name
	 *            of the type
	 * @param consts
	 *            the constants in the enum
	 */
	public EnumType(String _name, EnumConst... consts) {
		super(_name);
		for (EnumConst ec : consts)
			addElement(ec);
	}

	/**
	 * Value.
	 * 
	 * @param index
	 *            the index
	 * 
	 * @return value element at pos index
	 * 
	 * @author Andrea Calvagna 2007
	 */
	public EnumConst value(int index) {
		return this.elements.get(index);
	}

	/**
	 * A method for visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	@Override
	public <T> T accept(TypeVisitorI<T> ask) {
		return ask.forEnumType(this);
	}

	/**
	 * return the enum const with the string as id.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the enum const
	 */
	public EnumConst getEnumConst(String s) {
		for (EnumConst ec : allElements()) {
			if (ec.getIdString().equals(s))
				return ec;
		}
		return null;
	}
}
