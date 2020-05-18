package org.asmeta.flattener.undef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
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

public class GetFunctionsInTerm extends ReflectiveVisitor<List<Function>> {

	public List<Function> visit(VariableTerm varTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(NaturalTerm natTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(BooleanTerm boolTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(EnumTerm enumTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(IntegerTerm intTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(UndefTerm undefTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(LetTerm letTerm) {
		List<Function> functions = new ArrayList<>();
		for (Term t : letTerm.getAssignmentTerm()) {
			functions.addAll(visit(t));
		}
		functions.addAll(visit(letTerm.getBody()));
		return functions;
	}

	public List<Function> visit(SetCt setCt) {
		List<Function> functions = new ArrayList<>();
		functions.addAll(visit(setCt.getGuard()));
		for (Term t : setCt.getRanges()) {
			functions.addAll(visit(t));
		}
		functions.addAll(visit(setCt.getTerm()));
		return functions;
	}

	public List<Function> visit(ConditionalTerm condTerm) {
		List<Function> functions = new ArrayList<>();
		functions.addAll(visit(condTerm.getGuard()));
		functions.addAll(visit(condTerm.getThenTerm()));
		Term elseTerm = condTerm.getElseTerm();
		if (elseTerm != null) {
			functions.addAll(visit(elseTerm));
		}
		return functions;
	}

	public List<Function> visit(CaseTerm caseTerm) {
		List<Function> functions = new ArrayList<>();
		functions.addAll(visit(caseTerm.getComparedTerm()));
		List<Term> comparingTerm = caseTerm.getComparingTerm();
		List<Term> resultTerms = caseTerm.getResultTerms();
		for (int i = 0; i < comparingTerm.size(); i++) {
			functions.addAll(visit(comparingTerm.get(i)));
			functions.addAll(visit(resultTerms.get(i)));
		}
		Term otherTerm = caseTerm.getOtherwiseTerm();
		if (otherTerm != null) {
			functions.addAll(visit(otherTerm));
		}
		return functions;
	}

	public List<Function> visit(DomainTerm dt) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(FiniteQuantificationTerm fqtTerm) {
		List<Function> functions = new ArrayList<>();
		functions.addAll(visit(fqtTerm.getGuard()));
		List<Term> ranges = fqtTerm.getRanges();
		for (int i = 0; i < ranges.size(); i++) {
			functions.addAll(visit(ranges.get(i)));
		}
		return functions;
	}

	public List<Function> visit(ForallTerm forallTerm) {
		return visit((FiniteQuantificationTerm) forallTerm);
	}

	public List<Function> visit(ExistTerm existTerm) {
		return visit((FiniteQuantificationTerm) existTerm);
	}

	public List<Function> visit(ExistUniqueTerm existUniqueTerm) {
		return visit((FiniteQuantificationTerm) existUniqueTerm);
	}

	public List<Function> visit(TupleTerm tupleTerm) throws Exception {
		List<Function> functions = new ArrayList<>();
		for (Term t : tupleTerm.getTerms()) {
			functions.addAll(visit(t));
		}
		return functions;
	}

	private List<Function> visitLocFun(Function fun, TupleTerm args) {
		List<Function> functions = new ArrayList<>();
		functions.add(fun);
		if (args != null) {
			for (Term t : args.getTerms()) {
				functions.addAll(visit(t));
			}
		}
		return functions;
	}

	public List<Function> visit(LocationTerm locTerm) throws Exception {
		return visitLocFun(locTerm.getFunction(), locTerm.getArguments());
	}

	public List<Function> visit(FunctionTerm funcTerm) throws Exception {
		return visitLocFun(funcTerm.getFunction(), funcTerm.getArguments());
	}

	public List<Function> visit(MapTerm mapTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(SetTerm setTerm) {
		return Collections.EMPTY_LIST;
	}

	public List<Function> visit(StringTerm strTerm) {
		return Collections.EMPTY_LIST;
	}
}
