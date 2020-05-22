package org.asmeta.flattener.util;

import java.util.Collection;
import java.util.Iterator;

import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;

public class StdlFunction {
	private Asm asm;

	public StdlFunction(Asm asm) {
		this.asm = asm;
	}

	public FunctionTerm not(Term term) {
		return stdlFunc("not", term);
	}

	public FunctionTerm eq(Term term1, Term term2) {
		return stdlFunc("eq", term1, term2);
	}

	public Term and(Collection<Term> operands) {
		return applyToList(operands, "and");
	}

	public Term or(Collection<Term> operands) {
		return applyToList(operands, "or");
	}

	private Term applyToList(Collection<Term> operands, String operator) {
		Iterator<Term> it = operands.iterator();
		if (operands.size() > 1) {
			Term first = it.next();
			Term second = it.next();
			FunctionTerm and = stdlFunc(operator, first, second);
			while (it.hasNext()) {
				and = stdlFunc(operator, and, it.next());
			}
			return and;
		} else {
			assert operands.size() == 1;
			return it.next();
		}
	}

	public FunctionTerm stdlFunc(String funcName, Term... arguments) {
		EList<Function> funcsSTDL = asm.getHeaderSection().getImportClause().get(0).getImportedFunction();
		Function not = null;
		for (Function f : funcsSTDL) {
			if (f.getName().equals(funcName)) {
				not = f;
				break;
			}
		}
		RuleFactory ruleFact = new RuleFactory();
		FunctionTerm ft = ruleFact.createFunctionTerm();
		DomainsFactory df = DomainsFactory.eINSTANCE;
		BooleanDomain boolDom = df.createBooleanDomain();
		ft.setDomain(boolDom);
		ft.setFunction(not);
		TupleTerm args = ruleFact.createTupleTerm();
		args.setArity(arguments.length);
		for (Term t : arguments) {
			args.getTerms().add(t);
		}
		ft.setArguments(args);
		return ft;
	}

	public Term neq(Term term1, Term term2) {
		return stdlFunc("neq", term1, term2);
	}
}
