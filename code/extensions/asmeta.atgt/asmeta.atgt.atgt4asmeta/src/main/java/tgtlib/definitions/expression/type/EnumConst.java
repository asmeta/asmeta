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

import tgtlib.definitions.expression.IdExpression;

/**
 * represents the enum constant. It is like Id + Type which is EnumType
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class EnumConst extends IdExpression {

	/**
	 * the type to which this constant belongs to. it can be null, assuming that
	 * EnumConsts exist before EnumTypes
	 * */
	ElementsType type;

	/**
	 * builds an enum constant.
	 * 
	 * @param id
	 *            the _id
	
	 */
	EnumConst(String id) {
		super(id, null);
		type = null;
	}

	/**
	 * 
	 * @param et EnumType
	 */
	public void setType(ElementsType et) {
		// ///assert type == null : this.id + " has already type " + this.type;
		assert type == null : "type of " + this.id + " already set to "
				+ type.getName();
		type = et;
	}

	/**
	 * 
	 * @return EnumType
	 */
	@Override
	public ElementsType getType() {
		return type;
	}

	/**
	 * if boolean return true and false, since they are nicer. Who needs 0 and 1 must override this method
	 * @return String
	 */
	@Override
	public String toString() {
		return toStrCheckBool(this, BoolType.FALSE_STR,BoolType.TRUE_STR);
	}
/**
 * return the string of this id considering also the booleans
 * @param id
 * @param falseStr
 * @param trueStr

 * @return String
 */
	public static String toStrCheckBool(IdExpression id,String falseStr, String trueStr){
		// use of == instead of equals in the future
		if (id.equals(BoolType.FALSE_CONST)){
			assert id == BoolType.FALSE_CONST;
			return falseStr;
		}
		if (id.equals(BoolType.TRUE_CONST)){
			assert id == BoolType.TRUE_CONST;
			return trueStr;
		}
		String idS = id.getIdString();
		// cannot be boolean !!
		assert !idS.equalsIgnoreCase("true");
		assert !idS.equalsIgnoreCase("false");
		return idS;
	}
	
	/**
	 * two enum consts are equals if their id is equals and their type.
	 * 
	 * @param o
	 *            the o
	 * 
	
	 * @return true, if equals */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof EnumConst) {
			EnumConst oe = (EnumConst) o;
			return (this.id.equals(oe.id) && type.equals(oe.type));
		}
		return false;
	}

}
