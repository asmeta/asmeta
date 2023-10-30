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
 * RuleSubstitution.java
 *
 * Created on 27 giugno 2006, 9.45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.RulePrinter;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

/**
 * Given a term assignment, i.e, an assignment between variables (the formal 
 * parameters of a rule) and terms (to substitute to variables), this class 
 * provides methods which do substitutions in order to obtain a new rule.
 *
 */
public class RuleSubstitution extends TermSubstitution {

	private static Logger logger = Logger.getLogger(RuleSubstitution.class);
		
	/**
	 * Returns a string representation of a rule.
	 * 
	 */
	private static RulePrinter printer = new RulePrinter(true);

    /**
     * Constructor.
     *
     * @param params term assignment
     */
    public RuleSubstitution(TermAssignment params,RuleFactory ruleFactory) {
    	super(params,ruleFactory);
    }
    
    /**
     * Copy constructor.
     * 
     */
    public RuleSubstitution(RuleSubstitution substitution) {
    	super(substitution);
    }


	/**
	 * Does substitutions on the rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public Rule visit(Rule rule) {
        return (Rule) visit((Object)rule);
    }

	/**
	 * Does substitutions on the skip rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public SkipRule visit(SkipRule skipRule) {
    	logger.debug("<SkipRule>");
    	logger.debug("<Before>" + printer.visit(skipRule) + "</Before>");
    	logger.debug("<After>" + printer.visit(skipRule) + "</After>");
    	logger.debug("</SkipRule>");
        return ruleFactory.createSkipRule();
    }

	/**
	 * Does substitutions on the update rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public UpdateRule visit(UpdateRule updateRule) {
    	logger.debug("<UpdateRule>");
        logger.debug("<Location>");
        UpdateRule newUpdateRule = ruleFactory.createUpdateRule();
       	Term leftHandSide = updateRule.getLocation();
        Term newLeftHandSide = visit(leftHandSide);
        if (newLeftHandSide instanceof LocationTerm || newLeftHandSide instanceof VariableTerm) {
            newUpdateRule.setLocation(newLeftHandSide);
        } else if (newLeftHandSide instanceof FunctionTerm) {
        	// converts a FunctionTerm to a LocationTerm
            LocationTerm locationTerm = createLocationTerm((FunctionTerm) newLeftHandSide);
            newUpdateRule.setLocation(locationTerm);
        } else {
            throw new Error("Expected a location term or a location variable, but found " + newLeftHandSide.getClass().getName());
        }
        logger.debug("</Location>");
        logger.debug("<UpdatingTerm>");
        Term updatingTerm = updateRule.getUpdatingTerm();
        Term newRightHandSide = visit(updatingTerm);
        newUpdateRule.setUpdatingTerm(newRightHandSide);
        logger.debug("</UpdatingTerm>");
    	logger.debug("<Before>" + printer.visit(updateRule) + "</Before>");
    	logger.debug("<After>" + printer.visit(newUpdateRule) + "</After>");
        logger.debug("</UpdateRule>");
        return newUpdateRule;
    }

    /**
     * Converts a dynamic function term to a location term.
     *
     * @param functionTerm a function
     * @return the location
     */
    private LocationTerm createLocationTerm(FunctionTerm functionTerm) {
    	// IMPORTANT
    	// You must create a new tuple term in order to pass the arguments of
    	// the function term to the location term.
    	// If you simply use code like this:
    	// locationTerm.setArguments(functionTerm.getArguments());
    	// you get a javax.jmi.reflect.CompositionViolationException.
    	// I don't know if it is a bug or what.
    	// SUPPLEMENT: It isn't a bug. For each sub-element, which has a
    	// composition relationship with his main element (solid diamond), one
    	// needs to create a new object.
    	LocationTerm locationTerm = ruleFactory.createLocationTerm();
    	TupleTerm arguments = functionTerm.getArguments();
		if (arguments != null) {
    		TupleTerm tupleTerm = ruleFactory.createTupleTerm();
    		tupleTerm.setArity(arguments.getArity());
    		//assert arguments.getTerms() == null || arguments.getArity() == arguments.getTerms().size();
    		tupleTerm.getTerms().addAll(arguments.getTerms());
    		tupleTerm.setDomain(arguments.getDomain());
    		tupleTerm.setArity(arguments.getArity());
    		locationTerm.setArguments(tupleTerm);
    	}
    	locationTerm.setFunction(functionTerm.getFunction());
    	locationTerm.setDomain(functionTerm.getDomain());
    	return locationTerm;
    }

	/**
	 * Does substitutions on the block rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public BlockRule visit(BlockRule blockRule) {
    	logger.debug("<BlockRule>");
        BlockRule newBlockRule = ruleFactory.createBlockRule();
        logger.debug("<RuleList>");
        List<Rule> newRuleList = new ArrayList<Rule>();
        for (Rule rule : blockRule.getRules()) {
            Rule newRule = visit(rule);
            newRuleList.add(newRule);
        }
        newBlockRule.getRules().addAll(newRuleList);
        logger.debug("</RuleList>");
        logger.debug("<Before>" + printer.visit(blockRule) + "</Before>");
        logger.debug("<After>" + printer.visit(newBlockRule) + "</After>");
        logger.debug("</BlockRule>");
        return newBlockRule;
    }

	/**
	 * Does substitutions on the sequential rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public SeqRule visit(SeqRule seqRule) {
    	logger.debug("<SeqRule>");
        SeqRule newSeqRule = ruleFactory.createSeqRule();
        List<Rule> newRuleList = new ArrayList<Rule>();
        logger.debug("<RuleList>");
        for (Rule rule : seqRule.getRules()) {
            Rule newRule = visit(rule);
            newRuleList.add(newRule);
        }
        newSeqRule.getRules().addAll(newRuleList);
        logger.debug("</RuleList>");
    	logger.debug("<Before>" + printer.visit(seqRule) + "</Before>");
        logger.debug("<After>" + printer.visit(newSeqRule) + "</After>");
        logger.debug("</SeqRule>");
        return newSeqRule;
    }

	/**
	 * Does substitutions on the term as rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public Rule visit(TermAsRule termAsRule) {
    	TermAsRule newTermAsRule = ruleFactory.createTermAsRule();
    	logger.debug("<TermAsRule>");
    	Term newTerm = visit(termAsRule.getTerm());    	
    	List<Term> args = termAsRule.getParameters();
    	if (args != null && args.size() > 0) {
    		logger.debug("<Arguments>");
    		List<Term> newArgs = visit(args);
    		logger.debug("</Arguments>");
    		newTermAsRule.getParameters().addAll(newArgs);
    	}
    	newTermAsRule.setTerm(newTerm);    	    	    
    	logger.debug("<Before>" + printer.visit(termAsRule) + "</Before>");
        logger.debug("<After>" + printer.visit(newTermAsRule) + "</After>");
        logger.debug("</TermAsRule>");
        return newTermAsRule;
    }

	/**
	 * Does substitutions on the conditional rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public ConditionalRule visit(ConditionalRule condRule) {
    	logger.debug("<ConditionalRule>");
        ConditionalRule newCondRule = ruleFactory.createConditionalRule();
        logger.debug("<Guard>");
        Term guard = visit(condRule.getGuard());
        logger.debug("</Guard>");
        logger.debug("<ThenRule>");
        Rule thenRule = visit(condRule.getThenRule());
        logger.debug("</ThenRule>");
        Rule elseRule = null;
        if (condRule.getElseRule() != null) {
        	logger.debug("<ElseRule>");
            elseRule = visit(condRule.getElseRule());
            logger.debug("</ElseRule>");
        }
        newCondRule.setGuard(guard);
        newCondRule.setThenRule(thenRule);
        newCondRule.setElseRule(elseRule);
    	logger.debug("<Before>" + printer.visit(condRule) + "</Before>");
        logger.debug("<After>" + printer.visit(newCondRule) + "</After>");
        logger.debug("</ConditionalRule>");
        return newCondRule;
    }

	/**
	 * Does substitutions on the switch rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
	public CaseRule visit(CaseRule caseRule) {
		logger.debug("<CaseRule>");
		CaseRule newCase = ruleFactory.createCaseRule();
		logger.debug("<ComparedTerm>");
		Term newComparedTerm = visit(caseRule.getTerm());
		newCase.setTerm(newComparedTerm);
		logger.debug("</ComparedTerm>");
		logger.debug("<ComparingTerms>");
		List<Term> newComparingTerms = newCase.getCaseTerm();
		visit(caseRule.getCaseTerm(), newComparingTerms);
		logger.debug("</ComparingTerms>");
		logger.debug("<BranchRules>");
		List<Rule> newBranchRules = visitRules(caseRule.getCaseBranches());
		newCase.getCaseBranches().addAll(newBranchRules);
		logger.debug("</BranchRules>");
		if (caseRule.getOtherwiseBranch() != null) {
			logger.debug("<OtherwiseRule>");
			Rule newOtherwise = visit(caseRule.getOtherwiseBranch());
			newCase.setOtherwiseBranch(newOtherwise);
			logger.debug("</OtherwiseRule>");
		}
		logger.debug("<Before>" + printer.visit(caseRule) + "</Before>");
    	logger.debug("<After>" + printer.visit(newCase) + "</After>");
		logger.debug("</CaseRule>");
		return newCase;
	}

	/**
	 * Does substitutions on the let rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public LetRule visit(LetRule let) {
		logger.debug("<LetRule>");
		LetRule newLet = ruleFactory.createLetRule();
		RuleSubstitution newSubstitution = new RuleSubstitution(this);
		logger.debug("<Variables>");
		List<VariableTerm> newVarList = newLet.getVariable();
		newSubstitution.visitBoundVars(let.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<InitTerms>");
		List<Term> newInitList = newLet.getInitExpression();
		newSubstitution.visit(let.getInitExpression(), newInitList);
		logger.debug("</InitTerms>");
		logger.debug("<InRule>");
		Rule newInRule = newSubstitution.visit(let.getInRule());
		newLet.setInRule(newInRule);
		logger.debug("</InRule>");
		logger.debug("<Before>" + printer.visit(let) + "</Before>");
    	logger.debug("<After>" + printer.visit(newLet) + "</After>");
		logger.debug("</LetRule>");
		return newLet;
	}

	/**
	 * Does substitutions on the forall rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
	public ForallRule visit(ForallRule forall) {
		logger.debug("<ForallRule>");
		ForallRule newForall = ruleFactory.createForallRule();
		RuleSubstitution newSubstitution = new RuleSubstitution(this);
		logger.debug("<Variables>");
		List<VariableTerm> newVarList = newForall.getVariable();
		newSubstitution.visitBoundVars(forall.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<Ranges>");
		List<Term> newRanges = newSubstitution.visit(forall.getRanges());
		newForall.getRanges().addAll(newRanges);
		logger.debug("</Ranges>");
		logger.debug("<Guard>");
		Term newGuard = newSubstitution.visit(forall.getGuard());
		newForall.setGuard(newGuard);
		logger.debug("</Guard>");
		logger.debug("<DoRule>");
		Rule newDoRule = newSubstitution.visit(forall.getDoRule());
		newForall.setDoRule(newDoRule);
		logger.debug("</DoRule>");
    	logger.debug("<Before>" + printer.visit(forall) + "</Before>");
    	logger.debug("<After>" + printer.visit(newForall) + "</After>");
    	logger.debug("</ForallRule>");
		return newForall;
	}

	/**
	 * Does substitutions on the choose rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
	public ChooseRule visit(ChooseRule choose) {
		logger.debug("<ChooseRule>");
		ChooseRule newChoose = ruleFactory.createChooseRule();
		RuleSubstitution newSubstitution = new RuleSubstitution(this);
		logger.debug("<Variables>");
		List<VariableTerm> newVarList = newChoose.getVariable();
		newSubstitution.visitBoundVars(choose.getVariable(), newVarList);
		logger.debug("</Variables>");
		logger.debug("<Ranges>");
		List<Term> newRanges = newSubstitution.visit(choose.getRanges());
		newChoose.getRanges().addAll(newRanges);
		logger.debug("</Ranges>");
		logger.debug("<Guard>");
		Term newGuard = newSubstitution.visit(choose.getGuard());
		newChoose.setGuard(newGuard);
		logger.debug("</Guard>");
		logger.debug("<DoRule>");
		Rule newDoRule = newSubstitution.visit(choose.getDoRule());
		newChoose.setDoRule(newDoRule);
		logger.debug("</DoRule>");
		if (choose.getIfnone() != null) {
			logger.debug("<IfnoneRule>");
			Rule newIfnone = newSubstitution.visit(choose.getIfnone());
			newChoose.setIfnone(newIfnone);
			logger.debug("</IfnoneRule>");
		}
    	logger.debug("<Before>" + printer.visit(choose) + "</Before>");
    	logger.debug("<After>" + printer.visit(newChoose) + "</After>");
    	logger.debug("</ChooseRule>");
		return newChoose;
	}

	/**
	 * Does substitutions on the choose rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
	public IterativeWhileRule visit(IterativeWhileRule itWhileRule) {
		logger.debug("<IterativeWhileRule>");
		IterativeWhileRule newItWhileRule = ruleFactory.createIterativeWhileRule();
		RuleSubstitution newSubstitution = new RuleSubstitution(this);
		logger.debug("<Guard>");
		Term newGuard = newSubstitution.visit(itWhileRule.getGuard());
		newItWhileRule.setGuard(newGuard);
		logger.debug("</Guard>");
		logger.debug("<Rule>");
		Rule newRule = newSubstitution.visit(itWhileRule.getRule());
		newItWhileRule.setRule(newRule);
		logger.debug("</Rule>");
		logger.debug("<Before>" + printer.visit(itWhileRule) + "</Before>");
    	logger.debug("<After>" + printer.visit(newItWhileRule) + "</After>");
    	logger.debug("</IterativeWhileRule>");
		return newItWhileRule;
	}

	/**
	 * Does substitutions on the macro call rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public MacroCallRule visit(MacroCallRule macro) {
    	logger.debug("<MacroCallRule>");
    	MacroCallRule newMacro = ruleFactory.createMacroCallRule();
    	logger.debug("<Arguments>");
    	List<Term> newParams = visit(macro.getParameters());
    	newMacro.getParameters().addAll(newParams);
    	logger.debug("</Arguments>");
    	newMacro.setCalledMacro(macro.getCalledMacro());
		logger.debug("<Before>" + printer.visit(macro) + "</Before>");
    	logger.debug("<After>" + printer.visit(newMacro) + "</After>");
    	logger.debug("</MacroCallRule>");
    	return newMacro;
    }
    
	/**
	 * Does substitutions on the turbo call rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
    public TurboCallRule visit(TurboCallRule turbo) {
    	logger.debug("<TurboCallRule>");
    	TurboCallRule newTurbo = ruleFactory.createTurboCallRule();
    	logger.debug("<Arguments>");
    	List<Term> newParams = visit(turbo.getParameters());
    	newTurbo.getParameters().addAll(newParams);
    	logger.debug("</Arguments>");
    	newTurbo.setCalledRule(turbo.getCalledRule());
		logger.debug("<Before>" + printer.visit(turbo) + "</Before>");
    	logger.debug("<After>" + printer.visit(newTurbo) + "</After>");
    	logger.debug("</TurboCallRule>");
    	return newTurbo;
    }
    
    public TurboReturnRule visit(TurboReturnRule retRule) {
    	TurboReturnRule newRetRule = ruleFactory.createTurboReturnRule();
    	newRetRule.setLocation(retRule.getLocation());
    	newRetRule.setUpdateRule(retRule.getUpdateRule());
    	return newRetRule;
    }

	/**
	 * Does substitutions on the extend rule.
	 * 
	 * @param rule a rule
	 * @return the new rule
	 */
	public ExtendRule visit(ExtendRule extend) {
		logger.debug("<ExtendRule>");
		ExtendRule newExtend = ruleFactory.createExtendRule();
		RuleSubstitution newSubstitution = new RuleSubstitution(this);
		logger.debug("<Variables>");
		Collection<VariableTerm> newVarList = newExtend.getBoundVar();
		newSubstitution.visitBoundVars(extend.getBoundVar(), newVarList);
		logger.debug("</Variables>");
		newExtend.setExtendedDomain(extend.getExtendedDomain());
		logger.debug("<DoRule>");
		Rule newDoRule = newSubstitution.visit(extend.getDoRule());
		newExtend.setDoRule(newDoRule);
		logger.debug("</DoRule>");
		logger.debug("<Before>" + printer.visit(extend) + "</Before>");
    	logger.debug("<After>" + printer.visit(newExtend) + "</After>");
		logger.debug("</ExtendRule>");
		return newExtend;
	}

	/**
	 * Does substitutions on a list of rules.
	 * 
	 * @param ruleList a list of rules
	 * @return the list of new rules
	 */
    protected List<Rule> visitRules(Collection<Rule> ruleList) {
    	ArrayList<Rule> newList = new ArrayList<Rule>();
    	for (Rule rule : ruleList) {
			Rule newRule = visit(rule);
			newList.add(newRule);
		}
    	return newList;
    }

}
