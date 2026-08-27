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

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import tgtlib.definitions.NamedTerm;

/**
 * Do statement. Handles a list of ASM statements in parallel
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class DoStatement extends BasicRule {

	/** The statements. */
	protected Vector<BasicRule> statements;

	/**
	 * Instantiates a new do statement.
	 */
	public DoStatement() {
		this.statements = new Vector<BasicRule>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forDoStatement(this);
	}

	/**
	 * Adds the statement.
	 * 
	 * @param stmt
	 *            the stmt
	 */
	public void addStatement(BasicRule stmt) {
		this.statements.add(stmt);
	}

	/**
	 * All statements.
	 * 
	 * @return the enumeration< basic rule>
	 */
	public Enumeration<BasicRule> allStatements() {
		return this.statements.elements();
	}

	/** calls the visitor for every rule in the do statement and returns the results
	 * 
	 * @param d
	 * @param visitor
	 * @return
	 */
	public List<NamedTerm> addResults(RuleVisitor<List<NamedTerm>> visitor) {
		Vector<NamedTerm> list = new Vector<NamedTerm>();
		int i = 1;
		for (Enumeration<BasicRule> e = this.allStatements(); e.hasMoreElements();) {
			BasicRule r = e.nextElement();
			List<NamedTerm> l = r.accept(visitor);
			// add the name
			for (NamedTerm ne : l) {
				ne.setName(ne.getName() + Integer.valueOf(i));
			}
			list.addAll(l);
			i++;
		}
		return list;
	}
}
