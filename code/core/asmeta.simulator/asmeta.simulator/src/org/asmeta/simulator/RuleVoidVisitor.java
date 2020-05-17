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

import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

/** visits the rules doing nothing, except recursively calling the rules inside 
 * Use as adaptor
 * 
 * @author garganti
 *
 */
public class RuleVoidVisitor extends ReflectiveVisitor<Void> implements IRuleVisitor<Void>{
	
	@Override
	public Void visit(Rule rule) {
		return visit((Object) rule);
	}
	
	@Override
	public Void visit(SkipRule rule) {
		return null;
	}
	
	@Override
	public Void visit(UpdateRule rule) {
		return null;		
	}

	@Override
	public Void visit(TermAsRule rule) {
		return null;		
	}

	@Override
	public Void visit(BlockRule block) {
		List<Rule> rules = block.getRules();
		for (Rule rule : rules) {
			visit(rule);
		}
		return null;
	}

	@Override
	public Void visit(SeqRule seq) {
		List<Rule> rules = seq.getRules();
		for (Rule rule : rules) {
			visit(rule);
		}
		return null;
	}

	@Override
	public Void visit(ConditionalRule rule) {
		Rule rule1 = rule.getThenRule();
		Rule rule2 = rule.getElseRule();
		visit(rule1);
		if (rule2 != null) {
			visit(rule2);
		}
		return null;
	}
	
	@Override
	public Void visit(ExtendRule rule) {
		Rule rule1 = rule.getDoRule();
		visit(rule1);
		return null;
	}

	@Override
	public Void visit(LetRule rule) {
		Rule rule1 = rule.getInRule();
		visit(rule1);
		return null;
	}

	@Override
	public Void visit(ChooseRule rule) {
		Rule rule1 = rule.getDoRule();
		Rule rule2 = rule.getIfnone();
		visit(rule1);
		if (rule2 != null) {
			visit(rule2);
		}
		return null;
	}

	@Override
	public Void visit(ForallRule rule) {
		Rule rule1 = rule.getDoRule();
		visit(rule1);
		return null;
	}
	
	@Override
	public Void visit(MacroCallRule rule) {
		Rule rule1 = rule.getCalledMacro().getRuleBody();
		visit(rule1);		
		return null;
	}

	@Override
	public Void visit(CaseRule rule) {
		List<Rule> rules1 = rule.getCaseBranches();
		Rule rule2 = rule.getOtherwiseBranch();
		for (Rule rule1 : rules1) {
			visit(rule1);
		}
		if (rule2 != null) {
			visit(rule2);
		}
		return null;
	}

}
