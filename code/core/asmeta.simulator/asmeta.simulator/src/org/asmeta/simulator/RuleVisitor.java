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

/**
 * abstract visitor of the rules.
 * 
 * @author garganti
 * 
 * @param <T>
 */
public abstract class RuleVisitor<T> extends ReflectiveVisitor<T> implements IRuleVisitor<T>{

	/**
	 * call the reflective visitor
	 * 
	 * @param rule
	 * @return
	 */
	@Override
	public final T visit(Rule rule) {
		return visit((Object) rule);
	}

	@Override
	public abstract T visit(SkipRule rule);

	@Override
	public abstract T visit(UpdateRule rule);

	@Override
	public abstract T visit(TermAsRule rule);

	@Override
	abstract public T visit(BlockRule block);

	@Override
	abstract public T visit(SeqRule seq);

	@Override
	abstract public T visit(ConditionalRule rule);

	@Override
	abstract public T visit(ExtendRule rule);

	@Override
	abstract public T visit(LetRule rule);

	@Override
	abstract public T visit(ChooseRule rule);

	@Override
	abstract public T visit(ForallRule rule);

	@Override
	abstract public T visit(MacroCallRule rule) throws Exception;

	@Override
	abstract public T visit(CaseRule rule);
}
