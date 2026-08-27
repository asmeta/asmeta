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
package extgt.coverage.combinatorial;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;

/**
 * The Class MonitoredData. represents monitored data
 * 
 * list of monitored variable (of EnumType) with their domains
 */
public class MonitoredData {

	/** all the variables defined by enum. */
	protected Vector<TypedInitExpression> enumVars = new Vector<TypedInitExpression>();

	/**
	 * the variable must be of type EnumType.
	 * 
	 * @param o
	 *            the o
	 * 
	 * @return true, if adds the
	 */
	public boolean add(TypedInitExpression o) {
		assert o.getType() instanceof ElementsType;
		return enumVars.add(o);
	}

	/**
	 * return all the variables with type enumType it cannot be modified (sorted
	 * or added members).
	 * 
	 * @return the vars
	 */
	public List<TypedInitExpression> getVars() {
		return Collections.unmodifiableList(enumVars);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (TypedInitExpression item : enumVars) {
			result.append(item.getName());
			result.append("=[");
			for (Iterator<EnumConst> e = ((ElementsType) item.getType())
					.allElements().iterator();;) {
				result.append(e.next().toString());
				if (e.hasNext())
					result.append(", ");
				else
					break;
			}
			result.append("]");

		}
		return result.toString();
	}

}
