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
 * interface visitor of the rules.
 * 
 * @author garganti
 * 
 * @param <T>
 */
public interface IRuleVisitor<T> {

	public T visit(Rule rule);

	public T visit(SkipRule rule);

	public T visit(UpdateRule rule);

	public T visit(TermAsRule rule);

	public T visit(BlockRule block);

	public T visit(SeqRule seq);

	public T visit(ConditionalRule rule);

	public T visit(ExtendRule rule);

	public T visit(LetRule rule);

	public T visit(ChooseRule rule);

	public T visit(ForallRule rule);

	public T visit(MacroCallRule rule) throws Exception;

	public T visit(CaseRule rule);
}
