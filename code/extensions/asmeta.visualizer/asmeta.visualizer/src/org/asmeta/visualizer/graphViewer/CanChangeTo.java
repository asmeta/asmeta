package org.asmeta.visualizer.graphViewer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.asmeta.simulator.RuleVisitor;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

/**
 * returns the value to which it can change the term to for now as a string
 */
public class CanChangeTo extends RuleVisitor<Set<String>> {
	private static AdvancedTermPrinter termPrinter = new AdvancedTermPrinter(false);
	private Term term;

	/**
	 *
	 * @param term
	 *            that I want to check if it can be changed and to What
	 */
	public CanChangeTo(Term term) {
		this.term = term;
	}

	@Override
	public Set<String> visit(BlockRule block) {
		Set<String> values = new HashSet<String>();
		for (Rule r : block.getRules()) {
			values.addAll(visit(r));
		}
		return values;
	}

	@Override
	public Set<String> visit(CaseRule rule) {
		Set<String> values = new HashSet<String>();
		for (Rule r : rule.getCaseBranches()) {
			values.addAll(visit(r));
		}
		if (rule.getOtherwiseBranch() != null)
			values.addAll(visit(rule.getOtherwiseBranch()));
		return values;
	}

	@Override
	public Set<String> visit(ChooseRule rule) {
		return visit(rule.getDoRule());
	}

	@Override
	public Set<String> visit(ConditionalRule rule) {
		Set<String> values = new HashSet<String>(visit(rule.getThenRule()));
		Rule elseRule = rule.getElseRule();
		if (elseRule != null) {
			values.addAll(visit(elseRule));
		}
		return values;
	}

	@Override
	public Set<String> visit(ExtendRule rule) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Set<String> visit(ForallRule rule) {
		return visit(rule.getDoRule());
	}

	@Override
	public Set<String> visit(LetRule rule) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Set<String> visit(MacroCallRule rule) throws Exception {
		Iterator<Term> parameters = rule.getParameters().iterator();
		MacroDeclaration calledMacro = rule.getCalledMacro();
		for (VariableTerm var : calledMacro.getVariable()) {
			termPrinter.mapVarTerm.put(var, parameters.next());
		}
		Set<String> macro = this.visit(calledMacro.getRuleBody());
		for (VariableTerm var : calledMacro.getVariable()) {
			termPrinter.mapVarTerm.remove(var);
		}
		return macro;
	}

	@Override
	public Set<String> visit(SeqRule seq) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Set<String> visit(SkipRule rule) {
		return Collections.EMPTY_SET;
	}

	@Override
	public Set<String> visit(TermAsRule rule) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Set<String> visit(UpdateRule rule) {
		// System.out.println("checking " + termPrinter.visit(rule.getLocation()) + "=="
		// + termPrinter.visit(term));
		if (termPrinter.visit(rule.getLocation()).equals(termPrinter.visit(term))) {
			return Collections.singleton(termPrinter.visit(rule.getUpdatingTerm()));
		}
		return Collections.EMPTY_SET;
	}
}