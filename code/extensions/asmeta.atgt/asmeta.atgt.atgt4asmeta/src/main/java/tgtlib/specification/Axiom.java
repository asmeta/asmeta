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
package tgtlib.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tgtlib.definitions.expression.Expression;

/**
 * represents assumptions about the environment.
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class Axiom {

	/** The name. */
	private String name; // it can be null

	/** The body. */
	private Expression body;

	/**
	 * Instantiates a new axiom.
	 * 
	 * @param name
	 *            the name
	 * @param body
	 *            the body
	 */
	public Axiom(String name, Expression body) {
		this.name = name;
		this.body = body;
	}

	/**
	 * <p>
	 * The term representing the boolean-valued body expression of the
	 * constraint. This term can be interpreted in a state with respect to a
	 * variable assignment.
	 * </p>
	 * 
	
	 * @return Value of reference body. */
	public Expression getBody() {
		return body;
	}

	/**
	 * Gets the name.
	 * 
	
	 * @return the name */
	public String getName() {
		return name == null ? "" : name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	static public Collection<Expression> getExpressions(Collection<Axiom> aa){
		List<Expression> res = new ArrayList<>();
		for(Axiom a: aa){
			res.add(a.getBody());
		}
		return res;
	}		
	
}
