package org.asmeta.simulator.util;

import java.util.List;

import org.asmeta.parser.util.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
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
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
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
public class TermChecker extends ReflectiveVisitor<Boolean> {

	
	private java.util.function.Function<Function, Boolean> checkFunction; 
	
	
	private TermChecker(java.util.function.Function<Function, Boolean> checkFunction) {
		this.checkFunction = checkFunction;
	}
	
	// return true if the term contains a monitored function
	public static TermChecker monitoredFinder = new TermChecker(f -> Defs.isMonitored(f));
	// idem for controlled and shared and out functions
	public static TermChecker controlledFinder = new TermChecker(f -> Defs.isControlled(f));
	public static TermChecker outFinder = new TermChecker(f -> Defs.isOut(f));
	public static TermChecker sharedFinder = new TermChecker(f -> Defs.isShared(f));
	

	
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
		// if it is monitored or its arguments are
		if (visit(funcTerm.getArguments())) return true;
		Function function = funcTerm.getFunction();
		if (checkFunction.apply(function)) return true;
		// if it is derived and its definition contains a monitored
		if (Defs.isDerived(function) && visit(((asmeta.definitions.DerivedFunction) function).getDefinition().getBody()))
				return true;
		return false;
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
		return checkFunction.apply(loc.getFunction()) || visit(loc.getArguments());
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

	public boolean visit(RealTerm term) {
		return false;
	}
	
	public boolean visit(asmeta.terms.furtherterms.ConditionalTerm ct) {
		return visit(ct.getGuard()) || visit(ct.getThenTerm()) || (ct.getElseTerm() != null && visit(ct.getElseTerm()));
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
	
	public boolean visit(LetTerm letTerm) {
		return visit(letTerm.getBody());
	}

}
