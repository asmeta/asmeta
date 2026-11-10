package org.asmeta.flattener.rule;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.asmeta.flattener.statistics.Statistics;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.DefinitionsFactory;
import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.basicterms.impl.BasictermsFactoryImpl;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;

//
//  transform a choose rule in a standard rule with the element as monitored with some constraints give in INVAR
//  
public class ChooseRuleINVARFlattener extends RuleFlattener {

	private int chooseCounter = 0;
	// keep track of the variables --> original choosen var and its rule	
	private Map<String, AbstractMap.SimpleEntry<String,RuleDeclaration>> varsConvertedToMon = new HashMap<>();

	
	static public Map<String, AbstractMap.SimpleEntry<String,RuleDeclaration>> lastChooseVars;
	
	public ChooseRuleINVARFlattener(Asm asm) {
		super(asm);
	}
	
	@Override
	public Asm flattenASM() {
		Asm result = super.flattenASM();
		// copy 
		lastChooseVars = Collections.unmodifiableMap(varsConvertedToMon);
		return result;		
	}


	@Override
	public Rule visit(ChooseRule chooseRule) {
		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());
		//
		EList<VariableTerm> vars = chooseRule.getVariable();
		if (vars.size() > 1) {
			throw new Error("Currently we support only choose rules with one variable");
		}
		// 1. create a new monitored variable
		// value and domain of choose variable
		VariableTerm var = vars.get(0);
		// add as monitored function
		MonitoredFunction chosenVar = DefinitionsPackage.eINSTANCE.getDefinitionsFactory().createMonitoredFunction();
		chosenVar.setArity(0);
		// varName = "var_" + var.getName() + "_" + chooseCounter;
		String name = var.getName().replaceAll("\\$", "_X_");
		String chosenVarName = "varforchoose_" + name + "_" + chooseCounter++;
		chosenVar.setName(chosenVarName);
		// it is a variable, use the codomain
		chosenVar.setDomain(null);
		chosenVar.setCodomain(var.getDomain());
		chosenVar.setArity(0);
		// add to the map, so
		assert !varsConvertedToMon.containsKey(chosenVarName)
				: chosenVarName + " already taken in rule declaration " + varsConvertedToMon.get(chosenVarName);
		varsConvertedToMon.put(chosenVarName, new AbstractMap.SimpleEntry<>(var.getName(),currentRuleDeclaration));
		// add chosen var to the ignature
		Signature signature = asm.getHeaderSection().getSignature();
		signature.getFunction().add(chosenVar);
		//
		// rewrite the internal rule by substituing the variable
		// replace variable in the rest of the rule
		LocationTerm lt = ruleFact.createLocationTerm();
		lt.setFunction(chosenVar);
		lt.setDomain(chosenVar.getDomain());
		{
			// add the new assignment for the variale
			Map<VariableTerm, Term> map = new HashMap<>();
			map.put(var, lt);
			trv.addMap(map);
			// build the new do rule
			// attenzione potrebbe contenere una choose rule a sua volta
			Rule newRule = visit(chooseRule.getDoRule());
			// no ifnone
			assert chooseRule.getIfnone() == null;
			// add also the else if there ifnone
			if (chooseRule.getIfnone() != null) {
				// TODO
				Rule newIfNoneRule = visit(chooseRule.getIfnone());
				// newCondRule.setElseRule(newIfNoneRule);
			}
			// add invariants
			assert chooseRule.getGuard() != BasictermsFactoryImpl.eINSTANCE.createBooleanTerm(false);
			if (chooseRule.getGuard() != BasictermsFactoryImpl.eINSTANCE.createBooleanTerm(true)) {
				// ADD INVARIANTS if condition is not true
				InvarConstraint invar = DefinitionsFactory.eINSTANCE.createInvarConstraint();
				Term newGuard = trv.visit(chooseRule.getGuard());
				invar.setBody(newGuard);
				asm.getBodySection().getInvariantConstraint().add(invar);
			}
			trv.removeMap(map);
			return newRule;
		}
	}

	@Override
	public String getCode() {
		return "ChRI";
	}
}
