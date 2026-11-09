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

import java.util.Collections;
import java.util.List;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.specification.location.Function;
import tgtlib.specification.location.FunctionApplication;
import tgtlib.specification.location.Location;
import tgtlib.specification.location.LogicalVariable;
import tgtlib.specification.location.AsmVariable;

/**
 * UpdateRule statement: var:=val or
 *  loc(a1,1n) := val
 * 
 * @author Sax Rinzivillo, Angelo Gargantini, Sergio Galati
 */

public class UpdateRule extends BasicRule {

	/** The location - can be also a function application. */
	protected Location location;

	/** The value. */
	protected Expression value;

	/**
	 * Instantiates a new update rule.
	 *  
	 * @param _var
	 *            the _var
	 * @param _value
	 *            the _value
	 */
	public UpdateRule(AsmVariable _var, Expression _value) {
		this((Location)_var,_value);
	}

	public UpdateRule(Function _var, Expression _value, List<IdExpression> args) {
		this(new FunctionApplication(_var,args),_value);
	}
	
	public UpdateRule(LogicalVariable lvar, Expression exp) {
		this((Location)lvar,exp);
	}

	private UpdateRule(Location _var, Expression _value) {
		assert _var != null;
		this.location = _var;
		assert _value != null;
		this.value = _value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forAssignment(this);
	}

	/**
	 * return the location to be updated.
	 * 
	 * @return the var
	 */
	public Location getVar() {
		return this.location;
	}

	/**
	 * return the location arguments - null if it is a variable update.
	 * 
	 * @return the arg
	 */
	public List<IdExpression> getArg() {
		if (this.location instanceof FunctionApplication) {
			return ((FunctionApplication)(this.location)).getArgs();
		}
		return null;
	}

	/**
	 * return the expression to be assigned.
	 * 
	 * @return the value
	 */
	public Expression getValue() {
		return this.value;
	}

}
