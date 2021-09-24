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
 * AsmState.java
 *
 * Created on 26 maggio 2006, 15.20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.parser.Defs;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;

/**
 * The state of an ASM.
 * 
 */
public class State extends LocationSet {
	private static Logger logger = Logger.getLogger(State.class);

	/**
	 * Returns the value of monitored and shared locations.
	 * 
	 */
	// PA 18/5/2011: why static? It give problem when more than a simulator is
	// running.
	// static Environment environment;
	// changed in not static
	Environment environment;

	/**
	 * Initialization of dynamic functions and domains.
	 * 
	 */
	Initialization initialization;

	public Map<Location, Value> previousLocationValues;

	/**
	 * Creates an empty state.
	 * 
	 * @param init initialization
	 */
	public State(Initialization init, Environment env) {
		assert env != null;
		environment = env;
		initialization = init;
		previousLocationValues = new HashMap<Location, Value>();// PA: 10 giugno 2010
	}

	/**
	 * Copy constructor.
	 * 
	 * @param state a state
	 */
	public State(State state) {
		super(state);
		environment = state.environment;
		initialization = state.getInitialization();
	}

	/**
	 * Returns the initialization of dynamic functions and domains.
	 * 
	 * @return initialization
	 */
	public Initialization getInitialization() {
		return initialization;
	}

	protected Value evalUserDefinedFunc(State state, Function function, Value[] arguments) {
		FunctionDefinition funcDef = function.getDefinition();
		List<?>/* <VariableTerm> */ variables = funcDef.getVariable();
		Term body = funcDef.getBody();
		ValueAssignment newAssignment = new ValueAssignment();
		newAssignment.put(variables, arguments);
		TermEvaluator newTermEvaluator = new TermEvaluator(state, environment, newAssignment);
		Value value = newTermEvaluator.visit(body);
		return value;
	}

	/**
	 * Returns the content of the given location.
	 * 
	 * @param location a location
	 * @return location's content
	 */
	public Value<?> read(Location location) {
		Value<?> value = locationMap.get(location);
		if (value == null) {
			// PA 21/02/2012 in order to read derived locations values
			if (Defs.isDerived(location.getSignature())) {
				logger.debug(location);
				value = evalUserDefinedFunc(this, location.getSignature(), location.getElements());
				// System.out.println(location + " = " + value);
				// TODO why it is not put into the state map? AG
				return value;
			}
			// the location is not in the state, does initialization
			DynamicFunction signature = (DynamicFunction) location.getSignature();
			if (Defs.isControlled(signature) || Defs.isOut(signature)) {
				logger.debug("<Initialization>");
				Value[] elements = location.getElements();
				value = evalFuncInit(signature, elements);
				logger.debug("</Initialization>");
			} else if (Defs.isMonitored(signature) || Defs.isShared(signature)) {
				logger.debug("<EnvironmentInitialization>");
				logger.debug(location);
				value = environment.read(location, this);
				logger.debug("</EnvironmentInitialization>");
			} else {
				throw new RuntimeException("Unknown function kind of " + location.getName());
			}
			locationMap.put(location, value);
		}
		return value;
	}

	/**
	 * 20/10/10 PA: for monitoring. We need the values of all the locations. TODO:
	 * problems with function overloading? TODO: the method is not correct. It does
	 * not consider function not yet initialized.
	 * 
	 * @param function
	 * @return
	 */
	public Map<Location, Value> read(Function function) {
		if (function.getArity() == 0) {
			read(new Location(function, new Value[0]));
		}
		Map<Location, Value> values = new HashMap<Location, Value>();
		for (Location location : locationMap.keySet()) {
			// TODO questo controlla solo il nome dovrebbe controllare la funzione non il nome
			if (location.getSignature().getName().equals(function.getName())) {
				// if(location.getSignature().equals(function)) {
				values.put(location, read(location));
			}
		}
		return values;
	}

	/**
	 * Returns the abstract constant with the given name.
	 * 
	 * @param name constant name
	 * @return the constant value
	 */
	public Value readAbstractConst(String name) {
		// FIXME add some optimization, i.e. search only in the domain of the value
		for (Set<ReserveValue> s : abstractSets.values()) {
			for (ReserveValue av : s) {
				if (av.getValue().equals(name)) {
					return av;
				}
			}
		}
		return UndefValue.UNDEF;
	}

	/**
	 * Evaluates the initialization of the given location.
	 * 
	 * @param func      a function
	 * @param arguments arguments
	 * @return the initial value of the location
	 */
	private Value evalFuncInit(DynamicFunction func, Value[] arguments) {
		Value value;
		FunctionInitialization funcInit = searchFunctInit(func);
		if (funcInit == null) {
			// locations without initialization are undefined
			value = UndefValue.UNDEF;
		} else {
			ValueAssignment newAssignment = new ValueAssignment();
			List<?>/* <VariableTerm> */ variables = funcInit.getVariable();
			newAssignment.put(variables, arguments);
			TermEvaluator newTermEvaluator = new TermEvaluator(this, environment, newAssignment);
//			// FIXME does this code work?
			// I get the null pointer since it sets the nev to null !!!
//			TermEvaluator newTermEvaluator = 
//				new TermEvaluator(null, null, newAssignment);
			Term body = funcInit.getBody();
			value = newTermEvaluator.visit(body);
		}
		return value;
	}

	/**
	 * Searches a function initialization.
	 * 
	 * @param func a function declaration
	 * @return the function initialization, otherwise null
	 */
	private FunctionInitialization searchFunctInit(DynamicFunction func) {
		if (initialization != null) {
			Collection<FunctionInitialization> funcInits = initialization.getFunctionInitialization();
			for (FunctionInitialization funcInit : funcInits) {
				DynamicFunction funcDcl = funcInit.getInitializedFunction();
				if (Defs.equals(func, funcDcl)) {
					return funcInit;
				}
			}
			// FIXME: func could be initialized by a function that takes as domain func
			// typedomain
			// AG giugno 09
			if (func.getDomain() instanceof ConcreteDomain) {
				ConcreteDomain temp = (ConcreteDomain) func.getDomain();
				func.setDomain(temp.getTypeDomain());
				for (FunctionInitialization funcInit : funcInits) {
					DynamicFunction funcDcl = funcInit.getInitializedFunction();
					if (Defs.equals(func, funcDcl)) {
						// reste the original domain
						func.setDomain(temp);
						return funcInit;
					}
				}

			}
		}
		return null;
	}

	/**
	 * Returns the content of a dynamic domain. If the domain is the super set of
	 * others domains, the method returns the union of all such domains.
	 * 
	 * @param domain a dynamic domain
	 * @return the content
	 */
	public SetValue read(Domain domain) {
		Set<Value<?>> set = new HashSet<>();
		for (Map.Entry<LocationSet.WrappedDomain, Set<ReserveValue>> entry : abstractSets.entrySet()) {
			Domain dom1 = entry.getKey().domain;
			if (Defs.isSubsetOf(dom1, domain)) {
				set.addAll(entry.getValue());
			}
		}
		return new SetValue(set);
	}

	/**
	 * Applies the given update set to the state.
	 * 
	 * @param updateSet an update set
	 */
	public void fireUpdates(UpdateSet updateSet) {
		// updates the locations
		locationMap.putAll(updateSet.locationMap);
		// adds the sets
		add(updateSet.abstractSets);
	}

	// Inizio modifiche PA: 10 giugno 2010
	/**
	 * It prints the controlled part of the state.
	 * 
	 * @return
	 */
	public String getContrLocsState() {
		return formatString(getContrLocs(false), '\n', true);
	}

	/**
	 * 
	 * @return the controlled part of the state
	 */
	public Map<Location, Value> getContrLocs(boolean retrieveSelf) {
		Map<Location, Value> contrLocationMap = new HashMap<Location, Value>();
		Function function;
		for (Location location : locationMap.keySet()) {
			function = location.getSignature();
			if (!retrieveSelf && Defs.isSelf(function)) {
				continue;
			}
			if (Defs.isControlled(function) || Defs.isOut(function)) {
				contrLocationMap.put(location, locationMap.get(location));
			}
		}
		return contrLocationMap;
	}

	public Map<Location, Value> getContrLocs() {
		return getContrLocs(true);
	}
	
	/**
	 * 
	 * @return the controlled part of the state
	 */
	public Map<Location, Value> getOutLocs(boolean retrieveSelf) {
		Map<Location, Value> outLocationMap = new HashMap<Location, Value>();
		Function function;
		for (Location location : locationMap.keySet()) {
			function = location.getSignature();
			if (!retrieveSelf && Defs.isSelf(function)) {
				continue;
			}
			if (Defs.isOut(function)) {
				outLocationMap.put(location, locationMap.get(location));
			}
		}
		return outLocationMap;
	}

	public Map<Location, Value> getOutLocs() {
		return getOutLocs(true);
	}

	/**
	 * It prints the monitored part of the state.
	 * 
	 * @return
	 */
	public String getMonLocsState() {
		Map<Location, Value> monLocationMap = getMonLocs();
		return formatString(monLocationMap, '\n', false);
	}
	// Fine modifiche PA: 10 giugno 2010

	// return the monitored part of the state
	// as a new map
	public Map<Location, Value> getMonLocs() {
		Map<Location, Value> monLocationMap = new HashMap<Location, Value>();
		Function function;
		for (Location location : locationMap.keySet()) {
			function = location.getSignature();
			if (Defs.isMonitored(function)
			// || Defs.isShared(function)
			) {
				monLocationMap.put(location, locationMap.get(location));
			}
		}
		return monLocationMap;
	}
	
	
}
