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
package atgt.coverage;

import java.util.List;
import java.util.Vector;

import atgt.specification.location.Function;
import atgt.specification.location.Variable;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;

/**
 * RuleDeclaration Guard Coverage. Esegue una scansione della specifica e genera
 * una collezione di casi di test.
 * 
 * DEFINITION: for every rule there exists a test in which the rule does not
 * fire and the value v of some location that would be updated by the rule to
 * v_r is different to the value it would be updated to (if the rule fired, the
 * value of some location would be different)
 * 
 * @author Sax Rinzivillo, Sergio Galati
 */

public class RuleGuardVisitor extends CompleteRuleVisitor {
	
	
	static IdExpressionCreator idExpr = new IdExpressionCreator();

	/**
	 * Instantiates a new rule guard visitor.
	 */
	public RuleGuardVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.CompleteRuleVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Rule Guard Coverage";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.CompleteRuleVisitor#getAbbrName()
	 */
	@Override
	public String getAbbrName() {
		return "RG";
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. Per
	 * un assegnamento del tipo
	 * 
	 * <PRE>
	 * var := value
	 * </PRE>
	 * 
	 * verra' ritornata la condizione
	 * 
	 * <PRE>
	 * varP != value
	 * </PRE>
	 * non sono sicuro sia necessario 
	 * @param a
	 *            the a
	 * 
	 * @return the list<atgt.coverage. named expression>
	 */
	@Override
	public List<NamedTerm> forAssignment(UpdateRule a) {
		Vector<NamedTerm> list = new Vector<NamedTerm>();
		Expression var;
		if (a.getArg() == null) {
			var = ((Variable) a.getVar()).getIdExpression();
		} else {
			Function f = (Function)a.getVar();
			var = new FunctionTerm(f.getIdExpression(),f.getDomain(),a.getArg());
		}
		Expression notEq = new NotEqualsExpression(var, a.getValue());
		list.add(new NamedTerm("RG", notEq));
		return list;
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata una lista contenente due <I>Test Predicate</I>
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list<atgt.coverage. named expression>
	 */
	@Override
	public List<NamedTerm> forIfThenElse(ConditionalRule ite) {
		return distributeOverConditional(ite,false);
	}

}
