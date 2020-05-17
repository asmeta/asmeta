/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator;

import java.util.Collection;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;

/**
 * Free variables in terms finder.
 *
 */
public class VarFinder extends ReflectiveVisitor<VariableSet> {

	/**
	 * Gets the free variables contained in the given term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(Term term) {
		if (!(term instanceof ConstantTerm)) {
			return visit((Object) term);
		}
		return new VariableSet();
	}
	
	/**
	 * Gets the free variables contained in the given variable term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(VariableTerm var) {
		VariableSet freeVars = new VariableSet();
		freeVars.add(var);
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given tuple term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(TupleTerm tuple) {
		if (tuple != null) {
			return visit(tuple.getTerms());
		}
		return new VariableSet();
	}
	
	/**
	 * Gets the free variables contained in the given function term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(FunctionTerm func) {
		return visit(func.getArguments());
	}
	
	/**
	 * Gets the free variables contained in the given location term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(LocationTerm loc) {
		return visit(loc.getArguments());
	}
	
	/**
	 * Gets the free variables contained in the given conditional term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(ConditionalTerm cond) {
		VariableSet freeVars = new VariableSet();
		freeVars.addAll(visit(cond.getGuard()));
		freeVars.addAll(visit(cond.getThenTerm()));
		if (cond.getElseTerm() != null) {
			freeVars.addAll(visit(cond.getElseTerm()));
		}
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given case term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(CaseTerm caseTerm) {
		VariableSet freeVars = new VariableSet();
		freeVars.addAll(visit(caseTerm.getComparedTerm()));
		freeVars.addAll(visit(caseTerm.getComparingTerm()));
		freeVars.addAll(visit(caseTerm.getResultTerms()));
		if (caseTerm.getOtherwiseTerm() != null) {
			freeVars.addAll(visit(caseTerm.getOtherwiseTerm()));
		}
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given domain term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(DomainTerm domain) {
		return new VariableSet();
	}
	
	
	/**
	 * Gets the free variables contained in the given rule as term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(RuleAsTerm ruleAsTerm) {
		return new VariableSet();
	}
		
	/**
	 * Gets the free variables contained in the given let term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(LetTerm letTerm) {
		VariableSet freeVars = new VariableSet();
		VariableSet varSet = visit(letTerm.getVariable());
		VariableSet initSet = visit(letTerm.getAssignmentTerm());
		VariableSet bodySet = visit(letTerm.getBody());		
		freeVars.addAll(initSet);
		freeVars.addAll(bodySet);
		freeVars.removeAll(varSet);
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given exist term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(ExistTerm exist) {
		return visitQuant(exist);
	}
	
	/**
	 * Gets the free variables contained in the given forall term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(ForallTerm forall) {
		return visitQuant(forall);
	}
	
	/**
	 * Gets the free variables contained in the given finite quantification term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	private VariableSet visitQuant(FiniteQuantificationTerm quant) {
		VariableSet freeVars = new VariableSet();
		VariableSet varSet = visit(quant.getVariable());
		VariableSet rangeSet = visit(quant.getRanges());
		VariableSet guardSet = visit(quant.getGuard());		
		freeVars.addAll(rangeSet);
		freeVars.addAll(guardSet);
		freeVars.removeAll(varSet);
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given set comprehension term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(SetCt setCt) {
		return visitCt(setCt);
	}

	/**
	 * Gets the free variables contained in the given sequence comprehension term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(SequenceCt seqCt) {
		return visitCt(seqCt);
	}

	/**
	 * Gets the free variables contained in the given comprehension term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	private VariableSet visitCt(ComprehensionTerm comp) {
		VariableSet freeVars = new VariableSet();
		VariableSet varSet = visit(comp.getVariable());
		VariableSet rangeSet = visit(comp.getRanges());
		VariableSet guardSet = visit(comp.getGuard());
		VariableSet termSet = visit(comp.getTerm());
		freeVars.addAll(rangeSet);
		freeVars.addAll(guardSet);
		freeVars.addAll(termSet);
		freeVars.removeAll(varSet);
		return freeVars;
	}
	
	/**
	 * Gets the free variables contained in the given set term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(SetTerm set) {
		return visit(set.getTerm());
	}
	
	/**
	 * Gets the free variables contained in the given sequence term.
	 * 
	 * @param term a term
	 * @return the free variable set
	 */
	public VariableSet visit(SequenceTerm seq) {
		return visit(seq.getTerms());
	}
	
	/**
	 * Gets the free variables contained in the given collection of terms.
	 * 
	 * @param term a collection of terms
	 * @return the free variable set
	 */
	private VariableSet visit(Collection<? extends Term> terms) {
		VariableSet freeVars = new VariableSet();
		for (Object o : terms) {
			Term term = (Term) o;
			VariableSet nextSet = visit(term);
			freeVars.addAll(nextSet);
		}
		return freeVars;
	}
		
}
