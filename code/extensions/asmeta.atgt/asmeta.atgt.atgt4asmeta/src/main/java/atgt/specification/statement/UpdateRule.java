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

import java.util.List;

import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Expression;

/**
 * UpdateRule statement: var:=val or
 *  loc(a1,1n) := val
 * 
 * @author Sax Rinzivillo, Angelo Gargantini, Sergio Galati
 */

public class UpdateRule extends BasicRule {

	/** The location_fun_name. */
	protected Location location;

	/** The location_args. */
	protected List<? extends Expression> location_args;

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
	public UpdateRule(Variable _var, Expression _value) {
		this((Location)_var,_value,null);
	}

	/**
	 * Instantiates a new update rule.
	 * 
	 * @param _var
	 *            the _var
	 * @param _value
	 *            the _value
	 * @param _arg
	 *            the _arg
	 */
	public UpdateRule(Function _var, Expression _value, List<? extends Expression> arg) {
		this((Location)_var,_value,arg);
	}
	
	public UpdateRule(LogicalVariable lvar, Expression exp) {
		this((Location)lvar,exp,null);
	}

	private UpdateRule(Location _var, Expression _value, List<? extends Expression> arg) {
		this.location = _var;
		assert _var != null;
		this.value = _value;
		assert value != null;
		this.location_args = arg;
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
	public List<? extends Expression> getArg() {
		return this.location_args;
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
