package org.asmeta.flattener.rule;

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
	// keep track of the variables
	Map<String, RuleDeclaration> varsConvertedToMon = new HashMap<>();

	public ChooseRuleINVARFlattener(Asm asm) {
		super(asm);
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
		chosenVar.setCodomain(var.getDomain());
		chosenVar.setArity(0);
		// add to the map, so
		assert !varsConvertedToMon.containsKey(chosenVarName)
				: chosenVarName + " already taken in rule declaration " + varsConvertedToMon.get(chosenVarName);
		varsConvertedToMon.put(chosenVarName, currentRuleDeclaration);
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
			Rule newRule = visit(chooseRule.getDoRule());
			// attenzione potrebbe contenere una choose rule a sua volta
			// TODO if there is the guard
			if (chooseRule.getGuard().equals("TRUE")) {
				ConditionalRule newCondRule = ruleFact.createConditionalRule();
				// TO BE COMPLETED
			}
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
			// no ifnone
			// TODO
			assert chooseRule.getIfnone() == null;
			trv.removeMap(map);
			return newRule;
		}
		/*
		 * // build something like // function chooseVar0 = chooseone({$b in ConcrDom|
		 * lt($b,$a) : $b}) // note that chooseVar0 must be dynamic or static according
		 * to its definition SetCt setCtTerm = ruleFact.createSetCt(); // {... in ... |
		 * EList<VariableTerm> setVars = setCtTerm.getVariable(); setVars.add(var);
		 * setCtTerm.setDomain(varDomain); // | ... :
		 * setCtTerm.setGuard(chooseRule.getGuard()); // : ... setCtTerm.setTerm(var);
		 * setCtTerm.getRanges().addAll(chooseRule.getRanges()); FunctionTerm chooseone
		 * = stdlFunction.stdlFunc("chooseone", setCtTerm); Function functionForChoose;
		 * // check the // collect all the terms in the def Body List<EObject> notStatic
		 * = new ArrayList<>(); DynamicInTermFinder ns = new
		 * DynamicInTermFinder(notStatic); ns.visit(chooseone); // static or derived
		 * function to be added in the signature if (notStatic.isEmpty()) // no dynamic
		 * functions functionForChoose =
		 * DefinitionsPackage.eINSTANCE.getDefinitionsFactory().createStaticFunction();
		 * else functionForChoose =
		 * DefinitionsPackage.eINSTANCE.getDefinitionsFactory().createDerivedFunction();
		 * //StaticFunction staticFun = functionForChoose.setArity(0);
		 * functionForChoose.setCodomain(varDomain); String funcName = "chooseVar" +
		 * (counter++); functionForChoose.setName(funcName);
		 * 
		 * // function definition FunctionDefinition defFuncDef =
		 * StructureFactory.eINSTANCE.createFunctionDefinition();
		 * defFuncDef.setDefinedFunction(functionForChoose);
		 * defFuncDef.setBody(chooseone); // add function definition to the body Body
		 * asmBody = asm.getBodySection();
		 * asmBody.getFunctionDefinition().add(defFuncDef);
		 */
	}

	@Override
	public String getCode() {
		return "ChRI";
	}
}
