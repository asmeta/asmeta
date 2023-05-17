package org.asmeta.flattener.rule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.term.DomainVisitor;
import org.asmeta.flattener.term.TermSimplifier;
import org.asmeta.flattener.util.StdlFunction;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
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

public class RuleSimplifier extends ReflectiveVisitor<Rule> implements AsmetaFlattener {
	static final Logger logger = Logger.getLogger(RuleSimplifier.class);

	private TermSimplifier ts;
	private Asm asm;
	private RuleFactory ruleFact;

	public RuleSimplifier(Asm asm) {
		this.asm = asm;
		DomainVisitor dv = new DomainVisitor(asm);
		dv.visitDomains();
		StdlFunction sl = new StdlFunction(asm);
		ts = new TermSimplifier(dv, sl);
		ruleFact = new RuleFactory();
	}

	@Override
	public Asm flattenASM() {
		List<RuleDeclaration> rules = asm.getBodySection().getRuleDeclaration();
		for (RuleDeclaration r : rules) {
			r.setRuleBody(visit(r.getRuleBody()));
		}
		return asm;
	}

	private List<Rule> simplifyRules(List<Rule> rules) {
		List<Rule> newRules = new ArrayList<>();
		for (Rule rule : rules) {
			Rule newRule = visit(rule);
			if (!(newRule instanceof SkipRule)) {
				newRules.add(newRule);
			}
		}
		return newRules;
	}

	public Rule visit(TermAsRule termAsRule) {
		TermAsRule newTermAsRule = ruleFact.createTermAsRule();
		newTermAsRule.setTerm(ts.visit(termAsRule.getTerm()));
		List<Term> oldParams = termAsRule.getParameters();
		for(Term p: oldParams) {
			newTermAsRule.getParameters().add(ts.visit(p));
		}
		//System.out.println(newTermAsRule.getTerm());
		return newTermAsRule;
	}

	public Rule visit(BlockRule blockRule) {
		List<Rule> newRules = simplifyRules(blockRule.getRules());
		if (newRules.size() == 0) {
			logger.debug("block rule simplified");
			// Statistics.getInstance().increaseValue(this.getCode());
			return ruleFact.createSkipRule();
		} else if (newRules.size() == 1) {
			logger.debug("block rule simplified");
			// Statistics.getInstance().increaseValue(this.getCode());
			return newRules.get(0);
		} else {
			BlockRule newBlockRule = ruleFact.createBlockRule();
			newBlockRule.getRules().addAll(newRules);
			return newBlockRule;
		}
	}

	public Rule visit(SeqRule seqRule) {
		List<Rule> newRules = simplifyRules(seqRule.getRules());
		if (newRules.size() == 0) {
			logger.debug("seq rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			return ruleFact.createSkipRule();
		} else if (newRules.size() == 1) {
			logger.debug("seq rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			return newRules.get(0);
		} else {
			SeqRule newSeqRule = ruleFact.createSeqRule();
			newSeqRule.getRules().addAll(newRules);
			return newSeqRule;
		}
	}

	public Rule visit(ConditionalRule condRule) {
		Term newGuard = ts.visit(condRule.getGuard());
		assert condRule.getThenRule() != null;
		Rule newThenRule = visit(condRule.getThenRule());
		Rule newElseRule = null;
		if (condRule.getElseRule() != null) {
			newElseRule = visit(condRule.getElseRule());
		}
		if (newGuard.equals(TermSimplifier.trueT)) {
			logger.debug("conditional rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			return newThenRule;
		} else if (newGuard.equals(TermSimplifier.falseT)) {
			logger.debug("conditional rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			if (newElseRule != null) {
				return newElseRule;
			} else {
				return ruleFact.createSkipRule();
			}
		}
		ConditionalRule newCondRule = ruleFact.createConditionalRule();
		newCondRule.setGuard(newGuard);
		assert newThenRule != null;
		newCondRule.setThenRule(newThenRule);
		newCondRule.setElseRule(newElseRule);
		return newCondRule;
	}

	public Rule visit(UpdateRule updateRule) {
		UpdateRule newUpdateRule = ruleFact.createUpdateRule();
		newUpdateRule.setLocation(ts.visit(updateRule.getLocation()));
		newUpdateRule.setUpdatingTerm(ts.visit(updateRule.getUpdatingTerm()));
		return newUpdateRule;
	}

	public Rule visit(ChooseRule chooseRule) {
		Term newGuard = ts.visit(chooseRule.getGuard());
		Rule newOther = null;
		if (chooseRule.getIfnone() != null) {
			newOther = visit(chooseRule.getIfnone());
		}
		if (newGuard.equals(TermSimplifier.falseT)) {
			logger.debug("choose rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			if (newOther != null) {
				return newOther;
			} else {
				return ruleFact.createSkipRule();
			}
		}
		ChooseRule newChooseRule = ruleFact.createChooseRule();
		newChooseRule.getVariable().addAll(chooseRule.getVariable());
		newChooseRule.getRanges().addAll(chooseRule.getRanges());
		newChooseRule.setGuard(newGuard);
		newChooseRule.setDoRule(visit(chooseRule.getDoRule()));
		;
		newChooseRule.setIfnone(newOther);
		return newChooseRule;
	}

	public Rule visit(ForallRule forallRule) {
		Term newGuard = ts.visit(forallRule.getGuard());
		if (newGuard.equals(TermSimplifier.falseT)) {
			logger.debug("forall rule simplified");
			Statistics.getInstance().increaseValue(this.getCode());
			return ruleFact.createSkipRule();
		}
		ForallRule newForallRule = ruleFact.createForallRule();
		newForallRule.getVariable().addAll(forallRule.getVariable());
		newForallRule.getRanges().addAll(forallRule.getRanges());
		newForallRule.setGuard(newGuard);
		newForallRule.setDoRule(visit(forallRule.getDoRule()));
		;
		return newForallRule;
	}

	public Rule visit(SkipRule skipRule) {
		return skipRule;
	}

	public Rule visit(CaseRule caseRule) {
		CaseRule newCaseRule = ruleFact.createCaseRule();
		newCaseRule.setTerm(ts.visit(caseRule.getTerm()));
		List<Term> caseTerm = caseRule.getCaseTerm();
		List<Rule> caseBranches = caseRule.getCaseBranches();
		List<Term> newCaseTerm = new ArrayList<Term>();
		List<Rule> newCaseBranches = new ArrayList<Rule>();
		for (int i = 0; i < caseTerm.size(); i++) {
			newCaseTerm.add(ts.visit(caseTerm.get(i)));
			newCaseBranches.add(visit(caseBranches.get(i)));
		}
		newCaseRule.getCaseTerm().addAll(newCaseTerm);
		newCaseRule.getCaseBranches().addAll(newCaseBranches);
		if (caseRule.getOtherwiseBranch() != null) {
			newCaseRule.setOtherwiseBranch(visit(caseRule.getOtherwiseBranch()));
		}
		return newCaseRule;
	}

	public Rule visit(MacroCallRule macroCallRule) {
		MacroCallRule newMacroCallRule = ruleFact.createMacroCallRule();
		newMacroCallRule.setCalledMacro(macroCallRule.getCalledMacro());
		List<Term> newParameters = new ArrayList<Term>();
		for (Term p : macroCallRule.getParameters()) {
			newParameters.add(ts.visit(p));
		}
		newMacroCallRule.getParameters().addAll(newParameters);
		return newMacroCallRule;
	}

	public Rule visit(LetRule letRule) {
		LetRule newLetRule = ruleFact.createLetRule();
		newLetRule.getVariable().addAll(letRule.getVariable());
		List<Term> initExp = letRule.getInitExpression();
		List<Term> newInitExp = new ArrayList<Term>();
		for (Term exp : initExp) {
			newInitExp.add(ts.visit(exp));
		}
		newLetRule.getInitExpression().addAll(newInitExp);
		newLetRule.setInRule(visit(letRule.getInRule()));
		return newLetRule;
	}

	public Rule visit(ExtendRule extendRule) {
		ExtendRule newExtendRule = ruleFact.createExtendRule();
		newExtendRule.getBoundVar().addAll(extendRule.getBoundVar());
		newExtendRule.setExtendedDomain(extendRule.getExtendedDomain());
		Rule newDoRule = visit(extendRule.getDoRule());
		assert newDoRule != null;
		newExtendRule.setDoRule(newDoRule);
		return newExtendRule;
	}

	@Override
	public String getCode() {
		return "RS";
	}
}
