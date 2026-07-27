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
 * A generic constant.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class Constant extends AsmTerm {

	/**
	 * build a constant of name, type and initial value.
	 * 
	 * @param _name
	 *            the _name
	 * @param _type
	 *            the _type
	 * @param _value
	 *            the _value
	 */
	public Constant(IdExpression _name, Type _type, Expression _value) {
		super(_name, _type, _value);
	}

	/**
	 * A method for Visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	@Override
	public <T> T accept(LocationVisitorI<T> ask) {
		return ask.forConstant(this);
	}

}
