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

/**
 * The Interface RuleVisitor.
 */
public interface RuleVisitor<T> {

	/**
	 * For if then else.
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the t
	 */
	public T forIfThenElse(ConditionalRule ite);

	/**
	 * For skip.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the t
	 */
	public T forSkip(Skip s);

	/**
	 * For assignment.
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the t
	 */
	public T forAssignment(UpdateRule a);

	/**
	 * For do statement.
	 * 
	 * @param d
	 *            the d
	 * 
	 * @return the t
	 */
	public T forDoStatement(DoStatement d);

	/**
	 * For macro call rule.
	 * 
	 * @param mcr
	 *            the mcr
	 * 
	 * @return the t
	 */
	public T forMacroCallRule(MacroCallRule mcr);

	/** for choose rule
	 * 
	 * @param chooseRule
	 * @return
	 */
	public T  forChooseRule(ChooseRule chooseRule);

	
	/** for case rule */
	
	public T forCaseStatement(CaseStatement caseStatement);
}
