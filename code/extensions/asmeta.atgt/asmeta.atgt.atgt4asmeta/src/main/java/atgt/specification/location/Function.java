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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

/**
 * A generic function instead of a variable: TODO this should not be a Term, since it makes no sense use a function as a term
 * for application see FunctionApplication (Function + arguments)
 * 
 * @author Sergio
 */

public class Function extends Location {

	/** The Constant primeSuffix. */
	public static final String primeSuffix = "___P";

	/** The codomain. */
	protected Type codomain;
	//
	/** The init values. */
	protected Hashtable<String, Expression> initValues;

	/**
	 * a function with domain e codomain and an initial value for all.
	 * 
	 * @param _name
	 *            the _name
	 * @param dom
	 *            the domain of the function (a function f : A-> B, Ais the domain
	 *            howver, the value
	 * @param _codom
	 *            the _codom
	 * @param _value
	 *            the _value
	 */
	public Function(IdExpression _name, Type dom, Type _codom, Expression _value) {
		super(_name, dom, _value);
		assert ! name.getIdString().contains("(");
		this.initValues = new Hashtable<String, Expression>();
		this.codomain = _codom;
		this.isMonitored();
		if (_value != null) {
			// if _value is not null I set all values of domain to _value
			if (dom instanceof EnumType) {
				for (EnumConst e : ((ElementsType) dom).allElements()) {
					String val = e.toString();
					this.initValues.put(val, _value);
				}
			}
			if (dom instanceof BoundType) {
				BoundType bt = (BoundType) dom;
				int start = bt.getLow();
				int end = bt.getUp();
				Integer btDelta = bt.getDelta();
				int delta = btDelta!=null? btDelta: 1;
				while (start < end) {
					String _start = Integer.toString(start);
					// Integer _start = new Integer(start));
					this.initValues.put(_start, _value);
					start = start + delta;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.Location#accept(atgt.specification.location.
	 * LocationVisitorI)
	 */
	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		return ask.forFunction(this);
	}

	/**
	 * Gets the domain.
	 * 
	 * @return the domain
	 */
	public Type getDomain() {
		return super.getType();
	}

	/**
	 * Gets the codomain.
	 * 
	 * @return the codomain
	 */
	public Type getCodomain() {
		return this.codomain;
	}

	/**
	 * Set all law or some value.
	 * 
	 * @param _law
	 *            the _law
	 */
	public void setInitialStates(String _law) {
		// Controlled = true;
		// Sto ipotizzando che _value sia del tipo:
		// k1:= valore1; k2 := valore2 ecc...
		StringTokenizer valori = new StringTokenizer(_law, "|");

		// String lawpart = valori.nextToken();
		for (; valori.hasMoreTokens();) {
			StringTokenizer couple = new StringTokenizer(valori.nextToken(), ":=");
			String key = couple.nextToken();
			String newvalue = couple.nextToken();
			if (this.initValues.containsKey(key))
				this.initValues.remove(key);
			this.initValues.put(key, new IdExpressionCreator().createIdExpression(newvalue, null));
		}
	}

	/**
	 * Gets the initial value.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the initial value
	 */
	public Expression getInitialValue(String key) {
		return this.initValues.get(key);
	}

	/**
	 * Gets the initial values.
	 * 
	 * @return the initial values
	 */
	public Hashtable<String, Expression> getInitialValues() {
		return this.initValues;
	}

	/**
	 * Gets the string law.
	 * 
	 * @return the string law
	 */
	public String getStringLaw() {
		String sl = "";

		for (Enumeration e = this.initValues.keys(); e.hasMoreElements();) {
			String key = (String) e.nextElement();
			sl += key + ":=" + this.initValues.get(key) + " ";
		}
		return sl;
	}

	/**
	 * Variable name of primed variable.
	 * 
	 * @return the primed name
	 */

	public String getPrimedName() {
		return this.name + primeSuffix;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Function) {
			Function func = (Function) obj;
			return getIdExpression().equals(func.getIdExpression()) && getDomain().equals(func.getDomain());			
		}
		return false;
	}
}
