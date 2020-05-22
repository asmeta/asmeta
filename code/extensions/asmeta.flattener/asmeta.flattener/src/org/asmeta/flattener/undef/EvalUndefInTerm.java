package org.asmeta.flattener.undef;

import java.util.List;
import java.util.Map;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.domains.Domain;
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

public class EvalUndefInTerm extends ReflectiveVisitor<Term> {
	private Map<Domain, Term> mapDomainsUndefTerm;
	private Domain domain;
	private boolean found;

	public EvalUndefInTerm(Map<Domain, Term> mapDomainsUndefTerm, Domain domain) {
		this.mapDomainsUndefTerm = mapDomainsUndefTerm;
		this.domain = domain;
	}

	public Term visit(VariableTerm varTerm) {
		return varTerm;
	}

	public Term visit(NaturalTerm natTerm) {
		return natTerm;
	}

	public Term visit(BooleanTerm boolTerm) {
		return boolTerm;
	}

	public Term visit(EnumTerm enumTerm) {
		return enumTerm;
	}

	public Term visit(IntegerTerm intTerm) {
		return intTerm;
	}

	public Term visit(UndefTerm undefTerm) {
		found = true;
		return mapDomainsUndefTerm.get(domain);
	}

	public Term visit(LetTerm letTerm) {
		for (Term t : letTerm.getAssignmentTerm()) {
			Term res = visit(t);
			if(found) {
				return res;
			}
		}
		return letTerm;
	}

	public Term visit(SetCt setCt) {
		Term res = visit(visit(setCt.getGuard()));
		if(found) {
			return res;
		}
		for (Term t : setCt.getRanges()) {
			res = visit(t);
			if(found) {
				return res;
			}
		}
		res = visit(setCt.getTerm());
		if(found) {
			return res;
		}
		return setCt;
	}

	public Term visit(ConditionalTerm condTerm) {
		Term res = visit(visit(condTerm.getGuard()));
		if(found) {
			return res;
		}
		res = visit(visit(condTerm.getThenTerm()));
		if(found) {
			return res;
		}
		Term elseTerm = condTerm.getElseTerm();
		if (elseTerm != null) {
			res = visit(visit(elseTerm));
			if(found) {
				return res;
			}
		}
		return condTerm;
	}

	public Term visit(CaseTerm caseTerm) {
		Term res = visit(visit(caseTerm.getComparedTerm()));
		if(found) {
			return res;
		}
		List<Term> comparingTerm = caseTerm.getComparingTerm();
		List<Term> resultTerms = caseTerm.getResultTerms();
		for (int i = 0; i < comparingTerm.size(); i++) {
			res = visit(visit(comparingTerm.get(i)));
			if(found) {
				return res;
			}
			res = visit(visit(resultTerms.get(i)));
			if(found) {
				return res;
			}
		}
		Term otherTerm = caseTerm.getOtherwiseTerm();
		if (otherTerm != null) {
			res = visit(visit(otherTerm));
			if(found) {
				return res;
			}
		}
		return caseTerm;
	}

	public Term visit(FiniteQuantificationTerm fqtTerm) {
		Term res = visit(visit(fqtTerm.getGuard()));
		if(found) {
			return res;
		}
		List<Term> ranges = fqtTerm.getRanges();
		for (int i = 0; i < ranges.size(); i++) {
			res = visit(visit(ranges.get(i)));
			if(found) {
				return res;
			}
		}
		return fqtTerm;
	}

	public Term visit(ForallTerm forallTerm) {
		return visit((FiniteQuantificationTerm) forallTerm);
	}

	public Term visit(ExistTerm existTerm) {
		return visit((FiniteQuantificationTerm) existTerm);
	}

	public Term visit(ExistUniqueTerm existUniqueTerm) {
		return visit((FiniteQuantificationTerm) existUniqueTerm);
	}

	public Term visit(TupleTerm tupleTerm) throws Exception {
		for (Term t : tupleTerm.getTerms()) {
			Term res = visit(visit(t));
			if(found) {
				return res;
			}
		}
		return tupleTerm;
	}

	public Term visit(LocationTerm locTerm) throws Exception {
		TupleTerm args = locTerm.getArguments();
		if (args != null) {
			for (Term t : args.getTerms()) {
				Term res = visit(visit(t));
				if(found) {
					return res;
				}
			}
		}
		return locTerm;
	}

	public Term visit(FunctionTerm funcTerm) throws Exception {
		TupleTerm args = funcTerm.getArguments();
		if (args != null) {
			for (Term t : args.getTerms()) {
				Term res = visit(visit(t));
				if(found) {
					return res;
				}
			}
		}
		return funcTerm;
	}

	public Term visit(MapTerm mapTerm) {
		return mapTerm;
	}

	public Term visit(SetTerm setTerm) {
		return setTerm;
	}

	public Term visit(StringTerm strTerm) {
		return strTerm;
	}
}
