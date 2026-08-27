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

import tgtlib.definitions.expression.IdExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class MacroCallRule.
 * 
 * @author garganti
 */
public class MacroCallRule extends BasicRule {

	/** The rd. */
	private RuleDeclaration rd;
	/* the actual paramtyers can be empty*/
	private List<IdExpression> parameters;

	/**
	 * Gets the rule declaration.
	 * 
	 * @return the rule declaration
	 */
	public RuleDeclaration getRuleDeclaration() {
		return this.rd;
	}

	/**
	 * Creates a new instance of MacroCallRule.
	 * 
	 * @param r1
	 *            the r1
	 */
	public MacroCallRule(RuleDeclaration r1, List<IdExpression> paramters) {
		this.rd = r1;
		this.parameters = paramters; 
		assert paramters.size() == r1.getParamters().size() : r1.name + " has "+ r1.getParamters().size() + " paramters and it is called with " + paramters.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.BasicRule#accept(atgt.specification.statement.RuleVisitor)
	 */
	@Override
	public <T> T accept(RuleVisitor<T> ask) {
		return ask.forMacroCallRule(this);
	}

	public List<IdExpression> getParamters(){
		return java.util.Collections.unmodifiableList(parameters);
	}
}