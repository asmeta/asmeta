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
package atgt.specification.expression;

import tgtlib.definitions.expression.PrimedIdExpression;

/**
 * The visitor for Asm expressions - for primed id, it must return undef (not primed id in ASM) -never used
 */
public abstract class AsmExpressionVisitor<T> implements tgtlib.definitions.expression.ExpressionVisitor<T>{

	
	@Override
	public final T forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		throw new RuntimeException("not implemented yet");
	}
}
