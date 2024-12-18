package org.asmeta.refinement;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
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
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

public class GetMonitoredFunctionsUsedInTerm extends ReflectiveVisitor<Set<Function>> {
	public final static GetMonitoredFunctionsUsedInTerm INSTANCE = new GetMonitoredFunctionsUsedInTerm();

	private GetMonitoredFunctionsUsedInTerm() {}

	public Set<Function> visit(Term t) {
		return invokeMethod(t, "visit");
	}

	public Set<Function> visit(ConditionalTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getThenTerm()));
		Term elseTerm = t.getElseTerm();
		if(elseTerm != null) {
			funcs.addAll(visit(elseTerm));
		}
		return funcs;
	}

	public Set<Function> visit(CaseTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getComparedTerm()));
		for(Term term: t.getResultTerms()) {
			funcs.addAll(visit(term));
		}
		if(t.getOtherwiseTerm() != null) {
			funcs.addAll(visit(t.getOtherwiseTerm()));
		}
		return funcs;
	}

	public Set<Function> visit(ForallTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getGuard()));
		for(TermAsRule term: t.getTermAsRule()) {
			funcs.addAll(visit(term.getTerm()));
		}
		return funcs;
	}

	public Set<Function> visit(ExistTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getGuard()));
		for(TermAsRule term: t.getTermAsRule()) {
			funcs.addAll(visit(term.getTerm()));
		}
		return funcs;
	}

	public Set<Function> visit(ExistUniqueTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getGuard()));
		for(TermAsRule term: t.getTermAsRule()) {
			funcs.addAll(visit(term.getTerm()));
		}
		return funcs;
	}

	public Set<Function> visit(ConstantTerm t) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(FunctionTerm t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getFunction()));
		TupleTerm args = t.getArguments();
		if(args != null && args.getArity() > 0) {
			funcs.addAll(visit(args));
		}
		return funcs;
	}

	public Set<Function> visit(TupleTerm t) {
		Set<Function> funcs = new HashSet<Function>();
		List<Term> terms = t.getTerms();
		for(Term e: terms) {
			funcs.addAll(visit(e));
		}
		return funcs;
	}

	public Set<Function> visit(MapTerm t) {
		Set<Function> funcs = new HashSet<Function>();
		for(TupleTerm p: t.getPair()) {
			funcs.addAll(visit(p));
		}
		for(TermAsRule r: t.getTermAsRule()) {
			funcs.addAll(visit(r));
		}
		return funcs;
	}

	public Set<Function> visit(TermAsRule t) {
		Set<Function> funcs = new HashSet<Function>(visit(t.getTerm()));
		for(Term e: t.getParameters()) {
			funcs.addAll(visit(e));
		}
		return funcs;
	}

	private Set<Function> visit(Function func) {
		if(Defs.isMonitored(func)) {
			return Collections.singleton(func);
		}
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(LocationTerm t) {
		Function func = t.getFunction();
		return visit(func);
	}

	public Set<Function> visit(VariableTerm t) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(IntegerTerm t) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(EnumTerm t) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(BooleanTerm t) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(Rule r) {
		return invokeMethod(r, "visit");
	}

	public Set<Function> visit(SkipRule r) {
		return Collections.EMPTY_SET;
	}
	
	public Set<Function> visit(UndefTerm r) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(UpdateRule r) {
		Set<Function> funcs = new HashSet<Function>(visit(r.getLocation()));
		funcs.addAll(visit(r.getUpdatingTerm()));
		return funcs;
	}

	public Set<Function> visit(BlockRule r) {
		Set<Function> funcs = new HashSet<Function>();
		for(Rule p: r.getRules()) {
			funcs.addAll(visit(p));
		}
		return funcs;
	}

	public Set<Function> visit(ConditionalRule r) {
		Set<Function> funcs = new HashSet<Function>(visit(r.getGuard()));
		funcs.addAll(visit(r.getThenRule()));
		if(r.getElseRule() != null) {
			funcs.addAll(visit(r.getElseRule()));
		}
		return funcs;
	}

	public Set<Function> visit(CaseRule r) {
		Set<Function> funcs = new HashSet<Function>(visit(r.getTerm()));
		List<Rule> caseBranches = r.getCaseBranches();
		List<Term> caseTerms = r.getCaseTerm();
		for(int i = 0; i < caseBranches.size(); i++) {
			funcs.addAll(visit(caseBranches.get(i)));
			funcs.addAll(visit(caseTerms.get(i)));
		}
		if(r.getOtherwiseBranch() != null) {
			funcs.addAll(visit(r.getOtherwiseBranch()));
		}
		return funcs;
	}

	public Set<Function> visit(ForallRule r) {
		Set<Function> funcs = new HashSet<Function>(visit(r.getGuard()));
		funcs.addAll(visit(r.getDoRule()));
		return funcs;
	}

	public Set<Function> visit(ChooseRule r) {
		Set<Function> funcs = new HashSet<Function>(visit(r.getGuard()));
		funcs.addAll(visit(r.getDoRule()));
		if(r.getIfnone() != null) {
			funcs.addAll(visit(r.getIfnone()));
		}
		return funcs;
	}

	public Set<Function> visit(MacroCallRule r) {
		return new HashSet<Function>(visit(r.getCalledMacro().getRuleBody()));
	}
}