package org.asmeta.flattener.undef;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class GetUndefSource extends ReflectiveVisitor<Set<Function>> {
	private static GetUndefSource gus = new GetUndefSource();

	public static Set<Function> getUndefSources(Asm asm) {
		Set<Function> funcsWithUndef = new HashSet<>();
		List<Function> funcs = asm.getHeaderSection().getSignature().getFunction();
		Set<Function> funcsWithInit = new HashSet<>();
		for (FunctionInitialization fi : asm.getDefaultInitialState().getFunctionInitialization()) {
			funcsWithInit.add(fi.getInitializedFunction());
		}
		for (Function f : funcs) {
			if (Defs.isControlled(f) && !funcsWithInit.contains(f)) {
				funcsWithUndef.add(f);
			}
		}
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			funcsWithUndef.addAll(gus.visit(rd.getRuleBody()));
		}
		return funcsWithUndef;
	}

	public Set<Function> visitList(List<Rule> rules) {
		Set<Function> funcs = new HashSet<>();
		for (Rule b : rules) {
			funcs.addAll(visit(b));
		}
		return funcs;
	}

	public Set<Function> visit(BlockRule blockRule) {
		return visitList(blockRule.getRules());
	}

	public Set<Function> visit(SeqRule seqRule) {
		return visitList(seqRule.getRules());
	}

	public Set<Function> visit(CaseRule caseRule) {
		Set<Function> funcs = new HashSet<>();
		for (Rule branch : caseRule.getCaseBranches()) {
			funcs.addAll(visit(branch));
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			funcs.addAll(visit(otherwise));
		}
		return funcs;
	}

	public Set<Function> visit(ChooseRule chooseRule) {
		Set<Function> funcs = new HashSet<>();
		visit(chooseRule.getDoRule());
		Rule ifNone = chooseRule.getIfnone();
		if (ifNone != null) {
			funcs.addAll(visit(ifNone));
		}
		return funcs;
	}

	public Set<Function> visit(ConditionalRule conditionalRule) {
		Set<Function> funcs = new HashSet<>();
		funcs.addAll(visit(conditionalRule.getThenRule()));
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			funcs.addAll(visit(elseRule));
		}
		return funcs;
	}

	public Set<Function> visit(ExtendRule extendRule) {
		Set<Function> funcs = new HashSet<>();
		funcs.addAll(visit(extendRule.getDoRule()));
		return funcs;
	}

	public Set<Function> visit(ForallRule forallRule) {
		Set<Function> funcs = new HashSet<>();
		funcs.addAll(visit(forallRule.getDoRule()));
		return funcs;
	}

	public Set<Function> visit(LetRule letRule) {
		Set<Function> funcs = new HashSet<>();
		funcs.addAll(visit(letRule.getInRule()));
		return funcs;
	}

	public Set<Function> visit(MacroCallRule macroCallRule) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(SkipRule skipRule) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(TermAsRule termAsRule) {
		return Collections.EMPTY_SET;
	}

	public Set<Function> visit(UpdateRule updateRule) {
		Set<Function> funcs = new HashSet<>();
		Term location = updateRule.getLocation();
		if (location instanceof LocationTerm) {
			Function func = ((LocationTerm) location).getFunction();
			if (GetUndefInTerm.getUndefInTerm(updateRule.getUpdatingTerm())) {
				funcs.add(func);
			}
		}
		return funcs;
	}
}
