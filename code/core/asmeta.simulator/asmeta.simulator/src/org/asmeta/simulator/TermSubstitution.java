/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 * 
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 * 
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 * 
 *   http://www.gnu.org/licenses/gpl.txt
 * 
 *   
 *******************************************************************************/

/*
 * TermSubstitution.java
 *
 * Created on 25 giugno 2006, 17.02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.wrapper.RuleFactory;

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
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;

/**
 * Substituter of the terms in the place of the free variables. 
 * 
 */
public class TermSubstitution extends ReflectiveVisitor<Term> {
    
	private static Logger logger = Logger.getLogger(TermSubstitution.class);
	
	/**
	 * Prints a string representation of terms.
	 * 
	 */
	protected AsmetaTermPrinter printer = AsmetaTermPrinter.getAsmetaTermPrinter(true);
	
	/**
	 * Creates new terms and rules.
	 * 
	 */
	public static RuleFactory ruleFactory;
	
	/**
	 * This field is added to the name of renamed bound variables to avoid
	 * name clashes.
	 *  
	 */
	public static int varSuffix = 0;
	
    /**
     * Terms to substitute to free variables.
     * 
     */
    protected TermAssignment assignment;
    
    
    /**
     * Free variables contained in the terms to substitute.
     *  
     */
    protected VariableSet freeVars;
    
    /**
     * Constructor.
     *  
     * @param params assignment of terms and free variables
     */
    public TermSubstitution(TermAssignment params) {
        assignment = params;
        // FIXME getFrees() should go in the class TermAssignment to avoid
        // to call it every time a new TermSubstitution is created
        freeVars = getFrees();
    }
    
    /**
     * Copy constructor.
     * 
     */
    public TermSubstitution(TermSubstitution substitution) {
    	assignment = new TermAssignment(substitution.assignment);
    	freeVars = getFrees();
    }
       
    /**
     * Gets the free variables contained in the terms to substitute.
     * 
     * @return set of free variables
     */
    private VariableSet getFrees() {
    	VariableSet set = new VariableSet();
    	VarFinder finder = new VarFinder();
    	for (Object o : assignment.assignmentMap.values()) {
    		Term nextTerm = (Term) o;
			VariableSet nextSet = finder.visit(nextTerm);
			set.addAll(nextSet);
		}
    	return set;
    }
    
	/**
	 * Makes substitutions on a term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public Term visit(Term term) {
    	if (!(term instanceof ConstantTerm)) {
    		return visit((Object) term);
    	}
    	return term;
    }
    
	/**
	 * Makes substitutions on a free variable term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public Term visit(VariableTerm variable) {
    	logger.debug("<VariableTerm>");
        Term newTerm = getSubstitute(variable);
        logger.debug("<Before>" + printer.visit(variable) + "</Before>");
        logger.debug("<After>" + printer.visit(newTerm) + "</After>");
        logger.debug("</VariableTerm>");
        return newTerm;
    }
    
	/**
	 * Gets the term to substitute to the given free variable.
	 * 
	 * @param variable a variable
	 * @return the corresponding term 
	 */
	protected Term getSubstitute(VariableTerm variable) {
		Term substitute = assignment.get(variable);
		return substitute == null ? variable : substitute;
	}
    
	/**
	 * Makes substitutions on a tuple term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public TupleTerm visit(TupleTerm tuple) {
    	if (tuple != null) {
    		//assert tuple.getTerms() == null || tuple.getArity() == tuple.getTerms().size();
    		logger.debug("<TupleTerm>");
    		TupleTerm newTuple = ruleFactory.createTupleTerm();
    		newTuple.setDomain(tuple.getDomain());
    		newTuple.setArity(tuple.getArity());
    		List<Term> newTerms = visit(tuple.getTerms());
    		newTuple.getTerms().addAll(newTerms);
    		assert newTuple.getTerms() == null || newTuple.getArity() == newTuple.getTerms().size();
    		logger.debug("<Before>" + printer.visit(tuple) + "</Before>");
    		logger.debug("<After>" + printer.visit(newTuple) + "</After>");
    		logger.debug("</TupleTerm>");
    		return newTuple;
    	}
    	return null;
    }
    
	/**
	 * Makes substitutions on a function term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public FunctionTerm visit(FunctionTerm function) {
    	logger.debug("<FunctionTerm>");
		logger.debug("<Name>" + function.getFunction().getName() + "</Name>");
        FunctionTerm newFunction = ruleFactory.createFunctionTerm();
        newFunction.setDomain(function.getDomain());
		TupleTerm newTuple = visit(function.getArguments());
		//assert newTuple.getTerms() == null || newTuple.getArity() == newTuple.getTerms().size();
        newFunction.setArguments(newTuple);
        newFunction.setFunction(function.getFunction());
    	logger.debug("<Before>" + printer.visit(function) + "</Before>");
        logger.debug("<After>" + printer.visit(newFunction) + "</After>");
        logger.debug("</FunctionTerm>");
        return newFunction;
    }
    
	/**
	 * Makes substitutions on a location term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public LocationTerm visit(LocationTerm location) {
    	logger.debug("<LocationTerm>");
		logger.debug("<Name>" + location.getFunction().getName() + "</Name>");
        LocationTerm newLocation = ruleFactory.createLocationTerm();
        newLocation.setDomain(location.getDomain());
		TupleTerm newTuple = visit(location.getArguments());
        newLocation.setArguments(newTuple);
        newLocation.setFunction(location.getFunction());
    	logger.debug("<Before>" + printer.visit(location) + "</Before>");
        logger.debug("<After>" + printer.visit(newLocation) + "</After>");
        logger.debug("</LocationTerm>");
        return newLocation;
    }
	
	/**
	 * Makes substitutions on a conditional term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public ConditionalTerm visit(ConditionalTerm cond) {
		logger.debug("<ConditionalTerm>");
		ConditionalTerm newCond = ruleFactory.createConditionalTerm();
		newCond.setDomain(cond.getDomain());
		logger.debug("<Guard>");
		Term newGuard = visit(cond.getGuard());
		newCond.setGuard(newGuard);
		logger.debug("</Guard>");
		logger.debug("<ThenTerm>");
		Term newThen = visit(cond.getThenTerm());
		newCond.setThenTerm(newThen);
		logger.debug("</ThenTerm>");
		if (cond.getElseTerm() != null) {
			logger.debug("<ElseTerm>");
			Term newElse = visit(cond.getElseTerm());
			newCond.setElseTerm(newElse);
			logger.debug("</ElseTerm>");
		}
		logger.debug("<Before>" + printer.visit(cond) + "</Before>");
    	logger.debug("<After>" + printer.visit(newCond) + "</After>");
		logger.debug("</ConditionalTerm>");
		return newCond;
	}
	
	/**
	 * Makes substitutions on a case term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public CaseTerm visit(CaseTerm caseTerm) {
		logger.debug("<CaseTerm>");
		CaseTerm newCase = ruleFactory.createCaseTerm();
		newCase.setDomain(caseTerm.getDomain());
		logger.debug("<ComparedTerm>");
		Term newComparedTerm = visit(caseTerm.getComparedTerm());
		newCase.setComparedTerm(newComparedTerm);
		logger.debug("</ComparedTerm>");
		logger.debug("<ComparingTerms>");
		List<Term> newComparingTerms = newCase.getComparingTerm();
		visit(caseTerm.getComparingTerm(), newComparingTerms);
		logger.debug("</ComparingTerms>");
		logger.debug("<ResultTerms>");
		List<Term> newResultTerms = visit(caseTerm.getResultTerms());
		newCase.getResultTerms().addAll(newResultTerms);
		logger.debug("</ResultTerms>");		
		if (caseTerm.getOtherwiseTerm() != null) {
			logger.debug("<OtherwiseTerm>");
			Term newOtherwiseTerm = visit(caseTerm.getOtherwiseTerm());
			newCase.setOtherwiseTerm(newOtherwiseTerm);
			logger.debug("</OtherwiseTerm>");
		}
		logger.debug("<Before>" + printer.visit(caseTerm) + "</Before>");
    	logger.debug("<After>" + printer.visit(newCase) + "</After>");
		logger.debug("</CaseTerm>");
		return newCase;
	}
	
	/**
	 * Makes substitutions on a domain term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public DomainTerm visit(DomainTerm domain) {
    	logger.debug("<DomainTerm>");
    	logger.debug("<Before>" + printer.visit(domain) + "</Before>");
    	logger.debug("<After>" + printer.visit(domain) + "</After>");
    	logger.debug("</DomainTerm>");
		return domain;
    }
    
	/**
	 * Makes substitutions on an enumeration term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public EnumTerm visit(EnumTerm enumTerm) {
    	logger.debug("<EnumTerm>");
    	logger.debug("<Before>" + printer.visit(enumTerm) + "</Before>");
    	logger.debug("<After>" + printer.visit(enumTerm) + "</After>");
    	logger.debug("</EnumTerm>");
        return enumTerm;
    }
	
	/**
	 * Makes substitutions on a let term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public LetTerm visit(LetTerm let) {
		logger.debug("<LetTerm>");
		TermSubstitution newSubstitution = new TermSubstitution(this);
		LetTerm newLet = ruleFactory.createLetTerm();
		logger.debug("<Variables>");
		List<VariableTerm> newVarList = newLet.getVariable();
		newSubstitution.visitBoundVars(let.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<InitTerms>");
		List<Term> newInitList = newLet.getAssignmentTerm();
		newSubstitution.visit(let.getAssignmentTerm(), newInitList);
		logger.debug("</InitTerms>");
		newLet.setDomain(let.getDomain());
		logger.debug("<Body>");
		Term newBody = newSubstitution.visit(let.getBody());
		newLet.setBody(newBody);
		logger.debug("</Body>");
		logger.debug("<Before>" + printer.visit(let) + "</Before>");
    	logger.debug("<After>" + printer.visit(newLet) + "</After>");
		logger.debug("</LetTerm>");
		return newLet;
	}

	/**
	 * Makes substitutions on an exist term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public ExistTerm visit(ExistTerm exist) {
		logger.debug("<ExistTerm>");
		ExistTerm newExist = ruleFactory.createExistTerm();
		visitQuant(exist, newExist);
		logger.debug("<Before>" + printer.visit(exist) + "</Before>");
    	logger.debug("<After>" + printer.visit(newExist) + "</After>");
		logger.debug("</ExistTerm>");
		return newExist;
	}
	
	/**
	 * Makes substitutions on a forall term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public ForallTerm visit(ForallTerm forall) {
		logger.debug("<ForallTerm>");
		ForallTerm newForall = ruleFactory.createForallTerm();
		visitQuant(forall, newForall);
		logger.debug("<Before>" + printer.visit(forall) + "</Before>");
    	logger.debug("<After>" + printer.visit(newForall) + "</After>");
		logger.debug("</ForallTerm>");
		return newForall;
	}
		
	/**
	 * Makes substitutions on a finite quantification term.
	 * 
	 * @param quantTerm a finite quantification term
	 * @param newQuantTerm the new term
	 */
	protected void visitQuant(FiniteQuantificationTerm quantTerm, FiniteQuantificationTerm newQuantTerm) {
		logger.debug("<Variables>");
		TermSubstitution newSubstitution = new TermSubstitution(this);
		List<VariableTerm> newVarList = newQuantTerm.getVariable();
		newSubstitution.visitBoundVars(quantTerm.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<Ranges>");
		List<Term> newRanges = newSubstitution.visit(quantTerm.getRanges());
		newQuantTerm.getRanges().addAll(newRanges);
		logger.debug("</Ranges>");
		newQuantTerm.setDomain(quantTerm.getDomain());
		logger.debug("<Guard>");
		Term newGuard = newSubstitution.visit(quantTerm.getGuard());
		newQuantTerm.setGuard(newGuard);
		logger.debug("</Guard>");
	}
	
	/**
	 * Makes substitutions on a rule as term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public RuleAsTerm visit(RuleAsTerm term) {
    	logger.debug("<RuleAsTerm name=\"" + term.getRule().getName() + "\"/>");
    	return term;
    }
	
	/**
	 * Makes substitutions on a set comprehension term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public SetCt visit(SetCt setCt) {
		logger.debug("<SetCt>");
		SetCt newSetCt = ruleFactory.createSetCt();
		visitCt(setCt, newSetCt);
		logger.debug("<Before>" + printer.visit(setCt) + "</Before>");
    	logger.debug("<After>" + printer.visit(newSetCt) + "</After>");
		logger.debug("</SetCt>");
		return newSetCt;
	}

	/**
	 * Makes substitutions on a sequence comprehension term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public SequenceCt visit(SequenceCt seqCt) {
		logger.debug("<SequenceCt>");
		SequenceCt newSeqCt = ruleFactory.createSequenceCt();
		visitCt(seqCt, newSeqCt);
		logger.debug("<Before>" + printer.visit(seqCt) + "</Before>");
    	logger.debug("<After>" + printer.visit(newSeqCt) + "</After>");
		logger.debug("</SequenceCt>");
		return newSeqCt;
	}

	/**
	 * Makes substitutions on a comprehension term.
	 * 
	 * @param ct a comprehension term
	 * @param newCt the new term
	 */
	public void visitCt(ComprehensionTerm ct, ComprehensionTerm newCt) {
		logger.debug("<Variables>");
		TermSubstitution newSubstitution = new TermSubstitution(this);
		List<VariableTerm> newVarList = newCt.getVariable();
		newSubstitution.visitBoundVars(ct.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<Ranges>");
		List<Term> newRanges = newSubstitution.visit(ct.getRanges());
		newCt.getRanges().addAll(newRanges);
		logger.debug("</Ranges>");
		newCt.setDomain(ct.getDomain());
		// get the guard
		logger.debug("<Guard>");
		Term newGuard = ct.getGuard();
		if (newGuard != null) newGuard = newSubstitution.visit(ct.getGuard());
		else logger.debug("null guard");
		newCt.setGuard(newGuard);
		logger.debug("</Guard>");
		logger.debug("<Term>");
		Term newTerm = newSubstitution.visit(ct.getTerm());
		newCt.setTerm(newTerm);
		logger.debug("</Term>");
	}
	
	/**
	 * Makes substitutions on a set term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
    public SetTerm visit(SetTerm setTerm) {
    	logger.debug("<SetTerm>");
    	SetTerm newSet = ruleFactory.createSetTerm();
    	Collection<Term> newTerms = newSet.getTerm();
    	visit(setTerm.getTerm(), newTerms);
    	newSet.setDomain(setTerm.getDomain());
    	newSet.setSize(setTerm.getSize());
    	logger.debug("<Before>" + printer.visit(setTerm) + "</Before>");
    	logger.debug("<After>" + printer.visit(setTerm) + "</After>");
    	logger.debug("</SetTerm>");
        return setTerm;
    }
	
	/**
	 * Makes substitutions on a sequence term.
	 * 
	 * @param term a term
	 * @return the new term
	 */
	public SequenceTerm visit(SequenceTerm sequence) {
    	logger.debug("<SequenceTerm>");
    	SequenceTerm newSeq = ruleFactory.createSequenceTerm();
    	// creates the list of terms in it
    	// List newTerms = newSeq.getTerms();
    	List<Term> newTerms = new ArrayList<Term>();
    	newSeq.getTerms().addAll(newTerms);
    	visit(sequence.getTerms(), newTerms);
    	newSeq.setDomain(sequence.getDomain());
    	newSeq.setSize(sequence.getSize());
    	logger.debug("<Before>" + printer.visit(sequence) + "</Before>");
    	logger.debug("<After>" + printer.visit(sequence) + "</After>");
    	logger.debug("</SequenceTerm>");
        return sequence;
    }
	
	/**
	 * Makes a copy of the given variable.
	 * 
	 * @param var a variable
	 * @return the copy
	 */
	protected VariableTerm copy(VariableTerm var) {
		VariableTerm newVar = ruleFactory.createVariableTerm();
		newVar.setDomain(var.getDomain());
		newVar.setName(var.getName());
		newVar.setKind(var.getKind());
		return newVar;
	}
	
	/**
	 * Makes substitutions on a list of terms.
	 * 
	 * @param term a list of terms
	 * @return the new list of terms
	 */
    protected List<Term> visit(Collection<?>/*<Term>*/ termList) {
    	ArrayList<Term> newList = new ArrayList<Term>(termList.size());
    	return visit(termList, newList);
    }

    /**
     * Makes substitutions on a list of terms.
     * 
     * @param termList a list of terms
     * @param newList the new list of terms
     * @return <i>newList</i>
     */
    protected List<Term> visit(
    		Collection<?>/*<Term>*/ termList, 
    		List<Term> newList) {
    	for (Object o : termList) {
    		Term term = (Term) o;
			Term newTerm = visit(term);
			newList.add(newTerm);
		}
    	return newList;
    }
    
    /**
     * Makes substitutions on a collection of terms.
     * 
     * @param termList a collection of terms
     * @param newList the new collection of terms
     * @return <i>newList</i>
     */

    protected Collection<Term> visit(
    		Collection<?>/*<Term>*/ termList, 
    		Collection<Term> newList) {
    	for (Object o : termList) {
    		Term term = (Term) o;
			Term newTerm = visit(term);
			newList.add(newTerm);
		}
    	return newList;
    }
    	
	/**
	 * Mangles a variable name to avoid name clashes.
	 * 
	 * @param var a variable
	 */
	void mangleName(VariableTerm var) {
		// the character '!' can not belong to a standard name so the
		// new name is certainly unique
		var.setName(var.getName() + "!" + ++varSuffix );
	}
	
	/**
	 * Renames the bound variables (i.e. not in the field <i>freeVars</i>)
	 * contained in the given collection.
	 * 
	 * @param varList a variable collection
	 * @return the new collection
	 */
	protected Collection<VariableTerm> visitBoundVars(Collection<VariableTerm> varList) {
		List<VariableTerm> newList = new ArrayList<VariableTerm>();
		return visitBoundVars(varList, newList);
	}
	
	/**
	 * Renames the bound variables (i.e. not in the field <i>freeVars</i>)
	 * contained in the given collection.
	 * 
	 * @param varList a variable collection
	 * @param newList the new collection
	 * @return <i>newList</i>
	 */
	protected Collection<VariableTerm> visitBoundVars(
			Collection<VariableTerm> varList, 
			Collection<VariableTerm> newList) {
		for (Object o : varList) {
			VariableTerm var = (VariableTerm) o;
			VariableTerm newVar;
			if (freeVars.contains(var)) {
				// rename the variable
				VariableTerm renamedVar = copy(var);
				mangleName(renamedVar);
				// put the renamed variable in the assignment
				assignment.put(var, renamedVar);
				newVar = copy(renamedVar);
			} else {
				newVar = copy(var);
			}
			newList.add(newVar);
		}
		return newList;		
	}
	
}
