package org.asmeta.flattener.args;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class RemoveArgumentsRuleFlattener extends ReflectiveVisitor<Rule> {
	private RuleFactory ruleFact;

	public RemoveArgumentsRuleFlattener() {
		this.ruleFact = new RuleFactory();
	}

	public RemoveArgumentsRuleFlattener(RuleFactory ruleFact) {
		this.ruleFact = ruleFact;
	}

	public Rule visit(MacroCallRule macroCallRule) {
		//TODO: this is wrong.
		//It can be done only if the passed values are not used by reference,
		//i.e., if they are not updated in the left-hand side of an update rule
		/*RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		tf.setInLocationArguments(true);
		List<Term> newParameters = new ArrayList<Term>();
		for (Term par : macroCallRule.getParameters()) {
			newParameters.add(tf.visit(par));
		}
		MacroCallRule newMacroCallRule = ruleFact.createMacroCallRule();
		newMacroCallRule.setCalledMacro(macroCallRule.getCalledMacro());
		newMacroCallRule.getParameters().addAll(newParameters);
		return checkIfLetRule(tf, newMacroCallRule);*/
		return macroCallRule;
	}

	public Rule visit(ConditionalRule conditionalRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newGuard = tf.visit(conditionalRule.getGuard());
		ConditionalRule newConditionalRule = ruleFact.createConditionalRule();
		newConditionalRule.setGuard(newGuard);
		newConditionalRule.setThenRule(visit(conditionalRule.getThenRule()));
		assert newConditionalRule.getThenRule() != null;
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			newConditionalRule.setElseRule(visit(elseRule));
		}
		return checkIfLetRule(tf, newConditionalRule);
	}

	public Rule visit(CaseRule caseRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newTerm = tf.visit(caseRule.getTerm());
		CaseRule newCaseRule = ruleFact.createCaseRule();
		newCaseRule.setTerm(newTerm);
		List<Term> newCases = new ArrayList<Term>();
		List<Rule> newCaseBranches = new ArrayList<Rule>();
		Iterator<Rule> caseBranchesIt = caseRule.getCaseBranches().iterator();
		for (Term ct : caseRule.getCaseTerm()) {
			newCases.add(tf.visit(ct));
			newCaseBranches.add(visit(caseBranchesIt.next()));
		}
		newCaseRule.getCaseTerm().addAll(newCases);
		newCaseRule.getCaseBranches().addAll(newCaseBranches);
		Rule otherwiseBranch = caseRule.getOtherwiseBranch();
		if (otherwiseBranch != null) {
			newCaseRule.setOtherwiseBranch(visit(otherwiseBranch));
		}
		return checkIfLetRule(tf, newCaseRule);
	}

	public Rule visit(BlockRule blockRule) {
		BlockRule newBlockRule = ruleFact.createBlockRule();
		List<Rule> newRules = new ArrayList<Rule>();
		for (Rule rule : blockRule.getRules()) {
			newRules.add(visit(rule));
		}
		newBlockRule.getRules().addAll(newRules);
		return newBlockRule;
	}

	public Rule visit(TermAsRule termAsRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newTerm = tf.visit(termAsRule.getTerm());
		List<Term> newPars = termAsRule.getParameters();
		for (Term par : termAsRule.getParameters()) {
			newPars.add(tf.visit(par));
		}
		TermAsRule newTermAsRule = ruleFact.createTermAsRule();
		newTermAsRule.setTerm(newTerm);
		newTermAsRule.getParameters().addAll(newPars);
		return checkIfLetRule(tf, newTermAsRule);
	}

	public Rule visit(SeqRule seqRule) {
		SeqRule newSeqRule = ruleFact.createSeqRule();
		List<Rule> newSeqRuleRules = newSeqRule.getRules();
		for (Rule rule : seqRule.getRules()) {
			newSeqRuleRules.add(visit(rule));
		}
		return newSeqRule;
	}

	public Rule visit(ForallRule forallRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		ForallRule newForallRule = ruleFact.createForallRule();
		newForallRule.getRanges().addAll(forallRule.getRanges());
		newForallRule.getVariable().addAll(forallRule.getVariable());
		Term guard = forallRule.getGuard();
		if (guard != null) {
			newForallRule.setGuard(tf.visit(guard));
		}
		newForallRule.setDoRule(visit(forallRule.getDoRule()));
		return checkIfLetRule(tf, newForallRule);
	}

	public Rule visit(ChooseRule chooseRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newGuard = tf.visit(chooseRule.getGuard());
		ChooseRule newChooseRule = ruleFact.createChooseRule();
		newChooseRule.getRanges().addAll(chooseRule.getRanges());
		newChooseRule.getVariable().addAll(chooseRule.getVariable());
		newChooseRule.setGuard(newGuard);
		newChooseRule.setDoRule(visit(chooseRule.getDoRule()));
		Rule ifnoneRule = chooseRule.getIfnone();
		if (ifnoneRule != null) {
			newChooseRule.setIfnone(visit(ifnoneRule));
		}
		return checkIfLetRule(tf, newChooseRule);
	}

	public Rule visit(LetRule letRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		LetRule newLetRule = ruleFact.createLetRule();
		newLetRule.getVariable().addAll(letRule.getVariable());
		newLetRule.getInitExpression().addAll(letRule.getInitExpression());
		newLetRule.setInRule(visit(letRule.getInRule()));
		return checkIfLetRule(tf, newLetRule);
	}

	public Rule visit(UpdateRule updateRule) {
		RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
		Term newLoc = tf.visit(updateRule.getLocation());
		Term newUpTerm = tf.visit(updateRule.getUpdatingTerm());
		UpdateRule newUpdateRule = ruleFact.createUpdateRule();
		newUpdateRule.setLocation(newLoc);
		newUpdateRule.setUpdatingTerm(newUpTerm);
		return checkIfLetRule(tf, newUpdateRule);
	}

	private Rule checkIfLetRule(RemoveArgumentsTermFlattener tf, Rule rule) {
		Map<VariableTerm, Term> map = tf.getMapForLet();
		if (map.size() > 0) {
			return buildLetRule(rule, map);
		} else {
			return rule;
		}
	}

	private Rule buildLetRule(Rule rule, Map<VariableTerm, Term> map) {
		return buildLetRule(rule, map, ruleFact);
	}

	public static LetRule buildLetRule(Rule rule, Map<VariableTerm, Term> map, RuleFactory ruleFact) {
		//Statistics.getInstance().increaseValue(this.getClass().getName());
		//Statistics.getInstance().increaseValue("AR");
		
		LetRule lr = ruleFact.createLetRule();
		for (Entry<VariableTerm, Term> vt : map.entrySet()) {
			lr.getVariable().add(vt.getKey());
			lr.getInitExpression().add(vt.getValue());
		}
		assert lr.getVariable().size() == lr.getInitExpression().size();
		lr.setInRule(rule);
		return lr;
	}

	public Rule visit(SkipRule skipRule) {
		return skipRule;
	}
}
