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
package atgt.specification;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.Variable;
import atgt.specification.statement.BasicRule;
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
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.SpecificationAnalyzer;

// TODO: Auto-generated Javadoc
/**
 * DA ELIMINARE ??.
 */
public class FlatteningVisitor implements
		SpecificationAnalyzer<ASMSpecification,ASMSpecification>, RuleVisitor<List<BasicRule>>,
		RuleDeclarationVisitor<List<RuleDeclaration>> {

	/** The guard. */
	protected Expression guard;

	/**
	 * Instantiates a new flattening visitor.
	 * 
	 * @param _guard
	 *            the _guard
	 */
	public FlatteningVisitor(Expression _guard) {
		this.guard = _guard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.SpecificationAnalyzer#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public ASMSpecification analyze(ASMSpecification SP) {
		// Vector list = new Vector();
		ASMSpecification newSP = new ASMSpecification();

		// Copy all type in the new specification.
		for (Type t : SP.allTypes())
			newSP.addType(t);

		// Copy all constants
		for (Constant c : SP.allConstants())
			newSP.addConstant(c);

		// Copy all variables
		for (Enumeration e = SP.allVariables(); e.hasMoreElements();)
			newSP.addVariable((Variable) e.nextElement());

		// Copy all Functions
		for (Enumeration e = SP.allFunction(); e.hasMoreElements();)
			newSP.addFunction((Function) e.nextElement());

		// Add all flatted rules
		for (RuleDeclaration r : SP.allRules()) {
			List<RuleDeclaration> list = r.accept(this);
			for (RuleDeclaration r_flat : list)
				newSP.addRule(r_flat);
		}
		return newSP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forSkip(atgt.specification.statement.Skip)
	 */
	@Override
	public List<BasicRule> forSkip(Skip s) {
		return new Vector<BasicRule>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forAssignment(atgt.specification.statement.UpdateRule)
	 */
	@Override
	public List<BasicRule> forAssignment(UpdateRule a) {
		Vector<BasicRule> list = new Vector<BasicRule>();
		list.add(new ConditionalRule(this.guard, a));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forDoStatement(atgt.specification.statement.DoStatement)
	 */
	@Override
	public List<BasicRule> forDoStatement(DoStatement d) {
		Vector<BasicRule> list = new Vector<BasicRule>();
		DoStatement doStmt = new DoStatement();

		for (Enumeration e = d.allStatements(); e.hasMoreElements();) {
			BasicRule s = (BasicRule) e.nextElement();
			if (s instanceof ConditionalRule)
				list.addAll(s.accept(this));
			else
				doStmt.addStatement(s);
		}
		list.add(new ConditionalRule(this.guard, doStmt));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forIfThenElse(atgt.specification.statement.ConditionalRule)
	 */
	@Override
	public List<BasicRule> forIfThenElse(ConditionalRule ite) {
		Vector<BasicRule> list = new Vector<BasicRule>();
		Expression thenGuard;
		Expression elseGuard;

		if (this.guard == null) {
			thenGuard = ite.getGuard();
			elseGuard = NotExpression.createNotExpression(ite.getGuard());
		} else {
			thenGuard = new AndExpression(this.guard, ite.getGuard());
			elseGuard = new AndExpression(this.guard, NotExpression.createNotExpression(ite
					.getGuard()));
		}

		list.addAll(ite.getThenPart().accept(new FlatteningVisitor(thenGuard)));
		BasicRule s = ite.getElsePart();
		if (!(s instanceof Skip))
			list.addAll(ite.getElsePart().accept(
					new FlatteningVisitor(elseGuard)));

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleVisitor#forMacroCallRule(atgt.specification.statement.MacroCallRule)
	 */
	@Override
	public List<BasicRule> forMacroCallRule(MacroCallRule ite) {
		System.err.println("NOT IMPLEMENTED");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration(atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	public List<RuleDeclaration> forRuleDeclaration(RuleDeclaration r) {
		int counter = 0;
		String name = r.getName();
		ConditionalRule ite = (ConditionalRule) r.getBody();
		Vector<BasicRule> list = new Vector<BasicRule>();
		Vector<RuleDeclaration> result = new Vector<RuleDeclaration>();

		list.addAll(r.getBody().accept(new FlatteningVisitor(null)));
		for (BasicRule s : list) {
			result.add(new RuleDeclaration(name + "_" + counter++, s));
		}

		return result;
	}

	@Override
	public List<BasicRule> forChooseRule(ChooseRule chooseRule) {
		throw new RuntimeException("not implemented yet");
	}

	@Override
	public List<BasicRule> forCaseStatement(CaseStatement caseStatement) {
		throw new RuntimeException("not implemented yet");
	}

}
