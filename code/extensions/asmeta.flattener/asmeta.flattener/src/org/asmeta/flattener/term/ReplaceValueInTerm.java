package org.asmeta.flattener.term;

import java.util.List;

import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.StringTerm;

public class ReplaceValueInTerm extends TermFlattenerVisitor {
	private Term[] values;
	private List<VariableTerm> vars;
	private RuleFactory ruleFact;

	public ReplaceValueInTerm(Term[] values, List<VariableTerm> vars) {
		assert values.length == vars.size();
		this.values = values;
		this.vars = vars;
		this.ruleFact = new RuleFactory();
	}

	public Term visit(VariableTerm vt) {
		int i = 0;
		for (VariableTerm var : vars) {
			if (vt.getName().equals(var.getName())) {
				ConstantTerm newValue = null;
				if (values[i] instanceof NaturalTerm) {
					newValue = ruleFact.createNaturalTerm();
				} else if (values[i] instanceof IntegerTerm) {
					// modified 01.03.25 - originally was NaturalTerm
					newValue = ruleFact.createIntegerTerm();
				} else if (values[i] instanceof BooleanTerm) {
					newValue = ruleFact.createBooleanTerm();
				} else if (values[i] instanceof EnumTerm) {
					newValue = ruleFact.createEnumTerm();
				} else {
					throw new Error(values[i].getClass().getSimpleName() + " not supported!");
				}
				String symbol = ((ConstantTerm) values[i]).getSymbol();
				newValue.setSymbol(symbol);
				return newValue;
			}
			i++;
		}
		return vt;
	}

	public Term visit(LocationTerm lt) {
		LocationTerm newLocTerm = ruleFact.createLocationTerm();

		newLocTerm.setFunction(lt.getFunction());
		newLocTerm.setDomain(lt.getDomain());

		// set arguments in new LocationTerm
		TupleTerm tupleTerm = lt.getArguments();
		if(tupleTerm != null) {
			TupleTerm newTupleTerm = ruleFact.createTupleTerm();
			newTupleTerm.setArity(tupleTerm.getArity());
			newTupleTerm.setDomain(tupleTerm.getDomain());
			for (Term arg : tupleTerm.getTerms()) {
				Term x = visit(arg);
				newTupleTerm.getTerms().add(x);
			}
			newLocTerm.setArguments(newTupleTerm);
		}
		return newLocTerm;
	}

	public Term visit(FunctionTerm ft) {
		FunctionTerm newFunctionTerm = ruleFact.createFunctionTerm();
		newFunctionTerm.setDomain(ft.getDomain());
		newFunctionTerm.setFunction(ft.getFunction());

		// set arguments in new FunctionTerm
		if(ft.getArguments() != null) {
			TupleTerm tupleTerm = ft.getArguments();
			TupleTerm newTupleTerm = ruleFact.createTupleTerm(); // TODO newLocTerm.getArguments();
			newTupleTerm.setArity(tupleTerm.getArity());
			newTupleTerm.setDomain(tupleTerm.getDomain());
	
			List<Term> terms = newTupleTerm.getTerms();
			for (Term arg : tupleTerm.getTerms()) {
				terms.add(visit(arg));
			}
			newFunctionTerm.setArguments(newTupleTerm);
		}
		return newFunctionTerm;
	}

	public Term visit(IntegerTerm it) {
		return it;
	}
	
	public Term visit(StringTerm strTerm) {
		return strTerm;
	}

	public Term visit(EnumTerm et) {
		return et;
	}

	public Term visit(BooleanTerm bt) {
		return bt;
	}

	public Term visit(ConditionalTerm ct) {
		ConditionalTerm newConditionalTerm = ruleFact.createConditionalTerm();
		newConditionalTerm.setDomain(ct.getDomain());
		newConditionalTerm.setElseTerm(visit(ct.getElseTerm()));
		newConditionalTerm.setGuard(visit(ct.getGuard()));
		newConditionalTerm.setThenTerm(visit(ct.getElseTerm()));
		return newConditionalTerm;
	}

	public Term visit(CaseTerm ct) {
		CaseTerm newCaseTerm = ruleFact.createCaseTerm();
		newCaseTerm.setComparedTerm(visit(ct.getComparedTerm()));
		newCaseTerm.setDomain(ct.getDomain());
		newCaseTerm.setOtherwiseTerm(visit(ct.getComparedTerm()));

		EList<Term> compT = newCaseTerm.getComparingTerm();
		for (Term arg : ct.getComparingTerm()) {
			compT.add(visit(arg));
		}
		EList<Term> resT = newCaseTerm.getResultTerms();
		for (Term arg : ct.getResultTerms()) {
			resT.add(visit(arg));
		}
		return null;
	}

	public Term visit(ForallTerm forallTerm) {
		ForallTerm newForallTerm = ruleFact.createForallTerm();
		newForallTerm.setGuard(visit(forallTerm.getGuard()));
		newForallTerm.getRanges().addAll(forallTerm.getRanges());
		newForallTerm.getVariable().addAll(forallTerm.getVariable());
		return newForallTerm;
	}

	public Term visit(TupleTerm tt) {
		return tt;
	}

	public Term visit(DomainTerm dt) {
		return dt;
	}

	public Term visit(NaturalTerm nt) {
		return nt;
	}

	public Term visit(UndefTerm ut) {
		return ut;
	}
}
