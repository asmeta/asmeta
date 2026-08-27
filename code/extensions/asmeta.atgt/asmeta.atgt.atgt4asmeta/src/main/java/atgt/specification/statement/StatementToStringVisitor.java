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

import tgtlib.definitions.expression.Expression;

/**
 * Fornisca uan rappresentazione di tipo String per gli elementi del data type.
 * 
 * TO BE COMPLEYTED ...
 * 
 * @author Angelo Gargantini
 */
public class StatementToStringVisitor implements RuleVisitor<String>,
		RuleDeclarationVisitor<String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forSkip(atgt.specification.statement.Skip)
	 */
	@Override
	public String forSkip(Skip s) {
		return "Skip";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forAssignment(atgt.specification.statement.UpdateRule)
	 */
	@Override
	public String forAssignment(UpdateRule a) {
		if (a.getArg() == null)
			return a.getVar() + " := " + a.getValue().toString();
		else{
			assert a.getArg().size() ==1;
			return a.getVar() + "[" + a.getArg().get(0) + "]" + " := "
					+ a.getValue().toString();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forDoStatement(atgt.specification.statement.DoStatement)
	 */
	@Override
	public String forDoStatement(DoStatement d) {
		StringBuffer str = new StringBuffer("par");
		return str.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forIfThenElse(atgt.specification.statement.ConditionalRule)
	 */
	@Override
	public String forIfThenElse(ConditionalRule ite) {
		Expression guard = ite.getGuard();
		assert guard != null;
		BasicRule thenPart = ite.getThenPart();
		assert thenPart != null;
		BasicRule elsePart = ite.getElsePart();
		return "if " + guard + " then " + thenPart.accept(this) + (elsePart!=null?(" else " + elsePart.accept(this)):"");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration(atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	public String forRuleDeclaration(RuleDeclaration r) {
		return "Rule " + r.getName() + " = " + r.getBody().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forMacroCallRule(atgt.specification.statement.MacroCallRule)
	 */
	@Override
	public String forMacroCallRule(MacroCallRule mcr) {

		return "Macro " + mcr.getRuleDeclaration().getName();

	}

	@Override
	public String forChooseRule(ChooseRule chooseRule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public String forCaseStatement(CaseStatement caseStatement) {
		throw new RuntimeException("not implemented yet");
	}
}
