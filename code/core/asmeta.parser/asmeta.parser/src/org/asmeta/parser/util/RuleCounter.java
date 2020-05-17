package org.asmeta.parser.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.asmeta.parser.ASMParser;

import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
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

/** it counts the number of rules (not only the declarations
 * */
public class RuleCounter extends org.asmeta.parser.util.ReflectiveVisitor<Void> {

	private Set<Rule> rules;

	public void visit(Asm asm) throws Exception {
		// this.asm = asm;
		if (asm.getMainrule() != null) {
			visit(asm.getMainrule().getRuleBody());
		}
		// can be null for module
		if (asm.getDefaultInitialState() != null) {
			for (AgentInitialization ai : asm.getDefaultInitialState().getAgentInitialization()) {
				visit(ai.getProgram());
			}
		}
	}

	public int getNumOfRules(Rule rule) {
		rules = new HashSet<Rule>();
		visit(rule);
		// return numOfRules;
		return rules.size();
	}

	public int getNumOfRules(Asm asm) throws Exception {
		rules = new HashSet<Rule>();
		visit(asm);
		// return numOfRules;
		return rules.size();
	}

	public int getNumOfRules(String asm) throws Exception {
		File asmFile = new File(asm);
		return getNumOfRules(ASMParser.setUpReadAsm(asmFile).getMain());
	}

	public void visit(BlockRule blockRule) {
		for (Rule rule : blockRule.getRules()) {
			visit(rule);
		}
	}

	public void visit(CaseRule caseRule) throws Exception {
		for (Rule branch : caseRule.getCaseBranches()) {
			visit(branch);
		}
		Rule otherRule = caseRule.getOtherwiseBranch();
		if (otherRule != null) {
			visit(otherRule);
		}
	}

	public void visit(ChooseRule chooseRule) throws Exception {
		visit(chooseRule.getDoRule());
		Rule ifNoneRule = chooseRule.getIfnone();
		if (ifNoneRule != null) {
			visit(ifNoneRule);
		}
	}

	public void visit(ConditionalRule conditionalRule) throws Exception {
		visit(conditionalRule.getThenRule());
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			visit(elseRule);
		}
	}

	public void visit(ForallRule forallRule) throws Exception {
		visit(forallRule.getDoRule());
	}

	public void visit(LetRule letRule) {
		visit(letRule.getInRule());
	}

	public void visit(MacroCallRule macroCallRule) {
		visit(macroCallRule.getCalledMacro().getRuleBody());
	}

	public void visit(Rule rule) {
		rules.add(rule);
		invokeMethod(rule, "visit");
	}

	public void visit(SeqRule seqRule) throws Exception {
		for (Rule rule : seqRule.getRules()) {
			visit(rule);
		}
	}

	public void visit(SkipRule skipRule) {
	}

	public void visit(UpdateRule updateRule) {
	}

	public void visit(TermAsRule termAsRule) {
	}
}
