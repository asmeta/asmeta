package org.asmeta.refinement;

import java.util.HashSet;
import java.util.Set;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

/**
 * It renames all the choose variables with the given replacement.
 *
 */
public class RenameChooseVariables extends ReflectiveVisitor {
	private String replacement;
	private Set<Rule> visitedRules;

	public RenameChooseVariables(String replacement) {
		this.replacement = replacement;
		visitedRules = new HashSet<Rule>();
	}

	public void visit(Rule r) {
		if(!visitedRules.contains(r)) {
			visitedRules.add(r);
			invokeMethod(r, "visit");
		}
	}

	public void visit(ChooseRule r) {
		for(VariableTerm var: r.getVariable()) {
			var.setName("$" + replacement + var.getName().substring(1));
		}
		visit(r.getDoRule());
		if(r.getIfnone() != null) {
			visit(r.getIfnone());
		}
	}

	public void visit(ForallRule r) {
		visit(r.getDoRule());
	}

	public void visit(LetRule r) {
		visit(r.getInRule());
	}

	public void visit(UpdateRule r) {		
	}

	public void visit(BlockRule r) {
		for(Rule rule: r.getRules()) {
			visit(rule);
		}
	}

	public void visit(ConditionalRule r) {
		visit(r.getThenRule());
		Rule elseRule = r.getElseRule();
		if(elseRule != null) {
			visit(elseRule);
		}
	}

	public void visit(SkipRule r) {		
	}

	public void visit(MacroCallRule r) {
		visit(r.getCalledMacro().getRuleBody());
	}

	public void visit(ExtendRule r) {
		visit(r.getDoRule());
	}

	public void visit(CaseRule r) {
		for(Rule rule: r.getCaseBranches()) {
			visit(rule);
		}
		Rule otherwiseBranch = r.getOtherwiseBranch();
		if(otherwiseBranch != null) {
			visit(otherwiseBranch);
		}
	}
}