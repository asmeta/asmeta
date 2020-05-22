package org.asmeta.flattener.term;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;

public class TermRenameVars extends TermFlattenerVisitor {
	static final Logger logger = Logger.getLogger(TermRenameVars.class);
	private Map<VariableTerm, Term> variableMap;
	public Term currentSelf;
	// private Map<String, VariableTerm> varNames;
	private RuleFactory ruleFact = new RuleFactory();

	public TermRenameVars() {
		resetVariableMap();
	}

	public TermRenameVars(Map<VariableTerm, Term> variableMap) {
		setVariableMap(variableMap);
	}

	public void addMap(Map<VariableTerm, Term> variableMap) {
		/*
		 * for(Entry<VariableTerm, Term> e: variableMap.entrySet()) { VariableTerm key =
		 * e.getKey(); Term value = e.getValue(); assert
		 * !this.variableMap.containsKey(key); if(this.variableMap.containsValue(key)) {
		 * for(VariableTerm a: this.variableMap.keySet()) { if(this.variableMap.get(a)
		 * == key) { this.variableMap.put(a, value); } } } this.variableMap.put(key,
		 * value); }
		 */
		logger.debug("newMap " + variableMap);
		for (Entry<VariableTerm, Term> e : variableMap.entrySet()) {
			assert !this.variableMap.containsKey(e.getKey());
			this.variableMap.put(e.getKey(), e.getValue());
		}
		/*
		 * for(VariableTerm var: variableMap.keySet()) { String varName = var.getName();
		 * assert !varNames.containsKey(varName); varNames.put(varName, var); } assert
		 * this.variableMap.size() == varNames.size();
		 */
	}

	public void removeMap(Map<VariableTerm, Term> variableMap) {
		for (Entry<VariableTerm, Term> e : variableMap.entrySet()) {
			this.variableMap.remove(e.getKey(), e.getValue());
		}
		/*
		 * for(VariableTerm var: variableMap.keySet()) { String varName = var.getName();
		 * assert varNames.containsKey(varName): varName; varNames.remove(varName); }
		 * assert this.variableMap.size() == varNames.size();
		 */
	}

	public void resetVariableMap() {
		this.variableMap = new HashMap<>();
		// varNames = new HashMap<String, VariableTerm>();
	}

	public void setVariableMap(Map<VariableTerm, Term> variableMap) {
		this.variableMap = variableMap;
		/*
		 * varNames = new HashMap<>(); for(VariableTerm var: variableMap.keySet()) {
		 * String varName = var.getName(); assert !varNames.containsKey(varName);
		 * varNames.put(varName, var); } assert this.variableMap.size() ==
		 * varNames.size();
		 */
	}

	public Term visit(BooleanTerm boolTerm) {
		return boolTerm;
	}

	public Term visit(CaseTerm caseTerm) {
		CaseTerm newCaseTerm = ruleFact.createCaseTerm();
		newCaseTerm.setComparedTerm(visit(caseTerm.getComparedTerm()));
		newCaseTerm.setDomain(caseTerm.getDomain());
		Term oTerm = caseTerm.getOtherwiseTerm();
		if (oTerm != null) {
			newCaseTerm.setOtherwiseTerm(visit(oTerm));
		}
		List<Term> newResultTerms = new ArrayList<Term>();
		for (Term rt : caseTerm.getResultTerms()) {
			newResultTerms.add(visit(rt));
		}
		newCaseTerm.getResultTerms().clear();
		newCaseTerm.getResultTerms().addAll(newResultTerms);
		return newCaseTerm;
	}

	public Term visit(ConditionalTerm condTerm) {
		ConditionalTerm newCondTerm = ruleFact.createConditionalTerm();
		newCondTerm.setGuard(visit(condTerm.getGuard()));
		newCondTerm.setThenTerm(visit(condTerm.getThenTerm()));
		Term elseTerm = condTerm.getElseTerm();
		if (elseTerm != null) {
			newCondTerm.setElseTerm(visit(elseTerm));
		}
		return newCondTerm;
	}

	public Term visit(EnumTerm enumTerm) {
		return enumTerm;
	}

	public Term visit(ExistTerm existTerm) {
		return visit(existTerm, ruleFact.createExistTerm());
	}

	public Term visit(ExistUniqueTerm existUniqueTerm) {
		return visit(existUniqueTerm, ruleFact.createExistUniqueTerm());
	}

	public Term visit(FiniteQuantificationTerm fqtTerm, FiniteQuantificationTerm newFqtTerm) {
		List<VariableTerm> variables = newFqtTerm.getVariable();
		for (VariableTerm v : fqtTerm.getVariable()) {
			VariableTerm vt = ruleFact.createVariableTerm();
			vt.setDomain(v.getDomain());
			vt.setFiniteQuantificationTerm(newFqtTerm);
			vt.setKind(v.getKind());
			vt.setName(v.getName());
			variables.add(vt);
		}
		newFqtTerm.setDomain(fqtTerm.getDomain());
		List<Term> ranges = newFqtTerm.getRanges();
		for (Term r : fqtTerm.getRanges()) {
			ranges.add(r);
		}
		Term newGuard = visit(fqtTerm.getGuard());
		newFqtTerm.setGuard(newGuard);
		assert variables.size() > 0;
		assert variables.size() == ranges.size();
		assert variables.size() == fqtTerm.getVariable().size();
		return newFqtTerm;
	}

	public Term visit(ForallTerm forallTerm) {
		return visit(forallTerm, ruleFact.createForallTerm());
	}

	public Term visit(FunctionTerm funcTerm) throws Exception {
		FunctionTerm newFunctionTerm = ruleFact.createFunctionTerm();
		newFunctionTerm.setDomain(funcTerm.getDomain());
		newFunctionTerm.setFunction(funcTerm.getFunction());

		List<Term> newTerms = new ArrayList<Term>();
		TupleTerm arguments = funcTerm.getArguments();
		if (arguments != null) {
			for (Term term : arguments.getTerms()) {
				newTerms.add(visit(term));
			}
			TupleTerm newTupleTerm = ruleFact.createTupleTerm();
			newTupleTerm.setArity(arguments.getArity());
			newTupleTerm.getTerms().addAll(newTerms);
			newFunctionTerm.setArguments(newTupleTerm);
		}
		return newFunctionTerm;
	}

	public Term visit(IntegerTerm intTerm) {
		return intTerm;
	}

	public Term visit(LetTerm letTerm) {
		return letTerm;
	}

	public Term visit(LocationTerm locTerm) throws Exception {
		// System.out.println(locTerm.getFunction().getName());
		String locName = locTerm.getFunction().getName();
		if (locName.equals("self") && currentSelf != null) {
			return currentSelf;
		} else {
			LocationTerm newLocationTerm = ruleFact.createLocationTerm();
			newLocationTerm.setDomain(locTerm.getDomain());
			newLocationTerm.setFunction(locTerm.getFunction());

			TupleTerm arguments = locTerm.getArguments();
			if (arguments != null) {
				List<Term> newTerms = new ArrayList<Term>();
				for (Term term : arguments.getTerms()) {
					newTerms.add(visit(term));
				}
				TupleTerm newTupleTerm = ruleFact.createTupleTerm();
				newTupleTerm.setArity(arguments.getArity());
				newTupleTerm.getTerms().addAll(newTerms);
				newLocationTerm.setArguments(newTupleTerm);
			}
			return newLocationTerm;
		}
	}

	
	private String print(Term t) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visitTerm(t);
		ap.close();
		String tS = sw.toString();
		return tS;
	}
	
	// TODO check whether it is correct. should we also visit the map term?
	public Term visit(MapTerm mapTerm) {
		
		assert false: "MapTerm not supported "+ mapTerm.getDomain().getName();
		return mapTerm;
	}

	public Term visit(NaturalTerm natTerm) {
		return natTerm;
	}

	public Term visit(StringTerm strTerm) {
		return strTerm;
	}

	public Term visit(TupleTerm tupleTerm) throws Exception {
		List<Term> newTerms = new ArrayList<Term>();
		for (Term term : tupleTerm.getTerms()) {
			newTerms.add(visit(term));
		}
		TupleTerm newTupleTerm = ruleFact.createTupleTerm();
		newTupleTerm.setArity(tupleTerm.getArity());
		newTupleTerm.getTerms().addAll(newTerms);
		return newTupleTerm;
	}

	public Term visit(UndefTerm undefTerm) {
		return undefTerm;
	}

	public Term visit(VariableTerm varTerm) {
		if (variableMap.containsKey(varTerm)) {
			logger.debug(variableMap.get(varTerm) + " for " + varTerm);
			return variableMap.get(varTerm);
		}
		/*for(VariableTerm k: variableMap.keySet()) {
			if(k.getName().equals(varTerm.getName())) {
				return variableMap.get(k);
			}
		}*/
		/*
		 * String varName = varTerm.getName(); if (varNames.containsKey(varName)) {
		 * VariableTerm var = varNames.get(varName); assert var != null; Term value =
		 * variableMap.get(var); assert value != null: "variableMap " + variableMap +
		 * "\nvarNames " + varNames; logger.debug(value + " for " + varTerm); return
		 * variableMap.get(var); }
		 */
		logger.debug("No value for " + varTerm + "\n" + variableMap);
		return varTerm;
	}
}
