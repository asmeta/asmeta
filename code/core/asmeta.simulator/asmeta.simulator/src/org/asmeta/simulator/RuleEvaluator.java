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
 * RuleEvaluator.java
 *
 * Created on 22 maggio 2006, 11.39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.util.RandomIterator;
import org.asmeta.simulator.value.AgentValue;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CollectionValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.RuleValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.basicterms.impl.LocationTermImpl;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

/**
 * Provides methods to evaluate rules in a given state
 * 
 */
public class RuleEvaluator extends RuleVisitor<UpdateSet> {

	private static final Random RAND = new Random();

	public static final Logger logger = Logger.getLogger(RuleEvaluator.class);

	/**
	 * Shuffles the elements in the choose rule.
	 * 
	 */
	public static boolean isShuffled = false;

	/**
	 * Returns a string representation of a term.
	 * 
	 */
	private static AsmetaTermPrinter printer = AsmetaTermPrinter.getAsmetaTermPrinter(true);

	/**
	 * Caches the macro substitutions.
	 * 
	 */
	static HashMap<String, Rule> macros = new HashMap<String, Rule>();

	public final TermEvaluator termEval;

	/**
	 * Constructs an evaluator: reuses the covered macros
	 * to be used in the same run
	 * 
	 * @param state
	 *            state
	 * @param environment
	 *            environment
	 * @param assignment
	 *            assignment
	 */
	protected RuleEvaluator(State state, Environment environment, ValueAssignment assignment) {
		termEval = new TermEvaluator(state, environment, assignment);
	}

	/**
	 * Constructs a new fresh evaluator.
	 * 
	 * @param state
	 *            state
	 * @param environment
	 *            environment
	 * @param factory
	 *            factory
	 */
	public RuleEvaluator(State state, Environment environment, RuleFactory factory) {
		this(state, environment, new ValueAssignment());
		TermSubstitution.ruleFactory = factory;
	}

	Value visitTerm(Term t) {
		return termEval.visit(t);
	}

	/**
	 * Evaluates a skip rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(SkipRule rule) {
		logger.debug("<SkipRule>");
		logger.debug("<UpdateSet>{}</UpdateSet>");
		logger.debug("</SkipRule>");
		return new UpdateSet();
	}

	/**
	 * Evaluates an update rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(UpdateRule rule) {
		logger.debug("<UpdateRule>");
		UpdateSet updateSet = new UpdateSet();
		logger.debug("<UpdatingTerm>");
		Term rhsTerm = rule.getUpdatingTerm();
		Value content = visitTerm(rhsTerm);
		logger.debug("</UpdatingTerm>");
		Term lhsTerm = rule.getLocation();
		if (lhsTerm instanceof LocationTerm) {
			logger.debug("<LocationTerm>");
			LocationTerm locationTerm = (LocationTerm) lhsTerm;
			Function signature = locationTerm.getFunction();
			logger.debug("<Name>" + signature.getName() + "</Name>");
			TupleTerm tupleTerm = locationTerm.getArguments();
			logger.debug("<Arguments>" + tupleTerm + "</Arguments>");
			List arguments;
			// if tuple term has no arguments (plain variable), then build an empty value
			// array
			if (tupleTerm == null) {
				arguments = Collections.emptyList();
			} else {
				//assert tupleTerm.getTerms().size() == tupleTerm.getArity() : "tupleTerm.getTerms().size(): " + tupleTerm.getTerms().size() + "  tupleTerm.getArity(): " + tupleTerm.getArity();
				arguments = ((TupleValue) visitTerm(tupleTerm)).getValue();
			}
			logger.debug("</LocationTerm>");
			Location location = new Location(signature, (Value[]) arguments.toArray(new Value[arguments.size()]));
			checkCompatibility(content,location);
			updateSet.putUpdate(location, content);
		} else if (lhsTerm instanceof VariableTerm) {
			// FIXME experimental!!
			VariableTerm variable = (VariableTerm) lhsTerm;
			termEval.assignment.put(variable, content);
			// throw new UnsupportedOperationException();
		} else {
			throw new RuntimeException("Unknown left-hand-side term " + lhsTerm.getClass());
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</UpdateRule>");
		return updateSet;
	}

	/**
	 * Check compatibility.
	 *
	 * @param content the content to be assign to the location
	 * @param location the location
	 */
	// check the compatibility of content with location (i.e. content can be copied into location)
	private void checkCompatibility(Value content, Location location) {
		// if the conte is undef, it is correct in any case - undef can be assigned to any domain
		if (content instanceof UndefValue)
			return; 
		Domain codomain = location.getSignature().getCodomain();
		if (codomain instanceof ConcreteDomain) {
			ConcreteDomain concreteDomain = ((ConcreteDomain)codomain);
			// get the values in the domain (should work both static and dynamic)
			SetValue values = termEval.getValues(concreteDomain);
			if (!values.getValue().stream().anyMatch(x -> x.equals(content)))
				throw new InvalidValueException(content,location);
		}
	}

	/**
	 * Evaluates a conditional rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(ConditionalRule condRule) {
		logger.debug("<ConditionalRule>");
		UpdateSet updateSet;
		BooleanValue guardValue = evalGuard(condRule);
		if (guardValue == BooleanValue.TRUE) {
			logger.debug("<ThenRule>");
			updateSet = visit(condRule.getThenRule());
			logger.debug("</ThenRule>");
		} else {
			if (condRule.getElseRule() != null) {
				// there is an else clause
				logger.debug("<ElseRule>");
				updateSet = visit(condRule.getElseRule());
				logger.debug("</ElseRule>");
			} else {
				updateSet = new UpdateSet();
			}
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</ConditionalRule>");
		return updateSet;
	}

	// used by condittional rule to evaluate the guard
	protected BooleanValue evalGuard(ConditionalRule condRule) {
		logger.debug("<Guard>");
		Value value = visitTerm(condRule.getGuard());
		assert value instanceof BooleanValue : value + "\n" + AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(condRule.getGuard());
		// if undef launch an execption
		if (value instanceof UndefValue) 
			throw new RuntimeException(AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(condRule.getGuard()) + " is undef");
		BooleanValue guardValue = (BooleanValue) value;
		logger.debug("</Guard>");
		return guardValue;
	}

	/**
	 * Evaluates a switch rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(CaseRule caseRule) {
		logger.debug("<CaseRule>");
		UpdateSet updateset = null;
		logger.debug("<ComparedTerm>");
		Value comparedValue = visitTerm(caseRule.getTerm());
		// Hook method for RuleEvalWCov
		checkComparedValue(caseRule, comparedValue);
		logger.debug("</ComparedTerm>");
		Iterator<Rule> branchRuleIt = caseRule.getCaseBranches().iterator();
		for (Term comparingTerm : caseRule.getCaseTerm()) {
			Rule branchRule = branchRuleIt.next();
			logger.debug("<ComparingTerm>");
			Value comparingValue = visitTerm(comparingTerm);
			logger.debug("</ComparingTerm>");
			if (comparedValue.equals(comparingValue)) {
				logger.debug("<BranchRule>");
				updateset = visit(branchRule);
				logger.debug("</BranchRule>");
				break;
			}
		}
		if (updateset == null) {
			if (caseRule.getOtherwiseBranch() != null) {
				logger.debug("<OtherwiseRule>");
				updateset = visit(caseRule.getOtherwiseBranch());
				logger.debug("</OtherwiseRule>");
			} else {
				updateset = new UpdateSet();
			}
		}
		logger.debug("<UpdateSet>" + updateset + "</UpdateSet>");
		logger.debug("</CaseRule>");
		return updateset;
	}
	
	// Hook method for RuleEvalWCov
	protected void checkComparedValue(CaseRule caseRule, Value comparedValue) {
		// do no additional operations by default
	}


	/**
	 * Evaluates a block rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(BlockRule blockRule) {
		logger.debug("<BlockRule>");
		UpdateSet updateSet = new UpdateSet();
		Collection<Rule> ruleCollection = blockRule.getRules();
		for (Rule nextRule : ruleCollection) {
			UpdateSet nextUpdateSet = visit(nextRule);
			updateSet.union(nextUpdateSet);
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</BlockRule>");
		return updateSet;
	}

	/**
	 * Evaluates a sequential rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(SeqRule seqRule) {
		logger.debug("<SeqRule>");
		Collection<Rule> ruleCollection = seqRule.getRules();
		// the resulting update set of the seq
		UpdateSet updateSet = new UpdateSet();
		UpdateSet nextUpdateSet = null;
		State nextState = new State(termEval.state);
		for (Rule nextRule : ruleCollection) {
			// apply the changes of the last visited rule to get the new state
			if (nextUpdateSet != null) {
				// apply all the changes so far
				nextState.fireUpdates(nextUpdateSet);
				// all the value changes are committed, clear them
				// nextUpdateSet.resetValueChanges();
			}
			RuleEvaluator newRuleEvaluator = createRuleEvaluator(nextState,termEval.environment,termEval.assignment);
			nextUpdateSet = newRuleEvaluator.visit(nextRule);
			updateSet.merge(nextUpdateSet);
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</SeqRule>");

		return updateSet;
	}

	/**
	 * Evaluates an IterativeWhileRule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	public UpdateSet visit(IterativeWhileRule iterativeWhileRule) {
		logger.debug("<IterativeWhileRule>");
		Rule rule = iterativeWhileRule.getRule();
		Term guard = iterativeWhileRule.getGuard();
		UpdateSet updateSet = new UpdateSet();
		UpdateSet nextUpdateSet = null;
		BooleanValue guardValue = (BooleanValue) termEval.visit(guard);
		State nextState = new State(termEval.state);
		// TODO controllare che sia corretto
		while (guardValue.getValue()) {
			RuleEvaluator newRuleEvaluator = createRuleEvaluator(nextState,termEval.environment,termEval.assignment);
			nextUpdateSet = newRuleEvaluator.visit(rule);
			updateSet.merge(nextUpdateSet);
			// PA 2014/01/16: il seguente if prima era all'inizio del ciclo.
			// e' stato spostato qui
			if (nextUpdateSet != null) {
				nextState.fireUpdates(nextUpdateSet);
			}
			guardValue = (BooleanValue) newRuleEvaluator.termEval.visit(guard);
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</IterativeWhileRule>");
		return updateSet;
	}

	/**
	 * Evaluates a let rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(LetRule letRule) {
		logger.debug("<LetRule>");
		Collection<VariableTerm> varCollection = letRule.getVariable();
		Iterator<Term> initTermIterator = letRule.getInitExpression().iterator();
		ValueAssignment newAssignment = new ValueAssignment(termEval.assignment);
		TermEvaluator newTermEvaluator = new TermEvaluator(termEval.state, termEval.environment, newAssignment);
		logger.debug("<InitList>");
		for (VariableTerm var : varCollection) {
			logger.debug("<VariableTerm name=\"" + var.getName() + "\">");
			Term initTerm = initTermIterator.next();
			Value initValue = newTermEvaluator.visit(initTerm);
			newAssignment.put(var, initValue);
			logger.debug("<Value>" + initValue + "</Value>");
			logger.debug("</VariableTerm>");
			// Hook method for RuleEvalWCov
			checkInitTerm(letRule, initValue, initTerm);
		}
		afterInitExpressionVisit(letRule);
		logger.debug("</InitList>");
		logger.debug("<InRule>");
		RuleEvaluator newRuleEvaluator = createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
		UpdateSet updateSet = newRuleEvaluator.visit(letRule.getInRule());
		logger.debug("</InRule>");
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</LetRule>");
		return updateSet;
	}
	
	// Hook method for RuleEvalWCov
	protected void checkInitTerm(LetRule letRule, Value initValue, Term initTerm) {
		// do no additional operations by default
	}
	
	// Hook method for RuleEvalWCov
	protected void afterInitExpressionVisit(LetRule letRule) {
		// do no additional operations by default
	}

	/**
	 * Evaluates a forall rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(ForallRule forRule) {
		logger.debug("<ForallRule>");
		UpdateSet updateSet = new UpdateSet();
		Value[] boundValues = new Value[forRule.getVariable().size()];
		CollectionValue[] domains = evaluateRanges(forRule.getRanges());
		visitForall(0, domains, boundValues, forRule, updateSet);
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</ForallRule>");
		return updateSet;
	}

	/**
	 * Valuta una <i>ForallRule</i>.<br>
	 * L'algoritmo di valutazione e' il seguente:<br>
	 * per ogni valore v1 appartenente al dominio associato alla variabile 1<br>
	 * per ogni valore v2 appartenente al dominio associato alla variabile 2<br>
	 * ...<br>
	 * per ogni valore vn appartenente al dominio associato alla variabile n<br>
	 * se (v1...vn) soddisfano la guardia, fai quello che devi fare <br>
	 * <br>
	 * Poiche' pero' il numero della variabili non e' noto a priori, uso un
	 * algoritmo ricorsivo che nasconde un po' l'idea di partenza.
	 * 
	 * @param varIndex
	 *            indice della variabile a cui assegnare un valore appartenente al
	 *            dominio associato
	 * @param domains
	 *            domini su cui variano le variabili logiche della regola
	 * @param boundValues
	 *            array che contiene i valori delle variabili gia' fissate
	 * @param forRule
	 *            regola da valutare
	 * @param updateSet
	 *            update set prodotto dalla valutazione. La costruzione avviene in
	 *            modo incrementale.
	 */
	private void visitForall(int varIndex, CollectionValue<?>[] domains, Value<?>[] boundValues, ForallRule forRule,
			UpdateSet updateSet) {
		if (varIndex < domains.length) {
			CollectionValue<?> currentDomain = domains[varIndex];
			for (Value<?> elem : currentDomain) {
				boundValues[varIndex] = elem;
				visitForall(varIndex + 1, domains, boundValues, forRule, updateSet);
			}
		} else {
			ValueAssignment newAssignment = new ValueAssignment(termEval.assignment);
			newAssignment.put(forRule.getVariable(), boundValues);
			RuleEvaluator newEvaluator = createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
			logger.debug("<Guard>");
			BooleanValue guard = (BooleanValue) newEvaluator.visitTerm(forRule.getGuard());
			logger.debug("</Guard>");
			if (guard.getValue()) {
				logger.debug("<DoRule>");
				UpdateSet newSet = newEvaluator.visit(forRule.getDoRule());
				updateSet.union(newSet);
				logger.debug("</DoRule>");
				onForallGuardTrue(); // Hook method for RuleEvalWCov
			}
		}
	}
	
	// Hook method for RuleEvalWCov
	protected void onForallGuardTrue() {
		// do no additional operations by default
	}

	/**
	 * Evaluates a choose rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(ChooseRule chooseRule) {
		logger.debug("<ChooseRule>");
		// set the initial empty UpdateSet
		UpdateSet updateSet = new UpdateSet();
		// the variable content
		Value[] boundValues = new Value[chooseRule.getVariable().size()];
		CollectionValue[] domains = evaluateRanges(chooseRule.getRanges());
		if (!visitChoose(0, domains, boundValues, chooseRule, updateSet)) {
			onChooseGuardAlwaysFalse(chooseRule); // Hook method for RuleEvalWCov
			if (chooseRule.getIfnone() != null) {
				logger.debug("<IfnoneRule>");
				updateSet = visit(chooseRule.getIfnone());
				logger.debug("</IfnoneRule>");
			}
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</ChooseRule>");
		return updateSet;
	}

	/**
	 * Valuta una <i>ChooseRule</i>.<br>
	 * NOTA: vedi la documentazione relativa al metodo <i>visit(ForallRule)</i>.
	 * 
	 * @param varIndex
	 *            indice della variabile a cui assegnare un valore appartenente al
	 *            dominio associato
	 * @param domains
	 *            domini su cui variano le variabili logiche della regola
	 * @param boundContent
	 *            array che contiene i valori delle variabili gia' fissate
	 * @param chooseRule
	 *            regola da valutare
	 * @param updateSet
	 *            update set prodotto dalla valutazione. La costruzione avviene in
	 *            modo incrementale.
	 * 
	 * @return true se e' riuscito a fissare la varIndex variable ad un valore
	 */
	private boolean visitChoose(int varIndex, CollectionValue[] domains, Value[] boundContent, ChooseRule chooseRule,
			UpdateSet updateSet) {
		if (varIndex < domains.length) {
			CollectionValue currentDomain = domains[varIndex];
			Iterator<Value> values = currentDomain.iterator();
			// shuffle stuff
			Object o = currentDomain.getValue();
			if (isShuffled) {
				logger.debug("interating randomly");
				// TODO da sistemare un po' AG
				if (o instanceof Collection<?>)
					values = new RandomIterator<Value>((Collection<Value>) o, RAND);
				else
					logger.debug("interating randomly not possible");
			}
			while (values.hasNext()) {
				Value elem = values.next();
				// fix the element with index varIndex and consider the next
				// variable
				boundContent[varIndex] = elem;
				logger.debug(
						"fixing var " + chooseRule.getVariable().get(varIndex).getName() + " to " + elem.toString());

				// fix the i +1 th variable
				if (visitChoose(varIndex + 1, domains, boundContent, chooseRule, updateSet)) {
					return true;
				}
				// if false, take the next value for variable
			}
			// logger.debug("no value for var "
			// + ((VariableTerm) chooseRule.getVariable().get(varIndex))
			// .getName());
			return false;
		} else {
			// all variables are fixed
			ValueAssignment newAssignment = new ValueAssignment(termEval.assignment);
			newAssignment.put(chooseRule.getVariable(), boundContent);
			RuleEvaluator newEvaluator = createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
			logger.debug("<Guard>");
			BooleanValue guard = null;
			Term guardTerm = chooseRule.getGuard();
			if (guardTerm != null) {				
				Value termValue = newEvaluator.visitTerm(guardTerm);
				if (termValue instanceof UndefValue)
					throw new RuntimeException("Guard "+ printer.visit(guardTerm) +"is undef");
				guard = (BooleanValue) termValue;
			}
			logger.debug("</Guard>");
			if (guard != null && guard.getValue()) {
				logger.debug("<DoRule>");
				UpdateSet newSet = newEvaluator.visit(chooseRule.getDoRule());
				updateSet.union(newSet);
				logger.debug("</DoRule>");
				onChooseGuardTrue(chooseRule); // Hook method for RuleEvalWCov
			}
			return guard.getValue();
		}
	}
	
	// Hook method for RuleEvalWCov
	protected void onChooseGuardTrue(ChooseRule chooseRule) {
		// do no additional operations by default
	}
	
	// Hook method for RuleEvalWCov
	protected void onChooseGuardAlwaysFalse(ChooseRule chooseRule) {
		// do no additional operations by default
	}

	/**
	 * Evaluates a macro call rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 * @throws Exception
	 */
	@Override
	public UpdateSet visit(MacroCallRule macroRule) throws NotCompatibleDomainsException {
		MacroDeclaration dcl = macroRule.getCalledMacro();
		logger.debug("<MacroCallRule name=\"" + dcl.getName() + "\">");
		List<Term> actualParameters = macroRule.getParameters();

		// PA 10/11/2011 - Inizio
		Rule rule1 = macroRule.getCalledMacro().getRuleBody();
		Iterator<VariableTerm> formalParameters = macroRule.getCalledMacro().getVariable().iterator();
		for (Term actualParameter : actualParameters) {
			Domain actualParameterDomain = actualParameter.getDomain();
			Domain formalDomain = formalParameters.next().getDomain();
			// TODO Il lancio dell'eccezione dovrebbe essere ripristinato.
			// Pero' ora lancia anche l'eccezione quando non dovrebbe.
			// Vedi la simulazione con roulette.asm.
			/*
			 * if(!compatible(actualParameterDomain, formalDomain)) { throw new
			 * NotCompatibleDomainsException(actualParameterDomain, formalDomain); }
			 */
		}
		UpdateSet updates = visit(dcl, actualParameters);
		logger.debug("</MacroCallRule>");
		return updates;
	}

	// PA 14/11/2011
	public static boolean compatible(Domain actual, Domain formal) {
		if ((actual == formal) || (formal instanceof UndefDomain) || (actual instanceof UndefDomain)
				|| ((formal instanceof AnyDomain) && formal.getName().equals("Any"))
				|| ((actual instanceof AnyDomain) && actual.getName().equals("Any")))
			return true;
		if (actual instanceof ConcreteDomain && formal instanceof TypeDomain) {
			return compatible(formal, ((ConcreteDomain) actual).getTypeDomain());
		}
		if (actual instanceof PowersetDomain && formal instanceof PowersetDomain)
			return compatible(((PowersetDomain) actual).getBaseDomain(), ((PowersetDomain) formal).getBaseDomain());
		if ((actual instanceof SequenceDomain) && (formal instanceof SequenceDomain)
				&& compatible(((SequenceDomain) actual).getDomain(), ((SequenceDomain) formal).getDomain()))
			return true;
		if ((actual instanceof BagDomain) && (formal instanceof BagDomain)
				&& compatible(((BagDomain) actual).getDomain(), ((BagDomain) formal).getDomain()))
			return true;
		if ((actual instanceof MapDomain) && (formal instanceof MapDomain)
				&& compatible(((MapDomain) actual).getSourceDomain(), ((MapDomain) formal).getSourceDomain())
				&& compatible(((MapDomain) actual).getTargetDomain(), ((MapDomain) formal).getTargetDomain()))
			return true;
		if (actual.getName().equals(formal.getName()))
			return true;
		return false;
	}

	/**
	 * Evaluates a turbo call rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	public UpdateSet visit(TurboCallRule turboRule) {
		RuleDeclaration dcl = turboRule.getCalledRule();
		logger.debug("<TurboCallRule name=\"" + dcl.getName() + "\">");
		List<Term> arguments = turboRule.getParameters();
		Value[] values = visit(arguments);
		EList<VariableTerm> vars = dcl.getVariable();
		ValueAssignment newAssignment = new ValueAssignment();
		newAssignment.put(vars, values);
		RuleEvaluator newEval = createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
		UpdateSet updates = newEval.visit(dcl.getRuleBody());
		logger.debug("</TurboCallRule>");
		return updates;
	}

	public UpdateSet visit(TurboLocalStateRule rule) {
		System.out.println(rule);
		return null;
	}

	Value[] visit(List<Term> terms) {
		Value[] values = new Value[terms.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = visitTerm(terms.get(i));
		}
		return values;
	}

	Value getResult(UpdateSet updates) {
		for (Entry<Location, Value> entry : updates) {
			Location loc = entry.getKey();
			Function signature = loc.getSignature();
			// search the 0-ary function result of the Standard Library
			if (signature.getName().equals("result") && signature.getArity() == 0
					&& Defs.getAsmName(signature).equals("StandardLibrary")) {
				return entry.getValue();
			}
		}
		return null;
	}

	public UpdateSet visit(TurboReturnRule retRule) {
		logger.debug("<TurboReturnRule>");
		logger.debug("<UpdatingTerm>");
		TurboCallRule turboRule = retRule.getUpdateRule();
		UpdateSet updateSet = visit(turboRule);
		Value content = getResult(updateSet);
		logger.debug("</UpdatingTerm>");
		Term lhsTerm = retRule.getLocation();
		if (lhsTerm instanceof LocationTerm) {
			logger.debug("<LocationTerm>");
			LocationTerm locationTerm = (LocationTerm) lhsTerm;
			Function signature = locationTerm.getFunction();
			logger.debug("<Name>" + signature.getName() + "</Name>");
			TupleTerm tupleTerm = locationTerm.getArguments();
			Value[] arguments;
			if (tupleTerm != null) {
				//assert tupleTerm.getTerms().size() == tupleTerm.getArity();
				arguments = ((TupleValue) visitTerm(tupleTerm)).getValueAsArray();
			} else {
				arguments = new Value[0];
			}
			logger.debug("</LocationTerm>");
			Location location = new Location(signature, arguments);
			updateSet.putUpdate(location, content);
		} else if (lhsTerm instanceof VariableTerm) {
			// FIXME experimental!!
			VariableTerm variable = (VariableTerm) lhsTerm;
			termEval.assignment.put(variable, content);
			// throw new UnsupportedOperationException();
		} else {
			throw new RuntimeException("Unknown left-hand-side term " + lhsTerm.getClass());
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</TurboReturnRule>");
		return updateSet;
	}

	/**
	 * Evaluates a rule given the declaration and the arguments.
	 * 
	 * @param dcl
	 *            rule's declaration
	 * @param arguments
	 *            arguments
	 * @return the rule's update set
	 */
	public UpdateSet visit(RuleDeclaration dcl, List<Term> arguments) {
		List<VariableTerm> variables = dcl.getVariable();
		UpdateSet updateSet = null;
		Rule body = dcl.getRuleBody();
		if (dcl.getVariable().size() != arguments.size()) {
			throw new RuntimeException("The number of arguments of the "
					+ "term as rule doesn't match the number of arguments of " + "the associated rule as term");
		}
		// FINDBUGS: A value is checked here to see whether it is null, but this
		// value can't be null because it was previously dereferenced and if it
		// were null a null pointer exception would have occurred at the earlier
		// dereference. Essentially, this code and the previous dereference disagree
		// as to whether this value is allowed to be null. Either the check is
		// redundant or the previous dereference is erroneous.
		// if (arguments == null || arguments.size() == 0) {
		if (arguments.size() == 0) {
			// macro without parameters: not need to perform substitution
			updateSet = visit(body);
		} else {
			String signature = Defs.getAsmName(dcl) + "::" + dcl.getName() + printer.visit(arguments, "[", "]");
			Rule newRule = buildNewRule(arguments, variables, body, signature);
			updateSet = visit(newRule);
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		return updateSet;
	}

	protected Rule buildNewRule(List<Term> arguments, List<VariableTerm> variables, Rule body, String signature) {
		Rule newRule = macros.get(signature);
		if (newRule == null) {
			logger.debug("<Substitution>");
			TermAssignment macroAssignment = new TermAssignment();
			macroAssignment.put(variables, arguments);
			RuleSubstitution substitution = new RuleSubstitution(macroAssignment,TermSubstitution.ruleFactory);
			newRule = substitution.visit(body);
			logger.debug("</Substitution>");
			macros.put(signature, newRule);
		}
		return newRule;
	}

	/**
	 * Evaluates an extend rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(ExtendRule extendRule) {
		logger.debug("<ExtendRule>");
		UpdateSet updateSet = new UpdateSet();
		ValueAssignment newAssignment = new ValueAssignment(termEval.assignment);
		Domain dom = extendRule.getExtendedDomain();
		// Originally only AbstractDomains could be extended.
		// Now every domain can be extended
		// // AbstractTd abstractTd = (AbstractTd) (dom instanceof AbstractTd ? dom
		// // : ((ConcreteDomain)dom).getTypeDomain());
		// if (!(dom instanceof AbstractTd)) {
		// throw new UnsupportedOperationException(
		// "Only abstract domain are extended - for now");
		// }
		// AbstractTd domain = (AbstractTd) dom;
		Collection<VariableTerm> varList = extendRule.getBoundVar();
		for (VariableTerm var : varList) {
			ReserveValue newValue = ReserveValue.getFromReserve(dom);
			updateSet.add(dom, newValue);
			newAssignment.put(var, newValue);
		}
		RuleEvaluator newEvaluator = createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
		UpdateSet doSet = newEvaluator.visit(extendRule.getDoRule());
		updateSet.union(doSet);
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</ExtendRule>");
		return updateSet;
	}

	/**
	 * Evaluates a term as a rule.
	 * 
	 * @param rule
	 *            a rule
	 * @return the rule's update set
	 */
	@Override
	public UpdateSet visit(TermAsRule termAsRule) {
		logger.debug("<TermAsRule>");
		Term term = termAsRule.getTerm();
		/*
		 * if (term instanceof FunctionTerm) { FunctionTerm fun = (FunctionTerm) term;
		 * if (fun.getFunction().getName().equals("program")) { TupleValue tuple =
		 * visit(fun.getArguments()); AgentValue agent = (AgentValue)
		 * tuple.getValue()[0]; MacroDeclaration program = ((MacroCallRule)
		 * agent.getProgram()).getCalledMacro(); List<Term> arguments =
		 * fun.getArguments().getTerms(); UpdateSet updateSet = visit(program,
		 * arguments); logger.debug("</TermAsRule>"); return updateSet; } }
		 */
		RuleValue ruleValue = (RuleValue) visitTerm(term);
		AgentValue agent = ruleValue.getAgent();
		// if agent is null, assume that it is self agent 
		// with as program the current rule which is evaluated 
		if (agent == null) {
			Domain selfvalueDomain = TermEvaluator.self.getSignature().getCodomain();
			agent = new AgentValue(TermEvaluator.self.getName(),selfvalueDomain, termAsRule);
		}
		RuleDeclaration dcl = ruleValue.getRule();
		List<Term> arguments = termAsRule.getParameters();
		// set the self location
		termEval.state.applyLocationUpdate(TermEvaluator.self, agent);
		UpdateSet updateSet = visit(dcl, arguments);
		logger.debug("</TermAsRule>");
		return updateSet;
	}

	/**
	 * Evaluates a list of domain terms.
	 * 
	 * @param domains
	 *            domain terms
	 * @return an array of CollectionValue
	 */
	private CollectionValue[] evaluateRanges(List<Term> domains) {
		CollectionValue[] values = new CollectionValue[domains.size()];
		logger.debug("<Domains total=\"" + domains.size() + "\">");
		for (int i = 0; i < domains.size(); i++) {
			Term domain = domains.get(i);
			// VariableTerm var = (VariableTerm) varList.get(i);
			values[i] = (CollectionValue) visitTerm(domain);
		}
		logger.debug("</Domains>");
		return values;
	}

	/** make a new rule evaluator similar to this but with a different parts
	 * 
	 * @param state
	 * @param environment
	 * @param assignment
	 * @return
	 */
	protected RuleEvaluator createRuleEvaluator(State state, Environment environment, ValueAssignment assignment) {
		return new RuleEvaluator(state, environment, assignment);
	}

}


