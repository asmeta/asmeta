package org.asmeta.flattener.rule;

import java.util.HashMap;
import java.util.Map;

import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.simulator.util.StdlFunction;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.Signature;
import asmeta.structure.StructureFactory;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;

public class ChooseRuleFlattener extends RuleFlattener {
	private StdlFunction stdlFunction;
	private int counter;

	public ChooseRuleFlattener(Asm asm) {
		super(asm);
		stdlFunction = new StdlFunction(asm);
	}

	@Override
	public Rule visit(ChooseRule chooseRule) {
		// Statistics.getInstance().increaseValue(this.getClass().getName());
		Statistics.getInstance().increaseValue(this.getCode());

		// TODO dobbiamo visitare la guardia della choose rule per vedere se ci sono
		// altre variabili non della choose. Se ci sono, queste diventano argomenti
		// della funzione derivata

		EList<VariableTerm> vars = chooseRule.getVariable();
		if (vars.size() > 1) {
			throw new Error("Currently we support only choose rules with one variable");
		}

		// value and domain of choose variable
		VariableTerm var = vars.get(0);
		Domain varDomain = var.getDomain();

		// derived function to be added in the signature
		DerivedFunction derFunc = DefinitionsPackage.eINSTANCE.getDefinitionsFactory().createDerivedFunction();
		derFunc.setArity(0);
		derFunc.setCodomain(varDomain);
		String funcName = "chooseVar" + (counter++);
		derFunc.setName(funcName);

		// function definition
		FunctionDefinition defFuncDef = StructureFactory.eINSTANCE.createFunctionDefinition();
		defFuncDef.setDefinedFunction(derFunc);

		SetCt setCtTerm = ruleFact.createSetCt();
		// {... in ... |
		EList<VariableTerm> setVars = setCtTerm.getVariable();
		setVars.add(var);
		setCtTerm.setDomain(varDomain);
		// | ... :
		setCtTerm.setGuard(chooseRule.getGuard());
		// : ...
		setCtTerm.setTerm(var);
		setCtTerm.getRanges().addAll(chooseRule.getRanges());

		FunctionTerm chooseone = stdlFunction.stdlFunc("chooseone", setCtTerm);
		defFuncDef.setBody(chooseone);

		// add declaration to the signature
		Signature signature = asm.getHeaderSection().getSignature();
		signature.getFunction().add(derFunc);
		// add function definition to the body
		Body asmBody = asm.getBodySection();
		asmBody.getFunctionDefinition().add(defFuncDef);

		ConditionalRule newCondRule = ruleFact.createConditionalRule();
		// create location term for the isDef STDL function
		LocationTerm lt = ruleFact.createLocationTerm();
		lt.setFunction(derFunc);
		lt.setDomain(varDomain);
		FunctionTerm isDef = stdlFunction.stdlFunc("isDef", lt);
		newCondRule.setGuard(isDef);

		Map<VariableTerm, Term> map = new HashMap<>();
		map.put(var, lt);
		trv.addMap(map);
		Rule newDoRule = visit(chooseRule.getDoRule());
		trv.removeMap(map);
		newCondRule.setThenRule(newDoRule);
		return newCondRule;
	}

	@Override
	public String getCode() {
		return "ChR";
	}
}
