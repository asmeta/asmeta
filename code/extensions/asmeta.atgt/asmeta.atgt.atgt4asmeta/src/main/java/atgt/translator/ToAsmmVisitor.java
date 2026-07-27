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
package atgt.translator;

import atgt.specification.ASMSpecification;
import atgt.specification.constraints.OneInputAssumption;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.RuleDeclarationVisitor;
import atgt.specification.statement.RuleVisitor;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.expression.ExpressionTranslator;
import tgtlib.specification.Axiom;

/**
 * transform to string the ASM spec.
 */
public class ToAsmmVisitor extends TranslatorVisitor {

	/**
	 * Instantiates a new to asmm visitor.
	 */
	public ToAsmmVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.translator.TranslatorVisitor#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public StringBuffer analyze(ASMSpecification sp) {

		String indent = "";

		StringBuffer result = new StringBuffer("ASM " + sp.name + "\n");

		// definitions
		result.append("definitions:\n");

		// axioms
		result.append(indent + "// Axioms \n");

		ExpressionTranslator toSal = new ExpressionToSALVisitor();
		for (Axiom ax : sp.getAxiom()){
			if (ax == OneInputAssumption.OIA) 
				result.append(indent + "// OIA assumption Axiom \n");
			else
				result.append(indent + "axiom " + ax.getName() + ":" + ax.getBody().accept(toSal) + "\n");
		}
		// rules
		result.append(indent + "// Rules \n");

		// RuleDeclaration translation
		indent += "\t";
		result.append(indent + "// Rules \n");
		for (RuleDeclaration r: sp.allRules()) {
			result.append("rule "
					+ r.getName()
					+ " : "
					+ r.getBody().accept(
//							new StatementToSPINVisitor(indent)));
							new TempStatementToStringVisitor(indent)));
				}
		return result;
	}

}
// TEMP 
class TempStatementToStringVisitor implements RuleVisitor<String>, RuleDeclarationVisitor<String> {

	public TempStatementToStringVisitor(String indent) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String forRuleDeclaration(RuleDeclaration r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forIfThenElse(ConditionalRule ite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forSkip(Skip s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forAssignment(UpdateRule a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forDoStatement(DoStatement d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forMacroCallRule(MacroCallRule mcr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forChooseRule(ChooseRule chooseRule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forCaseStatement(CaseStatement caseStatement) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
