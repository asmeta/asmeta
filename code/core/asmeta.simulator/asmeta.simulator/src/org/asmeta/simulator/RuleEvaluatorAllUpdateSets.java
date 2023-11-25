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

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.value.AgentValue;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CollectionValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.RuleValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.Value;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
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
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

/**
 * Provides methods to evaluate rules.
 * 
 */
public class RuleEvaluatorAllUpdateSets extends RuleVisitor<SetUpdateSet> {
	//private static final Random RAND = new Random();
	public static boolean COMPUTE_COVERAGE = false;
	public static Logger logger = Logger.getLogger(RuleEvaluatorAllUpdateSets.class);

	/**
	 * Shuffles the elements in the choose rule.
	 * 
	 */
	//static boolean isShuffled = false;
	
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

	// covered macros
	// FIXME: l'uso di static is due to the fact that several RuleEvaluator 
	// are created for the same run;
	private static Collection<MacroDeclaration> coveredMacros;

	public TermEvaluator termEval;

	/**
	 * Constructs an evaluator: reuses the covere dmacros
	 * 
	 * @param state state
	 * @param environment environment
	 * @param assignment assignment
	 */
	protected RuleEvaluatorAllUpdateSets(State state, 
			Environment environment,
			ValueAssignment assignment) {
		termEval = new TermEvaluator(state, environment, assignment);
	}

	/**
	 * Constructs a new fresh evaluator.
	 * 
	 * @param state state
	 * @param environment environment
	 * @param factory factory
	 */
	public RuleEvaluatorAllUpdateSets(State state, 
			Environment environment,
			RuleFactory factory) {
		this(state, environment, new ValueAssignment());
		TermSubstitution.ruleFactory = factory;
		coveredMacros = new HashSet<MacroDeclaration>();
	}

	
	
	/**
	 * Evaluates a rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	/*public SetUpdateSet visit(Rule rule) {
		//return (Set<UpdateSet>) visit((Object) rule);
		return visit((Object) rule);
	}*/

	/**
	 * Evaluates a skip rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(SkipRule rule) {
		logger.debug("<SkipRule>");
		logger.debug("<UpdateSet>{}</UpdateSet>");
		logger.debug("</SkipRule>");
		return new SetUpdateSet(new UpdateSet());
	}

	/**
	 * Evaluates an update rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(UpdateRule rule) {
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
			logger.debug("<Arguments>" +  tupleTerm + "</Arguments>");
			Value[] arguments;
			// if tuple term has no arguments (plain variable), then build an empty value array 
			if (tupleTerm == null){
				arguments = new Value[0];
			} else {
				//assert tupleTerm.getTerms().size() == tupleTerm.getArity();
				arguments = ((TupleValue)visitTerm(tupleTerm)).getValueAsArray();
			}
			logger.debug("</LocationTerm>");
			Location location = new Location(signature, arguments);
			updateSet.putUpdate(location, content);
		} else if (lhsTerm instanceof VariableTerm) {
			// FIXME experimental!!
			VariableTerm variable = (VariableTerm) lhsTerm;
			termEval.assignment.put(variable, content);
//			throw new UnsupportedOperationException();
		} else {
			throw new RuntimeException("Unknown left-hand-side term " + lhsTerm.getClass());
		}
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</UpdateRule>");
		return new SetUpdateSet(updateSet);
	}

	/**
	 * Evaluates a conditional rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(ConditionalRule condRule) {
		logger.debug("<ConditionalRule>");
		SetUpdateSet setUpdateSets;
		logger.debug("<Guard>");
		BooleanValue guardValue = (BooleanValue) visitTerm(condRule.getGuard());
		logger.debug("</Guard>");
		if (guardValue.getValue()) {
			logger.debug("<ThenRule>");
			setUpdateSets = visit(condRule.getThenRule());
			logger.debug("</ThenRule>");
		} else {
			if (condRule.getElseRule() != null) {
				// there is an else clause
				logger.debug("<ElseRule>");
				setUpdateSets = visit(condRule.getElseRule());
				logger.debug("</ElseRule>");
			} else {
				setUpdateSets = new SetUpdateSet(new UpdateSet());
			}
		}
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</ConditionalRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a switch rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(CaseRule caseRule) {
		logger.debug("<CaseRule>");
		SetUpdateSet setUpdateSets = null;
		logger.debug("<ComparedTerm>");
		Value comparedValue = visitTerm(caseRule.getTerm());
		logger.debug("</ComparedTerm>");
		Iterator<Rule> branchRuleIt = caseRule.getCaseBranches().iterator();
		for (Term comparingTerm: caseRule.getCaseTerm()) {
			Rule branchRule = branchRuleIt.next();
			logger.debug("<ComparingTerm>");
			Value comparingValue = visitTerm(comparingTerm);
			logger.debug("</ComparingTerm>");
			if (comparedValue.equals(comparingValue)) {
				logger.debug("<BranchRule>");
				setUpdateSets = visit(branchRule);
				logger.debug("</BranchRule>");
				break;
			}
		}
		if (setUpdateSets == null) {
			if (caseRule.getOtherwiseBranch() != null) {
				logger.debug("<OtherwiseRule>");
				setUpdateSets = visit(caseRule.getOtherwiseBranch());
				logger.debug("</OtherwiseRule>");
			} else {
				setUpdateSets = new SetUpdateSet(new UpdateSet());
			}
		}
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</CaseRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a block rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(BlockRule blockRule) {
		logger.debug("<BlockRule>");
		SetUpdateSet setUpdateSets = new SetUpdateSet(new UpdateSet());
		Collection<Rule> ruleCollection = blockRule.getRules();
		for (Rule nextRule: ruleCollection) {
			SetUpdateSet nextUpdateSets = visit(nextRule);
			SetUpdateSet newUpdateSets = new SetUpdateSet();
			for(UpdateSet nextUpdateSet: nextUpdateSets) {
				for(UpdateSet oldUpdateSet: setUpdateSets) {
					UpdateSet us = new UpdateSet();
					us.union(nextUpdateSet);
					us.union(oldUpdateSet);
					newUpdateSets.add(us);
				}
			}
			setUpdateSets = newUpdateSets;
		}
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</BlockRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a sequential rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	//DA COMPLETARE
	@Override
	public SetUpdateSet visit(SeqRule seqRule) {
		//return null;
		logger.debug("<SeqRule>");
		Collection<Rule> ruleCollection = seqRule.getRules();
		// the resulting update sets of the seq rule
		SetUpdateSet setUpdateSets = new SetUpdateSet();
		//UpdateSet nextUpdateSet = null;
		SetUpdateSet nextUpdateSets = null;
		State nextState = new State(termEval.state);
		State oldState = new State(termEval.state);
		for (Rule nextRule : ruleCollection) {
			// apply the changes of the last visited rule to get the new state
			if (nextUpdateSets != null) {
				for(UpdateSet nextUpdateSet: nextUpdateSets) {
					State tempState = new State(oldState);
					tempState.fireUpdates(nextUpdateSet);
				}
			}
			RuleEvaluatorAllUpdateSets newRuleEvaluator = new RuleEvaluatorAllUpdateSets(nextState, termEval.environment, termEval.assignment);
			nextUpdateSets = newRuleEvaluator.visit(nextRule);			
			setUpdateSets.merge(nextUpdateSets);
		}
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</SeqRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a let rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(LetRule letRule) {
		logger.debug("<LetRule>");
		Collection<VariableTerm> varCollection = letRule.getVariable();
		Iterator<Term> initTermIterator = letRule.getInitExpression().iterator();
		ValueAssignment newAssignment = 
			new ValueAssignment(termEval.assignment);
		TermEvaluator newTermEvaluator = new TermEvaluator(termEval.state,
				termEval.environment, newAssignment);
		logger.debug("<InitList>");
		for (VariableTerm var : varCollection) {
			logger.debug("<VariableTerm name=\"" + var.getName() + "\">");
			Term initTerm = initTermIterator.next();
			Value initValue = newTermEvaluator.visit(initTerm);
			newAssignment.put(var, initValue);
			logger.debug("<Value>" + initValue + "</Value>");	
			logger.debug("</VariableTerm>");
		}
		logger.debug("</InitList>");
		logger.debug("<InRule>");
		RuleEvaluatorAllUpdateSets newRuleEvaluator = new RuleEvaluatorAllUpdateSets(termEval.state,
				termEval.environment, newAssignment);
		SetUpdateSet setUpdateSets = newRuleEvaluator.visit(letRule.getInRule());
		logger.debug("</InRule>");
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</LetRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a forall rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(ForallRule forRule) {
		logger.debug("<ForallRule>");
		SetUpdateSet setUpdateSets = new SetUpdateSet(new UpdateSet());
		Value[] boundValues = new Value[forRule.getVariable().size()];
		CollectionValue[] domains = evaluateRanges(forRule.getRanges());
		visitForall(0, domains, boundValues, forRule, setUpdateSets);
		logger.debug("<SetUpdateSets>" + setUpdateSets + "</SetUpdateSets>");
		logger.debug("</ForallRule>");
		return setUpdateSets;
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
	 * Poiche' pere' il numero della variabili non e' noto a priori, uso un
	 * algoritmo ricorsivo che nasconde un po' l'idea di partenza.
	 * 
	 * @param varIndex
	 *            indice della variabile a cui assegnare un valore appartenente
	 *            al dominio associato
	 * @param domains
	 *            domini su cui variano le variabili logiche della regola
	 * @param boundValues
	 *            array che contiene i valori delle variabili gia' fissate
	 * @param forRule
	 *            regola da valutare
	 * @param setUpdateSets
	 *            update set prodotto dalla valutazione. La costruzione avviene
	 *            in modo incrementale.
	 */
	private void visitForall(int varIndex, CollectionValue[] domains,
			Value[] boundValues, ForallRule forRule,
			SetUpdateSet setUpdateSets) {
		if (varIndex < domains.length) {
			CollectionValue<?> currentDomain = domains[varIndex];
			for (Value elem : currentDomain) {
				boundValues[varIndex] = elem;
				visitForall(varIndex + 1, domains, boundValues, forRule, setUpdateSets);
			}
		} else {
			ValueAssignment newAssignment = 
				new ValueAssignment(termEval.assignment);
			newAssignment.put(forRule.getVariable(), boundValues);
			RuleEvaluatorAllUpdateSets newEvaluator = 
				new RuleEvaluatorAllUpdateSets(termEval.state, termEval.environment, newAssignment);
			logger.debug("<Guard>");
			BooleanValue guard = 
				(BooleanValue) newEvaluator.visitTerm(forRule.getGuard());
			logger.debug("</Guard>");
			if (guard.getValue()) {
				SetUpdateSet doRuleUpdateSets = newEvaluator.visit(forRule.getDoRule());
				for(UpdateSet oldUpsdateSet: setUpdateSets) {
					for(UpdateSet doRuleUpdateSet: doRuleUpdateSets) {
						oldUpsdateSet.union(doRuleUpdateSet);
					}
				}
			}
		}
	}

	/**
	 * Evaluates a choose rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(ChooseRule chooseRule) {
		logger.debug("<ChooseRule>");
		SetUpdateSet setUpdateSets = new SetUpdateSet();
		// the variable content
		Value[] boundValues = new Value[chooseRule.getVariable().size()];
		CollectionValue[] domains = evaluateRanges(chooseRule.getRanges());
		visitChoose(0, domains, boundValues, chooseRule, setUpdateSets);
		if(setUpdateSets.size() == 0) {
			if (chooseRule.getIfnone() != null) {
				setUpdateSets = visit(chooseRule.getIfnone());
			}
			else {
				setUpdateSets.add(new UpdateSet());
			}
		}
		return setUpdateSets;
	}

	/**
	 * Valuta una <i>ChooseRule</i>.<br>
	 * NOTA: vedi la documentazione relativa al metodo <i>visit(ForallRule)</i>.
	 * 
	 * @param varIndex
	 *            indice della variabile a cui assegnare un valore appartenente
	 *            al dominio associato
	 * @param domains
	 *            domini su cui variano le variabili logiche della regola
	 * @param boundContent
	 *            array che contiene i valori delle variabili gia' fissate
	 * @param chooseRule
	 *            regola da valutare
	 * @param updateSet
	 *            update set prodotto dalla valutazione. La costruzione avviene
	 *            in modo incrementale.
	 * 
	 * @return true se e' riuscito a fissare la varIndex variable ad un valore
	 */
	private /*boolean*/void visitChoose(int varIndex,
			CollectionValue[] domains,
			Value[] boundContent, ChooseRule chooseRule,
			SetUpdateSet setUpdateSets) {
		if (varIndex < domains.length) {
			CollectionValue currentDomain = domains[varIndex];
			Iterator<Value> values =  currentDomain.iterator();
			while (values.hasNext()) {
 				Value elem = values.next();
				// fix the element with index varIndex and consider the next
				// variable
				boundContent[varIndex] = elem;
				visitChoose(varIndex + 1, domains, boundContent,
						chooseRule, setUpdateSets);
			}
		} else {
			ValueAssignment newAssignment = new ValueAssignment(termEval.assignment);
			newAssignment.put(chooseRule.getVariable(), boundContent);
			RuleEvaluator newEvaluator = RuleEvaluatorFactory.RULE_EVAL_FACT.createRuleEvaluator(termEval.state,termEval.environment,newAssignment);
			BooleanValue guard = null;
			if (chooseRule.getGuard() != null) {
				guard = (BooleanValue) newEvaluator.visitTerm(chooseRule.getGuard());
			}
			if (guard != null && guard.getValue()) {
				logger.debug("<DoRule>");
				RuleEvaluatorAllUpdateSets newEvaluatorAll = 
					new RuleEvaluatorAllUpdateSets(termEval.state, termEval.environment, newAssignment);
				SetUpdateSet doRuleUpdateSets = newEvaluatorAll.visit(chooseRule.getDoRule());
				setUpdateSets.add(doRuleUpdateSets);
				logger.debug("</DoRule>");
			}
		}
	}

	/**
	 * Evaluates a macro call rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(MacroCallRule macroRule) {
		MacroDeclaration dcl = macroRule.getCalledMacro();
		logger.debug("<MacroCallRule name=\"" + dcl.getName() + "\">");		
		List<Term> arguments = macroRule.getParameters();		
		SetUpdateSet updates = visit(dcl, arguments);
		// keep track of all the macro evaluated
		if (COMPUTE_COVERAGE) {
			coveredMacros.add(macroRule.getCalledMacro());
		}
		logger.debug("</MacroCallRule>");
		return updates;
	}
	
	/**
	 * Evaluates a turbo call rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	public SetUpdateSet visit(TurboCallRule turboRule) {
		RuleDeclaration dcl = turboRule.getCalledRule();
		logger.debug("<TurboCallRule name=\"" + dcl.getName() + "\">");
		List<Term> arguments = turboRule.getParameters();
		Value[] values = visit(arguments);
		EList<VariableTerm> vars = dcl.getVariable();
		ValueAssignment newAssignment = new ValueAssignment();
		newAssignment.put(vars, values);
		RuleEvaluatorAllUpdateSets newEval = new RuleEvaluatorAllUpdateSets(termEval.state, termEval.environment, newAssignment);
		SetUpdateSet setUpdateSets = newEval.visit(dcl.getRuleBody());
		logger.debug("</TurboCallRule>");
		return setUpdateSets;
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
			if (signature.getName().equals("result") 
					&& signature.getArity() == 0
					&& Defs.getAsmName(signature).equals("StandardLibrary")) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public SetUpdateSet visit(TurboReturnRule retRule) {
		logger.debug("<TurboReturnRule>");
		logger.debug("<UpdatingTerm>");
		TurboCallRule turboRule = retRule.getUpdateRule();
		SetUpdateSet setUpdateSets = visit(turboRule);
		for(UpdateSet updateSet: setUpdateSets) {
			Value content = getResult(updateSet);
			Term lhsTerm = retRule.getLocation();
			if (lhsTerm instanceof LocationTerm) {
				LocationTerm locationTerm = (LocationTerm) lhsTerm;
				Function signature = locationTerm.getFunction();			
				TupleTerm tupleTerm = locationTerm.getArguments();
				//assert tupleTerm == null || tupleTerm.getTerms().size() == tupleTerm.getArity();
				Value[] arguments = ((TupleValue)visitTerm(tupleTerm)).getValueAsArray();
				Location location = new Location(signature, arguments);
				updateSet.putUpdate(location, content);
			} else if (lhsTerm instanceof VariableTerm) {
				// FIXME experimental!!
				VariableTerm variable = (VariableTerm) lhsTerm;
				termEval.assignment.put(variable, content);
//				throw new UnsupportedOperationException();
			} else {
				throw new RuntimeException("Unknown left-hand-side term " + lhsTerm.getClass());
			}
			logger.debug("<SetUpdateSets>" + updateSet + "</SetUpdateSets>");
			logger.debug("</TurboReturnRule>");
		}
		return setUpdateSets;
	}
	
	/**
	 * Evaluates a rule given the declaration and the arguments.
	 * 
	 * @param dcl rule's declaration
	 * @param arguments arguments
	 * @return the rule's update set
	 */
	public SetUpdateSet visit(RuleDeclaration dcl, List<Term> arguments) {
		List<VariableTerm> variables = dcl.getVariable();
		SetUpdateSet setUpdateSets = null;
		Rule body = dcl.getRuleBody();
		if (dcl.getVariable().size() != arguments.size()) {
			throw new RuntimeException("The number of arguments of the " +
					"term as rule doesn't match the number of arguments of " +
					"the associated rule as term");
		}
		//FINDBUGS: A value is checked here to see whether it is null, but this
		//value can't be null because it was previously dereferenced and if it
		//were null a null pointer exception would have occurred at the earlier
		//dereference. Essentially, this code and the previous dereference disagree
		//as to whether this value is allowed to be null. Either the check is
		//redundant or the previous dereference is erroneous.
		//if (arguments == null || arguments.size() == 0) {
		if (arguments.size() == 0) {
			// macro without parameters: not need to perform substitution
			setUpdateSets = visit(body);
		} else {			
			String signature = Defs.getAsmName(dcl) + "::" + dcl.getName() 
				+ printer.visit(arguments, "[", "]");
			Rule newRule = macros.get(signature);
			if (newRule == null) {
				logger.debug("<Substitution>");
				TermAssignment macroAssignment = new TermAssignment();
				macroAssignment.put(variables, arguments);
				RuleSubstitution substitution = 
					new RuleSubstitution(macroAssignment,TermSubstitution.ruleFactory);
				newRule = substitution.visit(body);
				logger.debug("</Substitution>");
				macros.put(signature, newRule);
			}
			setUpdateSets = visit(newRule);
		}
		logger.debug("<UpdateSet>" + setUpdateSets + "</UpdateSet>");
		return setUpdateSets;
	}

	/**
	 * Evaluates an extend rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(ExtendRule extendRule) {
		logger.debug("<ExtendRule>");
		UpdateSet updateSet = new UpdateSet();
		ValueAssignment newAssignment = 
			new ValueAssignment(termEval.assignment);
		Domain dom = extendRule.getExtendedDomain();
//      Originally only AbstractDomains could be extended. 
//		Now every domain can be extended
//		// AbstractTd abstractTd = (AbstractTd) (dom instanceof AbstractTd ? dom
//		// : ((ConcreteDomain)dom).getTypeDomain());
//		if (!(dom instanceof AbstractTd)) {
//			throw new UnsupportedOperationException(
//					"Only abstract domain are extended - for now");
//		}
//		AbstractTd domain = (AbstractTd) dom;
		Collection<VariableTerm> varList = extendRule.getBoundVar();
		for (VariableTerm var : varList) {
			ReserveValue newValue = ReserveValue.getFromReserve(dom);
			updateSet.add(dom, newValue);
			newAssignment.put(var, newValue);
		}
		RuleEvaluatorAllUpdateSets newEvaluator = 
			new RuleEvaluatorAllUpdateSets(termEval.state, termEval.environment, newAssignment);
		SetUpdateSet doSets = newEvaluator.visit(extendRule.getDoRule());
		doSets.union(updateSet);
		logger.debug("<UpdateSet>" + updateSet + "</UpdateSet>");
		logger.debug("</ExtendRule>");
		return doSets;
	}

	/**
	 * Evaluates a term as a rule. 
	 * 
	 * @param rule a rule
	 * @return the rule's update set
	 */
	@Override
	public SetUpdateSet visit(TermAsRule termAsRule) {
		logger.debug("<TermAsRule>");
		Term term = termAsRule.getTerm();
		/*
		if (term instanceof FunctionTerm) {
			FunctionTerm fun = (FunctionTerm) term;
			if (fun.getFunction().getName().equals("program")) {
				TupleValue tuple = visit(fun.getArguments());
				AgentValue agent = (AgentValue) tuple.getValue()[0];
				MacroDeclaration program = ((MacroCallRule) agent.getProgram()).getCalledMacro();
				List<Term> arguments = fun.getArguments().getTerms();
				UpdateSet updateSet = visit(program, arguments);
				logger.debug("</TermAsRule>");
				return updateSet;
			}	
		}
		*/
		RuleValue ruleValue = (RuleValue) visitTerm(term);
		AgentValue agent = ruleValue.getAgent();
		RuleDeclaration dcl = ruleValue.getRule();
		List<Term> arguments = termAsRule.getParameters();
		// set the self location
		termEval.state.locationMap.put(TermEvaluator.self, agent);
		SetUpdateSet setUpdateSets = visit(dcl, arguments);
		logger.debug("</TermAsRule>");
		return setUpdateSets;
	}

	/**
	 * Evaluates a list of domain terms.
	 * 
	 * @param domains domain terms
	 * @return an array of CollectionValue
	 */
	private CollectionValue[] evaluateRanges(List<Term> domains) {
		CollectionValue[] values = new CollectionValue[domains.size()];
		logger.debug("<Domains total=\"" + domains.size() + "\">");
		for (int i = 0; i < domains.size(); i++) {			
			Term domain = domains.get(i);
//			VariableTerm var = (VariableTerm) varList.get(i);
			values[i] = (CollectionValue) visitTerm(domain);
		}
		logger.debug("</Domains>");
		return values;
	}

	/** print the macro that were covered
	 * 
	 */
	public static void printCoveredMacro(PrintStream ps) {
		for (MacroDeclaration md : coveredMacros) {
			ps.println(md.getName());
		}
	}

	Value visitTerm(Term t){
		return termEval.visit(t);		
	}

}
