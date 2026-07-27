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
package atgt.coverage.evalc;

import static atgt.translator.ExpressionToC.EXPR_TO_C;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import atgt.specification.location.Location;
import atgt.specification.location.LogicalVariable;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.RuleExprReplacerVisitor;
import atgt.specification.statement.RuleVisitor;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.evalcoverage.TranslatorInputsToC;
import tgtlib.util.IterableEnumeration;

/**
 * The Class RuleToC. translates the rules in C
 */
public class RuleToC implements RuleVisitor<StringBuffer> {

	/** The Constant RULE_TO_C. */
	static final RuleToC RULE_TO_C = new RuleToC();

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forAssignment(atgt.specification.statement.UpdateRule)
	 */
	@Override
	public StringBuffer forAssignment(UpdateRule a) {
		// location
		Location var = a.getVar();
		assert ! (var instanceof LogicalVariable) : var.toString()  + " assigned to " + a.getValue();
		String location = var.toString() + TranslatorInputsToC.PRIMED_SUFFIX;
		// 
		if (a.getArg() != null && a.getArg().size() != 0){
			assert a.getArg().size() == 1;
			// add arguments
			location+= "[" + a.getArg().get(0).accept(EXPR_TO_C)+ "]";
		}
		return new StringBuffer(location).append(" = ").append(
				a.getValue().accept(EXPR_TO_C)).append(";\n");
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forDoStatement(atgt.specification.statement.DoStatement)
	 */
	@Override
	public StringBuffer forDoStatement(DoStatement d) {
		StringBuffer result = new StringBuffer("/* block statement */\n");
		for (BasicRule br : new IterableEnumeration<BasicRule>(d
				.allStatements())) {
			result.append(br.accept(this));
		}
		return result;
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forIfThenElse(atgt.specification.statement.ConditionalRule)
	 */
	@Override
	public StringBuffer forIfThenElse(ConditionalRule ite) {
		StringBuffer result = new StringBuffer("if (");
		result.append(ite.getGuard().accept(EXPR_TO_C)).append("){\n");
		result.append(ite.getThenPart().accept(this)).append("}");
		if (ite.getElsePart() != null) {
			// append also the else part
			result.append("else{\n");
			result.append(ite.getElsePart().accept(this)).append("}");
		}
		return result.append("\n");
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forMacroCallRule(atgt.specification.statement.MacroCallRule)
	 */
	@Override
	public StringBuffer forMacroCallRule(MacroCallRule mcr) {
		// replace a macro call with the translation of the body
		RuleDeclaration rs = mcr.getRuleDeclaration();
		StringBuffer result = new StringBuffer("\t/* for called rule "+ rs.getName() + "*/\n");
		BasicRule rule = rs.getBody();
		// in case of parameters
		if (mcr.getParamters().size() != 0){
			List<IdExpression> paramters = mcr.getParamters();
			List<IdExpression> logicalvas = rs.getParamters();
			assert paramters.size() == logicalvas.size();
			for (int i = 0; i < paramters.size(); i++) {
				IdExpression param = paramters.get(i);
				RuleExprReplacerVisitor rep = new RuleExprReplacerVisitor(logicalvas.get(i), param);
				//System.out.println("replacing in " + );
				rule = rule.accept(rep);
			}
		}
		result.append(rule.accept(this));
		return result;
	}

	// @Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forSkip(atgt.specification.statement.Skip)
	 */
	@Override
	public StringBuffer forSkip(Skip s) {
		return new StringBuffer("/* skip */\n");
	}

	@Override
	public StringBuffer forChooseRule(ChooseRule chooseRule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public StringBuffer forCaseStatement(CaseStatement caseStatement) {
		StringBuffer result = new StringBuffer("/* case statement */\n");
		Iterator<Entry<IdExpression, BasicRule>> cases = caseStatement.allCases();
		//
		while(cases.hasNext()) {
			Entry<IdExpression, BasicRule> next = cases.next();
			// build the conditional
			BinaryExpression guard = BinaryExpression.mkBinExpr(caseStatement.getSelector(), Operator.EQ, next.getKey());
			result.append("if (");
			result.append(guard.accept(EXPR_TO_C)).append("){\n");
			result.append(next.getValue().accept(this)).append("} else ");
		}
		//
		result.append("{\n");			
		if (caseStatement.getDefaultRule() != null){
			result.append(caseStatement.getDefaultRule().accept(this));			
		}
		result.append("}\n");
		//
		return result;
	}
}
