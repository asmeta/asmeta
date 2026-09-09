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
package tgtlib.definitions.expression;

import tgtlib.definitions.expression.type.Type;

/**
 * symbolic variables, constant and so on. They are immutable. No two instances with the
 * same string id exist. It is case sensitive: two instances with different case may
 * exist. to create instance of this, use IDexpression creator
 * This id is typeless:TODO add type information
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 * @version $Revision: 1.0 $
 */

public class IdExpression implements Comparable<IdExpression>,
		PrimedIdUIdExpression, Expression, IdUNotIdExpression {

	// to memorize where it is created useful for debugging
	// protected IdExpressionCreator icc;
	
	/** The id. */
	protected String id;
	
	protected Type type;

	/**
	 * Instantiates a new id expression. TODO make private and use IDExpressore
	 * Creator instead
	 * 
	 * @param _id
	 *            the _id
	 * @param _type the type : it can be null
	 */
	protected IdExpression(String _id, Type _type) {
		this.id = _id;
		this.type = _type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.expression.Expression#accept(atgt.specification.expression
	 * .ExpressionVisitorI)
	 */
	/**
	 * Method accept.
	 * 
	 * @param ask
	 *            ExpressionVisitor<T>
	 * @return T
	 * @see 
	 *      tgtlib.definitions.expression.Expression#accept(ExpressionVisitor<T>)
	 */
	@Override
	public <T> T accept(ExpressionVisitor<T> ask) {
		return ask.forIdExpression(this);
	}

	/**
	 * Gets the id.
	 * 
	 * 
	 * @return the id
	 */
	public String getIdString() {
		return this.id;
	}

	/**
	 * Method getID.
	 * 
	 * @return IdExpression
	 * @see tgtlib.definitions.expression.PrimedIdUIdExpression#getID()
	 */
	@Override
	public IdExpression getID() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.expression.Expression#toString()
	 */
	@Override
	public String toString() {
		// TEMP: if bool, it should be not the boolean true and false
		assert !id.equalsIgnoreCase("true");
		assert !id.equalsIgnoreCase("false");
		//
		return this.id;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	/**
	 * Method compareTo.
	 * 
	 * @param o
	 *            IdExpression
	 * @return int
	 */
	@Override
	public int compareTo(IdExpression o) {
		return this.id.compareTo(o.id);
	}

	/**
	 * override equal method for objects
	 * 
	 * 
	 * @param e2
	 *            Object
	 * @return true if refers to the same expression
	 */
	@Override
	public boolean equals(Object e2) {
		if (this == e2)
			return true;
		if (!(e2 instanceof IdExpression))
			return false;
		// no ids can have equal string ids unless they are identical !
		if (id.equals(((IdExpression) e2).id)) return true;
		//XXX Marco Radave rimosso.... assert ! id.equals(((IdExpression) e2).id) : id + " " +(id == ((IdExpression) e2).id) ;
		// + " icc1 " + icc + " icc2" + ((IdExpression) e2).icc;
		return false;
	}	
	
	/** return the type of this id expression. In this way this can sobsitritue the variables (TODO eliminate variables use only
	 * this. The type can be useful when dealing with operations (like =) and so on.
	 */
	public Type getType(){
		return type;
	}

	public void setType(Type _type) {
		// allow only if type is not already set. This is to avoid problems with the type of the same id expression created in different contexts
		assert type == null : "type already set for " + id + " to " + type + " cannot set to " + _type;
		type = _type;
	}	
}
