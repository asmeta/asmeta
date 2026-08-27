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

// TODO: Auto-generated Javadoc
/**
 * The Interface RuleDeclarationVisitor.
 */
public interface RuleDeclarationVisitor<T> {

	/**
	 * For rule declaration.
	 * 
	 * @param r
	 *            the r
	 * 
	 * @return the t
	 */
	T forRuleDeclaration(RuleDeclaration r);

}
