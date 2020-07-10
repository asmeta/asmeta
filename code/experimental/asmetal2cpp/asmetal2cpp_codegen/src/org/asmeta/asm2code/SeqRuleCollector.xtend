package org.asmeta.asm2code

import asmeta.transitionrules.basictransitionrules.BlockRule
import asmeta.transitionrules.basictransitionrules.ChooseRule
import asmeta.transitionrules.basictransitionrules.ConditionalRule
import asmeta.transitionrules.basictransitionrules.ExtendRule
import asmeta.transitionrules.basictransitionrules.ForallRule
import asmeta.transitionrules.basictransitionrules.LetRule
import asmeta.transitionrules.basictransitionrules.MacroCallRule
import asmeta.transitionrules.basictransitionrules.MacroDeclaration
import asmeta.transitionrules.basictransitionrules.Rule
import asmeta.transitionrules.basictransitionrules.SkipRule
import asmeta.transitionrules.basictransitionrules.TermAsRule
import asmeta.transitionrules.basictransitionrules.UpdateRule
import asmeta.transitionrules.derivedtransitionrules.CaseRule
import asmeta.transitionrules.turbotransitionrules.SeqRule
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.asmeta.simulator.RuleVisitor
import org.eclipse.emf.common.util.EList

// it collects alla the macro rules that are called inside a seq rule
class SeqRuleCollector extends RuleVisitor<List<Rule>> {

	boolean seqBlock

	/**  seqBlock iff it is called in a seq rule*/
	new(boolean seqBlock) {
		this.seqBlock = seqBlock
	}

	override List<Rule> visit(BlockRule object) {
		return listRules(object.rules)
	}

	def private List<Rule> listRules(EList<Rule> rules) {
		var List<Rule> sb = new ArrayList
		for (var int i = 0; i < rules.size(); i++) {
			sb.addAll(new SeqRuleCollector(seqBlock).visit(rules.get(i)))
		}
		return sb
	}

	override List<Rule> visit(MacroCallRule object) {
		// if it is inside a seq, use the seq variant
		if(seqBlock) return Collections.singletonList(object.calledMacro.ruleBody) else return Collections.emptyList();
	}

	def List<Rule> visit(MacroDeclaration object) {
		return Collections.emptyList();
	}

	override List<Rule> visit(SeqRule object) {
		// force seq to true
		return new SeqRuleCollector(true).listRules(object.rules)
	}

	override List<Rule> visit(UpdateRule object) {
		return Collections.emptyList();
	}

	override List<Rule> visit(SkipRule object) {
		return Collections.emptyList();
	}

	override List<Rule> visit(CaseRule object) {
		var List<Rule> list = new ArrayList(listRules(object.caseBranches))
		if(object.otherwiseBranch !== null) list.addAll(this.visit(object.otherwiseBranch))
		return list
	}

	
	override visit(TermAsRule rule) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override visit(ExtendRule rule) {
		return this.visit(rule.doRule)
	}

	override visit(ConditionalRule rule) {
		var List<Rule> list = new ArrayList<Rule>
		list.addAll(this.visit(rule.thenRule))
		if(rule.elseRule !== null) list.addAll(this.visit(rule.elseRule))
		return list
	}

	override visit(LetRule rule) {
		return this.visit(rule.inRule)
	}

	override visit(ChooseRule rule) {
		var List<Rule> list = new ArrayList<Rule>
		list.addAll(this.visit(rule.doRule))
		if(rule.ifnone !== null) list.addAll(this.visit(rule.ifnone))
		return list
}
	override visit(ForallRule rule) {
		return this.visit(rule.doRule)
	}

}
