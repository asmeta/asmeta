/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator;

import java.util.Collection;
import java.util.Iterator;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * Assignment between variable and term.
 * 
 */
public class TermAssignment extends Assignment<Term> {

	/**
	 * Constructor.
	 * 
	 */
	public TermAssignment() {
		super();
	}

	/**
	 * Copy constructor.
	 * 
	 * @param assignment
	 *            assignment
	 */
	public TermAssignment(TermAssignment assignment) {
		super(assignment);
	}

	/**
	 * Assigns a list of terms to a list of variables.
	 * 
	 * @param variables
	 *            variable list
	 * @param arguments
	 *            term list
	 */
	public void put(Collection<VariableTerm> variables, Collection<Term> arguments) {
		Iterator<Term> argIt = arguments.iterator();
		for (VariableTerm var : variables) {
			Term value = argIt.next();
			put(var, value);
		}
	}
}
