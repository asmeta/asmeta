package org.asmeta.flattener.undef;

import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.SetTerm;
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
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;

public class GetUndefInTerm extends ReflectiveVisitor<Boolean> {
	
	private static GetUndefInTerm getUndef = new GetUndefInTerm();

	public static Boolean getUndefInTerm(Term term) {
		return getUndef.visit(term);
	}
	
	public boolean visit(VariableTerm varTerm) {
		return false;
	}

	public boolean visit(NaturalTerm natTerm) {
		return false;
	}

	public boolean visit(BooleanTerm boolTerm) {
		return false;
	}

	public boolean visit(EnumTerm enumTerm) {
		return false;
	}

	public boolean visit(IntegerTerm intTerm) {
		return false;
	}

	public boolean visit(UndefTerm undefTerm) {
		return true;
	}

	public boolean visit(LetTerm letTerm) {
		for (Term t : letTerm.getAssignmentTerm()) {
			if (Boolean.TRUE.equals(visit(t))) {
				return true;
			}
		}
		return visit(letTerm.getBody());
	}

	public boolean visit(SetCt setCt) {
		if (visit(setCt.getGuard())) {
			return true;
		}
		for (Term t : setCt.getRanges()) {
			if (visit(t)) {
				return true;
			}
		}
		return visit(setCt.getTerm());
	}

	public boolean visit(ConditionalTerm condTerm) {
		if (visit(condTerm.getGuard()) || visit(condTerm.getThenTerm())) {
			return true;
		}
		Term elseTerm = condTerm.getElseTerm();
		if (elseTerm != null) {
			if (visit(elseTerm)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(CaseTerm caseTerm) {
		if (visit(caseTerm.getComparedTerm())) {
			return true;
		}
		List<Term> comparingTerm = caseTerm.getComparingTerm();
		List<Term> resultTerms = caseTerm.getResultTerms();
		for (int i = 0; i < comparingTerm.size(); i++) {
			if (visit(comparingTerm.get(i)) || visit(resultTerms.get(i))) {
				return true;
			}
		}
		Term otherTerm = caseTerm.getOtherwiseTerm();
		if (otherTerm != null) {
			if (visit(otherTerm)) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(FiniteQuantificationTerm fqtTerm) {
		if (visit(fqtTerm.getGuard())) {
			return true;
		}
		List<Term> ranges = fqtTerm.getRanges();
		for (int i = 0; i < ranges.size(); i++) {
			if (visit(ranges.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean visit(ForallTerm forallTerm) {
		return visit((FiniteQuantificationTerm) forallTerm);
	}

	public boolean visit(ExistTerm existTerm) {
		return visit((FiniteQuantificationTerm) existTerm);
	}

	public boolean visit(ExistUniqueTerm existUniqueTerm) {
		return visit((FiniteQuantificationTerm) existUniqueTerm);
	}

	public boolean visit(TupleTerm tupleTerm) throws Exception {
		for (Term t : tupleTerm.getTerms()) {
			if (visit(t)) {
				return true;
			}
		}
		return false;
	}

	private boolean visitLocFun(Function fun, TupleTerm args) {
		if (args != null) {
			for (Term t : args.getTerms()) {
				if (visit(t)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean visit(LocationTerm locTerm) throws Exception {
		return visitLocFun(locTerm.getFunction(), locTerm.getArguments());
	}

	public boolean visit(FunctionTerm funcTerm) throws Exception {
		return visitLocFun(funcTerm.getFunction(), funcTerm.getArguments());
	}

	public boolean visit(MapTerm mapTerm) {
		return false;
	}

	public boolean visit(SetTerm setTerm) {
		return false;
	}

	public boolean visit(StringTerm strTerm) {
		return false;
	}
}
