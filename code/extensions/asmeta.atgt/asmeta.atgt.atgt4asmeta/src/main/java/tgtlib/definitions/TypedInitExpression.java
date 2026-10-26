/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.definitions;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.Type;

/** Idterm: ID + type + initial value 
 * can be a constant or a location (function or variable)
 * TODO subclass of expression
 * */
public class TypedInitExpression extends TypedExpression implements tgtlib.definitions.expression.type.Variable {

	/** initial value of location. */
	private Expression value;

	/** 
	 * @param id
	 * @param _type : type of id
	 * @param _value : initial value (can be null)
	 * @deprecated : use the constructor without type information, which should be included in the ID now
	 */
	@Deprecated
	public TypedInitExpression(IdExpression id, Type _type, Expression _value) {
		super(id,_type);
		setValue(_value);
		// the type must me consistent (it cannot be null) 
		assert id.getType() == _type : "id:" + id.getIdString() + " with stored type " + id.getType() + " and now created with type " + _type;
	}

	/**
	 * 
	 * @param id (is with its type)
	 * @param _value (can be null)
	 */
	public TypedInitExpression(IdExpression id, Expression _value) {
		this(id,id.getType(), _value);
		assert id.getType() != null : "id " + id.getIdString() + " has no type";
	}
	
	
	/**
	 * Returns the name of location.
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return this.name.getIdString();
	}

	/**
	 * Return type of location.
	 * 
	 * @return the type
	 */
	@Override
	public Type getType() {
		return this.type;
	}

	/**
	 * Return the initial value of location. If it is undefined returns UndefExrpession
	 * 
	 * @return the value
	 */
	@Override
	public Expression getValue() {
		return this.value;
	}

	/**
	 * set the intial value of location it can be null, if the initial value is
	 * unkwnon.
	 * 
	 * @param _value
	 *            the _value
	 */
	public final  void setValue(Expression _value) {
		if (_value != null) this.value = _value;
		else value = Undef.UNDEF;
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Gets the id expression.
	 * 
	 * @return the id expression
	 */
	@Override
	public IdExpression getIdExpression() {
		return name;
	}
	@Override
	public boolean isControlled() {
		throw new RuntimeException("not known");
	}	
}
