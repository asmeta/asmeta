package org.asmeta.flattener.term;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.structure.Asm;
import asmeta.structure.FunctionDefinition;
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

public class TermDerivedRemover extends ReflectiveVisitor<Term> {
	private RuleFactory ruleFact = new RuleFactory();
	private List<FunctionDefinition> funcDefs;

	public TermDerivedRemover(Asm asm) {
		funcDefs = asm.getBodySection().getFunctionDefinition();
	}

	public Term visit(BooleanTerm boolTerm) {
		return boolTerm;
	}

	public Term visit(CaseTerm caseTerm) {
		CaseTerm newCaseTerm = ruleFact.createCaseTerm();
		newCaseTerm.setComparedTerm(visit(caseTerm.getComparedTerm()));
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
		newCaseTerm.setDomain(caseTerm.getDomain());
		assert newCaseTerm.getDomain() != null;
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
		//TODO
		return null;
	}

	// TODO check whether it is correct. should we also visit the map term?
	public Term visit(MapTerm mapTerm) {
		assert false;
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
		return varTerm;
	}
}
