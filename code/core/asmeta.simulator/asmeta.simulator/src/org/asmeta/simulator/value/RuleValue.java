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
package org.asmeta.simulator.value;

import java.util.List;

import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.VariableTerm;

/**
 * A rule value.
 *
 */
public class RuleValue extends Value<RuleDeclaration> {
	
	private RuleDeclaration ruleDcl;
	private AgentValue agent;
	
	/**
	 * Creates a rule value.
	 * 
	 * @param dcl a rule declaration
	 */
	public RuleValue(RuleDeclaration dcl, AgentValue agent) {
		this.ruleDcl = dcl;
		this.agent = agent;
	}
	
	/**
	 * Gets the rule declaration.
	 * 
	 * @return the rule declaration
	 */
	public RuleDeclaration getRule() { 
		return ruleDcl;
	}
	
	public AgentValue getAgent() {
		return agent;
	}

	@Override
	public String toString(){
		return "rule " + toString(ruleDcl);
	}
	
	/**
	 * Gets a string representation of the rule declaration.
	 * 
	 * @param dcl a rule declaration
	 * @return a string
	 */
	private String toString(RuleDeclaration dcl) {
		StringBuilder s = new StringBuilder();
		s.append(dcl.getName());
		s.append("(");
		List<?> vars = dcl.getVariable();
		if (vars != null && vars.size() > 0) {
			VariableTerm var = (VariableTerm) vars.get(0);
			s.append(var.getDomain().getName());
			for (int i = 1; i < vars.size(); i++) {
				var = (VariableTerm) vars.get(i);
				s.append("," + var.getDomain().getName());
			}
		}
		s.append(")");
		return s.toString();
	}

	//PA: 14 giugno 10
	@Override
	public Value<RuleDeclaration> clone() {
		return null;
	}

	@Override
	public RuleDeclaration getValue() {
		return getRule();
	}

}
