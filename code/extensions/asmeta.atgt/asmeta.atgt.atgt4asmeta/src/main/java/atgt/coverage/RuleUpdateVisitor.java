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

import java.util.Vector;

import atgt.specification.location.Function;
import atgt.specification.location.Variable;
import atgt.specification.statement.BasicRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;

// TODO: Auto-generated Javadoc
/**
 * RuleDeclaration Update Coverage. Esegue una scansione della specifica e
 * genera una collezione di casi di test. -the update is not trivial
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */

public class RuleUpdateVisitor extends BasicRuleVisitor {

	/**
	 * Contiene la condizione booleana della guardia della regola. Verr� usata
	 * per la generazione del <I>Test Predicate</I>.
	 */

	public RuleUpdateVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getName()
	 */
	@Override
	public String getName() {
		return "Rule Update Coverage";
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
	 * baseExpression &amp;&amp; (pre != value)
	 * </PRE>
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the list<atgt.coverage. named expression>
	 */
	@Override
	public java.util.List<NamedTerm> forAssignment(UpdateRule a) {
		Vector<NamedTerm> list = new Vector<NamedTerm>();
		Expression var;
		if (a.getArg() == null) {
			var = ((Variable) a.getVar()).getIdExpression();
		} else {
			Function f = (Function)a.getVar();
			var = new FunctionTerm(f.getIdExpression(),f.getDomain(),a.getArg());
		}
		Expression notEq = new NotEqualsExpression(var, a.getValue());
		list.add(new NamedTerm("", notEq));
		return list;
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente.
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list<atgt.coverage. named expression>
	 */
	@Override
	public java.util.List<NamedTerm> forIfThenElse(
			ConditionalRule ite) {

		// DA CORREGGERE
		java.util.List<NamedTerm> bodyTestsThen = ite
				.getThenPart().accept(new RuleUpdateVisitor());

		java.util.List<NamedTerm> result = new Vector<NamedTerm>();

		Expression baseExpression = ite.getGuard();

		for (NamedTerm tc : bodyTestsThen) {
			result.add(new NamedTerm("T" + tc.getName(), new AndExpression(
					baseExpression, tc.getCondition())));

		}
		// get the else case
		BasicRule elserule = ite.getElsePart();
		if (elserule != null) {
			java.util.List<NamedTerm> bodyTestsElse = elserule
					.accept(new RuleUpdateVisitor());

			baseExpression = NotExpression.createNotExpression(ite.getGuard());

			for (NamedTerm tc : bodyTestsElse) {
				result.add(new NamedTerm("F" + tc.getName(),
						new AndExpression(baseExpression, tc.getCondition())));

			}
		}

		return result;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.BasicRuleVisitor#getAbbrName()
	 */
	@Override
	public String getAbbrName() {
		return "UR";
	}
}
