package org.asmeta.flattener.rule;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.flattener.term.ReplaceValueInTerm;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

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

public class ReplaceValue extends ReflectiveVisitor<Rule> {
	private ReplaceValueInTerm tv;
	private RuleFactory ruleFact = new RuleFactory();

	public ReplaceValue(Term[] values, List<VariableTerm> vars) {
		tv = new ReplaceValueInTerm(values, vars);
	}

	public ReplaceValue(ReplaceValueInTerm replaceValueInTerm) {
		tv = replaceValueInTerm;
	}

	public Rule visit(UpdateRule updateRule) {
		UpdateRule newRule = ruleFact.createUpdateRule();
		newRule.setLocation(tv.visit(updateRule.getLocation()));
		newRule.setUpdatingTerm(tv.visit(updateRule.getUpdatingTerm()));
		return newRule;
	}

	public ConditionalRule visit(ConditionalRule conditionalRule) {
		ConditionalRule newCondRule = ruleFact.createConditionalRule();
		newCondRule.setGuard(tv.visit(conditionalRule.getGuard()));
		Rule thenRule = conditionalRule.getThenRule();
		assert thenRule != null;
		Rule visitThenRule = visit(thenRule);
		assert visitThenRule != null;
		newCondRule.setThenRule(visitThenRule);
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			newCondRule.setElseRule(visit(elseRule));
		}
		return newCondRule;
	}

	public Rule visit(CaseRule caseRule) {
		CaseRule newCaseRule = ruleFact.createCaseRule();

		newCaseRule.setTerm(tv.visit(caseRule.getTerm()));

		List<Term> caseTerm = caseRule.getCaseTerm();
		List<Term> newCaseTerm = newCaseRule.getCaseTerm();
		for (Term t : caseTerm) {
			newCaseTerm.add(t);
		}

		EList<Rule> branches = caseRule.getCaseBranches();
		EList<Rule> newBranches = newCaseRule.getCaseBranches();
		for (Rule v : branches) {
			newBranches.add(visit(v));
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			newCaseRule.setOtherwiseBranch(visit(otherwise));
		}
		return newCaseRule;
	}

	public Rule visit(MacroCallRule macroCallRule) {
		MacroCallRule newMacroCallRule = ruleFact.createMacroCallRule();
		MacroDeclaration calledMacro = macroCallRule.getCalledMacro();
		newMacroCallRule.setCalledMacro(calledMacro);
		EList<Term> newParameters = newMacroCallRule.getParameters();
		for (Term t : macroCallRule.getParameters()) {
			newParameters.add(tv.visit(t));
		}
		return newMacroCallRule;
	}

	public Rule visit(BlockRule blockRule) {
		BlockRule newBlockRule = ruleFact.createBlockRule();
		visitListRules(blockRule.getRules(), newBlockRule.getRules());
		return newBlockRule;
	}

	public Rule visit(SeqRule seqRule) {
		SeqRule newSeqRule = ruleFact.createSeqRule();
		visitListRules(seqRule.getRules(), newSeqRule.getRules());
		return newSeqRule;
	}

	private void visitListRules(List<Rule> rules, List<Rule> newRules) {
		for (Rule r : rules) {
			newRules.add(visit(r));
		}
	}

	public Rule visit(TermAsRule termAsRule) {
		TermAsRule newTermAsRule = ruleFact.createTermAsRule();

		newTermAsRule.setTerm(tv.visit(termAsRule.getTerm()));

		EList<Term> parameters = termAsRule.getParameters();
		EList<Term> newParameters = newTermAsRule.getParameters();
		for (Term t : parameters) {
			newParameters.add(tv.visit(t));
		}

		return newTermAsRule;
	}

	public Rule visit(ForallRule forallRule) {
		ForallRule newForallRule = ruleFact.createForallRule();
		newForallRule.setGuard(tv.visit(forallRule.getGuard()));
		EList<Term> ranges = forallRule.getRanges();
		EList<Term> newRanges = newForallRule.getRanges();
		for (Term t : ranges) {
			newRanges.add(tv.visit(t));
		}
		EList<VariableTerm> variables = forallRule.getVariable();
		EList<VariableTerm> newVariables = newForallRule.getVariable();
		for (VariableTerm t : variables) {
			newVariables.add((VariableTerm) tv.visit(t));
		}
		Rule doRule = forallRule.getDoRule();
		newForallRule.setDoRule(visit(doRule));
		return newForallRule;
	}

	public Rule visit(ChooseRule chooseRule) {
		ChooseRule newChooseRule = ruleFact.createChooseRule();
		newChooseRule.setGuard(tv.visit(chooseRule.getGuard()));

		EList<Term> ranges = chooseRule.getRanges();
		EList<Term> newRanges = newChooseRule.getRanges();
		for (Term t : ranges) {
			newRanges.add(tv.visit(t));
		}

		EList<VariableTerm> variables = chooseRule.getVariable();
		EList<VariableTerm> newVariables = newChooseRule.getVariable();
		for (VariableTerm t : variables) {
			newVariables.add((VariableTerm) tv.visit(t));
		}
		Rule doRule = chooseRule.getDoRule();
		newChooseRule.setDoRule(visit(doRule));
		return newChooseRule;
	}

	public Rule visit(LetRule letRule) {
		LetRule newLetRule = ruleFact.createLetRule();
		EList<Term> initExpression = letRule.getInitExpression();
		EList<Term> newInitExpression = newLetRule.getInitExpression();
		for (Term t : initExpression) {
			newInitExpression.add(tv.visit(t));
		}
		EList<VariableTerm> variables = letRule.getVariable();
		EList<VariableTerm> newVariables = newLetRule.getVariable();
		for (VariableTerm t : variables) {
			newVariables.add((VariableTerm) tv.visit(t));
		}

		Rule doRule = letRule.getInRule();
		newLetRule.setInRule(visit(doRule));
		return newLetRule;
	}

	public Rule visit(SkipRule skipRule) {
		return skipRule;
	}

	public Rule visit(ExtendRule extendRule) {
		ExtendRule newExtendRule = ruleFact.createExtendRule();

		List<VariableTerm> boundVar = extendRule.getBoundVar();
		List<VariableTerm> newBoundVar = newExtendRule.getBoundVar();
		List<VariableTerm> newVars = new ArrayList<>();
		for (VariableTerm t : boundVar) {
			// newBoundVar.add((VariableTerm) tv.visit(t));
			newVars.add((VariableTerm) tv.visit(t));
		}
		newBoundVar.addAll(newVars);

		Rule doRule = extendRule.getDoRule();
		extendRule.setDoRule(visit(doRule));
		return newExtendRule;
	}
}
