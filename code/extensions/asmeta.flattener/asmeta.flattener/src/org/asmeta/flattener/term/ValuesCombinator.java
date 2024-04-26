package org.asmeta.flattener.term;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

public class ValuesCombinator {

	public static void combineValuesFromVars(List<VariableTerm> vars, int index, ArrayList<Term[]> result,
			Stack<Term> stack, Map<String, Set<Term>> domainSet) throws Exception {
		List<Domain> domains = new ArrayList<Domain>();
		for (VariableTerm var : vars) {
			domains.add(var.getDomain());
		}
		ValuesCombinator.combineValues(domains, index, result, stack, domainSet);
	}

	private static void combineValues(List<Domain> domains, int index, ArrayList<Term[]> result, Stack<Term> stack,
			Map<String, Set<Term>> domainSet) throws Exception {
		Domain domain = domains.get(index);
		Set<Term> values = domainSet.get(domain.getName());
		if (values == null) {
			System.err.println("Domain " + domain.getName() + " cannot be combined!");
			throw new Exception("Domain " + domain.getName() + " cannot be combined!");
		}
		for (Term value : values) {
			stack.push(value);
			// it this is the last domain
			if (domains.size() == index + 1) {
				Term[] temp = new Term[stack.size()];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = stack.get(i);
				}
				result.add(temp);
			} else {
				combineValues(domains, index + 1, result, stack, domainSet);
			}
			stack.pop();
		}
	}
}
