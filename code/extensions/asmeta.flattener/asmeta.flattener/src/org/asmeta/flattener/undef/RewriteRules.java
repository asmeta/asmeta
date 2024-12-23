package org.asmeta.flattener.undef;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.flattener.args.RemoveArgumentsRuleFlattener;
import org.asmeta.flattener.args.RemoveArgumentsTermFlattener;
import org.asmeta.flattener.rule.RuleFlattener;
import org.asmeta.simulator.util.StdlFunction;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

public class RewriteRules extends RuleFlattener {
	private Set<Function> funcsWithUndef;
	private Map<Domain, Term> mapDomainsUndefTerm;

	public RewriteRules(Asm asm, Set<Function> funcsWithUndef, Map<Domain, Term> mapDomainsUndefTerm) {
		super(asm);
		this.funcsWithUndef = funcsWithUndef;
		this.mapDomainsUndefTerm = mapDomainsUndefTerm;
	}

	@Override
	public Rule visit(UpdateRule ur) {
		LocationTerm lt = (LocationTerm) ur.getLocation();
		Function func = lt.getFunction();
		if (funcsWithUndef.contains(func)) {
			Term ut = ur.getUpdatingTerm();
			EvalUndefInTerm eu = new EvalUndefInTerm(mapDomainsUndefTerm, func.getCodomain());
			Term newTerm = eu.visit(ut);
			if (newTerm != ut) {
				UpdateRule newUr = ruleFact.createUpdateRule();
				newUr.setLocation(lt);
				newUr.setUpdatingTerm(newTerm);
				return newUr;
			}
			RemoveArgumentsTermFlattener ratf = new RemoveArgumentsTermFlattener();
			try {
				ratf.visit(ur.getUpdatingTerm());
				LinkedHashMap<VariableTerm, Term> mapForLet = ratf.getMapForLet();
				if (mapForLet.size() > 0) {
					LetRule letRule = RemoveArgumentsRuleFlattener.buildLetRule(ur, mapForLet, ruleFact);
					StdlFunction stdl = new StdlFunction(asm);
					List<Term> neqs = new ArrayList<>();
					for (Term i : letRule.getVariable()) {
						if (mapDomainsUndefTerm.containsKey(i.getDomain())) {
							neqs.add(stdl.neq(i, mapDomainsUndefTerm.get(i.getDomain())));
						}
					}
					Term guard = stdl.and(neqs);
					ConditionalRule condRule = ruleFact.createConditionalRule();
					condRule.setGuard(guard);
					condRule.setThenRule(letRule.getInRule());
					UpdateRule elseRule = ruleFact.createUpdateRule();
					elseRule.setLocation(lt);
					elseRule.setUpdatingTerm(mapDomainsUndefTerm.get(lt.getDomain()));
					condRule.setElseRule(elseRule);
					letRule.setInRule(condRule);
					return letRule;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Error();
			}
		}
		return ur;
	}

	@Override
	public String getCode() {
		return "RR";
	}
}
