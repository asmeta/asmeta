package org.asmeta.flattener.term;

import java.util.List;

import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.BooleanTerm;
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

public class ReplaceTerm extends TermFlattenerVisitor {
	private String termToChange;
	private Term newTerm;
	private String newTermAsStr;
	private RuleFactory ruleFact;

	public ReplaceTerm(String termToChange, Term newTerm) {
		this.termToChange = termToChange;
		this.newTerm = newTerm;
		newTermAsStr = Utils.print(newTerm);
		ruleFact = new RuleFactory();
	}

	private boolean mustBeChanged(Term t) {
		return Utils.print(t).equals(termToChange);
	}

	public Term visit(VariableTerm vt) {
		if (mustBeChanged(vt)) {
			return newTerm;
		}
		return vt;
	}

	public Term visit(LocationTerm lt) {
		if (mustBeChanged(lt)) {
			return newTerm;
		}

		LocationTerm newLocTerm = ruleFact.createLocationTerm();

		newLocTerm.setFunction(lt.getFunction());
		newLocTerm.setDomain(lt.getDomain());

		// set arguments in new LocationTerm
		TupleTerm tupleTerm = lt.getArguments();
		if (tupleTerm != null) {
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
		if (ft.getFunction().getName().equals("eq")) {
			List<Term> args = ft.getArguments().getTerms();
			String firstArg = Utils.print(args.get(0));
			String secondArg = Utils.print(args.get(1));
			if ((firstArg.equals(termToChange) && secondArg.equals(newTermAsStr))
					|| (secondArg.equals(termToChange) && firstArg.equals(newTermAsStr))) {
				return ft;
			}
		}

		if (mustBeChanged(ft)) {
			return newTerm;
		}

		FunctionTerm newFunctionTerm = ruleFact.createFunctionTerm();
		newFunctionTerm.setDomain(ft.getDomain());
		newFunctionTerm.setFunction(ft.getFunction());

		// set arguments in new FunctionTerm
		if (ft.getArguments() != null) {
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
		if (mustBeChanged(it)) {
			return newTerm;
		}
		return it;
	}

	public Term visit(StringTerm strTerm) {
		if (mustBeChanged(strTerm)) {
			return newTerm;
		}
		return strTerm;
	}

	public Term visit(EnumTerm et) {
		if (mustBeChanged(et)) {
			return newTerm;
		}
		return et;
	}

	public Term visit(BooleanTerm bt) {
		if (mustBeChanged(bt)) {
			return newTerm;
		}
		return bt;
	}

	public Term visit(ConditionalTerm ct) {
		if (mustBeChanged(ct)) {
			return newTerm;
		}
		ConditionalTerm newConditionalTerm = ruleFact.createConditionalTerm();
		newConditionalTerm.setDomain(ct.getDomain());
		newConditionalTerm.setElseTerm(visit(ct.getElseTerm()));
		newConditionalTerm.setGuard(visit(ct.getGuard()));
		newConditionalTerm.setThenTerm(visit(ct.getElseTerm()));
		return newConditionalTerm;
	}

	public Term visit(CaseTerm ct) {
		if (mustBeChanged(ct)) {
			return newTerm;
		}
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
		return newCaseTerm;
	}

	public Term visit(ForallTerm forallTerm) {
		if (mustBeChanged(forallTerm)) {
			return newTerm;
		}
		ForallTerm newForallTerm = ruleFact.createForallTerm();
		newForallTerm.setGuard(visit(forallTerm.getGuard()));
		newForallTerm.getRanges().addAll(forallTerm.getRanges());
		newForallTerm.getVariable().addAll(forallTerm.getVariable());
		return newForallTerm;
	}

	public Term visit(TupleTerm tt) {
		if (mustBeChanged(tt)) {
			return newTerm;
		}
		return tt;
	}

	public Term visit(DomainTerm dt) {
		if (mustBeChanged(dt)) {
			return newTerm;
		}
		return dt;
	}

	public Term visit(NaturalTerm nt) {
		if (mustBeChanged(nt)) {
			return newTerm;
		}
		return nt;
	}

	public Term visit(UndefTerm ut) {
		if (mustBeChanged(ut)) {
			return newTerm;
		}
		return ut;
	}
}
