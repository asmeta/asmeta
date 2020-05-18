package org.asmeta.flattener.statistics;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.FunctionTerm;
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

public class StatisticsVisitor extends ReflectiveVisitor<Void> {
	private Asm asm;
	private Integer maxNestingLevel = 0;
	private Integer nestingLevel = 0;
	private LinkedHashMap<String, Integer> rulesMap;
	private HashMap<RuleDeclaration, Integer> ruleNesting;

	public StatisticsVisitor(Asm asm) {
		this.asm = asm;
		ruleNesting = new HashMap<RuleDeclaration, Integer>();
		rulesMap = new LinkedHashMap<String, Integer>();
		rulesMap.put("UpdateRule", 0);
		rulesMap.put("ParallelRule", 0);
		rulesMap.put("ConditionalRule", 0);
		rulesMap.put("ForallRule", 0);
		rulesMap.put("ChooseRule", 0);
		rulesMap.put("CaseRule", 0);
		rulesMap.put("LetRule", 0);
		rulesMap.put("MacroCallRule", 0);
	}

	public void visitAsm() {
		for(RuleDeclaration r: asm.getBodySection().getRuleDeclaration()) {
			visit(r.getRuleBody());
			ruleNesting.put(r, maxNestingLevel);
			nestingLevel = 0;
			maxNestingLevel = 0;
		}
		for(Integer m: ruleNesting.values()) {
			maxNestingLevel = Math.max(maxNestingLevel, m);
		}
	}

	public void updateNestingLevel(Integer i) {
		maxNestingLevel = Math.max(maxNestingLevel, i);
	}

	public void increaseRules(String s) {
		Integer i = rulesMap.get(s);
		if (i == null) {
			assert false : s;
			rulesMap.put(s, 1);
		} else {
			rulesMap.put(s, i + 1);
		}
	}

	public Integer getMaxNestingLevel() {
		return maxNestingLevel;
	}

	public HashMap<String, Integer> getRulesMap() {
		return rulesMap;
	}

	public Void visit(UpdateRule updateRule) {
		updateNestingLevel(nestingLevel);
		increaseRules("UpdateRule");
		// visit terms?
		return null;
	}

	public Void visit(BlockRule blockRule) {
		increaseRules("ParallelRule");
		for (Rule rule : blockRule.getRules()) {
			visit(rule);
		}
		return null;
	}

	public Void visit(CaseRule caseRule) {
		increaseRules("CaseRule");
		nestingLevel++;
		for (Rule branch : caseRule.getCaseBranches()) {
			visit(branch);
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			visit(otherwise);
		}
		nestingLevel--;
		return null;
	}

	public Void visit(ChooseRule chooseRule) {
		increaseRules("ChooseRule");
		nestingLevel++;
		visit(chooseRule.getDoRule());
		Rule ifNone = chooseRule.getIfnone();
		if (ifNone != null) {
			visit(ifNone);
		}
		nestingLevel--;
		return null;
	}

	public Void visit(ConditionalRule conditionalRule) {
		increaseRules("ConditionalRule");
		nestingLevel++;
		visit(conditionalRule.getThenRule());
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			visit(elseRule);
		}
		nestingLevel--;
		return null;
	}

	public Void visit(ExtendRule extendRule) {
		updateNestingLevel(nestingLevel);
		return null;
	}

	public Void visit(ForallRule forallRule) {
		increaseRules("ForallRule");
		nestingLevel++;
		visit(forallRule.getDoRule());
		nestingLevel--;
		return null;
	}

	public Void visit(LetRule letRule) {
		increaseRules("LetRule");
		visit(letRule.getInRule());
		return null;
	}

	public Void visit(MacroCallRule macroCallRule) {
		increaseRules("MacroCallRule");
		Integer macroNesting = ruleNesting.get(macroCallRule.getCalledMacro());
		nestingLevel = nestingLevel + macroNesting;
		updateNestingLevel(nestingLevel);
		nestingLevel = nestingLevel - macroNesting;
		return null;
	}

	public Void visit(SkipRule skipRule) {
		updateNestingLevel(nestingLevel);
		return null;
	}

	public Void visit(TermAsRule termAsRule) {
		Term term = termAsRule.getTerm();
		if (term instanceof FunctionTerm) {
			FunctionTerm f = (FunctionTerm) term;
			if (f.getFunction().getName().equals("program")) {
				Term agent = f.getArguments().getTerms().get(0);
loop:			for (Initialization is : asm.getInitialState()) {
					for (AgentInitialization ag : is.getAgentInitialization()) {
						//MacroDeclaration calledMacro = ag.getProgram().getCalledMacro();
						if (ag.getDomain() == agent.getDomain()) {
							increaseRules("MacroCallRule");
							Integer macroNesting = ruleNesting.get(ag.getProgram().getCalledMacro());
							nestingLevel = nestingLevel + macroNesting;
							updateNestingLevel(nestingLevel);
							nestingLevel = nestingLevel - macroNesting;
							break loop;
						}
					}
				}
			}
		}
		return null;
	}
}
