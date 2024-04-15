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
 * TermEvaluator.java
 *
 * Created on 23 maggio 2006, 13.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.util.UnresolvedReferenceException;
import org.asmeta.simulator.value.BagValue;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.CollectionValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.MapValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.RuleValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;

/**
 * Evaluator of terms.
 * 
 */
public class TermEvaluator extends ReflectiveVisitor<Value> implements ITermVisitor<Value> {
	
	// allow static evaluation of functions
	public static boolean allowLazyEval = true;

	private static Logger logger = Logger.getLogger(TermEvaluator.class);

	public static Location self;

	/**
	 * Evaluates the standard library functions.
	 * 
	 */
	protected StdlEvaluator stdlEvaluator = new StdlEvaluator();

	/**
	 * State.
	 * 
	 */
	protected State state;

	/**
	 * Environment.
	 * 
	 */
	protected Environment environment;

	/**
	 * Assignment of free variables.
	 * 
	 */
	protected ValueAssignment assignment;

	/** is the use of pre enabled */
	// boolean isPreEnabled = false;

	/**
	 * Constructor.
	 * 
	 * @param state       state
	 * @param environment environment
	 * @param assignment  assignment of the free variables
	 */
	public TermEvaluator(State state, Environment environment, ValueAssignment assignment) {
		assert state != null;
		// assert environment != null;
		// assert assignment != null;
		this.state = state;
		this.environment = environment;
		this.assignment = assignment;
	}

	/**
	 * Gets the state.
	 * 
	 * @return state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Gets the environment
	 * 
	 * @return state
	 */
	public Environment getEnv() {
		return environment;
	}

	/**
	 * Gets the environment
	 * 
	 * @return state
	 */

	public ValueAssignment getAssignment() {
		return assignment;
	}

	/**
	 * Evaluates a term.
	 * 
	 * @param term a term
	 * @return term's value
	 */
	@Override
	public Value visit(Term term) {
		return visit((Object) term);
	}

	/**
	 * Evaluates a variable term.
	 * 
	 * @param term a variable term
	 * @return term's value
	 */
	@Override
	public Value visit(VariableTerm var) {
		logger.debug("<VariableTerm>");
		logger.debug("<Name>" + var.getName() + "</Name>");
		Value value = assignment.get(var);
		if (value == null) {
			throw new RuntimeException("Variable " + var.getName() + " is unbound");
		}
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</VariableTerm>");
		return value;
	}

	/**
	 * Evaluates a tuple term.
	 * 
	 * @param term a tuple term
	 * @return term's value
	 */
	@Override
	public TupleValue visit(TupleTerm tuple) {
		logger.debug("<TupleTerm>");
		List<Value> result = new ArrayList<>();
		if (tuple != null) {
			assert tuple.getArity() == tuple.getTerms().size() : "tuple.getArity(): " + tuple.getArity()
					+ "  tuple.getTerms().size(): " + tuple.getTerms().size();
			List<?> termList = tuple.getTerms();
			for (Object o : termList) {
				Term term = (Term) o;
				// angelo aprile 2024
				Value newValue = allowLazyEval ? Value.lazy(term,this) : visit(term);
				result.add(newValue);
			}
			assert tuple.getArity() == result.size();
		}
		// assert tuple == null || tuple.getArity() == result.size();
		// careful : in case of lazy evaluation this would distruct lazyness, since it prints the actual value!
		logger.debug("<Value>" + (allowLazyEval ? "lazy eval " : result) + "</Value>");
		logger.debug("</TupleTerm>");
		return new TupleValue(result);
	}

	/**
	 * Evaluates a function term.
	 * 
	 * @param term a function term
	 * @return term's value
	 */
	@Override
	public Value visit(FunctionTerm functionTerm) {
		logger.debug("<FunctionTerm>");
		logger.debug("<Name>" + functionTerm.getFunction().getName() + "</Name>");
		TupleTerm terms = functionTerm.getArguments();
		// if it is not a variable terms must have the correct number of arguments
		assert terms == null && functionTerm.getFunction().getDomain() == null
				|| terms.getTerms().size() == terms.getArity();
		TupleValue tuple = visit(terms);
		Value[] args = tuple.getValueAsArray();
		assert terms == null || terms.getArity() == args.length;
		// logger.debug("<Args>" + Arrays.asList(args) + "</Args>");
		// PA: 17 giugno 10 - inizio modifiche
		try{
			Value value = evalFunc(functionTerm.getFunction(), args);
			/*
			 * Value value; if(functionTerm.getFunction().getName().equals("pre")) {
			 * if(isPreEnabled) { value =
			 * evalFuncPreviousState((FunctionTerm)terms.getTerms().get(0)); } else { throw
			 * new Error("The pre function can be used only in invariants."); } } else {
			 * value = evalFunc(functionTerm.getFunction(), args); }
			 */
			// PA: 17 giugno 10 - fine modifiche
			logger.debug("<Name>" + functionTerm.getFunction().getName() + "</Name>");
			logger.debug("<Value>" + value + "</Value>");
			logger.debug("</FunctionTerm>");
			return value;
		} catch (UnresolvedReferenceException e) {			
			// add some extra info
			String message = functionTerm.getFunction().getName() + AsmetaTermPrinter.getAsmetaTermPrinter(false).visit(terms);
			throw new UnresolvedReferenceException(e.getMessage() + " as in  " + message);
		}
	}

	/**
	 * Evaluates a location term.
	 * 
	 * @param term a location term
	 * @return term's value
	 */
	@Override
	public Value visit(LocationTerm functionTerm) {
		logger.debug("<LocationTerm>");
		Value value = visit((FunctionTerm) functionTerm);
		logger.debug("</LocationTerm>");
		return value;
	}

	/**
	 * Evaluates a function.
	 * 
	 * @param func      a function declaration
	 * @param arguments actual parameters
	 * @return function value
	 */
	protected Value evalFunc(Function func, Value[] arguments) {
		Value value;
		// NOTE the order of the checks does matter
		if (Defs.isDynamic(func)) {
			Location location = new Location(func, arguments);
			value = state.read(location);
			if (value == null) {
				if (Defs.isMonitored(func))
					value = environment.read(location, state);
				else
					value = UndefValue.UNDEF;
			}
			assert value != null : "Function " + func.getName() + " has not been found nor in environment or state, or it is null";
		} else if (Defs.isAbstractConst(func) || Defs.isAgentConst(func)) {
			value = state.readAbstractConst(func.getName());
		} else if (Defs.isBuiltIn(func)) {
			value = stdlEvaluator.visit(func, arguments);
		} else if (Defs.isUserDefined(func) || Defs.isDerived(func)) {
			value = evalUserDefinedFunc(func, arguments);
		} else {
			throw new Error("Unknown function type.");
		}
		return value;
	}

	// PA: 17 giugno 10
/*
 *  DO NOT USE, use get previous tsat eof the simulator instead 
 *  
     protected Value evalFuncPreviousState(FunctionTerm functionTerm) {
 		Value value;
		Function function = functionTerm.getFunction();
		TupleTerm terms = functionTerm.getArguments();
		TupleValue tuple = visit(terms);
		Value[] args = tuple.getValueAsArray();
		if (Defs.isDynamic(function)) {
			Location location = new Location(function, args);
			value = state.previousLocationValues.get(location);
			assert value != null;
		} else if (Defs.isAbstractConst(function) || Defs.isAgentConst(function)) {
			throw new Error("Wrong use of pre function.");
		} else if (Defs.isBuiltIn(function)) {
			throw new Error("Wrong use of pre function.");
		} else if (Defs.isUserDefined(function) || Defs.isDerived(function)) {
			throw new Error("Wrong use of pre function.");
		} else {
			throw new Error("Unknown function type.");
		}
		return value;
	}*/

	/**
	 * Evaluates an user-defined function.
	 * 
	 * @param func      a function declaration
	 * @param arguments actual parameters
	 * @return function value
	 */
	protected Value evalUserDefinedFunc(Function function, Value[] arguments) {
		FunctionDefinition funcDef = function.getDefinition();
		assert funcDef != null : function.getName();
		List<?>/* <VariableTerm> */ variables = funcDef.getVariable();
		Term body = funcDef.getBody();
		assert body != null : function.getName() + " has null body.\nvars: " + variables;
		ValueAssignment newAssignment = new ValueAssignment();
		newAssignment.put(variables, arguments);
		TermEvaluator newTermEvaluator = new TermEvaluator(state, environment, newAssignment);
		Value value = newTermEvaluator.visit(body);
		return value;
	}

	/**
	 * Evaluates a conditional term.
	 * 
	 * @param term a conditional term
	 * @return term's value
	 */
	@Override
	public Value visit(ConditionalTerm condTerm) {
		logger.debug("<ConditionalTerm>");
		logger.debug("<Guard>");
		assert !visit(condTerm.getGuard()).equals(UndefValue.UNDEF): condTerm;
		BooleanValue guard = (BooleanValue) visit(condTerm.getGuard());
		logger.debug("</Guard>");
		Value value;
		if (guard.getValue()) {
			logger.debug("<ThenTerm>");
			value = visit(condTerm.getThenTerm());
			logger.debug("</ThenTerm>");
		} else if (condTerm.getElseTerm() != null) {
			logger.debug("<ElseTerm>");
			value = visit(condTerm.getElseTerm());
			logger.debug("</ElseTerm>");
		} else {
			value = UndefValue.UNDEF;
		}
		logger.debug("</ConditionalTerm>");
		return value;
	}

	/**
	 * Evaluates a case term.
	 * 
	 * @param term a case term
	 * @return term's value
	 */
	@Override
	public Value visit(CaseTerm caseTerm) {
		logger.debug("<CaseTerm>");
		Value value = null;
		logger.debug("<ComparedTerm>");
		Value comparedValue = visit(caseTerm.getComparedTerm());
		logger.debug("</ComparedTerm>");
		Iterator<?>/* <Term> */ resultTermIt = caseTerm.getResultTerms().iterator();
		for (Object o : caseTerm.getComparingTerm()) {
			Term comparingTerm = (Term) o;
			Term resultTerm = (Term) resultTermIt.next();
			logger.debug("<ComparingTerm>");
			Value comparingValue = visit(comparingTerm);
			logger.debug("</ComparingTerm>");
			if (comparedValue.equals(comparingValue)) {
				logger.debug("<ResultTerm>");
				value = visit(resultTerm);
				logger.debug("</ResultTerm>");
				break;
			}
		}
		if (value == null) {
			if (caseTerm.getOtherwiseTerm() != null) {
				logger.debug("<OtherwiseTerm>");
				value = visit(caseTerm.getOtherwiseTerm());
				logger.debug("</OtherwiseTerm>");
			} else {
				value = UndefValue.UNDEF;
			}
		}
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</CaseTerm>");
		return value;
	}

	/**
	 * Evaluates a domain term.
	 * 
	 * @param term a domain term
	 * @return term's value
	 */
	@Override
	public SetValue visit(DomainTerm domainTerm) {
		logger.debug("<DomainTerm>");
		// the base domain is always a power set domain
		Domain baseDomain = ((PowersetDomain) domainTerm.getDomain()).getBaseDomain();
		SetValue values = getValues(baseDomain);
		logger.debug("</DomainTerm>");
		return values;

	}

	/**
	 * computes the values of a domain
	 * 
	 * @param baseDomain
	 * @return
	 */
	public SetValue getValues(Domain baseDomain) {
		SetValue values;
		logger.debug("<Domain>" + baseDomain.getName() + "</Domain>");
		if (baseDomain instanceof ConcreteDomain) {
			logger.debug("<Type>ConcreteDomain</Type>");
			ConcreteDomain concreteDomain = (ConcreteDomain) baseDomain;
			if (concreteDomain.getIsDynamic()) {
				values = state.read(concreteDomain);
			} else {
				if (concreteDomain.getTypeDomain() instanceof AgentDomain) {
					values = state.read(concreteDomain);
				} else {
					Term body = concreteDomain.getDefinition().getBody();
					values = (SetValue) visit(body);
				}
			}
		} else if (baseDomain instanceof AbstractTd) {
			logger.debug("<Type>AbstractTd</Type>");
			values = state.read(baseDomain);
		} else if (baseDomain instanceof EnumTd) {
			logger.debug("<Type>EnumTd</Type>");
			values = getEnumDomainContent((EnumTd) baseDomain);
		} else if (baseDomain instanceof BooleanDomain) {
			logger.debug("<Type>BooleanDomain</Type>");
			Set<Value> set = new HashSet<Value>();
			set.add(BooleanValue.FALSE);
			set.add(BooleanValue.TRUE);
			values = new SetValue(set);
		} else {
			throw new UnsupportedOperationException();
		}
		logger.debug("<Value>" + values + "</Value>");
		return values;
	}

	/**
	 * Gets the content of an enumeration domain.
	 * 
	 * @param domain an enumeration domain
	 * @return the content
	 */
	private static SetValue getEnumDomainContent(EnumTd domain) {
		Set<Value> set = new HashSet<Value>();
		for (Object o : domain.getElement()) {
			EnumElement nextEnum = (EnumElement) o;
			EnumValue nextValue = new EnumValue(nextEnum);
			set.add(nextValue);
		}
		return new SetValue(set);
	}

	/**
	 * Evaluates an enumeration term.
	 * 
	 * @param term an enumeration term
	 * @return term's value
	 */
	@Override
	public EnumValue visit(EnumTerm enumTerm) {
		logger.debug("<EnumTerm>");
		EnumValue value = new EnumValue(enumTerm);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</EnumTerm>");
		return value;
	}

	/**
	 * Evaluates a let term.
	 * 
	 * @param term a let term
	 * @return term's value
	 */
	@Override
	public Value visit(LetTerm letTerm) {
		logger.debug("<LetTerm>");
		ValueAssignment newAssignment = new ValueAssignment(assignment);
		TermEvaluator newEvaluator = new TermEvaluator(state, environment, newAssignment);
		Iterator<?>/* <Term> */ initTermIt = letTerm.getAssignmentTerm().iterator();
		logger.debug("<InitList>");
		for (Object o : letTerm.getVariable()) {
			VariableTerm variable = (VariableTerm) o;
			logger.debug("<VariableTerm>");
			logger.debug("<Name>" + variable.getName() + "</Name>");
			Term initTerm = (Term) initTermIt.next();
			Value initValue = newEvaluator.visit(initTerm);
			newAssignment.put(variable, initValue);
			logger.debug("<Value>" + initValue + "</Value>");
			logger.debug("</VariableTerm>");
		}
		logger.debug("</InitList>");
		logger.debug("<InTerm>");
		Term body = letTerm.getBody();
		Value value = newEvaluator.visit(body);
		logger.debug("</InTerm>");
		logger.debug("</LetTerm>");
		return value;
	}

	/**
	 * Evaluates an exist term.
	 * 
	 * @param term an exist term
	 * @return term's value
	 */
	@Override
	public BooleanValue visit(ExistTerm existTerm) {
		logger.debug("<ExistTerm>");
		Value[] boundValues = new Value[existTerm.getVariable().size()];
		Object/* Term */[] domains = existTerm.getRanges().toArray();
		boolean bool = visitExistTerm(0, domains, boundValues, existTerm);
		BooleanValue value = BooleanValue.parseBooleanValue(bool);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</ExistTerm>");
		return value;
	}

	/**
	 * Evaluates an exist term.
	 * 
	 * @param varIndex    next variable to bound a value
	 * @param domains     an array of range term
	 * @param boundValues current values of the bound variables
	 * @param existTerm   the term to evaluate
	 * @return the exist term value
	 */
	boolean visitExistTerm(int varIndex, Object/* Term */[] domains, Value[] boundValues, ExistTerm existTerm) {
		if (varIndex < existTerm.getVariable().size()) {
			Term rangeTerm = (Term) domains[varIndex];
			CollectionValue<?> range = (CollectionValue) visit(rangeTerm);
			for (Value<?> value : range) {
				boundValues[varIndex] = value;
				boolean bool = visitExistTerm(varIndex + 1, domains, boundValues, existTerm);
				if (bool) {
					return true;
				}
			}
		} else {
			ValueAssignment newAssignment = new ValueAssignment(assignment);
			newAssignment.put(existTerm.getVariable(), boundValues);
			TermEvaluator newEvaluator = new TermEvaluator(state, environment, newAssignment);
			logger.debug("<ExistTermGuard>");
			Term existTermGuard = existTerm.getGuard();
			BooleanValue guard = BooleanValue.TRUE;
			if (existTermGuard != null) {
				guard = (BooleanValue) newEvaluator.visit(existTermGuard);
			}
			logger.debug("</ExistTermGuard>");
			return guard.getValue();
		}
		return false;
	}

	/**
	 * Evaluates an exist term.
	 * 
	 * @param term an exist term
	 * @return term's value
	 */
	public BooleanValue visit(ExistUniqueTerm existUniqueTerm) {
		logger.debug("<ExistUniqueTerm>");
		Value[] boundValues = new Value[existUniqueTerm.getVariable().size()];
		Object/* Term */[] domains = existUniqueTerm.getRanges().toArray();
		boolean bool = visitExistUniqueTerm(0, domains, boundValues, existUniqueTerm);
		BooleanValue value = BooleanValue.parseBooleanValue(bool);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</ExistUniqueTerm>");
		return value;
	}

	/**
	 * Evaluates an exist term.
	 * 
	 * @param varIndex    next variable to bound a value
	 * @param domains     an array of range term
	 * @param boundValues current values of the bound variables
	 * @param existTerm   the term to evaluate
	 * @return the exist term value
	 */
	boolean visitExistUniqueTerm(int varIndex, Object/* Term */[] domains, Value[] boundValues,
			ExistUniqueTerm existUniqueTerm) {
		if (varIndex < existUniqueTerm.getVariable().size()) {
			Term rangeTerm = (Term) domains[varIndex];
			CollectionValue<?> range = (CollectionValue<?>) visit(rangeTerm);
			int trueCounter = 0;
			for (Value<?> value : range) {
				boundValues[varIndex] = value;
				boolean bool = visitExistUniqueTerm(varIndex + 1, domains, boundValues, existUniqueTerm);
				if (bool) {
					trueCounter++;
					// System.out.println("trueCounter " + trueCounter);
					if (trueCounter > 1) {
						return false;
					}
				}
			}
			return trueCounter == 1;
		} else {
			ValueAssignment newAssignment = new ValueAssignment(assignment);
			newAssignment.put(existUniqueTerm.getVariable(), boundValues);
			TermEvaluator newEvaluator = new TermEvaluator(state, environment, newAssignment);
			logger.debug("<ExistUniqueTermGuard>");
			Term existTermGuard = existUniqueTerm.getGuard();
			BooleanValue guard = BooleanValue.TRUE;
			if (existTermGuard != null) {
				guard = (BooleanValue) newEvaluator.visit(existTermGuard);
				// System.out.println(newAssignment + " " + guard.getValue());
			}
			logger.debug("</ExistUniqueTermGuard>");
			return guard.getValue();
		}
		// return false;
	}

	/**
	 * Evaluates a forall term.
	 * 
	 * @param term a forall term
	 * @return term's value
	 */
	@Override
	public BooleanValue visit(ForallTerm forTerm) {
		logger.debug("<ForallTerm>");
		Value[] boundValues = new Value[forTerm.getVariable().size()];
		Object/* Term */[] domains = forTerm.getRanges().toArray();
		BooleanValue value = BooleanValue.parseBooleanValue(visitForTerm(0, domains, boundValues, forTerm));
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</ForallTerm>");
		return value;
	}

	/**
	 * Evaluates a forall term.
	 * 
	 * @param varIndex    next variable to bound a value
	 * @param domains     an array of range term
	 * @param boundValues current values of the bound variables
	 * @param forTerm     the term to evaluate
	 * @return the forall term value
	 */
	boolean visitForTerm(int varIndex, Object/* Term */[] domains, Value[] boundValues, ForallTerm forTerm) {
		if (varIndex < forTerm.getVariable().size()) {
			Term rangeTerm = (Term) domains[varIndex];
			CollectionValue<?> range = (CollectionValue) visit(rangeTerm);
			for (Value<?> val : range) {
				boundValues[varIndex] = val;
				boolean bool = visitForTerm(varIndex + 1, domains, boundValues, forTerm);
				if (!bool) {
					return false;
				}
			}
		} else {
			ValueAssignment newAssignment = new ValueAssignment(assignment);
			newAssignment.put(forTerm.getVariable(), boundValues);
			TermEvaluator newEvaluator = new TermEvaluator(state, environment, newAssignment);
			logger.debug("<Guard>");
			BooleanValue guard = (BooleanValue) newEvaluator.visit(forTerm.getGuard());
			logger.debug("</Guard>");
			return guard.getValue();
		}
		return true;
	}

	/**
	 * Evaluates a set comprehension term.
	 * 
	 * @param term a set comprehension term
	 * @return term's value
	 */
	@Override
	public SetValue visit(SetCt setCt) {
		logger.debug("<SetCt>");
		Set<Value> setValue = new HashSet<Value>();
		Value[] boundValues = new Value[setCt.getVariable().size()];
		List<VariableTerm> freeVars = setCt.getVariable();
		List<Term> domains = setCt.getRanges();
		visitComprehension(0, freeVars, domains, boundValues, setCt, setValue);
		logger.debug("<Value>" + setValue + "</Value>");
		logger.debug("</SetCt>");
		return new SetValue(setValue);
	}

	// PA 2010/12/15
	public BagValue visit(BagCt bagCt) {
		logger.debug("<BagCt>");
		List<Value> bagValue = new ArrayList<Value>();
		Value[] boundValues = new Value[bagCt.getVariable().size()];
		List<VariableTerm> freeVars = bagCt.getVariable();
		List<Term> domains = bagCt.getRanges();
		visitComprehension(0, freeVars, domains, boundValues, bagCt, bagValue);
		logger.debug("<Value>" + bagValue + "</Value>");
		logger.debug("</BagCt>");
		return new BagValue(bagValue);
	}

	/**
	 * Evaluates a sequence comprehension term.
	 * 
	 * @param term a sequence comprehension term
	 * @return term's value
	 */
	@Override
	public SequenceValue visit(SequenceCt seqCt) {
		logger.debug("<SequenceCt>");
		List<Value> seqValue = new ArrayList<Value>();
		Value[] boundValues = new Value[seqCt.getVariable().size()];
		List<VariableTerm> freeVars = seqCt.getVariable();
		List<Term> domains = seqCt.getRanges();
		visitComprehension(0, freeVars, domains, boundValues, seqCt, seqValue);
		logger.debug("<Value>" + seqValue + "</Value>");
		logger.debug("</SequenceCt>");
		return new SequenceValue(seqValue);
	}

	/**
	 * Evaluates a comprehension term.
	 * 
	 * @param varIndex      next variable to bound a value
	 * @param boundVars     variables to bound
	 * @param domains       an array of range term
	 * @param boundValues   current values of the bound variables
	 * @param comprehension a comprehension term
	 * @param the           result of evaluation
	 */
	private void visitComprehension(int varIndex, List<VariableTerm> boundVars, List<Term> domains, Value[] boundValues,
			ComprehensionTerm comprehension, Collection<Value> result) {
		if (varIndex < boundVars.size()) {
			logger.debug("<VariableTerm>");
			VariableTerm variable = boundVars.get(varIndex);
			logger.debug("<Name>" + variable.getName() + "</Name>");
			Term rangeTerm = domains.get(varIndex);
			logger.debug("<Range>");
			CollectionValue<?> range = (CollectionValue) visit(rangeTerm);
			logger.debug("</Range>");
			logger.debug("</VariableTerm>");
			for (Value<?> value : range) {
				boundValues[varIndex] = value;
				visitComprehension(varIndex + 1, boundVars, domains, boundValues, comprehension, result);
			}
		} else {
			ValueAssignment newAssignment = new ValueAssignment(assignment);
			newAssignment.put(comprehension.getVariable(), boundValues);
			TermEvaluator newEvaluator = new TermEvaluator(state, environment, newAssignment);
			BooleanValue guard;
			if (comprehension.getGuard() != null) {
				logger.debug("<Guard>");
				guard = (BooleanValue) newEvaluator.visit(comprehension.getGuard());
				logger.debug("</Guard>");
			} else {
				// guard null -> true
				guard = BooleanValue.TRUE;
			}
			if (guard.getValue()) {
				logger.debug("<Add>");
				Value newValue = newEvaluator.visit(comprehension.getTerm());
				logger.debug("</Add>");
				result.add(newValue);
			}
		}
	}

	/**
	 * Evaluates a set term.
	 * 
	 * @param term a set term
	 * @return term's value
	 */
	@Override
	public SetValue visit(SetTerm setTerm) {
		logger.debug("<SetTerm>");
		Collection<?>/* <Term> */ terms = setTerm.getTerm();
		Set<Value> setValue = new HashSet<Value>();
		for (Object o : terms) {
			Term nextTerm = (Term) o;
			Value nextValue = visit(nextTerm);
			setValue.add(nextValue);
		}
		logger.debug("<Value>" + setValue + "</Value>");
		logger.debug("</SetTerm>");
		return new SetValue(setValue);
	}

	/**
	 * Evaluates a map term.
	 * 
	 * @param term a map term
	 * @return term's value
	 */
	@Override
	public MapValue visit(MapTerm mapTerm) {
		logger.debug("<MapTerm>");
		Collection<?> pairs = mapTerm.getPair();
		Map<Value, Value> map = new HashMap<Value, Value>();
		for (Object o : pairs) {
			TupleTerm next = (TupleTerm) ((Term) o);
			List<?> elements = next.getTerms();
			Term temp = (Term) elements.get(0);
			Value key = visit(temp);
			temp = (Term) elements.get(1);
			Value value = visit(temp);
			map.put(key, value);
		}
		MapValue mapValue = new MapValue(map);
		logger.debug("<Value>" + mapValue + "</Value>");
		logger.debug("</SetTerm>");
		return mapValue;
	}

	/**
	 * Evaluates a bag term.
	 * 
	 * @param term a bag term
	 * @return term's value
	 */
	@Override
	public BagValue visit(BagTerm bagTerm) {
		logger.debug("<BagTerm>");
		Collection<Term> terms = bagTerm.getTerm();
		List<Value> bag = new ArrayList<Value>();
		for (Term nextTerm : terms) {
			Value nextValue = visit(nextTerm);
			bag.add(nextValue);
		}
		logger.debug("<Value>" + bag + "</Value>");
		logger.debug("</BagTerm>");
		return new BagValue(bag);
	}

	/**
	 * Evaluates a sequence term.
	 * 
	 * @param term a sequence term
	 * @return term's value
	 */
	@Override
	public SequenceValue visit(SequenceTerm sequence) {
		logger.debug("<SequenceTerm>");
		Collection<?>/* <Term> */ terms = sequence.getTerms();
		List<Value> seqValue = new ArrayList<Value>();
		for (Object o : terms) {
			Term nextTerm = (Term) o;
			Value nextValue = visit(nextTerm);
			seqValue.add(nextValue);
		}
		logger.debug("<Value>" + seqValue + "</Value>");
		logger.debug("</SequenceTerm>");
		return new SequenceValue(seqValue);
	}

	/**
	 * Evaluates a natural term.
	 * 
	 * @param term a natural term
	 * @return term's value
	 */
	@Override
	public IntegerValue visit(NaturalTerm numericTerm) {
		logger.debug("<NaturalTerm>");
		IntegerValue value = new IntegerValue(numericTerm);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</NaturalTerm>");
		return value;
	}

	/**
	 * Evaluates an integer term.
	 * 
	 * @param term an integer term
	 * @return term's value
	 */
	@Override
	public IntegerValue visit(IntegerTerm numericTerm) {
		logger.debug("<IntegerTerm>");
		IntegerValue value = new IntegerValue(numericTerm);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</IntegerTerm>");
		return value;
	}

	/**
	 * Evaluates a real term.
	 * 
	 * @param term a real term
	 * @return term's value
	 */
	@Override
	public RealValue visit(RealTerm numericTerm) {
		logger.debug("<RealTerm>");
		RealValue value = new RealValue(numericTerm);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</RealTerm>");
		return value;
	}

	/**
	 * Evaluates a boolean term.
	 * 
	 * @param term a boolean term
	 * @return term's value
	 */
	@Override
	public BooleanValue visit(BooleanTerm booleanTerm) {
		logger.debug("<BooleanTerm>");
		BooleanValue value = BooleanValue.parseBooleanValue(booleanTerm);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</BooleanTerm>");
		return value;
	}

	/**
	 * Evaluates a string term.
	 * 
	 * @param term a string term
	 * @return term's value
	 */
	@Override
	public StringValue visit(StringTerm string) {
		logger.debug("<StringTerm>");
		StringValue value = new StringValue(string);
		logger.debug("<Value>" + value + "</Value>");
		logger.debug("</StringTerm>");
		return value;
	}

	/**
	 * Evaluates an undefined term.
	 * 
	 * @param term an undefined term
	 * @return term's value
	 */
	@Override
	public UndefValue visit(UndefTerm undef) {
		return UndefValue.UNDEF;
	}

	/**
	 * Evaluates a rule as term.
	 * 
	 * @param term a rule as term
	 * @return term's value
	 */
	@Override
	public RuleValue visit(RuleAsTerm rule) {
		RuleValue value;
		RuleDeclaration dcl = rule.getRule();
		// TODO add package name
		logger.debug("<RuleAsTerm name=\"" + dcl.getName() + "\">");
		// FIXME agent should not be null
		value = new RuleValue(dcl, null);
		logger.debug("</RuleAsTerm>");
		return value;
	}

}
