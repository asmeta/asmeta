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

import asmeta.transitionrules.basictransitionrules.Rule;

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
}
