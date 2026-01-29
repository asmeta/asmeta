package org.asmeta.simulator.util;

import java.util.List;

import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;

/**
 * PA (31/10/2010) It discovers if a term contains monitored functions. It is
 * used in the invariant verification to discover if an invariant contains
 * monitored functions. Indeed an invariant, depending on the fact that it
 * contains or does not contain monitored functions, must be evaluated in
 * different points of the execution.
 *
 */
public class MonitoredFinder extends ReflectiveVisitor<Boolean> {

	public boolean visit(Term term) {
		// System.out.println(term.getClass().getSimpleName());
		return invokeMethod(term);
		// return (Boolean) visit(term);
		// return visit(term);
		// return (Boolean) visit((Object) term);
	}

	public boolean visit(VariableTerm variable) {
		return false;
	}

	public boolean visit(SetCt setCtTerm) {
		return visit(setCtTerm.getGuard()) && visit(setCtTerm.getTerm());
	}

	public boolean visit(FunctionTerm funcTerm) {
		return Defs.isMonitored(funcTerm.getFunction()) || visit(funcTerm.getArguments());
	}

	public boolean visit(TupleTerm tupleTerm) {
		if (tupleTerm != null) {
			//assert tupleTerm.getTerms().size() == tupleTerm.getArity();
			List<Term> terms = tupleTerm.getTerms();
			for (Term term : terms) {
				if (visit(term)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean visit(LocationTerm loc) {
		return Defs.isMonitored(loc.getFunction()) || visit(loc.getArguments());
	}

	public boolean visit(NaturalTerm number) {
		return false;
	}

	public boolean visit(IntegerTerm number) {
		return false;
	}

	public boolean visit(EnumTerm term) {
		return false;
	}

	public boolean visit(BooleanTerm bool) {
		return false;
	}

	public boolean visit(UndefTerm undef) {
		return false;
	}

	public boolean visit(DomainTerm term) {
		return false;
	}

	public boolean visit(ExistTerm existTerm) throws Exception {
		return visit(existTerm.getGuard());
	}

	public boolean visit(ExistUniqueTerm exitUniqueTerm) {
		return visit(exitUniqueTerm.getGuard());
	}

	public boolean visit(StringTerm term) {
		return false;
	}

	public boolean visit(ForallTerm forallTerm) {
		return visit(forallTerm.getGuard());
	}

	public boolean visit(CaseTerm caseTerm) {
		if (visit(caseTerm.getComparedTerm())) {
			return true;
		}
		Term otherTerm = caseTerm.getOtherwiseTerm();
		if (otherTerm != null) {
			if (visit(otherTerm)) {
				return true;
			}
		}
		return false;
	}
}
