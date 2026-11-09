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
package tgtlib.specification.location;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import atgt.translator.monVarSimulationVisitor;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.location.Location.VarKind;

/**
 * A generic function instead of a variable: 
 * this is not a term, but a function with domain and codomain.
 * 
 * @author Sergio
 */

public class Function {

	/** The Constant primeSuffix. */
	public static final String primeSuffix = "___P";

	/** The codomain. */
	protected Type codomain;
	protected Type domain;
	//
	/** The init values.  location -> expression */
	// assuming arity == 1
	protected Hashtable<IdExpression, Expression> initValues;

	// name of the function
	private IdExpression name;
	
	
	/** The function kind. */
	protected VarKind funKind;

	/**
	 * a function with domain e codomain and an initial value for all.
	 * 
	 * @param _name  the _name
	 * @param dom    the domain of the function (a function f : A-> B, Ais the
	 *               domain howver, the value
	 * @param _codom the _codom
	 * @param _value the _value
	 */
	public Function(IdExpression _name, Type dom, Type _codom, Expression _value) {
		assert _name != null;
		assert !_name.getIdString().contains("(");
		this.name = _name;
		this.codomain = _codom;
		this.domain = dom;
		setInitialValues(_value);
	}

	private void setInitialValues(Expression _value) {
		this.initValues = new Hashtable<IdExpression, Expression>();
		if (_value != null) {
			// if _value is not null I set all values of domain to _value
			if (domain instanceof EnumType) {
				for (EnumConst e : ((ElementsType) domain).allElements()) {
					this.initValues.put(e, _value);
				}
			}
			if (domain instanceof BoundType) {
				BoundType bt = (BoundType) domain;
				int start = bt.getLow();
				int end = bt.getUp();
				Integer btDelta = bt.getDelta();
				int delta = btDelta != null ? btDelta : 1;
				while (start < end) {
					this.initValues.put(new NumericLiteral(start), _value);
					start = start + delta;
				}
			}
		}
	}

	/**
	 * Gets the domain.
	 * 
	 * @return the domain
	 */
	public Type getDomain() {
		return this.domain;
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
	 * @param _law the _law
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
			this.initValues.put( new IdExpressionCreator().createIdExpression(key, null), new IdExpressionCreator().createIdExpression(newvalue, null));
		}
	}

	/**
	 * Gets the initial value.
	 * 
	 * @param key the key
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
	public Hashtable<IdExpression, Expression> getInitialValues() {
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

	public IdExpression getIdExpression() {
		return name;
	}

	// returns the initial value of location. If it is undefined returns UndefExrpession
	public Expression getValuesExpression() {
		// TODO
		return new CaseExpression(null) {
			@Override
			public Map<IdExpression, Expression> getCases() {
				return initValues;
			}
		};
	}

	public void setControlled() {
		funKind = VarKind.CONTROLLED;
	}

	public void setMonitored() {
		funKind = VarKind.MONITORED;
	}

	public VarKind getVarKind() {
		return funKind;
	}

	public boolean isControlled() {
		return funKind == VarKind.CONTROLLED;
	}
	public boolean isMonitored() {
		return funKind == VarKind.MONITORED;
	}

	public String getName() {
		return name.getIdString();
	}

	public <S> S accept(LocationVisitorI<S> ask) {
		return ask.forFunction(this);
	}

	// not sure if this is correct, but it seems that the codomain is the type of the function
	public Type getType() {
		return codomain;
	}

	public void setValue(Expression initExpr) {
		setInitialValues(initExpr);
	}

	public Expression getValue() {
		throw new UnsupportedOperationException("Function.getValue() is not implemented yet");
	}

}
