/*******************************************************************************
 * Copyright (c) 2015 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.specification.statement;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import tgtlib.definitions.expression.IdExpression;

/**
 * Case statement
 * 
 * @author Angelo Gargantini
 */

public class CaseStatement extends BasicRule {

	/** The statements. */
	protected Map<IdExpression, BasicRule> cases;
	protected IdExpression selector;
	protected BasicRule defaultRule;
	
	/**
	 * Instantiates a new case statement.
	 * @param idExp
	 */
	public CaseStatement(IdExpression idExp) {
		this.cases = new LinkedHashMap<>();
		selector = idExp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forCaseStatement(this);
	}

	/**
	 * Adds a new case.
	 * @param value 
	 * 
	 * @param stmt
	 *            the stmt
	 */
	public void addCase(IdExpression value, BasicRule stmt) {
		this.cases.put(value,stmt);
	}

	/**
	 * All statements.
	 * 
	 * @return the enumeration< basic rule>
	 */
	public Iterator<Entry<IdExpression, BasicRule>> allCases() {
		return this.cases.entrySet().iterator();
	}


	public void addDefault(BasicRule defaultRule) {
		this.defaultRule = defaultRule;
	}

	public IdExpression getSelector() {
		return selector;
	}

	public BasicRule getDefaultRule() {
		return defaultRule;
	}
}