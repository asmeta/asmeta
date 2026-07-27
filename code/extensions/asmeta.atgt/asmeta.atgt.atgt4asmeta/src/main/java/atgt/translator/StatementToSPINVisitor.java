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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import atgt.specification.expression.IdReplacerVisitor;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.CaseStatement;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.RuleDeclarationVisitor;
import atgt.specification.statement.RuleExprReplacerVisitor;
import atgt.specification.statement.RuleVisitor;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

/**
 * The Class StatementToSPINVisitor.
 */
public class StatementToSPINVisitor implements RuleVisitor<String>, RuleDeclarationVisitor<String> {

	private static final boolean WRITE_ONLY_IF_CHANGED = false;

	public static boolean SHUFFLE_CHOOSE = false;

	/** The indent. */
	private String indent;

	/**
	 * Instantiates a new statement to spin visitor.
	 */
	public StatementToSPINVisitor() {
		this("\t");
	}

	/**
	 * Instantiates a new statement to spin visitor.
	 * 
	 * @param _indent
	 *            the _indent
	 */
	public StatementToSPINVisitor(String _indent) {
		this(_indent, Collections.EMPTY_MAP);
	}

	Map<RuleDeclaration, String> ruleNames;

	public StatementToSPINVisitor(String _indent, Map<RuleDeclaration, String> ruleNames) {
		this.ruleNames = ruleNames;
		this.indent = _indent;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.statement.RuleVisitor#forIfThenElse(atgt.specification
	 * .statement.ConditionalRule)
	 */
	@Override
	public String forIfThenElse(ConditionalRule ite) {
		StringBuffer result = new StringBuffer();
		// 0
		// add(result, indent + "/* conditional rule */" + "\n";
		add(result, "if");
		add(result, ":: " + ite.getGuard().accept(ExpressionToSPINVisitor.SINGLETON) + " ->");
		this.indent += "\t";// 1
		add(result, ite.getThenPart().accept(this));
		// remove one \t
		this.indent = this.indent.substring(1);// 0
		// add(result, indent +" \n";
		add(result, ":: else ->");
		this.indent += "\t";// 1
		BasicRule elsePart = ite.getElsePart();
		if (elsePart != null)
			add(result, elsePart.accept(this));
		else
			add(result, Skip.SKIP.accept(this));
		// add(result, indent + " )\n";
		this.indent = this.indent.substring(1);// 0
		add(result, "fi;");

		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forSkip(atgt.specification.
	 * statement .Skip)
	 */
	@Override
	public String forSkip(Skip s) {
		return this.indent + "skip\n";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.statement.RuleVisitor#forAssignment(atgt.specification
	 * .statement.UpdateRule)
	 */
	@Override
	public String forAssignment(UpdateRule a) {
		StringBuffer result = new StringBuffer();
		// left side
		Location var = a.getVar();		
		String leftSide = var.accept(LocationLeftUseToSPINVisitor.instance).toString();
		assert ! leftSide.contains("$") : var.getClass();
		// add the arguments
		String leftSideArgs = leftSide;
		if (a.getArg() != null){
			leftSideArgs += "[";
			assert a.getArg().size() == 1;
			leftSideArgs += a.getArg().get(0).accept(ExpressionToSPINVisitor.SINGLETON);
			leftSideArgs += "]";
		}
		// RIGHT SIDE
		Expression value = a.getValue();
		assert (value != null) : a.getVar();
		StringBuffer accept = value.accept(ExpressionToSPINVisitor.SINGLETON);
		assert (accept != null);
		String rightSide = accept.toString();
		// assign the result
		add(result,  leftSideArgs + " = " + rightSide + ";\n"); 
		// print the new value
		String printf;
		if (a.getArg() == null) {
			Type type = var.getType();
			if (type instanceof EnumType && value instanceof EnumConst){
				// if its is an enum, print the value enumerative
				printf = "printf(\"_Update_value " + leftSide + " " + rightSide + "\\n\");\n";
			} else if (type instanceof BoolType){
				// if it is a bool type, write true false depending on the right value
				// printf("You are married: %s",(married)?"true":"false");
				// IT DOES NOT WORK - SPin accept -> but not strings . ONLY CHARS
				printf = this.indent + "printf(\"_Update_value " + leftSide+ " %c.\\n\",((" + rightSide + ") -> 'T':'F'));\n";
			} else {
				// WRITE THE VALUE AS IT IS (as integer)
				printf =  "/* printing value of "+ leftSide + " as " + value.getClass().getSimpleName() + "*/\n";					
				printf += "printf(\"_Update_value " + leftSide+ " %d.\\n\"," + rightSide + ");\n";
			}
		} else {
			assert a.getArg().size() == 1;
			// it is a function
			printf = "printf(\"_Update_value " + leftSide
					+ "[%d]" + "  %d.\\n\"," + a.getArg().get(0).accept(ExpressionToSPINVisitor.SINGLETON) + ","
					+ var + Variable.primeSuffix + "[" + a.getArg().get(0).accept(ExpressionToSPINVisitor.SINGLETON)
					+ "]" + ");\n";
		}
		if (!WRITE_ONLY_IF_CHANGED) {
			// AD IT IS
			add(result,printf);
		} else {
			/*
			 * scrive solo se cambia attenzione: COMPLICA IL MODELLO !!!!
			 */

			add(result,  "if\n");

			// condizione
			if (a.getArg() == null) {
				add(result,  ":: " + var + "!=" + var
						+ Variable.primeSuffix + "->\n");
				// scrivi in output*/
				add(result, printf);
			} else {
				add(result,  ":: " + var + "[" + a.getArg()
						+ "]" + "!=" + var + Variable.primeSuffix + "["
						+ a.getArg() + "]" + "->\n");
				// scrivi in output*/
				add(result, printf);
			}

			// ELSE (in Spin this is mandatory
			add(result,  ":: else -> skip;\n");
			// end if
			add(result,  "fi;\n");
		}
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forDoStatement(atgt.
	 * specification .statement.DoStatement)
	 */
	@Override
	public String forDoStatement(DoStatement d) {
		StringBuffer result = new StringBuffer();
		for (Enumeration<BasicRule> e = d.allStatements(); e.hasMoreElements();)
			add(result, (e.nextElement()).accept(this));

		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration
	 * (atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	public String forRuleDeclaration(RuleDeclaration r) {
		// NEVER USED?????
		throw new RuntimeException();
	}

	/**
	 * add the result with the indentation and begine new line
	 * 
	 * @param result
	 * @param string
	 */
	private void add(StringBuffer result, String string) {
		// replace every new line inside
		string = string.replaceAll("\n", "\n" + this.indent);
		result.append(this.indent).append(string).append("\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forMacroCallRule(atgt.
	 * specification .statement.MacroCallRule)
	 */
	@Override
	public String forMacroCallRule(MacroCallRule mcr) {
		// call the inline
		String result =  this.indent + "/* called rule " + mcr.getRuleDeclaration().getName() + "*/\n";
		String ruleSpinName = ruleNames.get(mcr.getRuleDeclaration());
		assert ruleSpinName != null : " rule " + mcr.getRuleDeclaration().getName() + " not found";
		result+= this.indent + ruleSpinName + "(";
		List<IdExpression> paramters = mcr.getParamters();
		for (int i = 0; i < paramters.size(); i++) {
			IdExpression p = paramters.get(i);
			if (i>0)result+=',';
			result+=p.accept(ExpressionToSPINVisitor.SINGLETON);
		}
		result+=");";
		return result;
	}

	@Override
	public String forChooseRule(ChooseRule chooseRule) {
		StringBuffer result = new StringBuffer();
		this.indent += "\t";// 1
		add(result, "if\n");
		List<Expression> terms = new ArrayList<Expression>(chooseRule.getTerms());
		if (SHUFFLE_CHOOSE)
			Collections.shuffle(terms);
		for (Expression t : terms) {
			add(result, "/* choose value for " + chooseRule.getVar() + " = " + t.toString() + " condition "
					+ (chooseRule.getCondition() == null ? " true" : chooseRule.getCondition()) + " */\n");
			// build new rule
			RuleExprReplacerVisitor rep = new RuleExprReplacerVisitor(chooseRule.getVar(), (IdExpression) t);
			// compute the condition
			String guard;
			if (chooseRule.getCondition() != BoolType.TRUE_CONST) {
				Expression newCond = chooseRule.getCondition()
						.accept(new IdReplacerVisitor(chooseRule.getVar(), (IdExpression) t));
				guard = newCond.accept(ExpressionToSPINVisitor.SINGLETON) + "->";
			} else {
				// guard "true"
				guard = "";
			}
			// convert the rule and translate
			BasicRule newBody = chooseRule.getDoRule().accept(rep);
			add(result, ":: " + guard + newBody.accept(this));
		}
		add(result, "fi;\n");
		// remove one \t
		this.indent = this.indent.substring(1);// 0
		return result.toString();
	}

	@Override
	public String forCaseStatement(CaseStatement caseStatement) {
		Iterator<Entry<IdExpression, BasicRule>> cases = caseStatement.allCases();
		//
		StringBuffer result = new StringBuffer();
		// 0
		add(result, "/* case rule  */" + "\n");
		add(result, "if");
		while (cases.hasNext()) {
			Entry<IdExpression, BasicRule> next = cases.next();
			// build the conditional
			BinaryExpression guard = BinaryExpression.mkBinExpr(caseStatement.getSelector(), Operator.EQ,
					next.getKey());
			//
			add(result, ":: " + guard.accept(ExpressionToSPINVisitor.SINGLETON) + " ->");
			this.indent += "\t";// 1
			add(result, next.getValue().accept(this));
			// remove one \t
			this.indent = this.indent.substring(1);// 0
		}
		//
		// add(result, indent +" \n";
		add(result, ":: else ->");
		this.indent += "\t";// 1
		if (caseStatement.getDefaultRule() != null)
			add(result, caseStatement.getDefaultRule().accept(this));
		else
			add(result, Skip.SKIP.accept(this));
		// add(result, indent + " )\n";
		this.indent = this.indent.substring(1);// 0
		add(result, "fi;");
		return result.toString();
	}
}