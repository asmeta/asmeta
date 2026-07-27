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

/**
 * A generic rule.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class RuleDeclaration {// extends BasicRule{
	/** The name. */
	protected String name;

	/** The body. */
	protected BasicRule body;

	private List<IdExpression> parameters;

	/**
	 * Instantiates a new rule declaration.
	 * 
	 * @param _name
	 *            the _name
	 * @param _body
	 *            the _body
	 */
	public RuleDeclaration(String _name, BasicRule _body) {
		this(_name,_body,java.util.Collections.EMPTY_LIST);
	}

	
	/**
	 * Instantiates a new rule declaration.
	 *
	 * @param _name the name
	 * @param _body the body
	 * @param parameters the parameters
	 */
	public RuleDeclaration(String _name, BasicRule _body, List<IdExpression> parameters) {
		this.name = _name;
		this.body = _body;
		this.parameters = parameters;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the body.
	 * 
	 * @return the body
	 */
	public BasicRule getBody() {
		return this.body;
	}

	/**
	 * A method for Visitor pattern.
	 * 
	 * @param ask
	 *            the ask
	 * 
	 * @return the T
	 */
	public <T> T accept(RuleDeclarationVisitor<T> ask) {
		return ask.forRuleDeclaration(this);
	}

	/** returns the paramters
	 * @return 
	 * 
	 */
	
	public List<IdExpression> getParamters(){
		return java.util.Collections.unmodifiableList(parameters);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return accept(new StatementToStringVisitor());
	}
}
