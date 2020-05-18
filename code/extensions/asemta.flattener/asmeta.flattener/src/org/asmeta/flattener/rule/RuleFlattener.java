package org.asmeta.flattener.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.asmeta.flattener.term.DomainVisitor;
import org.asmeta.flattener.term.TermRenameVars;
import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.wrapper.RuleFactory;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
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

public abstract class RuleFlattener extends ReflectiveVisitor<Rule> implements AsmetaFlattener {
	static final Logger logger = Logger.getLogger(RuleFlattener.class);
	private static int counterForLogicVars = 0;
	protected RuleFactory ruleFact = new RuleFactory();
	protected Asm asm;
	protected TermRenameVars trv = new TermRenameVars();
	protected DomainVisitor dv;
	protected boolean rename = false;
	// public static boolean DO_STATS = true;

	public RuleFlattener(Asm asm) {
		this.asm = asm;
	}

	@Override
	public Asm flattenASM() {
		List<RuleDeclaration> rules = asm.getBodySection().getRuleDeclaration();
		for (RuleDeclaration r : rules) {
			Rule ruleBody = r.getRuleBody();
			assert ruleBody != null;
			r.setRuleBody(visit(ruleBody));
		}
		return asm;
	}

	public Rule visit(BlockRule blockRule) {
		int numRules = blockRule.getRules().size();
		BlockRule newBlockRule = ruleFact.createBlockRule();
		List<Rule> newRules = new ArrayList<>();
		for (Rule rule : blockRule.getRules()) {
			newRules.add(visit(rule));
		}
		newBlockRule.getRules().addAll(newRules);
		assert numRules == newBlockRule.getRules().size();
		return newBlockRule;
	}

	public Rule visit(SeqRule seqRule) {
		SeqRule newSeqRule = ruleFact.createSeqRule();
		for (Rule rule : seqRule.getRules()) {
			newSeqRule.getRules().add(visit(rule));
		}
		return newSeqRule;
	}

	public Rule visit(CaseRule caseRule) {
		CaseRule newCaseRule = ruleFact.createCaseRule();
		newCaseRule.setTerm(trv.visit(caseRule.getTerm()));
		for (Term caseTerm : caseRule.getCaseTerm()) {
			newCaseRule.getCaseTerm().add(trv.visit(caseTerm));
		}
		for (Rule branch : caseRule.getCaseBranches()) {
			newCaseRule.getCaseBranches().add(visit(branch));
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			newCaseRule.setOtherwiseBranch(visit(otherwise));
		}
		return newCaseRule;
	}

	public Rule visit(ChooseRule chooseRule) {
		ChooseRule newChooseRule = ruleFact.createChooseRule();

		Map<VariableTerm, Term> variablesMap = new HashMap<>();
		if (rename) {
			logger.debug("variables renamed");
			// logic variables must be renamed as the rule could be duplicated
			for (VariableTerm vt : chooseRule.getVariable()) {
				VariableTerm newVt = ruleFact.createVariableTerm();
				newVt.setDomain(vt.getDomain());
				newVt.setName(vt.getName() + (counterForLogicVars++));
				variablesMap.put(vt, newVt);
			}
			logger.debug("new map " + variablesMap);
			trv.addMap(variablesMap);
		}
		EList<VariableTerm> newVars = newChooseRule.getVariable();
		for (VariableTerm v : chooseRule.getVariable()) {
			newVars.add((VariableTerm) trv.visit(v));
		}
		newChooseRule.getRanges().addAll(chooseRule.getRanges());
		newChooseRule.setDoRule(visit(chooseRule.getDoRule()));
		assert newChooseRule.getDoRule() != null;
		newChooseRule.setGuard(trv.visit(chooseRule.getGuard()));
		if (chooseRule.getIfnone() != null) {
			newChooseRule.setIfnone(visit(chooseRule.getIfnone()));
		}
		if (rename) {
			// System.out.println("variable map reset");
			trv.removeMap(variablesMap);
		}
		return newChooseRule;
	}

	public Rule visit(ConditionalRule conditionalRule) {
		ConditionalRule newCondRule = ruleFact.createConditionalRule();
		newCondRule.setGuard(trv.visit(conditionalRule.getGuard()));
		assert conditionalRule.getThenRule() != null;
		newCondRule.setThenRule(visit(conditionalRule.getThenRule()));
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			newCondRule.setElseRule(visit(elseRule));
		}
		return newCondRule;
	}

	public Rule visit(ExtendRule extendRule) {
		ExtendRule newExtendRule = ruleFact.createExtendRule();
		newExtendRule.setExtendedDomain(extendRule.getExtendedDomain());

		Map<VariableTerm, Term> variablesMap = new HashMap<>();
		if (rename) {
			logger.debug("variables renamed");
			// logic variables must be renamed as the rule could be duplicated
			for (VariableTerm vt : extendRule.getBoundVar()) {
				VariableTerm newVt = ruleFact.createVariableTerm();
				newVt.setDomain(vt.getDomain());
				newVt.setName(vt.getName() + (counterForLogicVars++));
				variablesMap.put(vt, newVt);
			}
			logger.debug("new map " + variablesMap);
			trv.addMap(variablesMap);
		}
		List<VariableTerm> newVars = new ArrayList<VariableTerm>();
		for (VariableTerm v : extendRule.getBoundVar()) {
			newVars.add((VariableTerm) trv.visit(v));
		}
		newExtendRule.getBoundVar().addAll(newVars);

		Rule doRule = extendRule.getDoRule();
		assert doRule != null;
		newExtendRule.setDoRule(visit(doRule));
		if (rename) {
			trv.removeMap(variablesMap);
		}
		return newExtendRule;
	}

	public Rule visit(ForallRule forallRule) {
		ForallRule newForallRule = ruleFact.createForallRule();

		// logic variables must be renamed as the rule could be duplicated
		Map<VariableTerm, Term> variablesMap = new HashMap<>();
		if (rename) {
			logger.debug("variables renamed");

			for (VariableTerm vt : forallRule.getVariable()) {
				VariableTerm newVt = ruleFact.createVariableTerm();
				newVt.setDomain(vt.getDomain());
				String varName = vt.getName() + (counterForLogicVars++);
				newVt.setName(varName);
				variablesMap.put(vt, newVt);
				logger.debug(vt + " -> " + newVt);
			}
			trv.addMap(variablesMap);
		}
		EList<VariableTerm> newVars = newForallRule.getVariable();
		for (VariableTerm v : forallRule.getVariable()) {
			newVars.add((VariableTerm) trv.visit(v));
		}
		newForallRule.getRanges().addAll(forallRule.getRanges());
		newForallRule.setDoRule(visit(forallRule.getDoRule()));
		newForallRule.setGuard(trv.visit(forallRule.getGuard()));
		if (rename) {
			trv.removeMap(variablesMap);
		}
		return newForallRule;
	}

	public Rule visit(LetRule letRule) {
		LetRule newLetRule = ruleFact.createLetRule();
		// logic variables must be renamed as the rule could be duplicated
		Map<VariableTerm, Term> variablesMap = new HashMap<>();
		if (rename) {
			for (VariableTerm vt : letRule.getVariable()) {
				VariableTerm newVt = ruleFact.createVariableTerm();
				newVt.setDomain(vt.getDomain());
				// newVt.setName(vt.getName() + (counterForLogicVars++));
				newVt.setName(vt.getName());
				variablesMap.put(vt, newVt);
			}
			trv.addMap(variablesMap);
		}
		List<VariableTerm> newVars = newLetRule.getVariable();
		for (VariableTerm v : letRule.getVariable()) {
			newVars.add((VariableTerm) trv.visit(v));
		}
		List<Term> newInit = newLetRule.getInitExpression();
		for (Term init : letRule.getInitExpression()) {
			newInit.add(trv.visit(init));
		}
		newLetRule.setInRule(visit(letRule.getInRule()));
		if (rename) {
			trv.removeMap(variablesMap);
		}
		return newLetRule;
	}

	public Rule visit(MacroCallRule macroCallRule) {
		MacroCallRule newMacroCallRule = ruleFact.createMacroCallRule();
		MacroDeclaration calledMacro = macroCallRule.getCalledMacro();
		newMacroCallRule.setCalledMacro(calledMacro);
		for (Term p : macroCallRule.getParameters()) {
			newMacroCallRule.getParameters().add(trv.visit(p));
		}
		return newMacroCallRule;
	}

	public Rule visit(SkipRule skipRule) {
		return ruleFact.createSkipRule();
	}

	public Rule visit(TermAsRule termAsRule) {
		TermAsRule newTermAsRule = ruleFact.createTermAsRule();
		newTermAsRule.setTerm(trv.visit(termAsRule.getTerm()));
		EList<Term> newParameters = newTermAsRule.getParameters();
		for (Term p : termAsRule.getParameters()) {
			newParameters.add(trv.visit(p));
		}
		//System.out.println(((FunctionTermImpl)newTermAsRule.getTerm()).getFunction().getName());
		return newTermAsRule;
	}

	public Rule visit(UpdateRule updateRule) {
		UpdateRule newUpdateRule = ruleFact.createUpdateRule();
		newUpdateRule.setLocation(trv.visit(updateRule.getLocation()));
		newUpdateRule.setUpdatingTerm(trv.visit(updateRule.getUpdatingTerm()));
		return newUpdateRule;
	}
}
