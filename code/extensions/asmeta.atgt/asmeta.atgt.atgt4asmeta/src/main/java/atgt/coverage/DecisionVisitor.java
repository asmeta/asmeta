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

import atgt.specification.ASMSpecification;
import atgt.specification.statement.ChooseRule;
import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.statement.Skip;
import atgt.specification.statement.UpdateRule;
import tgtlib.definitions.NamedTerm;
import tgtlib.specification.SpecificationAnalyzer;

/**
 * computes all the decisions for a specification returns the decisions as set
 * of Expression; tiene conto dell'annidamento: se ho un if a then if b,
 * ritorna a and b se ho if a then .. else if b, ritorna a, !a and b
 * 
 * Ignore the else if else is empty 
 * 
 * 
 * @author garganti
 */
public class DecisionVisitor extends RuleTPExpractor implements
		SpecificationAnalyzer<List<NamedTerm>,ASMSpecification> {


	/**
	 * Creates a new instance of DecisionVisitor.
	 */
	private DecisionVisitor() {}

	/** The compute decisions (ignore else)*/
	static public DecisionVisitor computeDecisions = new DecisionVisitor();
	
	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata la lista vuota. Non ci sono <I>Test Predicate</I>
	 * da generare.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forSkip(Skip s) {
		return new Vector<NamedTerm>();
	}

	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata la lista vuota. Non ci sono <I>Test Predicate</I>
	 * da generare.
	 * 
	 * @param a
	 *            the a
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forAssignment(UpdateRule a) {
		return new Vector<NamedTerm>();
	}

	/**
	 * It builds the test predicates for all the rules in the block it add to
	 * the name a fine index.
	 * 
	 * @param d
	 *            the d
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forDoStatement(DoStatement d) {
		return d.addResults(this);
	}

	
	/**
	 * Ritorna la lista di <I>Test Predicate</I> per lo statement corrente. In
	 * particolare viene ritornata una lista contenente due <I>Test Predicate</I>
	 * nel caso ci sia un else
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public List<NamedTerm> forIfThenElse(ConditionalRule ite) {

		return distributeOverConditional(ite, true);
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
	public List<NamedTerm> forMacroCallRule(MacroCallRule ite) {
		return ite.getRuleDeclaration().accept(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration(atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	public List<NamedTerm> forRuleDeclaration(RuleDeclaration r) {
		List<NamedTerm> result = r.getBody().accept(this);
		for (NamedTerm ne : result)
			ne.setName(r.getName() + "_" + ne.getName());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.specification.SpecificationAnalyzer#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public List<NamedTerm> analyze(ASMSpecification SP) {

		List<NamedTerm> list;

		// Per ogni regola nella specifica esegue la generazione dei <I>Test
		// Predicate</I>
		// if SP has no main rule, otherwise, just generate for main rule
		// note: an asm without main rule has as main rule the par of all the
		// rules
		// as in asm gofer
		if (SP.getMainrule() == null) {
			list = new Vector<NamedTerm>();
			for (RuleDeclaration r: SP.allRules()) {
				list.addAll(r.accept(this));
			}
		} else {
			RuleDeclaration rd = SP.getMainrule();
			list = rd.accept(this);
		}

		return list;
	}

	@Override
	public List<NamedTerm> forChooseRule(ChooseRule chooseRule) {
		return distributechhose(chooseRule);
	}

}
