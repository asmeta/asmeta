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
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.OrExpression;

/**
 * Complete Rule Coverage. Esegue una scansione della specifica e genera una
 * collezione di casi di test.
 * 
 * For every rule, the guard is true and an update is not trivial tc ={guard \/ (
 * up_1 \/ ... \/ up_n) up_i = l' != l
 * 
 * @author Sax Rinzivillo, Angelo Gargantini, Sergio Galati
 */

public class CompleteRuleVisitor extends BasicRuleVisitor {

	/**
	 * Instantiates a new complete rule visitor.
	 */
	public CompleteRuleVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Complete Rule Coverage";
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. Per
	 * un assegnamento del tipo
	 * 
	 * <PRE>
	 * var := value
	 * </PRE>
	 * 
	 * verr� ritornata la condizione
	 * 
	 * <PRE>
	 * pre != value
	 * </PRE>
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forAssignment(UpdateRule a) {
		List<NamedTerm> list = new Vector<NamedTerm>();
		Expression id;
		if (a.getArg() == null) {
			id = ((Variable)a.getVar()).getIdExpression();
		} else {					
			Function func = (Function)a.getVar();
			assert func.getDomain() == func.getIdExpression().getType();
			id = new FunctionTerm(func.getIdExpression(),func.getCodomain(),a.getArg());
		}
		list.add(new NamedTerm("", new NotEqualsExpression(id, a.getValue())));
		return list;
	}


	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata una lista contenente due <I>Test Predicate</I>
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forIfThenElse(ConditionalRule ite) {
		Vector<NamedTerm> list = new Vector<NamedTerm>();

		List<NamedTerm> listThen = ite.getThenPart().accept(this);

		// WARNING LIST MUST BE >= 2
		// otherwise we get update coverage
		if (listThen.size() >= 2) {
			Expression e1 = listThen.get(0).getCondition();
			for (int i = 1; i < listThen.size(); i++)
				e1 = new OrExpression(e1, listThen.get(i).getCondition());
			list.add(new NamedTerm("T", new AndExpression(ite.getGuard(),
					e1)));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getAbbrName()
	 */
	@Override
	public String getAbbrName() {
		return "CR";
	}

}
